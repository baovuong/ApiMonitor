package bvworks.apimonitor.bean;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class CallBean {
	
	private int id;
	private String name;
	private DateTime date;
	
	public CallBean() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public void setDate(String dateString) {
		//DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		setDate(DateTime.parse(dateString,format));
	}
	
}
