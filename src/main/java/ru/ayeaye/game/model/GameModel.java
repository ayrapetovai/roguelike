package ru.ayeaye.game.model;

import java.util.HashMap;
import java.util.Map;

import ru.ayeaye.game.logic.triggers.Trigger;
import test2.ActionType;

public class GameModel {
	private Map<ActionType, Trigger> triggers = new HashMap<>();
	private GameField field;
	private TimeQueue timeQueue = new TimeQueue();
	private GameLogSource gameLogSource;

	public GameField getField() {
		return field;
	}

	public void setField(GameField field) {
		this.field = field;
	}

	public TimeQueue getTimeQueue() {
		return timeQueue;
	}

	public void setTimeQueue(TimeQueue timeQueue) {
		this.timeQueue = timeQueue;
	}

	public GameLogSource getGameLogSource() {
		return gameLogSource;
	}

	public void setGameLogSource(GameLogSource gameLogSource) {
		this.gameLogSource = gameLogSource;
	}

	public Map<ActionType, Trigger> getTriggers() {
		return triggers;
	}
	
}
