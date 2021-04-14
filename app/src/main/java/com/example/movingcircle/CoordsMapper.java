package com.example.movingcircle;

import android.opengl.Matrix;

public class CoordsMapper {
    public static float[] calculateCursorRay(float cursorX, float cursorY, float displayWidth, float displayHeight, float[] projectionMatrix, float[] viewMatrix) {
        float[] normalizedCoords = getNormalizedDeviceCoords(cursorX, cursorY, displayWidth, displayHeight);
        float[] clipCoords = new float[] {
                normalizedCoords[0],
                normalizedCoords[1],
                -1f,
                1f
        };
        float[] eyeCoords = toEyeCoords(clipCoords, projectionMatrix);
        return toWorldCoords(eyeCoords, viewMatrix);
    }

    private static float[] toWorldCoords(float[] eyeCoords, float[] viewMatrix) {
        float[] invertedView = new float[viewMatrix.length];
        Matrix.invertM(invertedView, 0, viewMatrix, 0);
        float[] rayWorld = new float[4];
        Matrix.multiplyMV(rayWorld, 0, invertedView, 0, eyeCoords, 0);
        return new float[]{rayWorld[0], rayWorld[1], rayWorld[2]};
    }

    private static float[] toEyeCoords(float[] clipCoords, float[] projectionMatrix) {
        float[] invertedProjection = new float[projectionMatrix.length];
        Matrix.invertM(invertedProjection, 0, projectionMatrix, 0);
        float[] eyeCoords = new float[4];
        Matrix.multiplyMV(eyeCoords, 0, invertedProjection, 0, clipCoords, 0);
        return new float[]{eyeCoords[0], eyeCoords[1], -1f, 0.0f};
    }

    private static float[] getNormalizedDeviceCoords(float cursorX, float cursorY, float displayWidth, float displayHeight) {
        float[] normalizedVec = new float[]{
                ((cursorX / displayWidth) * 2 - 1),
                -((cursorY / displayHeight) * 2 - 1)
        };
        return normalizedVec;
    }
}
