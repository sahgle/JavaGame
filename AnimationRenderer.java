import javax.imageio.ImageIO;
import javax.swing.*;

import com.sun.javafx.geom.Vec2d;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AnimationRenderer {

	    private ArrayList<BufferedImage> spriteSheets;
	    private int frameWidth;
	    private int frameHeight;
	    private int frameCount;
	    private int currentFrame;
	    private int animationSpeed;
		private int spriteSheetIndex;
	
	   

	    public AnimationRenderer(ArrayList<String> spriteSheetPaths, int frameWidth, int frameHeight, int frameCount, int animationSpeed) {
	        this.spriteSheets = loadSpriteSheets(spriteSheetPaths);
	        this.frameWidth = frameWidth;
	        this.frameHeight = frameHeight;
	        this.frameCount = frameCount;
	        this.animationSpeed = animationSpeed;
	        this.currentFrame = 0;
	        spriteSheetIndex = 0;
	        startAnimation();
	    }

	    private ArrayList<BufferedImage> loadSpriteSheets(ArrayList<String> spriteSheetPaths) {
	        ArrayList<BufferedImage> spriteSheets = new ArrayList<>();

	        for (String path : spriteSheetPaths) {
	            try {
	                BufferedImage spriteSheet = ImageIO.read(new File(path));
	                spriteSheets.add(spriteSheet);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }

	        return spriteSheets;
	    }

	    public void startAnimation() {
	        Thread animationThread = new Thread(() -> {
	            while (true) {
	                try {
	                    Thread.sleep(animationSpeed);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }

	                currentFrame = (currentFrame + 1) % frameCount;
	                
	            }
	        });

	        animationThread.start();
	    }

	    

		public void setIndexAnimation(int i) {
			spriteSheetIndex = i;
			
		}

		public int getSheetIndex() {
			return spriteSheetIndex;
			
		}
		
		public BufferedImage getSprite(int index) {
			return spriteSheets.get(index);
		}

		public int getCurrentFrame() {
			return currentFrame;
		}

		
		
		
		

}

