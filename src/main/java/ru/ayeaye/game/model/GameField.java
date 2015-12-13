package ru.ayeaye.game.model;

public class GameField {
	private final FieldCell[][] cells;
	private final int widthInCells;
	private final int heightInCells;
	
	private GameObject player;
	private FieldCell selectedCell;
	
	public GameField(int widthInCells, int heightInCells) {
		this.widthInCells = widthInCells;
		this.heightInCells = heightInCells;
		
		cells = new FieldCell[this.heightInCells][this.widthInCells];
		
		for (int i = 0; i < this.heightInCells; i++) {
			for (int j = 0; j < this.widthInCells; j++) {
				cells[i][j] = new FieldCell(i, j, this);
			}
		}
	}
	
	public void diselectCell() {
		if (selectedCell != null) {
			selectedCell.setSelected(false);
		}
		selectedCell = null;
	}
	
	public boolean setCellSelected(int i, int j) {
		if (0 > i || i >= heightInCells || 0 > j || j >= widthInCells) {
			return false;
		} else {
			FieldCell targetCell = cells[i][j];
			if (selectedCell != null) {
				selectedCell.setSelected(false);
			}
			targetCell.setSelected(true);
			selectedCell = targetCell;
			return true;
		}
	}
	
	public FieldCell getCellOrException(int i, int j) {
		if (0 > i || i >= heightInCells || 0 > j || j >= widthInCells) {
			throw new IllegalArgumentException("Wrong (i, j) == (" + i + "," + j + ")");
		}
		return cells[i][j];
	}
	
	public FieldCell getCellOrNull(int i, int j) {
		if (0 > i || i >= heightInCells || 0 > j || j >= widthInCells)
			return null;
		else
			return cells[i][j];
	}
	
	// использовать геттер только в классе для рисования
	public FieldCell[][] getCells() {
		return cells;
	}
	
	public int getWidthInCells() {
		return widthInCells;
	}

	public int getHeightInCells() {
		return heightInCells;
	}

	public GameObject getPlayer() {
		return player;
	}

	public void setPlayer(GameObject player) {
		this.player = player;
	}
	
	public FieldCell getSelectedCell() {
		// FIXME: как насчет синхронизации с setCellSelected?
		return selectedCell;
	}
	
}
