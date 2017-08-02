package com.huyd.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.huyd.domain.FirstBean;
import com.huyd.domain.PointBean;
import com.huyd.findingfault.GameActivity;
import com.huyd.impl.ClickErrorCallback;
import com.huyd.util.Circle;
import com.huyd.util.Point;

/**
 * Author: huyd
 * Date: 2017-07-28
 * Time: 15:42
 * Describe:自定义View.绘制圆环
 */
public class DrawRingImageView extends ImageView {


	private int e = 0;

	Canvas canvas;

	private ImageViewListener imageViewListener = null;

	private List<MyBean> list = null;
	private FirstBean firstBean = new FirstBean();
	Map<String, List<PointBean>> map = new HashMap<String, List<PointBean>>();

	private int MaxAlpha = 255;// 最大透明度
	private boolean START = true;// 如果不设置这个START进行判断,每次点击屏幕后,屏幕只允许出现一个圆

	private float pointx, pointy;

	private int flag = 0;

	PointBean pointBean = new PointBean();
	List<PointBean> lists = new ArrayList<PointBean>();

	public Map<String, List<PointBean>> getMap() {
		return map;
	}

	public void setMap(Map<String, List<PointBean>> map) {
		this.map = map;
	}

	public List<PointBean> getLists() {
		return lists;
	}

	public void setLists(List<PointBean> lists) {
		this.lists = lists;
	}


	public DrawRingImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public DrawRingImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		list = new ArrayList<MyBean>();
	}

	/**
	 * MyView大小
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		this.canvas = canvas;

//		Paint paint = new Paint();
//		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//		canvas.drawPaint(paint);
//		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
//		canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

		for (int i = 0; i < list.size(); i++) {
			MyBean wave = list.get(i);
			canvas.drawCircle(wave.X, wave.Y, wave.radius, wave.paint);
		}
//		if (flag == 2) {
//			for (int i = 0; i < list.size(); i++) {
//				MyBean wave = list.get(i);
//				canvas.drawCircle(0, 0, 0, wave.paint);
//			}

//		}
	}

	//监听改变
	public void onLinstenerChange(float x, float y) {
		Circle circle = new Circle(new Point(x, y), 50);
		Point p = new Point(x, y);
		MyBean bean = new MyBean();
		bean.radius = 0; // 点击后 半径先设为0
		bean.alpha = MaxAlpha; // alpha设为最大值 255
		bean.width = bean.radius / 8; // 描边宽度 这个随意
		bean.X = (int) x; // 所绘制的圆的X坐标
		bean.Y = (int) y; // 所绘制的圆的Y坐标
		bean.paint = initPaint(bean.alpha, bean.width);

		if (list.size() == 0) {
			// 如果不设置这个START进行判断,每次点击屏幕后,屏幕只允许出现一个圆
			START = true;
		}
		list.add(bean);
		invalidate();
		if (START) {
			handler.sendEmptyMessage(0);
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);


		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:


				Log.i("position", "X=" + event.getX() + " Y=" + event.getY());

				List<PointBean> l = map.get(0);


				if (lists.size() >= 2) {
					for (int i = 0; i < 2; i++) {

						Log.i("position--", "X=" + event.getX() + " Y=" + event.getY());
						Circle circle = new Circle(new Point(lists.get(i).getX(), lists.get(i).getY()), 50);
						Point p = new Point(event.getX(), event.getY());

						if (circle.isInner(p)) {
							lists.remove(i);
							flag = flag + 1;
							// 点击屏幕后 半径设为0,alpha设置为255
							MyBean bean = new MyBean();
							bean.radius = 0; // 点击后 半径先设为0
							bean.alpha = MaxAlpha; // alpha设为最大值 255
							bean.width = bean.radius / 8; // 描边宽度 这个随意
							bean.X = (int) event.getX(); // 所绘制的圆的X坐标
							bean.Y = (int) event.getY(); // 所绘制的圆的Y坐标
							bean.paint = initPaint(bean.alpha, bean.width);

							if (imageViewListener != null) {
								imageViewListener.onImageViewChanged(this, (int) event.getX(), (int) event.getY());
							}

							if (list.size() == 0) {
								// 如果不设置这个START进行判断,每次点击屏幕后,屏幕只允许出现一个圆
								START = true;
							}
							list.add(bean);
							invalidate();
							if (START) {
								handler.sendEmptyMessage(0);
							}
						}

						if (circle.isInner(p)){
							e =0;
							break;
						}else {//点击不在规定范围中
							e = 2;

						}

					}
					Intent intent = new Intent("com.example.broadcasttest.ERROR_BROADCAST");
					intent.putExtra("error", String.valueOf(e));
					getContext().sendBroadcast(intent);
				}

//				if (flag == 2) {
//					try {
//						if (lists.size() >= 2) {
//							lists.remove(0);
//							lists.remove(1);
//						}
//					} catch (Exception e) {
//
//					}
//
////					drawLine();
//				}
				break;
		}

		if (flag == 2) {
			Intent intent = new Intent("com.example.broadcasttest.MY_BROADCAST");
			intent.putExtra("flag", String.valueOf(flag));
			getContext().sendBroadcast(intent);
//			for (int i = 0; i < list.size(); i++) {
//				list.remove(i);
//			}
//			clear();
//			clearHandler.sendEmptyMessage(0);
//			flag = 0;
		} else {
			Intent intent = new Intent("com.example.broadcasttest.MY_BROADCAST");
			intent.putExtra("flag", String.valueOf(flag));
			getContext().sendBroadcast(intent);
		}


		return true;
	}

	/**
	 * 初始化paint
	 */
	public Paint initPaint(int alpha, float width) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);// 抗锯齿
		paint.setStrokeWidth(width);// 描边宽度
		paint.setStyle(Paint.Style.STROKE);// 圆环
		paint.setAlpha(alpha);// 透明度
		paint.setColor(Color.RED);// 颜色
		return paint;
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case 0:
					Refresh();
					invalidate();
					if (list != null && list.size() > 0) {
						handler.sendEmptyMessageDelayed(0, 50);// 每50毫秒发送
					}
					break;

				default:
					break;
			}
		}

	};

	Handler clearHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case 0:
					invalidate();
					if (list != null && list.size() > 0) {
						handler.sendEmptyMessageDelayed(0, 50);// 每50毫秒发送
					}
					break;
			}
		}
	};


	private void clear() {
		for (int i = 0; i < list.size(); i++) {
			MyBean bean = list.get(i);
			if (START == false && bean.alpha == 0) {
				list.remove(i);
				bean.paint = null;
				bean = null;
				continue;
			} else if (START == true) {
				START = false;
			}

			bean.radius = 70;
//			bean.radius += 5;// 半径每次+5
//			bean.alpha -= 10;// 透明度每次减10
//			if (bean.alpha < 0) {
//				// 透明度小雨0的时候 赋为0
			bean.alpha = 0;
//			}
			bean.width = bean.radius / 8; // 描边宽度设置为半径的1/4
			bean.paint.setAlpha(bean.alpha);
			bean.paint.setStrokeWidth(bean.width);
		}
		invalidate();
	}

	int dot = 0;

	/***
	 * 刷新
	 */
	private void Refresh() {
		for (int i = 0; i < list.size(); i++) {
			MyBean bean = list.get(i);
			if (START == false && bean.alpha == 0) {
				list.remove(i);
				bean.paint = null;
				bean = null;
				continue;
			} else if (START == true) {
				START = false;
			}
			bean.radius = 70;
			dot = dot + 1;

//			bean.radius += 5;// 半径每次+5
//			bean.alpha -= 10;// 透明度每次减10
//			if (bean.alpha < 0) {
//				// 透明度小雨0的时候 赋为0
//				bean.alpha = 0;
//			}
			bean.width = bean.radius / 8; // 描边宽度设置为半径的1/4
			bean.paint.setAlpha(bean.alpha);
			bean.paint.setStrokeWidth(bean.width);
		}
	}

	public float getPointx() {
		return pointx;
	}

	public void setPointx(float pointx) {
		this.pointx = pointx;
	}

	public float getPointy() {
		return pointy;
	}

	public void setPointy(float pointy) {
		this.pointy = pointy;
	}

	public void setImageViewListener(ImageViewListener imageViewListener) {
		this.imageViewListener = imageViewListener;
	}

	public void getFlagData(ClickErrorCallback clickErrorCallback) {
		clickErrorCallback.getFlag(e);
	}
}