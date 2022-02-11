package SymbolTable.Symbols;

import Types.*;
import java.util.ArrayList;

public class TypeSymbol extends Symbol{
    public int size;
    public ArrayList<Integer> offsets;

    public TypeSymbol(String name, Type type, int siz, ArrayList<Integer> offs){
        super(name,type);
        size = siz;
        offsets = offs;
    }

    public TypeSymbol(String name, Type type, int siz){
        super(name,type);
        size = siz;
        offsets = null;
    }
}
