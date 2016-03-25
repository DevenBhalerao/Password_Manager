package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;



public class Main extends Application {
	public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
    	String user_id = "1";
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Home_Screen.fxml")); 
        Parent root = (Parent)fxmlLoader.load(); 
        HomeScreen controller = fxmlLoader.<HomeScreen>getController();
        controller.setUser(user_id);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 700, 575));
        primaryStage.show();
    }

}
