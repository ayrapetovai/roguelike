package ru.ayeaye.game.logic.triggers;

import java.util.Map;

import ru.ayeaye.game.logic.actions.ActionParameter;
import ru.ayeaye.game.logic.actions.GenericAction;

public abstract class Trigger {
	public abstract void preApplay(Map<ActionParameter, Object> context);
	public abstract GenericAction postApplay(Map<ActionParameter, Object> context);
}
