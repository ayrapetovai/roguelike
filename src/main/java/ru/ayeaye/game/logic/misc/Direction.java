package ru.ayeaye.game.logic.misc;

public enum Direction {
	
	LEFT_DOWN(true),
	DOWN(false),
	RIGHT_DOWN(true),
	
	LEFT(false),
	HALT(false),
	RIGHT(false),
	
	LEFT_UP(true),
	UP(false),
	RIGHT_UP(true),

	NONE(false);

	private final boolean diagonal;
	
	private Direction(boolean diagonal) {
		this.diagonal = diagonal;
	}
	
	public boolean isDiagonal() {
		return diagonal;
	}
}
