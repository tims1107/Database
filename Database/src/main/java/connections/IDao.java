package connections;

import java.sql.Connection;

public interface IDao {
	
	public void connect();
	public void disconnect();
	public Object getConnection();
}
