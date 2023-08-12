import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player {
    private AnimationRenderer animationRenderer;
    private int playerX;
    private int playerY;
    	int playerColow;
     int playerRow;
    private int playerXVelocity;
    private int playerYVelocity;
    private ArrayList<BetterCircle> projectiles;

    public Player() {
        projectiles = new ArrayList<>();
        playerX = 64;
        playerY = 64;
        playerColow = 1;
        playerRow = 1;
        // Create a list of sprite sheet paths
        ArrayList<String> spriteSheetPaths = new ArrayList<>();
        spriteSheetPaths.add(getClass().getResource("res/player.png").getPath());
        spriteSheetPaths.add(getClass().getResource("res/playerLeft.png").getPath());
        spriteSheetPaths.add(getClass().getResource("res/playerRight.png").getPath());
        spriteSheetPaths.add(getClass().getResource("res/playerUp.png").getPath());
        spriteSheetPaths.add(getClass().getResource("res/RightAtack.png").getPath());

        // Create the animation renderer with sprite sheet paths, frame dimensions, frame count, and animation speed
        animationRenderer = new AnimationRenderer(spriteSheetPaths, 64, 64, 4, 200); // Example values
    }

    public AnimationRenderer getPlayerAnimation() {
        return animationRenderer;
    }

    public void animationLeft() {
        animationRenderer.setIndexAnimation(1);
    }

    public void animationRight() {
        animationRenderer.setIndexAnimation(2);
    }

    public void animationUp() {
        animationRenderer.setIndexAnimation(3);
    }

    public void animationDown() {
        animationRenderer.setIndexAnimation(0);
    }

    public int getSheetIndex() {
        return animationRenderer.getSheetIndex();
    }

    public void animationRightAtack() {
        animationRenderer.setIndexAnimation(4);
    }

    public BufferedImage getSprite(int index) {
        return animationRenderer.getSprite(index);
    }

    public int getAnimationIndex() {
        return animationRenderer.getSheetIndex();
    }

    public int getCurrentFrame() {
        return animationRenderer.getCurrentFrame();
    }

    public int getX() {
        return playerX;
    }

    public int getY() {
        return playerY;
    }

    public boolean animationIsRight() {
        return animationRenderer.getSheetIndex() == 2;
    }

    public boolean animationIsRightAtack() {
        return animationRenderer.getSheetIndex() == 4;
    }

    public void move() {
        playerX += playerXVelocity;
        playerY += playerYVelocity;
    }

    public int getPlayerXVelocity() {
        return playerXVelocity;
    }

    public void setPlayerXVelocity(int playerXVelocity) {
        this.playerXVelocity = playerXVelocity;
    }

    public int getPlayerYVelocity() {
        return playerYVelocity;
    }

    public void setPlayerYVelocity(int playerYVelocity) {
        this.playerYVelocity = playerYVelocity;
    }

    public void releaseCircle() {
        BetterCircle circle = new BetterCircle(playerX, playerY, 10, null); // Adjust the radius as needed
        projectiles.add(circle);
    }

	public void setY(int i) {
		playerY = i;
		
	}

	public void setX(int i) {
		playerX = i;
		
	}
}

    