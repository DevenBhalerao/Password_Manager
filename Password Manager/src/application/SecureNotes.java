package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SecureNotes {

	private String userID = "0";

	@FXML
	private HTMLEditor editor;

	private File currentFile;

	private String password;

	public SecureNotes() {
		// TODO Auto-generated constructor stub

	}

	@FXML
	private void onSaveAs(MouseEvent event) {
		String text = editor.getHtmlText();
		if (userID.equals("0")) {
			password = getPasswordfromUser();
		}
		AES a = new AES(password);
		a.encrypt(text);
		String encryptedText = a.getEncryptedString();
		// System.out.println(text);
		System.out.println("as" + encryptedText);
		openSaveLocation(encryptedText);

	}

	private File openSaveLocation(String text) {
		// TODO Auto-generated method stub
		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CRYPT files (*.ldn)", "*.ldn");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showSaveDialog(editor.getScene().getWindow());

		if (file != null) {
			SaveFile(text, file);
		}

		return file;

	}

	private void SaveFile(String content, File file) {
		try {
			FileWriter fileWriter;
			fileWriter = new FileWriter(file);
			fileWriter.write(content);
			fileWriter.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	@FXML
	private void onOpen() throws IOException {
		String decryptedText = "";
		File previousFile = currentFile;
		System.out.println( "decryptedText is " + decryptedText);
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File("./"));
		currentFile = fileChooser.showOpenDialog(null);
		if (currentFile != null) {
			List<String> text = Files.readAllLines(currentFile.toPath());
			System.out.println("current file is " + currentFile.toPath());
			String fileText = text.get(0);
			if (userID.equals("0")) {
				password = getPasswordfromUser();
			}
			System.out.println("decrypting filetext whic is : " + fileText );
			AES a = new AES(password);
			a.setDecryptedString("");
			String exceptionThrown = a.decrypt(fileText);
			if (exceptionThrown != null) {
				currentFile = previousFile;
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("You can only open files that were craeted by you.");
				alert.showAndWait();
			}

			decryptedText = a.getDecryptedString();
			 System.out.println( "text decrypted is : " + decryptedText);
			editor.setHtmlText(decryptedText);
			System.out.println(editor.getHtmlText());
		}
	}

	private String getPasswordfromUser() {
		// TODO Auto-generated method stub
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Encyption");
		dialog.setHeaderText("Enter your password for encryption");
		dialog.setContentText("Password");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			return result.get();
		}
		return null;
	}

	@FXML
	private void onSave(MouseEvent event) {
		String text = editor.getHtmlText();
		if (userID.equals("0")) {
			password = getPasswordfromUser();
		}
		AES a = new AES(password);
		a.encrypt(text);
		String encryptedText = a.getEncryptedString();
		// System.out.println("s" + encryptedText);
		if (currentFile == null) {
			currentFile = openSaveLocation(encryptedText);
		} else {

			SaveFile(encryptedText, currentFile);
		}
	}

	public void setUser(Account account) {
		// TODO Auto-generated method stub

		this.userID = account.getUserID();
		this.password = account.getUserID();
	}

	@FXML
	private void onBackBtnClick(MouseEvent event) throws Exception {
		Stage stage = (Stage) editor.getScene().getWindow();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Home_Screen.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setTitle("Hello World");
		stage.setScene(new Scene(root, 700, 575));
		stage.show();
	}
}
