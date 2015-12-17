package ru.ayeaye.game.logic.actions;

import ru.ayeaye.game.model.Attribute;
import ru.ayeaye.game.model.GameModel;
import ru.ayeaye.game.model.GameObject;
import ru.ayeaye.game.model.Tag;

public class CreatureCastsSpell implements GenericAction {

	private GameObject target;
	
	public CreatureCastsSpell(GameObject target) {
		this.target = target;
	}
	
	@Override
	public boolean isContinuous() {
		return false;
	}

	@Override
	public float getDelay() {
		return 0.5f;
	}

	@Override
	public boolean canApplay(GameModel model) {
		return target.getTags().contains(Tag.CREATURE);
	}

	@Override
	public void applay(GameModel model) {
		System.out.println(target.getAttributes().get(Attribute.DESCRIPTION) + " is under cast!");
	}

	@Override
	public boolean canPutIntoTimeQueue(GameModel model) {
		return target.getTags().contains(Tag.CREATURE);
	}

	@Override
	public String getDesctiption() {
		return target.getAttributes().get(Attribute.DESCRIPTION) + " is under cast!";
	}

	@Override
	public GenericAction getPostAction() {
		return null;
	}
}
