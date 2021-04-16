package com.example.movingcircle;

import android.content.res.AssetManager;
import android.opengl.Matrix;
import android.util.Log;


public class Circle {

    static final float[] circleColor = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };
    private float radius;
    private float[] pos;
    private float[] velocity;
    private float[] acceleration;
    private GraphicalModel model;
    private float leftX;
    private float rightX;
    private float topY;
    private float bottomY;

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

        if(this.pos[0] + this.radius <= this.leftX)
            this.velocity[0] = -this.velocity[0];
        if(this.pos[0] - this.radius >= this.rightX)
            this.velocity[0] = -this.velocity[0];
        if(this.pos[1] - this.radius <= this.topY)
            this.velocity[1] = -this.velocity[1];
        if(this.pos[1] + this.radius >= this.bottomY)
            this.velocity[1] = -this.velocity[1];

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

    public void follow(float[] force) {
        this.acceleration[0] = (force[0] - this.acceleration[0]) / 10;
        this.acceleration[1] = (force[1] - this.acceleration[1]) / 10;
        this.acceleration[2] = (force[2] - this.acceleration[2]) / 10;
    }

    public void setPos(float[] newPos) {
        this.pos = newPos;
    }

    public void setBounds(float leftX, float rightX, float topY, float bottomY) {
        this.leftX = leftX;
        this.rightX = rightX;
        this.topY = topY;
        this.bottomY = bottomY;
    }
}
