package com.example.movingcircle;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class SurfaceView extends GLSurfaceView {

    private Renderer renderer;

    public SurfaceView(Context context) {
        super(context);
        this.renderer = new MyRenderer();
        this.setRenderer(this.renderer);
    }

    public SurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
