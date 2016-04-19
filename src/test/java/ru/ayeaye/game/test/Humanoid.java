package ru.ayeaye.game.test;

/**
 * Существо
 * @author ayeaye
 *
 */
public class Humanoid implements LeavingBean, Cloneable {
	
	public static Humanoid createHuman() {
		Humanoid human = new Humanoid();
		human.setAgility(100);
		human.setEndurance(100);
		human.setIntelligence(100);
		human.setSolidity(10);
		human.setStrength(100);
		
		human.setPain(0);
		human.setPenitration(10);
		human.setConcentration(100);
		return human;
		
	}
	
	private int strength;
	private int agility;
	private int intelligence;
	
	private int penitration;
	private int solidity;
	
	private int endurance;
	private int concentration;
	private int pain;
	
	private Item inLeftHand;
	private Item inRightHand;
	
	private Item inBothHands;
	
	private Item onHead;
	private Item onTorso;
	private Item onHends;
	private Item onLegs;
	private Item coat;
	private Item belt;
	
	private final Inventory inventory = new Inventory(this);
	
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
	public int getIntelligence() {
		return intelligence;
	}
	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}
	public int getPenitration() {
		return penitration;
	}
	public void setPenitration(int penitration) {
		this.penitration = penitration;
	}
	public int getSolidity() {
		return solidity;
	}
	public void setSolidity(int solidity) {
		this.solidity = solidity;
	}
	public int getEndurance() {
		return endurance;
	}
	public void setEndurance(int endurance) {
		this.endurance = endurance;
	}
	public int getConcentration() {
		return concentration;
	}
	public void setConcentration(int concentration) {
		this.concentration = concentration;
	}
	public int getPain() {
		return pain;
	}
	public void setPain(int pain) {
		this.pain = pain;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public Item getInLeftHand() {
		return inLeftHand;
	}
	public void setInLeftHand(Item inLeftHand) {
		if (inLeftHand != this.inLeftHand && this.inLeftHand != null) {
			this.inLeftHand.putOff(this);
		}
		inLeftHand.putOn(this);
		this.inLeftHand = inLeftHand;
	}
	public Item getInRightHand() {
		return inRightHand;
	}
	public void setInRightHand(Item inRightHand) {
		if (inRightHand != this.inRightHand && this.inRightHand != null) {
			this.inRightHand.putOff(this);
		}
		inRightHand.putOn(this);
		this.inRightHand = inRightHand;
	}
	
	public Item getInBothHands() {
		return inBothHands;
	}
	public void setInBothHands(Item inBothHands) {
		if (inBothHands != this.inBothHands && this.inBothHands != null) {
			this.inBothHands.putOff(this);
		}
		inBothHands.putOn(this);
		this.inBothHands = inBothHands;
	}
	public Item getOnHead() {
		return onHead;
	}
	public void setOnHead(Item onHead) {
		if (onHead != this.onHead && this.onHead != null) {
			this.onHead.putOff(this);
		}
		onHead.putOn(this);
		this.onHead = onHead;
	}
	public Item getOnTorso() {
		return onTorso;
	}
	public void setOnTorso(Item onTorso) {
		if (onTorso != this.onTorso && this.onTorso != null) {
			this.onTorso.putOff(this);
		}
		onTorso.putOn(this);
		this.onTorso = onTorso;
	}
	public Item getOnHends() {
		return onHends;
	}
	public void setOnHends(Item onHends) {
		this.onHends = onHends;
	}
	public Item getOnLegs() {
		return onLegs;
	}
	public void setOnLegs(Item onLegs) {
		this.onLegs = onLegs;
	}
	public Item getCoat() {
		return coat;
	}
	public void setCoat(Item coat) {
		this.coat = coat;
	}
	public Item getBelt() {
		return belt;
	}
	public void setBelt(Item belt) {
		this.belt = belt;
	}
	@Override
	public Humanoid clone() {
		Humanoid clone = new Humanoid();
		clone.setAgility(agility);
		clone.setConcentration(concentration);
		clone.setEndurance(endurance);
		clone.setIntelligence(intelligence);
		clone.setPain(pain);
		clone.setPenitration(penitration);
		clone.setSolidity(solidity);
		clone.setStrength(strength);
		return clone;
		
	}
	@Override
	public String toString() {
		return "Humanoid [strength=" + strength + ", agility=" + agility
				+ ", intelligence=" + intelligence + ", penitration="
				+ penitration + ", solidity=" + solidity + ", endurance="
				+ endurance + ", concentration=" + concentration + ", pain="
				+ pain + ", inLeftHand=" + inLeftHand + ", inRightHand="
				+ inRightHand + ", inBothHands=" + inBothHands + ", onHead="
				+ onHead + ", onTorso=" + onTorso + ", onHends=" + onHends
				+ ", onLegs=" + onLegs + ", coat=" + coat + ", belt=" + belt
				+ ", inventory=" + inventory + "]";
	}
	
}
