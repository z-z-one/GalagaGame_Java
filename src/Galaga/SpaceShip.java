package Galaga;

import java.awt.Image;

import javax.swing.ImageIcon;

public class SpaceShip {
	//상수
	private int CANVAS_WIDTH;
	private int CANVAS_HEIGHT;
	ImageIcon ship ;
	Image img_ship ;
	int centerX;
	int centerY;
	boolean isHit;
	
	public SpaceShip(int cANVAS_WIDTH, int cANVAS_HEIGHT) {
		super();
		this.CANVAS_WIDTH = cANVAS_WIDTH;
		this.CANVAS_HEIGHT = cANVAS_HEIGHT;
		ship = new ImageIcon(GalagaFrame.class.getResource("/imgs/spaceShip.png"));
		img_ship = ship.getImage();
		centerX = this.CANVAS_WIDTH/2 - 30;
		centerY = this.CANVAS_HEIGHT - 150;
		this.isHit = false;
		
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	
}
