package connections;

import java.util.List;


public class ItemURL {
	private final String name;
	private final String server;
	private final int port;
	private final String username;
	private final String password;
	private final String servicename;
	private final String drivertype;
	private final String protocol;
	private final String type;
	private final String database;
	private final String uri;
	private final String driver;
	private final String source;
	private final String validquery;
	private final String collection;
	private final List<Servers> servers;
	private final int minutes;
	private final int starttime;
	private final int readtimeout;
	private final String env;
	private final String lab;
	private final List<DBList> dblist;
	
	
	
	ItemURL(){
		protocol = getProtocol();
		name = getName();
		server = getServer();
		port = getPort();
		username = getUsername();
		password = getPassword();
		servicename = getServicename();
		drivertype = getDrivertype();
		type = getType();
		database = getDatabase();
		uri = getUri();
		driver = getDriver();
		source = getSource();
		validquery = getValidquery();
		collection = getCollection();
		servers = getServers();
		minutes = getMinutes();
		starttime = getStarttime();
		readtimeout = getReadtimeout();
		env = getEnv();
		lab = getLab();
		
		dblist = getDblist();
		
	}
	
	
	
	



	public List<DBList> getDblist() {
		return dblist;
	}







	public String getEnv() {
		return env;
	}





	public String getLab() {
		return lab;
	}

	
	
	
	
	
	
	
	public int getReadtimeout() {
		return readtimeout;
	}







	public int getStarttime() {
		return starttime;
	}

	
	
	
	public int getMinutes() {
		return minutes;
	}





	public List<Servers> getServers() {
		return servers;
	}





	public String getCollection() {
		return collection;
	}



	public String getSource() {
		return source;
	}
	
	
	/**
	 * @return the driver
	 */
	public String getDriver() {
		return driver;
	}


	public final String getUri() {
		return uri;
	}

	public final String getName() {
		return name;
	}
	
	
	public String getType() {
		return type;
	}
	public final String getServer() {
		return server;
	}
	
	public final int getPort() {
		return port;
	}
	
	public final String getUsername() {
		return username;
	}
	
	public final String getPassword() {
		return password;
	}
	
	public final String getServicename() {
		return servicename;
	}
	
	public final String getDrivertype() {
		return drivertype;
	}
	
	public final String getProtocol() {
		return protocol;
	}
	public String getDatabase() {
		return database;
	}

	public final String getValidquery() {
		return validquery;
	}
	
	
	
	
	
}

