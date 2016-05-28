/*
package co.in.mobilepay.service.impl;

import android.content.Context;
import android.util.Log;

import co.in.mobilepay.bus.MobilePayBus;
import co.in.mobilepay.dao.UserDao;
import co.in.mobilepay.dao.impl.UserDaoImpl;
import co.in.mobilepay.entity.UserEntity;
import co.in.mobilepay.json.response.CardJson;
import co.in.mobilepay.json.response.LoginError;
import co.in.mobilepay.json.response.ResponseData;
import co.in.mobilepay.json.response.TokenJson;
import co.in.mobilepay.service.CardService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

*/
/**
 * Created by Nithish on 13-03-2016.
 *//*

public class CardServiceImpl extends BaseService implements CardService {

    private UserDao userDao;

    public CardServiceImpl(Context context) {
        super();
        try {
            userDao = new UserDaoImpl(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    */
/**
     * Create New Card
     *
     * @param cardJson
     *//*

    @Override
    public void createCard(CardJson cardJson) {
        try {
            UserEntity userEntity =  userDao.getUser();
            cardJson.setServerToken(userEntity.getServerToken());
            cardJson.setAccessToken(userEntity.getAccessToken());
            Call<ResponseData> responseDataCall = mobilePayAPI.createCard(cardJson);
            CardServiceCallback cardServiceCallback = new CardServiceCallback();
            responseDataCall.enqueue(cardServiceCallback);
        } catch (Exception e) {
            Log.e("Error", "Error in createCard", e);
            cardError();
        }

    }

    */
/**
     * Remove the given card
     *
     * @param cardJson
     *//*

    @Override
    public void removeCard(CardJson cardJson) {
        try {
            UserEntity userEntity =  userDao.getUser();
            cardJson.setServerToken(userEntity.getServerToken());
            cardJson.setAccessToken(userEntity.getAccessToken());
            Call<ResponseData> responseDataCall = mobilePayAPI.removeCard(cardJson);
            CardServiceCallback cardServiceCallback = new CardServiceCallback();
            responseDataCall.enqueue(cardServiceCallback);
        } catch (Exception e) {
            Log.e("Error", "Error in removeCard", e);

            cardError();
        }
    }

    private void cardResponse(ResponseData responseData) {
        if (responseData.getStatusCode() == MessageConstant.LOGIN_INVALID_PIN) {
            LoginError loginError = new LoginError(MessageConstant.LOGIN_INVALID_PIN);
            MobilePayBus.getInstance().post(loginError);
        } else {
            MobilePayBus.getInstance().post(responseData);
        }

    }


    private void cardError() {
        ResponseData responseData = new ResponseData();
        responseData.setStatusCode(MessageConstant.CARD_LIST_FAILURE);
        cardResponse(responseData);
    }

    */
/**
     * Get the List of Card
     *
     *//*

    @Override
    public void getCardList() {
        try {
           UserEntity userEntity =  userDao.getUser();
            TokenJson tokenJson = new TokenJson();
            tokenJson.setServerToken(userEntity.getServerToken());
            tokenJson.setAccessToken(userEntity.getAccessToken());
            Call<ResponseData> responseDataCall = mobilePayAPI.getCardList(tokenJson);
            CardServiceCallback cardServiceCallback = new CardServiceCallback();
            responseDataCall.enqueue(cardServiceCallback);
        } catch (Exception e) {
            Log.e("Error", "Error in getCardList", e);
            cardError();
        }


    }


    private class CardServiceCallback implements Callback<ResponseData> {
        public CardServiceCallback() {
        }

        @Override
        public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
            cardResponse(response.body());
        }

        @Override
        public void onFailure(Call<ResponseData> call, Throwable t) {
            cardError();
        }
    }
}
*/
