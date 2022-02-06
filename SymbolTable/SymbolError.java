package SymbolTable;

public class SymbolError extends RuntimeException {
    public SymbolError(String message, Throwable err) {
        super(message, err);
    }
}
