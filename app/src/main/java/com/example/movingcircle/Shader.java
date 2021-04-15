package com.example.movingcircle;

import android.content.res.AssetManager;
import android.opengl.GLES31;


public class Shader {

    private int vertexShaderHandle;
    private int fragmentShaderHandle;
    private String vertexShaderPath;
    private String fragmentShaderPath;
    private AssetManager assetManager;

    private int programHandle;

    public Shader(String vertexShaderPath, String fragmentShaderPath, AssetManager assetManager) {
        this.vertexShaderPath = vertexShaderPath;
        this.fragmentShaderPath = fragmentShaderPath;
        this.assetManager = assetManager;
    }

    public void construct() {
        String vertexShader, fragmentShader;
        vertexShader = AssetsTools.readAsset(vertexShaderPath, assetManager);
        fragmentShader = AssetsTools.readAsset(fragmentShaderPath, assetManager);
        this.vertexShaderHandle = loadShader(GLES31.GL_VERTEX_SHADER, vertexShader);
        this.fragmentShaderHandle = loadShader(GLES31.GL_FRAGMENT_SHADER, fragmentShader);
        this.programHandle = GLES31.glCreateProgram();
        GLES31.glAttachShader(this.programHandle, vertexShaderHandle);
        GLES31.glAttachShader(this.programHandle, fragmentShaderHandle);
        GLES31.glLinkProgram(this.programHandle);
    }

    public void start() {
        GLES31.glUseProgram(this.programHandle);
    }

    public void stop() {
        GLES31.glUseProgram(0);
    }

    public void cleanup() {
        this.stop();
        GLES31.glDetachShader(this.programHandle, this.vertexShaderHandle);
        GLES31.glDetachShader(this.programHandle, this.fragmentShaderHandle);
    }

    private static int loadShader(int type, String shaderCode) {
        int shader = GLES31.glCreateShader(type);
        GLES31.glShaderSource(shader, shaderCode);
        GLES31.glCompileShader(shader);
        return shader;
    }

    public int getPosHandle() {
        return GLES31.glGetAttribLocation(this.programHandle, "position");
    }

    public int getColorUniformHandle() {
        return GLES31.glGetUniformLocation(this.programHandle, "vColor");
    }

    public int getProjectionMatrix() {
        return GLES31.glGetUniformLocation(this.programHandle, "projectionMatrix");
    }

    public int getTransformationMatrixUnfirom() {
        return GLES31.glGetUniformLocation(this.programHandle, "transformationMatrix");
    }

    public int getViewMatrixUniform() {
        return GLES31.glGetUniformLocation(this.programHandle, "viewMatrix");
    }
}
