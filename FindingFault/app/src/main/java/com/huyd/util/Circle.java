package com.huyd.util;

/**
 * Author: huyd
 * Date: 2017-07-31
 * Time: 11:13
 * Describe:判断是否在圆内
 */
public class Circle {
	/**
	 * 圆心坐标
	 */
	protected Point center;

	/**
	 * 圆半径
	 */
	protected double radius;

	public Circle(Point center, double radius) {
		this.center = new Point(center.x, center.y);
		this.radius = radius;
	}

	/**
	 * 判断p是否在圆内
	 *
	 * @param p 待判断点
	 * @return 是否在圆内
	 */
	public boolean isInner(Point p) {
		double dist = 0;

		dist = Math.sqrt((p.x - center.x) * (p.x - center.x) + (p.y - center.y) * (p.y - center.y));

		return (dist <= radius ? true : false);
	}
}