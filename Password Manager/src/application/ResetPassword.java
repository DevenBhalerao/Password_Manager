package application;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ResetPassword {
	
	@FXML
	private TextField newPasswordTField;
	

	@FXML
	private TextField reconfirmedPasswordTField;
	

	@FXML
	private Button submit;


	private Account account;
	
	@FXML
	private void onResetBtn(MouseEvent event) throws ClassNotFoundException, SQLException{
		String newPassword = newPasswordTField.getText();
		String reconfirmedPassword = reconfirmedPasswordTField.getText();
		
		if(newPassword.equals(reconfirmedPassword)){
			account.updatePassword(newPassword);
		}
		else{
			Alert alert= new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Passwords do not match");
			alert.showAndWait();
		}
	}
	
	public void setUser(Account account){
		this.account = account;
	}
	
	

}
