/*
package co.in.mobilepay.service.impl;

import android.content.Context;

import java.io.File;

*/
/**
 * Created by Nithishkumar on 4/12/2016.
 *//*

public class FileCache {

    private File cacheDir;

    public FileCache(Context context) {
        //Find the dir to save cached images
        cacheDir = context.getCacheDir();
        if (!cacheDir.exists())
            cacheDir.mkdirs();
    }



    public File getFile(String url) {
        return new File(cacheDir, String.valueOf(url.hashCode()));
    }

    public void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null)
            return;
        for (File f : files)
            f.delete();
    }
}
*/
