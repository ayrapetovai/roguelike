package ru.ayeaye.game.display.layouts;

import ru.ayeaye.game.display.widgets.Widget;

public abstract class LinearLayout extends AbstractLayout {
	
	@Override
	public void dispatchKeyEvent(int keyCode, int modifier) {
		for (Widget widget: getWidgets()) {
			if (widget.isVisible() && widget.isAlowedToDispatchKey()) {
				widget.dispatchKeyEvent(keyCode, modifier);
			}
		}
	}
}
