package com.example.movingcircle;

import android.content.res.AssetManager;
import android.opengl.GLES31;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GameEngine implements GLSurfaceView.Renderer {

    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];

    private WorldManager worldManager;

    private static final int COORDS_PER_VERTEX = 3;
    private static final int VERTEX_STRIDE = COORDS_PER_VERTEX * Float.BYTES;

    public GameEngine(AssetManager assetManager) {
        this.worldManager = new WorldManager(assetManager);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES31.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        new Thread(new Runnable() {
            @Override
            public void run() {
                worldManager.worldLoop();
            }
        }).start();
        this.worldManager.getCircle().getModel().getShader().construct();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES31.glViewport(0, 0, width, height);
        float ratio = (float) width / height;

        Matrix.frustumM(this.projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        Matrix.setLookAtM(this.viewMatrix, 0, 0.0f, 0.0f, -3f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);
        Matrix.multiplyMM(this.vPMatrix, 0, this.projectionMatrix, 0, this.viewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES31.glClear(GLES31.GL_COLOR_BUFFER_BIT | GLES31.GL_DEPTH_BUFFER_BIT);
        drawModel(this.worldManager.getCircle().getModel());
    }

    private void drawModel(GraphicalModel model) {
        model.getShader().start();
        int posHandle = model.getShader().getPosAttribHandle();
        int colorUniformHandle = model.getShader().getColorUniformHandle();
        int vPMatrixHandle = model.getShader().getMVPMatrixUniformMatrix();
        int translationMatrixHandle = model.getShader().getTranslationMatrixUnfirom();
        GLES31.glEnableVertexAttribArray(posHandle);
        GLES31.glVertexAttribPointer(posHandle, COORDS_PER_VERTEX, GLES31.GL_FLOAT, false, VERTEX_STRIDE, model.getVertexBuffer());
        GLES31.glUniform4fv(colorUniformHandle, 1, model.getColor(), 0);
        GLES31.glUniformMatrix4fv(vPMatrixHandle, 1, false, this.vPMatrix, 0);
        GLES31.glUniformMatrix4fv(translationMatrixHandle, 1, false, model.getTranslationMatrix(), 0);
        GLES31.glDrawArrays(GLES31.GL_TRIANGLES, 0, model.getVertexCount());
        GLES31.glDisableVertexAttribArray(posHandle);
        model.getShader().stop();
    }
}
