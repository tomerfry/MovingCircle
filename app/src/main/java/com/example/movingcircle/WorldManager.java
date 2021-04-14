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
            if(currTime - prevTime > 10) {
                prevTime = currTime;
                this.circle.update();
            }
        }
    }

    public Circle getCircle() {
        return this.circle;
    }

    public void applyTouch(float x, float y, float displayWidth, float displayHeight) {
        Log.i("AAAAA", String.format("applyTouch: %f, %f", x, y));
        this.circle.followCursor(x, y, displayWidth, displayHeight);
    }
}
