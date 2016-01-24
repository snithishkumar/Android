package co.in.mobilepay.dao;

import java.sql.SQLException;
import java.util.List;

import co.in.mobilepay.entity.PurchaseEntity;

/**
 * Created by Nithish on 24-01-2016.
 */
public interface PurchaseDao {

    void createPurchase(PurchaseEntity purchaseEntity)throws SQLException;

    void updatePurchase(PurchaseEntity purchaseEntity)throws SQLException;

    List<PurchaseEntity> getPurchaseList()throws SQLException;

    List<PurchaseEntity> getPurchaseHistoryList()throws SQLException;

    String getLastPurchaseId()throws SQLException;

    PurchaseEntity getPurchaseEntity(int purchaseId)throws SQLException;
}
