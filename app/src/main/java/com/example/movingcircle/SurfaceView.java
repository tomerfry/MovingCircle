package com.example.movingcircle;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class SurfaceView extends GLSurfaceView {

    private Renderer renderer;
    private WorldManager worldManager;


    public SurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        this.worldManager = new WorldManager(this.getContext().getAssets());
        this.renderer = new MyRenderer(worldManager.getCircle());
        this.setRenderer(this.renderer);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event == null)
                    return false;

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    worldManager.applyTouch(event.getX(), event.getY(), (float) getWidth(), (float) getHeight());
                    return true;
                }
                return false;
            }
        });

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
