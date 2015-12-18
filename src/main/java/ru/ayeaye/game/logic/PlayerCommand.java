package ru.ayeaye.game.logic;

import ru.ayeaye.game.logic.actions.GenericAction;
import ru.ayeaye.game.logic.misc.Direction;
import ru.ayeaye.game.model.FieldCell;
import ru.ayeaye.game.model.GameModel;
import ru.ayeaye.game.model.GameObject;

public interface PlayerCommand {
	
	void setTargetDirection(Direction direction);
	void setTargetGameObject(GameObject gameObject);
	void setTargetFieldCell(FieldCell fieldCell);
	
	TargetSource getTargetSource();
	GenericAction createAction(GameModel model);
}
