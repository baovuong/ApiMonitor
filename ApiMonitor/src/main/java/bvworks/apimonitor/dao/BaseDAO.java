package bvworks.apimonitor.dao;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

public class BaseDAO {
	private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private final String DB_URL = "jdbc:mysql://localhost/apimonitor";	
	
	private String username = "";
	private String password = "";
	
	protected BaseDAO() {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// importing username and password
		
		URL path = BaseDAO.class.getResource("/sql/auth.ini");
		
		try {
			System.out.println(path.getFile());
			Wini authIni = new Wini(new File(path.getFile()));
			username = authIni.get("mysql", "username", String.class);
			password = authIni.get("mysql", "password", String.class);
		} catch (InvalidFileFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	protected Connection createConnection() {
		try {
			return (Connection) DriverManager.getConnection(DB_URL, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
