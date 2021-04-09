package com.example.movingcircle;

import org.joml.Vector3f;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

public class CircleEntity extends WorldEntity {

    private float radius;
    static final int steps = 40;
    static final float angle = (float) Math.PI * 2.0f / steps;

    public CircleEntity(Vector3f pos, float radius, float[] color) {
        super(pos, null);

        this.radius = radius;

        Vector3f prev = new Vector3f(pos.x, pos.y, pos.z);
        Vector3f newV = new Vector3f();

        ArrayList<Vector3f> verticesCoords = new ArrayList<>();

        for(int i = 0; i <= steps; i++) {
            newV.x = radius * (float) (Math.sin((double) angle * i));
            newV.y = -radius * (float) (Math.cos((double) angle * i));

            verticesCoords.add(prev);
            verticesCoords.add(newV);
            verticesCoords.add(pos);

            prev.x = newV.x;
            prev.y = newV.y;
            prev.z = newV.z;
        }

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(verticesCoords.size() * 3);
        FloatBuffer vertices = byteBuffer.asFloatBuffer();
        int offset = 0;
        for (Vector3f vertex:verticesCoords) {
            vertex.get(offset, vertices);
            offset += 3;
        }

        Model circleModel = new Model(vertices, null, color);
        this.setEntityModel(circleModel);
    }
}
