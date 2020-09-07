package com.tsingtech.opengltutorial;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * @description: 渐变色方块
 * @author: zach liu
 * @date: 2020/9/4
 */
public class SmoothColoredSquare extends Square {

    private float[] colors = {
            1f, 0f, 0f, 1f,  // vertex 0 red
            0f, 1f, 0f, 1f,  // vertex 1 green
            0f, 0f, 1f, 1f,  // vertex 2 blue
            1f, 0f, 1f, 1f,  // vertex 3 magenta
    };

    private FloatBuffer colorBuffer;

    public SmoothColoredSquare() {
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder());
        colorBuffer = cbb.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);
    }

    @Override
    public void draw(GL10 gl) {
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);

        super.draw(gl);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }
}
