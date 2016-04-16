package co.in.mobilepay.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import co.in.mobilepay.json.response.MerchantJson;

/**
 * Created by Nithish on 24-01-2016.
 */
@DatabaseTable(tableName = "MerchantEntity")
public class MerchantEntity {
    public static final String MERCHANT_ID = "MerchantId";
    public static final String MERCHANT_GUID = "MerchantGuid";
    public static final String MERCHANT_NAME = "MerchantName";
    public static final String MERCHANT_ADDRESS = "MerchantAddress";
    public static final String AREA = "Area";
    public static final String MOBILE_NUMBER = "MobileNumber";
    public static final String LAND_LINE_NUMBER = "LandLineNumber";
    public static final String PIN_CODE = "PinCode";
    public static final String SERVER_MERCHANT_ID = "ServerMerchantId";

    @DatabaseField(columnName = MERCHANT_ID,generatedId = true)
    private int merchantId;
    @DatabaseField(columnName = MERCHANT_GUID,unique = true)
    private String merchantGuid;
    @DatabaseField(columnName = MERCHANT_NAME)
    private String merchantName;
    @DatabaseField(columnName = MERCHANT_ADDRESS)
    private String merchantAddress;
    @DatabaseField(columnName = AREA)
    private String area;
    @DatabaseField(columnName = MOBILE_NUMBER)
    private long mobileNumber;
    @DatabaseField(columnName = LAND_LINE_NUMBER)
    private long landLineNumber;
    @DatabaseField(columnName = PIN_CODE)
    private String pinCode;

    @DatabaseField(columnName = SERVER_MERCHANT_ID)
    private int serverMerchantId;

    private long createdDateTime;
    private long lastModifiedDateTime;

    public MerchantEntity(){

    }

    public MerchantEntity(MerchantJson merchantJson){
        toClone(merchantJson);
    }

    public void toClone(MerchantJson merchantJson){
        this.merchantGuid = merchantJson.getMerchantUuid();
        this.merchantName = merchantJson.getMerchantName();
        this.merchantAddress = merchantJson.getAddress();
        this.area = merchantJson.getArea();
        this.mobileNumber = merchantJson.getMobileNumber();
        this.landLineNumber = merchantJson.getLandNumber();
        this.createdDateTime = merchantJson.getCreatedDateTime();
        this.lastModifiedDateTime = merchantJson.getLastModifiedDateTime();
        this.pinCode = merchantJson.getPinCode();
        this.serverMerchantId = merchantJson.getServerMerchantId();
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public int getServerMerchantId() {
        return serverMerchantId;
    }

    public void setServerMerchantId(int serverMerchantId) {
        this.serverMerchantId = serverMerchantId;
    }

    public String getMerchantGuid() {
        return merchantGuid;
    }

    public void setMerchantGuid(String merchantGuid) {
        this.merchantGuid = merchantGuid;
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

    public long getLandLineNumber() {
        return landLineNumber;
    }

    public void setLandLineNumber(long landLineNumber) {
        this.landLineNumber = landLineNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    @Override
    public String toString() {
        return "MerchantEntity{" +
                "merchantId=" + merchantId +
                ", merchantName='" + merchantName + '\'' +
                ", merchantAddress='" + merchantAddress + '\'' +
                ", area='" + area + '\'' +
                ", mobileNumber=" + mobileNumber +
                ", landLineNumber=" + landLineNumber +
                '}';
    }
}
