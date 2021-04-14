package com.example.movingcircle;

import android.content.res.AssetManager;


public class Circle {

    private float radius;
    private float[] pos;
    private float[] velocity;
    private float[] acceleration;

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
        this.velocity = new float[]{0.0f, 0.0f, 0.0f};
        this.acceleration = new float[]{0.0f, 0.0f, 0.0f};
    }

    public void initGraphics(float[] projectionMatrix) {
        this.model.init(projectionMatrix);
    }

    public void draw() {
        this.model.draw(this.pos);
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
    }

    public void applyForce(float[] force) {
        this.acceleration[0] += force[0];
        this.acceleration[1] += force[1];
        this.acceleration[2] += force[2];
    }

    public void followCursor(float x, float y, float width, float height) {
        float[] ray = CoordsMapper.calculateCursorRay(x, y, width, height, this.model.getProjectionMatrix(), this.model.getViewMatrix());

        this.pos[0] = ray[0];
        this.pos[1] = ray[1];
        this.pos[2] = ray[2];
    }
}
