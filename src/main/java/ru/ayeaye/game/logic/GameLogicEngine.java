package ru.ayeaye.game.logic;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.ayeaye.game.logic.actions.GenericAction;
import ru.ayeaye.game.logic.playercommands.PlayerCommand;
import ru.ayeaye.game.logic.states.LogicState;
import ru.ayeaye.game.logic.triggers.Trigger;
import ru.ayeaye.game.model.GameModel;

public class GameLogicEngine {
	
	private static Logger log = LoggerFactory.getLogger(GameLogicEngine.class);
	
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
				
				log.debug("Action delay is " + action.getDelay());
				
				action.execute();
				
				if (trigger != null) {
					GenericAction triggerAction = trigger.postApplay(action.getContext());
					if (triggerAction != null && triggerAction.canPutInTimeQueue()) {
						model.getTimeQueue().addAction(triggerAction);
					}
				}
				
				if (action.isContinuos()) {
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
