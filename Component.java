import javafx.scene.layout.Pane;

public interface Component {
	void decorate();

	void addButtons(CommandButton... buttons);

	Pane getPane();
}
