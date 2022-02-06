package ErrorHandle;

import java.util.ArrayList;

public class ErrorMsg {
    public ErrorMsg(String filename){
        filename_ = filename;

        poslist_ = new ArrayList<Integer>();
        poslist_.add(0);

        haserrors_ = false;
    }

    public void newline(int endpos){
        poslist_.add(endpos);
    }

    public void error(int pos, String message){
        haserrors_ = true;
        int linha = poslist_.size();
        while(pos < poslist_.get(linha-1)){
            linha--;
        }
        System.out.println("Erro na compilação do arquivo "+filename_+":");
        System.out.println("linha "+String.valueOf(linha)+ ", caractere "+String.valueOf(pos - poslist_.get(linha-1))+":\t"+message);
    }

    public boolean has_errors(){
        return haserrors_;
    }

    private String filename_;
    private boolean haserrors_;
    private ArrayList<Integer> poslist_;
};