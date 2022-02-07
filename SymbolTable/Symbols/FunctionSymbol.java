package SymbolTable.Symbols;

import SymbolTable.Types;

public class FunctionSymbol extends Symbol {
    public FunctionSymbol() {
        super();
    }

    public FunctionSymbol(String name, Types[] argList) {
        //TODO Não sei se deve ser criado um tipo para funções. De qualquer forma, o tipo deve ser substituido aqui
        super(name, Types.UNDEFINED); 
        this.argList = argList;
    }

    public Types[] argList;
}
