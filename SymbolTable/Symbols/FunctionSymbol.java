package SymbolTable.Symbols;

import Types.*;
import java.util.ArrayList;

public class FunctionSymbol extends Symbol {
    public FunctionSymbol() {
        super();
    }

    public FunctionSymbol(String name, Type ret_type, String labl, int siz, ArrayList<Type> argList, Boolean is_nat) {
        super(name, ret_type); 
        this.argList = argList;
        label=labl;
        is_native = is_nat;
        size = siz;
    }

    public FunctionSymbol(String name, Type ret_type, String labl, int siz, ArrayList<Type> argList) {
        super(name, ret_type); 
        this.argList = argList;
        label=labl;
        is_native = false;
        size = siz;
    }

    public ArrayList<Type> argList;
    public String label;
    public Boolean is_native;
    public int size;
}


