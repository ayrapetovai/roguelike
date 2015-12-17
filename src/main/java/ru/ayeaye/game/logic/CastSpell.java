package ru.ayeaye.game.logic;

import ru.ayeaye.game.logic.misc.Direction;
import ru.ayeaye.game.model.FieldCell;
import ru.ayeaye.game.model.GameModel;
import ru.ayeaye.game.model.GameObject;
import test2.GenericAction;

public class CastSpell implements PlayerCommand {

	private FieldCell targetFieldCell;
	
	@Override
	public GenericAction createAction(GameModel model) {
//		return new CreatureCastsSpell(targetFieldCell.getObjectWithTag(Tag.CREATURE));
		return null;
	}

	@Override
	public TargetSource getTargetSource() {
		return TargetSource.FIELDCELL;
	}

	@Override
	public void setTargetDirection(Direction direction) {
	}

	@Override
	public void setTargetGameObject(GameObject gameObject) {
	}

	@Override
	public void setTargetFieldCell(FieldCell fieldCell) {
		this.targetFieldCell = fieldCell;		
	}
}
