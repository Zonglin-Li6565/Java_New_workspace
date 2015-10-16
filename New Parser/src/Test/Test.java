package Test;

import java.util.Scanner;

import Exceptions.LexicalError;
import Exceptions.RuntimeError;
import Exceptions.SyntaxError;
import Parse.FunctionManager;
import Parse.Parser;

public class Test {
	static Scanner sca;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		sca = new Scanner(System.in);
		FunctionManager functionManager = FunctionManager.getInstance("A:\\Test\\functions.pca");
		Parser parser = new Parser(functionManager);
		String s;
		do{
			s = sca.nextLine();
			if(s.equals("add function")){
				String signature = sca.nextLine();
				String expression = sca.nextLine();
				String description = sca.nextLine();
				try {
					functionManager.addFunction(signature.toCharArray(), expression.toCharArray(), description);
				} catch (LexicalError e) {
					// TODO Auto-generated catch block
					System.out.println(e);
					//e.printStackTrace();
				} catch (SyntaxError e) {
					// TODO Auto-generated catch block
					System.out.println(e);
					//e.printStackTrace();
				} catch (RuntimeError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
				
			}
			if(s.equals("delete function")){
				String signature = sca.nextLine();
				functionManager.deleteFunction(signature);
				continue;
				
			}
			try {
				System.out.println("= " + parser.parse(s));
			} catch (LexicalError e) {
				// TODO Auto-generated catch block
				System.out.println(e);
				//e.printStackTrace();
			} catch (SyntaxError e) {
				// TODO Auto-generated catch block
				System.out.println(e);
				//e.printStackTrace();
			} catch (RuntimeError e) {
				// TODO Auto-generated catch block
				System.out.println(e);
				//e.printStackTrace();
			}
		}while(!s.equals("end"));
	}

}
