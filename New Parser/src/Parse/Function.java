package Parse;

import Token.Token;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Zonglin Li on 8/2/2015.
 */
public class Function implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4210116164237013026L;
    public ArrayList<Token> tokenStream;
    public ArrayList<String> parameters;
    public String name;
    public String description;

    public Function() {
        parameters = new ArrayList<String>();
    }

    public String getSignature(){
        String display = "";
        display += name;
        display += "(";
        for(int i = 0; i < parameters.size(); i++){
            display += " " + parameters.get(i);
            if(i == parameters.size() - 1){
                display += ")";
            }else{
                display += ",";
            }
        }
        return display;
    }

    public String getExpression(){
        String display = "";
        for(int j = 0; j < tokenStream.size(); j++){
            display += tokenStream.get(j).toString();
        }
        return display;
    }

    public String toString(){
        String display = "";
        display += getSignature();
        display += " = ";
        display += getExpression();
        display += "\n";
        display += description;
        return display;
    }
}