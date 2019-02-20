import javafx.scene.control.Button;

interface Command {
	public void Execute();
}

class CommandButton extends Button implements Command {
	private SimplePane p;
	static Originator originator = new Originator();
	static CareTaker careTaker = new CareTaker();

	public CommandButton(String text) {
		super(text);
	}

	public void setPane(SimplePane myPane) {
		setP(myPane);
	}

	public void Execute() {
	}

	public SimplePane getP() {
		return p;
	}

	public void setP(SimplePane p) {
		this.p = p;
	}

}

class NewButton extends CommandButton {
	public NewButton(String text) {
		super(text);
		this.setText("New");
	}

	@Override
	public void Execute() {
		getP().newGame();
	}
}

class UndoButton extends CommandButton {
	public UndoButton(String text) {
		super(text);
		this.setText("Undo");
	}

	@Override
	public void Execute() {
		if (careTaker.getMementoUndoListSize() > 0 && getP().checkCells() != true) {

			originator.getStateFromMemento(careTaker.getUndoMemento());
			careTaker.addToRedoList(careTaker.removeLastUndo());

			getP().setPane(' ', originator.getRow(), originator.getColumn());
		}
	}
}

class RedoButton extends CommandButton {
	public RedoButton(String text) {
		super(text);
		this.setText("Redo");
	}

	@Override
	public void Execute() {

		if (careTaker.getMementoRedoListSize() > 0) {

			originator.getStateFromMemento(careTaker.getRedoMemento());
			careTaker.addToUndoList(careTaker.removeLastRedo());

			char token = originator.getShape() == 'X' ? 'O' : 'X';

			getP().setPane(token, originator.getRow(), originator.getColumn());
		}
	}
}

class TimeButton extends CommandButton {
	public TimeButton(String text) {
		super(text);
		this.setText("Time");
	}

	@Override
	public void Execute() {
		GregorianCalendarAdapter cal = new GregorianCalendarAdapter();
		getP().getLblStatus().setText("Time: " + cal.getHour() + ":" + cal.getMinute() + ":" + cal.getSecond());
	}
}