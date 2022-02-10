package SymbolTable.Symbols;

import Types.*;

public class Symbol {
    public Symbol() {
        name = "";
        level = -1;
        type = null;
        collision = -1;
    }

    public Symbol(String name, Type type) {
        this.name = name;
        this.level = -1;
        this.type = type;
        collision = -1;
    }

    public String name;
    public int level;
    public Type type;
    public int collision;
}
