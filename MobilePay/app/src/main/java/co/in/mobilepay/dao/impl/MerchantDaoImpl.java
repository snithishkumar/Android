package co.in.mobilepay.dao.impl;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import co.in.mobilepay.dao.MerchantDao;
import co.in.mobilepay.entity.MerchantEntity;

/**
 * Created by Nithish on 25-01-2016.
 */
public class MerchantDaoImpl extends BaseDaoImpl implements MerchantDao {

    Dao<MerchantEntity,Integer> merchantDao = null;

    public MerchantDaoImpl(Context context) throws SQLException {
        super(context);
    }

    @Override
    protected void initDao() throws SQLException {
        merchantDao = databaseHelper.getDao(MerchantEntity.class);
    }

    @Override
    public void createMerchant(MerchantEntity merchantEntity) throws SQLException {
        merchantDao.create(merchantEntity);
    }

    @Override
    public void updateMerchant(MerchantEntity merchantEntity) throws SQLException {
        merchantDao.update(merchantEntity);
    }

    @Override
    public void isMerchantPresent(String merchantUuid, long lastModifiedTime) throws SQLException {

    }

    @Override
    public MerchantEntity getMerchant(String merchantUuid) throws SQLException {
        return null;
    }

    @Override
    public MerchantEntity getMerchant(int merchantId) throws SQLException {
        return null;
    }
}
