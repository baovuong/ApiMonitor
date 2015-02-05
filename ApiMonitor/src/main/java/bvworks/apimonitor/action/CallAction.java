package bvworks.apimonitor.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

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
		Map<String,Integer> result = new HashMap<String,Integer>();
		
		CallDAO dao = CallDAO.getInstance();
		
		CallBean[] calls = dao.getCallsByName(name);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");		
		for (CallBean call : calls) {
			// by minute
			System.out.println("date: "+call.getDate().toString());
			String dateString = format.format(call.getDate());
			if (!result.containsKey(dateString)) {
				result.put(dateString, 1);
			} else {
				result.put(dateString, result.get(dateString) + 1);
			}
		}
		
		return result;
	}
}
