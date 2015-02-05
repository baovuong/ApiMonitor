package bvworks.apimonitor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import bvworks.apimonitor.bean.CallBean;

public class CallDAO extends BaseDAO {
	private static CallDAO instance = null;
	private static Object mutex = new Object();
	
	public static CallDAO getInstance() {
		if (instance == null) {
			synchronized (mutex) {
				if (instance == null) instance = new CallDAO();
			}
		}
		return instance;
	}
	
	public String[] getNames() {
		List<String> names = new LinkedList<String>();
		Connection connection = null;
		try {
			connection  = createConnection();
			Statement statement = connection.createStatement();
			String query = "SELECT DISTINCT name FROM calls";
			ResultSet rs = statement.executeQuery(query);
			
			while (rs.next()) {
				names.add(rs.getString("name"));
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

		return names.toArray(new String[names.size()]);	
	}
	
	public void addCall(String call, String ip) {
		System.out.println("calldao.ip: "+ip);
		
		Connection connection = null;
		try {
			connection = createConnection();
			String sql = "INSERT INTO calls (date,name,ip_address) VALUES (NOW(),?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, call);
			statement.setString(2, ip);
			
			statement.execute();
			
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public CallBean[] getCallsByName(String name) {
		System.out.println("dao looking for "+name);
		List<CallBean> calls = new LinkedList<CallBean>();
		Connection connection = null;
		try {
			connection  = createConnection();
			String query = "SELECT * FROM calls WHERE name = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			ResultSet rs = statement.executeQuery();
			
			while (rs.next()) {
				calls.add(fetchCall(rs));
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
		return calls.toArray(new CallBean[calls.size()]);			
	}
	
	public CallBean[] getCallsToday() {
		List<CallBean> calls = new LinkedList<CallBean>();
		Connection connection = null;
		try {
			connection  = createConnection();
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM calls WHERE date LIKE CONCAT(CURDATE(),'%')";
			ResultSet rs = statement.executeQuery(query);
			
			while (rs.next()) {
				calls.add(fetchCall(rs));
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
		return calls.toArray(new CallBean[calls.size()]);			
	}
	
	public CallBean[] getCalls() {
		List<CallBean> calls = new LinkedList<CallBean>();
		Connection connection = null;
		try {
			connection  = createConnection();
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM calls";
			ResultSet rs = statement.executeQuery(query);
			
			while (rs.next()) {
				calls.add(fetchCall(rs));
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

		return calls.toArray(new CallBean[calls.size()]);		
	}
	
	private CallBean fetchCall(ResultSet rs) throws SQLException {
		CallBean call = new CallBean();
		call.setId(rs.getInt("id"));
		call.setDate(rs.getString("date"));
		call.setName(rs.getString("name"));
		return call;
	}
}
