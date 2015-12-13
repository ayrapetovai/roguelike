package ru.ayeaye.game.input;

import org.newdawn.slick.Input;
import org.newdawn.slick.InputListener;

public class GameInputListener implements InputListener {
	
	private static GameInputListener instance = new GameInputListener();
	
	public static GameInputListener getInstance() {
		return instance;
	}
	
	private volatile int key = 0;
	private volatile int modifier;
	
	private volatile int mouseButton = -1;
	private volatile boolean mouseReleased;
	private volatile boolean mousePressed;
	private volatile int mouseX = -1;
	private volatile int mouseY = -1;
	
	private GameInputListener() {
	}
	
	@Override
	public void mouseWheelMoved(int change) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
//		mouseButton = button;
//		mouseX = x;
//		mouseY = y;
//		mouseReleased = true;
//		mousePressed = true;
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		mouseButton = button;
		mouseX = x;
		mouseY = y;
		mouseReleased = false;
		mousePressed = true;
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
//		mouseButton = button;
//		mouseX = x;
//		mouseY = y;
//		mouseReleased = true;
//		mousePressed = false;
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		mouseX = newx;
		mouseY = newy;
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setInput(Input input) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAcceptingInput() {
		return true;
	}

	@Override
	public void inputEnded() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_LSHIFT) {
			modifier = Input.KEY_LSHIFT;
		} else if (key == Input.KEY_LCONTROL) {
			modifier = Input.KEY_LCONTROL;
		} else if (key == Input.KEY_LALT) {
			modifier = Input.KEY_LALT;
		} else {
			this.key = key;
		}
		
	}

	@Override
	public void keyReleased(int key, char c) {
		if (key == Input.KEY_LSHIFT || key == Input.KEY_LCONTROL || key == Input.KEY_LALT) {
			modifier = 0;
		}
	}

	@Override
	public void controllerLeftPressed(int controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerLeftReleased(int controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerRightPressed(int controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerRightReleased(int controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerUpPressed(int controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerUpReleased(int controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerDownPressed(int controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerDownReleased(int controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerButtonPressed(int controller, int button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerButtonReleased(int controller, int button) {
		// TODO Auto-generated method stub
		
	}

	public int getKey() {
		return key;
	}

	public int getModifier() {
		return modifier;
	}

	public void clearKey() {
		key = 0;
	}

	public void clearMouse() {
		mouseButton = -1;
	}
	
	public int getMouseButton() {
		return mouseButton;
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

}
