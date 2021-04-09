package com.example.movingcircle;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class SurfaceView extends GLSurfaceView {

    private Renderer renderer;
    private WorldManager worldManager;


    public SurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        this.worldManager = new WorldManager();
        this.renderer = new MyRenderer(worldManager.getTriangle(), worldManager.getCircle());
        this.setRenderer(this.renderer);
    }

    public SurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
