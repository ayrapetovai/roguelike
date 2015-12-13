package ru.ayeaye.game.display.widgets;

import org.newdawn.slick.Graphics;
import ru.ayeaye.game.display.ImageConstants;
import ru.ayeaye.game.display.controller.TerrainController;
import ru.ayeaye.game.display.layouts.AbstractLayout;
import ru.ayeaye.game.model.FieldCell;
import ru.ayeaye.game.model.GameField;

public class TerrainWidget extends Widget {

	private GameField gameField;
	
	public TerrainWidget(String name) {
		super(name);
	}

	public TerrainWidget(String name, int width, int height) {
		super(name, width, height);
	}
	
	public TerrainWidget(String name, float widthProportion, float heightProportion) {
		super(name, widthProportion, heightProportion);
	}
	
	@Override
	public void customRender(Graphics graphcis, final int absoluteX, final int absoluteY, final int minWidth, final int minHeight) {
		final int heightInCells = gameField.getHeightInCells();
		final int widthInCells = gameField.getWidthInCells();
		
		final int widthInPixels = widthInCells * ImageConstants.cellWidthInPixels;	
		final int heightInPixels = heightInCells * ImageConstants.cellHeightInPixels;
		
		final int absoluteXOffset = absoluteX - gameField.getPlayer().getLocationCell().getJ() * ImageConstants.cellWidthInPixels + getWidth()/2 - ImageConstants.cellWidthInPixels/2;
		final int absoluteYOffset = absoluteY - gameField.getPlayer().getLocationCell().getI() * ImageConstants.cellHeightInPixels + getHeight()/2 - ImageConstants.cellHeightInPixels/2;
		
		// расчитываем граничные индексы отображаемых тайлов
		int fromI = 0;
		if (absoluteYOffset - absoluteY < 0) {
			fromI = -(absoluteYOffset - absoluteY) / ImageConstants.cellHeightInPixels;
		}
		if (fromI > 0 )
			fromI--;
		
		int toI = heightInCells;
		if (absoluteYOffset + heightInPixels - absoluteY > getHeight()) {
			toI = (getHeight() - (absoluteYOffset - absoluteY))/ImageConstants.cellHeightInPixels;
		}
		if (toI < heightInCells)
			toI++;
		
		int fromJ = 0;
		if (absoluteXOffset - absoluteX < 0) {
			fromJ = -(absoluteXOffset - absoluteX) / ImageConstants.cellWidthInPixels;
		}
		if (fromJ > 0 )
			fromJ--;
		
		int toJ = widthInCells;
		if (absoluteXOffset + widthInPixels - absoluteX> getWidth()) {
			toJ = (getWidth() - (absoluteXOffset - absoluteX))/ImageConstants.cellWidthInPixels;
		}
		if (toJ < widthInCells)
			toJ++;
		
//		int counter = 0;
		final FieldCell[][] cells = gameField.getCells();
		// проходим только по тем тайлам, которые попали в область виджета
		for (int yIndexI = fromI; yIndexI < toI; yIndexI++) {
			for (int xIndexJ = fromJ; xIndexJ < toJ; xIndexJ++) {
				cells[yIndexI][xIndexJ].render(graphcis, absoluteXOffset + xIndexJ * ImageConstants.cellWidthInPixels, absoluteYOffset + yIndexI * ImageConstants.cellHeightInPixels);
//				counter ++;
			}
		}
//		log.debug("visited " + counter);
	}
	
	public void setGameField(GameField gameField) {
		this.gameField = gameField;
		this.controller = new TerrainController(gameField);
	}
	
	@Override // Никаких разметок для этого виджета, он отображает только игровое поле
	public void setLayout(AbstractLayout layout) {
		throw new IllegalStateException("No layouts for this widget");
	}
}
