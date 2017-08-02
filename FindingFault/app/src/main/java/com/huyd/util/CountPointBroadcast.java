package com.huyd.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.huyd.findingfault.GameActivity;

/**
 * Author: huyd
 * Date: 2017-07-31
 * Time: 13:15
 * Describe:
 */
public class CountPointBroadcast extends BroadcastReceiver {

	private Message message;
	String flag = "";

	@Override
	public void onReceive(Context context, Intent intent) {

		flag = intent.getStringExtra("flag");

		//调用Message接口的方法
		message.getMsg(flag);


	}


	public interface Message {
		public void getMsg(String str);

		public void getError(String p);
	}

	public void setMessage(Message message) {
		this.message = message;
	}
}
