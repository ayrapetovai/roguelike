package test;

import java.util.List;
import java.util.Map;

import ru.ayeaye.game.model.FieldCell;
import ru.ayeaye.game.model.GameObject;

public class Executors {

	public static final Executer MoveToCell = (Map<ActionParameter, Object> context) -> {
		GameObject goTarget = (GameObject) context.get(ActionParameter.SOURCE_GAME_OBJECT);
		FieldCell fsTarget = (FieldCell) context.get(ActionParameter.TARGET_FIELD_CELL);
		
		FieldCell goTargetHomeCell = goTarget.getLocationCell();
		goTargetHomeCell.removeGameObject(goTarget);
		fsTarget.pushGameObject(goTarget);
	};
	public static final Executer MoveThrowPath = (Map<ActionParameter, Object> context) -> {
		GameObject goTarget = (GameObject) context.get(ActionParameter.SOURCE_GAME_OBJECT);
		FieldCell fsTarget = (FieldCell) context.get(ActionParameter.TARGET_FIELD_CELL);
		List<FieldCell> path = (List<FieldCell>) context.get(ActionParameter.PATH_FIELD_CELLS);
		
		FieldCell goTargetHomeCell = goTarget.getLocationCell();
		goTargetHomeCell.removeGameObject(goTarget);
		fsTarget.pushGameObject(goTarget);
		
		int counter = (Integer) context.get(ActionParameter.COUNTER_INTEGER) + 1;
		if (counter < path.size())
			context.put(ActionParameter.TARGET_FIELD_CELL, path.get(counter));
		context.put(ActionParameter.COUNTER_INTEGER, counter);
	};

}
