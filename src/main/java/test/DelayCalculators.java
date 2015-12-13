package test;

import java.util.Map;

import ru.ayeaye.game.model.FieldCell;
import ru.ayeaye.game.model.GameObject;

public class DelayCalculators {

	public static final DelayCalculator WalkDelayCalculator = (Map<ActionParameter, Object> context) -> {
		GameObject goTarget = (GameObject) context.get(ActionParameter.SOURCE_GAME_OBJECT);
		FieldCell fsTarget = (FieldCell) context.get(ActionParameter.TARGET_FIELD_CELL);
		
		return goTarget.getLocationCell().getDirectionTo(fsTarget).isDiagonal()? 1.2f: 1f;
	};

}
