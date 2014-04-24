package a6;

import a5.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	private View v;
	private Controller c;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage s) throws Exception {
		Constants.read("src/constants.txt");
		CritterWorld cw = new CritterWorld();
		v = new View(s, cw);
		c = new Controller(v, cw);
		s.show();
	}
	
}
