
public class PaneWithUpdate extends DecoratorPane {

	PaneWithUpdate(Component pane) {
		super(pane);
	}

	@Override
	public void decorate() {
		super.decorate();

		NewButton jbtNew = new NewButton("New");
		TimeButton jbtTime = new TimeButton("Time");
		UndoButton jbtUndo = new UndoButton("Undo");
		RedoButton jbtRedo = new RedoButton("Redo");

		super.addButtons(jbtNew, jbtUndo, jbtRedo, jbtTime);
	}
}
