import java.util.ArrayList;
import java.util.Hashtable;


public class Token {
	static final int NUMBER = 1, FUNCTION = 2, OPERATOR = 3, IDENTIFIER = 4, PARENTHESIS = 5;
	int kind;
	/**Operator*/
	int operator; // + : 1; - : 2; * : 5; / : 6;
	/**Number*/
	double value;
	/**Function*/
	TreeNode commands;
	Hashtable<String, Double> parameters;
	/**Identifier and Function*/
	String Name;
	/**Parenthesis*/
	int side; // 1: left; 2: right
	public Token(int kind){
		this.kind = kind;
	}
	
	public Token(int kind, int operator, ArrayList<Object> something){
		this.kind = kind;
		this.operator = operator;
	}
	
	public Token(int kind, int value){
		this.kind = kind;
		this.value = value;
	}
	
	public Token(int kind, TreeNode commands, int num_Parameters, String Name){
		this.kind = kind;
		this.commands = commands;
		this.Name = Name;
	}
	
	public Token(int kind, String Name){
		this.Name = Name;
	}
}
