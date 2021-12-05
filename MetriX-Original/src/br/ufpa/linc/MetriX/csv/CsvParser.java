package br.ufpa.linc.MetriX.csv;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CsvParser {
	private Map<String,List<Field>> fields;
	private String []titles;
	private int numLines;
	private FileReader file;
	private char look =0;
	private int count=0;
	
	private char nextChar() throws IOException{
		char c = look;
		look = (char)file.read();
		count ++;
		if(count % (1024*1024)==0) System.out.println("Readed "+count/(1024*1024)+" MiB");
		else if(count % 1024==0) System.out.println("Readed "+count/(1024)+" KiB");
		return c;
	}
	
	private StringField getString() throws IOException{
		String r = "";
		if(look == '"'){
			nextChar();
			while(look!='"') r+= nextChar();
			match('"');
		}
		else {
			while(Character.isJavaIdentifierPart(look)) r += nextChar();
		}
		return new StringField(r);
	}
	private NumField getNum() throws IOException{
		String r = "";
		while(Character.isDigit(look) || look == '.'){
			r+= nextChar();
		}
		
		return new NumField(Double.valueOf(r));
	}
	
	private Field getField() throws IOException{
		if(Character.isDigit(look))return getNum();
		else return getString();
	}
	
	private List<Field> getLine() throws IOException{
		List <Field> line = new LinkedList<Field>();
		line.add(getField());
		while(look == ','){
			match(',');
			line.add(getField());
		}
		return line;
	}
	private void load() throws IOException{
		fields = new HashMap<String, List<Field>>();
		List<Field> firstLine = getLine();
		titles = new String[firstLine.size()];
		int k = 0;
		for (Field field : firstLine) {
			fields.put(field.getValueS(), new ArrayList<Field>());
			titles[k++] = field.getValueS();
		}
		numLines = 0;
		while (file.ready()){
			match('\r');
			match('\n');
			List<Field> line = getLine();
			int size = line.size();
			for(int i=0; i < size; ++i){
				fields.get(titles[i]).add(line.get(i));
			}
			++numLines;
		}
		System.out.println("File Loaded.");
	}
	
	private void match(char c) throws IOException {
		if(look==c) nextChar();
//		else throw new Exception();
		else System.err.println("Error: Wrong char: '"+look+"'");
	}

	public CsvParser(File file) throws IOException {
		fields = new HashMap<String, List<Field>>();
//		File file = new File(filename);
		if(file.canRead()){
			FileReader fileReader = new FileReader(file);
			this.file = fileReader;
			nextChar();
			load();
//			String s= "";
//			System.out.println("Reading the file...");
////			int c = fileReader.read();
////			fileReader.
//			int count =0;
//			while (fileReader.ready()){
//				int c = fileReader.read();
//				s += (char) c;
//				count++;
//				if(count % (1024*1024)==0) System.out.println("Readed "+count/(1024*1024)+" MiB");
//				else if(count % 1024==0) System.out.println("Readed "+count/(1024)+" KiB");
////				System.out.print(".");
//			}
////			System.out.println();
//			fileReader.close();
//			System.out.println("Identifying lines");
//			String[] lines = s.split("\n");
//			//Achando o títulos
//			System.out.println("Identifying titles");
//			titles = lines[0].split(",");
//			for (String t : titles) {
//				fields.put(t, new LinkedList<Field>());
//			}
//			System.out.println("Reading lines");
//			for (int i = 1; i < lines.length; i++) {
//				System.out.println("Reading cells of line "+i);
//				String[] cells = lines[i].split(",");
//				for (int j = 0; j < cells.length; j++) {
//					Field f;
//					if(Character.isDigit(cells[j].charAt(0))){
//						f = new NumField(Double.valueOf(cells[j]));
//					}
//					else {
//						f= new StringField(cells[j]);
//					}
//					fields.get(titles[j]).add(f);
//				}
//			}
//			numLines = lines.length-1;
//			System.out.println("File Loaded.");
		}
	}
	
	public String[] getTitles() {
		return titles;
	}

	public Field getField(int line, String column){
		return fields.get(column).get(line);
	}
	
	public Field getField(int line, int column){
		return getField(line, titles[column]); 
	}

	public List<Field> getLine(int num){
		List<Field> line = new ArrayList<Field>();
		for (String title : titles) {
			line.add(getField(num, title));
		}
		return line;
	}

	public int getNumLines() {
		return numLines-1;
	}
	
	
	
}
