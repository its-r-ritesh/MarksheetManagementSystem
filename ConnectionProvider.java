package in.sterling.Connection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * A utility class to provide database connection.
 */


public class ConnectionProvider {

	private static Connection con;
	 /**
     * Returns a database connection.
     *
     * @return A Connection object representing a database connection.
     */

	public static Connection getConnection() {

		try {
			if (con == null) {
				 Properties props = new Properties();
	                FileInputStream in = new FileInputStream("C:\\Users\\91992\\eclipse-workspace\\MarksheetManagementSystem\\src\\in\\sterling\\Connection\\User.properties");
	                props.load(in);
	                in.close();
	                String driver = props.getProperty("driver");
	                String url = props.getProperty("url");
	                String username = props.getProperty("user");
	                String password = props.getProperty("pass");
				Class.forName(driver);
				con = DriverManager.getConnection(url, username, password);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;

	}
}
