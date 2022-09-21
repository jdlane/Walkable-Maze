package com.jacobscode;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Input implements KeyListener, MouseMotionListener{
	
	public static int turnAmount = 0;
	public static int lookUpAmount = 0;
	public static int moveAmount = 0;
	
	private static final double sensitivity = .5;

	private static Robot robot;
	
	public static void initInput(){
		try{
			robot = new Robot();
		}catch(Exception e){}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int kc = e.getKeyCode();
		if(kc == KeyEvent.VK_LEFT){
			moveAmount = 3;
		}
		if(kc == KeyEvent.VK_RIGHT){
			moveAmount = 1;
		}
		if(kc == KeyEvent.VK_UP){
			moveAmount = 4;
		}
		if(kc == KeyEvent.VK_DOWN){
			moveAmount = 2;
		}
		if(kc == KeyEvent.VK_M){
			Window.minimap = !Window.minimap;
		}
		if(kc == KeyEvent.VK_ESCAPE){
			Window.moveMouse = !Window.moveMouse;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int kc = e.getKeyCode();
		if(kc == KeyEvent.VK_LEFT){
			moveAmount = 0;
		}
		if(kc == KeyEvent.VK_RIGHT){
			moveAmount = 0;
		}
		if(kc == KeyEvent.VK_UP){
			moveAmount = 0;
		}
		if(kc == KeyEvent.VK_DOWN){
			moveAmount = 0;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		if(Window.moveMouse){
			if(e.getX() > Window.WINDOW_WIDTH/2){
				turnAmount = (int)(sensitivity*(Math.sqrt(e.getX()-Window.WINDOW_WIDTH/2)+1));
			}
			if(e.getX() < Window.WINDOW_WIDTH/2){
				turnAmount = (int)(-sensitivity*(Math.sqrt(Window.WINDOW_WIDTH/2-e.getX())+1));
			}
			if(e.getY() > Window.WINDOW_HEIGHT/2){
				//lookUpAmount += (int)(-4*sensitivity*(Math.sqrt(e.getY()-Window.WINDOW_HEIGHT/2)+1));
			}
			if(e.getY() < Window.WINDOW_HEIGHT/2){
				//lookUpAmount += (int)(4*sensitivity*(Math.sqrt(Window.WINDOW_HEIGHT/2-e.getY())+1));
			}
			
			robot.mouseMove(GameLoop.window.getLocationOnScreen().x+Window.WINDOW_WIDTH/2, GameLoop.window.getLocationOnScreen().y+Window.WINDOW_HEIGHT/2);
		}
	}

}
