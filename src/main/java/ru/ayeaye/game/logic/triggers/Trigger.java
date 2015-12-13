package ru.ayeaye.game.logic.triggers;

import java.util.Map;

import test.ActionParameter;

public abstract class Trigger {
	public abstract void preApplay(Map<ActionParameter, Object> context);
	public abstract test.GenericAction postApplay(Map<ActionParameter, Object> context);
}
