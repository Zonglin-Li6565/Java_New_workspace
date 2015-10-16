package Token;

/**
 * Created by Zonglin Li on 8/2/2015.
 */
public class Number extends Token{
    /**
     *
     */
    private static final long serialVersionUID = -8586934513545456170L;
    public double value;

    public Number(double value){
        this.kind = Token.NUMBER;
        this.value = value;
    }

    public String toString(){
        return value + "";
    }
}
