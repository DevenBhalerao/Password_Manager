package application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CreateAccount implements Initializable {
	private String userid;
	
	
	@FXML
	private TextField username;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private Button SubmitButt;
	
	@FXML
	private ComboBox<String> SeqQDropt;

	@FXML
	private Button CreateButt;
	
	@FXML
	private Button backbtn;
	private String masterpass;
	@FXML
	private TextField SeqQDropAns;
	
	@FXML
	private void onCreateButtClick(MouseEvent event) throws Exception {
		//System.out.println("on create init");
		String Username = username.getText();
		String Password = password.getText();
		AES a =new AES(Username);
		a.encrypt(Password);
		masterpass=a.getEncryptedString();
		AccountCreation account = new AccountCreation(masterpass);
		account.AddEntry(Username, Password);
		//System.out.println("lol");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//System.out.println("initizled");
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
