package co.in.mobilepay.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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
