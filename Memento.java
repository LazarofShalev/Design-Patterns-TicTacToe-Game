
import java.util.ArrayList;
import java.util.List;

//******************************//
//*** Memento Design Pattern ***//
//******************************//

public class Memento {

	private char shape;
	private int row;
	private int column;

	public Memento(char shape, int row, int column) {
		this.shape = shape;
		this.row = row;
		this.column = column;
	}

	public char getShape() {
		return this.shape;
	}

	public int getRow() {
		return this.row;
	}

	public int getColumn() {
		return this.column;
	}
}

class Originator {

	private char shape;
	private int row;
	private int column;

	public void setState(char shape, int row, int column) {
		this.shape = shape;
		this.row = row;
		this.column = column;
	}

	public Memento saveStateToMemento() {
		return new Memento(this.shape, this.row, this.column);
	}

	public void getStateFromMemento(Memento memento) {
		if (memento != null) {
			this.shape = memento.getShape();
			this.row = memento.getRow();
			this.column = memento.getColumn();
		}
	}

	public char getShape() {
		return this.shape;
	}

	public int getRow() {
		return this.row;
	}

	public int getColumn() {
		return this.column;
	}
}

class CareTaker {

	private List<Memento> undoMementoList = new ArrayList<Memento>();
	private List<Memento> redoMementoList = new ArrayList<Memento>();

	private int undoIndex;
	private int redoIndex;

	public CareTaker() {
		undoIndex = undoMementoList.size();
		redoIndex = redoMementoList.size();
	}

	public void addToUndoList(Memento state) {
		if (state != null) {
			undoMementoList.add(state);
			undoIndex = undoMementoList.size() - 1;
		}
	}

	public void addToRedoList(Memento state) {
		if (state != null) {
			redoMementoList.add(state);
			redoIndex = redoMementoList.size() - 1;
		}
	}

	public Memento removeLastUndo() {
		return undoMementoList.remove(undoMementoList.size() - 1);
	}

	public Memento removeLastRedo() {
		return redoMementoList.remove(redoMementoList.size() - 1);

	}

	public int getMementoUndoListSize() {
		return undoMementoList.size();
	}

	public int getMementoRedoListSize() {
		return redoMementoList.size();
	}

	public int getUndoIndex() {
		return undoIndex;
	}

	public int getRedoIndex() {
		return redoIndex;
	}

	public List<Memento> getUndoArrayList() {
		return undoMementoList;
	}

	public List<Memento> getRedoArrayList() {
		return redoMementoList;
	}

	public Memento getUndoMemento() {
		if (undoMementoList.isEmpty() || undoIndex < 0) {
			return null;
		}
		return undoMementoList.get(undoIndex--);
	}

	public Memento getRedoMemento() {
		if (redoMementoList.isEmpty() || redoIndex < 0) {
			return null;
		}
		return redoMementoList.get(redoIndex--);
	}
}
