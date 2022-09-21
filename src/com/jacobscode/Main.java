package com.jacobscode;

public class Main {

	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}
	
	public void run(){
		
		GameLoop gl = new GameLoop();
		gl.start();
		
	}

}
