package com.jacobscode;

public class Point {

	double x, y;
	
	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public static double getDistance(Point p1, Point p2){
		return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
	}
}
