package co.in.mobilepay.service.impl;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.in.mobilepay.dao.PurchaseDao;
import co.in.mobilepay.dao.impl.PurchaseDaoImpl;
import co.in.mobilepay.entity.PurchaseEntity;
import co.in.mobilepay.service.PurchaseService;
import co.in.mobilepay.view.model.ProductDetailsModel;
import co.in.mobilepay.view.model.PurchaseDetailsModel;
import co.in.mobilepay.view.model.PurchaseModel;

/**
 * Created by Nithish on 30-01-2016.
 */
public class PurchaseServiceImpl implements PurchaseService{

    private PurchaseDao purchaseDao;
    private Context context;
    private Gson gson;

    public PurchaseServiceImpl(Context context){
        this.context = context;
    }

    private void init()throws SQLException{
        purchaseDao = new PurchaseDaoImpl(context);
        gson = new Gson();
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
}
