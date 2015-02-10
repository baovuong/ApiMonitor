package bvworks.apimonitor.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import bvworks.apimonitor.action.CallAction;
import bvworks.apimonitor.bean.CallBean;
import bvworks.apimonitor.bean.CallCountBean;
import bvworks.apimonitor.dao.CallDAO;

@Path("/calls")
public class CallService {
	private CallAction action = new CallAction();
	
	@POST
	@Path("/add")
	public Response addCall(
			@FormParam("name") String name,
			@FormParam("ip") String ip) {
		
		System.out.println("callservice.ip: "+ip);
		
		String output = "";
		int status = 0;
		
		action.recordCall(name,ip);
		
		output = "Successfully added: " + name;
		status = 200;
		
		return Response.status(status).entity(output).build();
	}
	
	
	@GET
	@Path("/count/{name}/today")
	@Produces("application/json")
	public Map<String,Object> getCallCountToday(
			@PathParam("name") String name) {
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Integer> count = action.getCallCountToday(name);
		
		List<CallCountBean> countList = new LinkedList<CallCountBean>();
		Set<String> dates = count.keySet();
		for (String date : dates) {
			CallCountBean bean = new CallCountBean();
			bean.setDate(date);
			bean.setCount(count.get(date));
			countList.add(bean);
		}
		
		result.put("counts", countList);
		return result;
	}
	
	@GET
	@Path("/count/{name}")
	@Produces("application/json")
	public Map<String,Object> getCallCount(
			@PathParam("name") String name,
			@DefaultValue("hour") @QueryParam("per") String per) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Integer> count;
		if (per.equals("minute"))
			count = action.getCallCountPerMinute(name);
		else if (per.equals("hour"))
			count = action.getCallCountPerHour(name);
		else
			count = action.getCallCountPerDay(name);

		List<CallCountBean> countList = new LinkedList<CallCountBean>();
		Set<String> dates = count.keySet();
		for (String date : dates) {
			CallCountBean bean = new CallCountBean();
			bean.setDate(date);
			bean.setCount(count.get(date));
			countList.add(bean);
		}
		
		result.put("counts", countList);
		return result;
	}
	
	@GET
	@Path("/list")
	@Produces("application/json")
	public Map<String,Object> getCalls() {
		return getCallsByName("all");
	}
	
	@GET
	@Path("/list/{name}")
	@Produces("application/json")
	public Map<String,Object> getCallsByName(@PathParam("name") String name) {
		Map<String,Object> result = new HashMap<String,Object>();
		System.out.println("looking for: "+name);
		CallDAO dao = CallDAO.getInstance();
		CallBean[] calls = null;
		if (name.equals("all")) {
			calls = dao.getCalls();
		} else {
			System.out.println("looking for: "+name);
			calls = dao.getCallsByName(name);
		}
		
		result.put("calls", calls);
		
		return result;
	}
}
