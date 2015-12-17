package ru.ayeaye.game.logic.triggers;

import java.util.Map;

import test2.ActionParameter;
import test2.GenericAction;

public abstract class Trigger {
	public abstract void preApplay(Map<ActionParameter, Object> context);
	public abstract GenericAction postApplay(Map<ActionParameter, Object> context);
}
