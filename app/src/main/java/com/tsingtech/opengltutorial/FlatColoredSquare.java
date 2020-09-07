package com.tsingtech.opengltutorial;

import javax.microedition.khronos.opengles.GL10;

/**
 * @description: 带颜色方块
 * @author: zach liu
 * @date: 2020/9/4
 */
public class FlatColoredSquare extends Square {

    @Override
    public void draw(GL10 gl) {
        gl.glColor4f(0.5f, 0.5f, 1.0f, 1.0f);

        super.draw(gl);
    }
}
