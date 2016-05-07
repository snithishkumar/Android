package co.in.mobilepay.dao;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import co.in.mobilepay.db.DatabaseHelper;
import co.in.mobilepay.entity.AddressEntity;
import co.in.mobilepay.entity.UserEntity;

/**
 * Created by Nithish on 21-01-2016.
 */
public interface UserDao {

    boolean isUserPresent()throws SQLException;


    void createUser(UserEntity userEntity)throws SQLException;

    void updateUser()throws SQLException;


    UserEntity getUser()throws SQLException;

    void updateUser(UserEntity userEntity)throws SQLException;

    UserEntity getUser(String mobileNumber)throws SQLException;

    long getLastModifiedTime()throws  SQLException;

    List<AddressEntity> getAddressEntityList()throws SQLException;

    AddressEntity getAddressEntity(String addressUUID)throws  SQLException;

    AddressEntity getDefaultAddress()throws  SQLException;

    AddressEntity getMostRecentAddress()throws SQLException;

    void setDefaultAddress(String addressUUID)throws  SQLException;

    List<AddressEntity> getUnSyncedAddress()throws SQLException;

    void saveAddress(AddressEntity addressEntity)throws SQLException;

    void updateAddress(AddressEntity addressEntity)throws SQLException;

    AddressEntity getAddressEntity(int  addressId)throws  SQLException;

    void setDefaultAddress(int addressId)throws  SQLException;

    void removeUser()throws SQLException;


}
