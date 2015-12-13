package ru.ayeaye.game.logic;

import java.util.Map;
import java.util.TreeMap;

import ru.ayeaye.game.logic.misc.Direction;
import ru.ayeaye.game.model.FieldCell;
import ru.ayeaye.game.model.GameModel;
import ru.ayeaye.game.model.GameObject;
import ru.ayeaye.game.model.Tag;
import test.ActionParameter;
import test.ActionType;
import test.GenericActionPool;

public class PlayerMadeMousePushOnFieldCell implements PlayerCommand {

	private FieldCell targetFiledCell;
	
	public void setTargetCell(FieldCell targetCell) {
		this.targetFiledCell = targetCell;
	}
	
	@Override
	public test.GenericAction createAction(GameModel model) {
		boolean cellsAreNear = targetFiledCell.isNeighborOf(model.getField().getPlayer().getLocationCell());
		boolean thereIsCreatureInCell = targetFiledCell.hasObjectWithTag(Tag.CREATURE); 
		if (thereIsCreatureInCell && cellsAreNear) {
//			return new MeleeAttack(model.getField().getPlayer(), targetFiledCell.getObjectWithTag(Tag.CREATURE));
			return null;
		} else {
			test.GenericAction action = GenericActionPool.getAction(ActionType.WALK_THROW_PATH);
			Map<ActionParameter, Object> context = new TreeMap<>();
			context.put(ActionParameter.SOURCE_GAME_OBJECT, model.getField().getPlayer());
			context.put(ActionParameter.TARGET_FIELD_CELL, targetFiledCell);
			action.setContext(context);
			action.init();
			return action;
//			return new MoveCreatureToCell(model.getField().getPlayer(), targetFiledCell);
		}
	}

	@Override
	public TargetSource getTargetSource() {
		return TargetSource.FIELDCELL;
	}

	@Override
	public void setTargetDirection(Direction direction) {
	}

	@Override
	public void setTargetGameObject(GameObject gameObject) {
	}

	@Override
	public void setTargetFieldCell(FieldCell fieldCell) {
		this.targetFiledCell = fieldCell;
	}
}
