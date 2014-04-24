package a6;

import java.util.ArrayList;

import a5.Constants;
import a5.CritterWorld;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class View {
	private Group g;
	protected final Pane world;
	private Pane vbox;
	private ArrayList<HexPolygon> hexes;
	private int width = 1200;
	private int height = 900;
	private int hL = 40;
	private double hA = 20*Math.pow(3, 0.5);
	private int diff = 4;
	private CritterWorld cw;

	public View(Stage s) {
		g = new Group();
		Scene scene = new Scene(g);
		s.setScene(scene);
		s.setWidth(width);
		s.setHeight(height);
		hexes = new ArrayList<HexPolygon>();
		cw = new CritterWorld(false);
		
		BorderPane border = new BorderPane();
		world = new Pane();
		world.setPrefWidth(Constants.MAX_COLUMN*hL*3/2+Constants.MAX_COLUMN*diff + hL/2);
		world.setPrefHeight(Constants.MAX_ARRAY_ROW*hA*2+Constants.MAX_ARRAY_ROW*diff+hA);
		for(int i = 0; i < Constants.MAX_COLUMN; i++){
			for (int j = 0; j < Constants.MAX_ARRAY_ROW; j++){
				int x = i * hL*3/2;
				double y;
				if (i%2==0){
					y = hA + j*hA*2;
				} else {
					y = j*hA*2;
				}
				HexPolygon p = new HexPolygon(hL/2+x+diff*i, y+diff*j, hL*3/2+x+diff*i, y+diff*j, hL*2+x+diff*i, hA+y+diff*j,
						hL*3/2+x+diff*i, hA*2+y+diff*j, hL/2+x+diff*i, hA*2+y+diff*j, x+diff*i, hA+y+diff*j, i, Constants.MAX_ARRAY_ROW-j-1, this);
				world.getChildren().add(p);
				hexes.add(p);
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
	
	public void update(CritterWorld cw){
		this.cw = cw;
		for (HexPolygon h : hexes){
			h.draw();
		}
	}

	public Group getGroup() {
		return g;
	}

	public Pane getVBox() {
		return vbox;
	}
	
	public CritterWorld getCritterWorld(){
		return cw;
	}
	
	public ArrayList<HexPolygon> getHexes(){
		return hexes;
	}
	
	public Pane getWorld(){
		return world;
	}
}