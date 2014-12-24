package com.pangff.opengles20_demo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;

public class Triangle {
	
	private FloatBuffer vertexBuffer;
	int mProgram;
	int mPositionHandle;
	int mColorHandle;
	int vertexCount = 3;//顶点数
	int vertexStride = COORDS_PER_VERTEX*4;//跨度
	int mMVPMatrixHandle;
	
	// number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static float triangleCoords[] = {   // in counterclockwise order:逆时针定义
             0.0f,  0.622008459f, 0.0f, // top
            -0.5f, -0.311004243f, 0.0f, // bottom left
             0.5f, -0.311004243f, 0.0f  // bottom right
    };
    
   // Set color with red, green, blue and alpha (opacity) values
    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };
    
    public Triangle() {
    		/** 申请空间 **/
        // 初始化顶点坐标缓冲区(initialize vertex byte buffer for shape coordinates)
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // 一个float占4个字节(number of coordinate values * 4 bytes per float)
                triangleCoords.length * 4);
        // 硬件本地排序(use the device hardware's native byte order)
        bb.order(ByteOrder.nativeOrder());

        // 初始化顶点Floatbuffer(create a floating point buffer from the ByteBuffer)
        vertexBuffer = bb.asFloatBuffer();
        // 添加坐标(add the coordinates to the FloatBuffer)
        vertexBuffer.put(triangleCoords);
        // 从第一个坐标开始读(set the buffer to read the first coordinate)
        vertexBuffer.position(0);
        
        
        int vertexShader = MyGLSurfaceView.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyGLSurfaceView.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL ES Program
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram);                  // creates OpenGL ES program executables
    }
    
    public void draw(float[] mvpMatrix) {
    		// 将program加入OpenGL ES环境中
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram);

        // 获取指向vertex shader的成员vPosition的 handle
        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // 启用一个指向三角形的顶点数组的handle
        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // 准备三角形的坐标数据
        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                                     GLES20.GL_FLOAT, false,
                                     vertexStride, vertexBuffer);

        // 获取指向fragment shader的成员vColor的handle 
        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // 设置三角形的颜色
        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);
        
        
       // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        // 画三角形
        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        
        // 禁用指向三角形的顶点数组
        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
    
    /**
     * 顶点着色器
     */
    private final String vertexShaderCode =
    		 "uniform mat4 uMVPMatrix;   \n" +
    	    "attribute vec4 vPosition; \n" +
    	    "void main() {  \n" +
    	    "  gl_Position = uMVPMatrix * vPosition; \n" +
    	    "}";

    /** 片着色器 **/
    	private final String fragmentShaderCode =
    	    "precision mediump float;" +
    	    "uniform vec4 vColor;" +
    	    "void main() {" +
    	    "  gl_FragColor = vColor;" +
    	    "}";
}
