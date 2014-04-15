package a6;
import a5.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {
	Label stepLabel;
	int numSteps;
	View v;
	CritterWorld cw;
	Label critterLabel;
	public Controller(View v, CritterWorld cw1){
		this.v = v;
		numSteps = 0;
		cw=cw1;
		setWorldSteps();
	}
	
	void setWorldSteps(){
		Button b = new Button("step");
		stepLabel = new Label("Steps Advanced: 0");
		critterLabel = new Label("Critters Alive: 0");
		v.getVBox().getChildren().add(b);
		v.getVBox().getChildren().add(stepLabel);
		v.getVBox().getChildren().add(critterLabel);
		b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _) {
                cw.step();
                numSteps++;
                stepLabel.setText("Steps Advanced: " + numSteps);
                critterLabel.setText("Critters Alive: " + cw.critters.size());
            }
        });
	}

}
