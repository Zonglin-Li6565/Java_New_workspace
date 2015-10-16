import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class Main {
	
	static Token[] tokenStream;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Token.initialize();
		//System.out.println(Token.keywords);
		Scanner sca = new Scanner(System.in);
		while(sca.hasNextLine()){
			String line = sca.nextLine();
			boolean skip = false;
			try{
				lexcalAnalysis(line);
				//System.out.println("array length = " + tokenStream.length);
				Statements(0, tokenStream.length);
			}catch(Exception e){
				System.out.println("NO I WON'T");
				//e.printStackTrace(System.out);
				skip = true;
			}
			if(!skip)
				System.out.println("YES I WILL");
		}
		sca.close();
	}
	
	public static void lexcalAnalysis(String input) throws Exception{
		ArrayList<Token> tokens = new ArrayList<Token>();
		char charArray[] = input.toCharArray();
		int length = charArray.length, i = 0, j = 0;
		boolean toAdd = false;
		while(i < length){
			char c = charArray[i];
			i++;
			if(c != ' ' && !toAdd) {toAdd = true; j = i - 1;}
			if(c == ' ' && toAdd){
				String s = input.substring(j, i - 1);
				while(s.endsWith("s")){
					s = s.substring(0, s.length() - 1);
				}
				//System.out.println("get s: " + s);
				Token t = Token.keywords.get(s);
				if(t == null){
					throw new Exception("No, I don't know --> LexcalAnalysis(1)");
				}
				tokens.add(t);
				toAdd = false;
				j = i;
			}
			if(i == length && toAdd){
				String s = input.substring(j, i);
				while(s.endsWith("s")){
					s = s.substring(0, s.length() - 1);
				}
				//System.out.println("get s: " + s + " contains " + Token.keywords.contains(s));
				Token t = Token.keywords.get(s);
				if(t == null){
					throw new Exception("No, I don't know --> LexcalAnalysis(2)");
				}
				tokens.add(t);
				toAdd = false;
				break;
			}
		}
		tokenStream = new Token[tokens.size()];
		tokens.toArray(tokenStream);
	}
	
	public static void Statements(int start, int end) throws Exception{
		int i = end - 1;
		for(; i >= start; i--){
			if(tokenStream[i].id == Token.COMMA) break;
		}
		if(i == start - 1){
			Action(start, end);
		}else{
			Statements(start, i);
			Action(i + 1, end);
		}
		
	}
	
	public static void Action(int start, int end) throws Exception{
		int i = end - 1;
		for(; i >= start; i--){
			if(tokenStream[i].id == Token.VERB) break;
		}
		if(i == start - 1){
			throw new Exception("No, I don't know --> Action");
		}else{
			ActiveList(start, i);
			ActiveList(i + 1, end);
		}
	}
	
	public static void ActiveList(int start, int end) throws Exception{
		int i = end - 1;
		for(; i >= start; i--){
			if(tokenStream[i].id == Token.AND) break;
		}
		if(i == start - 1){
			Actor(start, end);
		}else{
			ActiveList(start, i);
			Actor(i + 1, end);
		}
	}
	
	public static void Actor(int start, int end) throws Exception{
		if((end - start) >= 3){
			throw new Exception("No, I don't know --> Actor(1)");
		}
		if(end - start == 1){
			if(!(tokenStream[start].id == Token.NOUN)) throw new Exception("No, I don't know --> Actor(2)");
			return;
		}
		if(end - start == 2){
			if(!(tokenStream[start].id == Token.ARTICLE && tokenStream[start + 1].id == Token.NOUN))
				throw new Exception("No, I don't know --> Actor(3)");
			return;
		}
		throw new Exception("No, I don't know --> Actor(4)");
	}

}

class Token{
	public static Hashtable<String, Token> keywords;
	public static final int ARTICLE = 0, NOUN = 1, VERB = 3, AND = 4, COMMA = 5;
	public int id;
	
	public Token(int id){
		this.id = id;
	}
	
	public static void initialize(){
		keywords = new Hashtable<String, Token>();
		keywords.put("a", new Token(0));
		keywords.put("the", new Token(0));
		keywords.put("tom", new Token(1));
		keywords.put("jerry", new Token(1));
		keywords.put("goofy", new Token(1));
		keywords.put("mickey", new Token(1));
		keywords.put("jimmy", new Token(1));
		keywords.put("dog", new Token(1));
		keywords.put("cat", new Token(1));
		keywords.put("mouse", new Token(1));
		keywords.put("hate", new Token(3));
		keywords.put("love", new Token(3));
		keywords.put("know", new Token(3));
		keywords.put("like", new Token(3));
		keywords.put("and", new Token(4));
		keywords.put(",", new Token(5));
	}
}