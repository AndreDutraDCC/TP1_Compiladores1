package ErrorHandle;

import java.util.ArrayList;

public class ErrorMsg {
    public ErrorMsg(String filename){
        filename_ = filename;

        last_pos_ = 0;
        poslist_ = new ArrayList<Integer>();
        poslist_.add(last_pos_);

        

        haserrors_ = false;
    }

    public void newline(int endpos){
        last_pos_ = endpos;
        poslist_.add(endpos);
    }

    public void error(int pos, String message, boolean raise) throws CompilingException{
        int linha = poslist_.size();
        String errmsg;

        haserrors_ = true;

        while(pos < poslist_.get(linha-1)){
            linha--;
        }

        errmsg = "Erro na compilação do arquivo "+filename_+":"+"\n"+
        " linha "+String.valueOf(linha)+ ", caractere "+String.valueOf(pos - poslist_.get(linha-1))+":\n\t"+message+"\n";
        if(raise){
            throw new CompilingException(errmsg);
        }
        else{
            System.out.println(errmsg);
        }
        
    }

    //Default don't throw
    public void error(int pos, String message){
        error(pos,message,false);
    }

    //Default use last pos
    public void error(String message, boolean raise){
        error(last_pos_,message,raise);
    }

    //Default use last pos and don't throw
    public void error(String message){
        error(message,false);
    }

    public boolean has_errors(){
        return haserrors_;
    }
    

    private String filename_;
    private boolean haserrors_;
    private ArrayList<Integer> poslist_;
    private int last_pos_;
};

class CompilingException extends RuntimeException {
    public CompilingException(String message) {
        super(message);
    }
}