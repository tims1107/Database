package connections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;



import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

public class AbstractMongo implements IDao {
	
	private static LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();

	private static Logger logger = lc.getLogger("Mongo DAO");
	
	private MongoClient mongoclient;	
	private MongoDatabase db;
	
	private final Map<String,Object> map = new HashMap<String,Object>();
	
	private final List<ServerAddress> addrs = new ArrayList<ServerAddress>();
	
	public AbstractMongo(Object obj){
		createMap(obj);
		createDS();
	}
	

	
	
	private void createMap(Object obj){
		String k;
		Object v;
		
		Object [] parms = null;
		
		try {
						
			Method [] methods = obj.getClass().getMethods();
			
			for(Method m : methods){
				
				k = m.getName();
			
				if(k.startsWith("get")){
					v = m.invoke(obj, parms);
										
					map.put(k,v);
					
				}
				
			}
			
						
			
		} 
		 catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage());
		} catch (SecurityException e) {
			logger.error(e.getMessage());
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		}
		
		
	}

	public void createDS() {
		
		
		
		connect();
		
		
	}

	public void connect() {
		
		
		addrs.add(new ServerAddress((String)map.get("getServer"), (Integer)map.get("getPort")));
		
		mongoclient = new MongoClient(addrs);
		db = mongoclient.getDatabase((String) map.get("getDatabase"));

	}

	public void disconnect() {
		mongoclient.close();
		mongoclient = null;
	}

	public Object getConnection() {
		if(mongoclient == null) {
			connect();
		}
		return db;
	}

}
