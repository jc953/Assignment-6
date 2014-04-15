package a6;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class View extends Canvas{
	Paint background;
	public View (Stage s){
		background = Color.BLACK;
		Group g = new Group();
		Pane pane = new HBox();
		VBox vbox = new VBox();
		g.getChildren().add(pane);
		g.getChildren().add(vbox);
		Scene sc = new Scene(g);
		s.setScene(sc);
		s.setWidth(1500);
		s.setHeight(900);
		s.show();
		GraphicsContext gc = getGraphicsContext2D();
		gc.setFill(background);
		gc.fillRect(0, 0, getWidth(), getHeight());
	}
}
