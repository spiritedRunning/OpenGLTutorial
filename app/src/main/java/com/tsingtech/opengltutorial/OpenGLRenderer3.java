package com.tsingtech.opengltutorial;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import com.tsingtech.opengltutorial.mesh.Cube;
import com.tsingtech.opengltutorial.mesh.Group;
import com.tsingtech.opengltutorial.mesh.Mesh;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @description: 真正的3D图形
 * @author: zach liu
 * @date: 2020/9/3
 */
public class OpenGLRenderer3 implements GLSurfaceView.Renderer {
    private Mesh root;

    public OpenGLRenderer3(Context context) {
        Group group = new Group();

        Cube cube = new Cube(1, 1, 1);
        cube.rx = 45;
        cube.ry = 45;
        group.add(cube);
        root = group;

    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        gl10.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        gl10.glShadeModel(GL10.GL_SMOOTH);
        gl10.glClearDepthf(1.0f);
        gl10.glEnable(GL10.GL_DEPTH_TEST);
        gl10.glDepthFunc(GL10.GL_LEQUAL);
        gl10.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);


    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        gl10.glViewport(0, 0, width, height);
        gl10.glMatrixMode(GL10.GL_PROJECTION);
        gl10.glLoadIdentity();
        GLU.gluPerspective(gl10, 45.0f, (float) width / height, 0.1f, 100.0f);
        gl10.glMatrixMode(GL10.GL_MODELVIEW);
        gl10.glLoadIdentity();
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        gl10.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        // 重置matrix
        gl10.glLoadIdentity();
        // 将画面后退一点
        gl10.glTranslatef(0, 0, -4);

        root.draw(gl10);
    }
}
