package ru.ayeaye.game.display.layouts;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.Graphics;

import ru.ayeaye.game.display.widgets.Widget;

public class ParentRelativeLayout extends LinearLayout {

	@Override
	public void render(Graphics graphics, int parentWidgetAbsoluteX, int parentWidgetAbsoluteY, int minWidth, int minHeight) {
		List<Widget> widgets = getWidgets();
		
		for (Widget widget: widgets) {
			widget.render(graphics, parentWidgetAbsoluteX + widget.getOffsetX(), parentWidgetAbsoluteY + widget.getOffsetY(), minWidth, minHeight);
		}
	}

	@Override
	public void dispatchMouseEvent(int mouseButton, int modifier, int mouseX, int mouseY, int parentAbsoluteX, int parentAbsoluteY, int minWidth, int minHeight) {
		LinkedList<Widget> widgets = getWidgets();
		Widget lastReceiver = getLastMouseEventReceiver();
		
		Iterator<Widget> it = widgets.descendingIterator();
		while (it.hasNext()) {
			Widget widget = it.next(); 
			if (widget.isAlowedToDispatchMouse() && widget.containsPoint(mouseX, mouseY, parentAbsoluteX + widget.getOffsetX(), parentAbsoluteY + widget.getOffsetY())) {
				if (lastReceiver != null && lastReceiver != widget) {
					lastReceiver.endDispatchMouseEvent();
				}
				widget.dispatchMouseEvent(mouseButton, modifier, mouseX, mouseY, parentAbsoluteX + widget.getOffsetX(), parentAbsoluteY + widget.getOffsetY(), minWidth, minHeight);
				setLastMouseEventReceiver(widget);
				break;
			}
		}
	}

}
