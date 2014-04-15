package a6;
import a5.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
	public Controller(View v){
		this.v = v;
		numSteps = 0;
		createWorld();
		setWorldSteps();
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
				cw = new CritterWorld();
            }
        });
		
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent _) {
				if(t.getText()!= null) cw = new CritterWorld(t.getText());
				else{
					Label warning = new Label("Please supply text");
					//add to view
				}
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
            }
        });
		timeline = new Timeline(new KeyFrame(Duration.millis(1000), 
				new EventHandler<ActionEvent>(){
				
				@Override
				public void handle(ActionEvent arg0){
					if (b1.getText() == "Stop Stepping"){
						step();
					}
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

			}
		});
	}
	
	void step(){
    	cw.step();
        numSteps++;
        stepLabel.setText("Steps Advanced: " + numSteps);
        critterLabel.setText("Critters Alive: " + cw.critters.size());
	}

}
