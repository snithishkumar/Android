package co.in.mobilepay.view.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import co.in.mobilepay.R;
import co.in.mobilepay.entity.UserEntity;
import co.in.mobilepay.service.AccountService;
import co.in.mobilepay.view.activities.ActivityUtil;
import co.in.mobilepay.view.activities.NaviDrawerActivity;

/**
 * Created by Nithish on 02/01/17.
 */

public class QrCodeFragment extends Fragment {

    public final static int QRcodeWidth = 750 ;
    private NaviDrawerActivity naviDrawerActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr_code_generator, container, false);
        ImageView qrCode = (ImageView)view.findViewById(R.id.qr_code);
        TextView mobileNumber = (TextView)view.findViewById(R.id.qr_code_mobileNumber);

        UserEntity userEntity = naviDrawerActivity.getAccountService().getUserDetails();
        try{
            Bitmap bitmap = textToImageEncode(userEntity.getMobileNumber());
            qrCode.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }

        mobileNumber.setText(userEntity.getMobileNumber());
        initBackButton();
        return view;

    }

    private void initBackButton(){
        final Drawable upArrow = ContextCompat.getDrawable(naviDrawerActivity,R.drawable.ic_arrow_back_white_24dp);
        //upArrow.setColorFilter( ContextCompat.getColor(naviDrawerActivity,R.color.white), PorterDuff.Mode.SRC_ATOP);
        naviDrawerActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        naviDrawerActivity.getSupportActionBar().setHomeButtonEnabled(true);
        naviDrawerActivity.getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }



    Bitmap textToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        Color.BLACK:getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 750, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.naviDrawerActivity = (NaviDrawerActivity)context;
    }

}
