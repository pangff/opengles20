package com.pangff.opengles20_demo;

import android.graphics.Bitmap;

public abstract class Model {
	public abstract void draw(float[] mvpMatrix);
	public abstract void setTexture(Bitmap bitmap);
}
