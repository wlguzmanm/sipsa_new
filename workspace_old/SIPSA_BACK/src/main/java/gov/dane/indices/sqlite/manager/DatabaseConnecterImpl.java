package gov.dane.indices.sqlite.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnecterImpl implements DatabaseConnecter{

	private String connectionUrl;
	public DatabaseConnecterImpl(String connectionUrl){
		this.connectionUrl = connectionUrl;
	}
	public Connection createConnection() throws SQLException {

		Connection connection = null;
		Properties connectionProps = new Properties();
		
		connection = DriverManager.getConnection(connectionUrl, connectionProps);

		return connection;
	}

	public String getConnectionUrl() {
		// TODO Auto-generated method stub
		return connectionUrl;
	}

}
