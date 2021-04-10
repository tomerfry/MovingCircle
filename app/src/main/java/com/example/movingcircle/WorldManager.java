package com.example.movingcircle;

import android.content.res.AssetManager;
import android.os.SystemClock;
import android.util.Log;

public class WorldManager {
    private Circle circle;

    static final float[] circleColor = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

    public WorldManager(AssetManager assetManager) {
        this.circle = new Circle(0.0f, 0.0f, 0.0f, 0.1f, circleColor, assetManager);
    }

    public void worldLoop() {
        long prevTime = SystemClock.uptimeMillis();
        long currTime;
        boolean gameRunning = true;

        while(gameRunning) {
            currTime = SystemClock.uptimeMillis();
            if(currTime - prevTime > 100) {
                prevTime = currTime;

                Log.i("AAAAAA", "worldLoop:");
            }
        }
    }

    public Circle getCircle() {
        return this.circle;
    }
}
