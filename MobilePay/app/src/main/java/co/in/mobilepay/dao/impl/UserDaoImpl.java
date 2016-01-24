package co.in.mobilepay.dao.impl;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import co.in.mobilepay.dao.UserDao;
import co.in.mobilepay.db.DatabaseHelper;
import co.in.mobilepay.entity.UserEntity;

/**
 * Created by Nithish on 22-01-2016.
 */
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

    DatabaseHelper databaseHelper = null;

    Dao<UserEntity,String> userDao = null;


    public UserDaoImpl(Context context)throws SQLException{
       super(context);

    }

    protected void initDao()throws SQLException{
        userDao = databaseHelper.getDao(UserEntity.class);

    }

    /**
     * Check whether any user is present or not
     * @return
     * @throws SQLException
     */
    public boolean isUserPresent()throws SQLException{
        long count = userDao.countOf();
        return count > 0 ? true : false;
    }

    /**
     * Create New User
     * @param userEntity
     * @throws SQLException
     */
    public void createUser(UserEntity userEntity)throws SQLException{
        userDao.create(userEntity);
    }

    /**
     * Get UserEntity
     * @return
     * @throws SQLException
     */
    public UserEntity getUser()throws SQLException{
        return userDao.queryBuilder().queryForFirst();
    }


}
