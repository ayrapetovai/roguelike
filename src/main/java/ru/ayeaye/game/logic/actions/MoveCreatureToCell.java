package ru.ayeaye.game.logic.actions;

import java.util.List;

import ru.ayeaye.game.model.FieldCell;
import ru.ayeaye.game.model.GameModel;
import ru.ayeaye.game.model.GameObject;
import ru.ayeaye.game.model.GenericAction;
import ru.ayeaye.game.model.Tag;
import ru.ayeaye.game.util.APath;

public class MoveCreatureToCell implements GenericAction {

	private final GameObject subject;
	
	private int nextCellNum;
	private final List<FieldCell> path;
	
	public MoveCreatureToCell(GameObject subject, FieldCell targetCell) {
		this.subject = subject;
		this.nextCellNum = 1;
		this.path = APath.findPath(subject.getLocationCell(), targetCell);
	}

	@Override
	public float getDelay() {
		// FIXME: ������ ���� ��������� ���� �� ������, �� ������� �������� ��� ���������
		return path.get(nextCellNum).getDirectionTo(subject.getLocationCell()).isDiagonal()? 1.2f: 1f;
	}

	@Override
	public boolean canApplay(GameModel model) {
		return subject.getTags().contains(Tag.CAN_WALK) && nextCellNum < path.size() && path.get(nextCellNum).hasObjectWithTag(Tag.STANDABLE) && !path.get(nextCellNum).hasObjectWithTag(Tag.CREATURE); // ���� ���� ����
	}

	@Override
	public void applay(GameModel model) {
		FieldCell subjectLocation = subject.getLocationCell();
		FieldCell targetCell = path.get(nextCellNum++);
		subjectLocation.removeGameObject(subject);
		targetCell.pushGameObject(subject);
	}
	
	@Override
	public String getDesctiption() {
		return subject.getDescription() + " goes to " + path.get(nextCellNum);
	}

	@Override
	public boolean isContinuous() {
		return nextCellNum < path.size();
	}

	@Override
	public boolean canPutIntoTimeQueue(GameModel model) {
		return canApplay(model);
	}

	@Override
	public GenericAction getPostAction() {
		// ���� ����� �� ������ ����������, � � ��� ���-�� ���� - ����� ��� ����!
		if (nextCellNum == path.size() - 1) {
			GameObject target = path.get(nextCellNum).getObjectWithTag(Tag.CREATURE);
			if (target != null) {
				return new MeleeAttack(subject, target);
			}
		}
		
		return null;
	}

}
