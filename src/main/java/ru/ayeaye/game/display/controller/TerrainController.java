package ru.ayeaye.game.display.controller;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Input;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.ayeaye.game.display.ImageConstants;
import ru.ayeaye.game.display.widgets.Widget;
import ru.ayeaye.game.logic.PlayerCommand;
import ru.ayeaye.game.logic.CastSpell;
import ru.ayeaye.game.logic.GameLogicEngine;
import ru.ayeaye.game.logic.PlayerPushWalkKey;
import ru.ayeaye.game.logic.PlayerMadeMousePushOnFieldCell;
import ru.ayeaye.game.logic.misc.Direction;
import ru.ayeaye.game.model.GameField;

public class TerrainController extends WidgetController {

	private static final Logger log = LoggerFactory.getLogger(TerrainController.class);
	
	private static Map<Integer, PlayerCommand> keyMapping;
	private static Map<Integer, Direction> keyDirectionMapping;
	static {
		keyMapping = new HashMap<>();
		
		keyMapping.put(Input.KEY_NUMPAD2, new PlayerPushWalkKey());
		keyMapping.put(Input.KEY_NUMPAD4, new PlayerPushWalkKey());
		keyMapping.put(Input.KEY_NUMPAD6, new PlayerPushWalkKey());
		keyMapping.put(Input.KEY_NUMPAD8, new PlayerPushWalkKey());
		keyMapping.put(Input.KEY_NUMPAD1, new PlayerPushWalkKey());
		keyMapping.put(Input.KEY_NUMPAD3, new PlayerPushWalkKey());
		keyMapping.put(Input.KEY_NUMPAD5, new PlayerPushWalkKey()); // пропустить ход
		keyMapping.put(Input.KEY_NUMPAD7, new PlayerPushWalkKey());
		keyMapping.put(Input.KEY_NUMPAD9, new PlayerPushWalkKey());
		keyMapping.put(Input.MOUSE_LEFT_BUTTON + 1000, new PlayerMadeMousePushOnFieldCell()); // FIXME: нужен новый маппинг для кнопок мыши
		
		keyDirectionMapping = new HashMap<>();
		
		keyDirectionMapping.put(Input.KEY_NUMPAD2, Direction.DOWN);
		keyDirectionMapping.put(Input.KEY_NUMPAD4, Direction.LEFT);
		keyDirectionMapping.put(Input.KEY_NUMPAD6, Direction.RIGHT);
		keyDirectionMapping.put(Input.KEY_NUMPAD8, Direction.UP);
		keyDirectionMapping.put(Input.KEY_NUMPAD1, Direction.LEFT_DOWN);
		keyDirectionMapping.put(Input.KEY_NUMPAD3, Direction.RIGHT_DOWN);
		keyDirectionMapping.put(Input.KEY_NUMPAD5, Direction.HALT);
		keyDirectionMapping.put(Input.KEY_NUMPAD7, Direction.LEFT_UP);
		keyDirectionMapping.put(Input.KEY_NUMPAD9, Direction.RIGHT_UP);
	}
	
	private GameField gameField;
	
	public TerrainController(GameField gameField) {
		this.gameField = gameField;
	}
	
	@Override
	public void handleKey(Widget caller, int keyCode, int modifier) {
		PlayerCommand playerCommand = keyMapping.get(keyCode);
		if (playerCommand != null) {
			switch (playerCommand.getTargetSource()) {
			case DIRECTION: playerCommand.setTargetDirection(keyDirectionMapping.get(keyCode)) ;break;
			case FIELDCELL: playerCommand.setTargetFieldCell(gameField.getSelectedCell()); break;
			case GAMEOBJECT_IN_INVENTORY: break;
			case NONE: break;
			}
			GameLogicEngine.getInstance().addAction(playerCommand);
		} else {
			log.info("Key is not bound");
		}
	}

	@Override
	public void handleMouse(Widget caller, int mouseButton, int modifier, int mouseX, int mouseY, int absoluteX, int absoluteY, int minWidth, int minHeight) {
		final int heightInCells = gameField.getHeightInCells();
		final int widthInCells = gameField.getWidthInCells();
		
		final int cellWidthInPixels = ImageConstants.cellWidthInPixels;
		final int cellHeightInPixels = ImageConstants.cellHeightInPixels;

		final int absoluteXOffset = absoluteX - gameField.getPlayer().getLocationCell().getJ() * cellWidthInPixels + minWidth/2 - cellWidthInPixels/2;
		final int absoluteYOffset = absoluteY - gameField.getPlayer().getLocationCell().getI() * cellHeightInPixels + minHeight/2 - cellHeightInPixels/2;
		
		final int indexI = (mouseY - absoluteYOffset)/cellHeightInPixels;
		final int indexJ = (mouseX - absoluteXOffset)/cellWidthInPixels;
		
		// TODO: упростить условие
		if (indexI >= 0 && indexI < heightInCells && indexJ >= 0 && indexJ < widthInCells &&
				mouseY - absoluteYOffset > 0 && mouseX - absoluteXOffset > 0 &&
				mouseY - absoluteY < minHeight && mouseX - absoluteX < minWidth) {
			gameField.setCellSelected(indexI, indexJ);
			PlayerCommand playerCommand = keyMapping.get(mouseButton + 1000);
			if (playerCommand != null) {
				switch (playerCommand.getTargetSource()) {
				case FIELDCELL: playerCommand.setTargetFieldCell(gameField.getSelectedCell()) ;break;
				case GAMEOBJECT_IN_INVENTORY: break;
				case NONE: break;
				}
				GameLogicEngine.getInstance().addAction(playerCommand);
			}
		} else {
			gameField.diselectCell();
		}
	}

	@Override
	public void handleMouseOff(Widget caller) {
		gameField.diselectCell();
	}

}
