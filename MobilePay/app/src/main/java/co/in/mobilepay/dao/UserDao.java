package co.in.mobilepay.dao;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import co.in.mobilepay.db.DatabaseHelper;
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


}
