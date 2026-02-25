package com.quickcommerce.thiskostha.dto;

public class CustomerAddressDTO {
	private LocationCordinates cordinates;
	private String flatNumber;
    private String floor;
    private String buildingName;
    private String street;
     private String addressType;
    private Boolean isDefault;
	
	public CustomerAddressDTO(LocationCordinates cordinates, String flatNumber, String floor, String buildingName,
			String street, String addressType, Boolean isDefault) {
		super();
		this.cordinates = cordinates;
		this.flatNumber = flatNumber;
		this.floor = floor;
		this.buildingName = buildingName;
		this.street = street;
		this.addressType = addressType;
		this.isDefault = isDefault;
	}
	public LocationCordinates getCordinates() {
		return cordinates;
	}
	public void setCordinates(LocationCordinates cordinates) {
		this.cordinates = cordinates;
	}
	public String getFlatNumber() {
		return flatNumber;
	}
	public void setFlatNumber(String flatNumber) {
		this.flatNumber = flatNumber;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public Boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	public CustomerAddressDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}
