package ru.ayeaye.game.logic;

import java.util.Map;
import java.util.TreeMap;

import ru.ayeaye.game.logic.actions.MeleeAttack;
import ru.ayeaye.game.logic.actions.MoveCreatureOnOneCell;
import ru.ayeaye.game.logic.misc.Direction;
import ru.ayeaye.game.model.GenericAction;
import ru.ayeaye.game.model.FieldCell;
import ru.ayeaye.game.model.GameModel;
import ru.ayeaye.game.model.GameObject;
import ru.ayeaye.game.model.Tag;
import test.ActionParameter;
import test.ActionType;
import test.GenericActionPool;

public class PlayerPushWalkKey implements PlayerCommand {

	private Direction direction;
	
	@Override
	public test.GenericAction createAction(GameModel model) {
		
		FieldCell targetFiledCell = model.getField().getPlayer().getLocationCell().getNeighbourCell(direction);
		if (targetFiledCell == null) {
			return null;
		}
		
		boolean thereIsCreatureInCell = targetFiledCell.hasObjectWithTag(Tag.CREATURE); 
		if (thereIsCreatureInCell) {
//			return new MeleeAttack(model.getField().getPlayer(), targetFiledCell.getObjectWithTag(Tag.CREATURE));
			return null;
		} else {
			test.GenericAction action = GenericActionPool.getAction(ActionType.WALK);
			Map<ActionParameter, Object> context = new TreeMap<>();
			context.put(ActionParameter.SOURCE_GAME_OBJECT, model.getField().getPlayer());
			context.put(ActionParameter.TARGET_FIELD_CELL, model.getField().getPlayer().getLocationCell().getNeighbourCell(direction));
			action.setContext(context);
			return action;
//			return new MoveCreatureOnOneCell(model.getField().getPlayer(), direction);
		}
	}

	@Override
	public TargetSource getTargetSource() {
		return TargetSource.DIRECTION;
	}

	@Override
	public void setTargetDirection(Direction direction) {
		this.direction = direction;
	}

	@Override
	public void setTargetGameObject(GameObject gameObject) {
	}

	@Override
	public void setTargetFieldCell(FieldCell fieldCell) {
	}

}
