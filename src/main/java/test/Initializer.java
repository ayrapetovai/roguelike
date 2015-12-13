package test;

import java.util.Map;

public interface Initializer {
	void init(Map<ActionParameter, Object> context);
}
