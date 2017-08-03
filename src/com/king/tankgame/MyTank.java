package com.king.tankgame4;

import java.awt.Point;

//我的坦克
class MyTank extends Tank{
	//我的坦克最多发射的子弹个数。
	private final int bullet_num = 4;
	
	boolean isThreadDead = false;
	
	MyTankMoveThread []moveThreads = null;
	
	public MyTank(int x,int y,int direction){
		super(x, y,direction);
		speed = 10;
		moveThreads = new MyTankMoveThread[4];
		for(int i=0;i<4;i++){
			moveThreads[i] = new MyTankMoveThread(this);
			moveThreads[i].enemyTanks =enemyTanks;
			moveThreads[i].start();
		}
	}
	public int getBullet_num() {
		return bullet_num;
	}
	public boolean isThreadDead() {
		return isThreadDead;
	}
	public void setThreadDead(boolean isThreadDead) {
		this.isThreadDead = isThreadDead;
	}
	public void setOtherThreadWait(int i){
		switch (i) {
		case 0:
			moveThreads[1].myTankStop();
			moveThreads[2].myTankStop();
			moveThreads[3].myTankStop();
			break;

		case 1:
			moveThreads[0].myTankStop();
			moveThreads[2].myTankStop();
			moveThreads[3].myTankStop();
			break;
		case 2:
			moveThreads[0].myTankStop();
			moveThreads[1].myTankStop();
			moveThreads[3].myTankStop();
			break;
		case 3:
			moveThreads[0].myTankStop();
			moveThreads[1].myTankStop();
			moveThreads[2].myTankStop();
			break;
		}
	}
	
	
	
	
}
