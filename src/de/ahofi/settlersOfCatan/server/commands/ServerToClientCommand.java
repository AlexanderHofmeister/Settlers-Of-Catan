package de.ahofi.settlersOfCatan.server.commands;

import java.io.Serializable;

public interface ServerToClientCommand extends Serializable {
	public void execute();
}
