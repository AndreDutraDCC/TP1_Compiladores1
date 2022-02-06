package SymbolTable.Symbols;

import SymbolTable.Types;

public class Symbol {
    public Symbol() {
        name = "";
        level = -1;
        type = Types.UNDEFINED;
        collision = -1;
    }

    public Symbol(String name, int level, Types type) {
        this.name = name;
        this.level = level;
        this.type = type;
        collision = -1;
    }

    public String name;
    public int level;
    public Types type;
    public int collision;
}
