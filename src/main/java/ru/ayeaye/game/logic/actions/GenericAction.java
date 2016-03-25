package ru.ayeaye.game.logic.actions;

import java.util.Map;

public abstract class GenericAction {
	protected final Map<ActionParameter, Object> context;
	protected Algorithm algo;
	
	public GenericAction(Map<ActionParameter, Object> context) {
		this.context = context;
		this.algo = new Algorithm(context);
	}
	
	public boolean canPutInTimeQueue() {
		return true;
	}
	
	public boolean canExecute() {
		return true;
	}
	
	public abstract void execute();
	
	public abstract float getDelay();
	
	public abstract boolean isContinuos();

	public abstract ActionType getActionType();
	
	public Map<ActionParameter, Object> getContext() {
		return context;
	}
	
	public Algorithm getAlgo() {
		return algo;
	}
}
