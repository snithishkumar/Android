package co.in.mobilepay.json.response;


public class MerchantJson {

	private String merchantName;
	private String merchantUuid;
	private String address;
	private String area;
	private String pinCode;
	private long mobileNumber;
	private long landNumber;
    private long createdDateTime;
    private long lastModifiedDateTime;
	private int serverMerchantId;

	public MerchantJson() {

	}

	public int getServerMerchantId() {
		return serverMerchantId;
	}

	public void setServerMerchantId(int serverMerchantId) {
		this.serverMerchantId = serverMerchantId;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public long getLandNumber() {
		return landNumber;
	}

	public void setLandNumber(long landNumber) {
		this.landNumber = landNumber;
	}

    public String getMerchantUuid() {
        return merchantUuid;
    }

    public void setMerchantUuid(String merchantUuid) {
        this.merchantUuid = merchantUuid;
    }

    public long getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(long createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public long getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public void setLastModifiedDateTime(long lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }

    @Override
	public String toString() {
		return "MerchantJson [merchantName=" + merchantName + ", address=" + address + ", area=" + area
				+ ", mobileNumber=" + mobileNumber + ", landNumber=" + landNumber + "]";
	}

}
