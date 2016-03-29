package com.lucene.pojo;

public class Hotel {
	private String id;
	private String name;
	private String city;
	private String description;
	
	public Hotel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Hotel(String id, String name, String city, String description) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		this.description = description;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Hotel [id=" + id + ", name=" + name + ", city=" + city + ", description=" + description + "]";
	}

}
