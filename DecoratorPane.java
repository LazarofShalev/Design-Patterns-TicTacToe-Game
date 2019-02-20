import javafx.scene.layout.Pane;

public abstract class DecoratorPane implements Component {
	private Component pane;

	DecoratorPane(Component pane) {
		this.pane = pane;
	}

	@Override
	public void decorate() {
		pane.decorate();
	}

	@Override
	public void addButtons(CommandButton... buttons) {
		pane.addButtons(buttons);
	}

	@Override
	public Pane getPane() {
		return pane.getPane();
	}

}
