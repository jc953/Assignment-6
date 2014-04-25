package a6;
import a5.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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
	VBox warning;
	Label infoLabel;
	Label hexSelected;
	Label position;
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
		infoLabel = new Label("Hover cursor over a command "
				+ "\nand watch this space for help");
		infoLabel.setFont(Font.font("Comic Sans MS",14));
		infoLabel.setMinHeight(80.0);
		infoLabel.setMaxHeight(80.0);
		v.getVBox().setMinWidth(200.0);
		v.getVBox().setMaxWidth(200.0);
		v.getVBox().getChildren().add(infoLabel);
		hexSelection();
		zoomSettings();
		
	}
	
	void createWorld(){
		Button b = new Button("Load World");
		final TextField t = new TextField("");
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
				if(!t.getText().equals("")){
					cw = new CritterWorld(t.getText());
					t.setText("");
			        stepLabel.setText("Steps Advanced: " + cw.steps);
					critterLabel.setText("Critters Alive: " + cw.critters.size());
				}
				else{
					warning("Please supply text!");
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
				infoLabel.setText("Hover cursor over a command "
						+ "\nand watch this space for help");
			}
		});
		
		t.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent _){
				infoLabel.setText("Hover cursor over a command "
						+ "\nand watch this space for help");
			}
		});
		
		b2.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent _){
				infoLabel.setText("Hover cursor over a command "
						+ "\nand watch this space for help");
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
		critterLabel.setFont(Font.font("Copperplate Gothic Bold", 14));
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
		
		b2.setOnMouseEntered(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent _){
				infoLabel.setText("Allows you to change the \nstepping speed "
						+ "to the number \nof milliseconds specified");
			}
		});
		
		b2.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent _){
				infoLabel.setText("Hover cursor over a command "
						+ "\nand watch this space for help");
			}
		});
		
		t.setOnMouseEntered(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent _){
				infoLabel.setText("Please specify a number \nbetween 500 and 10,000");
			}
		});
		
		t.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent _){
				infoLabel.setText("Hover cursor over a command "
						+ "\nand watch this space for help");
			}
		});
		
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
						warning("Please supply text!");
					}
				}
				catch (NumberFormatException nfe){
					warning("Please give a number \nin the correct format!");
				}
			}
		});
		
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent _) {
				if (cw != null) step();
				else{
					warning("Please load a world!");
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
				infoLabel.setText("Hover cursor over a command "
						+ "\nand watch this space for help");
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
						warning("Please supply text!");
					}
				}
				catch (NumberFormatException nfe){
					warning("Please give a number \nin the correct format");
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
				infoLabel.setText("Hover cursor over a command "
						+ "\nand watch this space for help");
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
				infoLabel.setText("Hover cursor over a command "
						+ "\nand watch this space for help");
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
				infoLabel.setText("Hover cursor over a command "
						+ "\nand watch this space for help");
			}
		});
	}
	
	void warning(String w){
		warning = new VBox();
		warning.setAlignment(Pos.TOP_CENTER);
		Button ok = new Button("OK");
		infoLabel.setText("Click OK to dismiss window");
		ok.setPrefWidth(75);
		Label warn = new Label(w);
		Image img = new Image("file:src/sad_ladybug.png");
		ImageView imgv = new ImageView();
		imgv.setImage(img);
		warning.getChildren().addAll(warn,ok,imgv);
		final Stage s1 = new Stage();
        Group g = new Group();
        g.getChildren().add(warning);
        Scene scene = new Scene(g);
        s1.setScene(scene);
        s1.setWidth(150);
        s1.setHeight(230);
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
		position = new Label("click on desired Hex");
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
			hexRockInfo = new Label("Hex Information:");
			hexRockInfo.setFont(Font.font("Copperplate Gothic Bold", 14));
			hexBox.getChildren().addAll(hexRockInfo, new Label ("This hex is a rock"));
			v.getVBox().getChildren().add(hexBox);
			return;
		}
		hexRockInfo = new Label("Hex Information:");
		hexRockInfo.setFont(Font.font("Copperplate Gothic Bold", 14));
		hexFoodInfo = new Label("Food value: " + selected.getFood());
		if (selected.getCritter() == null){
			hexCritterInfo = new Label("There is no critter\ncurrently inhabiting\nthis hex, but...");
			hexBox.getChildren().addAll(hexRockInfo, hexFoodInfo, hexCritterInfo);
			Button b = new Button("You can add one! From\n"
					+ " from file:");
			final TextField t1 = new TextField();
			hexBox.getChildren().addAll(b, t1);
			
			b.setOnMouseEntered(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent _){
					infoLabel.setText("Loads the critter from\nfile specified below \n and places it "
							+ "on this \nhex");
				}
			});
			
			b.setOnMouseExited(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent _){
					infoLabel.setText("Hover cursor over a command "
							+ "\nand watch this space for help");
				}
			});
			
			t1.setOnMouseEntered(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent _){
					infoLabel.setText("Please specify a file");
				}
			});
			
			t1.setOnMouseExited(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent _){
					infoLabel.setText("Hover cursor over a command "
							+ "\nand watch this space for help");
				}
			});
			
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
						warning("Please Supply Text!");
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
		hexCritterInfo.setFont(Font.font("Copperplate Gothic Bold", 14));
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
	
	void zoomSettings(){
		HBox zoom = new HBox();
		Button b1 = new Button("+");
		Button b2 = new Button("-");
		b1.setFont(Font.font("Copperplate Gothic Bold", 50));
		b2.setFont(Font.font("Copperplate Gothic Bold", 50));
		zoom.getChildren().addAll(b1,b2);
		v.getVBox().getChildren().add(zoom);
		
		b1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent _) {
				cw.zoom(v, true);
            }
        });

		
		b2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent _) {
				cw.zoom(v, false);
            }
        });

		
	}
	void removeHexBox(){
		v.getVBox().getChildren().remove(hexBox);
		hexBox = new VBox();
	}
}//add warnings and conditions with wrong stuff put into text fields. 
//Add popup for Program
//repositioning the controls? Is the infoLabel helpful enough?
//make + - buttons
//