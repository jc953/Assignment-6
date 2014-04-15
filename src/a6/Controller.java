package a6;
import a5.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Controller {
	Label stepLabel;
	Timeline timeline;
	int numSteps;
	View v;
	CritterWorld cw;
	Label critterLabel;
	Button b1;
	public Controller(View v, CritterWorld cw1){
		this.v = v;
		numSteps = 0;
		cw=cw1;
		setWorldSteps();
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
                step();
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
