/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package de.ahofi.settlersOfCatan.client.net;

import com.almasb.fxgl.core.logging.Logger;
import com.almasb.fxgl.net.NetworkConnection;

import de.ahofi.settlersOfCatan.server.ConnectionMessage;
import de.ahofi.settlersOfCatan.server.NetworkConfig;
import de.ahofi.settlersOfCatan.server.commands.ServerToClientCommand;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Client side of the network connection.
 * <p>
 * Example:
 *
 * <pre>
 * // server ip
 * Client client = new Client("127.0.0.1");
 * // add relevant parsers for messages from server
 * client.addParser(String.class, data -> System.out.println(data));
 * // connect to server
 * client.connect();
 * // send some messages
 * client.send("This is an example message");
 * // when done, disconnect from server
 * client.disconnect();
 * </pre>
 *
 * @author Almas Baimagambetov (AlmasB) (almaslvl@gmail.com)
 */
public final class Client extends NetworkConnection {

  private class TCPConnectionThread extends Thread {
    private ObjectOutputStream outputStream;
    private boolean running = false;

    @Override
    public void run() {
      try (Socket socket = new Socket(Client.this.serverIP, Client.this.tcpPort);
              ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
              ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
        this.outputStream = out;
        socket.setTcpNoDelay(true);
        Client.this.latch.countDown();
        this.running = true;

        onConnectionOpen();

        while (this.running) {
          final Object data = in.readObject();
          if (data == ConnectionMessage.CLOSE) {
            System.out.println("close");
            this.running = false;
            break;
          }
          if (data == ConnectionMessage.CLOSING) {
            System.out.println("closing");
            sendTCP(ConnectionMessage.CLOSE);
            this.running = false;
            break;
          }
          System.out.println("parsing " + data);
          Client.this.parsers.getOrDefault(data.getClass(), d -> {
          }).parse((Serializable) data);
        }
      } catch (final Exception e) {
        log.warning("Exception during TCP connection execution: " + e.getMessage());
        this.running = false;
        onConnectionClosed();
        return;
      }

      onConnectionClosed();
      log.debug("TCP connection closed normally");
    }
  }

  private class UDPConnectionThread extends Thread {
    private DatagramSocket outSocket;
    private boolean running = false;

    @Override
    public void run() {
      try (DatagramSocket socket = new DatagramSocket()) {
        this.outSocket = socket;
        Client.this.latch.countDown();
        this.running = true;

        sendUDP(ConnectionMessage.OPEN);

        while (this.running) {
          final byte[] buf = new byte[16384];
          final DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
          socket.receive(datagramPacket);

          try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(datagramPacket.getData()))) {
            final Object data = in.readObject();
            if (data == ConnectionMessage.CLOSE) {
              this.running = false;
              break;
            }
            if (data == ConnectionMessage.CLOSING) {
              sendUDP(ConnectionMessage.CLOSE);
              this.running = false;
              break;
            }
            Client.this.parsers.get(((ServerToClientCommand) data).getClass().getInterfaces()[0]).parse((Serializable) data);
          }
        }
      } catch (final Exception e) {
        log.warning("Exception during UDP connection execution: " + e.getMessage());
        this.running = false;
        return;
      }

      log.debug("UDP connection closed normally");
    }
  }

  private static final Logger log = Logger.get(Client.class);

  private final TCPConnectionThread tcpThread = new TCPConnectionThread();

  private final UDPConnectionThread udpThread = new UDPConnectionThread();
  private CountDownLatch latch;
  private final String serverIP;

  private InetAddress serverAddress;

  private final int tcpPort, udpPort;

  /**
   * Constructs a new client with given server IP configuration. No network
   * operation is done at this point.
   *
   * @param serverIP
   *          ip of the server machine
   */
  public Client(final String serverIP) {
    this(serverIP, NetworkConfig.DEFAULT_TCP_PORT, NetworkConfig.DEFAULT_UDP_PORT);
  }

  /**
   * Constructs a new client with given server IP and tcp/udp ports
   * configuration. No network operation is done at this point.
   *
   * @param serverIP
   *          ip of the server machine
   * @param tcpPort
   *          tcp port to use
   * @param udpPort
   *          udp port to use
   */
  public Client(final String serverIP, final int tcpPort, final int udpPort) {
    this.serverIP = serverIP;
    this.tcpPort = tcpPort;
    this.udpPort = udpPort;

    this.tcpThread.setDaemon(true);
    this.udpThread.setDaemon(true);
  }

  @Override
  public void close() {
    disconnect();
  }

  /**
   * Performs an actual (blocking for 10 sec) connection to the server.
   *
   * @return true if connected and all is OK, false if something failed
   * @throws Exception
   */
  public boolean connect() throws Exception {
    this.serverAddress = InetAddress.getByName(this.serverIP);

    this.latch = new CountDownLatch(2);
    this.tcpThread.start();
    this.udpThread.start();
    return this.latch.await(10, TimeUnit.SECONDS);
  }

  /**
   * Sends a message to server that client is about to disconnect and shuts down
   * connection threads.
   * <p>
   * Further calls to {@link #send(Serializable)} will throw
   * IllegalStateException
   */
  public void disconnect() {
    sendClosingMessage();

    this.tcpThread.running = false;
    this.udpThread.running = false;
  }

  @Override
  protected void sendTCP(final Serializable data) {
    if (this.tcpThread.running) {
      try {
        this.tcpThread.outputStream.writeObject(data);
      } catch (final Exception e) {
        handleError(e);
      }
    } else {
      throw new IllegalStateException("Client TCP is not connected");
    }
  }

  @Override
  protected void sendUDP(final Serializable data) {
    if (this.udpThread.running) {
      try {
        final byte[] buf = toByteArray(data);
        this.udpThread.outSocket.send(new DatagramPacket(buf, buf.length, this.serverAddress, this.udpPort));
      } catch (final Exception e) {
        handleError(e);
      }
    } else {
      throw new IllegalStateException("UDP connection not active");
    }
  }
}
