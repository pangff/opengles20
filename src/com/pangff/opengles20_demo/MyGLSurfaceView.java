package com.pangff.opengles20_demo;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;

public class MyGLSurfaceView extends GLSurfaceView implements Renderer{
	
	Triangle mTriangle;
	Square mSquare;
	
	public MyGLSurfaceView(Context context){
        super(context);
        setEGLContextClientVersion(2);
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(this);
    }

	/**
	 * 调用一次，初始化opengles 环境
	 */
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		
		// 设置背景颜色
        GLES20.glClearColor(0.97f, 0.58f, 0.0f, 1.0f);
       
        // initialize a triangle
        mTriangle = new Triangle();
        // initialize a square
        mSquare = new Square();
	}

	/**
	 * 当glView改变时调用，比如屏幕方向彼变化
	 */
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		//设置绘制区域
		GLES20.glViewport(0, 0, width, height);
	}

	/**
	 * 每次重绘调用
	 */
	@Override
	public void onDrawFrame(GL10 gl) {
		// 重绘背景
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        mTriangle.draw();
	}
	
	/**
	 * 编译着色器
	 * @param type
	 * @param shaderCode
	 * @return
	 */
	public static int loadShader(int type, String shaderCode){

	    // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
	    // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
	    int shader = GLES20.glCreateShader(type);

	    // add the source code to the shader and compile it
	    GLES20.glShaderSource(shader, shaderCode);
	    GLES20.glCompileShader(shader);

	    return shader;
	}
}