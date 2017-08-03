package com.king.tankgame4;

import java.awt.Point;
import java.util.Vector;

//坦克类
class Tank{
	//坦克横坐标
	int x=0;
	//坦克纵坐标
	int y=0;
	//坦克的方向  0表示向上 1表示向下 2表示向左 3表示向右
	int direction = 0;
	
	//线程的阻塞时间间隔。
	int speed = 1;
	//子弹
	Vector<Bullet> bullets ;
	
	//访问MyPanel里的敌人坦克。
	Vector<EnemyTank> enemyTanks = new Vector<EnemyTank>();
	
	//坦克是否存活
	boolean isAlive = true;

	public Tank(int x,int y,int direction){
		bullets = new Vector<Bullet>();
		this.x=x;
		this.y=y;
		this.direction=direction;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setLocation(int x,int y) {
		this.x = x;
		this.y = y;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public boolean isAlive() {
		return isAlive;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public void shot(){
		//创建一颗子弹
		Bullet bullet = new Bullet(x, y, direction);		
		//启动子弹线程
		bullets.add(bullet);
		Thread t = new Thread(bullet);
		t.start();		
		
	}
	//坦克死亡
	public void dead(){
		this.isAlive=false;
	}
	
	//坦克向上动
	public void moveUp(){
		
			
				if(y>15)y--;
			
		
	}
	
	//坦克向下动
	public void moveDown(){
		
			
				if(y<TankGame.HEIGHT-15)y++;
			
	}
	
	//坦克向左动
	public void moveLeft(){
		
			if(x>15)x--;
		
	}
	
	//坦克向右动
	public void moveRight(){
		
		
			if(x<TankGame.WIDTH-15)x++;
		
	}
	
	
}