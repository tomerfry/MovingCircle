package com.example.movingcircle;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

public class AssetsTools {

    public static String readAsset(String assetName, AssetManager assetManager) {
        try {
            InputStream stream = assetManager.open(assetName);
            byte[] buffer = new byte[stream.available()];
            int readlen = stream.read(buffer);
            stream.close();
            return new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
