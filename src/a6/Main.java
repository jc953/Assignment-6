package a6;

import a5.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
		v = new View(s);
		c = new Controller(v);
		s.show();
	}
	
}
