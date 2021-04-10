package com.example.movingcircle;

import android.content.res.AssetManager;
import android.content.res.Resources;

public class Circle {

    private float radius;
    private float[] pos;

    private GraphicalModel model;
    private float[] color;

    final static String vertexShaderPath = "vertexShader.glsl",
            fragmentShaderPath = "fragmentShader.glsl";

    public Circle(float posX, float posY, float posZ, float radius, float[] color, AssetManager assetManager) {
        this.color = color;
        this.radius = radius;
        this.pos = new float[]{posX, posY, posZ, 1.0f};
        this.model = new GraphicalModel(
                ShapesTools.getCircleCoords(this.pos, radius, 40),
                vertexShaderPath,
                fragmentShaderPath,
                this.color,
                assetManager
        );
    }

    public void initGraphics() {
        this.model.init();
    }

    public void draw(float[] mvpMatrix) {
        this.model.draw(mvpMatrix, this.pos);
    }


}
