package a6;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View {
	public View (Stage s){
		Group g = new Group();
		Pane pane = new HBox();
		VBox vbox = new VBox();
		g.getChildren().add(pane);
		g.getChildren().add(vbox);
		Scene sc = new Scene(g);
		s.setScene(sc);
		s.setWidth(1500);
		s.setHeight(900);
	}
}
