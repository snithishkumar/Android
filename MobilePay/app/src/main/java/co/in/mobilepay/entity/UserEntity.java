package co.in.mobilepay.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import co.in.mobilepay.json.request.RegisterJson;

/**
 * Created by Nithish on 21-01-2016.
 */
@DatabaseTable(tableName = "UserEntity")
public class UserEntity {

    public static final String USER_ID = "UserId";
    public static final String NAME = "Name";
    public static final String IMEI = "IMEI";
    public static final String MOBILE_NUMBER = "MobileNumber";
    public static final String IS_ACTIVE = "IsActive";
public static final String ACCESS_TOKEN = "accessToken";

    @DatabaseField(columnName = USER_ID,generatedId = true)
    private int userId;
    @DatabaseField(columnName = NAME)
    private String name;
    @DatabaseField(columnName = IMEI)
    private String imei;
    @DatabaseField(columnName = "Password")
    private String password;
    @DatabaseField(columnName = MOBILE_NUMBER)
    private String mobileNumber;
    @DatabaseField(columnName = IS_ACTIVE)
    private boolean isActive;
    @DatabaseField(columnName = ACCESS_TOKEN)
    private String accessToken;

    public UserEntity(){

    }

    public UserEntity(RegisterJson registerJson){
        this.name = registerJson.getName();
        this.imei = registerJson.getImei();
        this.password = registerJson.getPassword();
        this.mobileNumber = registerJson.getMobileNumber();
        this.isActive = true;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", imei='" + imei + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                '}';
    }
}
