package Intermediate;

import Intermediate.Nodes.*;
import java.util.Hashtable;

public class IntermediateTree {
    public IntermediateTree() {
        root_ = new SEQ(null, null);
        lastStmtNode_ = root_;
        freeLabel_ = currentTemp_ = freeStmtSlot_ = 0;
        dataLabels_ = new Hashtable<Integer, String>();
        varMapping_ = new Hashtable<String, String>();
    }

    public void appendNode(Stm n) {
        if(freeStmtSlot_ < 2) {
            lastStmtNode_.children[freeStmtSlot_] = n;
            freeStmtSlot_++;
        }else {
            SEQ newStmtNode = new SEQ(lastStmtNode_.children[1], n);
            lastStmtNode_.children[1] = newStmtNode;
            lastStmtNode_ = newStmtNode;
        }
    }

    public int makeDataLabel(String data) {
        dataLabels_.put(freeLabel_, data);
        return freeLabel_++;
    }

    public int getTemporary() {
        return currentTemp_++;
    }

    public int temporaryFromVariable(String varName) {
        int associatedId = getTemporary();
        varMapping_.put(varName, "t" + associatedId);
        return associatedId;
    }

    public String getAssociationsString() {
        int numAssociations = varMapping_.size();
        if(numAssociations <= 0)
            return "";
        String[] keys = new String[numAssociations];
        keys = varMapping_.keySet().toArray(keys); 
        String res = "";
        for(int i = 0; i < keys.length - 1; i++)
            res += String.format("Variavel \"%s\" esta associada a \"%s\"\n", keys[i], varMapping_.get(keys[i]));
        res += String.format("Variavel \"%s\" esta associada a \"%s\"", keys[keys.length - 1],
            varMapping_.get(keys[keys.length - 1]));
        return res;
    }

    public String getDataLabelsString() {
        int numLabels = dataLabels_.size();
        if(numLabels <= 0)
            return "";
        Integer[] keys = new Integer[numLabels];
        keys = dataLabels_.keySet().toArray(keys);
        String res = "";
        for(int i = 0; i < keys.length; i++)
            res += String.format("L%d = \"%s\"\n", i, dataLabels_.get(i));
        return res;
    }

    public String getTreeString() {
        return root_.stringRepresentation("");
    }

    private SEQ root_;
    private SEQ lastStmtNode_;
    private int freeLabel_;
    private int currentTemp_;
    private int freeStmtSlot_;
    private Hashtable<Integer, String> dataLabels_;
    private Hashtable<String, String> varMapping_;
}
