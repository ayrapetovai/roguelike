package ru.ayeaye.game.test;

import java.util.LinkedList;
import java.util.List;

public class Inventory {
	private final Humanoid owner;
	private final List<Item> items = new LinkedList<Item>();
	
	public Inventory(Humanoid owner) {
		this.owner = owner;
	}

}
