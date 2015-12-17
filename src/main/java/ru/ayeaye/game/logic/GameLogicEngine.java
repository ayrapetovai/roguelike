package ru.ayeaye.game.logic;

import java.util.List;
import java.util.Map;

import org.newdawn.slick.util.Log;

import ru.ayeaye.game.logic.states.LogicState;
import ru.ayeaye.game.logic.triggers.Trigger;
import ru.ayeaye.game.model.GameModel;
import test2.ActionParameter;
import test2.ActionType;
import test2.Algorithm;
import test2.GenericAction;

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
		List<GenericAction> actions = model.getTimeQueue().getCurrentActions();
		for (GenericAction action: actions) {
			model.getGameLogSource().addEntry("action at " + model.getTimeQueue().getCurrentTime());
			if (action.canExecute()) {
				Trigger trigger = model.getTriggers().get(action.getActionType());
				if (trigger != null) {
					trigger.preApplay(action.getContext());
				}
				
//				action.execute();
				ActionType actionType = action.getActionType();
				Map<ActionParameter, Object> context = action.getContext();
				Algorithm algo = new Algorithm(context, actionType);
				
				float d = algo.getDelay();
				Log.debug("Action delay is " + d);
				
				algo.execute();
				
				if (trigger != null) {
					GenericAction triggerAction = trigger.postApplay(action.getContext());
					if (triggerAction != null && triggerAction.canPutInTimeQueue()) {
						model.getTimeQueue().addAction(triggerAction);
					}
				}
				
				if (algo.isContinuos()) {
					model.getTimeQueue().addAction(action);
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
		GenericAction action = playerCommand.createAction(model);
		if (action != null && action.canPutInTimeQueue()) {
			model.getTimeQueue().addAction(action);
		}
		// TODO: dispose intention
	}
	
	public void setModel(GameModel model) {
		this.model = model;
	}
}
