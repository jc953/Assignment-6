package a6;

import a5.Constants;
import a5.Critter;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class HexPolygon extends Polygon {
	int column, row, arrRow;
	View v;
	double x, y;
	static Color background = Color.color(0, .6, 0);
	
	public HexPolygon(double a, double b, double c, double d, double e, double f, 
			double g, double h, double i, double j, double k, double l, int col, int row, View v){
		super(a, b, c, d, e, f, g, h, i, j, k, l);
		column = col;
		this.arrRow = row;
		this.row = arrRow + (col+1)/2;
		this.v = v;
		setStroke(Color.BLACK);
		setFill(background);
	}
	
	public void setCenter(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public boolean hasObject(){
		return isRock() || getCritter() != null || getFood() > 0;
	}
	
	public boolean isRock(){
		return v.getCritterWorld().hexes[column][arrRow].rock;
	}
	
	public int getFood(){
		return v.getCritterWorld().hexes[column][arrRow].food;
	}
	
	public Critter getCritter(){
		return v.getCritterWorld().hexes[column][arrRow].critter;
	}
	
	public void reset(){
		setFill(background);
	}
	
	public ImageView draw(){
		setFill(Color.TRANSPARENT);
		if (isRock()){
			Image img = new Image("file:src/rock.png");
			ImageView imgv = new ImageView();
			imgv.setImage(img);
			imgv.setFitHeight(Constants.HEX_LENGTH);
			imgv.setFitWidth(Constants.HEX_LENGTH);
			imgv.setX(x-Constants.HEX_LENGTH/2);
			imgv.setY(y-Constants.HEX_LENGTH/2);
			imgv.setRotate(v.getCritterWorld().hexes[column][arrRow].rockDir);
			return imgv;
		} else if (getCritter() != null){
			Image img;
			if (getFood() > 0) {
				img = new Image("file:src/critterfood.png");
			} else {
				img = new Image("file:src/critter.png");
			}
			ImageView imgv = new ImageView();
			imgv.setImage(img);
			double size = Constants.HEX_LENGTH/1.5 + getCritter().mem[3]*Constants.HEX_LENGTH/20.0;
			if (size > Constants.HEX_LENGTH) size = Constants.HEX_LENGTH;
			imgv.setFitHeight(size);
			imgv.setFitWidth(size);
			imgv.setX(x-size/2.0);
			imgv.setY(y-size/2.0);
			imgv.setRotate(getCritter().direction*60);
			ColorAdjust color = new ColorAdjust();
			int index = 0;
			for (int i = 0; i < v.programs.size(); i++){
				if (v.programs.get(i).equals(getCritter().program)) index = i;
			}
			
			double hue = v.hues.get(index);
			color.setHue(hue);
			imgv.setEffect(color);
			return imgv;
		} else if (getFood() > 0){
			Image img = new Image("file:src/food.png");
			ImageView imgv = new ImageView();
			imgv.setImage(img);
			imgv.setFitHeight(Constants.HEX_LENGTH);
			imgv.setFitWidth(Constants.HEX_LENGTH);
			imgv.setX(x-Constants.HEX_LENGTH/2);
			imgv.setY(y-Constants.HEX_LENGTH/2);
			return imgv;
		} else {
			System.out.println("Cannot draw rock or critter here");
			return null;
		}
	}	
}
