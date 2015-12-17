package test2;

import java.util.List;
import java.util.Map;

import ru.ayeaye.game.model.Attribute;
import ru.ayeaye.game.model.FieldCell;
import ru.ayeaye.game.model.GameObject;
import ru.ayeaye.game.model.Tag;
import ru.ayeaye.game.util.APath;

public class Algorithm {
	private Map<ActionParameter, Object> context;
	private ActionType actionType;
	
	public Algorithm(Map<ActionParameter, Object> context, ActionType actionType) {
		this.context = context;
		this.actionType = actionType;
	}
	
	public boolean execute() {
		GameObject sourceGO = (GameObject) context.get(ActionParameter.SOURCE_GAME_OBJECT);
		GameObject targetGO = (GameObject) context.get(ActionParameter.TARGET_GAME_OBJECT);
		
		FieldCell targetCell = (FieldCell) context.get(ActionParameter.TARGET_FIELD_CELL);
		
		switch (actionType) {
		case ATTACK:
			attack(sourceGO, targetGO);
			break;
		case MOVE_TO_CELL:
			moveNextCell(sourceGO, targetCell);
			break;
		case MOVE_THROW_PATH:
			moveThrowPath(sourceGO, targetCell);
			break;
		default:
			throw new IllegalStateException("No actoin implementation for " + actionType);
		}
		return true;
	}
	
	public float getDelay() {
		GameObject sourceGO = (GameObject) context.get(ActionParameter.SOURCE_GAME_OBJECT);;
		FieldCell targetCell = (FieldCell) context.get(ActionParameter.TARGET_FIELD_CELL);
		
		float ret;
		switch (actionType) {
		case ATTACK:
			ret = getAttackDelay(sourceGO);
			break;
		case MOVE_TO_CELL:
			ret = getMoveDelay(sourceGO, targetCell);
			break;
		case MOVE_THROW_PATH:
			ret = getMovePathDelay(sourceGO, targetCell);
			break;
		default:
			throw new IllegalStateException("No actoin implementation for " + actionType);
		}
		return ret;
	}
	
	public boolean isContinuos() {
		GameObject sourceGO = (GameObject) context.get(ActionParameter.SOURCE_GAME_OBJECT);;
		FieldCell targetCell = (FieldCell) context.get(ActionParameter.TARGET_FIELD_CELL);
		
		boolean ret;
		switch (actionType) {
		case ATTACK:
			ret = false;
			break;
		case MOVE_TO_CELL:
			ret = false;
			break;
		case MOVE_THROW_PATH:
			ret = pathIsNotOver();
			break;
		default:
			throw new IllegalStateException("No actoin implementation for " + actionType);
		}
		return ret;
	}

	//***************************************************************
	// алгоритмы расчета времени выполнения действия
	float getMoveDelay(GameObject sourceGO, FieldCell targetCell) {
		return (Float) sourceGO.getAttributes().get(Attribute.MOVE_SPEED_FLOAT) *
				(sourceGO.getLocationCell().getDirectionTo(targetCell).isDiagonal()? 1.2f: 1f);
	}
	float getAttackDelay(GameObject sourceGO) {
		return (Float) sourceGO.getAttributes().get(Attribute.ATTACK_SPEED_FLOAT);
	}
	float getMovePathDelay(GameObject sourceGO, FieldCell targetCell) {
		if (calcPath(sourceGO, targetCell) && pathIsNotOver()) {
			return getMoveDelay(sourceGO, getNextPathCell());
		}
		return 0;
	}
	//**************************************************************

	boolean canWalk(GameObject sourceGO){
		return sourceGO.getTags().contains(Tag.CAN_WALK);
	}
	boolean canFlye(GameObject sourceGO) {
		return sourceGO.getTags().contains(Tag.CAN_FLY);
	}
	boolean canMove(GameObject sourceGO){
		return canWalk(sourceGO) || canFlye(sourceGO);
	}
	private boolean canAttack(GameObject sourceGO) {
		return sourceGO.getTags().contains(Tag.CAN_ATTACK);
	}
	boolean hasCreature(FieldCell targetCell) {
		return targetCell.hasObjectWithTag(Tag.CREATURE);
	}
	boolean isStandable(FieldCell targetCell) {
		return !hasCreature(targetCell) && targetCell.hasObjectWithTag(Tag.STANDABLE);
	}
	boolean areNear(GameObject sourceGO, FieldCell targetCell) {
		return sourceGO.getLocationCell().isNeighborOf(targetCell);
	}
	boolean areNear(GameObject sourceGO, GameObject targetGO) {
		return sourceGO.getLocationCell().isNeighborOf(targetGO.getLocationCell());
	}
	boolean putCreatureInCell(GameObject sourceGO, FieldCell targetCell) {
		sourceGO.getLocationCell().popGameObject();
		targetCell.pushGameObject(sourceGO);
		return true;
	}
	boolean isDestractable(GameObject sourceGO) {
		return sourceGO.getTags().contains(Tag.DESTRACTABLE);
	}
	boolean isDestroyed(GameObject targetGO) {
		int hp = (Integer) targetGO.getAttributes().get(Attribute.HIT_POINTS_INT);
		return hp <= 0;
	}
	boolean makeMeleDamage(GameObject sourceGO, GameObject targetGO) {
		Integer targetHitPoints = (Integer) targetGO.getAttributes().get(Attribute.HIT_POINTS_INT);
		if (targetHitPoints > 0) {
			Integer attackPoints = (Integer) sourceGO.getAttributes().get(Attribute.ATTACK_POINTS_INT);
			int resultTargetHitPoints = targetHitPoints - attackPoints;
			targetGO.getAttributes().put(Attribute.HIT_POINTS_INT, resultTargetHitPoints);
			return true;
		} else {
			return false;
		}
	}
	boolean markDestroyed(GameObject targetGO) {
		targetGO.getLocationCell().removeGameObject(targetGO);
		return targetGO.getTags().add(Tag.DESTROYED);
	}
	boolean attack(GameObject sourceGO, GameObject targetGO) {
		return canMove(sourceGO) && areNear(sourceGO, targetGO) &&
				isDestractable(targetGO) && canAttack(sourceGO) && !isDestroyed(targetGO) &&
				makeMeleDamage(sourceGO, targetGO) && isDestroyed(targetGO) && markDestroyed(targetGO);
	}
	boolean moveThrowPath(GameObject sourceGO, FieldCell targetCell) {
		return calcPath(sourceGO, targetCell) && pathIsNotOver() && moveNextCell(sourceGO, getNextPathCell()) && proceedOnPath();
	}
	boolean moveNextCell(GameObject sourceGO, FieldCell targetCell) {
		return canMove(sourceGO) && areNear(sourceGO, targetCell) &&
				(
						isStandable(targetCell) && putCreatureInCell(sourceGO, targetCell)
							||
						hasCreature(targetCell) && attack(sourceGO, getCreature(targetCell))
				);
	}
	
	//********************************************************
	// Работа с путем от одной клетки до другой
	boolean calcPath(GameObject sourceGO, FieldCell targetCell) {
		List<FieldCell> path = (List<FieldCell>) context.get(ActionParameter.PATH_FIELD_CELLS);
		int counter = 1;
		if (path == null) {
			path = APath.findPath(sourceGO.getLocationCell(), targetCell);
			context.put(ActionParameter.PATH_FIELD_CELLS, path);
			context.put(ActionParameter.COUNTER_INTEGER, counter);
		}
		
		if (path.size() > 1)
			context.put(ActionParameter.TARGET_FIELD_CELL, path.get(counter));
		else
			return false;
		return true;
	}
	boolean pathIsNotOver() {
		List<FieldCell> path = (List<FieldCell>) context.get(ActionParameter.PATH_FIELD_CELLS);
		int counter = (Integer) context.get(ActionParameter.COUNTER_INTEGER);
		if (counter < path.size()) {
			FieldCell nextCell = path.get(counter);
			return isStandable(nextCell);
		} else {
			return false;
		}
	}
	FieldCell getNextPathCell() {
		List<FieldCell> path = (List<FieldCell>) context.get(ActionParameter.PATH_FIELD_CELLS);
		int counter = (Integer) context.get(ActionParameter.COUNTER_INTEGER);
		if (counter < path.size())
			return path.get(counter);
		return null; 
	}
	boolean proceedOnPath() {
		int counter = (Integer) context.get(ActionParameter.COUNTER_INTEGER) + 1;
		context.put(ActionParameter.COUNTER_INTEGER, counter);
		return true; 
	}
	//********************************************************
	
	GameObject getCreature(FieldCell targetCell) {
		return targetCell.getObjectWithTag(Tag.CREATURE);
	}
}
