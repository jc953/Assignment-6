package a6;

import a5.CritterWorld;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

public class HexPolygon extends Polygon {
	int column, row, arrRow;
	View v;
	double x, y;
	
	public HexPolygon(double a, double b, double c, double d, double e, double f, 
			double g, double h, double i, double j, double k, double l, int col, int row, View v){
		super(a, b, c, d, e, f, g, h, i, j, k, l);
		column = col;
		this.arrRow = row;
		this.row = arrRow + (col+1)/2;
		this.v = v;
		setStroke(Color.BLACK);
		setFill(Color.ANTIQUEWHITE);
		setupEventHandlers();
	}
	
	public void setCenter(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	private void setupEventHandlers(){
		setOnMousePressed(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				System.out.println(column);
				System.out.println(row);
				System.out.println(v.getCritterWorld().hexes[column][arrRow].rock);
				
			}
			
		});
	}
	
	public boolean hasObject(){
		return v.getCritterWorld().hexes[column][arrRow].rock || v.getCritterWorld().hexes[column][arrRow].critter != null;
	}
	
	public void reset(){
		setFill(Color.ANTIQUEWHITE);
	}
	
	public ImageView draw(){
		if (v.getCritterWorld().hexes[column][arrRow].rock){
			setFill(Color.WHITE);
			Image img = new Image("file:src/rock.png");
			ImageView imgv = new ImageView();
			imgv.setImage(img);
			imgv.setFitHeight(40);
			imgv.setFitWidth(40);
			imgv.setX(x-20);
			imgv.setY(y-20);
			imgv.setRotate(Math.random()*360);
			return imgv;
		} else if (v.getCritterWorld().hexes[column][arrRow].critter != null){
			setFill(Color.WHITE);
			Image img = new Image("file:src/critter.png");
			ImageView imgv = new ImageView();
			imgv.setImage(img);
			imgv.setFitHeight(40);
			imgv.setFitWidth(40);
			imgv.setX(x-20);
			imgv.setY(y-20);
			imgv.setRotate(v.getCritterWorld().hexes[column][arrRow].critter.direction*60);
			return imgv;
		} else {
			System.out.println("Cannot draw rock or critter here");
			return null;
		}
	}	
}
