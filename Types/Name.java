package Types;

import java.lang.annotation.Inherited;

public class Name extends Type {
    public Name(String name) {
        name_ = name;
    }

    public void bind(Type t) {
        binding_ = t;
    }

    public boolean hasLoop() {
        Type aux = binding_;
        boolean loops;
        binding_ = null;
        if(aux == null)
            loops = true;
        else if(aux instanceof Name)
            loops = ((Name) aux).hasLoop();
        else
            loops = false;
        binding_ = aux;
        return loops; 
    }

    @Override
    public Type actual() {
        return binding_.actual();
    }

    @Override
    public boolean convertsTo(Type t) {
        return actual().convertsTo(t);
    }



    private String name_;
    private Type binding_;
}
