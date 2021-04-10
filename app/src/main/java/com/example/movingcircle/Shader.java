package com.example.movingcircle;

import android.content.res.AssetManager;
import android.opengl.GLES31;


public class Shader {

    private String vertexShaderPath;
    private String fragmentShaderPath;
    private AssetManager assetManager;

    public Shader(String vertexShaderPath, String fragmentShaderPath, AssetManager assetManager) {
        this.vertexShaderPath = vertexShaderPath;
        this.fragmentShaderPath = fragmentShaderPath;
        this.assetManager = assetManager;
    }

    public int construct() {
        String vertexShader, fragmentShader;
        vertexShader = AssetsTools.readAsset(vertexShaderPath, assetManager);
        fragmentShader = AssetsTools.readAsset(fragmentShaderPath, assetManager);
        int shaderHandle;

        int vertexShaderHandle = loadShader(GLES31.GL_VERTEX_SHADER, vertexShader);
        int fragmentShaderHandle = loadShader(GLES31.GL_FRAGMENT_SHADER, fragmentShader);
        shaderHandle = GLES31.glCreateProgram();
        GLES31.glAttachShader(shaderHandle, vertexShaderHandle);
        GLES31.glAttachShader(shaderHandle, fragmentShaderHandle);
        GLES31.glLinkProgram(shaderHandle);

        return shaderHandle;
    }

    private static int loadShader(int type, String shaderCode) {
        int shader = GLES31.glCreateShader(type);
        GLES31.glShaderSource(shader, shaderCode);
        GLES31.glCompileShader(shader);
        return shader;
    }
}
