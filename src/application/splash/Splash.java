package application.splash;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Splash {

	private Stage stage;
	private int time = 2000;
	private SplashController spl;
	
	public double getTime() {
		return time;
	}
	public void setTime(int millis) {
		this.time = millis;
	}
	public Splash() 
	{
		this.time = 2000;
	}
	public Splash(int millis) 
	{
		this.time = millis;
	}
	public void playSplash(OnFinish finish){
		try{
			stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/splash/Splash.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.toFront();
			stage.setAlwaysOnTop(true);
			stage.show();
			spl = loader.getController();
			spl.playSplash(time , ()-> finish.setOnFinish());
			
		}
		catch(Exception ex){ ex.printStackTrace(); }
	}
	public void hideSplash(){
		spl.stopSplash();
		stage.close();
	}

	

}
