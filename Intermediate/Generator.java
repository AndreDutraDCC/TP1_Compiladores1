package Intermediate;

import java.util.ArrayList;
import java.util.Hashtable;
import Intermediate.Nodes.Stm;

public class Generator {
    public Generator() {
        currentScope_ = currentTemp_ = currentLabel_ = 0;
        scopeTrees_ = new ArrayList<IntermediateTree>(1);
        scopeTrees_.add(new IntermediateTree());
        scopeNames_ = new ArrayList<String>(1);
        scopeNames_.add("null");
        dataLabels_ = new Hashtable<Integer, String>();
        varMapping_ = new Hashtable<String, String>();
    }

    public int makeDataLabel(String data) {
        dataLabels_.put(currentLabel_, data);
        return currentLabel_++;
    }

    public int getTemporary() {
        return currentTemp_++;
    }

    public int temporaryFromVariable(String varName) {
        int associatedId = getTemporary();
        varMapping_.put(varName, "t" + associatedId);
        return associatedId;
    }

    public void appendStatement(Stm s) {
        scopeTrees_.get(currentScope_).appendNode(s);
    }

    // Cria um novo escopo e o torna o escopo atual, retornando o ID do rótulo associado
    public int enterScope() {
        int scopeLabelId = currentLabel_++;
        scopeNames_.add("L" + scopeLabelId);
        scopeTrees_.add(new IntermediateTree());
        currentScope_++;
        return scopeLabelId;
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

    public String genIntermediateCode() {
        String res = "";
        int numScopes = scopeNames_.size();
        for(int i = 0; i < numScopes; i++) {
            res += scopeNames_.get(i) + ":\n";
            res += scopeTrees_.get(i).getTreeString() + "\n";
        }
        return res;
    }

    private int currentTemp_;
    private int currentLabel_;
    private int currentScope_;
    private ArrayList<IntermediateTree> scopeTrees_;
    private ArrayList<String> scopeNames_;
    private Hashtable<Integer, String> dataLabels_;
    private Hashtable<String, String> varMapping_;
}
