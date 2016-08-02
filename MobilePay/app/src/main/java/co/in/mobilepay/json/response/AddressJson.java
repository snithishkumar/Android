package co.in.mobilepay.json.response;

import co.in.mobilepay.entity.AddressEntity;

/**
 * Created by Nithishkumar on 4/8/2016.
 */
public class AddressJson {

    private String name;
    private String addressUUID;
    private String mobile;
    private String address;
    private long lastModifiedTime;

    public AddressJson(){

    }

    public AddressJson(AddressEntity addressEntity){
        this.name = addressEntity.getName();
        this.addressUUID = addressEntity.getAddressUUID();
        this.mobile = addressEntity.getMobileNumber();
        this.address = addressEntity.getAddress();
        this.lastModifiedTime = addressEntity.getLastModifiedTime();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getAddressUUID() {
        return addressUUID;
    }

    public void setAddressUUID(String addressUUID) {
        this.addressUUID = addressUUID;
    }

    @Override
    public String toString() {
        return "AddressJson{" +
                "name='" + name + '\'' +
                ", addressUUID='" + addressUUID + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                ", lastModifiedTime=" + lastModifiedTime +
                '}';
    }
}
