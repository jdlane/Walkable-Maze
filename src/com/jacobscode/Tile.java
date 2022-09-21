package com.jacobscode;

import java.awt.Color;
import java.awt.Graphics;

public class Tile {
	
	boolean visited;
	int row, col;
	int x, y;
	
	boolean walls[];
	
	public static final int TILE_WIDTH = 15;
	public static final int TILE_HEIGHT = 15;

	public Tile(int row, int col){
		this.visited = false;
		this.row = row;
		this.col = col;
		this.walls = new boolean[]{true, true, true, true};
		this.x = row * TILE_WIDTH + Window.MAZE_OFFSET_X;
		this.y = col * TILE_HEIGHT + Window.MAZE_OFFSET_Y;
	}
	
	public Tile moveTo(int dir){
		
		this.walls[dir] = false;
		
		Tile nextTile = null; 
		if(dir == 0) nextTile = GameLoop.tiles[row][col-1];
		if(dir == 1) nextTile = GameLoop.tiles[row+1][col];
		if(dir == 2) nextTile = GameLoop.tiles[row][col+1];
		if(dir == 3) nextTile = GameLoop.tiles[row-1][col];
		
		nextTile.walls[(dir+2)%4] = false;
		
		return nextTile;
	}
	
	public void draw(Graphics bbg){
		bbg.setColor(Color.BLACK);
		if( walls[0] ) bbg.drawLine(x, y, x+TILE_WIDTH, y);
		if( walls[1] ) bbg.drawLine(x+TILE_WIDTH, y, x+TILE_WIDTH, y+TILE_WIDTH);
		if( walls[2] ) bbg.drawLine(x+TILE_WIDTH, y+TILE_WIDTH, x, y+TILE_WIDTH);
		if( walls[3] ) bbg.drawLine(x, y+TILE_WIDTH, x, y);
	}
	
}
