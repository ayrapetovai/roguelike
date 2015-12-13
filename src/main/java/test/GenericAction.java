package test;

import java.util.Map;

public class GenericAction {
	private final ActionType actionType;
	
	private Map<ActionParameter, Object> context;
	
	private Initializer initializer;
	private Condition canPutInTimeQueue;
	private Condition canExecute;
	private Condition canDoPostAction;
	private Executer executer;
	private DelayCalculator calcDelay;
	
	private GenericAction postAction;
	
	public GenericAction(ActionType actionType) {
		this.actionType = actionType;
	}
	
	public void init() {
		if (initializer != null)
			initializer.init(context);
	}
	
	public boolean canPutInTimeQueue() {
		return canPutInTimeQueue != null && canPutInTimeQueue.check(context);
	}
	
	public boolean canExecute() {
		return canExecute != null && canExecute.check(context);
	}
	
	public void execute() {
		if (executer != null)
			executer.execute(context);
	}
	
	public GenericAction getPostAction() {
		if (postAction != null && (canDoPostAction != null || canDoPostAction.check(context))) {
			return postAction;
		} else {
			return null;
		}
	}

	public float getDelay() {
		return calcDelay != null? calcDelay.calcDelay(context): 0f;
	}
	
	public ActionType getActionType() {
		return actionType;
	}
	
	public void setInitializer(Initializer initializer) {
		this.initializer = initializer;
	}
	
	public Map<ActionParameter, Object> getContext() {
		return context;
	}
	
	public void setContext(Map<ActionParameter, Object> context) {
		this.context = context;
	}

	public void setCanPutInTimeQueue(Condition canPutInTimeQueue) {
		this.canPutInTimeQueue = canPutInTimeQueue;
	}

	public void setCanExecute(Condition canExecute) {
		this.canExecute = canExecute;
	}

	public void setCanDoPostAction(Condition canDoPostAction) {
		this.canDoPostAction = canDoPostAction;
	}

	public void setExecuter(Executer executer) {
		this.executer = executer;
	}

	public void setPostAction(GenericAction postAction) {
		this.postAction = postAction;
	}
	
	public void setCalcDelay(DelayCalculator calcDelay) {
		this.calcDelay = calcDelay;
	}

}
