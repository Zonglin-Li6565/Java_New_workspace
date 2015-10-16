package Token;

/**
 * Created by Zonglin Li on 8/2/2015.
 */
public class Identifier extends Token{
    /**
     *
     */
    private static final long serialVersionUID = -3125222773871339058L;
    public double value;
    public String name;

    public Identifier(String name){
        this.kind = Token.IDENTIFIER;
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
