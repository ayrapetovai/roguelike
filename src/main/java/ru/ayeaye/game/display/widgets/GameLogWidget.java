package ru.ayeaye.game.display.widgets;

import java.util.List;

import org.newdawn.slick.Graphics;

import ru.ayeaye.game.model.GameLogSource;

public class GameLogWidget extends Widget {

	private GameLogSource logSource;
	
	private int indentLeft = 5;
	private int indentTop = -5;
	private int indentRight = 5;
	private int indentBottom = 10;
	private int indentBetweenLines = 0;
	
	public GameLogWidget(String name) {
		super(name);
	}
	
	public GameLogWidget(String name, int width, int height) {
		super(name, width, height);
	}
	
	@Override
	public void customRender(Graphics graphcis, final int absoluteX, final int absoluteY, final int minWidth, final int minHeight) {
		final List<String> logEntries = logSource.getLogEntries();
		
		final int beginX = absoluteX + indentLeft;
		final int endX = absoluteX + Math.min(getWidth(), minWidth) - indentRight;
		
		final int beginY = absoluteY + indentTop;
		final int endY = absoluteY + Math.min(getHeight(), minHeight) - indentBottom;
		
		final int textAreaWidth = endX - beginX;
		final int textAreaHeight = endY - beginY;
		
		final int letterHeight = graphcis.getFont().getHeight("A"); //TODO: это же константа, нужно вынести куда-нибудь
		final int letterWidth = graphcis.getFont().getHeight("A"); //TODO: это же константа, нужно вынести куда-нибудь
		final int lastLetterIndex = textAreaWidth / letterWidth;
		
		final int numberOfLines = textAreaHeight / (letterHeight + indentBetweenLines);
		List<String> visibleLines = null;
		if (logEntries.size() < numberOfLines) {
			visibleLines = logEntries;
		} else {
			visibleLines = logEntries.subList(0, numberOfLines);
		}
		
		int counter = absoluteY + textAreaHeight - letterHeight;
		for (String entry: visibleLines) {
//			int lineWidth = graphcis.getFont().getWidth(entry);
//			if (absoluteX + lineWidth > endX) {
//				String littelLine = entry.substring(0, lastLetterIndex);
//				graphcis.drawString(entry, beginX, counter);
//				counter -= letterHeight + indentBetweenLines;
//				if (counter < absoluteY + indentTop) {
//					break;
//				}
//				graphcis.drawString(entry.substring(lastLetterIndex, entry.length()), beginX, counter);
//				counter -= letterHeight + indentBetweenLines;
//				if (counter < absoluteY + indentTop) {
//					break;
//				}
//			} else {
				graphcis.drawString(entry, beginX, counter);
				counter -= letterHeight + indentBetweenLines;
				if (counter < absoluteY + indentTop) {
					break;
				}
//			}

		}
	}

	public void setLogSource(GameLogSource logSource) {
		this.logSource = logSource;
	}

	public int getIndentLeft() {
		return indentLeft;
	}

	public void setIndentLeft(int indentLeft) {
		this.indentLeft = indentLeft;
	}

	public int getIndentTop() {
		return indentTop;
	}

	public void setIndentTop(int indentTop) {
		this.indentTop = indentTop;
	}

	public int getIndentRight() {
		return indentRight;
	}

	public void setIndentRight(int indentRight) {
		this.indentRight = indentRight;
	}

	public int getIndentBottom() {
		return indentBottom;
	}

	public void setIndentBottom(int indentBottom) {
		this.indentBottom = indentBottom;
	}

	public int getIndentBetweenLines() {
		return indentBetweenLines;
	}

	public void setIndentBetweenLines(int indentBetweenLines) {
		this.indentBetweenLines = indentBetweenLines;
	}
	
}
