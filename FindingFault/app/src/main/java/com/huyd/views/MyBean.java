package com.huyd.views;

import android.graphics.Paint;

/**
 * Author: huyd
 * Date: 2017-07-28
 * Time: 17:03
 * Describe:
 */
public class MyBean {
	int alpha; // 透明度
	int X; // X坐标
	int Y; // Y坐标
	float width; // 描边宽度
	float radius; // 半径
	Paint paint; // 画笔

	public MyBean() {
	}

	public int getAlpha() {
		return alpha;
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public Paint getPaint() {
		return paint;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}
}
