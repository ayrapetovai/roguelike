package ru.ayeaye.game.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import ru.ayeaye.game.logic.actions.GenericAction;

public class TimeQueue {

	public class ActionHolder {
		public GenericAction action;
		float endTime;
		void init(GenericAction action) {
			this.action = action;
			endTime = action.getDelay() + currentTime;
		}
		
		@Override
		public String toString() {
			return "EH <action=" + action + ", endTime=" + endTime + ">";
		}
	}
	
	private class ActionHolderPool {
		private final LinkedList<ActionHolder> busy = new LinkedList<ActionHolder>();
		private final LinkedList<ActionHolder> free = new LinkedList<ActionHolder>();
		
		public ActionHolder get(GenericAction action) {
			ActionHolder ah = null;
			if (free.isEmpty()) {
				ah = new ActionHolder();
			} else {
				ah = free.poll();
			}
			
			busy.add(ah);
			ah.init(action);
			return ah;
		}
		
		public void putBack(ActionHolder ah) {
			busy.remove(ah);
			free.addFirst(ah);
		}
	}
	
	private class ActionHolderComparator implements Comparator<ActionHolder> {

		@Override
		public int compare(ActionHolder o1, ActionHolder o2) {
			if (o1.endTime - o2.endTime > 0) {
				return 1;
			} else {
				return -1;
			}
		}
	}
	
	private final ActionHolderPool actionHolderPool = new ActionHolderPool();
	
	private final ActionHolderComparator cmp = new ActionHolderComparator();
	
	private final LinkedList<ActionHolder> actionQueue = new LinkedList<ActionHolder>();
	
	private final List<GenericAction> actions = new ArrayList<GenericAction>();
	
	private float currentTime = 0f;
	
	public void addAction(GenericAction action) {
		if (action != null) {
			action.begin();
			actionQueue.addLast(actionHolderPool.get(action));
			actionQueue.sort(cmp);
		}
	}
	
	/**
	 * Do not modify result!
	 */
	public LinkedList<ActionHolder> getActions() {
		return actionQueue;
	}
	
	public List<GenericAction> getCurrentActions() {
		actions.clear();
		ActionHolder ah = actionQueue.pollFirst();
		
		if (ah != null) {
			actions.add(ah.action);
			while (actionQueue.peekFirst() != null && actionQueue.peekFirst().endTime == ah.endTime) {
				ActionHolder ee = actionQueue.pollFirst();
				actions.add(ee.action);
				actionHolderPool.putBack(ee);
			}
			currentTime = ah.endTime;
			actionHolderPool.putBack(ah);
		}
		// пусть события, кторые должны произойти одновременно, появятся в случайной последовательности
		// относительно друг-друга
		if (actions.size() > 1) {
			Collections.shuffle(actions);
		}
		return actions;
	}

	public float getCurrentTime() {
		return currentTime;
	}

	@Override
	public String toString() {
		return "TimeLine [currentTime=" + currentTime + ", actionQueue=" + actionQueue + "]";
	}

	public boolean isEmpty() {
		return actionQueue.isEmpty();
	}
	
}
