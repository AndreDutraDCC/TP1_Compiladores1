package SymbolTable;

import java.util.HashMap;
import java.util.ArrayList;
import SymbolTable.Symbols.*;

public class SymbolTable {
    public SymbolTable(){
        Table = new ArrayList<HashMap<String,Symbol>>();
    }

    public void enterBlock(){
        Table.add(new HashMap<String,Symbol>());
    }

    public void exitBlock(){
        Table.remove(Table.size() - 1);
    }

    public void installSymbol(Symbol s){
        HashMap<String,Symbol> last = Table.get(Table.size()-1);
        last.put(s.name,s);
    }

    public Symbol getSymbol(String name){
        Symbol res = null;
        for(int i = Table.size()-1; i>= 0; i--){
            res = Table.get(i).get(name);
            if(res!=null){
                break;
            }
        }
        return res;
    }

    public void removeSymbol(String name){
        Symbol res = null;
        for(int i = Table.size()-1; i>= 0; i--){
            res = Table.get(i).get(name);
            if(res!=null){
                Table.get(i).remove(name);
                break;
            }
        }
    }
    
    private ArrayList<HashMap<String,Symbol>> Table;
}
