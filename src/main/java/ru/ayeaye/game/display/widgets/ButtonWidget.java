package ru.ayeaye.game.display.widgets;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class ButtonWidget extends Widget {
	
	private String text = "";
	private boolean mouseOver;
	private Color mouseOverColor;
	private Color mouseOffColor;
	private float borderWidth = 1f;
	
	public ButtonWidget(String name) {
		super(name);
	}
	
	public ButtonWidget(String name, int width, int height) {
		super(name, width, height);
	}
	
	public ButtonWidget(String name, float widthProportion, float heightProportion) {
		super(name, widthProportion, heightProportion);
	}

	@Override
	public void customRender(Graphics graphcis, int absoluteX, int absoluteY, int minWidth, int minHeight) {
		final int width = Math.min(getWidth(), minWidth);
		final int height = Math.min(getHeight(), minHeight);
		final float oldLineWidth = graphcis.getLineWidth();
		
		graphcis.setLineWidth(borderWidth);
		graphcis.drawRect(absoluteX, absoluteY, width, height);
		graphcis.setLineWidth(oldLineWidth);
		
		final Color oldColor = graphcis.getColor();
		final Color fillColor = mouseOver? mouseOverColor: mouseOffColor;
		graphcis.setColor(fillColor);
		graphcis.fillRect((float)absoluteX + borderWidth/2, (float)absoluteY + borderWidth/2, (float)width - borderWidth + 1, (float)height - borderWidth);
		graphcis.setColor(oldColor);
		
		final int letterHeight = graphcis.getFont().getHeight("A"); //TODO: это же константа, нужно вынести куда-нибудь
		final int lineWidth = graphcis.getFont().getWidth(text);
		final int lineX = absoluteX + width/2 - lineWidth/2;
		final int lineY = absoluteY + height/2 - letterHeight/2 - letterHeight/7;
		
		graphcis.drawString(text, lineX, lineY);
	}
	
	@Override
	public void dispatchMouseEvent(int mouseButton, int modifier, int mouseX, int mouseY, int absoluteX, int absoluteY, int minWidth, int minHeight) {
		mouseOver = true;
		if (mouseButton == Input.MOUSE_LEFT_BUTTON) {
			log.debug("Button " + getName() + " activeted, modifier: " + modifier);
		}
	}
	
	@Override
	public void endDispatchMouseEvent() {
		mouseOver = false;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		if (text == null)
			text = "";
		this.text = text;
	}

	public Color getMouseOverColor() {
		return mouseOverColor;
	}

	public void setMouseOverColor(Color mouseOverColor) {
		this.mouseOverColor = mouseOverColor;
	}

	public Color getMouseOffColor() {
		return mouseOffColor;
	}

	public void setMouseOffColor(Color mouseOffColor) {
		this.mouseOffColor = mouseOffColor;
	}
	
}
