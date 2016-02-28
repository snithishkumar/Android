package co.in.mobilepay.json.request;

import co.in.mobilepay.entity.UserEntity;

/**
 * Created by Nithish on 23-01-2016.
 */
public class RegisterJson {

    private String name;
    private String password;
    private String mobileNumber;
    private String imei;

    public RegisterJson(){

    }

    public RegisterJson(String name,String password,String mobileNumber,String imei){
        this.name = name;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.imei = imei;
    }

    public RegisterJson(UserEntity userEntity){
        this.name = userEntity.getName();
        this.password = userEntity.getPassword();
        this.mobileNumber = userEntity.getMobileNumber();
        this.imei = userEntity.getImei();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    @Override
    public String toString() {
        return "RegisterJson [name=" + name + ", password=" + password + ", mobileNumber=" + mobileNumber + ", imei="
                + imei + "]";
    }

}
