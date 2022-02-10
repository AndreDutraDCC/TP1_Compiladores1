package Semant;

import AbsSyn.*;
import Types.*;

import SymbolTable.*;
import SymbolTable.Symbols.*;

import Intermediate.Nodes.*;
import static Grammar.sym.*;

import ErrorHandle.*;

public class Semant {
    public Semant(Exp program, ErrorMsg error){
        prog = program;
        err = error;

        VarEnv = new SymbolTable();
        TyEnv = new SymbolTable();
        FunEnv = new SymbolTable();

        //TODO: Instalar entradas na tabela de símbolos corretamente
        TyEnv.installSymbol(new Symbol()); //int
        TyEnv.installSymbol(new Symbol()); //string
        
        FunEnv.installSymbol(new Symbol()); //print
        FunEnv.installSymbol(new Symbol()); //getchar
        FunEnv.installSymbol(new Symbol()); //ord
        FunEnv.installSymbol(new Symbol()); //chr
        FunEnv.installSymbol(new Symbol()); //size
        FunEnv.installSymbol(new Symbol()); //substring
        FunEnv.installSymbol(new Symbol()); //concat
        FunEnv.installSymbol(new Symbol()); //exit
    }

    //TODO: Verificações extras do programa final
    public Stm translateProgram(){
        return translateExp(prog);
    }

    //Tradução de declarações

    private Stm translateDec(Dec d){
        if(d instanceof DecVar){
            return translateDecVar((DecVar) d);
        }
        if(d instanceof DecType){
            return translateDecType((DecType) d);
        }
        if(d instanceof DecFunc){
            return translateDecFunc((DecFunc) d);
        }
        
        return null;
    }

    private Stm translateDecVar(DecVar d){return null;}

    private Stm translateDecType(DecType d){return null;}

    private Stm translateDecFunc(DecFunc d){return null;}

    
    //Tradução de expressões

    private Stm translateExp(Exp e){
        if(e instanceof ExpInt){
            return translateExpInt((ExpInt) e);
        }
        if(e instanceof ExpString){
            return translateExpString((ExpString) e);
        }
        if(e instanceof ExpNil){
            return translateExpNil((ExpNil) e);
        }
        if(e instanceof ExpOp){
            return translateExpOp((ExpOp) e);
        }
        if(e instanceof ExpIf){
            return translateExpIf((ExpIf) e);
        }
        if(e instanceof ExpAttr){
            return translateExpAttr((ExpAttr) e);
        }
        if(e instanceof ExpArray){
            return translateExpArray((ExpArray) e);
        }
        if(e instanceof ExpRec){
            return translateExpRec((ExpRec) e);
        }
        if(e instanceof ExpBreak){
            return translateExpBreak((ExpBreak) e);
        }
        if(e instanceof ExpCall){
            return translateExpCall((ExpCall) e);
        }
        if(e instanceof ExpFor){
            return translateExpFor((ExpFor) e);
        }
        if(e instanceof ExpWhile){
            return translateExpWhile((ExpWhile) e);
        }
        if(e instanceof ExpLet){
            return translateExpLet((ExpLet) e);
        }
        if(e instanceof ExpSeq){
            return translateExpSeq((ExpSeq) e);
        }
        if(e instanceof Var){
            return translateVar((Var) e);
        }
        return null;
    }

    private Stm translateExpInt(ExpInt e){
        e.type = new INT();
        
        e.mem_size = 4;
        return new CONST(e.val);
    }

    private Stm translateExpString(ExpString e){
        e.type = new STRING();
        return null;//TODO STRING CODE
    }

    private Stm translateExpNil(ExpNil e){
        e.type = new NIL();
        return null;//TODO NIL CODE
    }

    private Stm translateExpOp(ExpOp e){
        Exp left = e.e1;
        Exp right = e.e2;

        Stm evall = translateExp(left);
        Stm evalr = translateExp(right);
        Stm res_code = null;

        if(left.type == null || right.type == null){
            return null;
        }

        switch(e.oper){
            case PLUS:
            case MINUS:
            case TIMES:
            case DIV:
                if(!(left.type.convertsTo(new INT()) && right.type.convertsTo(new INT()))){
                    err.error(e.pos,"Erro Semântico: Operandor aritmético não deve ser aplicado a operandos não inteiros.");
                    return null;
                }
                e.type = new INT();
                res_code = new BINOP(e.oper,evall,evalr);
                break;
            case EQ:
            case NEQ:
                if(left.type.convertsTo(new VOID()) || left.type.convertsTo(new VOID())){
                    err.error(e.pos,"Erro Semântico: Comparação não é definida para operandos sem valor de retorno (VOID).");
                    return null;
                }
                if(left.type.convertsTo(new NIL()) || left.type.convertsTo(new NIL())){
                    err.error(e.pos,"Erro Semântico: Comparação não é definida para operandos do tipo NIL.");
                    return null;
                }
                e.type = new INT();
                //res_code = TODO BIN OP EQ NEQ
                break;
            case LT:
            case LTE:
            case GT:
            case GTE:
                if(!(left.type.convertsTo(new INT()) && right.type.convertsTo(new INT()))){
                    err.error(e.pos,"Erro Semântico: Operador relacional não deve ser aplicado a operandos não inteiros.");
                    return null;
                }
                e.type = new INT();
                //res_code = TODO BIN OP REL
            case AND:
            case OR:
                if(!(left.type.convertsTo(new INT()) && right.type.convertsTo(new INT()))){
                    err.error(e.pos,"Erro Semântico: Operador lógico não deve ser aplicado a operandos não inteiros.");
                    return null;
                }
                e.type = new INT();
                //res_code = TODO BIN OP LOG
                
        }
        return res_code;
    }

    private Stm translateExpIf(ExpIf e){
        Exp cond_ = e.cond;
        Exp then_ = e.then_body;
        Exp else_ = e.else_body;
        
        Stm cond_code = translateExp(cond_);
        Stm then_code = translateExp(then_);
        Stm else_code = translateExp(else_);

        if(cond_code == null || then_code == null){
            return null;
        }
        if(!cond_.type.convertsTo(new INT())){
            err.error(cond_.pos, "Erro Semântico: O teste do condicional deve ser do tipo inteiro.");
        }
        if(else_ != null){
            if(!then_.type.convertsTo(else_.type)){
                err.error(e.pos, "Erro Semântico: Os tipos dos evaluandos do condicional devem ser iguais.");
            }
        }
        //If cond == 0 jump end
        //then code
        //jump end
        //LABEL end
        
        //If cond == 0 jump else
        //then code
        //jump end
        //LABEL else
        //else code
        //jump end
        //LABEL end
        return null;
    }

    private Stm translateExpAttr(ExpAttr e){return null;}

    private Stm translateExpArray(ExpArray e){return null;}

    private Stm translateExpRec(ExpRec e){return null;}

    private Stm translateExpBreak(ExpBreak e){return null;}

    private Stm translateExpCall(ExpCall e){return null;}

    private Stm translateExpFor(ExpFor e){return null;}

    private Stm translateExpWhile(ExpWhile e){return null;}

    private Stm translateExpLet(ExpLet e){return null;}

    private Stm translateExpSeq(ExpSeq e){return null;}

    //Tradução de Variáveis

    private Stm translateVar(Var v){return null;}

    private Stm translateVarField(VarField v){return null;}

    private Stm translateVarIndexed(VarIndexed v){return null;}

    private Stm translateVarSimple(VarSimple v){return null;}

    //Tradução de tipos

    private Stm translateTy(Ty v){return null;}

    private Stm translateTyArray(TyArray v){return null;}

    private Stm translateTyName(TyName v){return null;}

    private Stm translateTyRec(TyRec v){return null;}

    //Tradução de listas

    private Stm translateExpList(ExpList e){return null;}
    
    private Stm translateDecList(DecList d){return null;}
    
    private Stm translateFieldExpList(FieldExpList v){return null;}

    private Stm translateFieldTyList(FieldTyList v){return null;}

    //funções auxiliares

    //If cond != 0 goto lb_then
    //else goto lb_else
    public Stm ifthenelse(Stm cond, String lb_then, String lb_else){
        return new CJUMP(NEQ,cond,new CONST(0),new LABEL(lb_then),new LABEL(lb_else));
    }

    private Exp prog;
    private SymbolTable VarEnv,TyEnv,FunEnv;
    private ErrorMsg err;
}
