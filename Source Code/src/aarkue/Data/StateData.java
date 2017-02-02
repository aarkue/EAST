package aarkue.Data;

/**
 * Created by aarim on 28.01.2017.
 */

/**
 * Ansätze zur Datenstruktur eines Zustands/einer Zustandstabellenreihe. Nicht komplett implementiert.
 * NOT IN USE ANYMORE
 */
@Deprecated
public class StateData {
    private String[] outputs;
    private int[] resultStates;
    private boolean isEndState;
    private int nr;
    public StateData(int nr,String[] outputs,int[] resultStates,boolean isEndState){
        this.nr = nr;
        this.outputs = outputs;
        this.resultStates = resultStates;
        this.isEndState = isEndState;
    }

    public String[] getOutputs() {
        return outputs;
    }

    public void setOutputs(String[] outputs) {
        this.outputs = outputs;
    }

    public int[] getResultStates() {
        return resultStates;
    }

    public void setResultStates(int[] resultStates) {
        this.resultStates = resultStates;
    }

    public boolean isEndState() {
        return isEndState;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public void setEndState(boolean endState) {
        isEndState = endState;
    }

    public String formatToString(){
        StringBuilder bob = new StringBuilder(nr+"#");
        bob.append(isEndState).append("#");
        int lenght = resultStates.length;
        if(outputs.length < resultStates.length){lenght = outputs.length;
        }
        bob.append(lenght).append("#");
        for (int resultState : resultStates) {
            bob.append(resultState).append("#");
        }
        for (String output : outputs) {
            bob.append(output).append("#");
        }
        bob.append(System.lineSeparator());
        return bob.toString();
    }
}
