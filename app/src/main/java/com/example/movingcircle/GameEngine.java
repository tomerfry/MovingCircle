package com.example.movingcircle;

import android.content.res.AssetManager;
import android.opengl.GLES31;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GameEngine implements GLSurfaceView.Renderer {

    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];

    private WorldManager worldManager;

    private static final int COORDS_PER_VERTEX = 3;
    private static final int VERTEX_STRIDE = COORDS_PER_VERTEX * Float.BYTES;

    private float width, height;

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

        this.width = width;
        this.height = height;

        Matrix.orthoM(this.projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        Matrix.setLookAtM(this.viewMatrix, 0, 0.0f, 0.0f, -3f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES31.glClear(GLES31.GL_COLOR_BUFFER_BIT | GLES31.GL_DEPTH_BUFFER_BIT);
        drawModel(this.worldManager.getCircle().getModel());
    }

    private void drawModel(GraphicalModel model) {
        model.getShader().start();
        int posHandle = model.getShader().getPosHandle();
        int colorUniformHandle = model.getShader().getColorUniformHandle();
        int projectionMatrixHandle = model.getShader().getProjectionMatrix();
        int viewMatrixHandle = model.getShader().getViewMatrixUniform();
        int transformationMatrixHandle = model.getShader().getTransformationMatrixUnfirom();

        GLES31.glEnableVertexAttribArray(posHandle);
        GLES31.glVertexAttribPointer(posHandle, COORDS_PER_VERTEX, GLES31.GL_FLOAT, false, VERTEX_STRIDE, model.getVertexBuffer());

        GLES31.glUniformMatrix4fv(transformationMatrixHandle, 1, false, model.getTranslationMatrix(), 0);
        GLES31.glUniformMatrix4fv(projectionMatrixHandle, 1, false, this.projectionMatrix, 0);
        GLES31.glUniformMatrix4fv(viewMatrixHandle, 1, false, this.viewMatrix, 0);
        GLES31.glUniform4fv(colorUniformHandle, 1, model.getColor(), 0);

        GLES31.glDrawArrays(GLES31.GL_TRIANGLES, 0, model.getVertexCount());
        GLES31.glDisableVertexAttribArray(posHandle);
        model.getShader().stop();
    }

    public void onTouch(float x, float y) {
        float[] cursorPosInSpace = CoordsMapper.calculateCursorRay(x, y, this.width, this.height, this.projectionMatrix, this.viewMatrix);
        this.worldManager.applyTouch(cursorPosInSpace);
    }
}
