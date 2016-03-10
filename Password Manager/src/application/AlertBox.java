package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
	
	public void display(String title, String message){
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		
		Button bt = new Button("OK, Sorry");
		bt.setOnAction(e -> {
			window.close();
			e.consume();
			
		});
		
		Label lb = new Label();
		lb.setText(message);
		
		VBox layout = new VBox();
		layout.getChildren().addAll(lb,bt);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
	}

}
