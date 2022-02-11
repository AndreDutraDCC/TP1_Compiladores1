package Semant;

import java.util.ArrayList;
import java.util.List;

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

        TyEnv.installSymbol(new TypeSymbol("int", new INT(), 4)); //int
        TyEnv.installSymbol(new TypeSymbol("string", new STRING(), 4)); //string
        
        //FunEnv.installSymbol(new FunctionSymbol("malloc",new VOID(), -1, 0, new ArrayList<Type>(List.of(new INT())))); //malloc
        //FunEnv.installSymbol(new FunctionSymbol("initarray",new VOID(), -1, 0, new ArrayList<Type>(List.of(new INT(), new STRING())))); //initarray
        FunEnv.installSymbol(new FunctionSymbol("print",new VOID(), -1, 0, new ArrayList<Type>(List.of(new STRING())))); //print
        FunEnv.installSymbol(new FunctionSymbol("getchar",new STRING(), -1, 0, new ArrayList<Type>(List.of()))); //getchar
        FunEnv.installSymbol(new FunctionSymbol("ord",new INT(), -1, 0, new ArrayList<Type>(List.of(new STRING())))); //ord
        FunEnv.installSymbol(new FunctionSymbol("chr",new STRING(), -1, 0, new ArrayList<Type>(List.of(new INT())))); //chr
        FunEnv.installSymbol(new FunctionSymbol("size",new INT(), -1, 0, new ArrayList<Type>(List.of(new STRING())))); //size
        FunEnv.installSymbol(new FunctionSymbol("substring",new STRING(), -1, 0, new ArrayList<Type>(List.of(new STRING(), new INT(), new INT())))); //substring
        FunEnv.installSymbol(new FunctionSymbol("concat",new STRING(), -1, 0, new ArrayList<Type>(List.of(new STRING(),new STRING())))); //concat
        FunEnv.installSymbol(new FunctionSymbol("exit",new VOID(), -1, 0, new ArrayList<Type>(List.of(new INT())))); //exit
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

    //var id [: type_id] := exp
    private Stm translateDecVar(DecVar d){
        Stm content_code = translateExp(d.var_value);
        Type var_ty;
        int temp;
        
        if(content_code == null){//Propagação de erro na tradução da expressão
            return null;
        }

        if(VarEnv.getSymbol(d.var_name) != null){
            err.error(d.pos,"Erro Semântico: Variável de mesmo nome que \""+d.var_name+"\" já foi declarada.");
            return null;
        }

        var_ty = d.var_value.type;

        if(d.var_type != null){
            if(TyEnv.getSymbol(d.var_type) == null){
                err.error(d.pos,"Erro Semântico: tipo definido para a variável \""+d.var_name+"\" não foi declarado.");
                return null;
            }
            var_ty = TyEnv.getSymbol(d.var_type).type;

            if(!var_ty.convertsTo(d.var_value.type)){
                err.error(d.pos,"Erro Semântico: tipos incompatíveis na declaração da variável \""+d.var_name+"\".");
                return null;
            }
        }

        temp = 0;
        VarEnv.installSymbol(new VarSymbol(d.var_name, var_ty, temp, d.var_value.mem_size));

        d.type = new VOID();
        d.mem_size = 0;
        return new MOVE(new TEMP(temp),content_code);
    }

    //type id = ty
    private Stm translateDecType(DecType d){
        NAME res_type;
        TyRec body;
        
        if(translateTy(d.type_body) == null){
            return null;
        }

        if(TyEnv.getSymbol(d.type_name) != null){
            err.error(d.pos,"Erro Semântico: Tipo de mesmo nome já foi declarado.");
            return null;
        }

        res_type = new NAME(d.type_name);
        res_type.bind(d.type_body.type);

        if(res_type.hasLoop()){
            err.error(d.pos,"Erro Semântico: Declaração de tipo circular.");
        }

        if(d.type_body instanceof TyRec){
            body = (TyRec) d.type_body;
            TyEnv.installSymbol(new TypeSymbol(d.type_name, res_type, d.type_body.mem_size,body.offsets));
        }
        else{
            TyEnv.installSymbol(new TypeSymbol(d.type_name, res_type, d.type_body.mem_size));
        }

        d.type = new VOID();
        d.mem_size = 0;
        return new EXP(new CONST(0));
    }

    //function id(params) [: type_id] := exp
    private Stm translateDecFunc(DecFunc d){
        FieldTyList params = d.param_types;
        int temp;
        ArrayList<Type> args = new ArrayList<Type>();

        TypeSymbol ptype_s;

        if(FunEnv.getSymbol(d.func_name) != null){
            err.error(d.pos,"Erro Semântico: Função de mesmo nome já foi declarada.");
        }
        VarEnv.enterBlock();
        

        while(params != null){
            ptype_s = (TypeSymbol) TyEnv.getSymbol(params.field_type);

            if(ptype_s == null){
                err.error(d.pos,"Erro Semântico: tipo do parâmetro não declarado.");
                return null;
            }
            
            temp = 0;

            VarEnv.installSymbol(new VarSymbol(params.field_id, ptype_s.type,temp,ptype_s.size));

            args.add(ptype_s.type);

            params = params.tail;
        }

        Stm body_code = translateExp(d.body);

        VarEnv.exitBlock();

        if(body_code == null){
            return null;
        }

        Type ret_type = new VOID();
        int label;
        
        if(d.return_type != null){
            ptype_s = (TypeSymbol) TyEnv.getSymbol(d.return_type);
            if(ptype_s == null){
                err.error(d.pos,"Erro Semântico: Tipo inválido de retorno da função \""+d.func_name+"\".");
                return null;
            }

            ret_type = ptype_s.type;
            if(!ret_type.convertsTo(d.body.type)){
                err.error(d.pos,"Erro Semântico: Tipo da função \""+d.func_name+"\" incompatível com sua declaração.");
                return null;
            }

            body_code = new MOVE(new TEMP(-2),body_code);
        }
        //TODO associar código da expressão ao label gerado
        label = 0;
         
        d.type = new VOID();
        d.mem_size = 0;
        FunEnv.installSymbol(new FunctionSymbol(d.func_name, ret_type, label,  -1, args));
        return new EXP(new CONST(0));
    }

    
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
        //Stm else_code = translateExp(else_);

        if(cond_code == null || then_code == null){
            return null;
        }
        if(!cond_.type.convertsTo(new INT())){
            err.error(cond_.pos, "Erro Semântico: Tipo inválido, inteiro esperado.");
        }
        if(else_ != null){
            if(!then_.type.convertsTo(else_.type)){
                err.error(e.pos, "Erro Semântico: As cláusulas then e else do if devem ser de mesmo tipo.");
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

    private Stm translateTy(Ty v){
        if(v instanceof TyArray){
            return translateTyArray((TyArray) v);
        }
        if(v instanceof TyName){
            return translateTyName((TyName) v);
        }
        if(v instanceof TyRec){
            return translateTyRec((TyRec) v);
        }
        return null;
    }

    //array of type_id
    private Stm translateTyArray(TyArray v){
        TypeSymbol s = (TypeSymbol) TyEnv.getSymbol(v.elem_type);
        if(s == null){
            err.error(v.pos,"Erro Semântico: Tipo \""+v.elem_type+"\" não definido.");
            return null;
        }
        v.type = new ARRAY(s.type);
        v.mem_size = s.size;
        return new EXP(new CONST(0));
    }

    //type-id
    private Stm translateTyName(TyName v){
        TypeSymbol s = (TypeSymbol) TyEnv.getSymbol(v.ty_name);
        if(s == null){
            err.error(v.pos,"Erro Semântico: Tipo \""+v.ty_name+"\" não definido.");
            return null;
        }
        v.type = s.type;
        v.mem_size = s.size;
        return new EXP(new CONST(0));
    }

    //{id:type_id,id:type_id,...}
    private Stm translateTyRec(TyRec v){
        ArrayList<Integer> offsets = new ArrayList<Integer>();
        FieldTyList fields = v.field_types;
        TypeSymbol s;

        RECORD aux = null;
        int size = 0;

        if(v.field_types == null){
            err.error(v.pos,"Erro Semântico: Registro vazio.");
            return null;
        }

        while(fields != null){
            s = (TypeSymbol) TyEnv.getSymbol(fields.field_type);

            if(s == null){
                err.error(v.pos,"Erro Semântico: Tipo não definido.");
                return null;
            }

            aux = new RECORD(s.name, s.type, aux);

            offsets.add(size);

            size+=s.size;            
        }

        v.type = aux;
        v.mem_size = size;
        v.offsets = offsets;

        return new EXP(new CONST(0));
    }

    //Tradução de listas

    private Stm translateExpList(ExpList e){return null;}
    
    private Stm translateDecList(DecList d){return null;}
    
    private Stm translateFieldExpList(FieldExpList v){return null;}

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
