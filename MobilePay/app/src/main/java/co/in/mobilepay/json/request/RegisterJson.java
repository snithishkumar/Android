package co.in.mobilepay.json.request;

import co.in.mobilepay.entity.UserEntity;

/**
 * Created by Nithish on 23-01-2016.
 */
public class RegisterJson{

    private String name;
    private String password;
    private String mobileNumber;
    private String imei;
    private String email;
    private boolean isPasswordForget;

    public RegisterJson(){

    }

    public RegisterJson(String name,String password,String mobileNumber,String imei,boolean isPasswordForget,String email){
        this.name = name;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.imei = imei;
        this.isPasswordForget = isPasswordForget;
        this.email = email;
    }

    public RegisterJson(UserEntity userEntity){
        this.name = userEntity.getName();
        this.password = userEntity.getPassword();
        this.mobileNumber = userEntity.getMobileNumber();
        this.imei = userEntity.getImei();
        this.email = userEntity.getEmail();
    }

    public boolean isPasswordForget() {
        return isPasswordForget;
    }

    public void setPasswordForget(boolean passwordForget) {
        isPasswordForget = passwordForget;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "RegisterJson [name=" + name + ", password=" + password + ", mobileNumber=" + mobileNumber + ", imei="
                + imei + "]";
    }

}
