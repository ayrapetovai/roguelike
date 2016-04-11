package ru.ayeaye.game.logic.actions;

import java.util.Map;

import ru.ayeaye.game.model.GameObject;
import ru.ayeaye.game.model.Tag;

public abstract class GenericAction {
	protected final Map<ActionParameter, Object> context;
	protected Algorithm algo;
	
	public GenericAction(Map<ActionParameter, Object> context) {
		this.context = context;
		this.algo = new Algorithm(context);
	}
	
	public boolean canPutInTimeQueue() {
		GameObject sourceGO = (GameObject) context.get(ActionParameter.SOURCE_GAME_OBJECT);
		return !sourceGO.getTags().contains(Tag.BUSY);
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

	public void begin() {
		GameObject sourceGO = (GameObject) context.get(ActionParameter.SOURCE_GAME_OBJECT);
		sourceGO.getTags().add(Tag.BUSY);
	}

	public void end() {
		GameObject sourceGO = (GameObject) context.get(ActionParameter.SOURCE_GAME_OBJECT);
		sourceGO.getTags().remove(Tag.BUSY);
	}
}
