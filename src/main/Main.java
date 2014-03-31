package main;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			TabPane root = (TabPane)FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root, 700, 900);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("SWE 2 - MikroERP");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
