package ru.ayeaye.game.test;

/**
 * Класс для тестирования статов, навыков, боя.
 * @author ayeaye
 */
public class CharacterTest {

	public static void main(String[] args) {
		Humanoid person1 = Humanoid.createHuman();
		person1.setOnHead(Armor.createHelmet());
		person1.setOnTorso(Armor.createPlateArmor());
		person1.setInRightHand(Weapon.createSword());
		person1.setInLeftHand(Armor.createShild());
//		person1.setInLeftHand(Weapon.createSword());
		
		Humanoid person2 = Humanoid.createHuman();
		person2.setOnHead(Armor.createHelmet());
		person2.setOnTorso(Armor.createPlateArmor());
		person2.setInBothHands(Weapon.createGreatSword());
		
		simulateCombat(person1, person2);
	}

	private static void simulateCombat(Humanoid person1, Humanoid person2) {
		
		Humanoid player1;
		Humanoid player2;
		if (Math.random() > 0.49999) {
			System.out.println("person2 goes first");
			player1 = person2;
			player2 = person1;
		} else {
			System.out.println("person1 goes first");
			player1 = person1;
			player2 = person2;
		}
		
		while (true) {
			System.out.println("person1: " + person1);
			System.out.println("person2: " + person2);
			break;
		}
	}

}
