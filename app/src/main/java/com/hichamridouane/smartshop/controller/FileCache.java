package com.hichamridouane.smartshop.controller;

/**
 * Created by Hicham Ridouane on 05/07/2014.
 */

import java.io.File;
import android.content.Context;


public class FileCache {
    private File cacheDir;

    //constructeur pour vérifier la présence du répertoire de cache, si le répertoire n'existe pas le créer.

    public FileCache(Context context) {

        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            cacheDir = new File(
                    android.os.Environment.getExternalStorageDirectory(),
                    "SmartShopCache");
        else
            cacheDir = context.getCacheDir();
        if (!cacheDir.exists())
            cacheDir.mkdirs();
    }

    public File getFile(String url) {
        String filename = String.valueOf(url.hashCode());
        // String filename = URLEncoder.encode(url);
        File f = new File(cacheDir, filename);
        return f;

    }

    public void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null)
            return;
        for (File f : files)
            f.delete();
    }

}
