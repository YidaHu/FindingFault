package com.huyd.findingfault;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huyd.views.DrawRingImageView;

/**
 * Author: huyd
 * Date: 2017-07-31
 * Time: 16:32
 * Describe:
 */
public class RingFragment extends Fragment {

	private DrawRingImageView ring1, ring2;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.ring_fragment, container, false);
		ring1 = view.findViewById(R.id.ring1);
		ring2 = view.findViewById(R.id.ring2);
		return view;
	}


}
