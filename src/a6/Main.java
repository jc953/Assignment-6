package a6;

import a5.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Method to launch application
	 */
	@Override
	public void start(Stage s) throws Exception {
		Constants.read("src/constants.txt");
		CritterWorld cw = new CritterWorld();
		View v = new View(s, cw);
		Controller c = new Controller(v, cw);
		s.show();
	}
	
}
