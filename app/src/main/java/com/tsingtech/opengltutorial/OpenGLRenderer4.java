package com.tsingtech.opengltutorial;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import com.tsingtech.opengltutorial.mesh.Group;
import com.tsingtech.opengltutorial.mesh.Mesh;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @description: 材质渲染
 * @author: zach liu
 * @date: 2020/9/3
 */
public class OpenGLRenderer4 implements GLSurfaceView.Renderer {
    private Group root;

    public OpenGLRenderer4() {
        Group group = new Group();
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
        gl10.glTranslatef(0, 0, -4.5f);

        root.draw(gl10);
    }

    public void addMesh(Mesh mesh) {
        root.add(mesh);
    }
}
