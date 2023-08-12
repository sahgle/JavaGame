import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Enemy {
    private int x;
    private int y;
    private int width;
    private int height;
    private int speed;
    private int knockbackSpeed;
    private int knockbackDuration;
    private int knockbackTimer;
    private AnimationRenderer enemyAnimation;

    public Enemy(int x, int y, int width, int height, int speed, int knockbackSpeed, int knockbackDuration) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.knockbackSpeed = knockbackSpeed;
        this.knockbackDuration = knockbackDuration;
        this.knockbackTimer = 0;
        ArrayList<String> spriteSheetPaths = new ArrayList<>();
        spriteSheetPaths.add(getClass().getResource("res/zombieUP.png").getPath());
        spriteSheetPaths.add(getClass().getResource("res/ZombieDown.png").getPath());
        spriteSheetPaths.add(getClass().getResource("res/zombieLeft.png").getPath());
        spriteSheetPaths.add(getClass().getResource("res/ZombieRight.png").getPath());
        enemyAnimation = new AnimationRenderer(spriteSheetPaths, 48, 64, 3, 10);
        
         
    }

    public void update(Player player) {
        if (knockbackTimer > 0) {
            // Apply knockback
            int dx = x - player.getX();
            int dy = y - player.getY();
            int distance = (int) Math.sqrt(dx * dx + dy * dy);
            int knockbackX = (dx / distance) * knockbackSpeed;
            int knockbackY = (dy / distance) * knockbackSpeed;
            x += knockbackX;
            y += knockbackY;

            knockbackTimer--;
        } else {
            // Chase the player
            int distanceX = player.getX() - x;
            int distanceY = player.getY() - y;

            
            if (distanceY > 0) {
                y += speed;
                enemyAnimation.setIndexAnimation(1);
            } else if (distanceY < 0) {
                y -= speed;
                enemyAnimation.setIndexAnimation(0);
                
            }
            if (distanceX > 0) {
                x += speed;
                enemyAnimation.setIndexAnimation(3);
            } else if (distanceX < 0) {
                x -= speed;
                enemyAnimation.setIndexAnimation(2);
            }

        }
    }

    public void takeKnockback() {
        knockbackTimer = knockbackDuration;
    }

    public void draw(Graphics2D g2d, int cameraX, int cameraY) {
        enemyAnimation.startAnimation();
        // Calculate the coordinates relative to the camera
        int drawX = x - cameraX;
        int drawY = y - cameraY;

        // Get the current frame from the animation renderer
        BufferedImage spriteSheet = enemyAnimation.getSprite(enemyAnimation.getSheetIndex());
        int frameWidth = 48;
        int frameHeight = 64;
        int currentFrame = enemyAnimation.getCurrentFrame();

        // Calculate the frame position in the sprite sheet
        int frameX = currentFrame * frameWidth;
        int frameY = 0;

        // Draw the current frame
        g2d.drawImage(spriteSheet.getSubimage(frameX, frameY, frameWidth, frameHeight), drawX, drawY, null);
    }
    

	public int getX() {
		// TODO Auto-generated method stub
		return x;
	}

	public int getY() {
		// TODO Auto-generated method stub
		return y;
	}
}
