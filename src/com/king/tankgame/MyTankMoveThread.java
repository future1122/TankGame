package com.king.tankgame4;

import java.util.Vector;

public class MyTankMoveThread extends Thread{
	MyTank myTank = null;
	
	//访问MyPanel里的敌人坦克。
	Vector<EnemyTank> enemyTanks = new Vector<EnemyTank>();
	
	//控制我的坦克的行动线程的开关。 
	boolean isStarting  = false;
	
	public MyTankMoveThread(MyTank myTank){
		super();
		this.myTank = myTank;
	}
	//我的坦克开始动
		public void myTankStart(){
			this.isStarting = true;
		}
		//我的坦克停。
		public void myTankStop(){
			this.isStarting = false;
		}
		
	//控制我的坦克行动的线程。
		public void run() {
			// TODO Auto-generated method stub
			while(true){
					
					if(this.isStarting&&myTank.speed!=0){
						switch (myTank.direction) {
						case 0:
							
								myTank.moveUp();
								try {
									if(myTank.speed!=0) Thread.sleep(100/myTank.speed);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							
							break;

						case 1:
							
								myTank.moveDown();
								try {
									if(myTank.speed!=0) Thread.sleep(100/myTank.speed);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							
							break;
						case 2:
							
								myTank.moveLeft();
								try {
									if(myTank.speed!=0) Thread.sleep(100/myTank.speed);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							
							break;
						case 3:
							
								myTank.moveRight();
								try {
									if(myTank.speed!=0) Thread.sleep(100/myTank.speed);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							
							break;
						}
						if(!myTank.isAlive()||myTank.isThreadDead())break;
					}else{
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(!myTank.isAlive()||myTank.isThreadDead())break;
			}
		}
}
