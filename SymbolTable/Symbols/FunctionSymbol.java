package SymbolTable.Symbols;

import Types.*;

public class FunctionSymbol extends Symbol {
    public FunctionSymbol() {
        super();
    }

    public FunctionSymbol(String name, Type[] argList) {
        //TODO Não sei se deve ser criado um tipo para funções. De qualquer forma, o tipo deve ser substituido aqui
        super(name, null); 
        this.argList = argList;
    }

    public Type[] argList;
}
