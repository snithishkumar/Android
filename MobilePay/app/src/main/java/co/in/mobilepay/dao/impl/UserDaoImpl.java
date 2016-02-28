package co.in.mobilepay.dao.impl;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;

import co.in.mobilepay.dao.UserDao;
import co.in.mobilepay.db.DatabaseHelper;
import co.in.mobilepay.entity.UserEntity;

/**
 * Created by Nithish on 22-01-2016.
 */
public class UserDaoImpl extends BaseDaoImpl implements UserDao {


    Dao<UserEntity,Integer> userDao = null;


    public UserDaoImpl(Context context)throws SQLException{
       super(context);
        initDao();

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
        long count = userDao.queryBuilder().where().eq(UserEntity.IS_ACTIVE,true).countOf();
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
     * Create New User
     * @param userEntity
     * @throws SQLException
     */
    public void updateUser(UserEntity userEntity)throws SQLException{
        userDao.create(userEntity);
    }


    /**
     * Enable the User Account
     * @throws SQLException
     */
    public void updateUser()throws SQLException{
        UpdateBuilder<UserEntity, Integer> updateBuilder =  userDao.updateBuilder();
        updateBuilder.updateColumnValue(UserEntity.IS_ACTIVE,true);
        updateBuilder.update();
    }


    /**
     * Get UserEntity
     * @return
     * @throws SQLException
     */
    public UserEntity getUser()throws SQLException{
        try{
            return userDao.queryBuilder().queryForFirst();
        }catch (Exception e){
            e.printStackTrace();
            throw new SQLException(e);
        }

    }


}
