package a6;

import java.util.ArrayList;

import a5.Constants;
import a5.Critter;
import a5.CritterWorld;
import ast.Program;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class View {
	private Group g;
	protected final StackPane world;
	private VBox vbox;
	private ArrayList<HexPolygon> hexes;
	private int width;
	private int height;
	private int hL;
	private double hA;
	private int diff;
	private CritterWorld cw;
	private Pane actors;
	private Pane hexesPane;
	private Polygon background;
	ArrayList<Program> programs;
	ArrayList<Double> hues;

	public View(Stage s, CritterWorld cw) {
		width = Constants.SCENE_WIDTH;
		height = Constants.SCENE_HEIGHT;
		hL = Constants.HEX_LENGTH;
		hA = Constants.HEX_APOTHEM;
		diff = Constants.HEX_DIFF;
		programs = new ArrayList<Program>();
		hues = new ArrayList<Double>();
		g = new Group();
		Scene scene = new Scene(g);
		s.setScene(scene);
		s.setWidth(width);
		s.setHeight(height);
		hexes = new ArrayList<HexPolygon>();
		this.cw = cw;
		BorderPane border = new BorderPane();
		world = new StackPane();
		double width = Constants.MAX_COLUMN*hL*3/2+Constants.MAX_COLUMN*diff + hL/2;
		double height = Constants.MAX_ARRAY_ROW*hA*2+Constants.MAX_ARRAY_ROW*diff+hA;
		world.setPrefWidth(width);
		world.setPrefHeight(height);
		background = new Polygon(0,0, width, 0, width, height, 0, height);
		background.setFill(Color.WHITE);
		hexesPane = new Pane();
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
				p.setCenter(hL+x+diff*i, hA+y+diff*j);
				hexesPane.getChildren().add(p);
				hexes.add(p);
			}
		}
		actors = new Pane();
		world.getChildren().addAll(background, actors, hexesPane);
		ScrollPane sp = new ScrollPane();
		sp.setContent(world);
		sp.setPrefSize(850,850);
		vbox = new VBox();
		scene.setFill(Color.BLANCHEDALMOND);
		border.setLeft(sp);
		border.setRight(vbox);
		g.getChildren().add(border);
		update(cw);
	}
	
	public void update(CritterWorld cw){
		this.cw = cw;
		world.getChildren().removeAll(actors, hexesPane);
		actors = new Pane();
		world.getChildren().addAll(actors, hexesPane);
		setColors();
		for (HexPolygon h : hexes){
			h.reset();
			if (h.hasObject()){
				actors.getChildren().add(h.draw());
			}
		}
	}
	
	public void setColors(){
		for (Critter c : cw.critters){
			boolean exists = false;
				for (Program p : programs){
					if (c.program.equals(p)){
						exists = true;
						break;
					}
				}
			if (!exists){
				programs.add(c.program);
				hues.add(Math.random()*2-1);
			}
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