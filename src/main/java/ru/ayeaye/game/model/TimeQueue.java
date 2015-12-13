package ru.ayeaye.game.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class TimeQueue {

	private class ActionHolder {
		test.GenericAction action;
		float endTime;
		public void init(test.GenericAction action) {
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
		
		public ActionHolder get(test.GenericAction action) {
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
	
	private final List<test.GenericAction> actions = new ArrayList<test.GenericAction>();
	
	private float currentTime = 0f;
	
	public void addAction(test.GenericAction action) {
		if (action != null) {
			actionQueue.addLast(actionHolderPool.get(action));
			actionQueue.sort(cmp);
		}
	}
	
	public List<test.GenericAction> getCurrentActions() {
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
		// ����� �������, ������ ������ ��������� ������������, �������� � ��������� ������������������
		// ������������ ����-�����
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
