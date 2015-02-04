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
	
	public void addCall(String call) {
		Connection connection = null;
		try {
			connection = createConnection();
			String sql = "INSERT INTO calls (date,name) VALUES (NOW(),?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, call);
			
			boolean thing = statement.execute();
			System.out.println("thing: "+thing);
			
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public CallBean[] getCallsByName(String name) {
		List<CallBean> calls = new LinkedList<CallBean>();
		Connection connection = null;
		try {
			connection  = createConnection();
			String query = "SELECT * FROM calls WHERE name=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			ResultSet rs = statement.executeQuery(query);
			
			while (rs.next()) {
				CallBean call = new CallBean();
				call.setId(rs.getInt("id"));
				call.setName(rs.getString("name"));
				DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:MI:SS");
				call.setDate(dateFormat.parse(rs.getString("date")));
				
				calls.add(call);
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
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
				CallBean call = new CallBean();
				call.setId(rs.getInt("id"));
				call.setName(rs.getString("name"));
				DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:MI:SS");
				call.setDate(dateFormat.parse(rs.getString("date")));
				
				calls.add(call);
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
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
				CallBean call = new CallBean();
				call.setId(rs.getInt("id"));
				call.setName(rs.getString("name"));
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				call.setDate(dateFormat.parse(rs.getString("date")));
				
				calls.add(call);
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

		return calls.toArray(new CallBean[calls.size()]);		
	}
}
