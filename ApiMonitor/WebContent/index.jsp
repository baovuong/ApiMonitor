<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bvworks.apimonitor.dao.CallDAO" %>
<%@ page import="bvworks.apimonitor.action.CallAction" %>

<%
CallAction action = new CallAction();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>API Monitor</title>
<script src="jquery-1.11.2.min.js"></script>

</head>
<body>
<h1>API Monitor</h1>
<select id="apinames">
<% 
String[] names = action.getCallNames();
for (String name : names) {
	out.println(String.format("<option value=\"%s\">%s</option>", name,name));
}
%>
</select>
<canvas id="myChart" width="400" height="400"></canvas>

</body>
</html>