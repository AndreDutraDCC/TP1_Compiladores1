package SymbolTable.Symbols;

import Types.*;
import java.util.ArrayList;

public class FunctionSymbol extends Symbol {
    public FunctionSymbol() {
        super();
    }

    public FunctionSymbol(String name, Type ret_type, int labl, int pointer, ArrayList<Type> argList) {
        super(name, ret_type); 
        this.argList = argList;
        label=labl;
        stack_ptr = pointer;
    }

    public ArrayList<Type> argList;
    public int label, stack_ptr;
}


