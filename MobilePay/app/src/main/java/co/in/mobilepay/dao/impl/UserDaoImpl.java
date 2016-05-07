package co.in.mobilepay.dao.impl;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.List;

import co.in.mobilepay.dao.UserDao;
import co.in.mobilepay.db.DatabaseHelper;
import co.in.mobilepay.entity.AddressEntity;
import co.in.mobilepay.entity.UserEntity;

/**
 * Created by Nithish on 22-01-2016.
 */
public class UserDaoImpl extends BaseDaoImpl implements UserDao {


    Dao<UserEntity,Integer> userDao = null;
    Dao<AddressEntity,Integer> addressDao = null;


    public UserDaoImpl(Context context)throws SQLException{
       super(context);
        initDao();

    }

    protected void initDao()throws SQLException{
        userDao = databaseHelper.getDao(UserEntity.class);
        addressDao = databaseHelper.getDao(AddressEntity.class);

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


    @Override
    public void removeUser()throws SQLException{
        userDao.deleteBuilder().delete();
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
        userDao.update(userEntity);
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
    @Override
    public UserEntity getUser()throws SQLException{
        try{
            return userDao.queryBuilder().queryForFirst();
        }catch (Exception e){
            e.printStackTrace();
            throw new SQLException(e);
        }

    }


    /***
     * Get LastModifiedTime (Delivery address)
     * @return
     * @throws SQLException
     */
    @Override
    public long getLastModifiedTime()throws  SQLException{
       AddressEntity addressEntity =  addressDao.queryBuilder().orderBy(AddressEntity.LAST_MODIFIED_TIME,false).where().eq(AddressEntity.IS_SYNCED,false).queryForFirst();
        return addressEntity != null ? addressEntity.getLastModifiedTime() : 0;
    }


    /**
     * Returns List of AddressEntity
     * @return
     * @throws SQLException
     */
    @Override
    public List<AddressEntity> getAddressEntityList()throws SQLException{
        return addressDao.queryBuilder().orderBy(AddressEntity.LAST_MODIFIED_TIME,false).query();
    }

    /**
     * Returns AddressEntity for an Given UUID
     * @param addressUUID
     * @return
     * @throws SQLException
     */
    @Override
    public AddressEntity getAddressEntity(String addressUUID)throws  SQLException{
        return  addressDao.queryBuilder().where().eq(AddressEntity.ADDRESS_UUID, addressUUID).queryForFirst();
    }

    /**
     * Returns AddressEntity for an Id
     * @param addressId
     * @return
     * @throws SQLException
     */
    @Override
    public AddressEntity getAddressEntity(int  addressId)throws  SQLException{
        return  addressDao.queryBuilder().where().eq(AddressEntity.ADDRESS_ID, addressId).queryForFirst();
    }


    /**
     * Returns last used AddressEntity
     * @return
     * @throws SQLException
     */
    @Override
    public AddressEntity getDefaultAddress()throws  SQLException{
        return  addressDao.queryBuilder().where().eq(AddressEntity.IS_DEFAULT, true).queryForFirst();
    }


    /**
     * Set IS_DEFAULT to true for given address UUID and IS_DEFAULT to false for others
     * @param addressUUID
     * @throws SQLException
     */
    public void setDefaultAddress(String addressUUID)throws  SQLException{
        UpdateBuilder<AddressEntity,Integer> updateBuilder = addressDao.updateBuilder();
        updateBuilder.updateColumnValue(AddressEntity.IS_DEFAULT, false);
        updateBuilder.update();

        updateBuilder.where().eq(AddressEntity.ADDRESS_UUID, addressUUID);
        updateBuilder.updateColumnValue(AddressEntity.IS_DEFAULT, true);
        updateBuilder.update();
    }



    /**
     * Set IS_DEFAULT to true for given address Id and IS_DEFAULT to false for others
     * @param addressId
     * @throws SQLException
     */
    public void setDefaultAddress(int addressId)throws  SQLException{
        UpdateBuilder<AddressEntity,Integer> updateBuilder = addressDao.updateBuilder();
        updateBuilder.updateColumnValue(AddressEntity.IS_DEFAULT, false);
        updateBuilder.update();

        updateBuilder.where().eq(AddressEntity.ADDRESS_ID, addressId);
        updateBuilder.updateColumnValue(AddressEntity.IS_DEFAULT, true);
        updateBuilder.update();
    }

    /**
     * Get UnSynced AddressEntity List
     * @return
     * @throws SQLException
     */
    public List<AddressEntity> getUnSyncedAddress()throws SQLException{
        return addressDao.queryBuilder().where().eq(AddressEntity.IS_SYNCED,false).query();
    }


    /**
     * Create New Address Entity
     * @param addressEntity
     * @throws SQLException
     */
    public void saveAddress(AddressEntity addressEntity)throws SQLException{
        addressDao.create(addressEntity);
    }

    /**
     * Update Address Entity
     * @param addressEntity
     * @throws SQLException
     */
    public void updateAddress(AddressEntity addressEntity)throws SQLException{
        addressDao.update(addressEntity);
    }


    /**
     * Returns Most Recent AddressEntity
     * @return
     * @throws SQLException
     */
    @Override
    public AddressEntity getMostRecentAddress()throws SQLException{
       return   addressDao.queryBuilder().orderBy(AddressEntity.LAST_MODIFIED_TIME,false).queryForFirst();
    }

    @Override
    public UserEntity getUser(String mobileNumber)throws SQLException{
        return  userDao.queryBuilder().where().eq(UserEntity.MOBILE_NUMBER,mobileNumber).queryForFirst();
    }


}
