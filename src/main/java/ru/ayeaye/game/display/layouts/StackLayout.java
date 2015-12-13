package ru.ayeaye.game.display.layouts;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.Graphics;

import ru.ayeaye.game.display.widgets.Widget;

public class StackLayout extends AbstractLayout {

	@Override
	public void render(Graphics graphics, int parentAbsoluteX, int parentAbsoluteY, int w, int h) {
		List<Widget> widgets = getWidgets();
		
		for (Widget widget: widgets) {
			if (widget.isCentred()) {
				
				final int centerX = parentAbsoluteX + w / 2;
				final int centerY = parentAbsoluteY + h / 2;
				
				final int halfWidgetWidth = widget.getWidth() / 2;
				final int halfWidgetHeight = widget.getHeight() / 2;
				widget.render(graphics, centerX - halfWidgetWidth, centerY - halfWidgetHeight, widget.getWidth(), widget.getHeight());
			} else {
				widget.render(graphics, parentAbsoluteY + widget.getOffsetX(), parentAbsoluteY + widget.getOffsetY(), w, h);
			}
		}
	}

	@Override
	public void dispatchMouseEvent(int mouseButton, int modifier, int mouseX, int mouseY, int parentAbsoluteX, int parentAbsoluteY, int minWidth, int minHeight) {
		LinkedList<Widget> widgets = getWidgets();
		Widget lastReceiver = getLastMouseEventReceiver();
		Iterator<Widget> iterator = widgets.descendingIterator();
		while(iterator.hasNext()) {
			Widget widget = iterator.next();
			if (widget.isCentred()) {
				
				final int centerX = minWidth / 2;
				final int centerY = minHeight / 2;
				
				final int halfWidgetWidth = widget.getWidth() / 2;
				final int halfWidgetHeight = widget.getHeight() / 2;
				if (widget.isVisible() && widget.isAlowedToDispatchMouse() && widget.containsPoint(mouseX, mouseY, centerX - halfWidgetWidth, centerY - halfWidgetHeight)) {
					if (lastReceiver != null && lastReceiver != widget) {
						lastReceiver.endDispatchMouseEvent();
					}
					widget.dispatchMouseEvent(mouseButton, modifier,mouseX, mouseY, centerX - halfWidgetWidth, centerY - halfWidgetHeight, widget.getWidth(), widget.getHeight());
					setLastMouseEventReceiver(widget);
					break;
				}
			} else {
				if (widget.isVisible() && widget.isAlowedToDispatchMouse() && widget.containsPoint(mouseX, mouseY, parentAbsoluteX, parentAbsoluteY)) {
					if (lastReceiver != null && lastReceiver != widget) {
						lastReceiver.endDispatchMouseEvent();
					}
					widget.dispatchMouseEvent(mouseButton, modifier, mouseX, mouseY, parentAbsoluteX, parentAbsoluteY, widget.getWidth(), widget.getHeight());
					setLastMouseEventReceiver(widget);
					break;
				}
			}
			// если один из верхних виджетов уже принял/пропустил событие - прекращаем 
			if (widget.isVisible()) {
				break;
			}
		}
	}

	@Override
	public void dispatchKeyEvent(int keyCode, int modifier) {
		LinkedList<Widget> widgets = getWidgets();
		Iterator<Widget> iterator = widgets.descendingIterator();
		while(iterator.hasNext()) {
			Widget widget = iterator.next();
			if (widget.isVisible() && widget.isAlowedToDispatchKey()) {
				widget.dispatchKeyEvent(keyCode, modifier);
				break;
			}
		}
	}

}
