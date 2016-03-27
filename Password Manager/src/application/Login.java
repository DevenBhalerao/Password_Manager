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

public class Login implements Initializable{
	
	private String userid;
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
	
	@FXML
	private void onSubmitButtClick(MouseEvent event) throws Exception {
		// System.out.println("lol");
		if(validate()){
			//System.out.println("lol");
		Stage stage = (Stage) Username.getScene().getWindow();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Home_Screen.fxml")); 
        Parent root = (Parent)fxmlLoader.load(); 
        HomeScreen controller = fxmlLoader.<HomeScreen>getController();
        //System.out.println(userid + "submit" + masterPassword);
        controller.setUser(userid,masterPassword);
		stage.setTitle("Hello World");
		stage.setResizable(false);
		Scene scene = new Scene(root, 1000, 575);
		scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
		}
		else{
		Alert alert= new Alert(Alert.AlertType.ERROR);
		alert.setContentText("Invalid Username or Password");
		alert.showAndWait();
		}
	}
	private boolean validate() throws ClassNotFoundException, SQLException{
		String username = Username.getText();
		String password = Password.getText();
		AES a =new AES(username);
		a.encrypt(password);
		masterPassword= a.getEncryptedString();
		a = new AES(masterPassword);
		a.encrypt(username);
		username = a.getEncryptedString();
		//System.out.println(username);
		String correctPass ="";
		try {
			correctPass = new AccountCreation(masterPassword).getPass(username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		a.encrypt(password);
		password = a.getEncryptedString();
		try{
		if(correctPass.equals(password)){
			setUser(new AccountCreation(masterPassword).getUid(username),masterPassword);
			return true;
			
		}}
		catch(Exception e){

			
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
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("reset_password.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setTitle("Hello World");
		Scene scene = new Scene(root, 700, 575);
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
		stage.setTitle("Hello World");
		Scene scene = new Scene(root, 700, 575);
		scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	public void setUser(String user_id, String masterPassword) {
		// TODO Auto-generated method stub
		this.userid = user_id;
		this.masterPassword = masterPassword;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
}
