package com.example.movingcircle;

import android.content.res.AssetManager;
import android.opengl.GLES31;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.FloatBuffer;

public class GraphicalModel {

    private FloatBuffer vertexBuffer;
    private int vertexCount;
    private float[] color;

    private Shader shader;

    private float[] translationMatrix;
    private float[] identityMatrix;

    public GraphicalModel(FloatBuffer vertexBuffer,
                          String vertexShaderPath,
                          String fragmentShaderPath,
                          float[] color,
                          AssetManager assetManager) {
        this.vertexBuffer = vertexBuffer;
        this.vertexCount = this.vertexBuffer.capacity() / 3;
        this.shader = new Shader(vertexShaderPath, fragmentShaderPath, assetManager);
        this.color = color;
        this.translationMatrix = new float[16];
        this.identityMatrix = new float[16];
    }

    public FloatBuffer getVertexBuffer() {
        return vertexBuffer;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public float[] getColor() {
        return color;
    }

    public Shader getShader() {
        return shader;
    }

    public void repositionInSpace(float x, float y, float z) {
        Matrix.setIdentityM(this.identityMatrix, 0);
        Matrix.translateM(this.translationMatrix, 0, this.identityMatrix, 0, x, y, z);
    }

    public float[] getTranslationMatrix() {
        return translationMatrix;
    }
}
