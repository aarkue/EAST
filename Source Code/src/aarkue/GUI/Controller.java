package aarkue.GUI;

import aarkue.ZustandsHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.layout.FlowPane;

/**
 * Created by aarim on 27.01.2017
 * Controller Klasse um GUI Kontrolle zu geben
 */
public class Controller {

    /**
     * Checkbox um auszuwählen ob Alerts nach jedem Schritt der Simulation angezeigt werden sollen
     */
    @FXML
    private  CheckBox alertSelector;
    /**
     * ListView bietete eine Listanzeige die mit Hilfe der {@linkplain StatePane} Klasse eine Art Zustandstabelle erstellt
     */
    @FXML
    private  ListView listView;
    /**
     * FlowPane ist der LayoutManager der bei dem "Simulations"-Tab verwendet wird
     */
    @FXML
    private  FlowPane simFlowPane;
    /**
     * Die Instanz von TabPane wird dazu verwendet den Tab beim Start der Simulation zu ändern
     */
    @FXML
    private  TabPane tabPane;
    /**
     * Checkbox um zu bestimmen, ob Texteingabe-Felder im GUI angezeigt werden sollen
     */
    @FXML
    private  CheckBox visibleTextField;
    /**
     * Eine Instanz vom ZustandsHandler, der sämtliche variablen GUI-Elemente kontrolliert
     */
    private final ZustandsHandler handler = new ZustandsHandler();

    /**
     * Wird beim Klicken auf den "Start!" Button aufgerufen und gibt den Aufruf an den ZustandsHandler weiter
     */
    @FXML
    private void eingabeAlphabet() {
        if(handler.eingabe(listView)){
            visibleTextField.setDisable(false);
        }
    }

    /**
     * Wird beim Klicken auf den "Starte Simulation!" Button aufgerufen und startet die analoge Methode
     * des Zustandshandlers in einem neuen Thread
     * @throws InterruptedException
     */
    @FXML
    private void starteSimulation() throws InterruptedException {
        Platform.runLater(() -> {
            try {
                if(handler.simuliere(simFlowPane, alertSelector.isSelected(), tabPane)){
                    System.out.println("Simulation war erfolgreich!");
                }else{
                    System.out.println("Fehler bei Simulation");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Wird durch verändern der CheckBox "Textfelder anzeigen" aufgerufen
     * @param e
     */
    @FXML
    private void toggleTextField(ActionEvent e){
        System.out.println(e);
        handler.toggleTextFieldSichtbarkeit(visibleTextField.isSelected());
    }}
