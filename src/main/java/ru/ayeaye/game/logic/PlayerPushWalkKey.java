package ru.ayeaye.game.logic;

import java.util.Map;
import java.util.TreeMap;

import ru.ayeaye.game.logic.misc.Direction;
import ru.ayeaye.game.model.FieldCell;
import ru.ayeaye.game.model.GameModel;
import ru.ayeaye.game.model.GameObject;
import ru.ayeaye.game.model.Tag;
import test2.ActionParameter;
import test2.ActionType;
import test2.GenericAction;

public class PlayerPushWalkKey implements PlayerCommand {

	private Direction direction;
	
	@Override
	public GenericAction createAction(GameModel model) {
		
		FieldCell targetFiledCell = model.getField().getPlayer().getLocationCell().getNeighbourCell(direction);
		if (targetFiledCell == null) {
			return null;
		}
		Map<ActionParameter, Object> context = new TreeMap<>();
		ActionType at;
		if (targetFiledCell.hasObjectWithTag(Tag.CREATURE)) {
			at = ActionType.ATTACK;
			context.put(ActionParameter.TARGET_GAME_OBJECT, targetFiledCell.getObjectWithTag(Tag.CREATURE));
		} else {
			at = ActionType.MOVE_TO_CELL;
		}
		test2.GenericAction action = new GenericAction(at);
		context.put(ActionParameter.SOURCE_GAME_OBJECT, model.getField().getPlayer());
		context.put(ActionParameter.TARGET_FIELD_CELL, model.getField().getPlayer().getLocationCell().getNeighbourCell(direction));
		action.setContext(context);
		return action;
//			return new MoveCreatureOnOneCell(model.getField().getPlayer(), direction);
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
