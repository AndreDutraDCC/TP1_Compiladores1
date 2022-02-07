package Types;

public class Record extends Type {
    public Record() {
        fieldName_ = null;
        fieldType_ = null;
        tail_ = null;
    }

    public Record(String fieldName, Type fieldType, Record tail) {
        fieldName_ = fieldName;
        fieldType_ = fieldType;
        tail_ = tail;
    }

    public boolean isNull(Record r) {
        return (r == null) || (r.fieldName_ == null && r.fieldType_ == null && r.tail_ == null);
    }

    @Override
    public boolean convertsTo(Type t) {
        Type destination = t.actual();
        return (destination instanceof Record) || (destination instanceof Nil);
    }

    private String fieldName_;
    private Type fieldType_;
    private Record tail_;
}
