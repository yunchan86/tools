package com.utuky.activemq.commons;

import javax.jms.Connection;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;

import com.utuky.activemq.commons.model.ActiveConfigInfo;

public class ActiveMqConnection {

	private static ActiveMqConnection activeMqConnection ;
	private static PooledConnectionFactory pooledConnectionFactory;
	
	private ActiveMqConnection() {}
	
	public static synchronized ActiveMqConnection  getInstance(ActiveConfigInfo config) {
		if(activeMqConnection==null) {
			activeMqConnection = new ActiveMqConnection();
			initConnection(config);
		}
		return activeMqConnection;
	}
	
	private static void initConnection(ActiveConfigInfo config) {
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();  
        activeMQConnectionFactory.setUserName(config.getUsername());  
        activeMQConnectionFactory.setPassword(config.getPwd());  
        activeMQConnectionFactory.setBrokerURL(config.getBrokerUrl()); 
        pooledConnectionFactory = new PooledConnectionFactory(activeMQConnectionFactory);
        pooledConnectionFactory.setMaxConnections(config.getPoolSize());
	}
	
	public static Connection getConnction() {
		Connection connection = null;
		try {
			connection = pooledConnectionFactory.createConnection();
		} catch (Exception e) {
			throw new RuntimeException("ActiveMQ get Connection Exception via PoolConnection.",e);
		}
		return connection ;
	}
}
