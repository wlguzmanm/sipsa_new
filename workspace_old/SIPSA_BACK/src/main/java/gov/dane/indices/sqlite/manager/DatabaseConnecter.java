package gov.dane.indices.sqlite.manager;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConnecter {

/**
* Establishes a new connection to the database
* 
* @return A new connection to the database
* @throws SQLException
*/
public Connection createConnection() throws SQLException;

/**
* Returns the connection url
* 
* @return
*/
public String getConnectionUrl();
}