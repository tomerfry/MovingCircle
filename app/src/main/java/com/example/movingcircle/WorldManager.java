package com.example.movingcircle;

import android.util.Log;

import org.joml.Vector3f;

public class WorldManager {

    private Triangle triangle;
    private Circle circle;

    static final float[] circleColor = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };;

    public WorldManager() {
        this.triangle = new Triangle();
        this.circle = new Circle(new Vector3f(0.0f, 0.0f, 0.0f), 0.1f, circleColor);
    }

    public Triangle getTriangle() {
        return this.triangle;
    }

    public Circle getCircle() {
        return this.circle;
    }
}
