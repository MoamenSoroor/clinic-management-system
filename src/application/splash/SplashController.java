package application.splash;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public class SplashController {
	@FXML
	private ImageView myImage;
	
	Timeline timeline = null;

	
	@FXML
	private void initialize(){
		
	}
	public void playSplash(int milliSeconds , OnFinish finish){
		KeyFrame frame = new KeyFrame(Duration.millis(1000/60) ,event->{});
		timeline = new Timeline(frame);
		timeline.setCycleCount(milliSeconds * 60 /1000);
		timeline.setOnFinished(event->finish.setOnFinish());	
		timeline.play();
	}
	
	public void stopSplash(){
		timeline.stop();
	}

}
