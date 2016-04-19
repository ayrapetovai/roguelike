package ru.ayeaye.game.test;

public class Armor implements Item {
	public static Armor createHelmet() {
		Armor helmet = new Armor();
		helmet.name = "helmet";
		helmet.agility = -10;
		helmet.concentration = -20;
		helmet.solidity = 10;
		helmet.strength = -5;
		return helmet ;
	}

	public static Armor createPlateArmor() {
		Armor plateArmor = new Armor();
		plateArmor.name = "plate armor";
		plateArmor.agility = -30;
		plateArmor.concentration = -40;
		plateArmor.solidity = 30;
		plateArmor.strength = -30;
		return plateArmor ;
	}
	
	public static Armor createShild() {
		Armor plateArmor = new Armor();
		plateArmor.name = "shild";
		plateArmor.agility = -20;
		plateArmor.concentration = -20;
		plateArmor.solidity = 20;
		plateArmor.strength = -10;
		return plateArmor ;
	}
	
	private String name;
	private int strength;
	private int solidity;
	private int agility;
	private int concentration;
	
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

	public int getSolidity() {
		return solidity;
	}

	public void setSolidity(int solidity) {
		this.solidity = solidity;
	}

	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

	public int getConcentration() {
		return concentration;
	}

	public void setConcentration(int concentration) {
		this.concentration = concentration;
	}
	
	@Override
	public void putOn(Humanoid targetCreature) {
		targetCreature.setAgility(
				targetCreature.getAgility() + this.getAgility()
				);
		targetCreature.setStrength(
				targetCreature.getStrength() + this.getStrength()
				);
		targetCreature.setSolidity(
				targetCreature.getSolidity() + this.getSolidity()
				);
		targetCreature.setConcentration(
				targetCreature.getConcentration() + this.getConcentration()
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
		targetCreature.setSolidity(
				targetCreature.getSolidity() - this.getSolidity()
				);
		targetCreature.setConcentration(
				targetCreature.getConcentration() - this.getConcentration()
				);
	}

	@Override
	public String toString() {
		return "Armor [name=" + name + ", strength=" + strength + ", solidity="
				+ solidity + ", agility=" + agility + ", concentration="
				+ concentration + "]";
	}

}
