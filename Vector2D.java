
import java.awt.Color;
import java.util.ArrayList;

public class Vector2D {
    private double x;
    private double y;
    private double[] direction;
    private Color color;
    private double speed;
    private double radius;
 
    public Vector2D(double x, double y, double speed, double radius, Color color) {
        this.x = x;
        this.y = y;
        direction = new double[2];
        direction[0] = 0;
        direction[1] = 1;
        this.color = color;
        this.speed = speed;
        this.radius = radius;
    }
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void rotateDirection(double angleDegrees) {
        double angleRadians = Math.toRadians(angleDegrees);
        double cos = Math.cos(angleRadians);
        double sin = Math.sin(angleRadians);
        double newX = direction[0] * cos - direction[1] * sin;
        double newY = direction[0] * sin + direction[1] * cos;
        direction[0] = newX;
        direction[1] = newY;
    }
    
    public void vectorMove(Vector2D vector) {
        this.x += vector.getX();
        this.y += vector.getY();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDirectionX() {
        return direction[0];
    }

    public double getDirectionY() {
        return direction[1];
    }

    public void move(int width, int height) {
        double newX = x + direction[0] * speed;
        double newY = y + direction[1] * speed;

       
        
        x = x + direction[0] * speed;
        y = y + direction[1] * speed;


       
    }

	
	private double getRadius() {
		// TODO Auto-generated method stub
		return 20;
	}

	public void incSpeed() {
		speed++;
		
	}

	public boolean intersects(Vector2D other) {
	    double distance = Math.sqrt(Math.pow(other.x - this.x, 2) + Math.pow(other.y - this.y, 2));
	    return distance <= (this.radius + other.radius);
	}

	public void setX(double d) {
		this.x = d;
		
	}

	public void setY(double d) {
		this.y = d;
		
	}
}
