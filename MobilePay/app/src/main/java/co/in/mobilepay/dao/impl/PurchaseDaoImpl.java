package co.in.mobilepay.dao.impl;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import co.in.mobilepay.dao.PurchaseDao;
import co.in.mobilepay.entity.PurchaseEntity;

/**
 * Created by Nithish on 24-01-2016.
 */
public class PurchaseDaoImpl extends BaseDaoImpl implements PurchaseDao {

    private Dao<PurchaseEntity,Integer> purchaseDao = null;

    public PurchaseDaoImpl(Context context) throws SQLException {
        super(context);
    }

    /**
     * Initialize all the DAO's
     * @throws SQLException
     */
    @Override
    protected void initDao() throws SQLException {
        purchaseDao = databaseHelper.getDao(PurchaseEntity.class);
    }

    /**
     * Insert a New Record
     * @param purchaseEntity
     * @throws SQLException
     */
    @Override
    public void createPurchase(PurchaseEntity purchaseEntity) throws SQLException {
        purchaseDao.create(purchaseEntity);
    }

    /**
     * Update an Existing record
     * @param purchaseEntity
     * @throws SQLException
     */
    @Override
    public void updatePurchase(PurchaseEntity purchaseEntity) throws SQLException {
        purchaseDao.update(purchaseEntity);
    }

    /**
     * Get List of un payed PurchaseEntity
     * @return List of PurchaseEntity or empty list
     * @throws SQLException
     */
    @Override
    public List<PurchaseEntity> getPurchaseList() throws SQLException {
        return purchaseDao.queryBuilder().where().eq(PurchaseEntity.IS_PAYED,false).query();
    }

    /**
     * Returns last Purchased uuid
     * @return purchase uuid or null
     * @throws SQLException
     */
    @Override
    public String getLastPurchaseId() throws SQLException {
        PurchaseEntity purchaseEntity = purchaseDao.queryBuilder().selectColumns(PurchaseEntity.PURCHASE_GUID).orderBy(PurchaseEntity.PURCHASE_ID,false).queryForFirst();
        return  purchaseEntity != null ? purchaseEntity.getPurchaseGuid() : null;
    }

    @Override
    public PurchaseEntity getPurchaseEntity(int purchaseId) throws SQLException {
        return purchaseDao.queryForId(purchaseId);
    }



    @Override
    public PurchaseEntity getPurchaseEntity(String purchaseId) throws SQLException {
        return purchaseDao.queryBuilder().where().eq(PurchaseEntity.PURCHASE_GUID,purchaseId).queryForFirst();
    }

    /**
     * Get List of  payed PurchaseEntity
     * @return List of PurchaseEntity or empty list
     * @throws SQLException
     */
    @Override
    public List<PurchaseEntity> getPurchaseHistoryList() throws SQLException {
        return purchaseDao.queryBuilder().where().eq(PurchaseEntity.IS_PAYED,true).query();
    }
}
