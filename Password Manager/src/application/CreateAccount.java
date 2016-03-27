package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CreateAccount {
	private String userid;
	
	
	@FXML
	private TextField username;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private Button SubmitButt;
	
	@FXML
	private ChoiceBox SeqQDropt;

	@FXML
	private Button CreateButt;
	
	@FXML
	private Button backbtn;
	
	@FXML
	private TextField SeqQDropAns;
	
	@FXML
	private void onCreateButtClick(MouseEvent event) throws Exception {
		System.out.println("lol");
		String Username = username.getText();
		String Password = password.getText();
		String ans = SeqQDropAns.getText();
		String text = null;
		AES a =new AES(Username);
		a.encrypt(Password);
		text = a.getEncryptedString();
		
		AccountCreation account = new AccountCreation(text);
		account.AddEntry(Username, Password, ans);
		
		System.out.println("lol");
	}
	
	@FXML
	private void onBackBtnClick(MouseEvent event) throws Exception {
		Stage stage = (Stage) username.getScene().getWindow();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setTitle("Hello World");
		stage.setScene(new Scene(root, 700, 575));
		stage.show();
	}
}
