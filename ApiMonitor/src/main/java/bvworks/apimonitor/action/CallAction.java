package bvworks.apimonitor.action;

import bvworks.apimonitor.bean.CallBean;
import bvworks.apimonitor.dao.CallDAO;

public class CallAction {
	
	public CallAction() {
		
	}
	
	public String[] getCallNames() {
		CallDAO dao = CallDAO.getInstance();
		return dao.getNames();
	}
}
