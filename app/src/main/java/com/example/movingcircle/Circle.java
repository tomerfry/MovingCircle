package com.example.movingcircle;

import android.opengl.GLES31;
import android.util.Log;

import org.joml.Vector3f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

public class Circle implements ModelInterface {

    private float radius;
    private Vector3f pos;

    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private int mProgram;
    private int posHandle;
    private int colorHandle;

    private float[] color;
    private FloatBuffer vertexBuffer;
    private int verticesCount;

    static final short steps = 40;
    static final float angle = (float) Math.PI * 2.0f / steps;

    static final int COORDS_PER_VERTEX = 3;
    private final int vertexStride = COORDS_PER_VERTEX * 4;

    public Circle(Vector3f pos, float radius, float[] color) {
        this.color = color;
        this.radius = radius;
        this.pos = pos;

        Vector3f prev = new Vector3f(pos.x, pos.y, pos.z);
        Vector3f newV;

        ArrayList<Vector3f> circleCoords = new ArrayList<>();

        for (int i = 0; i <= steps; i++) {
            newV = new Vector3f(
                    radius * (float) (Math.sin((double) angle * i)),
                    -radius * (float) (Math.cos((double) angle * i)),
                    pos.z);

            circleCoords.add(pos);
            circleCoords.add(prev);
            circleCoords.add(newV);

            prev = newV;
        }
        this.verticesCount = circleCoords.size();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(this.verticesCount * 3 * Float.BYTES);
        byteBuffer.order(ByteOrder.nativeOrder());
        this.vertexBuffer = byteBuffer.asFloatBuffer();

        for(Vector3f v:circleCoords) {
            this.vertexBuffer.put(v.x).put(v.y).put(v.z);
        }
        this.vertexBuffer.position(0);
    }

    @Override
    public void constructProgram() {
        int vertexShader = MyRenderer.loadShader(GLES31.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyRenderer.loadShader(GLES31.GL_FRAGMENT_SHADER, fragmentShaderCode);

        this.mProgram = GLES31.glCreateProgram();
        GLES31.glAttachShader(this.mProgram, vertexShader);
        GLES31.glAttachShader(this.mProgram, fragmentShader);
        GLES31.glLinkProgram(this.mProgram);
    }

    @Override
    public void draw() {
        GLES31.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        this.posHandle = GLES31.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES31.glEnableVertexAttribArray(this.posHandle);

        // Prepare the triangle coordinate data
        GLES31.glVertexAttribPointer(this.posHandle, COORDS_PER_VERTEX,
                GLES31.GL_FLOAT, false,
                vertexStride, this.vertexBuffer);

        // get handle to fragment shader's vColor member
        colorHandle = GLES31.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        GLES31.glUniform4fv(colorHandle, 1, color, 0);

        // Draw the triangle
        GLES31.glDrawArrays(GLES31.GL_TRIANGLES, 0, this.verticesCount);

        // Disable vertex array
        GLES31.glDisableVertexAttribArray(this.posHandle);
    }
}
