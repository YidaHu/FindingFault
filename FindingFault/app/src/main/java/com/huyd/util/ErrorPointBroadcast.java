package com.huyd.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Author: huyd
 * Date: 2017-08-02
 * Time: 10:59
 * Describe:
 */
public class ErrorPointBroadcast extends BroadcastReceiver {
	private Info info;
	String p = "";

	@Override
	public void onReceive(Context context, Intent intent) {
		p = intent.getStringExtra("error");
		info.getError(p);
	}

	public interface Info {

		public void getError(String p);
	}

	public void setInfo(Info info) {
		this.info = info;
	}
}
