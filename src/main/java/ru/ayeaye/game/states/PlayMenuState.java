package ru.ayeaye.game.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class PlayMenuState extends MenuState {

	public static int ID = StateIdGenerator.getNextId();
	
	@Override
	public int getID() {
		return ID;
	}

	@Override
	public String getHeader() {
		return null; // нет заголовка
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		addMenuOption("[S]ave game and return to main menu",
				()-> {
					if (container.getInput().isKeyPressed(Input.KEY_S)) {
						log.debug("Save game");
						game.enterState(MainMenuState.ID);
					}
				});
		addMenuOption("[G]o to main menu",
				()-> {
					if (container.getInput().isKeyPressed(Input.KEY_G)) {
						game.enterState(MainMenuState.ID);
					}
				});
		addMenuOption("[R]eturn to game",
				()-> {
					if (container.getInput().isKeyPressed(Input.KEY_R)) {
						game.enterState(PlayState.ID);
					}
				});
	}

}
