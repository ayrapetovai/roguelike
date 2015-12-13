package ru.ayeaye.game.display.widgets;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import ru.ayeaye.game.display.controller.WidgetController;
import ru.ayeaye.game.display.layouts.AbstractLayout;

public class Widget {
	
	/**
	 * ��� �������, ��� ��������
	 */
	private String name = "";
	
	/**
	 * ������� ������������� ���������� ������ �� ������ ��������������� ������� ������
	 */
	private boolean centred = false;
	
	/**
	 * �������� �������, ���������� � ����� ������� ������������ ���������� (�������)
	 */
	private AbstractLayout layout;

	private ContextWidget contextWidget; 
	
	/**
	 * ������� ������������� ��������� �������
	 */
	private boolean visible = true;
	
	private int width;
	private int height;
	
	/**
	 * ������� ������ (�� ��������������� � ��������) ������ ������ ������
	 */
	private float widthProportion = 1f;
	/**
	 * ������� ������ (�� ��������������� � ��������) ������ ������ ������
	 */
	private float heightProportion = 1f;
	
	/**
	 *  �������� �� ��� x ������������� ���������� ��������� ������������� �������
	 */
	private int offsetX;
	/**
	 *  �������� �� ��� y ������������� ���������� ��������� ������������� �������
	 */
	private int offsetY;
	
	private Color backgroundColor = Color.transparent;
	
	private boolean alowedToDispatchMouse = true;
	private boolean alowedToDispatchKey = true;
	
	protected WidgetController controller; // TODO: private
	
	public Widget(String name) {
		this.name = name;
	}
	
	public Widget(String name, int width, int height) {
		this.name = name;
		this.width = width;
		this.height = height;
	}
	
	public Widget(String name, float widthProportion, float heightProportion) {
		this.name = name;
		this.widthProportion = widthProportion;
		this.heightProportion = heightProportion;
	}
	
	public final void render(Graphics graphcis, int absoluteX, int absoluteY, int minWidth, int minHeight) {
		if (visible) {
			if (width == 0)
				width = (int)(widthProportion * minWidth);
			if (height == 0)
				height = (int)(heightProportion * minHeight);
			graphcis.setClip(absoluteX, absoluteY, Math.min(minWidth, width), Math.min(minHeight, height));
			if (!Color.transparent.equals(backgroundColor)) {
				Color oldBackgroundColor = graphcis.getColor();
				graphcis.setColor(backgroundColor);
				graphcis.fillRect(absoluteX, absoluteY, Math.min(width, minWidth), Math.min(height, minHeight));
				graphcis.setColor(oldBackgroundColor);
			}
			customRender(graphcis, absoluteX, absoluteY, minWidth, minHeight);
			graphcis.clearClip();
			
			layoutRender(graphcis, absoluteX, absoluteY, minWidth, minHeight);
			if (contextWidget != null && contextWidget.isVisible()) {
				contextWidget.render(graphcis, contextWidget.getOffsetX(), contextWidget.getOffsetY(), minWidth, minHeight);
			}
		}
	}
	
	/**
	 * ��������� ����������� �������, ��� ������������ ��������� � ����������� ����� �������������� ���� �����
	 * @param graphcis
	 * @param absoluteX
	 * @param absoluteY
	 * @param minWidth - ��������� ������ ���������
	 * @param minHeight - ��������� ������ ���������
	 */
	public void customRender(Graphics graphcis, int absoluteX, int absoluteY, int minWidth, int minHeight) {
	}

	private final void layoutRender(Graphics graphcis, int absoluteX, int absoluteY, int minWidth, int minHeight) {
		if (layout != null)
			layout.render(graphcis, absoluteX, absoluteY, Math.min(width, minWidth), Math.min(height, minHeight));
	}
	
	public void dispatchMouseEvent(int mouseButton, int modifier, int mouseX, int mouseY, int absoluteX, int absoluteY, int minWidth, int minHeight) {
		if (mouseButton == 0) System.out.println("Widget " + name + " dispatching mouse " + mouseX + ": " + mouseY);
		if (contextWidget != null && contextWidget.isVisible()) {
			if (!contextWidget.isVisible() || contextWidget.isFolowMouse()) {
				int fix = -15;
				if (!contextWidget.isFolowMouse()) {
					fix = 10;
				}
				contextWidget.setOffsetX(mouseX - fix);
				contextWidget.setOffsetY(mouseY - fix);
			} else {
				if (!contextWidget.containsPoint(mouseX, mouseY, contextWidget.getOffsetX(), contextWidget.getOffsetY())) {
					contextWidget.setVisible(false);
				}
			}
			if (contextWidget.isVisible() && !contextWidget.isFolowMouse())
				contextWidget.dispatchMouseEvent(mouseButton, modifier, mouseX, mouseY, absoluteX, absoluteY, minWidth, minHeight);
		} else if (layout != null) {
			layout.dispatchMouseEvent(mouseButton, modifier, mouseX, mouseY, absoluteX, absoluteY, Math.min(width, minWidth), Math.min(height, minHeight));
		} else {
			if (mouseButton == 0) System.out.println("Widget " + name + " ends mouse dispatching");
			if (controller != null)
				controller.handleMouse(this, mouseButton, modifier, mouseX, mouseY, absoluteX, absoluteY, Math.min(width, minWidth), Math.min(height, minHeight));
		}
		
		if (contextWidget != null && mouseButton == 1) contextWidget.setVisible(true);
	}
	
	public void endDispatchMouseEvent() {
		System.out.println(getName() + ": endDispatchingMouse");
		if (layout != null) {
			layout.endDispatchMouseEvent();
		}
		if (controller != null) {
			controller.handleMouseOff(this);
		}
		if (contextWidget != null) {
			contextWidget.setVisible(false);
		}
	}
	
	public void dispatchKeyEvent(int keyCode, int modifier) {
		System.out.println("Widget " + name + " dispatching key");
		if (contextWidget != null && contextWidget.isVisible()) {
			contextWidget.dispatchKeyEvent(keyCode, modifier);
		} else if (layout != null) {
			layout.dispatchKeyEvent(keyCode, modifier);
		} else if (controller != null) {
			System.out.println("Widget " + name + " ends key dispatching");
			controller.handleKey(this, keyCode, modifier);
		}
	}
	
	public boolean containsPoint(int mouseX, int mouseY, int absoluteX, int absoluteY) {
		return absoluteX <= mouseX && mouseX <= absoluteX + width && absoluteY <= mouseY && mouseY <= absoluteY + height;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public boolean isCentred() {
		return centred;
	}

	public void setCentred(boolean centred) {
		this.centred = centred;
	}

	public void setLayout(AbstractLayout layout) {
		this.layout = layout;
	}

	public ContextWidget getContextWidget() {
		return contextWidget;
	}

	public void setContextWidget(ContextWidget contextWidget) {
		this.contextWidget = contextWidget;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getOffsetX() {
		return offsetX;
	}

	public float getWidthProportion() {
		return widthProportion;
	}

	public void setWidthProportion(float widthProportion) {
		this.widthProportion = widthProportion;
	}

	public float getHeightProportion() {
		return heightProportion;
	}

	public void setHeightProportion(float heightProportion) {
		this.heightProportion = heightProportion;
	}

	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isAlowedToDispatchMouse() {
		return alowedToDispatchMouse;
	}

	public void setAlowedToDispatchMouse(boolean alowedToDispatchMouse) {
		this.alowedToDispatchMouse = alowedToDispatchMouse;
	}
	
	public boolean isAlowedToDispatchKey() {
		return alowedToDispatchKey;
	}

	public void setAlowedToDispatchKey(boolean alowedToDispatchKey) {
		this.alowedToDispatchKey = alowedToDispatchKey;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": " + getName() + " (x:" + offsetX + ", y:" + offsetY + "; w:" + width + ", h:" + height + ")"; 
	}
}