package co.in.mobilepay.Sync;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import co.in.mobilepay.dao.MerchantDao;
import co.in.mobilepay.dao.PurchaseDao;
import co.in.mobilepay.dao.UserDao;
import co.in.mobilepay.entity.MerchantEntity;
import co.in.mobilepay.entity.PurchaseEntity;
import co.in.mobilepay.entity.UserEntity;
import co.in.mobilepay.json.response.CardDetailsJson;
import co.in.mobilepay.json.response.MerchantJson;
import co.in.mobilepay.json.response.PurchaseJson;
import co.in.mobilepay.json.response.ResponseData;
import co.in.mobilepay.service.ServiceUtil;

/**
 * Created by Nithish on 23-01-2016.
 */
public class AccountSync {

    private MobilePayAPI mobilePayAPI = null;

    private PurchaseDao purchaseDao;
    private MerchantDao merchantDao;
    private UserDao userDao;
    private Gson gson;

    private void processResponse(ResponseData responseData){
        try{
            int statusCode = responseData.getStatusCode();
            String data = responseData.getData();
         List<PurchaseJson> purchaseJsonList =   gson.fromJson(data, new TypeToken<List<PurchaseJson>>() {
            }.getType());
            for(PurchaseJson purchaseJson : purchaseJsonList){
                processPurchaseJson(purchaseJson);
            }
        }catch (Exception e){

        }
    }


    private void processCardResponse(ResponseData responseData){
        try{

            int statusCode = responseData.getStatusCode();
            String response = responseData.getData();
            String cardData =  ServiceUtil.netDecryption(response);
            List<CardDetailsJson> cardDetailsJsons =   gson.fromJson(cardData, new TypeToken<List<CardDetailsJson>>() {
         }.getType());
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    private void processPurchaseJson(PurchaseJson purchaseJson){
        try{
            PurchaseEntity purchaseEntity = purchaseDao.getPurchaseEntity(purchaseJson.getPurchaseId());
            if(purchaseEntity == null){
                UserEntity userEntity =  getUserEntity();
                MerchantEntity merchantEntity =   getMerchantEntity(purchaseJson);
                purchaseEntity = new PurchaseEntity(purchaseJson);
                purchaseEntity.setMerchantEntity(merchantEntity);
                purchaseEntity.setUserEntity(userEntity);
                purchaseDao.createPurchase(purchaseEntity);
            }else{
                if(purchaseEntity.getLastModifiedDateTime() < purchaseJson.getLastModifiedDateTime()){
                    purchaseDao.updatePurchase(purchaseEntity);
                }
            }
        }catch (Exception e){

        }

    }

    private UserEntity getUserEntity(){
        try{
            return userDao.getUser();
        }catch (Exception e){

        }
        return null;
    }

    private MerchantEntity getMerchantEntity(PurchaseJson purchaseJson){
        MerchantEntity merchantEntity = null;
        try {
            MerchantJson merchantJson = purchaseJson.getMerchants();
            if(merchantJson != null){
                merchantEntity =  merchantDao.getMerchant(merchantJson.getMerchantUuid());
                if(merchantEntity == null){
                    merchantEntity = new MerchantEntity(merchantJson);
                    merchantDao.createMerchant(merchantEntity);
                }else{
                    if(merchantEntity.getLastModifiedDateTime() < merchantJson.getLastModifiedDateTime()){
                        merchantEntity.toClone(merchantJson);
                        merchantDao.updateMerchant(merchantEntity);
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return merchantEntity;
    }

}
