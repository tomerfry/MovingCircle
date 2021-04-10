package com.example.movingcircle;

import android.opengl.GLES31;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyRenderer implements GLSurfaceView.Renderer {

    private Triangle triangle;
    private Circle circle;
    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];

    public MyRenderer(Triangle triangle, Circle circle) {
        this.triangle = triangle;
        this.circle = circle;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES31.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        this.triangle.constructProgram();
        this.circle.constructProgram();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES31.glViewport(0, 0, width, height);
        float ratio = (float) width / height;

        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES31.glClear(GLES31.GL_COLOR_BUFFER_BIT | GLES31.GL_DEPTH_BUFFER_BIT);

        Matrix.setLookAtM(this.viewMatrix, 0, 0.0f, 0.0f, -3f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);

        Matrix.multiplyMM(this.vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        this.circle.draw(this.vPMatrix);
    }

    public static int loadShader(int type, String shaderCode) {
        int shader = GLES31.glCreateShader(type);
        GLES31.glShaderSource(shader, shaderCode);
        GLES31.glCompileShader(shader);

        return shader;
    }
}
