package test;

import java.util.List;
import java.util.Map;

import ru.ayeaye.game.model.FieldCell;
import ru.ayeaye.game.model.GameObject;
import ru.ayeaye.game.util.APath;

public class Initializers {

	public static final Initializer InitPath = (Map<ActionParameter, Object> context) -> {
		GameObject goTarget = (GameObject) context.get(ActionParameter.SOURCE_GAME_OBJECT);
		FieldCell fsTarget = (FieldCell) context.get(ActionParameter.TARGET_FIELD_CELL);
		
		List<FieldCell> path = APath.findPath(goTarget.getLocationCell(), fsTarget);
		context.put(ActionParameter.PATH_FIELD_CELLS, path);
		int counter = 1;
		if (path.size() > 1)
			context.put(ActionParameter.TARGET_FIELD_CELL, path.get(counter));
		
		context.put(ActionParameter.COUNTER_INTEGER, counter);
	};

}
