package co.in.mobilepay.dao;

import java.sql.SQLException;
import java.util.List;

import co.in.mobilepay.entity.CounterDetailsEntity;
import co.in.mobilepay.entity.DiscardEntity;
import co.in.mobilepay.entity.MerchantEntity;
import co.in.mobilepay.entity.PurchaseEntity;
import co.in.mobilepay.entity.TransactionalDetailsEntity;
import co.in.mobilepay.json.response.PurchaseJson;

/**
 * Created by Nithish on 24-01-2016.
 */
public interface PurchaseDao {

    void createPurchase(PurchaseEntity purchaseEntity)throws SQLException;

    void updatePurchase(PurchaseEntity purchaseEntity)throws SQLException;

    List<PurchaseEntity> getPurchaseList()throws SQLException;

    List<PurchaseEntity> getPurchaseHistoryList()throws SQLException;


    List<String> getPurchaseUUIDs() throws SQLException;

    PurchaseEntity getPurchaseEntity(int purchaseId)throws SQLException;

    PurchaseEntity getPurchaseEntity(String purchaseId)throws SQLException;


    MerchantEntity getMerchantEntity(String merchantGuid)throws SQLException;

    void createMerchantEntity(MerchantEntity merchantEntity)throws SQLException;

    void updateMerchantEntity(MerchantEntity merchantEntity)throws SQLException;

    long getLeastLuggageServerTime()throws SQLException;

    long getMostRecentLuggageServerTime()throws SQLException;

    List<String> getPurchaseHistoryUUIDs()throws SQLException;

    void createDiscardEntity(DiscardEntity discardEntity)throws SQLException;

    List<PurchaseEntity> getUnSyncedDiscardEntity()throws SQLException;

    DiscardEntity getDiscardEntity(PurchaseEntity purchaseEntity)throws SQLException;

    void updateServerSyncTime(List<PurchaseJson> purchaseJsonList)throws SQLException;

    List<PurchaseEntity> getUnSyncedPayedEntity()throws SQLException;

    List<PurchaseEntity> getOrderStatusList()throws SQLException;

    void createTransactionalDetails(TransactionalDetailsEntity transactionalDetailsEntity)throws SQLException;

    List<TransactionalDetailsEntity> getTransactionalDetails(PurchaseEntity purchaseEntity)throws SQLException;

    List<String> getPurchaseUUIDs(List<String> purchaseUUIDS)throws SQLException;

    CounterDetailsEntity getCounterDetailsEntity(PurchaseEntity purchaseEntity)throws SQLException;

    void createCounterDetails(CounterDetailsEntity counterDetailsEntity)throws SQLException;

    void updateCounterDetails(CounterDetailsEntity counterDetailsEntity)throws SQLException;
}
