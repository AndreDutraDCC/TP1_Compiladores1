package AbsSyn;

import Types.*;

//Abstração do nó na árvore de sintaxe abstrata

public abstract class AbsNode {
    public int pos;
    public String classname;

    public Type type;

    public AbsNode(){
        type = null;
    }

    public void print(String pref, Boolean last){
        String sep;
        String kidsep;
        if(last){
            sep = "\u2517\u2501";
            kidsep = "  ";
        }
        else{
            sep = "\u2523\u2501";
            kidsep = "\u2503 ";
        }
        System.out.println(pref+sep+classname);
        printkids(pref+kidsep);
    }

    public void print(String pref){
        print(pref,true);
    }


    public void print(String pref, String str, Boolean last){
        String sep;
        if(last){
            sep = "\u2517\u2501";
        }
        else{
            sep = "\u2523\u2501";
        }
        System.out.println(pref+sep+"\""+str+"\"");
    }

    public void print(String pref, String str){
        print(pref,str,false);
    }

    public void print(String pref, int i){
        String sep = "\u2517\u2501";
        System.out.println(pref+sep+String.valueOf(i));
    }

    public void print(String pref, AbsNode n, Boolean last){
        if(n!=null){
            n.print(pref,last);
        }
    }

    public void print(String pref, AbsNode n){
        print(pref,n,false);
    }

    public abstract void printkids(String pref);

    
}
