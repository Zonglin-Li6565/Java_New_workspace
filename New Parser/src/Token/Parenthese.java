package Token;

/**
 * Created by Zonglin Li on 8/2/2015.
 */
public class Parenthese extends Token{
    /**
     *
     */
    private static final long serialVersionUID = -3507157210845273036L;
    public short side; // 1: left, 2: right
    public static final short LEFT = 1, RIGHT = 2;

    public Parenthese(short side){
        this.kind = Token.PARENTHESIS;
        this.side = side;
    }

    public String toString(){
        if(side == 1){
            return "(";
        }else {
            return ")";
        }
    }
}
