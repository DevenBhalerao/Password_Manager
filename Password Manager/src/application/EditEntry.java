package application;

import java.sql.SQLException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class EditEntry{

	private String entryID;
	private ObservableList<UserEntry> selectedItems;
	private UserEntry selectedEntry;
	private Account account;

	public EditEntry() {
		// TODO Auto-generated constructor stub
	}
	
	public EditEntry(Account account) {
		// TODO Auto-generated constructor stub
		this.account = account;
	}

	@FXML
	private TextField accountNameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private PasswordField reconfirmPWField;

	@FXML
	private Button AddEntryBTN;

	@FXML
	private Button CancelBTN;

	@FXML
	private TextField categoryField;

	@FXML
	private TextField loginIDField;

	@FXML
	private void onEditEntryBTN(MouseEvent event) throws ClassNotFoundException, SQLException {
		String accountName = accountNameField.getText();
		String password = passwordField.getText();
		String confirmedPW = reconfirmPWField.getText();
		String category = categoryField.getText();
		String loginID = loginIDField.getText();
		
		if (password.equals(confirmedPW)) {
			account.editEntry(accountName, category, password, loginID, entryID);
			Alert alert= new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Successfully Edited");
			alert.showAndWait();
		}else{
			Alert alert= new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Passwords do not match");
			alert.showAndWait();
		}

	}

	private void setEntryID() {
		// TODO Auto-generated method stub
		for(UserEntry entry : selectedItems){
			selectedEntry = entry;
			this.entryID = entry.getEntry_id();
		}
	}

	public void setUser(Account account) {
		this.account = account;

	}

	public void setSelectedItems(ObservableList<UserEntry> selectedItems) {
		//System.out.println(selectedItems);
		this.selectedItems = selectedItems;
	}
	

	public void setDataintoFields() {
		// TODO Auto-generated method stub
		setEntryID();
		accountNameField.setText(selectedEntry.getAccount_name());
		passwordField.setText(selectedEntry.getPassword());
		categoryField.setText(selectedEntry.getCategory());
		loginIDField.setText(selectedEntry.getLogin_id());
		
	}
	
}
