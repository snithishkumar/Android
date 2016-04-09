package co.in.mobilepay.json.response;

import co.in.mobilepay.entity.AddressEntity;

/**
 * Created by Nithishkumar on 4/8/2016.
 */
public class AddressJson {

    private String name;
    private String addressUUID;
    private String mobile;
    private String street;
    private String address;
    private String area;
    private String city;
    private long postalCode;
    private long lastModifiedTime;

    public AddressJson(){

    }

    public AddressJson(AddressEntity addressEntity){
        this.name = addressEntity.getName();
        this.addressUUID = addressEntity.getAddressUUID();
        this.mobile = addressEntity.getMobileNumber();
        this.street = addressEntity.getStreet();
        this.address = addressEntity.getAddress();
        this.area = addressEntity.getArea();
        this.city = addressEntity.getCity();
        this.postalCode = addressEntity.getPostalCode();
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(long postalCode) {
        this.postalCode = postalCode;
    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", postalCode=" + postalCode +
                '}';
    }
}
