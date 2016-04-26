package co.in.mobilepay.json.response;


import co.in.mobilepay.enumeration.DeviceType;

public class CloudMessageJson extends TokenJson{
	
	private String cloudId;
	private String imeiNumber;
	private DeviceType deviceType;
	public String getCloudId() {
		return cloudId;
	}
	public void setCloudId(String cloudId) {
		this.cloudId = cloudId;
	}
	public String getImeiNumber() {
		return imeiNumber;
	}
	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}
	public DeviceType getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}
	
	
	

}
