package com.example.movingcircle;

import android.content.res.AssetManager;
import android.os.SystemClock;
import android.util.Log;

public class WorldManager {
    private Circle circle;

    public WorldManager(AssetManager assetManager) {
        this.circle = new Circle(0.0f, 0.0f, 0.0f, 0.1f, assetManager);
    }

    public void worldLoop() {
        long prevTime = SystemClock.uptimeMillis();
        long currTime;
        boolean gameRunning = true;

        this.circle.applyForce(new float[]{0.01f, 0.0f, 0.0f});

        while(gameRunning) {
            currTime = SystemClock.uptimeMillis();
            if(currTime - prevTime > 10) {
                prevTime = currTime;
                this.circle.update();
            }
        }
    }

    public Circle getCircle() {
        return circle;
    }
}
