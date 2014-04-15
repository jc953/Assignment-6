package a6;
import a5.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class Controller {
	Label stepLabel;
	Timeline timeline;
	int numSteps;
	View v;
	CritterWorld cw;
	Label critterLabel;
	Button b1;
	TextField t;
	TextField t1;
	TextField t2;
	StackPane warning;
	public Controller(View v){
		this.v = v;
		numSteps = 0;
		createWorld();
		setWorldSteps();
		createCritters();
	}
	
	void createWorld(){
		Button b = new Button("Load World");
		t = new TextField();
		Button b2 = new Button("Load Random World");
		v.getVBox().getChildren().add(b);
		v.getVBox().getChildren().add(t);
		v.getVBox().getChildren().add(b2);
		
		b2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent _) {
				cw = new CritterWorld(true);
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
					critterLabel.setText("Critters Alive: " + cw.critters.size());
				}
				else{
					Label warning = new Label("Please supply text");
					//add to view
				}
				cw.update(v);
            }
        });
	}
	
	void setWorldSteps(){
		Button b = new Button("Step Once");
		b1 = new Button("Step Continuously");
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
					Label warning = new Label("Please load a world");
					//add to view
				}
				cw.update(v);
            }
        });
		timeline = new Timeline(new KeyFrame(Duration.millis(1000), 
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
	}
	
	void step(){
    	cw.step();
        numSteps++;
        stepLabel.setText("Steps Advanced: " + numSteps);
        critterLabel.setText("Critters Alive: " + cw.critters.size());
	}
	
	void createCritters(){
		Button b = new Button("Load");
		t1 = new TextField();
		Label l = new Label("Critters from file:");
		t2 = new TextField();
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
						warning();
					}
				}
				catch (NumberFormatException nfe){
					warning();
				}
				cw.update(v);
            }
        });
	}
	
	void warning(){
		warning = new StackPane();
		warning.setPrefSize(400,400);
		Button ok = new Button("Ok");
		Label warn = new Label("Please Supply Text!");
		warning.getChildren().add(ok);
		warning.getChildren().add(warn);
		v.getGroup().getChildren().add(warning);
		ok.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent _){
				v.getGroup().getChildren().remove(warning);
			}
		});
		
	}

}
