import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
        GameControl gameControl = new GameControl();
        gameControl.startCameraTracking();
        
        Thread n2 = new Thread(() ->{
        	
        	while (true) {
        		gameControl.update();
        		try {
        			Thread.sleep(20);
        			gameControl.addTimer(20);
        			
        		} catch (InterruptedException e) {
        			e.printStackTrace();
        		}
        		
        	}
        
        });
        
        n2.start();
		
	}
}
