package SymbolTable;
import java.util.ArrayList;
import java.util.Hashtable;
import SymbolTable.Symbols.Symbol;


public class SymbolTable {
    public SymbolTable() {
        level = -1;
        table = new ArrayList<Symbol>();
        scope = new ArrayList<Integer>();
        thash = new Hashtable<String, Integer>();
    }

    public void enterBlock() {
        level++;
        scope.add(table.size());
    }

    public void exitBlock() {
        int s = table.size();
        int b = scope.get(level);
        while(s > b) {
            s--;
            thash.replace(table.get(s).name, table.get(s).collision);
        }
        level--;
    }

    public Symbol getSymbol(String name) {
        int k = getPositionFromHash(name);
        while(k >= 0) {
            if(table.get(k).name == name)
                return table.get(k);
            k = table.get(k).collision;
        }
        throw new SymbolError(String.format("Symbol \"%s\" not found on the symbol table!", name), null);
    }

    public void installSymbol(Symbol newSymbol) throws SymbolError {
        int k = getPositionFromHash(newSymbol.name);
        while(k >= scope.get(level)) {
            if(table.get(k).name == newSymbol.name)
                throw new SymbolError(String.format("The symbol \"%s\" already exists on level %d!", newSymbol.name, newSymbol.level), null);
            k = table.get(k).collision;
        }
        newSymbol.collision = getPositionFromHash(newSymbol.name);
        table.add(newSymbol);
        thash.put(newSymbol.name, table.size() - 1);
    }

    public void removeSymbol(Symbol s) {
        int k = getPositionFromHash(s.name);
        while(k >= 0) {
            if(table.get(k).name == s.name) {
                thash.replace(s.name, table.get(k).collision);
                table.remove(k);
            }
            k = table.get(k).collision;
        }
    }

    private int getPositionFromHash(String name) {
        try{
            return thash.get(name);
        }catch(NullPointerException e) {
            return -1;
        }
    }
    
    private ArrayList<Symbol> table;
    private ArrayList<Integer> scope;
    private Hashtable<String, Integer> thash;
    private int level;
}
