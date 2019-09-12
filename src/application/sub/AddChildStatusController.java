package application.sub;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import application.database.StatusDatabase;
import application.utils.MUtils;
import javafx.event.ActionEvent;

public class AddChildStatusController {
	@FXML
	private TextField addStatus;
	@FXML
	private Button saveButton;
	@FXML
	private Button cancelButton;
	@FXML
	private ImageView statusNameError;
	
	@FXML
	private ListView<String> statusList;

	// Event Listener on Button[#saveButton].onAction
	@FXML
	public void onSave(ActionEvent event) {
		String status = addStatus.getText().trim();
		if(!addStatus.getText().isEmpty())
		{
			if(StatusDatabase.checkIfStatusExist(status)){
				MUtils.showErrorMessage("ÎØÃ ÇÖÇÝÉ ÊÔÎíÕ", "ÇáÊÔÎíÕ ÇáÐì ÞãÊ ÈÅÏÎÇáÉ ãæÌæÏ");
				return;
			}
			StatusDatabase.insertNewStatus(status);
			Stage stage = (Stage) cancelButton.getScene().getWindow();
			stage.close();
		}
		else
		{
			MUtils.showErrorMessage("ÎØÃ ÇÖÇÝÉ ÊÔÎíÕ", "áã íÊã ÇÏÎÇá Çì ÈíÇäÇÊ");
			return;
		}
	}
	// Event Listener on Button[#cancelButton].onAction
	@FXML
	public void onCancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	private void initialize()
	{
		saveButton.setDisable(true);	
		addStatus.textProperty().addListener(event ->{
			String name = addStatus.getText();
			if(addStatus.getText().isEmpty())
			{
				statusNameError.setVisible(false);
				saveButton.setDisable(true);	
			}
			else
			{
				if(!MUtils.IsString(name))
					statusNameError.setVisible(true);
				else {
						statusNameError.setVisible(false);
						saveButton.setDisable(false);	
				}
			}
		});
		statusList.setItems(StatusDatabase.selectAllStatus());
	}
}
