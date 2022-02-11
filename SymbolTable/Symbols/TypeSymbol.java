package SymbolTable.Symbols;

import Types.*;
import java.util.ArrayList;

public class TypeSymbol extends Symbol{
    public int size;
    public ArrayList<VarSymbol> fields;

    public TypeSymbol(String name, Type type, int siz, ArrayList<VarSymbol> fds){
        super(name,type);
        size = siz;
        fields = fds;
    }

    public TypeSymbol(String name, Type type, int siz){
        super(name,type);
        size = siz;
        fields = null;
    }
}
