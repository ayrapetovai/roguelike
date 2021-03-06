package ru.ayeaye.game.logic.actions;

import java.util.Map;

import ru.ayeaye.game.model.FieldCell;
import ru.ayeaye.game.model.GameObject;

public class FolowPathAction extends GenericAction {

	public FolowPathAction(Map<ActionParameter, Object> context) {
		super(context);
	}

	@Override
	public void execute() {
		GameObject sourceGO = (GameObject) context.get(ActionParameter.SOURCE_GAME_OBJECT);
		FieldCell targetCell = (FieldCell) context.get(ActionParameter.TARGET_FIELD_CELL);
		algo.doMoveThrowPath(sourceGO, targetCell);
	}

	@Override
	public float getDelay() {
		GameObject sourceGO = (GameObject) context.get(ActionParameter.SOURCE_GAME_OBJECT);
		FieldCell targetCell = (FieldCell) context.get(ActionParameter.TARGET_FIELD_CELL);
		return algo.getMovePathDelay(sourceGO, targetCell);
	}

	@Override
	public boolean isContinuos() {
		GameObject sourceGO = (GameObject) context.get(ActionParameter.SOURCE_GAME_OBJECT);
		FieldCell targetCell = (FieldCell) context.get(ActionParameter.TARGET_FIELD_CELL);
		return algo.isMoveThrowPathContinuos(sourceGO, targetCell);
	}

	@Override
	public ActionType getActionType() {
		return ActionType.MOVE_THROW_PATH;
	}

}
