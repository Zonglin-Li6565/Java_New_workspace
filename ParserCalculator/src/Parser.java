import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;


public class Parser {
	ArrayList<Token> input;
	String[] defaultMethods;
	
	public TreeNode ParseforFunction(ArrayList<Token> input) throws Exception{
		this.input = input;
		return Parsing(0, input.size());
	}
	
	/**
	 * 
	 * @param start
	 * @param the index after the last element
	 * @return
	 * @throws Exception 
	 */
	public TreeNode Parsing(int start, int end) throws Exception{
		int p = 0, location = 0;
		Token temp = null;
		if((end - start) == 1 && (input.get(start).kind != Token.PARENTHESIS) &&
				(input.get(start).kind != Token.OPERATOR)){
				TreeNode child = new TreeNode(input.get(start));
				return child;
		}
		while(input.get(end - 1).kind == Token.PARENTHESIS && input.get(start).kind == Token.PARENTHESIS){
			start ++;
			end --;
		}
		for(int i = end - 1; i > -1; i--){
			switch(input.get(i).kind){
			case Token.OPERATOR: {
				if(p == 0 && (temp == null || input.get(i).operator < temp.operator - 2)){
					temp = input.get(i);
					location = i;
				}
				break;
			}
			case Token.PARENTHESIS:{
				if(input.get(i).side == 1){
					p--;
				}else if(input.get(i).side == 2){
					p++;
				}
				break;
			}
			default:{
				break;
			}
			}
		}
		TreeNode current = new TreeNode(temp);
		current.leftChild = Parsing(start, location);
		current.rightChild = Parsing(location + 1, end);
		return current;
	}
	
	public double calculate(ArrayList<Token> input) throws Exception{
		this.input = input;
		return calculateParsing(0, input.size());
	}
	
	public double calculateParsing(int start, int end) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		int p = 0, location = 0;
		Token temp = null;
		if((end - start) == 1 && (input.get(start).kind != Token.PARENTHESIS) &&
				(input.get(start).kind != Token.OPERATOR)){
				if(input.get(start).kind == Token.NUMBER){
					return input.get(start).value;
				}
				if(input.get(start).kind == Token.FUNCTION){
					if(isDefaultMethod(input.get(start).Name)){
						return defaultMethodCalculator(input.get(start).Name, input.get(start).parameters.values().iterator());
					}
					return functionCalculator(input.get(start).commands, input.get(start).parameters);
				}
		}
		while(input.get(end - 1).kind == Token.PARENTHESIS && input.get(start).kind == Token.PARENTHESIS){
			start ++;
			end --;
		}
		for(int i = end - 1; i > -1; i--){
			switch(input.get(i).kind){
			case Token.OPERATOR: {
				if(p == 0 && (temp == null || input.get(i).operator < temp.operator - 2)){
					temp = input.get(i);
					location = i;
				}
				break;
			}
			case Token.PARENTHESIS:{
				if(input.get(i).side == 1){
					p--;
				}else if(input.get(i).side == 2){
					p++;
				}
				break;
			}
			default:{
				break;
			}
			}
		}
		double left = calculateParsing(start, location);
		double right = calculateParsing(location + 1, end);
		switch (temp.operator){
		case 1 : return left + right;
		case 2 : return left - right;
		case 5 : return left * right;
		case 6 : return left / right;
		default : break;
		}
		return -5000000;
	}
	
	public boolean isDefaultMethod(String name){
		for(int i = 0; i < defaultMethods.length; i++){
			if(defaultMethods[i].equals(name)){
				return true;
			}
		}
		return false;
	}
	
	public double functionCalculator(TreeNode node, Hashtable<String, Double> parameters){
		if(node.leftChild == null && node.rightChild == null){
			switch(node.token.kind){
			case Token.NUMBER : return node.token.value;
			case Token.IDENTIFIER : return parameters.get(node.token.Name);
			case Token.FUNCTION : return functionCalculator(node.token.commands, node.token.parameters);
			}
		}
		double left = functionCalculator(node.leftChild, parameters);
		double right = functionCalculator(node.rightChild, parameters);
		switch (node.token.operator){
		case 1 : return left + right;
		case 2 : return left - right;
		case 5 : return left * right;
		case 6 : return left / right;
		default : break;
		}
		return -5000000;
	}
	
	public double defaultMethodCalculator(String name, Iterator<Double> parameters){
		switch (name){
		case "sin" : return Math.sin(parameters.next());
		}
		return -1000000;
	}
}
