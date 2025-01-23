package Galaga;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Alien {
		//상수
		private int CANVAS_WIDTH;
		ImageIcon ail ;
		Image img ;
		int centerX;
		int centerY;
		boolean isHit;
		boolean score;
		
		
		public Alien(int cANVAS_WIDTH) {
			super();
			CANVAS_WIDTH = cANVAS_WIDTH;
			ail = new ImageIcon(GalagaFrame.class.getResource("/imgs/alien.png"));
			img = ail.getImage();
			centerX = (int)(Math.random()*(CANVAS_WIDTH-120)+60);
			centerY = 30;
			this.isHit = false;
			this.score = false;
			
		}


		public boolean getisHit() {
			return isHit;
		}


		public void setHit(boolean isHit) {
			this.isHit = isHit;
		}


		public void setScore(boolean score) {
			this.score = score;
		}


		public boolean isScore() {
			return score;
		}
}
