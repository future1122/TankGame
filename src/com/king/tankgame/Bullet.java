package com.king.tankgame4;


//子弹类
public class Bullet implements Runnable {
	//子弹横坐标
	int x;
	//子弹纵坐标
	int y;
	//子弹方向
	int direction;
	//子弹速度 线程阻塞的时间间隔
	int speed = 10;	//子弹是否死亡
	boolean isLive = true;
	//线程终止标志位。
	boolean isThreadDead = false;
	//以坦克的坐标来构造子弹的坐标。
	public Bullet(int x,int y,int direction) {
		// TODO Auto-generated constructor stub
		switch (direction) {
		case 0:
			this.x = x;
			this.y = y-15;
			break;

		case 1:
			this.x = x;
			this.y = y+15;
			break;
		case 2:
			this.x = x-15;
			this.y = y;
			break;
		case 3:
			this.x = x+15;
			this.y = y;
			break;
		}
		this.direction=direction;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
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
	
	
	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}
	// 子弹死亡
	public void dead(){
		this.isLive=false;
	}
	
	public boolean isTouch(Tank tank){
		if(tank==null)return false;
		int tankX = tank.getX();
		int tankY = tank.getY();
		switch (tank.direction) {
		case 0:
		case 1:
			//判断子弹与坦克相撞
			if(x>tankX-10&&x<tankX+10&&y>tankY-15&&y<tankY+15){
				this.dead();
				tank.dead();
				return true;
			}
				
				
			break;
		case 2:
		case 3:
			if(x>tankX-15&&x<tankX+15&&y>tankY-10&&y<tankY+10){
				this.dead();
				tank.dead();
				return true;
			}
			break;
		}
		
		return false;
	}
	
	public void run() {
		while(true){
				if(isThreadDead)break;
				if(speed==0){
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				for(int i=0;i<speed;i++){
					switch (this.direction) {
					case 0:
						this.y--;
						break;
					case 1:
						this.y++;
						break;
					case 2:
						this.x--;
						break;
					case 3:
						this.x++;
						break;
					}
					//如果子弹碰到边缘或者子弹死亡
					if(x<=0||x>=TankGame.WIDTH||y<=0||y>=TankGame.HEIGHT||isLive == false){
						this.isThreadDead = true;
						this.isLive = false;
						break;
					}
					try {
						if(speed!=0)Thread.sleep(100/speed);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}
	}
}
