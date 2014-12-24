package com.pangff.opengles20_demo;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;

public class MyGLSurfaceView extends GLSurfaceView implements Renderer{
	
	Triangle mTriangle;
	Square mSquare;
	
	//投影矩阵
    private float[] mProjectionMatrix = new float[16];
    
    //视图矩阵
    private float[] mViewMatrix = new float[16];
    
    //模型视图投影矩阵
    private float[] mMVPMatrix = new float[16];
	
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
		
		float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
	}

	/**
	 * 每次重绘调用
	 */
	@Override
	public void onDrawFrame(GL10 gl) {
		// 重绘背景
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        
     // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Draw shape
        mTriangle.draw(mMVPMatrix);
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