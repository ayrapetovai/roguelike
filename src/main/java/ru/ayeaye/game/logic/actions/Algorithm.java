package ru.ayeaye.game.logic.actions;

import java.util.List;
import java.util.Map;

import ru.ayeaye.game.display.ImageConstants;
import ru.ayeaye.game.model.Attribute;
import ru.ayeaye.game.model.FieldCell;
import ru.ayeaye.game.model.GameObject;
import ru.ayeaye.game.model.Tag;
import ru.ayeaye.game.util.APath;

public class Algorithm {
	private final Map<ActionParameter, Object> context;

	public Algorithm(Map<ActionParameter, Object> context) {
		this.context = context;
	}
	
	//***************************************************************
	// алгоритмы расчета времени выполнения действия
	public float getMoveDelay(GameObject sourceGO, FieldCell targetCell) {
		return (Float) sourceGO.getAttributes().get(Attribute.MOVE_SPEED_FLOAT) *
				(sourceGO.getLocationCell().getDirectionTo(targetCell).isDiagonal()? 1.2f: 1f);
	}
	public float getMeleAttackDelay(GameObject sourceGO) {
		return (Float) sourceGO.getAttributes().get(Attribute.MELE_ATTACK_SPEED_FLOAT);
	}
	public float getConjureDelay(GameObject sourceGO) {
		return (Float) sourceGO.getAttributes().get(Attribute.CONJURE_SPEED_FLOAT);
	}
	public float getMovePathDelay(GameObject sourceGO, FieldCell targetCell) {
		if (calcPath(sourceGO, targetCell)) {
			if (pathIsNotOver() && isStandable(getNextPathCell()))
				return getMoveDelay(sourceGO, getNextPathCell());
		}
		return 0;
	}
	//**************************************************************

	private boolean canWalk(GameObject sourceGO){
		return sourceGO.getTags().contains(Tag.CAN_WALK);
	}
	private boolean canFlye(GameObject sourceGO) {
		return sourceGO.getTags().contains(Tag.CAN_FLY);
	}
	private boolean canMove(GameObject sourceGO){
		return canWalk(sourceGO) || canFlye(sourceGO);
	}
	private boolean canAttack(GameObject sourceGO) {
		return sourceGO.getTags().contains(Tag.CAN_ATTACK);
	}
	private boolean hasCreature(FieldCell targetCell) {
		return targetCell.hasObjectWithTag(Tag.CREATURE);
	}
	private boolean isStandable(FieldCell targetCell) {
		return !hasCreature(targetCell) && targetCell.hasObjectWithTag(Tag.STANDABLE);
	}
	private boolean areNear(GameObject sourceGO, FieldCell targetCell) {
		return sourceGO.getLocationCell().isNeighborOf(targetCell);
	}
	private boolean areNear(GameObject sourceGO, GameObject targetGO) {
		return sourceGO.getLocationCell().isNeighborOf(targetGO.getLocationCell());
	}
	private boolean putCreatureInCell(GameObject sourceGO, FieldCell targetCell) {
		sourceGO.getLocationCell().popGameObject();
		targetCell.pushGameObject(sourceGO);
		return true;
	}
	private boolean isDestractable(GameObject sourceGO) {
		return sourceGO.getTags().contains(Tag.DESTRACTABLE);
	}
	private boolean isDestroyed(GameObject targetGO) {
		int hp = (Integer) targetGO.getAttributes().get(Attribute.HIT_POINTS_INT);
		return hp <= 0;
	}
	private boolean makeMeleDamage(GameObject sourceGO, GameObject targetGO) {
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
	private boolean markDestroyed(GameObject targetGO) {
		targetGO.setImage(ImageConstants.getInstance().corpse);
		targetGO.getTags().remove(Tag.CREATURE);
		return targetGO.getTags().add(Tag.DESTROYED);
	}
	public boolean doMeleAttack(GameObject sourceGO, GameObject targetGO) {
		return canMove(sourceGO) && areNear(sourceGO, targetGO) &&
				isDestractable(targetGO) && canAttack(sourceGO) && !isDestroyed(targetGO) &&
				makeMeleDamage(sourceGO, targetGO) && isDestroyed(targetGO) && markDestroyed(targetGO);
	}
	public boolean doMoveThrowPath(GameObject sourceGO, FieldCell targetCell) {
		return calcPath(sourceGO, targetCell) && doMoveToCell(sourceGO, getNextPathCell());
	}
	public boolean doMoveToCell(GameObject sourceGO, FieldCell targetCell) {
		return canMove(sourceGO) && areNear(sourceGO, targetCell) && isStandable(targetCell) && putCreatureInCell(sourceGO, targetCell);
	}
	public boolean doConjure(GameObject sourceGO, GameObject targetGO) {
		System.out.println("BOOM!");
		return true;
	}
	//********************************************************
	// Работа с путем от одной клетки до другой
	public boolean isMoveThrowPathContinuos(GameObject sourceGO, FieldCell targetCell) {
		List<FieldCell> path = APath.findPath(sourceGO.getLocationCell(), targetCell);
		return path.size() > 1 && isStandable(path.get(1));
	}
	private boolean calcPath(GameObject sourceGO, FieldCell targetCell) {
		List<FieldCell> path = APath.findPath(sourceGO.getLocationCell(), targetCell);
		context.put(ActionParameter.PATH_FIELD_CELLS, path);
		return path.size() > 1;
	}
	private boolean pathIsNotOver() {
		List<FieldCell> path = (List<FieldCell>) context.get(ActionParameter.PATH_FIELD_CELLS);
		return path.size() > 1;
	}
	private FieldCell getNextPathCell() {
		List<FieldCell> path = (List<FieldCell>) context.get(ActionParameter.PATH_FIELD_CELLS);
		if (path.size() > 1)
			return path.get(1);
		return null; 
	}
	//********************************************************
	
	private GameObject getCreature(FieldCell targetCell) {
		return targetCell.getObjectWithTag(Tag.CREATURE);
	}
}
