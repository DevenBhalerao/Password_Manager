package application;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class AddEntry {

	
	private String UserID;
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
	private void onAddEntryBTN(MouseEvent event) throws ClassNotFoundException, SQLException{
		String accountName = accountNameField.getText();
		String password = passwordField.getText();
		String confirmedPW = reconfirmPWField.getText();
		String category = categoryField.getText();
		String loginID = loginIDField.getText();

		if(password.equals(confirmedPW)){
			Account account = new Account(UserID);
			account.AddEntry(accountName, category, password, loginID);
			Alert alert= new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Successfully Added");
			alert.showAndWait();
		}
		
	}
	
	public void setUser(String userid){
		this.UserID = userid;
		
	}
	
	
	
}
