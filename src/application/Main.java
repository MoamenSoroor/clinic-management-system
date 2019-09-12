package application;

import java.io.File;
import application.database.Database;
import application.splash.Splash;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;


public class Main extends Application {
	
	HostServices host = getHostServices();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Database.setDataBaseConnection();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/MainFXML.fxml"));
			Parent root = loader.<HBox>load();
			MainFXMLController con = loader.getController();
			con.passHost(host);
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.setTitle("ÈÑäÇãÌ ÇÏÇÑÉ ÚíÇÏÉ ØÈíÉ");
			primaryStage.getIcons().add(new Image("file:src/application/icons/girl.png"));
			Splash sp = new Splash(2000);
			sp.playSplash(()-> {
				primaryStage.show();
				sp.hideSplash();
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public HostServices getHost()
	{
		return host;
	}
	
	public static void main(String[] args) {
		launch(args);
		File dir = new File("src/application/temp/");
		for(File f : dir.listFiles())
			f.delete();
		Database.closeDatabase();
	}
	
}
