package com.huyd.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Author: huyd
 * Date: 2017-08-02
 * Time: 13:43
 * Describe:
 */
public class SendRingPointBroadcast extends BroadcastReceiver {

	private PointMessage pointMessage;
	private String point = "";

	@Override
	public void onReceive(Context context, Intent intent) {
		point = intent.getStringExtra("point");
		pointMessage.getPoint(point);
	}

	public interface PointMessage {
		public void getPoint(String point);
	}

	public void setPoint(PointMessage pointMessage) {
		this.pointMessage = pointMessage;
	}
}
