package br.ufpa.linc.MetriX.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;




@SuppressWarnings("serial")
public class TesteSerial implements Serializable{

	private String nome =null;
	private int id = 0;
	
	
	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	public void persistir(){
		try {
			ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(getNome() + ".metrix"));
			outputStream.writeObject(this);
			outputStream.flush();
			outputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ler(String name){
		try {
			ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(name + ".metrix"));
			TesteSerial teste = (TesteSerial) inputStream.readObject();
			System.out.println(teste.getNome());
			System.out.println(teste.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public static void main(String[] args) {
//		TesteSerial canalha1 = new TesteSerial();
//		canalha1.setNome("metrixTeste");
//		canalha1.setId(1);
//		canalha1.persistir();
//		System.out.println("acabou");
//		
//		canalha1.setNome("");
//		System.out.println(canalha1.getNome());
//		System.out.println("-------------");
//		canalha1.ler("metrixTeste");
////		TesteSerial canalha2 = new TesteSerial();
////		canalha2.setNome("canalhao");
////		canalha2.setId(2);
//	}
}
