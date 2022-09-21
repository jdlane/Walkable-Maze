package com.jacobscode;

import java.util.ArrayList;

public class Wall {

	Point point1, point2;
	
	public static ArrayList<Wall> walls = new ArrayList<Wall>();
	
	public Wall(Point point1, Point point2){
		this.point1 = point1;
		this.point2 = point2;
		walls.add(this);
	}
	
	public Wall(int x1, int y1, int x2, int y2){
		this.point1 = new Point(x1, y1);
		this.point2 = new Point(x2, y2);
		walls.add(this);
	}
}
