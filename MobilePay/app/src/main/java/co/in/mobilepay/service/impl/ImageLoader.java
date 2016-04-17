package co.in.mobilepay.service.impl;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import co.in.mobilepay.R;
import co.in.mobilepay.sync.MobilePayAPI;
import co.in.mobilepay.sync.ServiceAPI;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nithishkumar on 4/12/2016.
 */
//https://dzone.com/articles/android-listview-optimizations-0
public class ImageLoader {

    private LruCache memoryCache;
    private FileCache fileCache;
  // private Map<ImageView,String> imageViews = Collections.synchronizedMap(new WeakHashMap()); -- TODO

    private Drawable mStubDrawable;
    private  Context context;

    public ImageLoader(Context context) {
        this.context = context.getApplicationContext();
        fileCache = new FileCache(context.getApplicationContext());
        init(context.getApplicationContext());
    }


    private void init(Context context) {
        // Get memory class of this device, exceeding this amount will throw an
        // OutOfMemory exception.
        final int memClass = ((ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE)).getMemoryClass();
        // 1/8 of the available mem
        final int cacheSize = 1024 * 1024 * memClass / 8;
        memoryCache = new LruCache(cacheSize);

        mStubDrawable = context.getResources().getDrawable(R.mipmap.luggage_cart); // TODO: 4/14/2016
    }

    public void displayImage(String merchantGuid,String merchantId, ImageView imageView) {
      //  imageViews.put(imageView, url);
        Bitmap bitmap = null;
        if (merchantGuid != null && merchantGuid.length() >  0)
            bitmap = (Bitmap) memoryCache.get(merchantGuid);
        if (bitmap != null) {
            //the image is in the LRU Cache, we can use it directly
            imageView.setImageBitmap(bitmap);
        } else {
            //the image is not in the LRU Cache
            //set a default drawable a search the image
            imageView.setImageDrawable(mStubDrawable);
            if (merchantGuid != null && merchantGuid.length() > 0)
                queuePhoto(merchantGuid,merchantId, imageView);
        }
    }

    /**
     * Search for the image in the device, then in the web
     * @param url
     * @return
     */
    private void queuePhoto( String merchantGuid,String merchantId, ImageView imageView) {
      final   PhotoToLoad   mPhoto = new PhotoToLoad(merchantGuid, imageView);
        //from SD cache
        File f = fileCache.getFile(merchantGuid);
        if (f.exists()) {
            Bitmap bitmap  = decodeFile(f);
            if (bitmap != null){
                memoryCache.put(mPhoto.url, bitmap);

             //   memoryCache.put(mPhoto.url, bitmap);

                // TransitionDrawable let you to make a crossfade animation between 2 drawables
                // It increase the sensation of smoothness
                TransitionDrawable td = null;
                if (bitmap != null) {
                    Drawable[] drawables = new Drawable[2];
                    drawables[0] = mStubDrawable;
                    drawables[1] = new BitmapDrawable(imageView.getResources(),bitmap);
                    td = new TransitionDrawable(drawables);
                    td.setCrossFadeEnabled(true); //important if you have transparent bitmaps
                }

                if (td != null) {
                    // bitmap found, display it !
                    mPhoto.imageView.setImageDrawable(td);
                    mPhoto.imageView.setVisibility(View.VISIBLE);

                    //a little crossfade
                    td.startTransition(200);
                } else {
                    //bitmap not found, display the default drawable
                    mPhoto.imageView.setImageDrawable(mStubDrawable);
                }
                return;
            }

        }

      Call<ResponseBody> responseCall = ServiceAPI.INSTANCE.getMobilePayAPI().getImage(merchantGuid,merchantId);
        responseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    File file = fileCache.getFile(mPhoto.url);
                    FileOutputStream fileOutputStream = null;
                    try {
                        fileOutputStream = new FileOutputStream(file);
                        fileOutputStream.write(response.body().bytes());
                        fileOutputStream.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            fileOutputStream.close();
                        } catch (Exception e) {

                        }

                    }
                    Bitmap bitmap = decodeFile(file);
                    memoryCache.put(mPhoto.url, bitmap);

                    // TransitionDrawable let you to make a crossfade animation between 2 drawables
                    // It increase the sensation of smoothness
                    TransitionDrawable td = null;
                    if (bitmap != null) {
                        Drawable[] drawables = new Drawable[2];
                        drawables[0] = mStubDrawable;
                        drawables[1] = new BitmapDrawable(context.getResources(),bitmap);
                        td = new TransitionDrawable(drawables);
                        td.setCrossFadeEnabled(true); //important if you have transparent bitmaps
                    }

                    if (td != null) {
                        // bitmap found, display it !
                        mPhoto.imageView.setImageDrawable(td);
                        mPhoto.imageView.setVisibility(View.VISIBLE);

                        //a little crossfade
                        td.startTransition(200);
                    } else {
                        //bitmap not found, display the default drawable
                        mPhoto.imageView.setImageDrawable(mStubDrawable);
                    }


                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
       // new LoadBitmapTask().execute(url, imageView);
    }






    //decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f) {
        Bitmap ret = null;
        try {
            FileInputStream is = new FileInputStream(f);
            ret = BitmapFactory.decodeStream(is, null, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }



    private class PhotoToLoad {
        public String url;
        public ImageView imageView;

        public PhotoToLoad(String u, ImageView i) {
            url = u;
            imageView = i;
        }
    }


 /*   private boolean imageViewReused(PhotoToLoad photoToLoad) {
        //tag used here
        String tag = imageViews.get(photoToLoad.imageView);
        if (tag == null || !tag.equals(photoToLoad.url))
            return true;
        return false;
    }*/



}
