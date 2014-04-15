package a6;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class View {
	public View (Stage s){
		Group g = new Group();
		BorderPane border = new BorderPane();
		Pane pane = new Pane();
		pane.setPrefWidth(900);
		Polygon p = new Polygon(0, 0, 100, 0, 100, 100, 0, 100);
		p.setFill(Color.ALICEBLUE);
		
		pane.getChildren().add(p);
		VBox vbox = new VBox();
		vbox.getChildren().add(new Button("step"));
		border.setLeft(pane);
		border.setRight(vbox);
		g.getChildren().add(border);
		Scene sc = new Scene(g);
		s.setScene(sc);
		s.setWidth(1200);
		s.setHeight(900);
	}
}