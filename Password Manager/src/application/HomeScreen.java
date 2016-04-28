package application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HomeScreen {

	private String userID;
	private Home_Dialog_Box dialogBox;
	private Account account;

	@FXML
	private BorderPane borderPane;

	@FXML
	private TableView<UserEntry> table;

	@FXML
	private TableColumn<UserEntry, String> accountColumn;

	@FXML
	private TableColumn<UserEntry, String> loginColumn;

	@FXML
	private TableColumn<UserEntry, String> passwordColumn;

	@FXML
	private TableColumn<UserEntry, String> categoryColumn;

	@FXML
	private Button addEntryBT;
	
	@FXML
	private Button AnalyzeBT;

	@FXML
	private Button editEntryBT;

	@FXML
	private Button deleteEntryBT;

	@FXML
	private Button PWGenerationBT;

	@FXML
	private Button PWAnalysisBT;

	@FXML
	private TextField searchField;
	@FXML
	private Button SNotesBT;

	@FXML
	private Button AccSettingsBT;

	@FXML
	private Button SearchBTBT;

	@FXML
	private Button LogOutBT;
	private String masterPassword;

	@FXML
	private void onAddEntry(MouseEvent event) throws Exception {
		// System.out.println("lol");
		dialogBox.display("add");
		constructEntryTable();
		table.setItems(getEntries());

		
	}

	@FXML
	private void onDeleteEntry(MouseEvent event) throws Exception {
		// System.out.println("lol");
		ObservableList<UserEntry> selectedItems = getSelectedItems();
		//System.out.println(selectedItems);
		dialogBox.setSelectedItems(selectedItems);
		dialogBox.display("delete");
		constructEntryTable();
		table.setItems(getEntries());
		
	}

	@FXML
	private void onAnalyzeBTClick(MouseEvent event) throws Exception {
		// System.out.println("lol");
		ObservableList<UserEntry> selectedItems = getSelectedItems();
		//System.out.println(selectedItems);
		dialogBox.setSelectedItems(selectedItems);
		dialogBox.display("analyze");
		constructEntryTable();
		table.setItems(getEntries());
		
	}
	
	private ObservableList<UserEntry> getSelectedItems() {
		// TODO Auto-generated method stub
		ObservableList<UserEntry> productSelected;
		productSelected = table.getSelectionModel().getSelectedItems();
		return productSelected;
	}

	@FXML
	private void onEditEntry(MouseEvent event) throws Exception {
		// System.out.println("lol");
		ObservableList<UserEntry> selectedItem = getSelectedItems();
		dialogBox.setSelectedItems(selectedItem);
		dialogBox.display("edit");
		constructEntryTable();
		table.setItems(getEntries());
	}

	@FXML
	private void onPWAnalysisClick(MouseEvent event) throws Exception {
		// System.out.println("lol");
		Stage stage = (Stage) borderPane.getScene().getWindow();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Password_Analysis.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		PasswordAnalysis controller = fxmlLoader.<PasswordAnalysis>getController();
        controller.setUser(account);
		stage.setTitle("Hello World");
		Scene scene = new Scene(root, 600, 575);
		scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void onPWGenerationClick(MouseEvent event) throws Exception {
		// System.out.println("lol");
		Stage stage = (Stage) borderPane.getScene().getWindow();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Password_Generator.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		PasswordGeneration controller = fxmlLoader.<PasswordGeneration>getController();
        controller.setUser(account);
		stage.setTitle("Hello World");
		Scene scene = new Scene(root, 700, 575);
		scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();

	}
	
	
	@FXML
	private void onSNotesClick(MouseEvent event) throws Exception {
		System.out.println("lol");
		Stage stage = (Stage) borderPane.getScene().getWindow();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Secure_Notes.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		SecureNotes controller = fxmlLoader.<SecureNotes>getController();
		controller.setUser(account);
		stage.setTitle("Hello World");
		Scene scene = new Scene(root, 700, 575);
		scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();

	}
	
	@FXML
	private void onLogOutClick(MouseEvent event) throws Exception {
		// System.out.println("lol");
		Stage stage = (Stage) borderPane.getScene().getWindow();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setTitle("Hello World");
		Scene scene = new Scene(root, 700, 575);
		scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void onSearchClick(MouseEvent event) throws Exception {
		// System.out.println("lol");
		String searchString = searchField.getText();
		table.setItems(getSearchItems(searchString));

	}

	private ObservableList<UserEntry> getSearchItems(String searchString) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		ObservableList<UserEntry> userEntries = FXCollections
				.observableArrayList(account.getSearchEntries(searchString));
		return userEntries;
	}

	private void constructEntryTable() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		//System.out.println("lol");
		accountColumn.setCellValueFactory(new PropertyValueFactory<UserEntry, String>("account_name"));
		loginColumn.setCellValueFactory(new PropertyValueFactory<UserEntry, String>("login_id"));
		passwordColumn.setCellValueFactory(new PropertyValueFactory<UserEntry, String>("password"));
		categoryColumn.setCellValueFactory(new PropertyValueFactory<UserEntry, String>("category"));
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		table.setItems(getEntries());
	}

	private ObservableList<UserEntry> getEntries() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		try{
		//System.out.println(account.getEntries());
		ObservableList<UserEntry> userEntries = FXCollections.observableArrayList(account.getEntries());
		return userEntries;
		}
		catch(Exception e){
			
		}
		return null;
	}

	public void setUser(Account account) {
		// TODO Auto-generated method stub
		this.account = account;
		dialogBox = new Home_Dialog_Box(account);
		try {
			constructEntryTable();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
