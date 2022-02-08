package Intermediate;

import Intermediate.Nodes.*;
import  java.util.ArrayList;
import java.util.Hashtable;

public class IntermediateTree {
    public IntermediateTree() {
        root_ = new SEQ(null, null);
        lastStmtNode_ = root_;
        currentLabel_ = currentTemp_ = freeStmtSlot_ = 0;
        labels_ = new ArrayList<String>();
        varMapping_ = new Hashtable<String, String>();
    }

    public void appendNode(AbsNode n) {
        if(freeStmtSlot_ < 2) {
            lastStmtNode_.children[freeStmtSlot_] = n;
            freeStmtSlot_++;
        }else {
            SEQ newStmtNode = new SEQ(lastStmtNode_.children[1], n);
            lastStmtNode_.children[1] = newStmtNode;
            lastStmtNode_ = newStmtNode;
        }
    }

    public String makeLabel() {
        String newLabel = "L" + currentLabel_;
        labels_.add(newLabel);
        currentLabel_++;
        return newLabel;
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
        String[] keys = (String[]) varMapping_.keySet().toArray();
        String res = "";
        for(int i = 0; i < keys.length - 1; i++)
            res += String.format("Variavel \"%s\" esta associada a \"%s\"\n", keys[i], varMapping_.get(keys[i]));
        res += String.format("Variavel \"%s\" esta associada a \"%s\"", keys[keys.length - 1],
            varMapping_.get(keys[keys.length - 1]));
        return res;
    }

    public String getTreeString() {
        return root_.toString();
    }

    private SEQ root_;
    private SEQ lastStmtNode_;
    private int currentLabel_;
    private int currentTemp_;
    private int freeStmtSlot_;
    private ArrayList<String> labels_;
    private Hashtable<String, String> varMapping_;
}
