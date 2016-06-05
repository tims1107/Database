package connections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;
import org.vibur.dbcp.ViburDBCPDataSource;
import org.vibur.dbcp.pool.ConnHolder;
import org.vibur.objectpool.PoolService;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;

public class ConcreteDAO implements IDao {
	
	private static LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();

	private static Logger logger = lc.getLogger("Sql DAO Base");
	
	
	private ConnHolder conholder;
	private final ViburDBCPDataSource ds =  new ViburDBCPDataSource();
	private final PoolService<ConnHolder> pool;
	
	private final Map<String,Object> map = new HashMap<String,Object>();
		
	
	public ConcreteDAO(Object obj) {
		
		createMap(obj);
		createDS();
		pool = ds.getPool();
		
	}
	
	private void createMap(Object obj){
		
		logger.debug("Create datasource get map");
		
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
	

	private void createDS() {
		
		logger.debug("Creating DS");
		
						
	    ds.setJdbcUrl((String) map.get("getUri"));
	    ds.setUsername((String) map.get("getUsername"));
	    ds.setPassword((String) map.get("getPassword"));
	    ds.setDriverClassName((String) map.get("getDriver"));

	    ds.setPoolInitialSize(1);
	    ds.setPoolMaxSize(1);

	    ds.setConnectionIdleLimitInSeconds(10);
	    ds.setTestConnectionQuery((String) map.get("getValidquery"));
	    
	    ds.setPoolEnableConnectionTracking(true);
	    

	    ds.setLogQueryExecutionLongerThanMs(500);
	    ds.setLogStackTraceForLongQueryExecution(true);

	    ds.setStatementCacheMaxSize(5);
	   
	    
	    logger.info("Initialize Pool " + ds.getJdbcUrl());

	    ds.start();
	  

	}

	public void connect() {
		
		
		pool.reduceCreated(1, true);
		
		conholder = pool.tryTake(10,TimeUnit.SECONDS);
		
		logger.info("Connecting SQL... " + conholder.value());
		
		

	}

	public void disconnect() {
		logger.info("Restore pool..." );
				
		
		if(conholder.value() != null){
			try {
				conholder.value().close();
				pool.restore(conholder,true);
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		
		
		conholder = null;
	
	}
	

	public Connection getConnection() {
		
		if(conholder == null){
			connect();
			if(conholder != null){
				logger.info("Connection made : " + conholder.value());
				return conholder.value();
			}
		}
	
		return null;
	}

	

}
