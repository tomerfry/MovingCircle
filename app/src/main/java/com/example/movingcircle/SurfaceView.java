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
        this.worldManager = new WorldManager(this.getContext().getAssets());
        this.renderer = new MyRenderer(worldManager.getCircle());
        this.setRenderer(this.renderer);

        new Thread(new Runnable() {
            @Override
            public void run() {
                worldManager.worldLoop();
            }
        }).start();

    }

    public SurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
