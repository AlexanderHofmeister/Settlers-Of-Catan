package de.ahofi.settlersOfCatan.server;

import com.almasb.fxgl.net.NetworkConnection;

import de.ahofi.settlersOfCatan.client.commands.ClientToServerCommand;
import de.ahofi.settlersOfCatan.game.Game;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

/**
 * MultiServer for multiple concurrent network connections (clients)
 * <p>
 * Since there isn't a 1 to 1 connection, {@link #stop()} must be explicitly
 * called to attempt a clean shutdown of the server
 *
 */
public final class MultiServer extends NetworkConnection {

  private static class FullInetAddress {
    private final InetAddress address;
    private final int port;

    public FullInetAddress(final InetAddress address, final int port) {
      this.address = address;
      this.port = port;
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj instanceof FullInetAddress) {
        final FullInetAddress other = (FullInetAddress) obj;
        return this.address.getHostAddress().equals(other.address.getHostAddress()) && this.port == other.port;
      }
      return false;
    }
  }

  private class TCPConnectionThread extends Thread {
    private boolean running = true;
    private ServerSocket server;

    @Override
    public void run() {
      try {
        this.server = new ServerSocket(MultiServer.this.tcpPort);
      } catch (final Exception e) {
        this.running = false;
        return;
      }

      while (this.running) {
        try {
          final Socket socket = this.server.accept();
          socket.setTcpNoDelay(true);

          final TCPThread t = new TCPThread(socket);
          t.setDaemon(true);
          MultiServer.this.tcpThreads.add(t);
          t.start();
        } catch (final Exception e) {
        }
      }

      try {
        this.server.close();
      } catch (final Exception ignored) {
      }
    }
  }

  private class TCPThread extends Thread {
    private boolean running = false;
    private ObjectOutputStream outputStream;
    private final Socket socket;

    public TCPThread(final Socket socket) {
      this.socket = socket;
    }

    @Override
    public void run() {
      try (ObjectOutputStream out = new ObjectOutputStream(this.socket.getOutputStream());
              ObjectInputStream in = new ObjectInputStream(this.socket.getInputStream())) {
        this.outputStream = out;
        this.socket.setTcpNoDelay(true);
        this.running = true;

        while (this.running) {
          final Object data = in.readObject();
          if (data == ConnectionMessage.CLOSE) {
            System.out.println("close");
            this.running = false;
            break;
          }
          if (data == ConnectionMessage.CLOSING) {
            System.out.println("closing");
            this.outputStream.writeObject(ConnectionMessage.CLOSE);
            this.running = false;
            break;
          }
          System.out.println("parsing: " + data);
          MultiServer.this.parsers.getOrDefault(data.getClass(), d -> {
          }).parse((Serializable) data);
        }
      } catch (final Exception e) {
        this.running = false;
        MultiServer.this.tcpThreads.remove(this);
        try {
          this.socket.close();
        } catch (final IOException ignored) {
        }
        return;
      }

      MultiServer.this.tcpThreads.remove(this);
      try {
        this.socket.close();
      } catch (final IOException ignored) {
      }
    }
  }

  private class UDPConnectionThread extends Thread {
    private DatagramSocket outSocket;
    private boolean running = false;

    @Override
    public void run() {
      try (DatagramSocket socket = new DatagramSocket(MultiServer.this.udpPort)) {
        this.outSocket = socket;
        this.running = true;

        while (this.running) {
          try {
            final byte[] buf = new byte[16384];
            final DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
            socket.receive(datagramPacket);

            try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(datagramPacket.getData()))) {
              final Object data = in.readObject();
              final FullInetAddress addr = new FullInetAddress(datagramPacket.getAddress(), datagramPacket.getPort());

              if (data == ConnectionMessage.OPEN) {
                if (!MultiServer.this.addresses.contains(addr)) {
                  MultiServer.this.addresses.add(addr);
                }
              }
              if (data == ConnectionMessage.CLOSE) {
                MultiServer.this.addresses.remove(addr);
                continue;
              }
              if (data == ConnectionMessage.CLOSING) {
                final byte[] sendBuf = toByteArray(ConnectionMessage.CLOSE);
                MultiServer.this.udpThread.outSocket.send(new DatagramPacket(sendBuf, sendBuf.length, addr.address, addr.port));
                continue;
              }
              MultiServer.this.parsers.get(((ClientToServerCommand) data).getClass().getInterfaces()[0]).parse((Serializable) data);
            }
          } catch (final Exception e) {
          }
        }
      } catch (final Exception e) {
        this.running = false;
        return;
      }

    }
  }

  private final TCPConnectionThread tcpThread = new TCPConnectionThread();

  private final UDPConnectionThread udpThread = new UDPConnectionThread();

  private final List<TCPThread> tcpThreads = Collections.synchronizedList(new ArrayList<>());

  private final List<FullInetAddress> addresses = Collections.synchronizedList(new ArrayList<>());

  private final int tcpPort, udpPort;

  @Getter
  private final Game game;

  /**
   * Constructs and configures a multi server with default ports No network
   * operation is done at this point.
   */
  public MultiServer() {
    this.tcpPort = NetworkConfig.DEFAULT_TCP_PORT;
    this.udpPort = NetworkConfig.DEFAULT_UDP_PORT;

    this.tcpThread.setDaemon(true);
    this.udpThread.setDaemon(true);

    this.game = Game.getInstance();
  }

  @Override
  public void close() {
    stop();
  }

  @Override
  protected void sendTCP(final Serializable data) {
    synchronized (this.tcpThreads) {
      this.tcpThreads.stream().filter(tcpThread -> tcpThread.running).forEach(tcpThread -> {
        try {
          tcpThread.outputStream.writeObject(data);
        } catch (final Exception e) {
        }
      });
    }
  }

  @Override
  protected void sendUDP(final Serializable data) {
    if (this.udpThread.running) {
      byte[] buf;
      try {
        buf = toByteArray(data);
        synchronized (this.addresses) {
          for (final FullInetAddress addr : this.addresses) {
            try {
              this.udpThread.outSocket.send(new DatagramPacket(buf, buf.length, addr.address, addr.port));
            } catch (final Exception e) {
            }
          }
        }
      } catch (final Exception exception) {
        exception.printStackTrace();
      }
    } else {
      throw new IllegalStateException("UDP connection not active");
    }
  }

  /**
   * Starts the server. This performs an actual network operation of binding to
   * ports and listening for incoming connections.
   */
  public void start() {
    this.tcpThread.start();
    this.udpThread.start();
  }

  /**
   * Sends a message to all connected clients that the server is about to shut
   * down. Then stops the server and the connection threads.
   * <p>
   * Further calls to {@link #send(Serializable)} will throw
   * IllegalStateException
   */
  public void stop() {
    sendClosingMessage();

    this.tcpThread.running = false;
    try {
      this.tcpThread.server.close();
    } catch (final IOException ignored) {
    }

    this.tcpThreads.forEach(t -> t.running = false);
    this.udpThread.running = false;
  }
}