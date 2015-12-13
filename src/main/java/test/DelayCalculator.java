package test;

import java.util.Map;

public interface DelayCalculator {
	float calcDelay(Map<ActionParameter, Object> context);
}
