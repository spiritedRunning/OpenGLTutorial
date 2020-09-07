package com.tsingtech.opengltutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        GLSurfaceView view = new GLSurfaceView(this);
//        view.setRenderer(new OpenGLRenderer3(this));  // todo

        OpenGLRenderer4 renderer = new OpenGLRenderer4();
        view.setRenderer(renderer);
        SimplePlane plane = new SimplePlane(1, 1);

        // Move and rotate the plane
        plane.z = 1.7f;
        plane.rx = -65;

        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.jay);
        plane.loadBitmap(mBitmap);
        renderer.addMesh(plane);

        setContentView(view);
    }
}