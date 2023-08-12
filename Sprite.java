import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;

public class Sprite {
	private BufferedImage image;
	
	public Sprite(String urls) {
		try {
            image= ImageIO.read(new File(urls));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	

	public Sprite(URL resource) {
		try {
			image = ImageIO.read(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		return image;
	}
}
