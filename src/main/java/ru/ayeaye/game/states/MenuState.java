package ru.ayeaye.game.states;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.ayeaye.game.util.MenuAction;
import ru.ayeaye.game.util.Tuple;

public abstract class MenuState extends BasicGameState {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private List<Tuple<String, MenuAction>> options = new ArrayList<Tuple<String, MenuAction>>();
	private int maxOptionTitleLength = -1;
	
	public abstract String getHeader();
	
	public void addMenuOption(String title, MenuAction action) {
		Tuple<String, MenuAction> t = new Tuple<String, MenuAction>(title, action);
		options.add(t);
	}
	
	@Override
	public final void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
		int y = 150;
		String header = getHeader();
		if (header != null) {
			int headerWidth = g.getFont().getWidth(header);
			int x = container.getWidth() / 2 - headerWidth / 2;
			g.drawString(header, x, y);
		}
		
		// поле maxOptionTitleLength инициализируется один раз, при первом запуске render(...)
		if (maxOptionTitleLength < 0) {
			for (Tuple<String, MenuAction> option: options) {
				int optionTitleLength = g.getFont().getWidth(option.first); 
				if (maxOptionTitleLength < optionTitleLength) {
					maxOptionTitleLength = optionTitleLength;
				}
			}
		}
		
//		org/newdawn/slick/data/defaultfont.png
//		TrueTypeFont fnt = new TrueTypeFont(new Font("Courier", 40, 20), true);
//		fnt.drawString(120, 50, "Main menu");
		y += 50;
		int x = container.getWidth() / 2 - maxOptionTitleLength / 2;
		
		for (Tuple<String, MenuAction> option: options) {
			g.drawString(option.first, x, y);
			y += 50;
		}
		
	}

	@Override
	public final void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		for (Tuple<String, MenuAction> option: options) {
			option.second.applay();
		}
		
	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		container.getInput().clearKeyPressedRecord();
	}
}
