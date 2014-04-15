package a6;

import a5.Constants;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class View {
	private Group g;
	private final Pane world;
	private Pane vbox;
	private int width = 1200;
	private int height = 900;
	private int hL = 60;
	private double hA = 30*Math.pow(3, 0.5);

	public View(Stage s) {
		g = new Group();
		Scene scene = new Scene(g);
		s.setScene(scene);
		s.setWidth(width);
		s.setHeight(height);

		BorderPane border = new BorderPane();
		world = new Pane();
		world.setPrefWidth(Constants.MAX_COLUMN*hL*3/2 + hL/2);
		world.setPrefHeight(Constants.MAX_ARRAY_ROW*hA*2+hA);
		for(int i = 0; i < Constants.MAX_COLUMN; i++){
			for (int j = 0; j < Constants.MAX_ARRAY_ROW; j++){
				int x = i * hL*3/2;
				double y;
				if (i%2==0){
					y = hA + j*hA*2;
				} else {
					y = j*hA*2;
				}
				HexPolygon p = new HexPolygon(hL/2+x, y, hL*3/2+x, y, hL*2+x, hA+y,
						hL*3/2+x, hA*2+y, hL/2+x, hA*2+y, x, hA+y, i, Constants.MAX_ARRAY_ROW-j-1);
				p.setStroke(Color.BLACK);
				p.setFill(Color.ANTIQUEWHITE);
				world.getChildren().add(p);
			}
		}
		ScrollPane sp = new ScrollPane();
		sp.setContent(world);
		sp.setPrefSize(850,850);
		vbox = new VBox();
		border.setLeft(sp);
		border.setRight(vbox);
		g.getChildren().add(border);
	}

	public Group getGroup() {
		return g;
	}

	public Pane getVBox() {
		return vbox;
	}
}