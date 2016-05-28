package co.in.mobilepay.service;

import java.util.List;

import co.in.mobilepay.entity.AddressEntity;
import co.in.mobilepay.entity.DiscardEntity;
import co.in.mobilepay.entity.PurchaseEntity;
import co.in.mobilepay.entity.TransactionalDetailsEntity;
import co.in.mobilepay.entity.UserEntity;
import co.in.mobilepay.view.model.ProductDetailsModel;
import co.in.mobilepay.view.model.PurchaseDetailsModel;
import co.in.mobilepay.view.model.PurchaseModel;

/**
 * Created by Nithish on 30-01-2016.
 */
public interface PurchaseService {

    List<PurchaseModel> getCurrentPurchase();

    PurchaseDetailsModel getProductDetails(int purchaseId);


    PurchaseEntity getPurchaseDetails(int purchaseId);

    UserEntity getUserEntity();

    void updatePurchaseData(PurchaseEntity purchaseEntity,List<ProductDetailsModel> productDetailsModelList);

    AddressEntity getDefaultAddress();

    List<AddressEntity> getAddressList();

    void saveAddress(AddressEntity addressEntity);

    void updateAddress(AddressEntity addressEntity);

    AddressEntity getAddress(int addressId);

    void updateDefaultAddress(int addressId);

    void declinePurchase(PurchaseEntity purchaseEntity,String reason);

    List<PurchaseModel> getOrderStatusList();


    List<PurchaseModel> getPurchaseHistoryList();

    void makePayment(PurchaseEntity purchaseEntity,AddressEntity addressEntity);


    DiscardEntity getDiscardEntity(PurchaseEntity purchaseEntity);

    void updatePurchaseEntity(PurchaseEntity purchaseEntity);

    void createTransactionDetails(TransactionalDetailsEntity transactionalDetailsEntity);

    List<TransactionalDetailsEntity> getTransactionalDetails(PurchaseEntity purchaseEntity);

}
