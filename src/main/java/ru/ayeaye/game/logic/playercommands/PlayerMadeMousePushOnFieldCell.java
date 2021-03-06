package ru.ayeaye.game.logic.playercommands;

import java.util.Map;
import java.util.TreeMap;

import ru.ayeaye.game.logic.actions.ActionParameter;
import ru.ayeaye.game.logic.actions.ActionType;
import ru.ayeaye.game.logic.actions.FolowPathAction;
import ru.ayeaye.game.logic.actions.GenericAction;
import ru.ayeaye.game.logic.actions.MeleAttackAction;
import ru.ayeaye.game.logic.misc.Direction;
import ru.ayeaye.game.model.FieldCell;
import ru.ayeaye.game.model.GameModel;
import ru.ayeaye.game.model.GameObject;
import ru.ayeaye.game.model.Tag;

public class PlayerMadeMousePushOnFieldCell implements PlayerCommand {

	private FieldCell targetFiledCell;
	
	public void setTargetCell(FieldCell targetCell) {
		this.targetFiledCell = targetCell;
	}
	
	@Override
	public GenericAction createAction(GameModel model) {
		boolean cellsAreNear = targetFiledCell.isNeighborOf(model.getField().getPlayer().getLocationCell());
		boolean thereIsCreatureInCell = targetFiledCell.hasObjectWithTag(Tag.CREATURE); 
		Map<ActionParameter, Object> context = new TreeMap<>();
		context.put(ActionParameter.SOURCE_GAME_OBJECT, model.getField().getPlayer());
		context.put(ActionParameter.TARGET_FIELD_CELL, targetFiledCell);
		GenericAction action;
		if (cellsAreNear && thereIsCreatureInCell) {
			action = new MeleAttackAction(context);
			context.put(ActionParameter.TARGET_GAME_OBJECT, targetFiledCell.getObjectWithTag(Tag.CREATURE));
		} else
			action = new FolowPathAction(context);
		return action;
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
