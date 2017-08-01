package com.huyd.findingfault;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.huyd.domain.FirstBean;
import com.huyd.domain.PointBean;
import com.huyd.util.CountPointBroadcast;
import com.huyd.util.ImgData;
import com.huyd.views.DrawRingImageView;
import com.huyd.views.MyBean;
import com.huyd.views.ProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.id.text1;

public class GameActivity extends AppCompatActivity implements CountPointBroadcast.Message {

	protected static final int STOP = 0x10000;
	protected static final int NEXT = 0x10001;
	private int iCount = 0;

	private List<MyBean> list = null;
	private boolean START = true;// 如果不设置这个START进行判断,每次点击屏幕后,屏幕只允许出现一个圆
	MyBean bean = new MyBean();

	CountPointBroadcast countPointBroadcast;
	String flag = "";

	private ProgressView progress;
	private DrawRingImageView ring1, ring2;
	String data = "{\"first\":{\"point\":[{\"x\":\"250\",\"y\":250},{\"x\":\"400\",\"y\":400}]},\"second\":{\"point\":[{\"x\":\"250\",\"y\":250},{\"x\":\"500\",\"y\":500}]}}";
	String[] pointCount = {"first", "second"};

	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		initView();

//		if (getSharedPreferences().equals("1"))
//
//		try {


		if (!getSahrePreference().equals("0")) {
			String value = ImgData.list.get(0);
			if (getSahrePreference().equals("1")) {
				if (value.equals("1")) {
					ring1.setBackground(this.getResources().getDrawable(R.mipmap.point1_1));
					ring2.setBackground(this.getResources().getDrawable(R.mipmap.point1_2));
					ImgData.removeList();
					removeSharedPreference();
				} else if (value.equals("2")) {
					ring1.setBackground(this.getResources().getDrawable(R.mipmap.poing2_1));
					ring2.setBackground(this.getResources().getDrawable(R.mipmap.poing2_2));
					ImgData.removeList();
					removeSharedPreference();
				} else {
					ring1.setBackground(this.getResources().getDrawable(R.mipmap.poing2_1));
					ring2.setBackground(this.getResources().getDrawable(R.mipmap.poing2_2));
					ImgData.removeList();
					removeSharedPreference();
				}
			} else {
				ring1.setBackground(this.getResources().getDrawable(R.mipmap.point1_1));
				ring2.setBackground(this.getResources().getDrawable(R.mipmap.point1_2));
				ImgData.renewList();
			}

		}
//		} catch (Exception e) {
//
//		}


		List<PointBean> lists = new ArrayList<PointBean>();
		Map<String, List<PointBean>> map = new HashMap<String, List<PointBean>>();

		//解析JSON数据,并存入Map
		JSONObject dataJson = null;
		try {
			dataJson = new JSONObject(data);

			for (int i = 0; i < 2; i++) {
				JSONObject response = dataJson.getJSONObject(pointCount[i]);
				JSONArray data = response.getJSONArray("point");
				for (int j = 0; j < 2; j++) {
					JSONObject info = data.getJSONObject(j);
					String x = info.getString("x");
					String y = info.getString("y");
					lists.add(new PointBean(Float.parseFloat(x), Float.parseFloat(y)));
				}
//				map.put(String.valueOf(i), lists);
			}

//			ring1.setMap(map);


//			JSONObject response = dataJson.getJSONObject(pointCount[0]);
//			JSONArray data = response.getJSONArray("point");
//			JSONObject info = data.getJSONObject(0);
//			String x = info.getString("x");
//			String y = info.getString("y");
//
//			JSONObject info1 = data.getJSONObject(1);
//			String x1 = info1.getString("x");
//			String y1 = info1.getString("y");
//
//			FirstBean firstBean = new FirstBean();
//			PointBean pointBean = new PointBean();
//			Map<String, FirstBean> map1 = new HashMap<String, FirstBean>();
//
//
//			lists.add(new PointBean(Float.parseFloat(x), Float.parseFloat(y)));
//			lists.add(new PointBean(Float.parseFloat(x1), Float.parseFloat(y1)));
//
//
//			firstBean.setPoint(lists);
//
//			map1.put("first", firstBean);
//
//			ring1.setFirstBean(firstBean);
//
			ring1.setLists(lists);

			Log.i("jsoninfo = ", lists.get(0).getX() + " " + lists.get(1).getX());
			Log.i("jsoninfo == ", lists.get(2).getX() + " " + lists.get(3).getX());


//			System.out.println(province+city+district+address);
//			Log.i("jsoninfo = ", x + " " + y);
		} catch (JSONException e) {
			e.printStackTrace();
		}


//		initView();


		countPointBroadcast = new CountPointBroadcast();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.example.broadcasttest.MY_BROADCAST");
		registerReceiver(countPointBroadcast, intentFilter);

		//因为这里需要注入Message，所以不能在AndroidManifest文件中静态注册广播接收器
		countPointBroadcast.setMessage(this);


	}

	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
	private void initView() {

		progress = (ProgressView) findViewById(R.id.progress);
		progress.setMaxCount(100);
		mThread.start();
//		new Thread(progressRunable).start();
//		progress.setCurrentCount(55);
//		nextRing();
		ring1 = (DrawRingImageView) findViewById(R.id.ring1);
		ring2 = (DrawRingImageView) findViewById(R.id.ring2);
	}

	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
	private void nextRing() {
		ring1.setBackground(this.getResources().getDrawable(R.mipmap.poing2_1));
		ring2.setBackground(this.getResources().getDrawable(R.mipmap.poing2_2));
//		ring1.setBackgroundResource(R.mipmap.poing2_1);
//		ring1.setForeground(R.mipmap.poing2_1);
//		ring1.setImageResource(R.mipmap.poing2_1);


		progress.setMaxCount(50);
		progress.setCurrentCount(0);
		ring1.postInvalidate();
//		mThread.destroy();
//		mThread.start();


	}


	//创建一个线程,每秒步长为5增加,到100%时停止
	Thread mThread = new Thread(new Runnable() {

		public void run() {

			for (int i = 0; i < 20; i++) {
				try {
					iCount = (i + 1) * 5;
					Thread.sleep(1000);
					if (i == 19) {
						Message msg = new Message();
						msg.what = STOP;
						mHandler.sendMessage(msg);
						break;
					} else {
						Message msg = new Message();
						msg.what = NEXT;
						mHandler.sendMessage(msg);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	});

	//定义一个Handler
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case STOP:
//					progress.setVisibility(View.GONE);
					Thread.currentThread().interrupt();
					break;
				case NEXT:
					if (!Thread.currentThread().isInterrupted()) {

						progress.setCurrentCount(iCount);
						if (iCount == 100) {
							stopAlert();
						}

					}
					break;
			}
		}
	};


	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void getMsg(String str) {
		flag = str;
		if (flag.equals("2")) {
//			Toast.makeText(GameActivity.this, "通关成功", Toast.LENGTH_SHORT).show();
//			nextRing();
			showAlert();
		}
	}


	private void showAlert() {
		new AlertDialog.Builder(this)
				.setTitle("恭喜你")
				.setMessage("你已经顺利闯关")
				.setPositiveButton("下一关", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
//						new Thread(checkActivation).start();
						setSharedPreference();
						Intent intentNext = new Intent(GameActivity.this, GameActivity.class);
						startActivity(intentNext);
					}
				})
				.setNegativeButton("返回", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						setSharedPreferenceMain();
						Intent intentMain = new Intent(GameActivity.this, MainActivity.class);
						startActivity(intentMain);
					}
				})
				.show();
	}


	private void stopAlert() {
		new AlertDialog.Builder(this)
				.setTitle("时间到")
				.setMessage("闯关失败")
				.setNegativeButton("返回", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						setSharedPreferenceMain();
						Intent intentMain = new Intent(GameActivity.this, MainActivity.class);
						startActivity(intentMain);
					}
				})
				.show();
	}


	public void setSharedPreference() {
		SharedPreferences sharedPreferences = getSharedPreferences("game", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("info", "1");
		editor.commit();// 提交修改
	}

	public void setSharedPreferenceMain() {
		SharedPreferences sharedPreferences = getSharedPreferences("game", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("info", "2");
		editor.commit();// 提交修改
	}


	// 获得sharedpreferences的数据
	public String getSahrePreference() {
		SharedPreferences sharedPreferences = getSharedPreferences("game", Context.MODE_PRIVATE);
		String info = sharedPreferences.getString("info", "");
		String str = String.valueOf(info);
		if (str.length() > 0) {
			return str;
		} else {
			return "0";
		}
//		return str;
	}

	// 清除sharedpreferences的数据
	public void removeSharedPreference() {
		SharedPreferences sharedPreferences = getSharedPreferences("game", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.remove("info");
		editor.commit();// 提交修改
	}
}