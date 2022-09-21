package com.jacobscode;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Window extends JFrame{

	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	
	public static final int MAZE_OFFSET_X = 20;
	public static final int MAZE_OFFSET_Y = 30;
	
	private BufferedImage backBuffer;
	private Insets insets;
	
	public static boolean firstPerson = true;
	public static boolean minimap = true;
	
	public static boolean moveMouse = true;
	
	public void init(){
		
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setTitle("Maze Game");
		
		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new java.awt.Point(0, 0), "blank cursor");

		// Set the blank cursor to the JFrame.
		getContentPane().setCursor(blankCursor);
		
		Input input = new Input();
		Input.initInput();
		addKeyListener(input);
		addMouseMotionListener(input);
		
		backBuffer = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		insets = getInsets();
	}
	
	public void draw(){
		Graphics g = getGraphics();
		Graphics bbg = backBuffer.getGraphics();
		if(!firstPerson){
			//bbg.setColor(Color.WHITE);
			//bbg.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		
			bbg.setColor(Color.GRAY);
			bbg.fillRect(MAZE_OFFSET_X, MAZE_OFFSET_Y, Tile.TILE_WIDTH*GameLoop.MAZE_WIDTH, Tile.TILE_HEIGHT*GameLoop.MAZE_HEIGHT);
		
		/*for(int i = 0; i < GameLoop.tiles.length; i++){
			for(int j = i % 2; j < GameLoop.tiles[i].length; j+=2){
				Tile tile = GameLoop.tiles[i][j];
				tile.draw(bbg);
			}
		}*/
		
			for(Wall w: Wall.walls){
				bbg.setColor(Color.BLACK);
				bbg.drawLine((int)w.point1.x, (int)w.point1.y, (int)w.point2.x, (int)w.point2.y);
			}
		
			GameLoop.player.draw(bbg);
		}else{
			bbg.setColor(Color.BLACK);
			bbg.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
			
			GameLoop.player.drawFirstPerson(bbg);
			
			if(minimap){
				bbg.setColor(Color.GRAY);
				bbg.fillRect(MAZE_OFFSET_X, MAZE_OFFSET_Y, Tile.TILE_WIDTH*GameLoop.MAZE_WIDTH, Tile.TILE_HEIGHT*GameLoop.MAZE_HEIGHT);
			
				for(Wall w: Wall.walls){
					bbg.setColor(Color.BLACK);
					bbg.drawLine((int)w.point1.x, (int)w.point1.y, (int)w.point2.x, (int)w.point2.y);
				}
			
				GameLoop.player.draw(bbg);
			}
		}
		
		g.drawImage(backBuffer, insets.left, insets.right, WINDOW_WIDTH, WINDOW_HEIGHT, null);
	}
	
}
