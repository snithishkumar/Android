package co.in.mobilepay.dao;

import java.sql.SQLException;

import co.in.mobilepay.entity.MerchantEntity;

/**
 * Created by Nithish on 25-01-2016.
 */
public interface MerchantDao {
    void createMerchant(MerchantEntity merchantEntity)throws SQLException;

    void updateMerchant(MerchantEntity merchantEntity)throws SQLException;

    void isMerchantPresent(String merchantUuid,long lastModifiedTime)throws SQLException;

    MerchantEntity getMerchant(String merchantUuid) throws SQLException;

    MerchantEntity getMerchant(int merchantId)throws SQLException;
}
