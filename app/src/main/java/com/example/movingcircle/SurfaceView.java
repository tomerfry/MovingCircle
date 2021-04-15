package com.example.movingcircle;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SurfaceView extends GLSurfaceView {

    private GameEngine gameEngine;

    public SurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        this.gameEngine = new GameEngine(context.getAssets());
        this.setRenderer(this.gameEngine);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event == null)
                    return false;

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    return true;
                }
                return false;
            }
        });
    }

    public SurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
