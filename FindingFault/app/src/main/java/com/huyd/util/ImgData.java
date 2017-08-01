package com.huyd.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: huyd
 * Date: 2017-08-01
 * Time: 13:10
 * Describe:
 */
public class ImgData {
	public static List<String> list = new ArrayList<String>();

	static {
		list.add("2");
		list.add("3");
		list.add("4");
	}

	public static void removeList() {
		if (list.size() > 0) {
			list.remove(0);
		}
	}

	public static void renewList() {
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				list.remove(i);
			}
			list.add("2");
			list.add("3");
			list.add("4");
		}
	}
}
