package ru.ayeaye.game.display.widgets;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import ru.ayeaye.game.display.ImageConstants;
import ru.ayeaye.game.logic.actions.ActionParameter;
import ru.ayeaye.game.logic.actions.GenericAction;
import ru.ayeaye.game.model.GameObject;
import ru.ayeaye.game.model.TimeQueue;
import ru.ayeaye.game.model.TimeQueue.ActionHolder;

public class TimeQueueWidget extends Widget {
	private TimeQueue timeQueue;

	public TimeQueueWidget(String name, int width, int height) {
		super(name, width, height);
	}

	@Override
	public void customRender(Graphics graphcis, int absoluteX, int absoluteY, int minWidth, int minHeight) {
		int y = absoluteY;
		for (ActionHolder ah: timeQueue.getActions()) {
			GenericAction action = ah.action;
			
			Image actionImage = ImageConstants.getInstance().exe; //TODO: action.getIcon();
			GameObject source = (GameObject)action.getContext().get(ActionParameter.SOURCE_GAME_OBJECT);
			Image sourceImage = source.getImage();
			
			graphcis.drawImage(sourceImage, absoluteX + 4, y);
			graphcis.drawImage(actionImage, absoluteX + 8 + sourceImage.getWidth(), y);

			y += actionImage.getHeight() + 4;
		}
	}
	
	public void setTimeQueue(TimeQueue timeQueue) {
		this.timeQueue = timeQueue;
	}

}
