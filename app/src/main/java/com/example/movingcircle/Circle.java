package com.example.movingcircle;

import android.content.res.AssetManager;
import android.opengl.Matrix;


public class Circle {

    static final float[] circleColor = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };
    private float radius;
    private float[] pos;
    private float[] velocity;
    private float[] acceleration;
    private GraphicalModel model;

    public Circle(float posX, float posY, float posZ, float radius, AssetManager assetManager) {
        this.radius = radius;
        this.pos = new float[]{posX, posY, posZ, 1.0f};
        this.model = new GraphicalModel(
                ShapesTool.getCircleCoords(this.pos, radius, 40),
                "vertexShader.glsl",
                "fragmentShader.glsl",
                circleColor,
                assetManager
        );
        this.velocity = new float[]{0.0f, 0.0f, 0.0f};
        this.acceleration = new float[]{0.0f, 0.0f, 0.0f};
    }

    public GraphicalModel getModel() {
        return model;
    }

    public void update() {
        this.velocity[0] += this.acceleration[0];
        this.velocity[1] += this.acceleration[1];
        this.velocity[2] += this.acceleration[2];

        this.acceleration[0] = 0.0f;
        this.acceleration[1] = 0.0f;
        this.acceleration[2] = 0.0f;

        this.pos[0] += this.velocity[0];
        this.pos[1] += this.velocity[1];
        this.pos[2] += this.velocity[2];

        this.model.repositionInSpace(this.pos[0], this.pos[1], this.pos[2]);
    }

    public void applyForce(float[] force) {
        this.acceleration[0] += force[0];
        this.acceleration[1] += force[1];
        this.acceleration[2] += force[2];
    }
}
