package com.quickcommerce.thiskostha.dto;

public class RestaurantDTO {
	
  private String name;
  private long mobileno;
  private String mailid;
  private String description;
  private int packagefees;
  private String type;
  
  public RestaurantDTO(String name, long mobileno, String mailid, String description, int packagefees, String type) {
	super();
	this.name = name;
	this.mobileno = mobileno;
	this.mailid = mailid;
	this.description = description;
	this.packagefees = packagefees;
	this.type = type;
  }
  public RestaurantDTO() {
	super();
  }
  
  public String getName() {
	return name;
}
  public void setName(String name) {
	this.name = name;
  }
  public long getMobileno() {
	return mobileno;
  }
  public void setMobileno(long mobileno) {
	this.mobileno = mobileno;
  }
  public String getMailid() {
	return mailid;
  }
  public void setMailid(String mailid) {
	this.mailid = mailid;
  }
  public String getDescription() {
	return description;
  }
  public void setDescription(String description) {
	this.description = description;
  }
  public int getPackagefees() {
	return packagefees;
  }
  public void setPackagefees(int packagefees) {
	this.packagefees = packagefees;
  }
  public String getType() {
	return type;
  }
  public void setType(String type) {
	this.type = type;
  }
  @Override
  public String toString() {
	return "RestaurantDTO [name=" + name + ", mobileno=" + mobileno + ", mailid=" + mailid + ", description="
			+ description + ", packagefees=" + packagefees + ", type=" + type + "]";
  }
  
}
