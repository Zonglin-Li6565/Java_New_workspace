package Token;

/**
 * Created by Zonglin Li on 8/2/2015.
 */
public class Operation extends Token{
    /**
     *
     */
    private static final long serialVersionUID = 4854809415551753758L;
    public short operation; // 1: +, 2: -, 5: *, 6: /
    public static final short PLUS = 1, MINUS = 2, TIMES = 5, DIVIDES = 6;

    public Operation(short operation){
        this.kind = Token.OPERATOR;
        this.operation = operation;
    }

    public String toString(){
        switch(operation){
            case PLUS : return "+";
            case MINUS: return "-";
            case TIMES: return "*";
            case DIVIDES: return "/";
        }
        return "";
    }
}
