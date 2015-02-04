package bvworks.apimonitor.bean;

import java.util.Date;

public class CallBean {
	
	private int id;
	private String name;
	private Date date;
	
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
