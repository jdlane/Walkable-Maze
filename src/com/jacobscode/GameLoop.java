package com.jacobscode;

import java.util.ArrayList;

public class GameLoop {
	
	private boolean running = true;
	private final int FPS = 100;
	
	public static Window window;
	
	public static final int MAZE_WIDTH = 20;
	public static final int MAZE_HEIGHT = 20;
	
	public static Player player;
	
	public static Tile[][] tiles = new Tile[MAZE_WIDTH][MAZE_HEIGHT];
	
	public void start(){
		
		window = new Window();
		
		initMaze();
		createMaze(tiles[(int)(Math.random()*MAZE_WIDTH)][(int)(Math.random()*MAZE_HEIGHT)], new ArrayList<Tile>());
		initWalls();
		
		window.init();
		
		player = new Player(Window.MAZE_OFFSET_X+Tile.TILE_WIDTH/2, Window.MAZE_OFFSET_Y+Tile.TILE_HEIGHT/2);
		
		loop();
		
	}
	
	public void loop(){
		
		long tickTime = 0;
		
		while(running){
			
			tickTime = System.currentTimeMillis();
			
			//game loop
			
			player.update();
			movement();
			window.draw();
			
			//keep FPS thru sleep
			tickTime = System.currentTimeMillis() - tickTime;
			
			long time = (1000/FPS) - tickTime;
			try{
				Thread.sleep(time);
			}catch(Exception e){}
		}
	}
	
	public void stop(){
		running = false;
	}
	
	private void initMaze(){
		
		for(int i = 0; i < MAZE_WIDTH; i++){
			for(int j = 0; j < MAZE_HEIGHT; j++){
				tiles[i][j] = new Tile(i, j);
			}
		}
		
	}
	
	private void createMaze(Tile tile, ArrayList<Tile> cache){
		
		ArrayList<Integer> unvisited = new ArrayList<Integer>();
		
		tile.visited = true;
		cache.add(tile);
		
		if(tile.row != 0 && !tiles[tile.row-1][tile.col].visited){
			unvisited.add(3);
		}
		if(tile.col != 0 && !tiles[tile.row][tile.col-1].visited){
			unvisited.add(0);
		}
		if(tile.row != MAZE_WIDTH-1 && !tiles[tile.row+1][tile.col].visited){
			unvisited.add(1);
		}
		if(tile.col != MAZE_HEIGHT-1 && !tiles[tile.row][tile.col+1].visited){
			unvisited.add(2);
		}
		
		if(unvisited.size() > 0){
			
			int nextDir = unvisited.get((int)(Math.random()*unvisited.size()));
			
			createMaze(tile.moveTo(nextDir), cache);
		}else{
			cache.remove(tile);
			while(cache.size() > 0){
				createMaze(cache.get(cache.size()-1), new ArrayList<Tile>());
				cache.remove(cache.size()-1);
			}
		}
		
	}
	
	private void initWalls(){
		Wall.walls.add(new Wall(Window.MAZE_OFFSET_X, Window.MAZE_OFFSET_Y, Window.MAZE_OFFSET_X+MAZE_WIDTH*Tile.TILE_WIDTH, Window.MAZE_OFFSET_Y));
		Wall.walls.add(new Wall(Window.MAZE_OFFSET_X, Window.MAZE_OFFSET_Y, Window.MAZE_OFFSET_X, Window.MAZE_OFFSET_Y+MAZE_HEIGHT*Tile.TILE_HEIGHT));
		Wall.walls.add(new Wall(Window.MAZE_OFFSET_X+MAZE_WIDTH*Tile.TILE_WIDTH, Window.MAZE_OFFSET_Y, Window.MAZE_OFFSET_X+MAZE_WIDTH*Tile.TILE_WIDTH, Window.MAZE_OFFSET_Y+MAZE_HEIGHT*Tile.TILE_HEIGHT - Tile.TILE_HEIGHT));
		Wall.walls.add(new Wall(Window.MAZE_OFFSET_X, Window.MAZE_OFFSET_Y+MAZE_HEIGHT*Tile.TILE_HEIGHT, Window.MAZE_OFFSET_X+MAZE_WIDTH*Tile.TILE_WIDTH - Tile.TILE_WIDTH, Window.MAZE_OFFSET_Y+MAZE_HEIGHT*Tile.TILE_HEIGHT));
		
		
		for(int i = 0; i < GameLoop.tiles.length; i++){
			for(int j = (i+1) % 2; j < GameLoop.tiles[i].length; j+=2){
				Tile t = tiles[i][j];
				if(i == MAZE_WIDTH-1 && j == MAZE_HEIGHT-1){
					t.walls[1] = false;
					t.walls[2] = false;
				}
				int tileX = Window.MAZE_OFFSET_X + i * Tile.TILE_WIDTH;
				int tileY = Window.MAZE_OFFSET_Y + j * Tile.TILE_HEIGHT;
				if(t.walls[0]) Wall.walls.add(new Wall(tileX, tileY, tileX + Tile.TILE_WIDTH, tileY));
				if(t.walls[1]) Wall.walls.add(new Wall(tileX + Tile.TILE_WIDTH, tileY, tileX + Tile.TILE_WIDTH, tileY + Tile.TILE_HEIGHT));
				if(t.walls[2]) Wall.walls.add(new Wall(tileX + Tile.TILE_WIDTH, tileY + Tile.TILE_HEIGHT, tileX, tileY + Tile.TILE_HEIGHT));
				if(t.walls[3]) Wall.walls.add(new Wall(tileX, tileY + Tile.TILE_HEIGHT, tileX, tileY));
			}
		}
	}

	private void movement(){
		
		Ray moveRay = new Ray(Player.lookDirection + Input.moveAmount * Math.PI/2);
		
		/*
		if(Input.moveAmount != 0){
			Ray moveRayX = new Ray(0);
			if(Math.cos(moveRay.angle)<0){
				moveRay.angle = Math.PI;
			}
			if(Math.cos(moveRay.angle) != 0){
				double dX = moveRayX.getCollisionDistanceIrrelative();
				if(dX == -1 || dX - Player.size/2 >= Math.abs(Player.moveSpeed * Math.cos(moveRay.angle))){
					player.position.x += Player.moveSpeed * Math.cos(moveRay.angle);
				}
			}
		
			Ray moveRayY = new Ray(Math.PI/2);
			if(Math.sin(moveRay.angle) < 0){
				moveRayY.angle = 3*Math.PI/2;
			}
			if(Math.sin(moveRay.angle) != 0){
				double dY = moveRayY.getCollisionDistanceIrrelative();
				if(dY == -1 || dY - (Player.size/2) >= Math.abs(Player.moveSpeed * Math.sin(moveRay.angle))){
					player.position.y += Player.moveSpeed * Math.sin(moveRay.angle);
				}
			}
		}
		*/
		
		//player.position.x += Player.moveSpeed * moveVectorX;
		//player.position.y += Player.moveSpeed * moveVectorY;
		
		if(Input.moveAmount != 0){
			double collisionDistance = moveRay.getCollisionDistanceIrrelative();
			if(collisionDistance == -1 || collisionDistance - Player.size/2 > Player.moveSpeed){
				player.position.y += Player.moveSpeed * Math.sin(moveRay.angle);
				player.position.x += Player.moveSpeed * Math.cos(moveRay.angle);
			}
		}
		
		Player.lookDirection += Input.turnAmount * Player.lookSpeed;
		Input.turnAmount = 0;
	}
}
