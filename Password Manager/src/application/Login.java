package application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Login implements Initializable {

	private String userID;
	private String usernameandPassword;
	private String masterPassword;
	
	@FXML
	private TextField Username;

	@FXML
	private PasswordField Password;

	@FXML
	private Button SubmitButt;

	@FXML
	private Button CreateAccButt;

	@FXML
	private Button ForgotPwdButt;

	@FXML
	private Button GuestNotes;
	private String masterpass;

	@FXML
	private void onSubmitButtClick(MouseEvent event) throws Exception {
		// System.out.println("lol");
		if (validate()) {
			// System.out.println("lol");
			Stage stage = (Stage) Username.getScene().getWindow();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Home_Screen.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			HomeScreen controller = fxmlLoader.<HomeScreen> getController();
			// System.out.println(userid + "submit" + masterPassword);
			Account account = new Account(userID, usernameandPassword);
			controller.setUser(account);
			stage.setTitle("Home Screen | Eldian");
			stage.setResizable(false);
			Scene scene = new Scene(root, 878, 538);
			scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Invalid Username or Password");
			alert.showAndWait();
		}
	}

	private boolean validate() throws ClassNotFoundException, SQLException {
		String username = Username.getText();
		String password = Password.getText();
		AES a =new AES(username);
		a.encrypt(password);
		masterpass=a.getEncryptedString();
		a =new AES(masterpass);
		a.encrypt(username);
		username = a.getEncryptedString();
		String correctPass = "";
		//System.out.println("the username is " + username) ;
		try {
			correctPass = new AccountCreation(masterpass).getPass(username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		a.encrypt(password);
		password = a.getEncryptedString();
		//System.out.println(correctPass + " matches user input " +  password);
		try {
			if (correctPass.equals(password)) {
				AccountCreation account = new AccountCreation(masterpass);
				
				account.updateTimeStamp(username);
				setUser(account.getUid(username), username+password);
				
				return true;

			}
		} catch (Exception e) {

		}
		return false;
		// TODO Auto-generated method stub

	}

	private String validatePass(String username2) {
		// TODO Auto-generated method stub

		return "";

	}

	@FXML
	private void onCreateAccButtClick(MouseEvent event) throws Exception {
		// System.out.println("lol");
		Stage stage = (Stage) Username.getScene().getWindow();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("create_acc.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setTitle("Hello World");
		Scene scene = new Scene(root, 700, 575);
		scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void onForgotPwdButtClick(MouseEvent event) throws Exception {
		// System.out.println("lol");
		Stage stage = (Stage) Username.getScene().getWindow();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("for_password.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setTitle("Hello World");
		Scene scene = new Scene(root, 400, 375);
		scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void onGuestNotesClick(MouseEvent event) throws Exception {
		// System.out.println("lol");
		Stage stage = (Stage) Username.getScene().getWindow();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Secure_Notes.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setTitle("Secure Notes | Eldian");
		Scene scene = new Scene(root, 700, 575);
		scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	public void setUser(String user_id, String usernameandPassword) {
		// TODO Auto-generated method stub
		this.userID = user_id;
		this.usernameandPassword = usernameandPassword;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
