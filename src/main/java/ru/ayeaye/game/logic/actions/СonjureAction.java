package ru.ayeaye.game.logic.actions;

import java.util.Map;

import ru.ayeaye.game.model.GameObject;

public class СonjureAction extends GenericAction {

	public СonjureAction(Map<ActionParameter, Object> context) {
		super(context);
	}

	@Override
	public void execute() {
		GameObject sourceGO = (GameObject) context.get(ActionParameter.SOURCE_GAME_OBJECT);
		GameObject targetGO = (GameObject) context.get(ActionParameter.TARGET_GAME_OBJECT);
		algo.doConjure(sourceGO, targetGO);
	}

	@Override
	public float getDelay() {
		GameObject sourceGO = (GameObject) context.get(ActionParameter.SOURCE_GAME_OBJECT);
		return algo.getConjureDelay(sourceGO);
	}

	@Override
	public boolean isContinuos() {
		return false;
	}

	@Override
	public ActionType getActionType() {
		return ActionType.CONJURE;
	}

}
