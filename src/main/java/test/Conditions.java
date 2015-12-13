package test;

import java.util.List;
import java.util.Map;

import ru.ayeaye.game.model.FieldCell;
import ru.ayeaye.game.model.GameObject;
import ru.ayeaye.game.model.Tag;

public class Conditions {

	public static final Condition CanMoveToCell = (Map<ActionParameter, Object> context) -> {
		GameObject goTarget = (GameObject) context.get(ActionParameter.SOURCE_GAME_OBJECT);
		FieldCell fsTarget = (FieldCell) context.get(ActionParameter.TARGET_FIELD_CELL);
		return goTarget.getTags().contains(Tag.CAN_WALK) && fsTarget.hasObjectWithTag(Tag.STANDABLE) && !fsTarget.hasObjectWithTag(Tag.CREATURE);
	};
	public static final Condition PathIsNotOver = (Map<ActionParameter, Object> context) -> {
		List<FieldCell> path = (List<FieldCell>) context.get(ActionParameter.PATH_FIELD_CELLS);
		int counter = (Integer) context.get(ActionParameter.COUNTER_INTEGER);
		return counter < path.size() && !path.get(counter).hasObjectWithTag(Tag.CREATURE) && path.get(counter).hasObjectWithTag(Tag.STANDABLE);
	};

}
