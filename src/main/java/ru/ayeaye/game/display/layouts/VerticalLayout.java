package ru.ayeaye.game.display.layouts;

import java.util.List;
import org.newdawn.slick.Graphics;
import ru.ayeaye.game.display.widgets.Widget;

public class VerticalLayout extends LinearLayout {
	
	@Override
	public void render(Graphics graphics, int parentAbsoluteX, int parentAbsoluteY, int minWidth, int minHeight) {
		List<Widget> widgets = getWidgets();

		int widgetY = parentAbsoluteY;
		for (Widget widget: widgets) {
			widget.render(graphics, parentAbsoluteX, widgetY, minWidth, minHeight);
			widgetY += widget.getHeight();
		}
	}

	@Override
	public void dispatchMouseEvent(int mouseButton, int modifier, int mouseX, int mouseY, int parentAbsoluteX, int parentAbsoluteY, int minWidth, int minHeight) {
		List<Widget> widgets = getWidgets();
		Widget lastReceiver = getLastMouseEventReceiver();
		
		boolean found = false;
		int widgetY = parentAbsoluteY;
		for (Widget widget: widgets) {
			if (widget.isAlowedToDispatchMouse() && widget.containsPoint(mouseX, mouseY, parentAbsoluteX, widgetY)) {
				if (lastReceiver != null && lastReceiver != widget) {
					lastReceiver.endDispatchMouseEvent();
				}
				widget.dispatchMouseEvent(mouseButton, modifier, mouseX, mouseY, parentAbsoluteX, widgetY, minWidth, minHeight);
				setLastMouseEventReceiver(widget);
				found = true;
				break;
			}
			widgetY += widget.getHeight();
		}
		
		if (!found) {
			if (lastReceiver != null) {
				lastReceiver.endDispatchMouseEvent();
				setLastMouseEventReceiver(null);
			}
		}
	}
}
