package ru.ayeaye.game.logic.actions;

import java.util.Map;

import ru.ayeaye.game.model.FieldCell;
import ru.ayeaye.game.model.GameObject;

public class MoveAction extends GenericAction {

	public MoveAction(Map<ActionParameter, Object> context) {
		super(context);
	}

	@Override
	public void execute() {
		GameObject sourceGO = (GameObject) context.get(ActionParameter.SOURCE_GAME_OBJECT);
		FieldCell targetCell = (FieldCell) context.get(ActionParameter.TARGET_FIELD_CELL);
		algo.doMoveToCell(sourceGO, targetCell);
	}

	@Override
	public float getDelay() {
		GameObject sourceGO = (GameObject) context.get(ActionParameter.SOURCE_GAME_OBJECT);
		FieldCell targetCell = (FieldCell) context.get(ActionParameter.TARGET_FIELD_CELL);
		return algo.getMoveDelay(sourceGO, targetCell);
	}

	@Override
	public boolean isContinuos() {
		return false;
	}

	@Override
	public ActionType getActionType() {
		return ActionType.MOVE_TO_CELL;
	}

}
