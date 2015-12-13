package ru.ayeaye.game.states;

public class StateIdGenerator {
	private static int nextId = 0;
	
	public static int getNextId() {
		return nextId++;
	}
}
