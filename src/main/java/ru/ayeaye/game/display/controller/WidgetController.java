package ru.ayeaye.game.display.controller;

import ru.ayeaye.game.display.widgets.Widget;

public abstract class WidgetController {
	public abstract void handleKey(Widget caller, int keyCode, int modifier);
	public abstract void handleMouse(Widget caller, int mouseButton, int modifier, int mouseX, int mouseY, int absoluteX, int absoluteY, int minWidth, int minHeight);
	public abstract void handleMouseOff(Widget caller);
}
