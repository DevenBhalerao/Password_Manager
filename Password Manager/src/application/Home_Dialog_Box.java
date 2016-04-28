package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Home_Dialog_Box {

	private String userID;
	private ObservableList<UserEntry> selectedItems;
	private String usernameandPassword;
	private Account account;
	

	public Home_Dialog_Box(Account account) {
		// TODO Auto-generated constructor stub
		this.account = account;
	}

	public void display(String function) throws Exception {
		if (function.equals("add"))
			addDialogBox();
		if (function.equals("delete"))
			deleteDialogBox();
		if (function.equals("edit"))
			editDialogBox();
		if (function.equals("analyze"))
			analyzeDialogBox();

	}

	private void analyzeDialogBox() throws IOException, ClassNotFoundException, SQLException {
		// TODO analyze dialog box
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Edit Entry");

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Analyze_Passwords.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		AnalyzeDatabasePasswords controller = fxmlLoader.<AnalyzeDatabasePasswords> getController();
		window.setResizable(false);
		controller.setUser(account);
		controller.constructTable();
		window.setScene(new Scene(root, 480, 425));
		window.showAndWait();
		
	}

	private void editDialogBox() throws Exception {
		// TODO Auto-generated method stub
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Edit Entry");
		window.setHeight(550);
		window.setWidth(450);

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Edit_Entry.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		EditEntry controller = fxmlLoader.<EditEntry> getController();
		window.setResizable(false);
		controller.setUser(account);
		controller.setSelectedItems(selectedItems);
		controller.setDataintoFields();
		window.setScene(new Scene(root, 700, 575));
		window.showAndWait();
	}

	private void deleteDialogBox() throws IOException, ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Look, a Confirmation Dialog");
		alert.setContentText("Are you ok with this?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			DeleteEntry delete = new DeleteEntry(account);
			delete.deleteEntries(selectedItems);
			// System.out.println(selectedItems + UserID);
			Alert alertdelete = new Alert(Alert.AlertType.INFORMATION);
			alertdelete.setContentText("Successfully Deleted");
			alertdelete.showAndWait();
		} else {

		}
	}

	private void addDialogBox() throws Exception {
		// TODO Auto-generated method stub
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Add Entry");
		window.setHeight(550);
		window.setWidth(375);
		window.setResizable(false);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Add_Entry.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		AddEntry controller = fxmlLoader.<AddEntry> getController();
		controller.setUser(account);
		window.setScene(new Scene(root, 700, 575));
		window.showAndWait();

	}

	public void setSelectedItems(ObservableList<UserEntry> selectedItems) {
		this.selectedItems = selectedItems;
	}

}
