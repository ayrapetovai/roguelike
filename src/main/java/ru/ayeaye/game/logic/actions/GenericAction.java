package ru.ayeaye.game.logic.actions;

import java.util.Map;

public class GenericAction {
	private final ActionType actionType;
	
	private Map<ActionParameter, Object> context;
	private Algorithm algo;
	
	public GenericAction(ActionType actionType) {
		this.actionType = actionType;
	}
	
	public boolean canPutInTimeQueue() {
		return true;
	}
	
	public boolean canExecute() {
		return true;
	}
	
	public void execute() {
		algo.execute();
	}
	
	public float getDelay() {
		return algo.getDelay();
	}
	
	public ActionType getActionType() {
		return actionType;
	}
	
	public Map<ActionParameter, Object> getContext() {
		return context;
	}
	
	public void setContext(Map<ActionParameter, Object> context) {
		this.context = context;
		this.algo = new Algorithm(context, actionType);
	}
	
	public Algorithm getAlgo() {
		return algo;
	}
}
