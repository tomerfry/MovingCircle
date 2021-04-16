package com.example.movingcircle;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

public class ShapesTool {

    public static FloatBuffer getCircleCoords(float[] pos, float radius, int circleResolution) {
        float prevX = pos[0], prevY = pos[1] - radius;
        float newX, newY;
        double angle = Math.PI * 2.0f / circleResolution;

        ArrayList<Float> circleCoords = new ArrayList<>();

        for (int i = 0; i <= circleResolution; i++) {
            newX = radius * (float) (Math.sin(angle * i));
            newY = -radius * (float) (Math.cos(angle * i));

            circleCoords.add(prevX);
            circleCoords.add(prevY);
            circleCoords.add(pos[2]);
            circleCoords.add(1.0f);

            circleCoords.add(newX);
            circleCoords.add(newY);
            circleCoords.add(pos[2]);
            circleCoords.add(1.0f);

            circleCoords.add(pos[0]);
            circleCoords.add(pos[1]);
            circleCoords.add(pos[2]);
            circleCoords.add(1.0f);

            prevX = newX;
            prevY = newY;
        }

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(circleCoords.size() * Float.BYTES);
        byteBuffer.order(ByteOrder.nativeOrder());

        FloatBuffer vertexBuffer = byteBuffer.asFloatBuffer();
        float[] floats = new float[circleCoords.size()];

        int i = 0;
        for (float f:circleCoords) {
            floats[i++] = f;
        }

        vertexBuffer.put(floats);
        vertexBuffer.position(0);

        return vertexBuffer;
    }
}
