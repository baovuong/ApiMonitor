<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="bvworks.apimonitor.action.CallAction"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="java.util.Date"%>
<%
	CallAction action = new CallAction();
String[] names = action.getCallNames();
String browserType = request.getHeader("User-Agent");
boolean mobile = false;
if (browserType.contains("Mobile"))
	mobile = true;
System.out.println("mobile: "+mobile);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>API Monitor</title>
<% if (mobile) {%>
<link rel="stylesheet"
    href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css">
<script src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
<%}%>
<script src="jquery-1.11.2.min.js"></script>
<script src="Chart.min.js"></script>
<script>

	function updateChart(labels, values) {
		$('#chart').replaceWith('<canvas id="chart" width="400" height="400"></canvas>');
		//var label = [0,1,2,3,4];
		//var data = [];
		var data = {
			labels : labels,
			datasets : [ {
				label : "Times Accessed",
				fillColor : "rgba(220,220,220,0.2)",
				strokeColor : "rgba(220,220,220,1)",
				pointColor : "rgba(220,220,220,1)",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				pointHighlightStroke : "rgba(220,220,220,1)",
				data : values
			} ]
		};

		var options = {

			///Boolean - Whether grid lines are shown across the chart
			scaleShowGridLines : true,

			//String - Colour of the grid lines
			scaleGridLineColor : "rgba(0,0,0,.05)",

			//Number - Width of the grid lines
			scaleGridLineWidth : 1,

			//Boolean - Whether to show horizontal lines (except X axis)
			scaleShowHorizontalLines : true,

			//Boolean - Whether to show vertical lines (except Y axis)
			scaleShowVerticalLines : true,

			//Boolean - Whether the line is curved between points
			bezierCurve : true,

			//Number - Tension of the bezier curve between points
			bezierCurveTension : 0.4,

			//Boolean - Whether to show a dot for each point
			pointDot : true,

			//Number - Radius of each point dot in pixels
			pointDotRadius : 4,

			//Number - Pixel width of point dot stroke
			pointDotStrokeWidth : 1,

			//Number - amount extra to add to the radius to cater for hit detection outside the drawn point
			pointHitDetectionRadius : 20,

			//Boolean - Whether to show a stroke for datasets
			datasetStroke : true,

			//Number - Pixel width of dataset stroke
			datasetStrokeWidth : 2,

			//Boolean - Whether to fill the dataset with a colour
			datasetFill : true,

		//String - A legend template

		};

		var ctx = $('#chart').get(0).getContext("2d");
		new Chart(ctx).Line(data, options);
	}
	
	function collectData() {
		
		var name = $("#apinames").val();
		
		$.ajax({
			url: 'api/calls/count/'+name,
			type: 'GET',
			dataType: 'json',
			data: {},
			success: function(data, textStatus, jqXHR) {
				var counts = data.counts;
				var labels = [];
				var values = [];
				for (var i=0; i<counts.length; i++) {
					labels.push(counts[i].date);
					values.push(counts[i].count);
				}
				
				updateChart(labels,values);
			},
			error: function() {
				alert("you done fucked up.");
			}
			
		});
	}
	
	$(document).ready(function() {
		collectData();
		
		$("#apinames").on('change',function() {
			//alert("changing data");
			collectData();
		});
	});
	
</script>

</head>
<body>

<%if (mobile) {%> <div data-role="page"> <%}%>

<%if (mobile) {%> <div data-role="header"> <%}%>
	<h1>API Monitor</h1>
<%if (mobile) {%> </div> <%}%>

<%if (mobile) {%><div data-role="main" class="ui-content"> <%}%>
<span>user agent: <%= browserType %></span>
<%if (mobile) {%><fieldset class="ui-field-contain"><label for="apinames">Name:</label><%}%>

	<select id="apinames">
	<%
	for (String name : names) {
	    out.println(String.format("<option value=\"%s\">%s</option>", name,name));
	}
	%>
	</select>
	<%if (mobile) {%></fieldset><%}%>
	
	<br />
	<canvas id="chart" width="400" height="400"></canvas>
    <%if (mobile) {%></div><%}%>
	<%if (mobile) {%></div><%}%>
</body>
</html>