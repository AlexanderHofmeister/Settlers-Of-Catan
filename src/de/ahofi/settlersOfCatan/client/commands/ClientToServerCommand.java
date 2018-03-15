package de.ahofi.settlersOfCatan.client.commands;

import de.ahofi.settlersOfCatan.server.MultiServer;

import java.io.Serializable;

public interface ClientToServerCommand extends Serializable {
  public void execute(MultiServer server);
}