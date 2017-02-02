package aarkue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by aarim on 27.01.2017.
 */
public class Main extends Application {
    /**
     * Start Methode von JavaFX. Automatisch generiert.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GUI/window.fxml"));
        primaryStage.setTitle("EAST: Der Simulator für endliche Automaten mit Textausgabefunktion"); // "E"ndliche "A"utomaten "S"imulator (mit) "T"extausgabe
        primaryStage.setScene(new Scene(root, 800, 200));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
