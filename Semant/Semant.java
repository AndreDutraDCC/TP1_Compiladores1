package Semant;

import java.util.ArrayList;
import java.util.List;

import AbsSyn.*;
import Types.*;

import SymbolTable.*;
import SymbolTable.Symbols.*;

import Intermediate.*;
import Intermediate.Nodes.*;
import static Grammar.sym.*;

import ErrorHandle.*;

public class Semant {
    public Semant(Exp program, ErrorMsg error){
        prog = program;
        err = error;

        code_tree = new Generator();

        VarEnv = new SymbolTable();
        TyEnv = new SymbolTable();
        FunEnv = new SymbolTable();

        enterBlock();

        TyEnv.installSymbol(new TypeSymbol("int", new INT(), 4)); //int
        TyEnv.installSymbol(new TypeSymbol("string", new STRING(), 4)); //string
        
        FunEnv.installSymbol(new FunctionSymbol("print",new VOID(), "print", 0,new ArrayList<Type>(List.of(new STRING())),true)); //print
        FunEnv.installSymbol(new FunctionSymbol("getchar",new STRING(), "getchar", 4, new ArrayList<Type>(List.of()),true)); //getchar
        FunEnv.installSymbol(new FunctionSymbol("ord",new INT(), "ord", 4, new ArrayList<Type>(List.of(new STRING())),true)); //ord
        FunEnv.installSymbol(new FunctionSymbol("chr",new STRING(), "chr", 4, new ArrayList<Type>(List.of(new INT())),true)); //chr
        FunEnv.installSymbol(new FunctionSymbol("size",new INT(), "size", 4, new ArrayList<Type>(List.of(new STRING())),true)); //size
        FunEnv.installSymbol(new FunctionSymbol("substring",new STRING(), "substring", 4, new ArrayList<Type>(List.of(new STRING(), new INT(), new INT())),true)); //substring
        FunEnv.installSymbol(new FunctionSymbol("concat",new STRING(), "concat", 4, new ArrayList<Type>(List.of(new STRING(),new STRING())),true)); //concat
        FunEnv.installSymbol(new FunctionSymbol("exit",new VOID(), "exit", 0, new ArrayList<Type>(List.of(new INT())),true)); //exit
    }

    public Generator translateProgram(){
        Stm prog_code = translateExp(prog);

        if(prog == null){
            return null;
        }
        
        if(prog_code.isExp()){
            prog_code = new EXP(prog_code);
        }

        int s_id = code_tree.createProcedure("");
        code_tree.associateScope(s_id,prog_code);

        return code_tree;
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

        temp = code_tree.temporaryFromVariable(d.var_name);

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
            TyEnv.installSymbol(new TypeSymbol(d.type_name, res_type, d.type_body.mem_size,body.fields));
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

        TypeSymbol ptype_s = null;
        FunctionSymbol this_func;

        Type ret_type = new VOID();
        int size = 0;

        int s_id;

        if(FunEnv.getSymbol(d.func_name) != null){
            err.error(d.pos,"Erro Semântico: Função de mesmo nome já foi declarada.");
            return null;
        }

        if(d.return_type != null){
            ptype_s = (TypeSymbol) TyEnv.getSymbol(d.return_type);
            if(ptype_s == null){
                err.error(d.pos,"Erro Semântico: Tipo inválido de retorno da função \""+d.func_name+"\".");
                return null;
            }

            ret_type = ptype_s.type;
            size = ptype_s.size;
        }

        s_id = code_tree.createProcedure(d.func_name);
        FunEnv.installSymbol(new FunctionSymbol(d.func_name, ret_type, code_tree.labelFromScope(s_id), size, null));
        
        VarEnv.enterBlock();

        while(params != null){
            ptype_s = (TypeSymbol) TyEnv.getSymbol(params.field_type);

            if(ptype_s == null){
                err.error(d.pos,"Erro Semântico: tipo do parâmetro não declarado.");
                return null;
            }
            
            temp = code_tree.temporaryFromParam(params.field_id);

            VarEnv.installSymbol(new VarSymbol(params.field_id, ptype_s.type,temp,ptype_s.size));

            args.add(ptype_s.type);

            params = params.tail;
        }

        this_func = (FunctionSymbol) FunEnv.getSymbol(d.func_name);

        this_func.argList = args;

        Stm body_code = translateExp(d.body);

        VarEnv.exitBlock();

        if(body_code == null){
            return null;
        }
        
        if(d.return_type != null){
            if(!ret_type.convertsTo(d.body.type)){
                err.error(d.pos,"Erro Semântico: Tipo da função \""+d.func_name+"\" incompatível com sua declaração.");
                return null;
            }

            body_code = new MOVE(new TEMP(-2),body_code);
        }

        code_tree.associateScope(s_id, body_code);
         
        d.type = new VOID();
        d.mem_size = 0;
        
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
        e.mem_size = 4;

        String label = "L"+String.valueOf(code_tree.makeDataLabel(e.val));

        return new Name(label);
    }

    private Stm translateExpNil(ExpNil e){
        e.type = new NIL();
        e.mem_size = 0;

        return new CONST(0);
    }

    //TODO início das não implementadas
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
        Exp cond = e.cond;
        Exp then = e.then_body;
        Exp els = e.else_body;
        
        Stm cond_code = translateExp(cond);
        Stm then_code = translateExp(then);
        Stm else_code;

        if(cond_code == null || then_code == null){
            return null;
        }
        if(!cond.type.convertsTo(new INT())){
            err.error(cond.pos, "Erro Semântico: Tipo inválido, inteiro esperado.");
            return null;
        }
        if(els == null){
            else_code = new EXP(new CONST(0));

            if(!then.type.convertsTo(new VOID())){
                err.error(then.pos, "Erro Semântico: VOID esperado.");
                return null;
            }
    
            e.type = new VOID();
            e.mem_size = 0;            
        }
        else{
            else_code = translateExp(els);

            if(else_code == null){
                return null;
            }

            if(!then.type.convertsTo(els.type)){
                err.error(e.pos, "Erro Semântico: As cláusulas then e else do if devem ter mesmo tipo.");
                return null;
            }

            e.type = then.type;
            e.mem_size = then.mem_size;
        }


        return null;
    }


    private Stm translateExpBreak(ExpBreak e){return null;}

    private Stm translateExpFor(ExpFor e){return null;}

    private Stm translateExpWhile(ExpWhile e){return null;}

    //TODO fim das expressões não implementadas

    private Stm translateExpCall(ExpCall e){
        FunctionSymbol s = (FunctionSymbol) FunEnv.getSymbol(e.func_name);
        ExpList aux_list;
        ArrayList<Stm> code_list = new ArrayList<Stm>();
        Stm aux_code;
        
        if(s == null){
            err.error(e.pos,"Erro Semântico: Função não declarada.");
            return null;
        }
        
        if(s.is_native){
            code_list.add(new CONST(0));
        }
        else{
            code_list.add(new TEMP(-1));
        }
        
        aux_list = e.args;
        for(Type arg_t: s.argList){
            if(aux_list == null){
                err.error(e.pos,"Erro Semântico: Número inválido de argumentos na chamada da função \""+e.func_name+"\".");
                return null;
            }
            
            aux_code = translateExp(aux_list.head);

            if(aux_code == null){
                return null;
            }

            if(!arg_t.convertsTo(aux_list.head.type)){
                err.error(e.pos,"Erro Semântico: Tipos de parâmetros incompatíveis na chamada da função \""+e.func_name+"\".");
                return null;
            }

            code_list.add(aux_code);
        }
        if(aux_list != null){
            err.error(e.pos,"Erro Semântico: Número inválido de argumentos na chamada da função \""+e.func_name+"\".");
            return null;
        }

        e.type = s.type;
        e.mem_size = s.size;
        return new CALL(new Name(s.label), code_list);
    }

    private Stm translateExpAttr(ExpAttr e){
        Stm var_code = translateVar(e.var);
        Stm exp_code = translateExp(e.val);

        if(var_code == null || exp_code == null){
            return null;
        }

        if(!e.var.type.convertsTo(e.val.type)){
            err.error(e.pos,"Erro Semântico: Tipos incompatíveis na atribuição.");
        }

        e.type = new VOID();
        e.mem_size = 0;

        return new MOVE(var_code,exp_code);
    }

    private Stm translateExpArray(ExpArray e){
        Stm len_code = translateExp(e.len);
        Stm val_code = translateExp(e.val);

        if(len_code == null || val_code == null){
            return null;
        }

        if(!e.len.type.convertsTo(new INT())){
            err.error(e.len.pos,"Erro Semântico: Inteiro esperado.");
            return null;
        }

        TypeSymbol s = (TypeSymbol) TyEnv.getSymbol(e.arr_type);

        if(s == null){
            err.error(e.pos,"Erro Semântico: Tipo não declarado: \""+e.arr_type+"\".");
            return null;
        }
        ARRAY arr_ty = (ARRAY) s.type.actual();

        if(!(arr_ty instanceof ARRAY)){
            err.error(e.pos,"Erro Semântico: Tipo \""+e.arr_type+"\" não é um array.");
            return null;
        }

        Type elem_type = arr_ty.elementsType_;

        if(!elem_type.convertsTo(e.val.type)){
            err.error(e.val.pos,"Erro Semântico: tipo do valor inicial do arranjo incompatível com o tipo \""+e.arr_type+"\".");
            return null;
        }

        e.type = s.type;
        e.mem_size = s.size;

        return new CALL(new Name("initarray"),new ArrayList<Stm>(List.of(new CONST(0),len_code,val_code)));
    }

    private Stm translateExpRec(ExpRec e){
        TypeSymbol s = (TypeSymbol) TyEnv.getSymbol(e.rec_type);

        if(s == null){
            err.error(e.pos,"Erro Semântico: tipo \""+e.rec_type+"\" não foi declarado.");
            return null;
        }

        int temp = code_tree.getTemporary();
        int offset = 0;

        FieldExpList aux_list;
        Stm exp_code;
        Stm Rec_code = new MOVE(new TEMP(temp), new CALL(new Name("malloc"),new ArrayList<Stm>(List.of(new CONST(0), new CONST(s.size)))));
        
        aux_list = e.fields;

        for(VarSymbol vs: s.fields){
            if(aux_list == null){
                err.error(e.pos,"Erro Semântico: Número inválido de campos na inicialização do registro \""+e.rec_type+"\".");
                return null;
            }
            
            exp_code = translateExp(aux_list.field_value);

            if(exp_code == null){
                return null;
            }

            if(!vs.type.convertsTo(aux_list.field_value.type)){
                err.error(e.pos,"Erro Semântico: Inicialização incompatível no registro \""+e.rec_type+"\".");
                return null;
            }

            Rec_code = new SEQ(Rec_code,new MOVE(new MEM(new BINOP(PLUS,new TEMP(temp), new CONST(offset))),exp_code));
            offset += vs.size;
            aux_list = aux_list.tail;
        }

        if(aux_list != null){
            err.error(e.pos,"Erro Semântico: Número inválido de campos na inicialização do registro \""+e.rec_type+"\".");
            return null;
        }

        e.type = s.type;
        e.mem_size = s.size;

        return new ESEQ(Rec_code, new TEMP(temp));
    }

    private Stm translateExpSeq(ExpSeq e){
        ExpList aux_list = e.exps;
        ArrayList<Stm> code_list = new ArrayList<Stm>();
        Stm aux_code,res_code;
        Exp aux_exp = null;

        while(aux_list != null){
            aux_exp = aux_list.head;
            aux_code = translateExp(aux_exp);
            if(aux_code == null){
                return null;
            }

            code_list.add(aux_code);
            aux_list = aux_list.tail;
        }

        e.type = aux_exp.type;
        e.mem_size = aux_exp.mem_size;

        if(code_list.isEmpty()){
            return new EXP(new CONST(0));
        }

        res_code = code_list.get(0);

        if(code_list.size() == 1){
            return res_code;
        }

        if(res_code.isExp()){
            res_code = new EXP(res_code);
        }

        for(int i = 1; i < code_list.size() - 1; i++){
            aux_code = code_list.get(i);
            
            if(aux_code.isExp()){
                aux_code = new EXP(aux_code);
            }

            res_code = new SEQ(res_code,aux_code);
        }

        aux_code = code_list.get(code_list.size() - 1);

        if(aux_code.isExp()){
            return new ESEQ(res_code,aux_code);
        }

        return new SEQ(res_code,aux_code);
    }
    
    private Stm translateExpLet(ExpLet e){
        Stm decs_code, exp_code, aux_code;
        DecList aux_dl;

        Boolean has_decs = (e.decs != null);

        enterBlock();

        decs_code = null;

        if(has_decs){
            decs_code = translateDec(e.decs.head);
            aux_dl = e.decs.tail;

            if(decs_code == null){
                return null;
            }

            while(aux_dl != null){
                aux_code = translateDec(aux_dl.head);
                if(aux_code == null){
                    return null;
                }

                decs_code = new SEQ(decs_code, aux_code);

                aux_dl = aux_dl.tail;
            }
        }

        exp_code = translateExp(e.exps);

        if(exp_code == null){
            return null;
        }

        exitBlock();

        e.type = e.exps.type;
        e.mem_size = e.exps.mem_size;

        if(!has_decs){
            return exp_code;
        }

        if(exp_code.isExp()){
            return new ESEQ(decs_code,exp_code);
        }

        return new SEQ(decs_code,exp_code);
    }

    //Tradução de Variáveis

    private Stm translateVar(Var v){
        if(v instanceof VarField){
            return translateVarField((VarField) v);
        }
        if(v instanceof VarIndexed){
            return translateVarIndexed((VarIndexed) v);
        }
        if(v instanceof VarSimple){
            return translateVarSimple((VarSimple) v);
        }
        return null;
    }

    private Stm translateVarField(VarField v){
        Var rec_v = v.var;

        Stm rec_code = translateVar(rec_v);

        if(rec_code == null){
            return null;
        }

        if(!(rec_v.type instanceof NAME) || !(rec_v.type.actual() instanceof RECORD)){
            err.error(v.pos,"Erro Semântico: Não é um registro.");
            return null;
        }

        NAME rec_type = (NAME) rec_v.type;

        TypeSymbol s = (TypeSymbol) TyEnv.getSymbol(rec_type.name_);

        if(s == null){
            err.error(v.pos, "Erro Semântico: Tipo não declarado.");
            return null;
        }
        int offset = 0;
        int size = 0;
        Type aux_ty = null;

        for(VarSymbol vs: s.fields){
            if(vs.name == v.field_name){
                aux_ty = vs.type;
                size = vs.size;
                break;
            }
            offset+=vs.size;
        }

        if(aux_ty == null){
            err.error(v.pos, "Erro Semântico: Nenhum campo compatível com \""+v.field_name+"\" no registro.");
            return null;
        }

        v.type = aux_ty;
        v.mem_size = size;

        return new MEM(new BINOP(PLUS, rec_code, new CONST(offset)));
    }

    private Stm translateVarIndexed(VarIndexed v){
        Var arr_var = v.var;
        ARRAY arr_type;

        Stm arr_code = translateVar(arr_var);
        Stm idx_code = translateExp(v.index);

        if((arr_code == null)||(idx_code == null)){
            return null;
        }

        if(!(arr_var.type.actual() instanceof ARRAY)){
            err.error(v.pos,"Erro Semântico: Não é um arranjo.");
            return null;
        }
        
        arr_type = (ARRAY) arr_var.type.actual();

        v.type = arr_type.elementsType_;
        v.mem_size = arr_var.mem_size;
        
        return new MEM(new BINOP(PLUS,arr_code,new BINOP(TIMES,idx_code,new CONST(arr_var.mem_size))));
    }

    private Stm translateVarSimple(VarSimple v){
        VarSymbol v_symb = (VarSymbol) VarEnv.getSymbol(v.name);
        
        if(v_symb == null){
            err.error(v.pos,"Erro Semântico: Variável não declarada.");
            return null;
        }

        v.type = v_symb.type;
        v.mem_size = v_symb.size;

        return new TEMP(v_symb.tmp);
    }

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
        ArrayList<VarSymbol> fds = new ArrayList<VarSymbol>();
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

            fds.add(new VarSymbol(fields.field_id, s.type, 0, s.size));

            size+=s.size;            
        }

        v.type = aux;
        v.mem_size = size;
        v.fields = fds;

        return new EXP(new CONST(0));
    }

    //funções auxiliares

    private void enterBlock(){
        VarEnv.enterBlock();
        TyEnv.enterBlock();
        FunEnv.enterBlock();
    }

    private void exitBlock(){
        VarEnv.exitBlock();
        TyEnv.exitBlock();
        FunEnv.exitBlock();
    }

    //If cond != 0 goto lb_then
    //else goto lb_else
    public Stm ifthenelse(Stm cond, String lb_then, String lb_else){
        return new CJUMP(NEQ,cond,new CONST(0),new LABEL(lb_then),new LABEL(lb_else));
    }

    private Generator code_tree;
    private Exp prog;
    private SymbolTable VarEnv,TyEnv,FunEnv;
    private ErrorMsg err;
}
