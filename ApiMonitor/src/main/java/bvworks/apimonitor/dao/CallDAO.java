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

import bvworks.apimonitor.data.Call;

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
	
	public Call[] getCallsByName(String name) {
		List<Call> calls = new LinkedList<Call>();
		Connection connection = null;
		try {
			connection  = createConnection();
			String query = "SELECT * FROM calls WHERE name=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			ResultSet rs = statement.executeQuery(query);
			
			while (rs.next()) {
				Call call = new Call();
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
		return calls.toArray(new Call[calls.size()]);			
	}
	
	public Call[] getCallsToday() {
		List<Call> calls = new LinkedList<Call>();
		Connection connection = null;
		try {
			connection  = createConnection();
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM calls WHERE date LIKE CONCAT(CURDATE(),'%')";
			ResultSet rs = statement.executeQuery(query);
			
			while (rs.next()) {
				Call call = new Call();
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
		return calls.toArray(new Call[calls.size()]);			
	}
	
	public Call[] getCalls() {
		List<Call> calls = new LinkedList<Call>();
		Connection connection = null;
		try {
			connection  = createConnection();
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM calls";
			ResultSet rs = statement.executeQuery(query);
			
			while (rs.next()) {
				Call call = new Call();
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

		return calls.toArray(new Call[calls.size()]);		
	}
}
