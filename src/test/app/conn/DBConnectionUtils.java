/**
 * 
 */
package test.app.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author paulp
 * 
 * This is a utility class to establish the connection
 * to the database (In this case a MySQL database) 
 */
public class DBConnectionUtils
{
	private static final String CONN_DRIVER = "com.mysql.jdbc.Driver";	
	private static final String HOST_NAME = "localhost";
	private static final String DB_NAME = "giphy";
	private static final String CONN_URL = "jdbc:mysql://" + HOST_NAME + ":3306/" + DB_NAME;
	// This is just for this demo purpose. Ideally the credentials
	// are stored in encrypted form in config files
	private static final String USERNAME = "root";
	private static final String PASSWORD = "password";
	
	/**
	 * Constructor
	 */
	public DBConnectionUtils()
	{
	}

	/**
	 * Retrieve the connecttion to the database
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getMySQLConnection() throws ClassNotFoundException, SQLException
	{
		return getMySQLConnection(HOST_NAME, DB_NAME, USERNAME, PASSWORD);
	}

	public static Connection getMySQLConnection(String hostName, String dbName, String userName, String password)
			throws SQLException, ClassNotFoundException
	{
		Class.forName(CONN_DRIVER);		
		Connection conn = DriverManager.getConnection(CONN_URL, userName, password);
		return conn;
	}
}
