package ru.ayeaye.game.logic;

import java.util.List;

import ru.ayeaye.game.logic.misc.Direction;
import ru.ayeaye.game.logic.states.LogicState;
import ru.ayeaye.game.logic.triggers.Trigger;
import ru.ayeaye.game.model.GameModel;

public class GameLogicEngine {
	private static class GameLogicEngineHolder {
		public static GameLogicEngine instance = new GameLogicEngine();
	}
	
	private GameModel model;
	private LogicState state;
	
	private GameLogicEngine() {
	}
	
	public static GameLogicEngine getInstance() {
		return GameLogicEngineHolder.instance;
	}
	
	public void go() {
		if (!model.getTimeQueue().isEmpty()) {
			makeOneMove();
		}
	}
	
	public void makeOneMove() {
		List<test.GenericAction> actions = model.getTimeQueue().getCurrentActions();
		for (test.GenericAction action: actions) {
			model.getGameLogSource().addEntry("action at " + model.getTimeQueue().getCurrentTime());
			if (action.canExecute()) {
				Trigger trigger = model.getTriggers().get(action.getActionType());
				if (trigger != null) {
					trigger.preApplay(action.getContext());
				}
				
				action.execute();
				
				if (trigger != null) {
					test.GenericAction triggerAction = trigger.postApplay(action.getContext());
					if (triggerAction != null && triggerAction.canPutInTimeQueue()) {
						model.getTimeQueue().addAction(triggerAction);
					}
				}
				
				test.GenericAction postAction = action.getPostAction();
				if (postAction != null && postAction.canPutInTimeQueue()) {
					model.getTimeQueue().addAction(postAction);
				}
			}
			
			// TODO: dispose Action
		}
		
		runLogic();
//		return !model.getPlayerHolder().getGameObject().isBusy();
	}
	
	private void runLogic() {
		// TODO Auto-generated method stub
	}

	public void addAction(PlayerCommand playerCommand) {
		test.GenericAction action = playerCommand.createAction(model);
		if (action != null && action.canPutInTimeQueue()) {
			model.getTimeQueue().addAction(action);
		}
		// TODO: dispose intention
	}
	
	public void setModel(GameModel model) {
		this.model = model;
	}
}
