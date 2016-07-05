package co.in.mobilepay.view.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import co.in.mobilepay.R;
import co.in.mobilepay.bus.MobilePayBus;
import co.in.mobilepay.json.response.ResponseData;
import co.in.mobilepay.service.ServiceUtil;
import co.in.mobilepay.service.impl.MessageConstant;
import co.in.mobilepay.view.activities.ActivityUtil;
import co.in.mobilepay.view.activities.MainActivity;

/**
 * Created by Nithish on 29-02-2016.
 */
public class MobileFragment extends Fragment implements View.OnClickListener {

    private TextView mobileNumber = null;
    private String mobile;
    private MainActivity mainActivity = null;
    private Button onSubmit = null;

    private MainActivityCallback mainActivityCallback;

    ProgressDialog progressDialog = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mobile, container, false);
        onSubmit = (Button) view.findViewById(R.id.mobile_submit);
        mobileNumber = (TextView) view.findViewById(R.id.mobile_number);
        mobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 10) {
                    onSubmit.setBackground(ContextCompat.getDrawable(mainActivity, R.drawable.button));
                    onSubmit.setTextColor(ContextCompat.getColor(mainActivity, R.color.white));
                }
            }
        });

        onSubmit.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        mobile = mobileNumber.getText().toString();
        if (mobile == null || mobile.isEmpty()) {
            mobileNumber.setError(getString(R.string.mobile_number_missing_error));
            return;
        }
        if (mobile.length() != 10) {
            mobileNumber.setError(getString(R.string.mobile_number_length_error));
            return;
        }
        boolean isNet = ServiceUtil.isNetworkConnected(mainActivity);
        if (isNet) {
            progressDialog = ActivityUtil.showProgress(getString(R.string.mobile_submit_heading), getString(R.string.mobile_submit_message), mainActivity);
            mainActivity.getAccountService().requestOtp(mobile, mainActivity);
        } else {
            ActivityUtil.showDialog(mainActivity, getString(R.string.no_network_heading), getString(R.string.no_network));
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mainActivity = (MainActivity) context;
        this.mainActivityCallback = mainActivity;
    }


    @Subscribe
    public void getSyncResponse(ResponseData responseData) {
        progressDialog.dismiss();
        if (responseData.getStatusCode() == MessageConstant.MOBILE_VERIFY_OK) {
            mainActivityCallback.success(MessageConstant.MOBILE_VERIFY_OK, mobile);
            return;
        }
        ActivityUtil.showDialog(mainActivity, "Error",getString(R.string.internal_error));
    }


    @Override
    public void onPause() {
        MobilePayBus.getInstance().unregister(this);

        super.onPause();
    }

    @Override
    public void onDestroyView() {
        hideKeyboard();
        super.onDestroyView();
    }

    private void hideKeyboard(){
      ActivityUtil.hideKeyboard(mainActivity);
    }

    @Override
    public void onResume(){
        MobilePayBus.getInstance().register(this);
        super.onResume();
    }

    public interface MainActivityCallback {
        void success(int code, Object data);
    }
}
