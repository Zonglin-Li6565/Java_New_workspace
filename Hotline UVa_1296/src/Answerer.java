import java.util.ArrayList;
import java.util.Scanner;


public class Answerer {
	static ArrayList<Person> persons = new ArrayList<Person>();
	static boolean contradict = false;
	
	public static void main(String[] args){
		Scanner sca = new Scanner(System.in);
		int times = Integer.parseInt(sca.nextLine());
		for(int i = 1; i <= times; i++){
			System.out.println("Dialogue #" + i + ":");
			parser(sca);
			if(i != times){
				System.out.println();
			}
		}
		sca.close();
	}
	
	//public static void parser(InputStream in){
	public static void parser(Scanner sca){
		initializePersons();
		contradict = false;
		while(sca.hasNext()){
			String read = sca.nextLine();
			if(read.endsWith("!")){					//terminate
				System.out.println(read);
				break;
			}
			if(contradict ){
				System.out.println(read);
				System.out.println("I am abroad." + "\n");
				continue;
			}
			ArrayList<String> words = readWords(read.toCharArray());
			if(read.endsWith(".")){							//statements
				String subject = words.get(0);
				//System.out.println("subject = " + subject);
				if(subject.equals("you")) subject = "I";
				else if(subject.equals("I")) subject = "you";
				//System.out.println("subject = " + subject);
				if(words.get(1).equals("don't") || words.get(1).equals("doesn't")){ //negative
					String object = words.size() > 3? words.get(3) : "";
					for(int i = 4; i < words.size(); i++) object += " " + words.get(i);
					Property pro = new Property(words.get(2), object, false);
					addProperty(subject, pro);
					//System.out.println("ArrayList = " + persons);
					continue;
				}else{																//positive
					String object = words.size() > 2? words.get(2) : "";
					for(int i = 3; i < words.size(); i++) object += " " + words.get(i);
					Property pro = new Property(words.get(1), object, true);
					if(subject.equals("nobody")){
						subject = "everybody";
						pro.isPositive = false;
					}
					addProperty(subject, pro);
					//System.out.println("ArrayList = " + persons);
					continue;
				}
			}else if(read.endsWith("?")){					//questions
				if(words.get(0).equals("do") || words.get(0).equals("does")){ // Question 1
					String subject = words.get(1);
					if(subject.equals("you")) subject = "I";
					else if(subject. equals("I")) subject = "you";
					String object = words.size() > 3? words.get(3) : "";
					for(int i = 4; i < words.size(); i++) object += " " + words.get(i);
					String s = answerQ1(subject, words.get(2), object);
					//System.out.println(read);
					System.out.println(s + "\n");
					continue;
				}
				if(words.get(0).equals("who")){								  // Question 2
					String object = words.size() > 2? words.get(2) : "";
					for(int i = 3; i < words.size(); i++) object += " " + words.get(i);
					String s = answerQ2(words.get(1), object);
					//System.out.println(read);
					System.out.println(s + "\n");
					continue;
				}
				if(words.get(0).equals("what")){							 // Question 3
					String subject = words.get(2);
					if(subject.equals("I")) subject = "you";
					else if(subject.equals("you")) subject = "I";
					String s = answerQ3(subject);
					//System.out.println(read);
					System.out.println(s + "\n");
				}
			}
		}
		//sca.close();
	}
	
	public static String answerQ1(String subject, String predicate, String object){
		StringBuilder sb = new StringBuilder();
		Person p = getPerson(subject);
		boolean addS = !(subject.equals("I") || subject.equals("you"));
		Property prop = new Property(predicate, object, true);
		Property pron = new Property(predicate, object, false);
		if(p == null) {if(!addProperty(subject, null)) return "I am abroad."; p = getPerson(subject); }
		if(p.properties.contains(prop)) sb.append("yes, " + subject + " ");
		else if(p.properties.contains(pron)) sb.append("no, " + subject + " " + (addS ? "doesn't " : "don't "));
		else return "maybe.";
		//sb.append(subject + " ");
		if(subject.equals("I") || subject.equals("you")){
			sb.append(predicate); 
		}else{
			sb.append(predicate + "s");
		}
		sb.append(object.equals("") ? "." : " " + object + ".");
		return sb.toString();
	}
	
	public static String answerQ2(String predicate, String object){
		StringBuilder sb = new StringBuilder();
		Property prop = new Property(predicate, object, true);
		Property pron = new Property(predicate, object, false);
		if(getPerson("everybody").properties.contains(prop)) 
			return "everybody " + predicate + (object.equals("") ? "." : " " + object + ".");
		if(getPerson("everybody").properties.contains(pron)) 
			return "nobody " + predicate + (object.equals("") ? "." : " " + object + ".");
		/*if(getPerson("nobody").properties.contains(pro))
			return "nobody " + predicate + (object.equals("") ? "." : " " + object + ".");*/
		ArrayList<String> subjects = new ArrayList<String>();
		for(int i = 0; i < persons.size(); i++){
			if(persons.get(i).properties.contains(prop)) subjects.add(persons.get(i).name);
		}
		if(subjects.size() == 0) return "I don't know.";
		if(subjects.size() == 1){
			sb.append(subjects.get(0) + " ");
			if(subjects.get(0).equals("I") || subjects.get(0).equals("you")){
				sb.append(prop.predicate);
			}else {
				sb.append(prop.predicate + "s");
			}
			sb.append(object.equals("") ? "." : " " + object + ".");
		}else{
			sb.append(subjects.get(0));
			for(int i = 1; i < subjects.size() - 1; i++){
				sb.append(", " + subjects.get(i));
			}
			sb.append(" and " + subjects.get(subjects.size() - 1));
			sb.append(" " + prop.predicate); 
			sb.append(object.equals("") ? "." : " " + object + ".");
		}
		return sb.toString();
	}
	
	public static String answerQ3(String subject){
		Person p = getPerson(subject);
		boolean addS = !(subject.equals("I") || subject.equals("you"));
		if(p == null) {if(!addProperty(subject, null)) return "I am abroad."; p = getPerson(subject); }
		StringBuilder sb = new StringBuilder();
		if(p.properties.size() == 0) return "I don't know.";
		sb.append(subject + " ");
		if(p.properties.get(0).isPositive){
			sb.append(p.properties.get(0).predicate + (addS ? "s" : ""));
		}else{
			sb.append((addS ? "doesn't " : "don't ") + p.properties.get(0).predicate);
		}
		if(!p.properties.get(0).object.equals(""))
			sb.append(" " + p.properties.get(0).object);
		int i = 1;
		for(; i < p.properties.size() - 1; i++){
			if(p.properties.get(i).isPositive){
				sb.append(", " +  p.properties.get(i).predicate + (addS ? "s" : ""));
			}else{
				sb.append((addS ? ", doesn't " : ", don't ") + p.properties.get(i).predicate);
			}
			if(!p.properties.get(i).object.equals(""))
				sb.append(" " + p.properties.get(i).object);
		}
		if(p.properties.size() > 1 ){
			sb.append(", and ");
			if(p.properties.get(i).isPositive){
				sb.append(p.properties.get(i).predicate + (addS ? "s" : ""));
			}else{
				sb.append((addS ? "doesn't " : "don't ") + p.properties.get(i).predicate);
			}
			if(!p.properties.get(i).object.equals(""))
				sb.append(" " + p.properties.get(i).object);
		}
		return sb.toString() + ".";
	}

	public static void initializePersons(){
		persons.clear();
		persons.add(new Person("everybody"));
	}
	
	public static Person getPerson(String name){
		for(Person p : persons){
			if(p.name.equals(name)) return p;
		}
		return null;
	}
	
	public static boolean addProperty(String name, Property pro){
		Person p = getPerson(name);
		if(p == null){
			Person np = new Person(name);
			Person everyBody = getPerson("everybody");
			np.properties.addAll(everyBody.properties);
			if(pro != null && !np.properties.contains(pro)){
				for(Property property : np.properties){
					if(property.similar(pro) && property.isPositive != pro.isPositive){
						contradict = true;
						return false;
					}
				}
				np.properties.add(pro);
			}
			persons.add(np);
			return true;
		}
		if(pro != null && !p.properties.contains(pro)){
			for(Property property : p.properties){
				if(property.similar(pro) && property.isPositive != pro.isPositive){
					contradict = true;
					return false;
				}
			}
			p.properties.add(pro);
		}
		if(name.equals("everybody"))
			for(int i = 1; i < persons.size(); i ++){
				for(Property property : persons.get(i).properties){
					if(property.similar(pro) && property.isPositive != pro.isPositive){
						contradict = true;
						return false;
					}
				}
				persons.get(i).properties.add(pro);
			}
		return true;
	}
	
	public static ArrayList<String> readWords(char[] input){
		StringBuilder sb = new StringBuilder();
		ArrayList<String> words = new ArrayList<String>(0);
		for(int i = 0; i < input.length; i++){
			if(input[i] == '.' || input[i] == '?') break;
			if(input[i] == ' '){
				words.add(sb.toString());
				sb = new StringBuilder();
				continue;
			}
			sb.append(input[i]);
		}
		words.add(sb.toString());
		return words;
	}
	
	private static class Person{
		String name;
		ArrayList<Property> properties;
		
		public Person(String name){
			this.name = name;
			properties = new ArrayList<Property>();
		}
		
		public String toString(){
			String s = name + " properties: " + properties;
			return s;
		}
	}
	
	private static class Property{
		String predicate;
		String object;
		boolean isPositive;
		
		public Property(String predicate, String object, boolean isPositive){
			if(predicate.endsWith("s")) predicate = predicate.substring(0, predicate.length() - 1);
			this.predicate = predicate;
			this.object = object;
			this.isPositive = isPositive;
		}
		
		public boolean equals(Object other){
			return predicate.equals(((Property)other).predicate) && object.equals(((Property)other).object)
					&& isPositive == ((Property)other).isPositive;
		}
		
		public boolean similar(Property other){
			return predicate.equals(other.predicate) && object.equals(other.object);
		}
		
		public String toString(){
			return "is positive: " + isPositive + " " + predicate + (object.equals("") ? "" : " " + object);
		}
	}
}
