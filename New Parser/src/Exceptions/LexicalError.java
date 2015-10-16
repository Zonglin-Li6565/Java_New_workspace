package Exceptions;

/**
 * Created by Zonglin Li on 8/2/2015.
 */

public class LexicalError extends Exception{
    private static final long serialVersionUID = 1L;
    private String extra;

    public LexicalError(){
    }

    public LexicalError(String extra){
        this.extra = extra;
    }

    public String toString(){
        if(extra != null) {
            return "Lexical Error: " + extra;
        }else {
            return "Lexical Error";
        }
    }
}