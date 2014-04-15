package a6;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View2 {
	public View2 (Stage s){
		Group g = new Group();
		BorderPane border = new BorderPane();
		Pane pane = new Pane();
		border.setPrefSize(1400, 900);
		pane.getChildren().add(new Button("hi"));
		VBox vbox = new VBox();
		vbox.getChildren().add(new Button("j"));
		border.setLeft(pane);
		border.setRight(vbox);
		g.getChildren().add(border);
		Scene sc = new Scene(g);
		s.setScene(sc);
		s.setWidth(1500);
		s.setHeight(900);
	}
}