package a6;

import a5.*;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	private CritterWorld cw;
	private View v;
	private Controller c;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage s) throws Exception {
		v = new View(s);
		s.show();
	}
}
