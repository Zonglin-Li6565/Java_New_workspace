package Parse;

import Exceptions.LexicalError;
import Exceptions.RuntimeError;
import Exceptions.SyntaxError;
import Token.Identifier;
import Token.Token;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Zonglin Li on 8/2/2015.
 */
public class FunctionManager {

    public static FunctionManager instance;
    public Hashtable<String, Function> functions;
    public final String[] defaultFunctions = {"sin", "cos", "tan", "max", "min", "arcsin", "arccos", "arctan",
            "squareRoot", "log", "ln", "random", "exp", "floor", "absolute"};
    public final String saveLocation;

    private FunctionManager(String fileLocation) {
        saveLocation = fileLocation;
        functions = loadFunctions();
    }

    public static FunctionManager getInstance(String fileLocation) {
        if (instance == null) {
            instance = new FunctionManager(fileLocation);
        }
        return instance;
    }

    public double functionExecutor(String functionName, ArrayList<Double> parameters) throws RuntimeError, SyntaxError {
        if (isDefaultFunction(functionName)) {
            return defaultFunctions(functionName, parameters);
        }
        Hashtable<String, Double> symbolTable = new Hashtable<>();
        Function function = functions.get(functionName);
        if (function == null) {
            throw new RuntimeError("No such function: " + functionName);
        }
        if (parameters.size() != function.parameters.size()) {
            throw new RuntimeError("Parameter mismatch at function: " + functionName);
        }
        for (int i = 0; i < parameters.size(); i++) {
            symbolTable.put(function.parameters.get(i), parameters.get(i));
        }
        Parser parser = new Parser(symbolTable, function.tokenStream, saveLocation, functionName);
        return parser.expression(0, function.tokenStream.size());
    }

    public void addFunction(char[] signature, char[] expression, String notes)
            throws RuntimeError, LexicalError, SyntaxError {
        if(signature.length == 0){
            throw new RuntimeError("The function name is empty");
        }else if(expression.length == 0){
            throw new RuntimeError("The expression is empty");
        }
        System.out.println("signature: " + signature.toString() + "expression: " + expression.toString());
        Function newFunction = null;
        try{
            newFunction = parseSignature(signature);
        }catch(LexicalError le){
            throw new LexicalError("The Function Name");
        }catch(SyntaxError se){
            throw new SyntaxError("The Function Name");
        }
        newFunction.description = notes;
        System.out.println("Funcion name: " + newFunction.name + " parameters: " + newFunction.parameters);
        try{
            newFunction.tokenStream = new Parser().LexicalAnalysis(expression);
        }catch(LexicalError le){
            throw new LexicalError("The Function Expression");
        }
        functions.put(newFunction.name, newFunction);
        saveFunctions();
        System.out.println("added");
    }

    public Function parseSignature(char[] signature) throws SyntaxError, LexicalError {
        Function function = new Function();
        Parser parser = new Parser();
        ArrayList<Token> tokenStream = parser.LexicalAnalysis(signature);
        if(tokenStream.size() < 4){
            throw new SyntaxError();
        }
        if (tokenStream.get(0).kind == Token.IDENTIFIER && tokenStream.get(1).kind == Token.PARENTHESIS
                && tokenStream.get(tokenStream.size() - 1).kind == Token.PARENTHESIS
                && tokenStream.get(2).kind == Token.IDENTIFIER) {
            function.name = ((Identifier) tokenStream.get(0)).name;
            function.parameters.add(((Identifier) tokenStream.get(2)).name);
        } else {
            throw new SyntaxError();
        }
        for (int i = 3; i < tokenStream.size() - 1; i += 2) {
            System.out.println(tokenStream.get(i));
            if (tokenStream.get(i).kind == Token.COMMA &&
                    tokenStream.get(i + 1).kind == Token.IDENTIFIER) {
                function.parameters.add(((Identifier) tokenStream.get(i + 1)).name);
            } else  {
                throw new SyntaxError();
            }
        }
        return function;
    }

    public boolean deleteFunction(String name) {
        if(isDefaultFunction(name)){
            return false;
        }
        functions.remove(name);
        File f = new File(saveLocation);
        if (f.exists()) {
            f.delete();
        }
        saveFunctions();
        return true;
    }

    public void saveFunctions() {
        File f = new File(saveLocation);
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
            out.writeObject(functions);
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Hashtable<String, Function> loadFunctions() {
        File f = new File(saveLocation);
        if (!f.exists()) {
            return new Hashtable<String, Function>();
        }
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
            @SuppressWarnings("unchecked")
            Hashtable<String, Function> h = (Hashtable<String, Function>) in.readObject();
            in.close();
            return h;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public Function getFunction(String name){
        return functions.get(name);
    }

    public String getFunctionSignature(String name){
        if(isDefaultFunction(name)){
            switch (name) {
                case "sin":
                    return "sin(x)";
                case "cos":
                    return "cos(x)";
                case "tan":
                    return "tan(x)";
                case "max":
                    return "max(x, y)";
                case "min":
                    return "min(x, y)";
                case "neg": return "neg(x)\nThe negative value of x";
                case "arcsin":
                    return "arcsin(x)";
                case "arccos":
                    return "arccos(x)";
                case "arctan":
                    return "arctan(x)";
                case "squareRoot":
                    return "squareRoot(x)";
                case "log":
                    return "log(base, x)";
                case "ln":
                    return "ln(x)";
                case "random":
                    return "random(low_bound, up_bound)";
                case "exp": return "exp(base, power)";
                case "floor" : return "floor(x)";
                case "absolute" : return "absolute(x)";
            }
        }else{
            Function f = functions.get(name);
            return f.getSignature();
        }
        return null;
    }

    public String[] getFunctionList(){
        String[] list;
        if(functions.size() == 0){
            list = new String[defaultFunctions.length + functions.size() + 6];
            list[list.length - 1] = "Nothing now, add some functions. ^_^";
        }else{
            list = new String[defaultFunctions.length + functions.size() + 5];
        }
        //System.out.println("list.size = " + list.length + " functions.size = " + functions.size());
        list[0] = "======Build in functions======";
        int i = 1;
        for(; i < defaultFunctions.length + 1; i++){
            list[i] = getFunctionSignature(defaultFunctions[i - 1]);
        }
        list[i] = "======Build in constants======";
        i++;
        list[i] = "PI";
        i++;
        list[i] = "E";
        i++;
        list[i] = "=======Your functions=======";
        i++;
        Object[] mfunctions = functions.values().toArray();
        int start = i;
        if(functions.size() == 0){
            return list;
        }
        for(; i < list.length; i++){
            list[i] = mfunctions[i - start].toString();
        }
        return list;
    }

    public boolean isDefaultFunction(String name) {
        for (String s : defaultFunctions) {
            if (s.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public double defaultFunctions(String name, ArrayList<Double> parameters) throws RuntimeError {
        try {
            switch (name) {
                case "sin":
                    return Math.sin(parameters.get(0));
                case "cos":
                    return Math.cos(parameters.get(0));
                case "tan":
                    return Math.tan(parameters.get(0));
                case "max":
                    return Math.max(parameters.get(0), parameters.get(1));
                case "min":
                    return Math.min(parameters.get(0), parameters.get(1));
                case "neg":
                    return 0 - parameters.get(0);
                case "arcsin":
                    return Math.asin(parameters.get(0));
                case "arccos":
                    return Math.acos(parameters.get(0));
                case "arctan":
                    return Math.atan(parameters.get(0));
                case "squareRoot":
                    return Math.sqrt(parameters.get(0));
                case "log":
                    return (Math.log(parameters.get(0)) / Math.log(parameters.get(1)));
                case "ln":
                    return (Math.log(parameters.get(0)) / Math.log(Math.E));
                case "random":
                    return (Math.random() * (parameters.get(1) - parameters.get(0)) + parameters.get(0));
                case "exp" : {
                    double x = 1;
                    for(int i = 0; i < parameters.get(1); i++){
                        x *= parameters.get(0);
                    }
                    return x;
                }
                case "floor" :
                    return Math.floor(parameters.get(0));
                case "absolute" : return Math.abs(parameters.get(0));
            }
        } catch (Exception x) {
            throw new RuntimeError("Parameter mismatch at function: " + name);
        }
        return 0.0;
    }
}

