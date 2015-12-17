package ru.ayeaye.game.logic.actions;

import ru.ayeaye.game.model.GameModel;

public interface GenericAction {
	boolean isContinuous();
	float getDelay();
	boolean canApplay(GameModel model);
	void applay(GameModel model);
	boolean canPutIntoTimeQueue(GameModel model);
	GenericAction getPostAction();
	public String getDesctiption();
}
