package com.huyd.findingfault;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.huyd.views.DrawRingImageView;
import com.huyd.views.MyBean;

import java.util.List;

public class GameActivity extends AppCompatActivity {

	private List<MyBean> list = null;
	private boolean START = true;// 如果不设置这个START进行判断,每次点击屏幕后,屏幕只允许出现一个圆
	MyBean bean = new MyBean();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

	}

}
