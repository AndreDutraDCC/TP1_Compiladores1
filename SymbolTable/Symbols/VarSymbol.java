package SymbolTable.Symbols;

import Types.*;

public class VarSymbol extends Symbol{
    public VarSymbol(String nam, Type ty, int temp, int siz){
        super(nam,ty);
        tmp = temp;
        size = siz;
    }

    public int tmp,size;
}
