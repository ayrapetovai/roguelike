package ru.ayeaye.game.model;

public interface GenericAction {
	boolean isContinuous();
	float getDelay();
	boolean canApplay(GameModel model);
	void applay(GameModel model);
	boolean canPutIntoTimeQueue(GameModel model);
	GenericAction getPostAction();
	public String getDesctiption();
}
