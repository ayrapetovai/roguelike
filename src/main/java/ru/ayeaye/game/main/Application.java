package ru.ayeaye.game.main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import ru.ayeaye.game.input.GameInputListener;
import ru.ayeaye.game.states.ChangeSettingsState;
import ru.ayeaye.game.states.PlayMenuState;
import ru.ayeaye.game.states.PlayState;
import ru.ayeaye.game.states.LoadGameState;
import ru.ayeaye.game.states.MainMenuState;

public class Application extends StateBasedGame {

	private static final String GMAE_NAME = "Game";
	
	public Application() {
		super(GMAE_NAME);
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		gc.getInput().addListener(GameInputListener.getInstance());
		
		addState(new MainMenuState());
		addState(new PlayState());
		addState(new ChangeSettingsState());
		addState(new LoadGameState());
		addState(new PlayMenuState());
	}

}
