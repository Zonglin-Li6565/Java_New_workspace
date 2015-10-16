package Token;

import java.io.Serializable;

/**
 * Created by Zonglin Li on 8/2/2015.
 */
public abstract class Token implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 9165575069003320798L;
    public static final short NUMBER = 1, OPERATOR = 2, IDENTIFIER = 3, PARENTHESIS = 4, COMMA = 5;
    public short kind;

    public int getKind(){
        return kind;
    }
}
