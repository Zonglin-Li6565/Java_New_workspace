package Token;

/**
 * Created by Zonglin Li on 8/2/2015.
 */
public class Comma extends Token{

    /**
     *
     */
    private static final long serialVersionUID = -1084028501409242172L;

    public Comma(){
        this.kind = Token.COMMA;
    }

    public String toString(){
        return ",";
    }
}
