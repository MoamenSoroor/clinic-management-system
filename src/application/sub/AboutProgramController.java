package application.sub;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import application.MainFXMLController;
import javafx.event.ActionEvent;

import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

public class AboutProgramController {
	private MainFXMLController main;
	String str = "https://www.facebook.com/moamen.soroor?ref=bookmarks";

	@FXML
	public Hyperlink emailLink;
	@FXML
	private Button backbutton;

	// Event Listener on Hyperlink[#emailLink].onAction
	@FXML
	public void onEmailClicked(ActionEvent event) {
		main.getHost().showDocument(str);

	}
	// Event Listener on Button[#backbutton].onAction
	@FXML
	public void onBack(ActionEvent event) {
		Stage stage = (Stage)backbutton.getScene().getWindow();
		stage.close();
	}
	public void loadMain(MainFXMLController main2) {
		main = main2;
		
	}
}
