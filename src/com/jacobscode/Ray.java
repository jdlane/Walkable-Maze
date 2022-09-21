package com.jacobscode;

public class Ray {
	
	double angle;
	double length;
	
	public Ray(double angle){
		this.angle = angle;
		this.length = -1;
	}
	
	public double getCollisionDistance(){
		
		Point playerPos = GameLoop.player.position;
		
		double distance = -1;
		
		double angle = this.angle + Player.lookDirection;
		
		double raySlope = Math.tan(angle);
		
		for(Wall wall: Wall.walls){
			double wallSlope = (wall.point1.y - wall.point2.y) / (wall.point1.x - wall.point2.x);
			
			//horizontal
			if(wall.point1.y == wall.point2.y){
				
				double collisionX = (wall.point1.y - playerPos.y + raySlope * playerPos.x) / raySlope;
				
				double minX = wall.point1.x <= wall.point2.x ? wall.point1.x : wall.point2.x;
				double maxX = wall.point1.x > wall.point2.x ? wall.point1.x : wall.point2.x;
				
				//check if collision is within wall bounds, and within ray bounds
				if(collisionX > minX && collisionX < maxX && Math.sin(angle) * (wall.point1.y - playerPos.y) > 0){
					
					double currentDistance = Point.getDistance(new Point(collisionX, wall.point1.y), new Point(playerPos.x, playerPos.y));
					if(currentDistance < distance || distance == -1){
						distance = currentDistance;
					}
				}
				
			//vertical
			}else if(wall.point1.y == wall.point1.y){
				
				double collisionY = raySlope * (wall.point1.x - playerPos.x) + playerPos.y;
				
				double minY = wall.point1.y <= wall.point2.y ? wall.point1.y : wall.point2.y;
				double maxY = wall.point1.y > wall.point2.y ? wall.point1.y : wall.point2.y;
				
				//check if collision is within wall bounds, and within ray bounds
				if(collisionY > minY && collisionY < maxY && Math.cos(angle) * (wall.point1.x - playerPos.x) > 0){
					
					
					double currentDistance = Point.getDistance(new Point(wall.point1.x, collisionY), new Point(playerPos.x, playerPos.y));
					if(currentDistance < distance || distance == -1){
						distance = currentDistance;
					}
				}
			//not vertical or horizontal
			}else if(raySlope != wallSlope){	
				double collisionX = (raySlope * playerPos.x - wallSlope * wall.point1.x + wall.point1.y - playerPos.y) / (raySlope - wallSlope);
				double collisionY = raySlope * ( collisionX - playerPos.x ) + playerPos.y;
				
				//check if correct direction
				if(Math.cos(angle) * (collisionX - playerPos.x) > 0 && Math.sin(angle) * (collisionY - playerPos.y) > 0){
					double currentDistance = Point.getDistance(new Point(collisionX, collisionY), playerPos);
					//if closest, replace collision
					if(currentDistance < distance || distance == -1){
						distance = currentDistance;
					}
				}
				
			//if parallel but intersect an infinite points	
			}else if((wall.point1.y - wallSlope * wall.point1.x) == (playerPos.y - raySlope*playerPos.x)){
				
			}
		}
		
		return distance;
	}
	
	public double getCollisionDistanceIrrelative(){
		
		Point playerPos = GameLoop.player.position;
		
		double distance = -1;
		
		double angle = this.angle;
		
		double raySlope = Math.tan(angle);
		
		for(Wall wall: Wall.walls){
			double wallSlope = (wall.point1.y - wall.point2.y) / (wall.point1.x - wall.point2.x);
			
			//horizontal
			if(wall.point1.y == wall.point2.y){
				
				double collisionX = (wall.point1.y - playerPos.y + raySlope * playerPos.x) / raySlope;
				
				double minX = wall.point1.x <= wall.point2.x ? wall.point1.x : wall.point2.x;
				double maxX = wall.point1.x > wall.point2.x ? wall.point1.x : wall.point2.x;
				
				//check if collision is within wall bounds, and within ray bounds
				if(collisionX > minX && collisionX < maxX && Math.sin(angle) * (wall.point1.y - playerPos.y) > 0){
					
					double currentDistance = Point.getDistance(new Point(collisionX, wall.point1.y), new Point(playerPos.x, playerPos.y));
					if(currentDistance < distance || distance == -1){
						distance = currentDistance;
					}
				}
				
			//vertical
			}else if(wall.point1.y == wall.point1.y){
				
				double collisionY = raySlope * (wall.point1.x - playerPos.x) + playerPos.y;
				
				double minY = wall.point1.y <= wall.point2.y ? wall.point1.y : wall.point2.y;
				double maxY = wall.point1.y > wall.point2.y ? wall.point1.y : wall.point2.y;
				
				//check if collision is within wall bounds, and within ray bounds
				if(collisionY > minY && collisionY < maxY && Math.cos(angle) * (wall.point1.x - playerPos.x) > 0){
					
					
					double currentDistance = Point.getDistance(new Point(wall.point1.x, collisionY), new Point(playerPos.x, playerPos.y));
					if(currentDistance < distance || distance == -1){
						distance = currentDistance;
					}
				}
			//not vertical or horizontal
			}else if(raySlope != wallSlope){	
				double collisionX = (raySlope * playerPos.x - wallSlope * wall.point1.x + wall.point1.y - playerPos.y) / (raySlope - wallSlope);
				double collisionY = raySlope * ( collisionX - playerPos.x ) + playerPos.y;
				
				//check if correct direction
				if(Math.cos(angle) * (collisionX - playerPos.x) > 0 && Math.sin(angle) * (collisionY - playerPos.y) > 0){
					double currentDistance = Point.getDistance(new Point(collisionX, collisionY), playerPos);
					//if closest, replace collision
					if(currentDistance < distance || distance == -1){
						distance = currentDistance;
					}
				}
				
			//if parallel but intersect an infinite points	
			}else if((wall.point1.y - wallSlope * wall.point1.x) == (playerPos.y - raySlope*playerPos.x)){
				
			}
		}
		
		return distance;
	}
}
