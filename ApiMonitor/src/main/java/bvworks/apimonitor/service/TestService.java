package bvworks.apimonitor.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/test")
public class TestService {
	private void recordCall(HttpServletRequest req) throws IOException {
		String url = "http://localhost:8080/apimonitor/api/calls/add";
		String charset = "UTF-8";  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()
		// ...
		
		String name = req.getRequestURI();
		String ip = req.getRemoteAddr();
		System.out.println("testservice.ip: "+ip);
		
		String query = String.format("name=%s&ip=%s", 
			     URLEncoder.encode(name,charset),
			     URLEncoder.encode(ip,charset));
		
		URLConnection connection = new URL(url).openConnection();
		connection.setDoOutput(true); // Triggers POST.
		connection.setRequestProperty("Accept-Charset", charset);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

		try (OutputStream output = connection.getOutputStream()) {
			output.write(query.getBytes(charset));			
		}

		
		InputStream response = connection.getInputStream();
		
		// let's print that data
		BufferedReader reader = new BufferedReader(new InputStreamReader(response));
		StringBuilder out = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			out.append(line);
		}
		System.out.println(out.toString());
		reader.close();
	}
	
	
	@GET
	@Path("/mock")
	public Response mockCall(@Context HttpServletRequest req) {
		
		try {
			recordCall(req);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int status = 200;
		String output = "executed mock call";
		return Response.status(status).entity(output).build();
		
	}
}
