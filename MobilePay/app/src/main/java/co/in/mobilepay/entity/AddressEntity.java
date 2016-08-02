package co.in.mobilepay.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import co.in.mobilepay.json.response.AddressJson;

/**
 * Created by Nithishkumar on 4/8/2016.
 */
@DatabaseTable(tableName = "AddressEntity")
public class AddressEntity {

    public static final String ADDRESS_ID = "AddressId";
    public static final String NAME = "Name";
    public static final String MOBILE_NUMBER = "MobileNumber";
    public static final String ADDRESS_UUID = "AddressUUID";
    public static final String ADDRESS = "Address";
    public static final String LAST_MODIFIED_TIME = "LastModifiedTime";
    public static final String IS_SYNCED = "IsSynced";
    public static final String IS_DEFAULT = "IsDefault";

    @DatabaseField(columnName = ADDRESS_ID,generatedId = true)
    private int addressId;
    @DatabaseField(columnName = NAME)
    private String name;
    @DatabaseField(columnName = MOBILE_NUMBER)
    private String mobileNumber;
    @DatabaseField(columnName = ADDRESS_UUID)
    private String addressUUID;

    @DatabaseField(columnName = ADDRESS)
    private String address;

    @DatabaseField(columnName = LAST_MODIFIED_TIME)
    private long lastModifiedTime;
    @DatabaseField(columnName = IS_SYNCED)
    private boolean isSynced;
    @DatabaseField(columnName = IS_DEFAULT)
    private boolean isDefault;

    public AddressEntity(){

    }

    public AddressEntity(AddressJson addressJson){
        toAddressEntity(addressJson);
    }

    public void toAddressEntity(AddressJson addressJson){
        this.addressUUID = addressJson.getAddressUUID();
        this.address = addressJson.getAddress();

        this.lastModifiedTime = addressJson.getLastModifiedTime();
        this.name = addressJson.getName();
        this.mobileNumber =addressJson.getMobile();

    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddressUUID() {
        return addressUUID;
    }

    public void setAddressUUID(String addressUUID) {
        this.addressUUID = addressUUID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public boolean isSynced() {
        return isSynced;
    }

    public void setSynced(boolean synced) {
        isSynced = synced;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public String toString() {
        return "AddressEntity{" +
                "addressId=" + addressId +
                ", name='" + name + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", addressUUID='" + addressUUID + '\'' +
                ", address='" + address + '\'' +
                ", lastModifiedTime=" + lastModifiedTime +
                ", isSynced=" + isSynced +
                ", isDefault=" + isDefault +
                '}';
    }
}
