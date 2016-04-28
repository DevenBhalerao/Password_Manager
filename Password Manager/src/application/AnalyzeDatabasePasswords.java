package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AnalyzeDatabasePasswords {
	
	
	private Account account;
	private ObservableList<UserEntry> selectedItems;
	private PasswordAnalysis analyzePassword = new PasswordAnalysis();
	
	@FXML
	private TableView<UserEntry> table;

	@FXML
	private TableColumn<UserEntry, String> loginColumn;

	@FXML
	private TableColumn<UserEntry, String> passwordColumn;
	
	@FXML
	private Button analyzePasswordBT;
	
	@FXML
	private Button changePasswordBT;

	@FXML
	private Button backBT;
	
	@FXML
	private void onanalyzePasswordBTClick(MouseEvent event) throws Exception {
		// System.out.println("lol");
		selectedItems = table.getSelectionModel().getSelectedItems();
		showPasswordAnalysisScreen();
	}
	
	private void showPasswordAnalysisScreen() throws IOException {
		Stage stage = (Stage) table.getScene().getWindow();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Password_Analysis.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		PasswordAnalysis controller = fxmlLoader.<PasswordAnalysis>getController();
        controller.setUser(account);
        controller.setPasswordFieldText(selectedItems.get(0).getPassword());
        controller.isPasswordValid();
		stage.setTitle("Eldian | Password Analysis");
		Scene scene = new Scene(root, 600, 575);
		scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void onchangePasswordBTClick(MouseEvent event) throws Exception {
		// System.out.println("lol");
		selectedItems = table.getSelectionModel().getSelectedItems();
		showEditScreen();
	}

	private void showEditScreen() throws IOException {
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

	public void setUser(Account account) {
		// TODO Auto-generated method stub
		this.account = account;
	}

	public void setSelectedItems(ObservableList<UserEntry> selectedItems) {
		// TODO Auto-generated method stub
		this.selectedItems = selectedItems;
	}

	public void constructTable() throws ClassNotFoundException, SQLException {
		// TODO contsruct table
		ObservableList<UserEntry> userEntries = getWeakPasswords();
		loginColumn.setCellValueFactory(new PropertyValueFactory<UserEntry, String>("login_id"));
		passwordColumn.setCellValueFactory(new PropertyValueFactory<UserEntry, String>("password"));
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		table.setItems(userEntries);
		
	}

	private ObservableList<UserEntry> getWeakPasswords() throws ClassNotFoundException, SQLException {
		// TODO weak passwrods return
		ArrayList<UserEntry> weakPasswords = new ArrayList<>();
		ObservableList<UserEntry> userEntries = FXCollections.observableArrayList(account.getEntries());
		//System.out.println(userEntries);
		for(UserEntry entry : userEntries){
			//System.out.println(entry.getPassword());
			if(!isPasswordValid(entry.getPassword())){
				weakPasswords.add(entry);
			}
		}
		return FXCollections.observableArrayList(weakPasswords);
	}

	private boolean isPasswordValid(String password) {
		// TODO Auto-generated method stub
		analyzePassword.setPassword(password);
		analyzePassword.setIsScreenLoaded(false);
		analyzePassword.initializeScreen();
		return analyzePassword.isPasswordValid();
	}
	
	


}
