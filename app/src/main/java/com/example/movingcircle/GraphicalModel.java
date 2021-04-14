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

    static final int COORDS_PER_VERTEX = 3;
    private final int vertexStride = COORDS_PER_VERTEX * Float.BYTES;

    private int mProgram;
    private int posHandle;
    private int vPMatrixHandle;
    private int modelPosHandle;
    private int colorHandle;
    private float[] viewMatrix;
    private float[] projectionMatrix;
    private float[] mvpMatrix;

    public GraphicalModel(FloatBuffer vertexBuffer,
                          String vertexShaderPath,
                          String fragmentShaderPath,
                          float[] color,
                          AssetManager assetManager) {
        this.vertexBuffer = vertexBuffer;
        this.vertexCount = this.vertexBuffer.capacity() / 3;
        this.shader = new Shader(vertexShaderPath, fragmentShaderPath, assetManager);
        this.color = color;

        this.viewMatrix = new float[16];
        this.projectionMatrix = new float[16];
        this.mvpMatrix = new float[16];

    }

    public void init(float[] projectionMatrix) {
        this.mProgram = this.shader.construct();
        this.projectionMatrix = projectionMatrix;
    }

    public void draw(float[] pos) {
        Matrix.setLookAtM(this.viewMatrix, 0, 0.0f, 0.0f, -3f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);

        Matrix.multiplyMM(this.mvpMatrix, 0, this.projectionMatrix, 0, this.viewMatrix, 0);

        GLES31.glUseProgram(this.mProgram);
        this.posHandle = GLES31.glGetAttribLocation(this.mProgram, "vPosition");
        this.vPMatrixHandle = GLES31.glGetUniformLocation(this.mProgram, "uMVPMatrix");

        this.modelPosHandle = GLES31.glGetUniformLocation(this.mProgram, "modelPos");
        GLES31.glUniform4fv(this.modelPosHandle, 1, pos, 0);

        GLES31.glUniformMatrix4fv(this.vPMatrixHandle, 1, false, mvpMatrix, 0);
        GLES31.glEnableVertexAttribArray(this.posHandle);

        GLES31.glVertexAttribPointer(this.posHandle, COORDS_PER_VERTEX,
                GLES31.GL_FLOAT, false,
                vertexStride, this.vertexBuffer);

        this.colorHandle = GLES31.glGetUniformLocation(this.mProgram, "vColor");

        GLES31.glUniform4fv(this.colorHandle, 1, this.color, 0);

        GLES31.glDrawArrays(GLES31.GL_TRIANGLES, 0, this.vertexCount);

        GLES31.glDisableVertexAttribArray(this.posHandle);
    }

    public float[] getViewMatrix() {
        return viewMatrix;
    }

    public float[] getProjectionMatrix() {
        return projectionMatrix;
    }
}
