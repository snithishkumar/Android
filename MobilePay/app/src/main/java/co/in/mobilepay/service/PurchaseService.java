package co.in.mobilepay.service;

import java.util.List;

import co.in.mobilepay.view.model.PurchaseDetailsModel;
import co.in.mobilepay.view.model.PurchaseModel;

/**
 * Created by Nithish on 30-01-2016.
 */
public interface PurchaseService {

    List<PurchaseModel> getCurrentPurchase();

    PurchaseDetailsModel getProductDetails(int purchaseId);
}
