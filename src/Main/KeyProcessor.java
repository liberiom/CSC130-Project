/* This will handle the "Hot Key" system. */

package Main;

import logic.Control;
import timer.stopWatchX;

public class KeyProcessor{
	// Static Fields
	private static char last = ' ';			// For debouncing purposes
	private static stopWatchX sw = new stopWatchX(250);
	private static boolean isRight = false, isLeft = false, isUp = false, isDown = false;
	
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
			// changePosition(0, -(Main.speed));
			break;
		
		case 'a':
			// changePosition(-(Main.speed), 0);
			changeDirection(true, false, false, false);
			while (isLeft) {
				Main.vector2d.setX(Main.vector2d.getX() + Main.speed);
			}
			break;
			
		case 's':
			// changePosition(0, Main.speed);
			break;
			
		case 'd':
			// changePosition(Main.speed, 0);
			changeDirection(false, true, false, false);
			while (isRight) {
				Main.vector2d.setX(Main.vector2d.getX() + Main.speed);
			}
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
	
	private static void changeDirection(boolean left, boolean right, boolean up, boolean down) {
		isLeft = left;
		isRight = right;
		isUp = up;
		isDown = down;
	}
	
	private static void changePosition(int xMove, int yMove) {
		Main.vector2d.setX(Main.vector2d.getX() + xMove);
		Main.vector2d.setY(Main.vector2d.getY() + yMove);
	}

}