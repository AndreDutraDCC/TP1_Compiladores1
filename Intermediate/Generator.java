package Intermediate;

import java.util.ArrayList;
import java.util.Hashtable;
import Intermediate.Nodes.Stm;

public class Generator {
    public static final String defaultProcedure = "null";

    public Generator() {
        currentScope_ = currentTemp_ = currentLabel_ = 0;
        procedureStatements_ = new ArrayList<Stm>(1);
        procedureStatements_.add(null);
        procedureLabels_ = new ArrayList<String>(1);
        procedureLabels_.add(defaultProcedure);
        dataLabels_ = new Hashtable<Integer, String>();
        varMapping_ = new Hashtable<String, String>();
        parMapping_ = new Hashtable<String, String>();
        procAssociations_ = new Hashtable<String, Integer>();
    }

    public int getLabel() {
        return currentLabel_++;
    }

    public int makeDataLabel(String data) {
        int lbl = getLabel();
        dataLabels_.put(lbl, data);
        return lbl;
    }

    public int getTemporary() {
        return currentTemp_++;
    }

    public int temporaryFromVariable(String varName) {
        int associatedId = getTemporary();
        varMapping_.put(varName, "t" + associatedId);
        return associatedId;
    }

    public int temporaryFromParam(String parName) {
        int associatedId = getTemporary();
        parMapping_.put(parName, "t" + associatedId);
        return associatedId;
    }

    // Cria um novo procedimento com o nome dado, retornando o id do escopo associado
    public int createProcedure(String procedureName) {
        if(procedureName == "")
            return 0;
        int lbl = getLabel();
        int scope = ++currentScope_;
        procAssociations_.put(procedureName, scope);
        procedureStatements_.add(null);
        procedureLabels_.add("L" + lbl);
        return scope;
    }

    // Retorna o label associado ao escopo (procedimento) cujo id é dado
    public String labelFromScope(int scopeId) {
        return procedureLabels_.get(scopeId);
    }

    // Associa o escopo cujo id é dado à árvore intermediária de raiz s
    public void associateScope(int scopeId, Stm s) {
        procedureStatements_.set(scopeId, s);
    }

    public String getVariableAssociations() {
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

    public String getParameterAssociations() {
        int numAssociations = parMapping_.size();
        if(numAssociations <= 0)
            return "";
        String[] keys = new String[numAssociations];
        keys = parMapping_.keySet().toArray(keys); 
        String res = "";
        for(int i = 0; i < keys.length - 1; i++)
            res += String.format("Parametro \"%s\" esta associado a \"%s\"\n", keys[i], parMapping_.get(keys[i]));
        res += String.format("Parametro \"%s\" esta associado a \"%s\"", keys[keys.length - 1],
            parMapping_.get(keys[keys.length - 1]));
        return res;
    }

    public String getProcedureAssociations() {
        int numAssociations = procAssociations_.size();
        if(numAssociations <= 0)
            return "";
        String[] keys = new String[numAssociations];
        keys = procAssociations_.keySet().toArray(keys);
        String res = "";
        for(int i = 0; i < keys.length; i++)
            res += String.format("%s esta rotulada como %s", keys[i], procedureLabels_.get(procAssociations_.get(keys[i])));
        res += String.format("%s esta rotulada como %s", keys[keys.length - 1], procedureLabels_.get(procAssociations_.get(keys[keys.length - 1])));
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
            res += String.format("L%d = \"%s\"\n", keys[i], dataLabels_.get(keys[i]));
        return res;
    }

    public String genIntermediateCode() {
        String res = "";
        int numScopes = procedureLabels_.size();
        for(int i = 0; i < numScopes; i++) {
            if(procedureLabels_.get(i) != "")
                res += procedureLabels_.get(i) + ":\n";
            res += procedureStatements_.get(i).stringRepresentation("") + "\n";
        }
        return res;
    }

    private int currentTemp_;
    private int currentLabel_;
    private int currentScope_;
    private ArrayList<Stm> procedureStatements_;
    private ArrayList<String> procedureLabels_;
    private Hashtable<Integer, String> dataLabels_;
    private Hashtable<String, String> varMapping_;
    private Hashtable<String, String> parMapping_;
    private Hashtable<String, Integer> procAssociations_;
}
