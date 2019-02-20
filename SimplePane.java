
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

public class SimplePane extends BorderPane implements Component, Finals {
	private Cell[][] cell = new Cell[3][3];
	private char whoseTurn = 'X';

	private static int numOfPanes = 0;

	private GridPane cellPane;
	private FlowPane jpButton;

	private boolean paneWithUpdate;

	private Label lblStatus = new Label("X's turn to play");

	public EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent arg0) {
			((Command) arg0.getSource()).Execute();
		}
	};

	public static Component getInstance() {
		numOfPanes++;
		if (numOfPanes <= UPDATE_FILE_PANES) {
			return new PaneWithUpdate(new SimplePane(true));
		} else if (numOfPanes <= MAX_PANES) {
			return new PaneWithoutUpdate(new SimplePane(false));
		}
		return null;
	}

	private SimplePane(boolean bool) {
		paneWithUpdate = bool;
	}

	@Override
	public void decorate() {

		cellPane = new GridPane();
		for (int i = 0; i < cell.length; i++) {
			for (int j = 0; j < cell.length; j++) {
				cell[i][j] = new Cell(i, j);
				cellPane.add(cell[i][j], i, j);
			}
		}

		this.setCenter(cellPane);
		this.setBottom(lblStatus);
		
	}
	
	@Override
	public void addButtons(CommandButton... buttons) {
		jpButton = new FlowPane();
		jpButton.getChildren().addAll(buttons);

		for (CommandButton b : buttons) {
			b.setPane(this);
			b.setOnAction(ae);
		}

		this.setTop(jpButton);
	}

	public void newGame() {
		lblStatus = new Label("X's turn to play");
		whoseTurn = 'X';

		CommandButton.careTaker.getUndoArrayList().clear();
		CommandButton.careTaker.getRedoArrayList().clear();

		decorate();
	}

	public void setPane(char shape, int i, int j) {
		whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
		// Display whose turn
		lblStatus.setText(whoseTurn + "'s turn");
		cell[i][j].setToken(shape);
	}

	public boolean checkCells() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (cell[i][j] != null)
					return false;
			}
		}
		return true;
	}

	@Override
	public Pane getPane() {
		return this;
	}

	public Label getLblStatus() {
		return lblStatus;
	}

	// An inner class for a cell
	private class Cell extends Pane { // Token used for this cell
		private char token = ' ';
		private int row;
		private int column;

		public Cell(int i, int j) {
			this.row = i;
			this.column = j;

			setStyle("-fx-border-color: black");
			this.setPrefSize(2000, 2000);
			this.setOnMouseClicked(e -> handleMouseClick());
		}

		/* Handle a mouse click event */
		private void handleMouseClick() { // If cell is empty and game is not over
			if (token == ' ' && whoseTurn != ' ') {
				setToken(whoseTurn); // Set token in the cell
				// Check game status
				if (isWon(whoseTurn)) {
					getLblStatus().setText(whoseTurn + " won! The game is over");
					whoseTurn = ' '; // Game is over
				} else if (isFull()) {
					getLblStatus().setText("Draw! The game is over");
					whoseTurn = ' '; // Game is over
				} else { // Change the turn
					whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
					// Display whose turn
					getLblStatus().setText(whoseTurn + "'s turn");
				}

				if (paneWithUpdate) {

					CommandButton.originator.setState(whoseTurn, row, column);
					CommandButton.careTaker.addToUndoList(CommandButton.originator.saveStateToMemento());

					for (int i = 0; i < CommandButton.careTaker.getMementoRedoListSize(); i++) {
						CommandButton.careTaker.removeLastRedo();
					}

				}
			}
		}

		/** Set a new token */
		public void setToken(char c) {
			token = c;

			if (token == 'X') {
				Line line1 = new Line(10, 10, this.getWidth() - 10, this.getHeight() - 10);
				line1.endXProperty().bind(this.widthProperty().subtract(10));
				line1.endYProperty().bind(this.heightProperty().subtract(10));

				Line line2 = new Line(10, this.getHeight() - 10, this.getWidth() - 10, 10);
				line2.startYProperty().bind(this.heightProperty().subtract(10));
				line2.endXProperty().bind(this.widthProperty().subtract(10));

				this.getChildren().addAll(line1, line2); // Add the lines to the pane

			} else if (token == 'O') {
				Ellipse ellipse = new Ellipse(this.getWidth() / 2, this.getHeight() / 2, this.getWidth() / 2 - 10,
						this.getHeight() / 2 - 10);

				ellipse.centerXProperty().bind(this.widthProperty().divide(2));
				ellipse.centerYProperty().bind(this.heightProperty().divide(2));
				ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
				ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));

				ellipse.setStroke(Color.BLACK);
				ellipse.setFill(Color.WHITE);

				this.getChildren().add(ellipse); // Add the ellipse to the pane
			} else if (token == ' ') {
				this.getChildren().clear();
			}
		}

		/** Return token */
		public char getToken() {
			return token;
		}

		/** Determine if the player with the specified token wins */
		public boolean isWon(char token) {
			for (int i = 0; i < 3; i++)

				if (cell[i][0].getToken() == token && cell[i][1].getToken() == token
						&& cell[i][2].getToken() == token) {
					return true;
				}

			for (int j = 0; j < 3; j++)
				if (cell[0][j].getToken() == token && cell[1][j].getToken() == token
						&& cell[2][j].getToken() == token) {
					return true;
				}

			if (cell[0][0].getToken() == token && cell[1][1].getToken() == token && cell[2][2].getToken() == token) {
				return true;
			}

			if (cell[0][2].getToken() == token && cell[1][1].getToken() == token && cell[2][0].getToken() == token) {
				return true;
			}

			return false;
		}

		/** Determine if the cell are all occupied */
		public boolean isFull() {
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++)
					if (cell[i][j].getToken() == ' ')
						return false;

			return true;
		}

	}

}
