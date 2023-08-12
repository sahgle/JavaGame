import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class userInput  implements KeyListener {
	
	private Player p1;
	private GameControl g1;
	
	
	public userInput(Player p1,GameControl game) {
	
		this.p1 = p1;
		this.g1 = game;
		
	}

	@Override
	public void keyPressed(KeyEvent key) {
			
		if(key.getKeyCode() == KeyEvent.VK_LEFT) {
			p1.animationLeft();
			p1.setPlayerXVelocity(-5);
		}else if(key.getKeyCode() == KeyEvent.VK_UP) {
			p1.animationUp();
			p1.setPlayerYVelocity(-5);
			
		}else if(key.getKeyCode() == KeyEvent.VK_RIGHT) {
			p1.animationRight();
			p1.setPlayerXVelocity(5);
		}
		else if(key.getKeyCode() == KeyEvent.VK_DOWN) {
			p1.animationDown();
			p1.setPlayerYVelocity(5);
		}else if(key.getKeyCode() == KeyEvent.VK_SPACE) {
			if(g1.getPowerUP()) {
				g1.spawnCircle();
				
			}
			
	
			
		}
			
				
				

	}
	
	@Override
	public void keyReleased(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_SPACE) {
			if(p1.animationIsRightAtack()){
				p1.animationRight();
			}	
		}
		if(key.getKeyCode() == KeyEvent.VK_LEFT) {
			
			p1.setPlayerXVelocity(0);
		}else if(key.getKeyCode() == KeyEvent.VK_UP) {
			
			p1.setPlayerYVelocity(0);
			
		}else if(key.getKeyCode() == KeyEvent.VK_RIGHT) {
		
			p1.setPlayerXVelocity(0);
		}
		else if(key.getKeyCode() == KeyEvent.VK_DOWN) {
			
			p1.setPlayerYVelocity(0);
		}
		

	}

	@Override
	public void keyTyped(KeyEvent key) {
				System.out.println("asdasdd");

	}
	
	
	
	

}
