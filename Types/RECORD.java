package Types;

public class RECORD extends Type {
    public RECORD() {
        fieldName_ = null;
        fieldType_ = null;
        tail_ = null;
    }

    public RECORD(String fieldName, Type fieldType, RECORD tail) {
        fieldName_ = fieldName;
        fieldType_ = fieldType;
        tail_ = tail;
    }

    public boolean isNull(RECORD r) {
        return (r == null) || (r.fieldName_ == null && r.fieldType_ == null && r.tail_ == null);
    }

    @Override
    public boolean convertsTo(Type t) {
        Type destination = t.actual();
        return (destination instanceof RECORD) || (destination instanceof NIL);
    }

    private String fieldName_;
    private Type fieldType_;
    private RECORD tail_;
}
