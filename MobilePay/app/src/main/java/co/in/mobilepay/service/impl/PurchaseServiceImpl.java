package co.in.mobilepay.service.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.in.mobilepay.dao.PurchaseDao;
import co.in.mobilepay.dao.UserDao;
import co.in.mobilepay.dao.impl.PurchaseDaoImpl;
import co.in.mobilepay.dao.impl.UserDaoImpl;
import co.in.mobilepay.entity.AddressEntity;
import co.in.mobilepay.entity.DiscardEntity;
import co.in.mobilepay.entity.PurchaseEntity;
import co.in.mobilepay.entity.TransactionalDetailsEntity;
import co.in.mobilepay.entity.UserEntity;
import co.in.mobilepay.enumeration.DiscardBy;
import co.in.mobilepay.enumeration.OrderStatus;
import co.in.mobilepay.service.PurchaseService;
import co.in.mobilepay.service.ServiceUtil;
import co.in.mobilepay.view.model.ProductDetailsModel;
import co.in.mobilepay.view.model.PurchaseDetailsModel;
import co.in.mobilepay.view.model.PurchaseModel;

/**
 * Created by Nithish on 30-01-2016.
 */
public class PurchaseServiceImpl extends BaseService implements PurchaseService{

    private PurchaseDao purchaseDao;
    private UserDao userDao;
    private Context context;

    public PurchaseServiceImpl(Context context){
        super();
        this.context = context;
        try{
            init();
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    private void init()throws SQLException{
        purchaseDao = new PurchaseDaoImpl(context);
        gson = new Gson();
        userDao = new UserDaoImpl(context);
    }


    @Override
    public List<PurchaseModel> getCurrentPurchase(){
        List<PurchaseModel> purchaseModelList = new ArrayList<>();
        try {
            List<PurchaseEntity> purchaseList =   purchaseDao.getPurchaseList();
            for (PurchaseEntity purchaseEntity : purchaseList){
                PurchaseModel purchaseModel = new PurchaseModel(purchaseEntity);
                purchaseModelList.add(purchaseModel);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return purchaseModelList;
    }


    @Override
    public List<PurchaseModel> getOrderStatusList(){
        List<PurchaseModel> purchaseModelList = new ArrayList<>();
        try {
            List<PurchaseEntity> purchaseList =   purchaseDao.getOrderStatusList();
            for (PurchaseEntity purchaseEntity : purchaseList){
                PurchaseModel purchaseModel = new PurchaseModel(purchaseEntity);
                purchaseModelList.add(purchaseModel);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return purchaseModelList;
    }

    /**
     * Returns Purchase History (ISPAYED and CANCELED,DELIVERED)
     * @return
     */
    @Override
    public List<PurchaseModel> getPurchaseHistoryList(){
        List<PurchaseModel> purchaseModelList = new ArrayList<>();
        try{
            List<PurchaseEntity> purchaseList =   purchaseDao.getPurchaseHistoryList();
            for (PurchaseEntity purchaseEntity : purchaseList){
                PurchaseModel purchaseModel = new PurchaseModel(purchaseEntity);
                purchaseModelList.add(purchaseModel);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return purchaseModelList;
    }

    @Override
    public PurchaseEntity getPurchaseDetails(int purchaseId){
        try{
            PurchaseEntity purchaseEntity =  purchaseDao.getPurchaseEntity(purchaseId);
            return purchaseEntity;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PurchaseDetailsModel getProductDetails(int purchaseId){
        try{
            PurchaseEntity purchaseEntity =  purchaseDao.getPurchaseEntity(purchaseId);
            PurchaseDetailsModel purchaseDetailsModel = new PurchaseDetailsModel(purchaseEntity);
            String productDetails = purchaseEntity.getProductDetails();
            List<ProductDetailsModel> productDetailsModelList = gson.fromJson(productDetails, new TypeToken<List<ProductDetailsModel>>() {
            }.getType());
            purchaseDetailsModel.getProductDetailsModelList().addAll(productDetailsModelList);
            String amountDetails = purchaseEntity.getAmountDetails();
            return purchaseDetailsModel;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }




    @Override
    public void updatePurchaseData(PurchaseEntity purchaseEntity,List<ProductDetailsModel> productDetailsModelList){
        try{
            purchaseEntity.setProductDetails(gson.toJson(productDetailsModelList));
            purchaseEntity.setAmountDetails("");
            purchaseEntity.setIsSync(false);
            purchaseEntity.setLastModifiedDateTime(ServiceUtil.getCurrentTimeMilli());
            purchaseDao.updatePurchase(purchaseEntity);
        }catch (Exception e){
            Log.e("Error", "Error in updatePurchaseData", e);
        }

    }


    @Override
    public void declinePurchase(PurchaseEntity purchaseEntity,String reason){
        try {
            purchaseEntity.setIsDiscard(true);
            purchaseEntity.setOrderStatus(OrderStatus.CANCELED);
            purchaseEntity.setIsSync(false);
            purchaseEntity.setLastModifiedDateTime(ServiceUtil.getCurrentTimeMilli());
            purchaseDao.updatePurchase(purchaseEntity);
            DiscardEntity discardEntity = new DiscardEntity();
            discardEntity.setCreatedDateTime(purchaseEntity.getLastModifiedDateTime());
            discardEntity.setDiscardBy(DiscardBy.USER);
            discardEntity.setPurchaseEntity(purchaseEntity);
            discardEntity.setReason(reason);
            purchaseDao.createDiscardEntity(discardEntity);
        }catch (Exception e){
            e.printStackTrace();
            Log.e("Error","Error in declinePurchase",e);
        }

    }

    public void updatePurchaseEntity(PurchaseEntity purchaseEntity){
        try{
            purchaseEntity.setLastModifiedDateTime(ServiceUtil.getCurrentTimeMilli());
            purchaseDao.updatePurchase(purchaseEntity);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
   public void makePayment(PurchaseEntity purchaseEntity,AddressEntity addressEntity){
       try {
           purchaseEntity.setIsSync(false);
           purchaseEntity.setOrderStatus(OrderStatus.PACKING);
           purchaseEntity.setLastModifiedDateTime(ServiceUtil.getCurrentTimeMilli());
           purchaseEntity.setAddressEntity(addressEntity);
           purchaseDao.updatePurchase(purchaseEntity);
       }catch (Exception e){
           e.printStackTrace();
           Log.e("Error","Error in makePayment",e);
       }

   }

    /**
     * Returns Discard Entity
     * @param purchaseEntity
     * @return
     */
    @Override
    public DiscardEntity getDiscardEntity(PurchaseEntity purchaseEntity){
        try {
            DiscardEntity discardEntity = purchaseDao.getDiscardEntity(purchaseEntity);
            return discardEntity;
        }catch (Exception e){
            e.printStackTrace();
            Log.e("Error", "Error in getDiscardEntity", e);
        }
        return null;
    }

    public UserEntity getUserEntity(){
        try{
            return userDao.getUser();
        }catch (Exception e){
            Log.e("Error","Error in getUserEntity",e);
        }
        return null;
    }


    /**
     * Get Default Address(Previous Choosed Address), Otherwise most recent updated or created address.
     * @return AddressEntity or null
     */
    @Override
    public AddressEntity getDefaultAddress(){
        try{
            // Get Default address
            AddressEntity addressEntity =  userDao.getDefaultAddress();
            // If Default address is not found, then most recent updated or created address
            if(addressEntity == null){
                addressEntity = userDao.getMostRecentAddress();
            }

            return addressEntity;
        }catch (Exception e){
            Log.e("Error","Error in getUserEntity",e);
        }
        return null;
    }


    /**
     * Returns List of Delivery Address
     * @return
     */
    @Override
    public List<AddressEntity> getAddressList(){
        try{
            return userDao.getAddressEntityList();
        }catch (Exception e){
            Log.e("Error","Error in getAddressList",e);
        }
        return new ArrayList<>();
    }


    /**
     * Create
     * @param addressEntity
     */
    @Override
    public void saveAddress(AddressEntity addressEntity){
        try {
            userDao.saveAddress(addressEntity);
            userDao.setDefaultAddress(addressEntity.getAddressId());
        }catch (Exception e){
            Log.e("Error","Error in saveAddress",e);
        }

    }

    /**
     * set Default address as currently choose address
     * @param addressId
     */
    public void updateDefaultAddress(int addressId){
        try {
            userDao.setDefaultAddress(addressId);
        }catch (Exception e){
            Log.e("Error","Error in updateDefaultAddress",e);
        }
    }

    /**
     * Update
     * @param addressEntity
     */
    @Override
    public void updateAddress(AddressEntity addressEntity){
        try {
            userDao.updateAddress(addressEntity);
            userDao.setDefaultAddress(addressEntity.getAddressId());
        }catch (Exception e){
            Log.e("Error","Error in saveAddress",e);
        }

    }

    /**
     * Get Address for an given id
     * @param addressId
     * @return
     */
    @Override
    public AddressEntity getAddress(int addressId){
        try {
            return  userDao.getAddressEntity(addressId);
        }catch (Exception e){
            Log.e("Error","Error in getAddress",e);
        }
        return null;
    }

    /**
     * Create Transactions
     * @param transactionalDetailsEntity
     */
    @Override
    public void createTransactionDetails(TransactionalDetailsEntity transactionalDetailsEntity){
        try {
            purchaseDao.createTransactionalDetails(transactionalDetailsEntity);
        }catch (Exception e){
            Log.e("Error","Error in createTransactionDetails",e);
        }
    }


    @Override
    public List<TransactionalDetailsEntity> getTransactionalDetails(PurchaseEntity purchaseEntity){
        try {
            return purchaseDao.getTransactionalDetails(purchaseEntity);
        }catch (Exception e){
            Log.e("Error","Error in getTransactionalDetails",e);
        }
        return new ArrayList<>();
    }


}
