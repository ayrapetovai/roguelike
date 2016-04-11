package ru.ayeaye.game.logic;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.ayeaye.game.logic.actions.ActionParameter;
import ru.ayeaye.game.logic.actions.GenericAction;
import ru.ayeaye.game.logic.actions.MeleAttackAction;
import ru.ayeaye.game.logic.actions.MoveAction;
import ru.ayeaye.game.logic.playercommands.PlayerCommand;
import ru.ayeaye.game.logic.states.LogicState;
import ru.ayeaye.game.logic.triggers.Trigger;
import ru.ayeaye.game.model.FieldCell;
import ru.ayeaye.game.model.GameModel;
import ru.ayeaye.game.model.GameObject;
import ru.ayeaye.game.model.Tag;
import ru.ayeaye.game.util.APath;

public class GameLogicEngine {
	
	private static Logger log = LoggerFactory.getLogger(GameLogicEngine.class);
	
	private static class GameLogicEngineHolder {
		public static GameLogicEngine instance = new GameLogicEngine();
	}
	
	private GameModel model;
	private LogicState state;
	private GameObject monster;
	private GameObject player;
	
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
		// FIXME: в модели дожна быть ссылка на персонажа игрока
		if (player == null) {
			player = model.getField().getCellOrException(2, 2).getObjectWithTag(Tag.CREATURE);
		}
		
		if (player.getTags().contains(Tag.BUSY)) {
			runLogic();
			
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
					
					action.end();
					
					if (action.isContinuos()) {
						model.getTimeQueue().addAction(action);
					}
				}
				
				// TODO: dispose Action
			}
			
//			runLogic();
//		return !model.getPlayerHolder().getGameObject().isBusy();
		}
	}
	
	private void runLogic() {
		if (monster == null) {
			monster = model.getField().getCellOrException(0, 0).getObjectWithTag(Tag.CREATURE);
		}
		if (!monster.getTags().contains(Tag.BUSY)) {
			Map<ActionParameter, Object> context = new TreeMap<ActionParameter, Object>();
			context.put(ActionParameter.SOURCE_GAME_OBJECT, monster);
			List<FieldCell> path = APath.findPath(monster.getLocationCell(), player.getLocationCell());
			GenericAction monsterAction = null;
			if (path.size() > 2) {
				context.put(ActionParameter.TARGET_FIELD_CELL, path.get(1));
				monsterAction = new MoveAction(context);
			} else {
				context.put(ActionParameter.TARGET_GAME_OBJECT, player);
				monsterAction = new MeleAttackAction(context);
			}
			model.getTimeQueue().addAction(monsterAction);
		}
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
