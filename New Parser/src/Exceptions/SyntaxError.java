package Exceptions;

/**
 * Created by Zonglin Li on 8/2/2015.
 */
public class SyntaxError extends Exception{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String extra;

    public SyntaxError(){}

    public SyntaxError(String extra){
        this.extra = extra;
    }

    public String toString() {
        if (extra != null) {
            return "Syntax Error: " + extra;
        }
        return "Syntax Error";
    }
}
