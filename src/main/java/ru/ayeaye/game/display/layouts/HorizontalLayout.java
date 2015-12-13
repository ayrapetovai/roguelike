package ru.ayeaye.game.display.layouts;

import java.util.List;

import org.newdawn.slick.Graphics;

import ru.ayeaye.game.display.widgets.Widget;

public class HorizontalLayout extends LinearLayout {

	@Override
	public void render(Graphics graphics, int parentWidgetAbsoluteX, int parentWidgetAbsoluteY, int w, int h) {
		List<Widget> widgets = getWidgets();
		
		int widgetX = parentWidgetAbsoluteX;
		for (Widget widget: widgets) {
			widget.render(graphics, widgetX, parentWidgetAbsoluteY, w, h);
			widgetX += widget.getWidth();
		}
	}
	
	@Override
	public void dispatchMouseEvent(int mouseButton, int modifier, int mouseX, int mouseY, int parentWidgetAbsoluteX, int parentWidgetAbsoluteY, int minWidth, int minHeight) {
		List<Widget> widgets = getWidgets();
		Widget lastReceiver = getLastMouseEventReceiver();

		boolean found = false;
		int widgetX = parentWidgetAbsoluteX;
		for (Widget widget: widgets) {
			if (widget.isAlowedToDispatchMouse() && widget.containsPoint(mouseX, mouseY, widgetX, parentWidgetAbsoluteY)) {
				if (lastReceiver != null && lastReceiver != widget) {
					lastReceiver.endDispatchMouseEvent();
				}
				widget.dispatchMouseEvent(mouseButton, modifier, mouseX, mouseY, widgetX, parentWidgetAbsoluteY, minWidth, minHeight);
				setLastMouseEventReceiver(widget);
				found = true;
				break;
			}
			widgetX += widget.getWidth();
		}
		if (!found) {
			if (lastReceiver != null) {
				lastReceiver.endDispatchMouseEvent();
				setLastMouseEventReceiver(null);
			}
		}
	}
}
