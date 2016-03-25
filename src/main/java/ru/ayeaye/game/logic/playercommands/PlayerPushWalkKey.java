package ru.ayeaye.game.logic.playercommands;

import java.util.Map;
import java.util.TreeMap;

import ru.ayeaye.game.logic.actions.ActionParameter;
import ru.ayeaye.game.logic.actions.GenericAction;
import ru.ayeaye.game.logic.actions.MeleAttackAction;
import ru.ayeaye.game.logic.actions.MoveAction;
import ru.ayeaye.game.logic.misc.Direction;
import ru.ayeaye.game.model.FieldCell;
import ru.ayeaye.game.model.GameModel;
import ru.ayeaye.game.model.GameObject;
import ru.ayeaye.game.model.Tag;

public class PlayerPushWalkKey implements PlayerCommand {

	private Direction direction;
	
	@Override
	public GenericAction createAction(GameModel model) {
		
		FieldCell targetFiledCell = model.getField().getPlayer().getLocationCell().getNeighbourCell(direction);
		if (targetFiledCell == null) {
			return null;
		}
		Map<ActionParameter, Object> context = new TreeMap<>();
		context.put(ActionParameter.SOURCE_GAME_OBJECT, model.getField().getPlayer());
		context.put(ActionParameter.TARGET_FIELD_CELL, model.getField().getPlayer().getLocationCell().getNeighbourCell(direction));

		GameObject creature = targetFiledCell.getObjectWithTag(Tag.CREATURE);
		GenericAction action;
		if (creature != null && creature != model.getField().getPlayer()) {
			context.put(ActionParameter.TARGET_GAME_OBJECT, targetFiledCell.getObjectWithTag(Tag.CREATURE));
			action = new MeleAttackAction(context);
		} else {
			action = new MoveAction(context);
		}
		return action;
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
