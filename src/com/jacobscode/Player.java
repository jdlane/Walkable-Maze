package com.jacobscode;

import java.awt.Color;
import java.awt.Graphics;

public class Player {
	
	Point position;
	
	public static final double RAY_DENSITY = 400;
	public static final double FOV = Math.PI / 2;

	public Ray[] rays;
	public static double lookDirection = 0;
	public static double moveSpeed = .4;
	public static double lookSpeed = .03;
	
	public static final int WALL_HEIGHT = 9000;
	
	public static double size = 5;
	
	public Player(int x, int y){
		this.position = new Point(x, y);
		initRays();
	}

	public void draw(Graphics bbg){
		bbg.setColor(Color.BLUE);
		bbg.drawOval((int)(position.x - size/2), (int)(position.y - size/2), (int)size, (int)size);
		
		bbg.setColor(Color.GREEN);
		for(Ray r: rays){
			if(r.length == -1){
				//bbg.drawLine((int)position.x, (int)position.y, (int)(position.x + 100*Math.cos(r.angle + lookDirection)), (int)(position.y + 100*Math.sin(r.angle + lookDirection)));
			}else{
				bbg.drawLine((int)position.x, (int)position.y, (int)(position.x + r.length*Math.cos(r.angle + lookDirection)), (int)(position.y + r.length*Math.sin(r.angle+lookDirection)));
			}
		}
	}
	
	public void drawFirstPerson(Graphics bbg){
		for(int i = 0; i < rays.length; i++){
			double distance = rays[i].getCollisionDistance();
			
			if(distance != -1){
				double adjustedDistance = distance * Math.cos(rays[i].angle);
				int boxHeight = (int)(WALL_HEIGHT/adjustedDistance/FOV);
				int shade = (int)(255/distance* 10);
				int boxOffset = Input.lookUpAmount;
				shade = shade > 255 ? 255 : shade;
				bbg.setColor(new Color(shade, shade, shade));
				bbg.fillRect((int)(i * (Window.WINDOW_WIDTH) / RAY_DENSITY / FOV - 2), (int)(Window.WINDOW_HEIGHT/2 - boxHeight/2) + boxOffset, (int)(Window.WINDOW_WIDTH / RAY_DENSITY / FOV) + 2, boxHeight);
			}
		}
	}
	
	public void update(){
		for(Ray r: rays){
			r.length = r.getCollisionDistance();
		}
	}
	
	public void initRays(){
		rays = new Ray[(int) (RAY_DENSITY * FOV)];
		for(int i = 0; i < (int) (FOV * RAY_DENSITY); i++){
			rays[i] = new Ray( -FOV/2 + i / RAY_DENSITY);
		}
	}
}
