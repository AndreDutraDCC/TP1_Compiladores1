package Intermediate;

import Intermediate.Nodes.*;
import  java.util.ArrayList;
import java.util.Hashtable;

public class IntermediateTree {
    public IntermediateTree() {
        root_ = new SEQ(null, null);
        lastStmtNode_ = root_;
        currentLabel_ = currentTemp_ = 0;
        freeStmtSlots_ = 2;
        labels_ = new ArrayList<String>();
        varMapping_ = new Hashtable<String, String>();
    }

    public void appendNode(AbsNode n) {
        if(freeStmtSlots_ > 0) {
            lastStmtNode_.children[freeStmtSlots_ - 1] = n;
            freeStmtSlots_--;
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

    public String getAssociations() {
        String[] keys = (String[]) varMapping_.keySet().toArray();
        String res = "";
        for(int i = 0; i < keys.length - 1; i++)
            res += String.format("Variavel \"%s\" esta associada a \"%s\"\n", keys[i], varMapping_.get(keys[i]));
        res += String.format("Variavel \"%s\" esta associada a \"%s\"", keys[keys.length - 1],
            varMapping_.get(keys[keys.length - 1]));
        return res;
    }

    private SEQ root_;
    private SEQ lastStmtNode_;
    private int currentLabel_;
    private int currentTemp_;
    private int freeStmtSlots_;
    private ArrayList<String> labels_;
    private Hashtable<String, String> varMapping_;
}
