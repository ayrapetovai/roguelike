package test;

import java.util.Map;

public interface Condition {
	boolean check(Map<ActionParameter, Object> context);
}
