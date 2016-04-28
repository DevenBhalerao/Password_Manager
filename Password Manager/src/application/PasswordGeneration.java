package application;

import java.util.LinkedList;
import java.util.List;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PasswordGeneration {
	
	private String userID;
	private String masterPassword;
	private boolean hasDigits;
	private boolean hasLowercase;
	private boolean hasUppercase;
	private boolean hasSpecial;

	List<CharacterRule> rules;

	@FXML 
	private TextField Password;

	@FXML
	private CheckBox Digits;

	@FXML
	private CheckBox Special;

	@FXML
	private CheckBox UpperCase;

	@FXML
	private CheckBox LowerCase;

	@FXML 
	private Button generatePassword;
	
	@FXML 
	private Button backBtn;

	@FXML 
	private TextField PasswordLength;
	private Account account;

	@FXML
	private void onChange(MouseEvent event) throws Exception{

		String length = PasswordLength.getText();
		if(length.equals("0")){
			PasswordLength.getStyleClass().add("text-field-error");
		}
		else if(!length.matches("[0-9]+")){
			PasswordLength.getStyleClass().add("text-field-error");
		}
		else if(Integer.parseInt(length) > 14 || Integer.parseInt(length) < 4  ){
			new AlertBox().display("Invalid Length ", "Length should be less than 14!!");
		}
		else{

			boolean choiceValid = getChoices();

			if(choiceValid){
				addRules();

				String GPassword = generatePassword(length);
				Password.setText(GPassword);
			}
		}
	}

	
	private String generatePassword(String length) {
		// TODO Auto-generated method stub
		PasswordGenerator generator = new PasswordGenerator();
		String password = generator.generatePassword(Integer.parseInt(length), rules);
		return password;
	}

	private void addRules() {
		// TODO Auto-generated method stub

		rules = new LinkedList<CharacterRule>();

		if(hasDigits)
			rules.add(new CharacterRule(EnglishCharacterData.Digit, 1 ));
		if(hasLowercase)
			rules.add(new CharacterRule(EnglishCharacterData.LowerCase, 1 ));
		if(hasUppercase)
			rules.add(new CharacterRule(EnglishCharacterData.UpperCase, 1));
		if(hasSpecial)
			rules.add(new CharacterRule(EnglishCharacterData.Special, 1));

	}

	private boolean getChoices() throws Exception{
		// TODO Auto-generated method stub
		if(!Digits.isSelected() && !Special.isSelected() && !UpperCase.isSelected() && !LowerCase.isSelected() ){
			new AlertBox().display("no choice Selected!"," you must select atleast one choice!!!!!!!!!!");
			Digits.getStyleClass().add("check-box-error");
			UpperCase.getStyleClass().add("check-box-error");
			Special.getStyleClass().add("check-box-error");
			LowerCase.getStyleClass().add("check-box-error");
			return false;
		}else{
			if(Digits.isSelected())
				hasDigits = true;
			else
				hasDigits = false;
			
			if(Special.isSelected())
				hasSpecial = true;
			else
				hasSpecial = false;
			
			if(UpperCase.isSelected())
				hasUppercase = true;
			else
				hasUppercase = false;
			
			if(LowerCase.isSelected())
				hasLowercase = true;
			else
				hasLowercase = false;
			
			return true;
		}
	}

	public void setUser(Account account) {
		// TODO Auto-generated method stub
		this.account = account;
	}
	
	@FXML
	private void onBackBtnClick(MouseEvent event) throws Exception {
		Stage stage = (Stage) PasswordLength.getScene().getWindow();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Home_Screen.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		HomeScreen controller = fxmlLoader.<HomeScreen> getController();
		controller.setUser(account);
		stage.setTitle("Hello World");
		stage.setScene(new Scene(root, 700, 575));
		stage.show();
	}

}
