package ru.ayeaye.game.logic.actions;

import ru.ayeaye.game.model.GenericAction;
import ru.ayeaye.game.model.Attribute;
import ru.ayeaye.game.model.GameModel;
import ru.ayeaye.game.model.GameObject;
import ru.ayeaye.game.model.Tag;

public class MeleeAttack implements GenericAction {

	private final GameObject attacker;
	private final GameObject target;
	
	public MeleeAttack(GameObject attacker, GameObject target) {
		this.attacker = attacker;
		this.target = target;
	}

	@Override
	public boolean isContinuous() {
		return false;
	}

	@Override
	public float getDelay() {
		return 1; // TODO: тут должна быть скорость атаки
	}

	@Override
	public boolean canApplay(GameModel model) {
		return attacker.getLocationCell().isNeighborOf(target.getLocationCell());
	}

	@Override
	public void applay(GameModel model) {
		if (attacker.getTags().contains(Tag.CAN_ATTACK)) {
			Integer targetHitPoints = (Integer) target.getAttributes().get(Attribute.HIT_POINTS_INT);
			if (targetHitPoints > 0) {
				Integer attackPoints = (Integer) attacker.getAttributes().get(Attribute.ATTACK_POINTS_INT);
				int resultTargetHitPoints = targetHitPoints - attackPoints;
				target.getAttributes().put(Attribute.HIT_POINTS_INT, resultTargetHitPoints);
			}
		} else {
			System.out.println(attacker.getDescription() + " can't attack!");
		}

	}

	@Override
	public boolean canPutIntoTimeQueue(GameModel model) {
		return attacker != target;
	}

	@Override
	public GenericAction getPostAction() {
		// FIXME: для теста написано
		Integer targetHitPoints = (Integer) target.getAttributes().get(Attribute.HIT_POINTS_INT);
		if (targetHitPoints <= 0) {
			target.getLocationCell().removeGameObject(target);
		}
		return null;
	}

	@Override
	public String getDesctiption() {
		return attacker.getDescription() + " attacks " + target.getDescription();
	}
	
}
