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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View {
	private Group g;
	protected final Pane world;
	private Pane vbox;
	private ArrayList<HexPolygon> hexes;
	private int width;
	private int height;
	private int hL;
	private double hA;
	private int diff;
	private CritterWorld cw;
	private Pane actors;
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
				p.setCenter(hL+x+diff*i, hA+y+diff*j);
				world.getChildren().add(p);
				hexes.add(p);
			}
		}
		actors = new Pane();
		world.getChildren().add(actors);
		ScrollPane sp = new ScrollPane();
		sp.setContent(world);
		sp.setPrefSize(850,850);
		vbox = new VBox();
		border.setLeft(sp);
		border.setRight(vbox);
		g.getChildren().add(border);
		update(cw);
	}
	
	public void update(CritterWorld cw){
		this.cw = cw;
		world.getChildren().remove(actors);
		actors = new Pane();
		world.getChildren().add(actors);
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
					}
				}
			if (!exists){
				programs.add(c.program);
				hues.add(Math.random());
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