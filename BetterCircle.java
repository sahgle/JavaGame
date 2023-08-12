import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javafx.scene.shape.Circle;

public class BetterCircle extends Circle {
		private int row;
		private int colow;
		 int xDirection;
		 int yDirection;
  		private Color color;
		
	public BetterCircle(double centerX, double centerY, double radious,Color color) {
		super(centerX, centerY, radious);
		this.color = color;
		
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColow() {
		return colow;
	}

	public void setColow(int colow) {
		this.colow = colow;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	
	
	
	
	
}
