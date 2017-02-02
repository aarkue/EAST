package aarkue.Data;

/**
 * Created by aarim on 28.01.2017.
 * Eine Datenklasse zur einfachen Abfrage des nächsten Zustands
 */
public class StateResponse {
    /**
     * Was ausgegeben werden soll
     */
    private final String output;
    /**
     * In welchen Zustand gegangen werden soll
     */
    private final int nextState;
    public StateResponse(String output,int nextState){
        this.output = output;
        this.nextState = nextState;
    }
// --Commented out by Inspection START (28.01.2017 12:45):
//    public StateResponse(int nextState){
//        this("",nextState);
//    }
// --Commented out by Inspection STOP (28.01.2017 12:45)
    public int getNextState() {
        return nextState;
    }
    public String getOutput() {
        return output;
    }
}
