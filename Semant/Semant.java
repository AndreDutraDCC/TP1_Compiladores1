package Semant;

import AbsSyn.*;
import Types.*;
import SymbolTable.*;
import ErrorHandle.*;

public class Semant {
    public Semant(Exp synt_root,ErrorMsg error){
        root = synt_root;
        err = error;

        VarEnv = new SymbolTable();
        TyEnv = new SymbolTable();
        FunEnv = new SymbolTable();
    }

    //Tradução de declarações

    public Stm translateDec(Dec d){return null;}

    public Stm translateDecVar(DecVar d){return null;}

    public Stm translateDecType(DecType d){return null;}

    public Stm translateDecFunc(DecFunc d){return null;}

    
    //Tradução de expressões

    public Stm translateExp(Exp e){return null;}

    public Stm translateExpInt(ExpInt e){return null;}

    public Stm translateExpString(ExpString e){return null;}

    public Stm translateExpNil(ExpNil e){return null;}

    public Stm translateExpOp(ExpOp e){return null;}

    public Stm translateExpIf(ExpIf e){return null;}

    public Stm translateExpAttr(ExpAttr e){return null;}

    public Stm translateExpArray(ExpArray e){return null;}

    public Stm translateExpRec(ExpRec e){return null;}

    public Stm translateExpBreak(ExpBreak e){return null;}

    public Stm translateExpCall(ExpCall e){return null;}

    public Stm translateExpFor(ExpFor e){return null;}

    public Stm translateExpWhile(ExpWhile e){return null;}

    public Stm translateExpLet(ExpLet e){return null;}

    public Stm translateExpSeq(ExpSeq e){return null;}

    //Tradução de Variáveis

    public Stm translateVar(Var v){return null;}

    public Stm translateVarField(VarField v){return null;}

    public Stm translateVarIndexed(VarIndexed v){return null;}

    public Stm translateVarSimple(VarSimple v){return null;}

    //Tradução de tipos

    public Stm translateTy(Ty v){return null;}

    public Stm translateTyArray(TyArray v){return null;}

    public Stm translateTyName(TyName v){return null;}

    public Stm translateTyRec(TyRec v){return null;}

    //Tradução de listas

    public Stm translateExpList(ExpList e){return null;}
    
    public Stm translateDecList(DecList d){return null;}
    
    public Stm translateFieldExpList(FieldExpList v){return null;}

    public Stm translateFieldTyList(FieldTyList v){return null;}

    private SymbolTable VarEnv,TyEnv,FunEnv;
    private Exp root;
    private ErrorMsg err;
}

class Stm{}