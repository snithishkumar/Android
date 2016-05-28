package co.in.mobilepay.json.response;


import co.in.mobilepay.enumeration.DeviceType;

public class CloudMessageJson{
	
	private String cloudId;
	private DeviceType deviceType;
	public String getCloudId() {
		return cloudId;
	}
	public void setCloudId(String cloudId) {
		this.cloudId = cloudId;
	}
	public DeviceType getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}
	
	
	

}
