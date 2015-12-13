package ru.ayeaye.game.display.layouts;

import java.util.LinkedList;

import org.newdawn.slick.Graphics;

import ru.ayeaye.game.display.widgets.Widget;

public abstract class AbstractLayout {
	
	private LinkedList<Widget> widgets;
	private Widget lastMouseEventReceiver;
	
	protected LinkedList<Widget> getWidgets() {
		return widgets;
	}
	
	public void addWidget(Widget w) {
		if (widgets == null)
			widgets = new LinkedList<>();
		
		if (widgets.contains(w)) {
			throw new IllegalArgumentException("Widget " + w + " is already in the layout");
		}
		
		widgets.add(w);
	}
	
	public boolean contains(Widget w) {
		return widgets.contains(w);
	}
	
	public void removeWidget(Widget w) {
		widgets.remove(w);
	}
	
	public abstract void render(Graphics graphics, int parentWidgetAbsoluteX, int parentWidgetAbsoluteY, int minWidth, int minHeight);

	public abstract void dispatchMouseEvent(int mouseButton, int modifier, int mouseX, int mouseY, int parentAbsoluteX, int parentAbsoluteY, int minWidth, int minHeight);
	
	public abstract void dispatchKeyEvent(int keyCode, int modifier);

	public void endDispatchMouseEvent() {
		Widget lasWidget = getLastMouseEventReceiver();
		if (lasWidget != null) {
			lasWidget.endDispatchMouseEvent();
			setLastMouseEventReceiver(null);
		}
	}

	public Widget getLastMouseEventReceiver() {
		return lastMouseEventReceiver;
	}

	public void setLastMouseEventReceiver(Widget lastMouseEventReceiver) {
		this.lastMouseEventReceiver = lastMouseEventReceiver;
	}
	
}
