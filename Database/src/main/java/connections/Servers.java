package connections;

import java.util.List;

public class Servers {
	
	private final String server;
	private final int port;

	public Servers() {
		this.server = getServer();
		this.port = getPort();
	}

	public String getServer() {
		return server;
	}

	public int getPort() {
		return port;
	}
	
	
	
	

}
