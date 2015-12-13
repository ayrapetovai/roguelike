package test;

import java.util.Map;
import java.util.TreeMap;

public class GenericActionPool {
	
	private static Map<ActionType, GenericAction> actions = new TreeMap<>();
	static {
		actions.put(ActionType.WALK, createWalkAction());
		actions.put(ActionType.WALK_THROW_PATH, createWalkThrowPath());
	}
	
	public static GenericAction getAction(ActionType actionType) {
		return actions.get(actionType);
	}

	private static GenericAction createWalkThrowPath() {
		GenericAction action = new GenericAction(ActionType.WALK);
		action.setInitializer(Initializers.InitPath);
		action.setCanPutInTimeQueue(Conditions.PathIsNotOver);
		action.setCanExecute(Conditions.CanMoveToCell);
		action.setExecuter(Executors.MoveThrowPath);
		action.setCalcDelay(DelayCalculators.WalkDelayCalculator);
		action.setCanDoPostAction(Conditions.PathIsNotOver);
		action.setPostAction(action);
		return action;
	}

	private static GenericAction createWalkAction() {
		GenericAction action = new GenericAction(ActionType.WALK);
		action.setCanPutInTimeQueue(Conditions.CanMoveToCell);
		action.setCanExecute(Conditions.CanMoveToCell);
		action.setExecuter(Executors.MoveToCell);
		action.setCalcDelay(DelayCalculators.WalkDelayCalculator);
		return action;
	}
}
