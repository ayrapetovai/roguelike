package ru.ayeaye.game.model;

import java.util.LinkedList;

import org.newdawn.slick.Graphics;

import ru.ayeaye.game.display.ImageConstants;
import ru.ayeaye.game.logic.misc.Direction;

public class FieldCell {
	private final LinkedList<GameObject> gameObjectStack = new LinkedList<GameObject>();

	private final GameField gameField;
	private boolean selected;
	private final int i;
	private final int j;
	
	public FieldCell(int i, int j, GameField gameField) {
		this.gameField = gameField;
		this.i = i;
		this.j = j;
	}
	
	public void removeGameObject(GameObject gameObject) {
		if (gameObjectStack.remove(gameObject))
			gameObject.setLocationCell(null);
	}
	
	public void pushGameObject(GameObject gameObject) {
		gameObjectStack.add(gameObject);
		gameObject.setLocationCell(this);
	}
	
	public GameObject popGameObject() {
		if (gameObjectStack.isEmpty())
			return null;
		
		GameObject go = gameObjectStack.removeLast();
		go.setLocationCell(null);
		return go;
	}

	public FieldCell getLeftCell() {
		return gameField.getCellOrNull(i, j - 1);
	}

	public FieldCell getRightCell() {
		return gameField.getCellOrNull(i, j + 1);
	}

	public FieldCell getUpCell() {
		return gameField.getCellOrNull(i - 1, j);
	}

	public FieldCell getDownCell() {
		return gameField.getCellOrNull(i + 1, j);
	}

	public FieldCell getLeftUpCell() {
		return gameField.getCellOrNull(i - 1, j - 1);
	}

	public FieldCell getRightUpCell() {
		return gameField.getCellOrNull(i - 1, j + 1);
	}

	public FieldCell getLeftDownCell() {
		return gameField.getCellOrNull(i + 1, j - 1);
	}

	public FieldCell getRightDownCell() {
		return gameField.getCellOrNull(i + 1, j + 1);
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void render(Graphics graphicContext, int x, int y) {
		// TODO: нужно мерджить рисунки одного уровня (дыра и пол, не должны быть разными рисунками. должен быть один)
		for (GameObject go: gameObjectStack ) {
			graphicContext.drawImage(go.getImage(), x, y, null); //TODO: последний аргумент - фильтр, пригодится для тумана войны
		}
		if (selected) {
			graphicContext.drawImage(ImageConstants.getInstance().selected, x, y, null);
		}
	}

	public void clear() {
		for (GameObject go: gameObjectStack) {
			go.setLocationCell(null);
		}
		gameObjectStack.clear();
	}
	
	public FieldCell getNeighbourCell(Direction direction) {
		FieldCell result = null;
		switch (direction) {
		case HALT: result = this; break;
		case DOWN: result = getDownCell(); break;
		case LEFT: result = getLeftCell(); break;
		case LEFT_DOWN: result = getLeftDownCell(); break;
		case LEFT_UP: result = getLeftUpCell(); break;
		case RIGHT: result = getRightCell(); break;
		case RIGHT_DOWN: result = getRightDownCell(); break;
		case RIGHT_UP: result = getRightUpCell(); break;
		case UP: result = getUpCell(); break;
		default:
			throw new IllegalArgumentException("Direction " + direction + " is not recognized");
		}
		return result;
	}
	
	@Override
	public String toString() {
//		return "FieldCell {i=" + i + ", j=" + j + ": " + gameObjectStack + "}";
		return "FieldCell {i=" + i + ", j=" + j + "}";
	}

	public Direction getDirectionTo(FieldCell locationCell) {
		// TODO: переделать
//		int diffI = i - locationCell.i;
//		int diffJ = j - locationCell.j;
//		return Direction.values()[9 - diffI*3 + diffJ];
		if (locationCell == this) {
			return Direction.HALT;
		} else if (locationCell == getDownCell()) {
			return Direction.DOWN;
		} else if (locationCell == getLeftCell()) {
			return Direction.LEFT;
		} else if (locationCell == getLeftDownCell()) {
			return Direction.LEFT_DOWN;
		} else if (locationCell == getLeftUpCell()) {
			return Direction.LEFT_UP;
		} else if (locationCell == getRightCell()) {
			return Direction.RIGHT;
		} else if (locationCell == getRightDownCell()) {
			return Direction.RIGHT_DOWN;
		} else if (locationCell == getRightUpCell()) {
			return Direction.RIGHT_UP;
		} else if (locationCell == getUpCell()) {
			return Direction.UP;
		}
		return Direction.NONE;
	}

	public boolean hasObjectWithTag(Tag tag) {
		for (GameObject go: gameObjectStack) {
			if (go.getTags().contains(tag)) {
				return true;
			}
		}
		return false;
	}

	public GameObject getObjectWithTag(Tag tag) {
		for (GameObject go: gameObjectStack) {
			if (go.getTags().contains(tag)) {
				return go;
			}
		}
		return null;
	}
	
	public boolean isNeighborOf(FieldCell other) {
		if (other == null) {
			return false;
		}
		return getDirectionTo(other) != Direction.NONE;
	}
}
