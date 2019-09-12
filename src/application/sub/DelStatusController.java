package application.sub;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import application.database.StatusDatabase;
import application.utils.MUtils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

public class DelStatusController {
	@FXML
	private ListView<String> statusList;
	@FXML
	private Button deleteStatus;
	@FXML
	private Button cancel;

	// Event Listener on Button[#deleteStatus].onAction
	@FXML
	private void initialize()
	{
		statusList.setItems(StatusDatabase.selectAllStatus());
		statusList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
	
	@FXML
	public void onDeleteStatus(ActionEvent event) {
		ObservableList<String> status = statusList.getSelectionModel().getSelectedItems();
		if(status.size() == 0)
		{
			MUtils.showErrorMessage("Œÿ√  ÕœÌœ", "·„ Ì „  ÕœÌœ «Ï  ‘ŒÌ’");
		}
		else
		{
			if(MUtils.showConfirmMessage(" √ﬂÌœ Õ–›  ‘ŒÌ’ ", "Â· «‰  „ √ﬂœ „‰ Õ–› «· ‘«ŒÌ’ «·„Õœœ…ø"))
			{
				for(String s : status)
				{
					StatusDatabase.deleteStatus(s);
				}
				MUtils.notification(" √ﬂÌœ Õ–›  ‘ŒÌ’", " „ «Õ–› «· ‘«ŒÌ’ «·„Õœœ…  »‰Ã«Õ");

				Stage stage = (Stage) cancel.getScene().getWindow();
				stage.close();
			}
			
		}
		
	}
	// Event Listener on Button[#cancel].onAction
	@FXML
	public void onCancel(ActionEvent event) {
		Stage stage = (Stage) cancel.getScene().getWindow();
		stage.close();
	}
}
