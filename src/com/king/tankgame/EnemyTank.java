package com.king.tankgame4;

import java.awt.Point;
import java.util.Vector;

public class EnemyTank extends Tank implements Runnable{
	//用来记录有多少个speed 也就是多少个阻塞间隔，从而确定多少时间发射一枚子弹。
	int count=0;
	//隔多少毫秒发射一次子弹。
	int time=1500;
	//线程终止标志位
	boolean isThreadDead = false;
	//
	boolean isThreadStarting = false;
	
	public EnemyTank(int x, int y,int direction) {
		
		super(x, y,direction);
		// TODO Auto-generated constructor stub
		isThreadStarting = true;
		speed = 5;
	}
	
	public void setEnemyTanks(Vector<EnemyTank> enemyTanks){
		this.enemyTanks = enemyTanks;
	}
	
	private int isTouchOtherEnemyTanks(){
		switch (this.direction) {
		case 0:
			for(int i=0;i<enemyTanks.size();i++){
				Tank enemyTank =  enemyTanks.get(i);
				if(enemyTank!=this){
					switch (enemyTank.direction) {
					case 0:
					case 1:
						if(x-10<=enemyTank.getX()+10&&x-10>=enemyTank.getX()-10&&y-15<=enemyTank.getY()+15&&y-15>=enemyTank.getY()-15)
							return 0;
						if(x+10<=enemyTank.getX()+10&&x+10>=enemyTank.getX()-10&&y-15<=enemyTank.getY()+15&&y-15>=enemyTank.getY()-15)
							return 0;
						break;
					case 2:
					case 3:
						if(x-10<=enemyTank.getX()+15&&x-10>=enemyTank.getX()-15&&y-15<=enemyTank.getY()+10&&y-15>=enemyTank.getY()-10)
							return 0;
						if(x+10<=enemyTank.getX()+15&&x+10>=enemyTank.getX()-15&&y-15<=enemyTank.getY()+10&&y-15>=enemyTank.getY()-10)
							return 0;
						break;

					}
				}
			}
			break;
		case 1:
			for(int i=0;i<enemyTanks.size();i++){
				Tank enemyTank =  enemyTanks.get(i);
				if(enemyTank!=this){
					switch (enemyTank.direction) {
					case 0:
					case 1:
						if(x-10<=enemyTank.getX()+10&&x-10>=enemyTank.getX()-10&&y+15>=enemyTank.getY()-15&&y+15<=enemyTank.getY()+15)
							return 1;
						if(x+10<=enemyTank.getX()+10&&x+10>=enemyTank.getX()-10&&y+15>=enemyTank.getY()-15&&y+15<=enemyTank.getY()+15)
							return 1;
						break;
					case 2:
					case 3:
						if(x-10<=enemyTank.getX()+15&&x-10>=enemyTank.getX()-15&&y+15>=enemyTank.getY()-10&&y+15<=enemyTank.getY()+10)
							return 1;
						if(x+10<=enemyTank.getX()+15&&x+10>=enemyTank.getX()-15&&y+15>=enemyTank.getY()-10&&y+15<=enemyTank.getY()+10)
							return 1;
						break;

					}
				}
			}
			break;
		case 2:
			for(int i=0;i<enemyTanks.size();i++){
				Tank enemyTank =  enemyTanks.get(i);
				if(enemyTank!=this){
					switch (enemyTank.direction) {
					case 0:
					case 1:
						if(x-15<=enemyTank.getX()+10&&x-15>=enemyTank.getX()-10&&y-10<=enemyTank.getY()+15&&y-10>=enemyTank.getY()-15)
							return 2;
						if(x-15<=enemyTank.getX()+10&&x-15>=enemyTank.getX()-10&&y+10<=enemyTank.getY()+15&&y+10>=enemyTank.getY()-15)
							return 2;
						break;
					case 2:
					case 3:
						if(x-15<=enemyTank.getX()+15&&x-15>=enemyTank.getX()-15&&y-10<=enemyTank.getY()+10&&y-10>=enemyTank.getY()-10)
							return 2;
						if(x-15<=enemyTank.getX()+15&&x-15>=enemyTank.getX()-15&&y+10<=enemyTank.getY()+10&&y+10>=enemyTank.getY()-10)
							return 2;
						break;

					}
				}
			}
			break;
		case 3:
			
			for(int i=0;i<enemyTanks.size();i++){
				Tank enemyTank =  enemyTanks.get(i);
				if(enemyTank!=this){
					switch (enemyTank.direction) {
					case 0:
					case 1:
						if(x-15>=enemyTank.getX()-10&&x-15<=enemyTank.getX()+10&&y-10<=enemyTank.getY()+15&&y-10>=enemyTank.getY()-15)
							return 3;
						if(x-15>=enemyTank.getX()-10&&x-15<=enemyTank.getX()+10&&y+10<=enemyTank.getY()+15&&y+10>=enemyTank.getY()-15)
							return 3;
						break;
					case 2:
					case 3:
						if(x-15>=enemyTank.getX()-15&&x-15<=enemyTank.getX()+15&&y-10<=enemyTank.getY()+10&&y-10>=enemyTank.getY()-10)
							return 3;
						if(x-15>=enemyTank.getX()-15&&x-15<=enemyTank.getX()+15&&y+10<=enemyTank.getY()+10&&y+10>=enemyTank.getY()-10)
							return 3;
						break;

					}
				}
			}
			break;
			
		
		}
		return -1;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		
		while(true){
			if(isThreadDead)break;
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			if(isThreadStarting){
				int k=0;
				//沿着方向走。
				switch (this.direction) {
				case 0:
					//向上自动走
					k=(int) (Math.random()*200);
					for(int i = 0;i<k;i++){
						if(isThreadStarting==false||y<=15||isTouchOtherEnemyTanks()==0)break;
							
						moveUp();
						count++;
						//每time秒射出一颗子弹
						if(isThreadStarting&&count%(speed*time/100)==0){
							count=0;
							this.shot();
						}
						try {
							if(speed!=0)Thread.sleep(100/speed);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
				case 1:
					//向下
					k=(int) (Math.random()*200);
					for(int i = 0;i<k;i++){
						if(isThreadStarting==false||y>=TankGame.HEIGHT-15||isTouchOtherEnemyTanks() == 1)break;				
						
						moveDown();
						count++;
						if(isThreadStarting&&count%(speed*time/100)==0){
							count=0;
							this.shot();
						}
						try {
							if(speed!=0)Thread.sleep(100/speed);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
				case 2:
					//向左
					k=(int) (Math.random()*200);
					for(int i = 0;i<k;i++){
						if(isThreadStarting==false||x<=15||isTouchOtherEnemyTanks()==2)break;
						moveLeft();
						
						count++;
						if(isThreadStarting&&count%(speed*time/100)==0){
							count=0;
							this.shot();
						}
						try {
							if(speed!=0)Thread.sleep(100/speed);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
				case 3:
					//向右
					k=(int) (Math.random()*200);
					for(int i = 0;i<k;i++){
						if(isThreadStarting==false||x>=TankGame.WIDTH-15||isTouchOtherEnemyTanks()==3)break;
						moveRight();
						count++;
						if(isThreadStarting&&count%(speed*time/100)==0){
							count=0;
							this.shot();
						}
						try {
							if(speed!=0)Thread.sleep(100/speed);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
				
				}
				if(isThreadStarting) this.direction = (int) (Math.random()*4);
				if(this.isAlive ==false){
					//坦克死后退出线程
					break;
				}
			}else{
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	

}
