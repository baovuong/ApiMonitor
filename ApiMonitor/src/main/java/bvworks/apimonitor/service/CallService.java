package bvworks.apimonitor.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import bvworks.apimonitor.dao.CallDAO;

@Path("/calls")
public class CallService {
	
	
	@POST
	@Path("/add")
	public Response addCall(@FormParam("name") String name) {
		String output = "";
		int status = 0;
		
		CallDAO dao = CallDAO.getInstance();
		
		dao.addCall(name);
		
		output = "Successfully added: " + name;
		status = 200;
		
		return Response.status(status).entity(output).build();
	}
	
	@GET
	@Path("/list/{name}")
	@Produces("application/json")
	public Map<String,Object> getCalls(@DefaultValue("") @PathParam("name") String name) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		CallDAO dao = CallDAO.getInstance();
		
		if (name.equals("")) {
			result.put("calls", dao.getCalls());
		} else {
			result.put("calls", dao.getCallsByName(name));
		}
		
		return result;
	}
}
