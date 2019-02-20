import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application implements Finals {

	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {

		for (int i = 0; i <= SimplePane.MAX_PANES; i++) {
			Component p = SimplePane.getInstance();
			if (p != null) {
				p.decorate();
				Pane pane = p.getPane();
				BorderPane borderPane = new BorderPane();
				borderPane.setCenter(pane);
				Scene scene = new Scene(borderPane, 300, 250);
				primaryStage.setTitle("TicTacToe");
				primaryStage.setScene(scene);
				primaryStage.show();
				primaryStage.setAlwaysOnTop(true);
				primaryStage = new Stage();
			} else
				System.out.println("Singleton Vioaltion");
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
