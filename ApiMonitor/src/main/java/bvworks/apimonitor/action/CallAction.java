package bvworks.apimonitor.action;

import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import bvworks.apimonitor.bean.CallBean;
import bvworks.apimonitor.dao.CallDAO;

public class CallAction {
	
	public CallAction() {
		
	}
	
	public String[] getCallNames() {
		CallDAO dao = CallDAO.getInstance();
		return dao.getNames();
	}

	public void recordCall(String name, String ip) {
		// TODO Auto-generated method stub
		CallDAO dao = CallDAO.getInstance();
		System.out.println("callaction.ip: "+ip);
		dao.addCall(name.substring(1).replace('/', '.'),ip);
	}
	
	public Map<String,Integer> getCallCount(String name) {
		return getCallCountPerMinute(name);
	}
	
	public Map<String,Integer> getCallCountPerMinute(String name) {
		String pattern = "yyyy-MM-dd HH:mm";
		
		TreeMap<String,Integer> result = getCallCountViaDateFormat(name,pattern);
		DateTimeFormatter format = DateTimeFormat.forPattern(pattern);		
		DateTime first = DateTime.parse(result.keySet().iterator().next(),format);
		DateTime last = DateTime.now();
		
		DateTime current = first;
		while (current.isBefore(last) || current.equals(last)) {
			String currentString = format.print(current);
			System.out.println(currentString);
			if (!result.keySet().contains(currentString))
				result.put(currentString, 0);
			current.plusMinutes(1);
		}
		return result;
	}
	
	public Map<String,Integer> getCallCountPerHour(String name) {
		String pattern = "yyyy-MM-dd HH";
		
		TreeMap<String,Integer> result = getCallCountViaDateFormat(name,pattern);
		
		DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
		
		DateTime first = DateTime.parse(result.keySet().iterator().next(),format);
		DateTime last = DateTime.now();
		
		DateTime current = first;
		while (current.isBefore(last) || current.equals(last)) {
			String currentString = format.print(current);
			System.out.println(currentString);
			if (!result.keySet().contains(currentString))
				result.put(currentString, 0);
			current.plusHours(1);
		}
		
		
		return result;
	}
	
	
	
	public Map<String,Integer> getCallCountPerDay(String name) {
		String pattern = "yyyy-MM-dd";
		TreeMap<String,Integer> result = getCallCountViaDateFormat(name,pattern);
		
		DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
		
		DateTime first = DateTime.parse(result.keySet().iterator().next(),format);
		DateTime last = DateTime.now();
		
		DateTime current = first;
		while (current.isBefore(last) || current.equals(last)) {
			String currentString = format.print(current);
			System.out.println(currentString);
			if (!result.keySet().contains(currentString))
				result.put(currentString, 0);
			current.plusDays(1);
		}
		
		
		return result;
	}
	
	private TreeMap<String,Integer> getCallCountViaDateFormat(String name, String formatString) {
		TreeMap<String,Integer> result = new TreeMap<String,Integer>();
		
		CallDAO dao = CallDAO.getInstance();
		
		CallBean[] calls = dao.getCallsByName(name);
		DateTimeFormatter format = DateTimeFormat.forPattern(formatString);	
		for (CallBean call : calls) {	
			String dateString = format.print(call.getDate());
			if (!result.containsKey(dateString)) {
				result.put(dateString, 1);
			} else {
				result.put(dateString, result.get(dateString) + 1);
			}
		}
		
		return result;			
	}
}
