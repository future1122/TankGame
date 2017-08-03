package com.king.tankgame4;
/*
 * 坦克游戏
 * 1.我的坦克可以上下移动
 * 2.我的坦克可以发射子弹，最多发射4颗
 * 3.我的坦克击中敌方时，敌方消失会爆炸。
 * 待实现功能：1.坦克不能重叠。
 * 			 2.我的坦克有多条命
 * 			 3.有地形
 * 			 4.有不同的关卡
 * 			 5.有不同的敌方坦克。
 * 			 6.能记录分数。
 * 			 7.实现存盘。（io编程）
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import com.sun.org.apache.xml.internal.security.Init;

public class TankGame extends JFrame implements ActionListener{
	public static final int HEIGHT = 600;
	public static final int WIDTH = 800;
	
	//判断画板是否已经添加到主界面上
	boolean isMyPanelExists = false;
	
	MyPanel mp =null;
	
	StartPanel startPanel =null;
	
	JMenuBar menuBar;
	//游戏是否开始
	boolean isGameStarting = false;
	//是否暂停
	boolean isPause = false;
	
	boolean isStartPanelExists =true;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TankGame tk = new TankGame(); 
	}
	
	public TankGame(){
		InitMenuBar();
		startPanel = new StartPanel();
		Thread thread = new Thread(startPanel);
		thread.start();
		this.add(startPanel);
		this.setTitle("Tank Game4");
		this.setSize(900,900);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private void createMyPanel() {
		// TODO Auto-generated method stub
		startPanel.isThreadDead=true;
		isGameStarting = true;
		if(isStartPanelExists){
			this.remove(startPanel);
			isStartPanelExists =false;
		}
		if(isMyPanelExists){
			this.remove(mp);
			isMyPanelExists =false;
		}
		mp = new MyPanel();
		Thread mpThread = new Thread(mp);
		mpThread.start();
		this.add(mp);
		isMyPanelExists =true;
		this.addKeyListener(mp);
		
	}

	private void InitMenuBar() {
		// TODO Auto-generated method stub
		menuBar = new JMenuBar();
		JMenu menu = new JMenu("选项(O)");
		
		JMenuItem newGameItem = new JMenuItem("开始新游戏!");
		JMenuItem pauseItem = new JMenuItem("开始/暂停");
				
		menu.add(newGameItem);
		menu.add(pauseItem);
		
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
		
		newGameItem.addActionListener(this);
		newGameItem.setActionCommand("newGame");
		pauseItem.addActionListener(this);
		pauseItem.setActionCommand("pause");
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		//按了开始新游戏，就创建一个新的画板。
		if(arg0.getActionCommand().equals("newGame")){
			this.createMyPanel();
			this.setVisible(true);
		}//按了开始暂停键，如果游戏没开始则开始新游戏，如果游戏开始了就暂停，如果已经暂停就恢复游戏。
		else if(arg0.getActionCommand().equals("pause")){
			//如果游戏开始了
			if(isGameStarting){
				//没有暂停就暂停游戏。
				if(isPause==false){
					mp.myTank.speed =0;
					for(int i=0;i<mp.enemyTanks.size();i++){
						EnemyTank enemyTank = mp.enemyTanks.get(i);
						enemyTank.speed = 0;
						enemyTank.isThreadStarting = false;
						for(int j=0;j<enemyTank.bullets.size();j++){
							Bullet bullet = enemyTank.bullets.get(j);
							bullet.speed = 0;
						}
					}
					for(int i=0;i<mp.left_bullets.size();i++){
						Bullet bullet = mp.left_bullets.get(i);
						bullet.speed = 0;
					}
					isPause = true;
					mp.isPause = true;
				}else{
					//暂停了就开始游戏。
					mp.myTank.speed = 10;
					for(int i=0;i<mp.enemyTanks.size();i++){
						EnemyTank enemyTank = mp.enemyTanks.get(i);
						enemyTank.speed = 5;
						enemyTank.isThreadStarting =true;
						for(int j=0;j<enemyTank.bullets.size();j++){
							Bullet bullet = enemyTank.bullets.get(j);
							bullet.speed = 10;
						}
					}
					for(int i=0;i<mp.left_bullets.size();i++){
						Bullet bullet = mp.left_bullets.get(i);
						bullet.speed = 2;
					}
					isPause = false;
					mp.isPause = false;
				}
			}else{
				this.createMyPanel();
				this.setVisible(true);
			}
		}
	}
}