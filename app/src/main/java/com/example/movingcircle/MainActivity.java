package com.example.movingcircle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Surface;

public class MainActivity extends AppCompatActivity {

    private SurfaceView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.view = new SurfaceView(this);

        setContentView(this.view);
    }
}