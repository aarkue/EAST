package aarkue.GUI;

import aarkue.Data.StateData;
import aarkue.Data.StateResponse;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;

/**
 * Created by aarim on 27.01.2017.
 * Diese Klasse bildet eine Reihe in der Zustandstabelle
 * und erweitert FlowPane.
 */
public class StatePane extends FlowPane {
    private final ChoiceBox<Integer>[] resZustaende;
    private final javafx.scene.control.TextField[] resAusgabe;
    private final int zustand;
    private final CheckBox checkEnd = new CheckBox();
    @SuppressWarnings("unchecked")
    public StatePane(int nr, ObservableList<Integer> zustaende, int alph) {
        zustand = nr;
        resZustaende = new ChoiceBox[alph];
        resAusgabe = new TextField[alph];
        for (int i = 0; i < resZustaende.length; i++) {
            resZustaende[i]  = new ChoiceBox<>(zustaende);
            resAusgabe[i] = new TextField();
            resZustaende[i].setValue(0);
        }
        Label l = new Label(nr+"     ");
        l.setFont(Font.font("Arial",15));
        this.getChildren().add(l);
        this.getChildren().add(checkEnd);
        for (int i = 0; i < resAusgabe.length; i++) {
            this.getChildren().add(resZustaende[i]);
            this.getChildren().add(resAusgabe[i]);
            resAusgabe[i].setPrefColumnCount(3);
            resAusgabe[i].setPromptText("Output");
            resAusgabe[i].setFont(Font.font("Arial",14));
        }
        this.setVisible(true);
    }


// --Commented out by Inspection START (28.01.2017 12:45):
//    @Deprecated
//    public void updateData(StateData d){
//        for (int i = 0; i < resZustaende.length; i++) {
//            resZustaende[i].setValue(d.getResultStates()[i]);
//        }
//        for (int i = 0; i < resAusgabe.length; i++) {
//            resAusgabe[i].setText(d.getOutputs()[i]);
//        }
//        checkEnd.setSelected(d.isEndState());
//    }
// --Commented out by Inspection STOP (28.01.2017 12:45)

    /**
     * Bestimmt den nächsten Zustand und die Textausgabe mit Hilfe von dem gelieferten Alphabetindex
     * @param alph Der Index im Alphabet array
     * @return StateResponse mit ausgegeben Text und nächsten Zustand
     */
    public StateResponse mapZustand(int alph){
        return new StateResponse(resAusgabe[alph].getText(),resZustaende[alph].getValue());
    }

    /**
     * Macht alle Text-Felder sichtbar/unsichtbar
     * @param visible
     */
    public void setTextFieldVisibility(boolean visible){
        for (TextField textField : resAusgabe) {
            textField.setVisible(visible);
        }
    }
    public boolean isEndZustand() {
        return checkEnd.isSelected();
    }
    public StateData getData(){
        int[] resuStates = new int[resZustaende.length];
        String[] ausgabe = new String[resAusgabe.length];
        for (int i = 0; i < resuStates.length; i++) {
            resuStates[i] = resZustaende[i].getValue();
        }
        for (int i = 0; i < resAusgabe.length; i++) {
            ausgabe[i] =  resAusgabe[i].getText();
        }
        return new StateData(zustand,ausgabe,resuStates,checkEnd.isSelected());
    }
}

