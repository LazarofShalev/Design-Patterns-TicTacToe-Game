
public class PaneWithoutUpdate extends DecoratorPane {

	PaneWithoutUpdate(Component pane) {
		super(pane);
	}

	@Override
	public void decorate() {
		super.decorate();

		NewButton jbtNew = new NewButton("New");

		super.addButtons(jbtNew);
	}
}
