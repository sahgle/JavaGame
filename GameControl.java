import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameControl extends JPanel {
    private int secondsOpened;

    private Player p1;
    private int cameraX;
    private int cameraY;
    private static final int CAMERA_SPEED = 8;
    private Sprite[][] map;
    private ArrayList<BetterCircle> circles;
    private Enemy enemy;
    private ArrayList<Vector2D> projectiles;
    private ArrayList<Vector2D> colected;
    private BetterCircle circle;

	private boolean circleMove;
	private int time;
	private boolean powerUp;
	private boolean menu;


    public GameControl() {
        map = new Sprite[20][20];
        circleMove = false;
        menu = false;
        time = 0;
        circles = new ArrayList<>();
        colected = new ArrayList<>();
        circle = null;
        Random random = new Random();
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                Sprite sprite = new Sprite(getClass().getResource("res/terrain.png").getPath());
                map[row][col] = sprite;
                if (random.nextInt(10) > 6 && row != 20 && col != 20 && col != 0 && row != 0) {
                    Color circleColor = random.nextInt(10) < 8 ? Color.white : Color.red;
                    BetterCircle circle = new BetterCircle(1, 1, 64, circleColor);
                    circle.setRow(row);
                    circle.setColow(col);
                    circles.add(circle);
                }
            }
        }
        

        this.p1 = new Player();

        projectiles = new ArrayList<>();
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.add(this);
        frame.addKeyListener(new userInput(p1, this));
        frame.setBounds(0, 0, 1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.cameraX = p1.getX() - this.getWidth() / 2;
        this.cameraY = p1.getY() - this.getHeight() / 2;

        enemy = new Enemy(400, 300, 64, 64, 3, 10, 10);
       
        
    }
    
    public void spawnCircle() {
        int playerCenterX = p1.getX();
        int playerCenterY = p1.getY();
        int circleRadius = 10; // Initial radius of the spawned circle

        // Create a new BetterCircle object with the initial properties
        BetterCircle circle = new BetterCircle(playerCenterX, playerCenterY, circleRadius,Color.red);
        circle.setRow((int) (0.5 + (playerCenterY / 64)) + 1);
        circle.setColow((int) (0.5 + (playerCenterX / 64)) + 1);

       this.circle = circle;
       powerUp = false;
    }
       
    


    public void startCameraTracking() {
       
    	Thread td = new Thread(() ->{
    	while (true) {
            // Calculate the distance between the camera and the player
            int distanceX = p1.getX() - (cameraX + getWidth() / 2);
            int distanceY = p1.getY() - (cameraY + getHeight() / 2);

            // Update the camera position based on the distance
            if (Math.abs(distanceX) > CAMERA_SPEED) {
                cameraX += distanceX > 0 ? CAMERA_SPEED : -CAMERA_SPEED;
            }
            if (Math.abs(distanceY) > CAMERA_SPEED) {
                cameraY += distanceY > 0 ? CAMERA_SPEED : -CAMERA_SPEED;
            }
            
            repaint();
            try {
            	Thread.sleep(20); // Adjust the delay as needed
            } catch (InterruptedException e) {
            e.printStackTrace();
            }
    	}
    	});
    	td.start();
    
  }

    public void addSec() {
        secondsOpened++;
    }

    public void update() {
      
    	if(menu)
    		return;
    	
    	
    	
    	p1.playerColow = (int) (0.5 + (p1.getX() / 64)) + 1;
        p1.playerRow = (int) (0.5 + (p1.getY() / 64)) + 1;
        enemy.update(p1);
        p1.move();
       
        Iterator<BetterCircle> circleIterator = circles.iterator();
        while (circleIterator.hasNext()) {
            BetterCircle bc = circleIterator.next();
            if (bc.getRow() == p1.playerRow && bc.getColow() == p1.playerColow) {
            	try {
                    java.applet.AudioClip clip = java.applet.Applet.newAudioClip(GameControl.class.getResource("res/a.wav"));
                    clip.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            	circleIterator.remove();
                colected.add(new Vector2D(bc.getCenterX(),bc.getCenterY(),Math.random()*10,20,Color.white));
                if(bc.getColor() == Color.red) {
                	powerUp = true;
                }
                
               
            }
        }
                
         if (playerCollidesWithEnemy(p1.playerColow, p1.playerRow)) {
            enemy.takeKnockback();
            circleMove = true;
            int angle = 2;
            
            for (Vector2D circle : colected) {
            	circle.rotateDirection(angle);
				angle += 2;
				circle.setX(p1.getX());
		        circle.setY(p1.getY());
			}
           
            
        }
      
        if(time % 3000 == 0) {
        	circleMove = false;
        	
         }
       
        Iterator<Vector2D> colectedIt = colected.iterator();
        while (colectedIt.hasNext() && colected.size() > 0 && circleMove ) {
            Vector2D cl = colectedIt.next();
           
            cl.move(1280, 1280);
           
           
            if (cl.getX() < 0 || cl.getX() > getWidth() ||
                    cl.getY() < 0 || cl.getY() > 1280) {
                colectedIt.remove();
               
            }
        }
        Iterator<Vector2D> projectileIterator = projectiles.iterator();
        while (projectileIterator.hasNext()) {
            Vector2D projectile = projectileIterator.next();
            projectile.move(1280, 1280);

            // Remove projectiles that are out of bounds
            if (projectile.getX() < 0 || projectile.getX() > getWidth() ||
                    projectile.getY() < 0 || projectile.getY() > 1280) {
                projectileIterator.remove();
                
            }
        }
        
        
        if(circle != null) {
        	circle.setRadius(circle.getRadius()+10);
        	if(circle.getRadius() > 800) {
        		circle = null;
        	}
        }
        
        if(enemyCollidesWithCircle()) {
        	enemy.takeKnockback();
        }
    }

    private boolean playerCollidesWithEnemy(int playerColow, int playerRow) {
        int enemyColow = (int) (0.5 + (enemy.getX() / 64)) + 1;
        int enemyRow = (int) (0.5 + (enemy.getY() / 64)) + 1;

        return playerColow == enemyColow && playerRow == enemyRow;
    }
    
   private boolean enemyCollidesWithCircle() {
	if(circle != null)
	   return circle.intersects(enemy.getX(), enemy.getY(), 48, 48);
	else 
		return false;
	   
   }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Create a buffer for double buffering
        BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dBuffer = buffer.createGraphics();

        // Clear the buffer
        g2dBuffer.setColor(Color.WHITE);
        g2dBuffer.fillRect(0, 0, getWidth(), getHeight());

        // Determine which sprite sheet and frame to draw
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                int spriteX = col * 64 - cameraX;
                int spriteY = row * 64 - cameraY;

                BufferedImage spriteImage = map[row][col].getImage();
                if (spriteImage == null)
                    break;
                g2dBuffer.drawImage(spriteImage, spriteX, spriteY, null);
            }
        }

        for (BetterCircle bc : circles) {
        	int circleX = bc.getColow() * 64 - cameraX;
            int circleY = bc.getRow() * 64 - cameraY;
            g2dBuffer.setColor(bc.getColor());
            g2dBuffer.drawOval(circleX - 32, circleY - 32, 64, 64);
            g2dBuffer.setColor(Color.white);
        }

        BufferedImage spriteSheet = p1.getSprite(p1.getAnimationIndex());
        int widthSprite = 64;
        if (spriteSheet.getWidth() == 1024) {
            widthSprite = 256;
        }

        int frameIndex = p1.getCurrentFrame() % 4;
        // Draw the current frame
        int frameX = frameIndex * widthSprite;
        BufferedImage currentImage = spriteSheet.getSubimage(frameX, 0, widthSprite, 64);
        
        int playerX = p1.getX() - cameraX;
        int playerY = p1.getY() - cameraY;
       
        if (powerUp) {
            Font font = new Font("Arial", Font.BOLD, 42);
            FontMetrics fontMetrics = g2dBuffer.getFontMetrics(font);
            int stringWidth = fontMetrics.stringWidth("Press PowerUp to stun or push zombie");
            int stringHeight = fontMetrics.getHeight();
            int messageX = playerX + (64 / 2) - (stringWidth / 2);
            int messageY = playerY + 64 + stringHeight;
            
            g2dBuffer.setFont(font);
            g2dBuffer.setColor(Color.black);
            g2dBuffer.drawString("Press SpaceBar to stun or push zombie", messageX, messageY);
        }
        // Draw the circle entered on the player
        if (circle != null) {
            int circleX = (int) ((int) circle.getCenterX() - circle.getRadius() - cameraX);
            int circleY = (int) ((int) circle.getCenterY() - circle.getRadius() - cameraY);
            int circleDiameter = (int) (circle.getRadius() * 2);
            
            g2dBuffer.setColor(Color.RED);
            g2dBuffer.drawOval(circleX, circleY, circleDiameter, circleDiameter);
            
            // Draw the player in the center of the circle
            g2dBuffer.drawImage(currentImage, playerX, playerY, null);
        } else {
            // Draw the player normally if no circle is present
            g2dBuffer.drawImage(currentImage, playerX, playerY, null);
        }

        enemy.draw(g2dBuffer, cameraX, cameraY);
        
        Iterator<Vector2D> projectileIterator = projectiles.iterator();
        while (projectileIterator.hasNext()) {
            Vector2D vec = projectileIterator.next();
            g2dBuffer.setColor(Color.RED);
            g2dBuffer.fillOval((int) (0.5 + vec.getX()) - 10 - cameraX, (int) (0.5 + vec.getY()) - 10 - cameraY, 20, 20);
        }

        Iterator<Vector2D> colectedIt = colected.iterator();
        while (colectedIt.hasNext()) {
        	Vector2D cl = colectedIt.next();
            g2dBuffer.setColor(Color.white);
            g2dBuffer.drawOval((int) (0.5 + cl.getX()) - 10-cameraX, (int) (0.5 + cl.getY()) - 10-cameraY, 20, 20);
        }

        // Draw the buffer onto the panel
        g2d.drawImage(buffer, 0, 0, null);
    }


    public ArrayList<Vector2D> getProjectiles() {
        return projectiles;
    }

	public void addTimer(int i) {
		time += i;
		
	}
	
	public boolean getPowerUP() {
		return powerUp;
	}

}
