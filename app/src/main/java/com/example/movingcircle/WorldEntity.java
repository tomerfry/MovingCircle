package com.example.movingcircle;

import org.joml.Vector3f;

public class WorldEntity {
    protected Vector3f pos, velocity, acceleration;
    protected Model entityModel;

    public WorldEntity(Vector3f pos, Model entityModel) {
        this.pos = pos;
        this.velocity = new Vector3f(0.0f, 0.0f, 0.0f);
        this.acceleration = new Vector3f(0.0f, 0.0f, 0.0f);
        this.entityModel = entityModel;
    }

    public void update() {
        this.velocity.add(acceleration);
        this.pos.add(this.velocity);
    }

    public void draw() {
        this.entityModel.draw();
    }

    public void applyForce(Vector3f force) {
        this.acceleration.add(force);
    }

    public Vector3f getPos() {
        return pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    public Vector3f getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector3f acceleration) {
        this.acceleration = acceleration;
    }

    public Model getEntityModel() {
        return entityModel;
    }

    public void setEntityModel(Model entityModel) {
        this.entityModel = entityModel;
    }
}
