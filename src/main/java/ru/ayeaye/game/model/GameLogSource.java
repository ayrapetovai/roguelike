package ru.ayeaye.game.model;

import java.util.LinkedList;
import java.util.List;

public class GameLogSource {
	final LinkedList<String> logEntries = new LinkedList<>();
	private int maxSize = 100;
	
	public GameLogSource() {
	}
	
	public GameLogSource(int maxSize) {
		if (maxSize < 1) {
			throw new IllegalArgumentException("Log size cannot be " + maxSize + ", it can be greater then 0");
		}
		this.maxSize = maxSize;
	}
	
	/**
	 * Использовать только для отображения на экране
	 * @return
	 */
	public List<String> getLogEntries() {
		return logEntries;
	}
	
	public void addEntry(String entry) {
		while (logEntries.size() >= maxSize) {
			logEntries.removeLast();
		}
		logEntries.push(entry);
	}
}
