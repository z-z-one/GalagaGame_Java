package Galaga;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Missile {

	SpaceShip ship = null;
	int x;
	int y;
	ImageIcon m ;
	Image img ;
	boolean isHit;

	public Missile(SpaceShip ship) {
		super();
		this.ship = ship;
		this.y=ship.centerY-20;
		this.x=-20;
		m = new ImageIcon(GalagaFrame.class.getResource("/imgs/missile.png"));
		img = m.getImage();
		this.isHit = false;
	}


	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public boolean getisHit() {
		return isHit;
	}


	public void setHit(boolean isHit) {
		this.isHit = isHit;
	}
	
	
	
	
}
