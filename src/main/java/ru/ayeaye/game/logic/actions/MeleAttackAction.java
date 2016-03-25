package ru.ayeaye.game.logic.actions;

import java.util.Map;

import ru.ayeaye.game.model.GameObject;

public class MeleAttackAction extends GenericAction {

	public MeleAttackAction(Map<ActionParameter, Object> context) {
		super(context);
	}

	@Override
	public float getDelay() {
		GameObject sourceGO = (GameObject) context.get(ActionParameter.SOURCE_GAME_OBJECT);
		return algo.getMeleAttackDelay(sourceGO);
	}

	@Override
	public void execute() {
		GameObject sourceGO = (GameObject) context.get(ActionParameter.SOURCE_GAME_OBJECT);
		GameObject targetGO = (GameObject) context.get(ActionParameter.TARGET_GAME_OBJECT);
		algo.doMeleAttack(sourceGO, targetGO);
	}

	@Override
	public boolean isContinuos() {
		return false;
	}

	@Override
	public ActionType getActionType() {
		return ActionType.MELE_ATACK;
	}

}
