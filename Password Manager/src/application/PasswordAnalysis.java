package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.EnglishSequenceData;
import org.passay.IllegalRegexRule;
import org.passay.IllegalSequenceRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.RuleResultDetail;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PasswordAnalysis implements Initializable {

	private String userID;
	private String Password;
	private PasswordValidator validator;
	private RuleResult result;
	private PasswordScore score;
	private double scoreVar;
	private boolean isValid;

	@FXML
	private Button backBtn;

	private String PWLengthText = "";
	private String NumberofNumsText = "";
	private String NumberofUpperCText = "";
	private String NumberofLowerCText = "";
	private String NumberofSymbolsText = "";
	private String NumberofCharsText = "";
	private String NumofRepeatedCharText = "";
	private String IllegalSequenceLabelText = "";

	private boolean PWLengthisValid;
	private boolean NumberofNumsisValid;
	private boolean NumberofUpperCisValid;
	private boolean NumberofLowerCisValid;
	private boolean NumberofSymbolsisValid;
	private boolean NumberofCharsisValid;
	private boolean NumofRepeatedCharisValid;
	private boolean IllegalSequenceLabelisValid;
	
	private HashMap<String,Label> errorMap;

	
	@FXML
	private GridPane grid;
	@FXML
	private TextField PasswordInput;

	@FXML
	private ProgressBar Progress;

	@FXML
	private Label PWComment;

	@FXML
	private Label PWLength;

	@FXML
	private Label NumberofChars;

	@FXML
	private Label NumberofNums;

	@FXML
	private Label NumofUpperC;

	@FXML
	private Label NumofSymbols;

	@FXML
	private Label NumofLowerC;

	@FXML
	private Label NumofRepeatedChar;

	@FXML
	private Label IllegalSequenceLabel;

	@FXML
	private Text PWQualityMetrics;

	@FXML
	private Button button;

	@FXML
	private void onChange(KeyEvent event) {
		// System.out.println("lol");
		Password = PasswordInput.getText();
		boolean isValidText = checkText(Password);
		System.out.println(PasswordInput.getText());
		isValid = validatePassword();
		System.out.println("is password valid : " + isValid);
		checkEachValidation();
		setLabelColors();
		System.out.println(score.getPasswordscore());
	}

	private boolean checkText(String password2) {
		// TODO Auto-generated method stub

		return false;
	}

	private void checkEachValidation() {
		// TODO Auto-generated method stub
		initializeLabels();
		ArrayList<String> listofErrors = new ArrayList<String>();
		for (RuleResultDetail msg : result.getDetails()) {
			System.out.println(msg.getErrorCode());
			listofErrors.add(msg.getErrorCode());
			
			errorMap.get(msg.getErrorCode());
		}

		scoreVar = Password.length() * 8;

		if (!isValid) {
			if (listofErrors.contains("TOO_SHORT")) {
				PWLengthText += "-- TOO SHORT";
				if (scoreVar > 20)
					scoreVar -= 10;
			} else {
				PWLengthisValid = true;
			}
			if (listofErrors.contains("INSUFFICIENT_UPPERCASE")) {
				NumberofUpperCText += "-- uppercase insufficient";
				if (scoreVar > 20)
					scoreVar -= 10;
			} else {
				NumberofUpperCisValid = true;
			}
			if (listofErrors.contains("INSUFFICIENT_LOWERCASE")) {
				NumberofLowerCText += "-- lower case insufficient";
				if (scoreVar > 20)
					scoreVar -= 10;
			} else {
				NumberofLowerCisValid = true;
			}
			if (listofErrors.contains("INSUFFICIENT_DIGIT")) {
				NumberofNumsText += "--insufficient digits";
				if (scoreVar > 20)
					scoreVar -= 10;
			} else {
				NumberofNumsisValid = true;
			}
			if (listofErrors.contains("INSUFFICIENT_SPECIAL")) {
				NumberofSymbolsText += "-- insuffcient specials";
				if (scoreVar > 20)
					scoreVar -= 10;
			} else {
				NumberofSymbolsisValid = true;
			}
			if (listofErrors.contains("INSUFFICIENT_ALPHABETICAL")) {
				NumberofCharsText += "-- insuffcient characters";
				if (scoreVar > 20)
					scoreVar -= 10;
			} else {
				NumberofCharsisValid = true;
			}

			if (listofErrors.contains("ILLEGAL_MATCH")) {
				NumofRepeatedCharText += "-- repeated charatcers not allowed";
				if (scoreVar > 20)
					scoreVar -= 10;
			} else {
				NumofRepeatedCharisValid = true;
			}

			if (listofErrors.contains("ILLEGAL_NUMERICAL_SEQUENCE")
					|| listofErrors.contains("ILLEGAL_ALPHABETICAL_SEQUENCE")
					|| listofErrors.contains("ILLEGAL_QWERTY_SEQUENCE")) {
				IllegalSequenceLabelText += "-- sequence of 3 not allowed";
				if (scoreVar > 20)
					scoreVar -= 10;
			} else {
				IllegalSequenceLabelisValid = true;
			}
		} else {
			scoreVar += 20;
		}

		score.setPasswordscore(scoreVar * 0.01);

		PWLength.setText(PWLengthText);
		NumberofNums.setText(NumberofNumsText);
		NumofUpperC.setText(NumberofUpperCText);
		NumofSymbols.setText(NumberofSymbolsText);
		NumofLowerC.setText(NumberofLowerCText);
		NumberofChars.setText(NumberofCharsText);
		NumofRepeatedChar.setText(NumofRepeatedCharText);
		IllegalSequenceLabel.setText(IllegalSequenceLabelText);

		listofErrors = null;
	}

	private void initializeLabels() {
		// TODO Auto-generated method stub
		PWLengthText = "Length of PW : ";
		NumberofNumsText = "Number of digits : ";
		NumberofUpperCText = "Number of upper case:";
		NumberofLowerCText = "Number of lower case :";
		NumberofSymbolsText = "Number of Symobols : ";
		NumberofCharsText = "NumberofChars :";
		NumofRepeatedCharText = "Num of repeated chars";
		IllegalSequenceLabelText = "Illgal sequence";

		PWLengthisValid = false;
		NumberofNumsisValid = false;
		NumberofUpperCisValid = false;
		NumberofLowerCisValid = false;
		NumberofSymbolsisValid = false;
		NumberofCharsisValid = false;
		NumofRepeatedCharisValid = false;
		IllegalSequenceLabelisValid = false;
	}

	private boolean validatePassword() {
		// TODO Auto-generated method stub
		result = validator.validate(new PasswordData(new String(Password)));
		if (result.isValid())
			return true;
		return false;
	}

	private void addRules() {
		validator = new PasswordValidator(Arrays.asList(
				// length between 8 and 16 characters
				new LengthRule(8, 16),

				// at least one upper-case character
				new CharacterRule(EnglishCharacterData.UpperCase, 2),

				// at least one lower-case character
				new CharacterRule(EnglishCharacterData.LowerCase, 2),

				// at least one digit character
				new CharacterRule(EnglishCharacterData.Digit, 2),

				// at least one symbol (special character)
				new CharacterRule(EnglishCharacterData.Special, 2),

				new CharacterRule(EnglishCharacterData.Alphabetical, 2),

				new IllegalRegexRule("(\\w)\\1+"),

				new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 3, true),

				new IllegalSequenceRule(EnglishSequenceData.Numerical, 3, true),

				new IllegalSequenceRule(EnglishSequenceData.USQwerty, 3, true)

		));

	}

	@FXML
	private void pbCheck(ActionEvent event) {
		score.setPasswordscore(score.getPasswordscore() + 0.1);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		addTextLimiter(PasswordInput, 14);
		initializeErrorMap();
		score = new PasswordScore();
		addRules();
		score.setPasswordscore(0);
		score.numberProperty().addListener((v, oldValue, newValue) -> {
			System.out.println(oldValue);
			System.out.println(newValue);

		});
		Progress.progressProperty().bind(score.numberProperty());

	}

	private void initializeErrorMap() {
		// TODO Auto-generated method stub
		HashMap<Label,String> labelToMsgMap = new HashMap<>();
		errorMap = new HashMap<>();
		errorMap.put("TOO_SHORT", value);	
		errorMap.put("INSUFFICIENT_UPPERCASE", value);
		errorMap.put("INSUFFICIENT_DIGIT", value);
		errorMap.put("INSUFFICIENT_SPECIAL", value);
		errorMap.put("INSUFFICIENT_ALPHABETICAL", value);
		errorMap.put("ILLEGAL_NUMERICAL_SEQUENCE", value);
		errorMap.put("ILLEGAL_ALPHABETICAL_SEQUENCE", value);
		errorMap.put("ILLEGAL_QWERTY_SEQUENCE", value);
		
	}

	public static void addTextLimiter(final TextField tf, final int maxLength) {
		tf.textProperty().addListener((ov, oldValue, newValue) -> {
			if (tf.getText().length() > maxLength) {
				String s = tf.getText().substring(0, maxLength);
				tf.setText(s);
			}

		});

	}

	public void setUser(String user_id) {
		// TODO Auto-generated method stub
		this.userID = user_id;
	}

	@FXML
	private void onBackBtnClick(MouseEvent event) throws Exception {
		Stage stage = (Stage) PWQualityMetrics.getScene().getWindow();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Home_Screen.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		HomeScreen controller = fxmlLoader.<HomeScreen> getController();
		controller.setUser(userID);
		stage.setTitle("Hello World");
		stage.setScene(new Scene(root, 700, 575));
		stage.show();
	}

}
