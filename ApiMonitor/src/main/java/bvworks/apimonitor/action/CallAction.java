package bvworks.apimonitor.action;

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
}
