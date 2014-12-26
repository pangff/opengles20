package com.pangff.opengles20_demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pangff.opengles2_prepare_demo.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void showTriangle(View view){
		startOpenGl(0);
	}
	
	public void showSquare(View view){
		startOpenGl(1);
	}

	public void startOpenGl(int flag){
		Intent intent = new Intent();
		intent.putExtra("flag", flag);
		intent.setClass(MainActivity.this, OpenGLActivity.class);
		startActivity(intent);
	}
}
