package Parse;

import Exceptions.LexicalError;
import Exceptions.RuntimeError;
import Exceptions.SyntaxError;
import Token.Comma;
import Token.Identifier;
import Token.Operation;
import Token.Parenthese;
import Token.Token;
import Token.Number;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Zonglin Li on 8/2/2015.
 */
public class Parser {

    private ArrayList<Token> tokenStream;
    private FunctionManager functionManager;
    private Hashtable<String, Double> symbolTable;
    private String selfFunctionName;

    public Parser() {
    }

    public Parser(Hashtable<String, Double> symbolTable, ArrayList<Token> tokenStream,
                  String filelocation, String selfName) {
        this.symbolTable = symbolTable;
        this.tokenStream = tokenStream;
        functionManager = FunctionManager.getInstance(filelocation);
        selfFunctionName = selfName;
    }

    public Parser(FunctionManager functionManager) {
        tokenStream = new ArrayList<>();
        this.functionManager = functionManager;
    }

    public double parse(String input) throws LexicalError, SyntaxError, RuntimeError {
        tokenStream.clear();
        tokenStream = LexicalAnalysis(input.toCharArray());
        //System.out.println(tokenStream);
        double result = expression(0, tokenStream.size());
        result = Math.floor(result * 10000000000.0 + 0.5) / 10000000000.0;
        return result;
    }

    public ArrayList<Token> LexicalAnalysis(char[] input) throws LexicalError {
        ArrayList<Token> tokenStream = new ArrayList<Token>();
        int i = 0;
        char peek;
        while (i < input.length) {
            peek = input[i];
            i++;
            if (peek == ' ') {
                continue;
            }
            switch (peek) {
                case '+':
                    tokenStream.add(new Operation(Operation.PLUS));
                    continue;
                case '-':{
                    if(tokenStream.size() - 1 < 0){
                        tokenStream.add(new Number(0));
                    }else if((tokenStream.get(tokenStream.size() - 1).kind == Token.PARENTHESIS ||
                            tokenStream.get(tokenStream.size() - 1).kind == Token.COMMA ) &&
                            ((Parenthese)tokenStream.get(tokenStream.size() - 1)).side == Parenthese.LEFT){
                        tokenStream.add(new Number(0));
                    }
                    tokenStream.add(new Operation(Operation.MINUS));
                    continue;
                }
                case '*':
                    tokenStream.add(new Operation(Operation.TIMES));
                    continue;
                case '/':
                    tokenStream.add(new Operation(Operation.DIVIDES));
                    continue;
                case '(':
                    tokenStream.add(new Parenthese(Parenthese.LEFT));
                    continue;
                case ')':
                    tokenStream.add(new Parenthese(Parenthese.RIGHT));
                    continue;
                case ',':
                    tokenStream.add(new Comma());
                    continue;
            }
            if (Character.isDigit(peek)) {
                StringBuffer s = new StringBuffer();
                boolean decimal = false;
                boolean exp = false;
                i--;
                while (true) {
                    if (i >= input.length) {
                        break;
                    }
                    peek = input[i];
                    if (!(Character.isDigit(peek) || peek == '.' || peek == 'E')) {
                        break;
                    }
                    if (peek == '.') {
                        if (!decimal) {
                            decimal = true;
                        } else {
                            throw new LexicalError();
                        }
                    }
                    if(peek == 'E') {
                        if(decimal && !exp){
                            exp = true;
                        } else {
                            throw new LexicalError();
                        }
                    }
                    s.append(peek);
                    i++;
                }
                tokenStream.add(new Number(Double.parseDouble(s.toString())));
                continue;
            }
            if (Character.isLetter(peek)) {
                StringBuffer s = new StringBuffer();
                i--;
                //while(i < input.length && Character.isLetter(peek)){
                while (true) {
                    if (i >= input.length) {
                        break;
                    }
                    peek = input[i];
                    if (!Character.isLetter(peek)) {
                        break;
                    }
                    s.append(peek);
                    i++;
                }
                tokenStream.add(new Identifier(s.toString()));
                continue;
            }
            throw new LexicalError();
        }
        return tokenStream;
    }

    public double expression(int start, int end) throws SyntaxError, RuntimeError {
        if (start == end) {
            throw new SyntaxError();
        }
        if (start == (end - 1)) {
            if (tokenStream.get(start).kind == Token.NUMBER) {
                return ((Number) tokenStream.get(start)).value;
            }
            if (tokenStream.get(start).kind == Token.IDENTIFIER) {
                if (((Identifier) tokenStream.get(start)).name.equals("E")) {
                    return Math.E;
                }
                if (((Identifier) tokenStream.get(start)).name.equals("PI")) {
                    return Math.PI;
                }
                if (symbolTable == null) {
                    throw new RuntimeError("No such variable: " +
                            ((Identifier) tokenStream.get(start)).name);
                }
                Double value = symbolTable.get(((Identifier) tokenStream.get(start)).name);
                if (value != null) {
                    return value;
                } else {
                    throw new RuntimeError("No such variable: " +
                            ((Identifier) tokenStream.get(start)).name);
                }
            }
        }
        int p = 0;
        int rootoperation = -1;
        for (int i = end - 1; i >= start; i--) {
            if (tokenStream.get(i).kind == Token.PARENTHESIS) {
                switch (((Parenthese) tokenStream.get(i)).side) {
                    case Parenthese.RIGHT:
                        p++;
                        break;
                    case Parenthese.LEFT:
                        p--;
                        break;
                    default:
                        throw new RuntimeError("Parser.expression->No.1");
                }
            }
            if (p == 0 && tokenStream.get(i).kind == Token.OPERATOR) {
                if (rootoperation == -1) {
                    rootoperation = i;
                } else if (p == 0 && ((Operation) tokenStream.get(i)).operation <
                        ((Operation) tokenStream.get(rootoperation)).operation - 2) {
                    rootoperation = i;
                }
            }
        }
        if (rootoperation != -1) {
            double left = expression(start, rootoperation);
            double right = expression(rootoperation + 1, end);
            switch (((Operation) tokenStream.get(rootoperation)).operation) {
                case Operation.PLUS:
                    return left + right;
                case Operation.MINUS:
                    return left - right;
                case Operation.TIMES:
                    return left * right;
                case Operation.DIVIDES:
                    return left / right;
                default:
                    throw new RuntimeError("Parser.expression->No.2");
            }
        } else {
            switch (tokenStream.get(start).kind) {
                case Token.PARENTHESIS:
                    return expression(start + 1, end - 1);
                case Token.IDENTIFIER: {   // the pattern could be a function
                    if (tokenStream.get(start + 1).kind == Token.PARENTHESIS &&
                            tokenStream.get(end - 1).kind == Token.PARENTHESIS) {
                        if(selfFunctionName != null &&
                                ((Identifier)tokenStream.get(start)).name.equals(selfFunctionName)){
                            throw new RuntimeError("Self recursion is not permitted");
                        }
                        int p_1 = 0;
                        ArrayList<Double> parameters = new ArrayList<Double>();
                        for (int j = start + 2, k = start + 2; j < end; j++) {
                            if (tokenStream.get(j).kind == Token.PARENTHESIS) {
                                switch (((Parenthese) tokenStream.get(j)).side) {
                                    case Parenthese.RIGHT:
                                        p_1--;
                                        break;
                                    case Parenthese.LEFT:
                                        p_1++;
                                        break;
                                    default:
                                        throw new RuntimeError("Parser.expression->No.1");
                                }
                            }
                            System.out.println("p_1 = " + p_1);
                            if ((p_1 == 0 && tokenStream.get(j).kind == Token.COMMA) || j == end - 1) {
                                System.out.println("k = " + k + " j = " + j);
                                parameters.add(expression(k, j));
                                System.out.println("parameter value = " + parameters.get(parameters.size() - 1));
                                k = j + 1;
                            }
                        }
                        return functionManager.functionExecutor(((Identifier) tokenStream.get(start)).name,
                                parameters);
                    }
                }
                default:
                    throw new SyntaxError();
            }
        }
    }
}


