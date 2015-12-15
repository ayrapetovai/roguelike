package ru.ayeaye.game.logic.actions;

import ru.ayeaye.game.logic.misc.Direction;
import ru.ayeaye.game.model.Attribute;
import ru.ayeaye.game.model.FieldCell;
import ru.ayeaye.game.model.GameModel;
import ru.ayeaye.game.model.GameObject;
import ru.ayeaye.game.model.GenericAction;
import ru.ayeaye.game.model.Tag;

public class MoveCreatureOnOneCell implements GenericAction {

	private final Direction direction;
	private final GameObject subject;
	
	public MoveCreatureOnOneCell(GameObject subject, Direction direction) {
		this.direction = direction;
		this.subject = subject;
	}

	@Override
	public float getDelay() {
		return direction.isDiagonal()? 1.2f: 1f;
	}

	@Override
	public boolean canApplay(GameModel model) {
		FieldCell targetCell = subject.getLocationCell().getNeighbourCell(direction);
		return targetCell != null && targetCell.hasObjectWithTag(Tag.STANDABLE) && !targetCell.hasObjectWithTag(Tag.CREATURE);
	}
	
	@Override
	public void applay(GameModel model) {
		if (subject.getTags().contains(Tag.CAN_WALK)) {
			FieldCell subjectLocation = subject.getLocationCell();
			FieldCell targetCell = subjectLocation.getNeighbourCell(direction);
			subjectLocation.removeGameObject(subject);
			targetCell.pushGameObject(subject);
		} else {
			System.out.println(subject + " can't walk!");
		}
	}
	
	@Override
	public String getDesctiption() {
		return subject.getAttributes().get(Attribute.DESCRIPTION) + " goes to " + direction;
	}

	@Override
	public boolean isContinuous() {
		return false;
	}

	@Override
	public boolean canPutIntoTimeQueue(GameModel model) {
		return canApplay(model);
	}

	@Override
	public GenericAction getPostAction() {
		return null;
	}
}
