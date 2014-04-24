package a6;
import a5.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.Font;

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
	Label hexCritterInfo;
	Label hexRockInfo;
	Label hexFoodInfo;
	int speed;
	VBox hexBox;
	Timeline timeline;
	public Controller(View v, CritterWorld cw){
		this.cw = cw;
		this.v = v;
		speed = 1000;
		createWorld();
		setWorldSteps();
		createCritters();
		infoLabel = new Label("");
		infoLabel.setFont(Font.font("Comic Sans MS",14));
		infoLabel.setMinHeight(80.0);
		infoLabel.setMaxHeight(80.0);
		v.getVBox().setMinWidth(200.0);
		v.getVBox().setMaxWidth(200.0);
		v.getVBox().getChildren().add(infoLabel);
		hexSelection();
		
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
				cw = new CritterWorld();
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
				infoLabel.setText("Loads the world from the file \nspecified below");
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
		final HBox speedControls = new HBox();
		stepLabel = new Label("Steps Advanced: 0");
		stepLabel.setFont(Font.font("Copperplate Gothic Bold",14));
		critterLabel = new Label("Critters Alive: 0");
		final Button b2 = new Button("Set step speed to: ");
		final TextField t = new TextField();
		t.setMaxWidth(50.0); 
		t.setMinWidth(50.0);
		speedControls.getChildren().addAll(b2, t);
		v.getVBox().getChildren().addAll(b, b1, speedControls, stepLabel, critterLabel);
		
		timeline = new Timeline(new KeyFrame(Duration.millis(speed), 
				new EventHandler<ActionEvent>(){
				
				@Override
				public void handle(ActionEvent arg0){
					if (b1.getText() == "Stop Stepping"){
						step();
					}
					cw.update(v);
				}
		}));
		
		
		b2.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent _){
				try{
					if(t.getText()!= null){
						speed = Integer.parseInt(t.getText());
						t.setText("");
						timeline = new Timeline(new KeyFrame(Duration.millis(speed), 
								new EventHandler<ActionEvent>(){
								
								@Override
								public void handle(ActionEvent arg0){
									if (b1.getText() == "Stop Stepping"){
										step();
									}
									cw.update(v);
								}
						}));
					}
					else{
						warning("Please Supply Text!");
					}
				}
				catch (NumberFormatException nfe){
					warning("Please give a number in the correct format");
				}
			}
		});
		
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
					infoLabel.setText("Advances the world \ncontinuously at a rate"
							+ " of \none step per second");
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
				infoLabel.setText("Loads the number of critters \nspecified "
						+ "below from file \nspecified below");
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
				infoLabel.setText("The number of critters to \ngenerate");
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
				infoLabel.setText("the file to generate the \ncritters from");
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
	
	void hexSelection(){
		hexSelected = new Label("Hex Selected:");
		position = new TextField("click on desired Hex");
		v.getVBox().getChildren().add(hexSelected);
		v.getVBox().getChildren().add(position);
		clicked = "";
		v.getWorld().setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent _){
				if(clicked == ""){
					position.setText("click on desired Hex");
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
						selected.setStroke(h.getStroke());
					}
					if(h.equals(selected)){
						h.setStroke(Color.BLACK);
						clicked = "";
						selected = null;
						removeHexBox();
					}
					else{
						selected = h;
						removeHexBox();
						hexControls();
						h.setStroke(Color.RED);
					}
				}
			});
		}
		
		if(selected != null){
			selected.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent _){
					selected.setFill(Color.DARKSEAGREEN);
					selected.setStroke(Color.BLACK);
					selected = null;
				}
			});
		}
		
		//do functionality of typing row,column instead of selecting
		
	}
	
	void hexControls(){
		if (selected == null) return;
		hexBox = new VBox();
		if(selected.isRock()){
			hexRockInfo = new Label("Hex Information:\nThis is a rock");
			hexBox.getChildren().add(hexRockInfo);
			v.getVBox().getChildren().add(hexBox);
			return;
		}
		hexRockInfo = new Label("Hex Information:");
		hexFoodInfo = new Label("Food value: " + selected.getFood());
		if (selected.getCritter() == null){
			hexCritterInfo = new Label("There is no critter\ncurrently inhabiting\nthis hex");
			hexBox.getChildren().addAll(hexRockInfo, hexFoodInfo, hexCritterInfo);
			Button b = new Button("Load a Critter in \nthis hex"
					+ " from file:");
			final TextField t1 = new TextField();
			hexBox.getChildren().addAll(b, t1);
			
			b.setOnAction(new EventHandler<ActionEvent>() {
				@Override
	            public void handle(ActionEvent _) {
					if(t1.getText()!= null){
					cw.addCritterHere(selected.column, selected.arrRow, t1.getText());
					t1.setText("");
					critterLabel.setText("Critters Alive: " + cw.critters.size());
					cw.update(v);
					removeHexBox();
					hexControls();
					}
					else{
						warning("Please Supply Text");
					}
				}
			});			
			v.getVBox().getChildren().add(hexBox);
		}
		displayCritterInfo();
	}
	
	void displayCritterInfo(){
		if(selected.getCritter() == null) return;
		hexCritterInfo = new Label("Critter Vital Statistics:");
		hexBox.getChildren().addAll(hexRockInfo, hexFoodInfo, hexCritterInfo,
				new Label("Memory size: " + selected.getCritter().mem[0]), 
				new Label("Defensive ability: " + selected.getCritter().mem[1]), 
				new Label("Offensive ability: " + selected.getCritter().mem[2]), 
				new Label("Size: " + selected.getCritter().mem[3]), 
				new Label("Energy: " + selected.getCritter().mem[4]), 
				new Label("Pass value: " + selected.getCritter().mem[5]), 
				new Label("Tag value: " + selected.getCritter().mem[6]), 
				new Label("Posture value: " + selected.getCritter().mem[7]));
		for(int i = 8; i < selected.getCritter().mem.length;i++){
			hexBox.getChildren().add(new Label("mem["+i+"]: "+ selected.getCritter().mem[i]));
		}
		StringBuffer sb = new StringBuffer();
		selected.getCritter().program.prettyPrint(sb);
		hexBox.getChildren().addAll(new Label("Program: "),new Label(sb.toString()));
		sb = new StringBuffer("The last rule performed was \n");
		if (selected.getCritter().lastRule != null) {
			selected.getCritter().lastRule.prettyPrint(sb);
			hexBox.getChildren().add(new Label(sb.toString()));
		} else {
			hexBox.getChildren().add(new Label("This critter has not \nperformed a rule yet."));
		}
		v.getVBox().getChildren().add(hexBox);
	}
	
	void removeHexBox(){
		v.getVBox().getChildren().remove(hexBox);
		hexBox = new VBox();
	}
}//add warnings and conditions with wrong stuff put into text fields. 
//Also add info for newly created text fields. Consider popups and shit
//Put some text into bold
//repositioning the controls? Is the infoLabel helpful enough?
//make + - buttons
//