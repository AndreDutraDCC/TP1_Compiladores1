package SymbolTable;
import java.util.ArrayList;
import java.util.HashMap;
import SymbolTable.Symbols.Symbol;


public class SymbolTable {
    public SymbolTable() {
        level = 0;
        table = new ArrayList<Symbol>();
        scope = new ArrayList<Integer>();
        thash = new HashMap<String, Integer>();
        scope.add(0);
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
            if(table.get(s).collision >= 0)
                thash.replace(table.get(s).name, table.get(s).collision);
            else
                thash.remove(table.get(s).name);
            table.remove(s);
        }
        scope.remove(level);
        level--;
    }

    public Symbol getSymbol(String name) {
        int k = getPositionFromHash(name);
        while(k >= 0) {
            if(table.get(k).name.equals(name))
                return table.get(k);
            k = table.get(k).collision;
        }
        return null;
    }

    public Symbol getSymbolInBlock(String name) {
        int k = getPositionFromHash(name);
        while(k >= scope.get(scope.size() - 1)) {
            if(table.get(k).name.equals(name))
                return table.get(k);
            k = table.get(k).collision;
        }
        return null;
    }

    public void installSymbol(Symbol newSymbol) throws SymbolError {
        int k = getPositionFromHash(newSymbol.name);
        while(k >= scope.get(level)) {
            if(table.get(k).name.equals(newSymbol.name))
                throw new SymbolError(String.format("The symbol \"%s\" already exists on level %d!", newSymbol.name, newSymbol.level), null);
            k = table.get(k).collision;
        }
        newSymbol.collision = getPositionFromHash(newSymbol.name);
        newSymbol.level = level;
        table.add(newSymbol);
        thash.put(newSymbol.name, table.size() - 1);
    }

    public void removeSymbol(String symbolName) {
        int k = getPositionFromHash(symbolName);
        while(k >= 0) {
            if(table.get(k).name.equals(symbolName)) {
                if(table.get(k).collision >= 0)
                    thash.replace(symbolName, table.get(k).collision);
                else
                    thash.remove(symbolName);
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
    private HashMap<String, Integer> thash;
    private int level;
}
