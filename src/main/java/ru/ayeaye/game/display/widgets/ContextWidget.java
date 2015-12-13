package ru.ayeaye.game.display.widgets;

public class ContextWidget extends Widget {
	
	private boolean folowMouse = true;
	
	public ContextWidget(String name) {
		super(name);
	}

	public ContextWidget(String name, int width, int height) {
		super(name, width, height);
	}
	
	@Override
	public void dispatchMouseEvent(int mouseButton, int modifier, int mouseX, int mouseY, int absoluteX, int absoluteY, int minWidth, int minHeight) {
		super.dispatchMouseEvent(mouseButton, modifier, mouseX, mouseY, getOffsetX(), getOffsetY(), minWidth, minHeight);
	}

	public boolean isFolowMouse() {
		return folowMouse;
	}

	public void setFolowMouse(boolean folowMouse) {
		this.folowMouse = folowMouse;
	}
}
