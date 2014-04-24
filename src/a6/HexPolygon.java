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
	
	public void draw(){
		if (v.getCritterWorld().hexes[column][arrRow].rock){
			setFill(Color.WHITE);
			Image img = new Image("file:src/rock.png");
			ImageView imgv = new ImageView();
			imgv.setImage(img);
			imgv.setX(column*30);
			imgv.setY(arrRow*30);
			v.world.getChildren().add(imgv);
		} else if (v.getCritterWorld().hexes[column][arrRow].critter != null){
			setFill(Color.WHITE);
			Image img;
			switch (v.getCritterWorld().hexes[column][arrRow].critter.direction){
			case 1: 
				img = new Image("file:src/critter1.png");
				break;
			case 2: 
				img = new Image("file:src/critter2.png");
				break;
			case 3: 
				img = new Image("file:src/critter3.png");
				break;
			case 4: 
				img = new Image("file:src/critter4.png");
				break;
			case 5: 
				img = new Image("file:src/critter5.png");
				break;
			default:
				img = new Image("file:src/critter.png");
				break;
			}
			setFill(new ImagePattern(img));
		} else {
			setFill(Color.ANTIQUEWHITE);
		}
	}	
}
