package Galaga;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.sql.Time;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


//프레임당 그려질 화면

public class GalagaPanel extends JPanel{
	
	private int CANVAS_WIDTH;
	private int CANVAS_HEIGHT;
	private SpaceShip ship = null;
	private int NUM=150;
	private Alien[] alien = new Alien[NUM];
	private Missile[] m = new Missile[NUM*10];
	//미사일 개수 떨어지면 게임오버 그동안 가능한 많은 적 없애야함 그걸로 점수 순위화..?
	
	private int times,times2;
	private int msNum;
	private int out;
	private int score;
	

	public GalagaPanel(int CANVAS_WIDTH,int CANVAS_HEIGHT) {
		this.CANVAS_WIDTH = CANVAS_WIDTH;
		this.CANVAS_HEIGHT = CANVAS_HEIGHT;
		this.out=0;
		this.times = 0;
		this.setSize(CANVAS_WIDTH,CANVAS_HEIGHT);
		this.setBackground(Color.black);
		
		this.ship = new SpaceShip(CANVAS_WIDTH, CANVAS_HEIGHT);
		
		for(int i=0;i<this.alien.length;i++) {
			this.alien[i]=new Alien(CANVAS_WIDTH);
		}
		for(int i=0;i<this.m.length;i++) {
			this.m[i]=new Missile(this.ship);
		}
	}		
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;	//그래픽 그리기 위해 지원하는 라이브러리
		
		if(out==0) {
			drawUI(g2d);
		}else {
			drawOut(g2d);
		}
	}
		
	public void drawOut(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("TimesRoman", Font.BOLD, 50));
		g2d.drawString("GAME OUT",CANVAS_WIDTH/2-150, CANVAS_HEIGHT/2-40);
		
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("TimesRoman", Font.BOLD, 30));
		int s = this.score;
		g2d.drawString("SCORE : "+s,CANVAS_WIDTH/2-145, CANVAS_HEIGHT/2+20);
		

	}
	
	private void drawUI(Graphics2D g2d) {
		//스코어 표시
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g2d.drawString("score : "+this.score,CANVAS_WIDTH/2 - 40, 20);
		
		//우주선이미지넣기
		g2d.drawImage(ship.img_ship,ship.centerX,ship.centerY,this);
		
		//외계인이미지넣기
		for(int i=0;i<times;i++) {	//times까지 해야 그전에 그렸던게 남아있음
			if(!alien[i].isHit) {
				g2d.drawImage(alien[i].img,alien[i].centerX,alien[i].centerY,this);	//
				if (i == NUM-3) {
					this.times = 0;
					System.out.println("clear");
				}
			}else {				//충돌여부가 참이면 그리는거 안함
				continue;
			}
			
		}
		
		//미사일이미지넣기
		for(int i=0;i<times2;i++) {	//times까지 해야 그전에 그렸던게 남아있음
			if((!m[i].isHit)&&(m[i].y>30)) {
				g2d.drawImage(m[i].img,m[i].x,m[i].y,this);	//
				this.msNum = i;				//발사되는 미사일 몇번째 미사일인지 알기 위해
				if (i == (NUM*1000-3)) {
					this.times2 = 0;
				}
			}else {		//충돌여부가 참이면 그리는거 안함
				continue;
			}
			
		}
	}
	

	public SpaceShip getShip() {
		return ship;
	}

	public Alien[] getAlien() {
		return alien;
	}
	
	public Missile[] getM() {
		return m;
	}


	public void setTimes() {
		this.times +=1;
	}


	public int getTimes() {
		return times;
	}
	
	public void setTimes2() {
		this.times2 +=1;
	}

	public int getTimes2() {
		return times2;
	}


	public int getMsNum() {
		return msNum;
	}


	public void setMsNum(int msNum) {
		this.msNum = msNum;
	}



	public void setHit(boolean hit , int num,int num2) {
		this.alien[num].isHit=hit;
		this.m[num2].isHit=hit;
	}

	public void setOut(int out) {
		this.out = out;
	}


	public void setScore(int score) {
		this.score = score;
	}


	public int getScore() {
		return score;
	}


	public int getOut() {
		return out;
	}
	
}
	

