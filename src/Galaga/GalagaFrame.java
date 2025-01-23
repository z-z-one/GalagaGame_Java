package Galaga;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

import javax.swing.*;

// 게임은 움직이는 영상을 출력하는 것이기 때문에 ms시간 단위의 처리로 처리해야함
// 프레임당 바뀔 정보들
// 전체적인 틀

public class GalagaFrame extends JFrame{
	//캔버스 크기
	static int CANVAS_WIDTH = 600 ;	
	static int CANVAS_HEIGHT = 800;
	
	private long n,n2;
	
	private GalagaPanel myPanel = null;
	private Timer timer = null;
	private SpaceShip ship= null;
	private Alien[] alien= null;
	private Missile[] m = null;
	private int shipXTarget ;
	
	public GalagaFrame(String title) {
		super(title);
		this.n=0;
		this.n2=0;
		this.setVisible(true);
		this.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
		this.setLocationRelativeTo(null); //setLocation(400,600);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 	//윈도우 제대로 종료를 위한
		
		myPanel = new GalagaPanel(CANVAS_WIDTH,CANVAS_HEIGHT);
		this.add("Center",myPanel);
		
		this.ship = myPanel.getShip();
		this.alien = myPanel.getAlien();
		this.shipXTarget = ship.centerX;						//우주선의 중심x좌표
		this.m = myPanel.getM();
		
		setKeyListener(); //여기서 좌표변경해주고 타이머에서 유아이 적용
		startTimer();	//타이머에서 시간마다 그림이 그려지도록 해야함
	}
	
	public void setKeyListener() {			//키보드 입력받는 메소드
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) { //키보드 입력 e에 들어옴
				if(e.getKeyCode() == KeyEvent.VK_LEFT) {
					shipXTarget -=10;
					if(ship.centerX<shipXTarget) {
						shipXTarget = ship.centerX;
					}
					
				}else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
					shipXTarget +=10;
					if(ship.centerX>shipXTarget) {
						shipXTarget = ship.centerX;
					}
				}
			}
			
		});
	}
	
	public void startTimer(){
		timer = new Timer(20,new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				movement();	//움직임을 처리하는 메서드
				checkCollision();
				n += 1;
				n2 +=1;
				if(n>15) {
					myPanel.setTimes();
					n=0;
				}
				if(n2>5) {
					myPanel.setTimes2();
					n2=0;
				}
				myPanel.repaint();//다시그리기 - 시간마다 프레임다시그림
			}
		});
		timer.start();
		
	}
	
	public void movement() {	
		//우주선 움직임
		if(ship.centerX<shipXTarget) {
			ship.centerX += 10;
			
		}else if(ship.centerX>shipXTarget) {		//왼쪽으로
			ship.centerX -= 10;
		} 
		//우주선의 x좌표화 미사일의 x좌표 동기화
		m[myPanel.getMsNum()].x = ship.centerX+30;

		//외계인들 아래로
		for(int i=0;i<myPanel.getTimes();i++) {			// 새로 생긴 애들까지만 y가 변해야하기떄문에
			alien[i].centerY +=4;						// 배열의 길이로 for문돌리면 배열내 모든 외계인 y좌표변해서 새로 태어나는 외계인들이 아래에서 태어남
		}
		//미사일 위로
		for(int i=0;i<myPanel.getTimes2();i++) {			// 새로 생긴 애들까지만 y가 변해야하기떄문에
			m[i].y -=4;						// 배열의 길이로 for문돌리면 배열내 모든 외계인 y좌표변해서 새로 태어나는 외계인들이 아래에서 태어남
		}
	}
	
	public void checkCollision() {
		// 미사일과 외계인의 충돌처리
		for(int i = 0; i<m.length;i++) {
			for(int j=0;j<alien.length;j++) {
				if (m[i].isHit|| alien[j].isHit) {			//이미 충돌한 미사일과 외계인일경우 충돌감지 더이상 안하기 위함
					continue;
				}else {										// 아직 충돌 안한 미사일과 외계인
					if(dupRect(new Rectangle(m[i].x,m[i].y,3,15),
						new Rectangle(alien[j].centerX,alien[j].centerY,45,40))
							&&(this.alien[j].centerY>50)) {			//영역이 겹치는지 검사 , 화면 영역 밖에서 겹치는 경우 제외시키기위함
						myPanel.setHit(true,j,i);
						if((!this.alien[j].isScore())) {
							this.alien[j].setScore(true);
							if(myPanel.getOut()!=1) {			//게임아웃이 아닐경우
								myPanel.setScore(myPanel.getScore()+10);
							}else {continue;}		//객체가 모두 존재는 하기때문에 아웃여부로 점수계산
							
						}
						this.alien[j].isHit = true;
						this.m[i].isHit = true;
					}					
				}
				if(!this.alien[j].isHit) {
					// 우주선과 외계인의 충돌
					int x[]= {ship.getCenterX(),ship.getCenterX()+((int)ship.ship.getIconWidth()/2),
							ship.getCenterX()+ship.ship.getIconWidth()};
					int y[]= {ship.getCenterY()+(ship.ship.getIconHeight())-10,ship.getCenterY()-10,
							ship.getCenterY()+(ship.ship.getIconHeight())-10};
					Polygon shipArea = new Polygon(x, y, 3); 
					Rectangle alienArea = new Rectangle(alien[j].centerX+5 ,alien[j].centerY+7 , 
							(int)(alien[j].ail.getIconWidth()*0.8), (int)(alien[j].ail.getIconHeight()*0.7));
					if( dupRect2(shipArea,alienArea) ) {			//영역이 겹치는지 검사
						myPanel.setOut(1);				//게임아웃으로 그리는 화면전환
					}
				}
			}
		}
	}
	
	public boolean dupRect(Rectangle rect1,Rectangle rect2) {
		return rect1.intersects(rect2); //받은 두개가 영역이 겹치는지 체크
	}
	public boolean dupRect2(Polygon pol,Rectangle2D rec ) {
		return pol.intersects(rec);
	}


	
	
}
