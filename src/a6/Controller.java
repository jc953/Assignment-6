package a6;
import a5.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller {
	Label stepLabel;
	View v;
	CritterWorld cw;
	Label critterLabel;
	Pane warning;
	Label infoLabel;
	Label hexSelected;
	TextField position;
	String clicked;
	HexPolygon selected;
	public Controller(View v){
		this.v = v;
		createWorld();
		setWorldSteps();
		createCritters();
		infoLabel = new Label("");
		v.getVBox().getChildren().add(infoLabel);
		hexControls();
		
	}
	
	void createWorld(){
		Button b = new Button("Load World");
		final TextField t = new TextField();
		Button b2 = new Button("Load Random World");
		v.getVBox().getChildren().add(b);
		v.getVBox().getChildren().add(t);
		v.getVBox().getChildren().add(b2);
		
		b2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent _) {
				cw = new CritterWorld(true);
		        stepLabel.setText("Steps Advanced: " + cw.steps);
				critterLabel.setText("Critters Alive: " + cw.critters.size());
				cw.update(v);
            }
        });
		
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent _) {
				if(t.getText()!= null){
					cw = new CritterWorld(t.getText());
					t.setText("");
			        stepLabel.setText("Steps Advanced: " + cw.steps);
					critterLabel.setText("Critters Alive: " + cw.critters.size());
				}
				else{
					warning("Please supply text");
				}
				cw.update(v);
            }
        });
		
		b.setOnMouseEntered(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent _){
				infoLabel.setText("Loads the world from the file \n specified below");
			}
		});
		
		t.setOnMouseEntered(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent _){
				infoLabel.setText("Please Specify a file");
			}
		});
		
		b2.setOnMouseEntered(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent _){
				infoLabel.setText("Generates a random world");
			}
		});
		
		b.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent _){
				infoLabel.setText("");
			}
		});
		
		t.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent _){
				infoLabel.setText("");
			}
		});
		
		b2.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent _){
				infoLabel.setText("");
			}
		});
		
		
	}
	
	void setWorldSteps(){
		Button b = new Button("Step Once");
		final Button b1 = new Button("Step Continuously");
		stepLabel = new Label("Steps Advanced: 0");
		critterLabel = new Label("Critters Alive: 0");
		v.getVBox().getChildren().add(b);
		v.getVBox().getChildren().add(b1);
		v.getVBox().getChildren().add(stepLabel);
		v.getVBox().getChildren().add(critterLabel);
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent _) {
				if (cw != null) step();
				else{
					warning("Please load a world");
				}
				cw.update(v);
            }
        });
		final Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), 
				new EventHandler<ActionEvent>(){
				
				@Override
				public void handle(ActionEvent arg0){
					if (b1.getText() == "Stop Stepping"){
						step();
					}
					cw.update(v);
				}
		}));
		b1.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent _){
				if(b1.getText() == "Step Continuously"){
					b1.setText("Stop Stepping");
					timeline.setCycleCount(Timeline.INDEFINITE);
					timeline.play();
				}
				else{
					b1.setText("Step Continuously");
					timeline.stop();
				}
				cw.update(v);

			}
		});
		
		b.setOnMouseEntered(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent _){
				infoLabel.setText("Advances the World one step");
			}
		});
		
		b.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent _){
				infoLabel.setText("");
			}
		});
		
		b1.setOnMouseEntered(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent _){
				if(b1.getText() == "Stop Stepping"){
					infoLabel.setText("Stops automatic advancement");
				}
				else{
					infoLabel.setText("Advances the world continuously at a rate"
							+ " \n of one step per second");
				}
			}
		});
		
		b1.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent _){
				infoLabel.setText("");
			}
		});
	}
	
	void step(){
    	cw.step();
        stepLabel.setText("Steps Advanced: " + cw.steps);
        critterLabel.setText("Critters Alive: " + cw.critters.size());
	}
	
	void createCritters(){
		Button b = new Button("Load");
		final TextField t1 = new TextField();
		Label l = new Label("Critters from file:");
		final TextField t2 = new TextField();
		v.getVBox().getChildren().add(b);
		v.getVBox().getChildren().add(t1);
		v.getVBox().getChildren().add(l);
		v.getVBox().getChildren().add(t2);
		
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent _) {
				try{
					if(t1.getText()!= null && t2.getText()!= null){
						for(int i=0;i<Integer.parseInt(t1.getText());i++){
							cw.addRandomCritter(t2.getText());
						}
						t1.setText("");
						t2.setText("");
						critterLabel.setText("Critters Alive: " + cw.critters.size());
					}
					else{
						warning("Please Supply Text!");
					}
				}
				catch (NumberFormatException nfe){
					warning("Please give a number in the correct format");
				}
				cw.update(v);
            }
        });
		
		b.setOnMouseEntered(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent _){
				infoLabel.setText("Loads the number of critters specified "
						+ "\nbelow from file specified below");
			}
		});
		
		b.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent _){
				infoLabel.setText("");
			}
		});
		
		t1.setOnMouseEntered(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent _){
				infoLabel.setText("The number of critters to generate");
			}
		});
		
		t1.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent _){
				infoLabel.setText("");
			}
		});
		
		t2.setOnMouseEntered(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent _){
				infoLabel.setText("the file to generate the critters from");
			}
		});
		
		t2.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent _){
				infoLabel.setText("");
			}
		});
	}
	
	void warning(String w){
		warning = new VBox();
		warning.setPrefSize(400,400);
		Button ok = new Button("Ok");
		ok.setOnMouseEntered(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent _){
				infoLabel.setText("Click to dismiss window");
			}
		});
		ok.setPrefWidth(150);
		Label warn = new Label(w);
		warning.getChildren().add(ok);
		warning.getChildren().add(warn);
        final Stage s1 = new Stage();
        Group g = new Group();
        g.getChildren().add(warning);
        Scene scene = new Scene(g);
        s1.setScene(scene);
        s1.setWidth(600);
        s1.setHeight(200);
        s1.setX(450);
        s1.setY(450);
        s1.show();
		ok.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent _){
				s1.close();
			}
		});
		
	}
	
	void hexControls(){
		hexSelected = new Label("Hex Selected:");
		position = new TextField("Enter: (column, row) of desired Hex,"
				+ "or click on desired Hex");
		v.getVBox().getChildren().add(hexSelected);
		v.getVBox().getChildren().add(position);
		clicked = "";
		v.getWorld().setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent _){
				if(clicked == ""){
					position.setText("Enter: (column, row) of desired Hex,"
							+ "or click on desired Hex");
				}
				else{
					position.setText(clicked);
				}
				
			}
		});
		
		for(final HexPolygon h: v.getHexes()){
			h.setOnMouseEntered(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent _){
					position.setText("("+h.column+","+h.row+")");
				}
			});
			
			h.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent _){
					clicked = "("+h.column+","+h.row+")";
					position.setText(clicked);
					if(selected != null){
						selected.setFill(h.getFill());
						selected.setStroke(h.getStroke());
					}
					if(h.equals(selected)){
						h.setFill(Color.ANTIQUEWHITE);
						h.setStroke(Color.BLACK);
						clicked = "";
						selected = null;
					}
					else{
						selected = h;
						h.setStroke(Color.RED);
						h.setFill(Color.BLUE);
					}
				}
			});
		}
		
		if(selected != null){
			selected.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent _){
					selected.setFill(Color.ANTIQUEWHITE);
					selected.setStroke(Color.BLACK);
					selected = null;
				}
			});
		}
		
		//do functionality of typing row,column instead of selecting
		
	}

}
