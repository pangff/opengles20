package com.pangff.opengles20_demo;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;

public class OpenGLActivity extends Activity {

	private GLSurfaceView mGLView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(hasGLES20()){
			mGLView = new MyGLSurfaceView(this,this.getIntent().getIntExtra("flag", 0));
		}
		setContentView(mGLView);
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				mGLView.requestRender();
			}
		}, 1000);
	}
	
	
 	@Override
    protected void onResume() {
        super.onResume();
        mGLView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLView.onPause();
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
