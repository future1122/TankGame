package com.king.tankgame4;

import java.awt.event.*;
import java.awt.*;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;

//我的画板
class MyPanel extends JPanel implements KeyListener,Runnable{
	//敌人的坦克数量
	int enemyNum = 8;
	//线程刷新的时间间隔
	int speed = 15;
	//判断是否游戏结束
	boolean isGameOver = false;
	//定义一个我的坦克
	MyTank myTank=null;
	//敌人的坦克
	Vector<EnemyTank> enemyTanks = new Vector<EnemyTank>();
	// 已经死掉的敌人坦克留下的子弹。
	Vector<Bullet> left_bullets = new Vector<Bullet>();
	//爆炸效果的图片。
	Image bombImages[] = null;
	//第一次画图片相当于加载
	boolean isFirst = true;
	//
	boolean isPause = false;

	//爆炸效果
	Vector<Bomb> bombs =new Vector<Bomb>();

	
	//构造函数
	public MyPanel(){
		//创建我的坦克
		myTank = new MyTank(200, 15,2);
		myTank.enemyTanks = enemyTanks;
		
		//创建敌人坦克
		for(int i=0;i<enemyNum;i++){
			EnemyTank etEnemyTank  = new EnemyTank(50*(i+1), 15,1);
			etEnemyTank.setEnemyTanks(enemyTanks);
			Thread etThread = new Thread(etEnemyTank);
			etThread.start();
			enemyTanks.add(etEnemyTank);
		}
		bombImages =new Image[3];
		for(int i=0;i<3;i++){
			Image image = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/image/bomb_"+(3-i)+".jpg"));
			bombImages[i] = image;
		}

		
	}
	
	
	//重新paint
	public void paint(Graphics g){
		super.paint(g);
		//背景黑色
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, TankGame.WIDTH, TankGame.HEIGHT);
		//	游戏结束提示胜利还是失败。
		if(isGameOver){
			g.setFont(new Font("Times New Roman", Font.PLAIN, 70));
			g.setColor(Color.blue);
			if(enemyTanks.size()==0)g.drawString("you win!", TankGame.WIDTH/2-100, TankGame.HEIGHT/2);
			else g.drawString("you lose!", TankGame.WIDTH/2-100, TankGame.HEIGHT/2);			
		}
				
		//画出我的坦克。
		if(myTank.isAlive) drawTank(myTank, g, 0);
		//画出我的子弹。
		g.setColor(Color.CYAN);
		for(int i =0;i<this.myTank.bullets.size();i++){
			if(myTank.bullets.get(i).isLive)
				g.draw3DRect(myTank.bullets.get(i).getX(), myTank.bullets.get(i).getY(), 1, 1, false);
		}
		
		//画出敌人的坦克
		for(int i=0;i<enemyTanks.size();i++){
			if(enemyTanks.get(i).isAlive)drawTank(enemyTanks.get(i), g, 1);
		}
		//画出爆炸效果
		this.drawBomb(g);
		
		//画出敌人的子弹。顺便判断敌方的子弹是否打到我。
		this.drawEnemyBullets(g);
		
		//画出敌人残留的子弹。 并判断是否打到我。
		this.drawEnemyLeftBullets(g);
		
		
	}
	private void drawEnemyLeftBullets(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.yellow);
		for(int i=0;i<left_bullets.size();i++){
			Bullet bullet = left_bullets.get(i);
			if(bullet.isLive())
				g.draw3DRect(bullet.getX(), bullet.getY(), 1, 1, false);
			if(bullet.isTouch(myTank)){
				Bomb bomb = new Bomb(myTank.getX()-13, myTank.getY()-13);
				bombs.add(bomb);
				//mytank为null 游戏结束。
				isGameOver = true;
				left_bullets.remove(i);
			}
		}
	}
	private void drawEnemyBullets(Graphics g) {
		// TODO Auto-generated method stub
		for(int i=0;i<enemyTanks.size();i++){
			for(int j=0;j<this.enemyTanks.get(i).bullets.size();j++){
				Bullet bullet = enemyTanks.get(i).bullets.get(j);
				if(bullet.isLive)					
					g.draw3DRect(bullet.getX(), bullet.getY(), 1, 1, false);
				if(bullet.isTouch(myTank)){
					Bomb bomb = new Bomb(myTank.getX()-13, myTank.getY()-13);
					bombs.add(bomb);
					//mytank为null 游戏结束。
					isGameOver = true;
					enemyTanks.get(i).bullets.remove(j);
					j--;				
				}
					
				
			}
		}
	}
	private void drawBomb(Graphics g) {
		// TODO Auto-generated method stub
		if(isFirst){
			isFirst = false;
			g.drawImage(bombImages[0], 10000, 10000, 30, 30, this);
			g.drawImage(bombImages[1], 10000, 20000, 30, 30, this);
			g.drawImage(bombImages[2], 10000, 20000, 30, 30, this);
			
		}
		for(int i=0;i<bombs.size();i++){
			Bomb bomb = bombs.get(i);
			
			if(bomb.life>6)g.drawImage(bombImages[0], bomb.x, bomb.y, 30, 30, this);
						
			else if (bomb.life>3)g.drawImage(bombImages[1], bomb.x, bomb.y, 30, 30, this);
			
			else g.drawImage(bombImages[2], bomb.x, bomb.y, 30, 30, this);
			
			
			bomb.lifeDown();
			if(bomb.life==0){
				bombs.remove(i);
				i--;
			}
		}
	}
	//画出坦克函数
	public void drawTank(Tank tank,Graphics g,int type){
		switch (type) {
		//type0 是自己的tank 
		case 0:
			g.setColor(Color.CYAN);
			break;
		//type1是敌方的坦克
		case 1:
			g.setColor(Color.yellow);
			break;
		}
		// 根据方向画坦克 0向上 1向下 2向左 3向右
		switch(tank.getDirection()){
		case 0:
			g.fill3DRect(tank.getX()-10, tank.getY()-15,5 , 30,false);
			g.fill3DRect(tank.getX()+5,tank.getY()-15,5,30,false);
			g.fill3DRect(tank.getX()-5, tank.getY()-10, 10, 20,false);
			g.drawLine(tank.getX(), tank.getY(), tank.getX(), tank.getY()-15);
			break;
		case 1:
			g.fill3DRect(tank.getX()-10, tank.getY()-15,5 , 30,false);
			g.fill3DRect(tank.getX()+5,tank.getY()-15,5,30,false);
			g.fill3DRect(tank.getX()-5, tank.getY()-10, 10, 20,false);
			g.drawLine(tank.getX(), tank.getY(), tank.getX(), tank.getY()+15);
			break;
		case 2:
			g.fill3DRect(tank.getX()-15, tank.getY()-10,30 , 5,false);
			g.fill3DRect(tank.getX()-15,tank.getY()+5,30,5,false);
			g.fill3DRect(tank.getX()-10, tank.getY()-5, 20, 10,false);
			g.drawLine(tank.getX(), tank.getY(), tank.getX()-15, tank.getY());
			break;
		case 3:
			g.fill3DRect(tank.getX()-15, tank.getY()-10,30 , 5,false);
			g.fill3DRect(tank.getX()-15,tank.getY()+5,30,5,false);
			g.fill3DRect(tank.getX()-10, tank.getY()-5, 20, 10,false);
			g.drawLine(tank.getX(), tank.getY(), tank.getX()+15, tank.getY());
			break;
		}
	}
	// 键被按下的处理 a表示向左 s表示向下 d表示向右 w表示向上 
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(isPause ==false){
			switch(arg0.getKeyCode()){
			case KeyEvent.VK_W:
				myTank.setDirection(0);
					myTank.moveThreads[0].myTankStart();
				myTank.setOtherThreadWait(0);
				
				break;
			case KeyEvent.VK_S:
				this.myTank.setDirection(1);
					myTank.moveThreads[1].myTankStart();
				myTank.setOtherThreadWait(1);
				break;
			case KeyEvent.VK_A:
				this.myTank.setDirection(2);
					myTank.moveThreads[2].myTankStart();
				myTank.setOtherThreadWait(2);
				break;
			case KeyEvent.VK_D:
				this.myTank.setDirection(3);
					myTank.moveThreads[3].myTankStart();
				myTank.setOtherThreadWait(3);
				break;
			case KeyEvent.VK_J:
				if(myTank.bullets.size()<myTank.getBullet_num())this.myTank.shot();
				break;
		}
		}
	}
	//键被释放
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(isPause==false){
			switch(arg0.getKeyCode()){
			case KeyEvent.VK_W:
				myTank.moveThreads[0].myTankStop();
				break;
			case KeyEvent.VK_S:
				myTank.moveThreads[1].myTankStop();
				break;
			case KeyEvent.VK_A:
				myTank.moveThreads[2].myTankStop();
				break;
			case KeyEvent.VK_D:
				myTank.moveThreads[3].myTankStop();
				break;
			}
		}
		
	}
	//键 的一个值被输出
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub		
	}
	
	
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//判断我的子弹是否打到敌人
			this.myBulletHitEnemy();
			//将越界的我的子弹以及敌人的子弹移除
			this.removeBullet();
			
			this.repaint();
			//游戏结束
			this.gameOver();
			//如果游戏结束线程终止
			if(isGameOver&&bombs.size()==0)break;
		}
	}
	//游戏结束。
	private void gameOver(){
		if(isGameOver&&bombs.size()==0){
			myTank.setThreadDead(true); 
			for(int i=0;i<enemyTanks.size();i++) {
				EnemyTank enemyTank = enemyTanks.get(i);
				enemyTank.isThreadDead = true;
				for(int j=0;j<enemyTank.bullets.size();j++){
					enemyTank.bullets.get(j).isThreadDead = true;
				}
			}
			
			for(int i=0;i<left_bullets.size();i++){
				left_bullets.get(i).isThreadDead = true;
			}
		}
	}
	
	private void removeBullet() {
		// TODO Auto-generated method stub
		//我的子弹从集合中移除
		for(int i=0;i<myTank.bullets.size();i++){
			if(!myTank.bullets.get(i).isLive()){
				myTank.bullets.remove(i);
				i--;
			}
		}
		// 将死亡的保留子弹从集合中移除。
		for(int i=0;i<left_bullets.size();i++){
			if(!left_bullets.get(i).isLive()){
				left_bullets.remove(i);
				i--;
			}
		}
	}
	//我的子弹击中敌方。
	private void myBulletHitEnemy() {
		// TODO Auto-generated method stub
		for(int i=0;i<enemyTanks.size();i++){
			for(int j=0;j<this.myTank.bullets.size();j++){
				if(i>-1&& myTank.bullets.get(j).isLive()&&myTank.bullets.get(j).isTouch(enemyTanks.get(i))){
					//保留死亡的坦克射出的子弹。
					for(int k=0;k<enemyTanks.get(i).bullets.size();k++){
						left_bullets.add(enemyTanks.get(i).bullets.get(k));
					}
					//将已经击中敌人的子弹从集合中移除
					myTank.bullets.remove(j);
					// 将已经死了的敌方坦克从集合中移除
					Bomb bomb = new Bomb(enemyTanks.get(i).getX()-13, enemyTanks.get(i).getY()-13);
					
					bombs.add(bomb);
					enemyTanks.remove(i);
					if(enemyTanks.size()==0) {
						isGameOver =true;
						break;
					}
					i--;
					j--;
				}
			}
		}
	}
}
