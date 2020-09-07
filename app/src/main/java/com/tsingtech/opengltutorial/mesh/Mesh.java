package com.tsingtech.opengltutorial.mesh;

import android.graphics.Bitmap;
import android.opengl.GLU;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * @description: 空间形体最基本元素Mesh, 三角形网格
 * @author: zach liu
 * @date: 2020/9/4
 */
public class Mesh {
    private FloatBuffer verticesBuffer = null;
    private ShortBuffer indicesBuffer = null;

    private int mTextureId = -1;
    private Bitmap mBitmap;
    private FloatBuffer mTextureBuffer;
    private boolean mShouldLoadTexture = false;

    private int numOfIndices = -1;

    private float[] rgba = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
    private FloatBuffer colorBuffer = null;

    public float x = 0, y = 0, z = 0;
    public float rx = 0, ry = 0, rz = 0;  // rotate param


    public void draw(GL10 gl) {
        gl.glFrontFace(GL10.GL_CCW);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glCullFace(GL10.GL_BACK);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, verticesBuffer);
        // set flat color
        gl.glColor4f(rgba[0], rgba[1], rgba[2], rgba[3]);

        if (colorBuffer != null) {
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
            gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
        }


        // new part start
        if (mShouldLoadTexture) {
            loadGLTexture(gl);
            mShouldLoadTexture = false;
        }

        if (mTextureId != -1 && mTextureBuffer != null) {
            gl.glEnable(GL10.GL_TEXTURE_2D);
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
            gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);
        }
        // new part end


        gl.glTranslatef(x, y, z);
        gl.glRotatef(rx, 1, 0, 0);
        gl.glRotatef(ry, 0, 1, 0);
        gl.glRotatef(rz, 0, 0, 1);

        // Point out the where the color buffer is
        gl.glDrawElements(GL10.GL_TRIANGLES, numOfIndices, GL10.GL_UNSIGNED_SHORT, indicesBuffer);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);


        // new part start
        if (mTextureId != -1 && mTextureBuffer != null) {
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        }
        // new part end

        gl.glDisable(GL10.GL_CULL_FACE);
    }

    /**
     * 允许子类重新定义顶点坐标
     * @param vertices
     */
    protected void setVertices(float[] vertices) {
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        verticesBuffer = vbb.asFloatBuffer();
        verticesBuffer.put(vertices);
        verticesBuffer.position(0);
    }

    /**
     * 允许子类重新定义顶点的顺序
     * @param indices
     */
    protected void setIndices(short[] indices) {
        ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        indicesBuffer = ibb.asShortBuffer();
        indicesBuffer.put(indices);
        indicesBuffer.position(0);
        numOfIndices = indices.length;
    }

    protected void setColor(float red, float green, float blue, float alpha) {
        rgba[0] = red;
        rgba[1] = green;
        rgba[2] = blue;
        rgba[3] = alpha;
    }

    /**
     * 允许子类重新定义颜色
     * @param colors
     */
    protected void setColors(float[] colors) {
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder());
        colorBuffer = cbb.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);
    }

    protected void setTextureCoordinates(float[] textureCoords) {
        ByteBuffer tbb = ByteBuffer.allocateDirect(textureCoords.length * 4);
        tbb.order(ByteOrder.nativeOrder());
        mTextureBuffer = tbb.asFloatBuffer();
        mTextureBuffer.put(textureCoords);
        mTextureBuffer.position(0);
    }

    public void loadBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
        this.mShouldLoadTexture = true;
    }

    public void loadGLTexture(GL10 gl) {
        int[] textures = new int[1];
        gl.glGenTextures(1, textures, 0);
        mTextureId = textures[0];

        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);

        // Create Nearest Filtered Texture
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);  // 设置最小过滤，第三个参数决定用什么过滤；
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);  // 设置最大过滤，也是第三个参数决定；

        // Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT); // 纹理坐标一般用str表示，分别对应xyz，2d纹理用st表示
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT); // 接上面，纹理和你画的几何体可能不是完全一样大的，在边界的时候如何处理呢？就是这两个参数决定的，wrap表示环绕，可以理解成让纹理重复使用，直到全部填充完成；

        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mBitmap, 0);
    }

}
