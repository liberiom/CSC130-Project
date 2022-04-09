package Main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

import Data.Vector2D;
import Data.spriteInfo;
import FileIO.EZFileRead;
import logic.Control;
import timer.stopWatchX;

public class Main{
	// Fields (Static) below...
	
	public static stopWatchX timer = new stopWatchX(100);
	private int currentText = 0;
	// End Static fields...
	public static void main(String[] args) {
		Control ctrl = new Control();				// Do NOT remove!
		ctrl.gameLoop();							// Do NOT remove!
	}
	
	/* This is your access to things BEFORE the game loop starts */
	public static void start(){
		HashMap<String, String> map = new HashMap<>();
		String raw = "", key, value;
		StringTokenizer st = new StringTokenizer(raw, "*");
		EZFileRead ezr = new EZFileRead("scripts.txt");
		int lines = ezr.getNumLines();
		for (int i = 0; i < lines; i++) {
			raw = ezr.getLine(i);
			key = st.nextToken();
			value = st.nextToken();
			map.put(key, value);
		}
		
		// Debugging
		for (int i = 0; i < map.size(); i++) {
			System.out.println(map.get("string" + i));
		}
	} 
	
	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		
	}
}
