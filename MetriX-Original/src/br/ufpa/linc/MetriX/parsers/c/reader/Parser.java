package br.ufpa.linc.MetriX.parsers.c.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet; 
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	
	private String code;
	private String pathDir, fileName;

	private Set<Function> functions;
	private List<Parser> includes=null;
	private Set<String> toBeKill;

	public Parser(String pathDir, String fileName) throws FileNotFoundException {
		this(pathDir, fileName, new ArrayList<Parser>(), new HashSet<String>());
		includes.add(this);
	}
	
	private Parser(String pathDir, String fileName, List<Parser> includeds, Set<String> toBeKilled) throws FileNotFoundException{
		includes = includeds;
		toBeKill =  toBeKilled;
		File f = new File(pathDir + fileName);
		functions = new HashSet<Function>();
		this.pathDir = pathDir;
		this.fileName = fileName;
		Scanner scanner = new Scanner(f);
		code = "";
		while(scanner.hasNextLine())
			code += scanner.nextLine() +"\n";
	}

	public static void main(String[] args) throws FileNotFoundException {
		Parser p = new Parser("teste/SDL/", "SDL.h");
		p.preRun();
		System.out.println();
		
		for (Parser parser : p.getIncludes()) {
			parser.parse();
			System.out.println("File:"+parser.getFileName()+parser.getFunctions());
		}
	}
	
	public List<Parser> getIncludes() {
		return includes;
	}

	public void preRun() throws FileNotFoundException{
		String steste2 = Pattern.compile("(/\\*(?si)(.*?)\\*/)").matcher(code).replaceAll("");
		//Killing includes
		Matcher includeMatcher = Pattern.compile("#\\s*include*\\s+[<\"]([A-Za-z0-9/._ -]*)[<\"]").matcher(steste2);
		while(includeMatcher.find()){
			String newIncluded = includeMatcher.group(1);
			if(!hasIncluded(newIncluded)){
				Parser p = new Parser(pathDir, newIncluded, includes, toBeKill);
				includes.add(p);
				p.preRun();
			}
		}
		steste2 = includeMatcher.replaceAll("");
		//Killing defines
		Matcher defineMatcher = Pattern.compile("#\\s*define*\\s+([\\p{Alnum}_]*)\\s*([^#\n\\\\]|\\\\\n)*").matcher(steste2);
		while(defineMatcher.find()){
			toBeKill.add(defineMatcher.group(1));
		}
		steste2 = defineMatcher.replaceAll("");
		//Adding evil keyword to toBeKill list
		toBeKill.add("extern");
		toBeKill.add("volatile");
		toBeKill.add("const");
		toBeKill.add("register");
		//Killing all that are in toBeKill list
		Iterator<String> it = toBeKill.iterator();
		String defineds = "("+it.next()+")";
		while(it.hasNext()){
			defineds += "|("+it.next()+")";
		}
		steste2 = Pattern.compile(defineds).matcher(steste2).replaceAll("");
		//Killing all others #directives
		steste2 = Pattern.compile("#([^\n\\\\]|\\\\\n)*").matcher(steste2).replaceAll("");
		//----------------------------------
		code = steste2;
	}
	

	private boolean hasIncluded(String newIncluded) {
		for (Parser parser : includes) {
			if(parser.getFileName().equals(newIncluded)) return true;
		}
		return false;
	}

	public void teste1() {

		Pattern includePattern = Pattern.compile("[^\\(](int)\\s+(?:.*?)\\s+([\\p{Alnum}_]+)\\s*\\((.*?)\\)\\s*;\\s*");
		Matcher includeMatcher = includePattern.matcher(code);
		while(includeMatcher.find()){
			System.out.println(includeMatcher.group());
//			System.out.println("\t"+includeMatcher.group(2));
		}
	}
	
	public void parse() {
		Matcher typeMatcher = Pattern.compile("typedef\\s*struct\\s*[A-Za-z0-9_]*\\s*\\{[^}]*\\}\\s*([A-Za-z0-9_]*)\\s*;").matcher(code);
		code = typeMatcher.replaceAll("");
		typeMatcher = Pattern.compile("(typedef[^;]*);").matcher(code);
		code = typeMatcher.replaceAll("");
		
		Matcher functionMatcher = Pattern.compile("([A-Za-z0-9_]*)\\s+(\\**)\\s*([A-Za-z0-9_]*)\\s*\\(([^)]*)\\);").matcher(code);
		while(functionMatcher.find()){
			Matcher paramsMatcher = Pattern.compile("([A-Za-z0-9_]+)\\s+(\\**)\\s*([A-Za-z0-9_]+)[,]?").matcher(functionMatcher.group(3));
			Function function = new Function();
			function.setType(functionMatcher.group(1));
			function.setPointerLevel(functionMatcher.group(2).length());
			function.setName(functionMatcher.group(3));
			LinkedList<Parameter> attributes = new LinkedList<Parameter>();
			function.setAtributes(attributes);
			functions.add(function);
			while(paramsMatcher.find()){
//				System.out.println("  "+paramsMatcher.group(1)+ " " + paramsMatcher.group(2)+  " " + paramsMatcher.group(3));
				Parameter attribute = new Parameter();
				attribute.setType(paramsMatcher.group(1));
				attribute.setPointerLevel(paramsMatcher.group(2).length());
				attribute.setName(paramsMatcher.group(3));
				attributes.add(attribute);
			}
		}
	}

	public Set<Function> getFunctions() {
		return functions;
	}

	public String getFileName() {
		return fileName;
	}

}
