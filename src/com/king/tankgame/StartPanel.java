package com.king.tankgame4;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class StartPanel extends JPanel implements Runnable{
	
	int time = 0;
	boolean isThreadDead = false;
	@Override
	public void paint(Graphics arg0) {
		// TODO Auto-generated method stub
		super.paint(arg0);
		arg0.setColor(Color.black);
		arg0.fillRect(0, 0, TankGame.WIDTH, TankGame.HEIGHT);
		if(time%2==0){
			Font font = new Font("华文新魏", Font.BOLD, 30);
			arg0.setFont(font);
			arg0.setColor(Color.yellow);
			arg0.drawString("Stage 1", TankGame.WIDTH/2, TankGame.HEIGHT/2);
			time = 0;
		}
	}

	public void run() {
		// TODO Auto-generated method stub
		while(true){
			if(isThreadDead)break;
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO: handle exception
				e.getStackTrace();
			}
			time++;
			repaint();
		}
	}
}
