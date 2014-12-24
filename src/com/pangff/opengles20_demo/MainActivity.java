package com.pangff.opengles20_demo;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class MainActivity extends Activity {

	private GLSurfaceView mGLView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(hasGLES20()){
			mGLView = new MyGLSurfaceView(this);
		}
		setContentView(mGLView);
	}
	
	/**
	 * 判断是否支持es2.0
	 * @return
	 */
	private boolean hasGLES20(){
		ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
		ConfigurationInfo info = am.getDeviceConfigurationInfo();
		return info.reqGlEsVersion >=0x20000;
	}
}
