/* This will handle the "Hot Key" system. */

package Main;

import logic.Control;
import timer.stopWatchX;

public class KeyProcessor{
	// Static Fields
	private static char last = ' ';			// For debouncing purposes
	private static stopWatchX sw = new stopWatchX(50);
	
	// Static Method(s)
	public static void processKey(char key){
		if(key == ' ')				return;
		// Debounce routine below...
		if(key == last)
			if(sw.isTimeUp() == false)			return;
		last = key;
		sw.resetWatch();
		
		/* TODO: You can modify values below here! */
		switch(key){
		case '%':								// ESC key
			System.exit(0);
			break;
		case 'w':
			changePosition(0, -(Main.speed));
			break;
		
		case 'a':
			changePosition(-(Main.speed), 0);
			break;
			
		case 's':
			changePosition(0, Main.speed);
			break;
			
		case 'd':
			changePosition(Main.speed, 0);
			Main.spritesRight.add(Main.spritesRight.remove());
			break;
		
		case '$':
			Main.trigger = "space bar is triggered";
			break;
			
		case 'm':
			// For mouse coordinates
			Control.isMouseCoordsDisplayed = !Control.isMouseCoordsDisplayed;
			break;
		}
	}
	
	
	private static void changePosition(int xMove, int yMove) {
		Main.startPosition.setX(Main.startPosition.getX() + xMove);
		Main.startPosition.setY(Main.startPosition.getY() + yMove);
	}

}