package ru.ayeaye.game.test;

public class Weapon implements Item {
	public static Item createSword() {
		Weapon sword = new Weapon();
		sword.setName("sword");
		sword.setAgility(-3);
		sword.setStrength(0);
		sword.setPenitration(20);
		return sword;
	}
	public static Item createGreatSword() {
		Weapon sword = new Weapon();
		sword.setName("great sword");
		sword.setAgility(-20);
		sword.setStrength(0);
		sword.setPenitration(50);
		return sword;
	}
	
	private String name;
	private int strength;
	private int agility;
	private int penitration;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStrength() {
		return strength;
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}
	public int getAgility() {
		return agility;
	}
	public void setAgility(int agility) {
		this.agility = agility;
	}
	public int getPenitration() {
		return penitration;
	}
	public void setPenitration(int penitration) {
		this.penitration = penitration;
	}

	@Override
	public void putOn(Humanoid targetCreature) {
		targetCreature.setAgility(
				targetCreature.getAgility() + this.getAgility()
				);
		targetCreature.setStrength(
				targetCreature.getStrength() + this.getStrength()
				);
		targetCreature.setPenitration(
				targetCreature.getPenitration() + this.getPenitration()
				);
	}

	@Override
	public void putOff(Humanoid targetCreature) {
		targetCreature.setAgility(
				targetCreature.getAgility() - this.getAgility()
				);
		targetCreature.setStrength(
				targetCreature.getStrength() - this.getStrength()
				);
		targetCreature.setPenitration(
				targetCreature.getPenitration() - this.getPenitration()
				);
	}
	@Override
	public String toString() {
		return "Weapon [name=" + name + ", strength=" + strength + ", agility="
				+ agility + ", penitration=" + penitration + "]";
	}

}
