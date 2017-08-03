package com.king.tankgame4;

public class Bomb {
	int x;
	int y;
	int life=9;;
	
	public Bomb(int x,int y) {
		// TODO Auto-generated constructor stub
		this.x=x;
		this.y=y;
	}
	
	public void lifeDown(){
		life--;
	}
}
