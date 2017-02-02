package aarkue;

import aarkue.Data.StateResponse;
import aarkue.GUI.StatePane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;

import java.util.Optional;

/**
 * Created by aarim on 27.01.2017.
 * Klasse die alle dynamischen (d.h. in der Anzahl nicht festgelegt) GUI-Elemente verwaltet
 * Hier spielt sich die wesentliche Abfolge ab
 */
public class ZustandsHandler {
    private char[] alphabet;
    private ObservableList obsList;

    /**
     * Eingabe des Alphabets und der Anzahl der Zustände
     * @param listView
     * @return
     */
    public boolean eingabe(ListView listView) {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("Eingabe!");
        inputDialog.setHeaderText("Geben Sie das Eingabealphabet ein!");
        Optional<String> opt = inputDialog.showAndWait();
        if (opt.isPresent() && validateAlphabet(opt.get().toCharArray())) {
            alphabet = opt.get().toCharArray();
            System.out.println("Gültiges Alphabet erkannt!");
            TextInputDialog inputZustaendeDialog = new TextInputDialog();
            inputZustaendeDialog.setTitle("Eingabe!");
            inputZustaendeDialog.setHeaderText("Geben Sie die Anzahl der Zustaende ein! (Diese werden 0 bis (n-1) benannt)");
            try {
                Optional<String> opt2 = inputZustaendeDialog.showAndWait();
                if(opt2.isPresent()) {
                     int zustaende = Integer.parseInt(opt2.get());
                    System.out.println("Anzahl der Zustände:" + zustaende);
                    Label[] alphLabel = new Label[alphabet.length];
                    alphLabel[0] = new Label("#  Endz.  " + alphabet[0] + "         ");
                    alphLabel[0].setFont(Font.font("Arial", 15));
                    for (int i = 1; i < alphLabel.length; i++) {
                        alphLabel[i] = new Label("            " + alphabet[i] + "          ");
                        alphLabel[i].setFont(Font.font("Arial", 15));
                    }
                    Integer[] zustaendeArr = new Integer[zustaende];
                    for (int i = 0; i < zustaendeArr.length; i++) {
                        zustaendeArr[i] = i;
                    }
                    FlowPane labels = new FlowPane();
                    labels.getChildren().addAll(alphLabel);
                    obsList = FXCollections.observableArrayList(labels);
                    listView.setItems(obsList);
                    for (int i = 0; i < zustaendeArr.length; i++) {
                        obsList.add(new StatePane(i, FXCollections.observableArrayList(zustaendeArr), alphabet.length));
                    }
                    return true;
                }else{
                    throw new NumberFormatException();}

            } catch (NumberFormatException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("NumberFormatException");
                a.setContentText("Bitte geben Sie eine gültige Nummer ein! Eingabevorgang wird abgebrochen.");
                System.out.println("Ungueltige Nummer eingegeben!" + e);
                a.show();
                return false;
            }
        } else {
            System.out.println("Bitte ein gültiges Alphabet eingeben!");
           return false;
        }
    }

    /**
     * Kontrolliert ob ein Alphabet valide ist.
     * @param toValidate Das zu überprüfende Alphabet
     * @return false wenn ein Element doppelt vorkommt oder kein Element im ALphabet vorhanden ist
     *         sonst true
     */
    private boolean validateAlphabet(char[] toValidate){
        if(toValidate.length < 1){
            return false;
        }
        for (int i = 0; i < toValidate.length; i++) {
            for (int j = 0; j < toValidate.length; j++) {
                if(toValidate[i] == toValidate[j] && i != j){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Hier läuft die Simulation des Automaten ab.
     * @param simFlowPane
     * @param enableAlerts ob Alerts angezeigt werden sollen (Der Alert im Endzustand wird immer angezeigt)
     * @param tabPane
     * @return Ob die Simulation ohne Fehler laufen konnte
     * @throws InterruptedException
     */
    public boolean simuliere(FlowPane simFlowPane,boolean enableAlerts,TabPane tabPane) throws InterruptedException {
        System.out.println("Starte Simulation. Alerts sind auf "+enableAlerts+" gesetzt!");
        SingleSelectionModel<Tab> selModel = tabPane.getSelectionModel();

        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("Eingabe!");
        inputDialog.setHeaderText("Geben Sie die Eingabefolge ein!");
        Optional<String> opt = inputDialog.showAndWait();
        if(opt.isPresent() && validateBand(opt.get().toCharArray()) ) {
             char[] temp = opt.get().toCharArray();
                Button[] zustandsButton = new Button[temp.length];
                for (int i = 0; i < temp.length; i++) {
                    zustandsButton[i] = new Button(temp[i] + "");
                    zustandsButton[i].setFont(Font.font("Arial Black", 20));
                    zustandsButton[i].setDisable(true);
                }
                simFlowPane.getChildren().addAll(zustandsButton);


                int zustand = 0;
                selModel.select(1);
                String completeOutput = "";
            boolean endZustandErreicht = false;
                for (int i = 0; i < temp.length; i++) {
                    zustandsButton[i].setDisable(false);
                    StateResponse sr = ((StatePane) obsList.get(zustand + 1)).mapZustand(new String(alphabet).indexOf(temp[i]));
                    completeOutput += sr.getOutput();
                    System.out.println(sr.getOutput());
                    if (enableAlerts) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Zustand: " + zustand);
                        alert.setHeaderText("Zustand: " + zustand + System.lineSeparator() + "(" + zustand + ";" + temp[i] + ") ->" + sr.getNextState());
                        alert.setContentText("Output of this state: " + sr.getOutput());
                        alert.showAndWait();
                    }
                    zustand = sr.getNextState();
                    if (((StatePane) obsList.get(zustand + 1)).isEndZustand()) {//+1 weil vor den StatePanes noch was ist
                        endZustandErreicht = true;
                        Alert finish = new Alert(Alert.AlertType.INFORMATION);
                        finish.setTitle("Zustand: " + zustand + " ist ein Endzustand!");
                        finish.setHeaderText("Zustand: " + zustand + System.lineSeparator());
                        finish.setContentText("Der komplette Output: " + completeOutput);
                        finish.showAndWait();
                        break;
                    }
                    zustandsButton[i].setDisable(true);
                    System.out.println(zustand + "" + temp[i]);

                }
                if(!endZustandErreicht){
                    Alert finish = new Alert(Alert.AlertType.INFORMATION);
                    finish.setTitle("Kein Endzustand erreicht!");
                    finish.setHeaderText("Es wurde kein Endzustand erreicht. Der letzt Zustand ist: " + zustand + System.lineSeparator());
                    finish.setContentText("Der komplette Output: " + completeOutput);
                    finish.showAndWait();
                }
                System.out.println("Complete Output:" + completeOutput);
            simFlowPane.getChildren().removeAll(zustandsButton);
                printAllData();
                return true;

            } else {
                return false;
            }
    }

    /**
     * Überprüft das Eingabeband. Es dürfen nur Elemente des Alphabets verwendet werden
     * @param temp
     * @return
     */
    private boolean validateBand(char[] temp) {
        for (char c : temp) {
            boolean found = false;
            for (char c1 : alphabet) {if (c == c1){found = true;}}
            if(!found){return false;}
        }
        return  true;
    }

    public void toggleTextFieldSichtbarkeit(boolean visible){
        for (int i = 1; i < obsList.size(); i++) {
            ((StatePane) obsList.get(i)).setTextFieldVisibility(visible);
        }
    }

    /**
     * Ansätze zum Import/Export von Automaten. Nicht komplett implementiert.
     */
    private void printAllData(){
        StringBuilder bob = new StringBuilder();
        for (int i = 1; i < obsList.size(); i++) {
            bob.append(((StatePane) obsList.get(i)).getData().formatToString());
        }
        System.out.println(bob.toString());
    }
}
