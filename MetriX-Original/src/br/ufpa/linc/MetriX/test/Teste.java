package br.ufpa.linc.MetriX.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.configurations.Files;

public class Teste {

	private List<Analise> lista = null;
	private String nome = null;
	private int count = 0;

	public List<Analise> getLista() {
		return lista;
	}

	public void setLista(List<Analise> lista) {
		this.lista = lista;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Teste() {
		// lista = new ArrayList<Analise>();
		// fazACoisaToda();
	}

	private void adiciona(String metric, int qnt, List<Analise> list) {
		for (Analise a : list) {
			if (a.getMetrica().equals(metric)) {
				a.setQnt(a.getQnt() + qnt);
				return;
			}
		}
		list.add(new Analise(metric, qnt));
	}

	private void escreve() {
		Collections.sort(lista);
		for (Analise a : lista) {
			System.out.println(a.getMetrica() + " " + a.getQnt());
		}
	}

	public void fazACoisaToda() {
		String[] n = new String[] {
				arquivoToString(new File(
						"/home/david/Desktop/teste/analise/azureus_total.csv")),
				arquivoToString(new File(
						"/home/david/Desktop/teste/analise/jackrabbit_total.csv")),
				arquivoToString(new File(
						"/home/david/Desktop/teste/analise/jasperreports_total.csv")),
				arquivoToString(new File(
						"/home/david/Desktop/teste/analise/jedit_total.csv")),
				arquivoToString(new File(
						"/home/david/Desktop/teste/analise/jung_total.csv")),
				arquivoToString(new File(
						"/home/david/Desktop/teste/analise/megamek_total.csv")),
				arquivoToString(new File(
						"/home/david/Desktop/teste/analise/prefuse_total.csv")),
				arquivoToString(new File(
						"/home/david/Desktop/teste/analise/siena_total.csv")),
				arquivoToString(new File(
						"/home/david/Desktop/teste/analise/uima_total.csv")),
				arquivoToString(new File(
						"/home/david/Desktop/teste/analise/webapsse_agenda_cliente_total.csv")),
				arquivoToString(new File(
						"/home/david/Desktop/teste/analise/webapsse_manager_server_total.csv")),
				arquivoToString(new File(
						"/home/david/Desktop/teste/analise/zk_total.csv")) };

		// Pattern p = Pattern.compile("(\\d+)\\.(\\d+)E(\\d+)_(\\d+)");
		Pattern p = Pattern.compile("(\\d+)\\.(\\d+)E(\\d+)_(\\d+)");
		Pattern p2 = Pattern.compile("(\\d+)_(\\d+)");

		int pot = 0;
		int cont = 0;
		String n2s = "";
		String valor = "1.857315E7_1";
		for (String s1 : n) {
			String n2 = s1.replace(" ", "_");
			String[] numeros = n2.split("\n");
			for (String s : numeros) {
				valor = s;// s.replace("_", " ");
				Matcher m = p.matcher(valor);
				Matcher m2 = p2.matcher(valor);
				if (m.matches()) {
					pot = Integer.parseInt(m.group(3));
					n2s = m.group(2);
					while (n2s.length() < pot)
						n2s += "0";

					// System.out.println(m.group(1) + n2s + " " + m.group(4));
					adiciona(m.group(1) + n2s, Integer.parseInt(m.group(4)),
							lista);
				} else if (m2.matches()) {
					// System.out.println(m2.group(1) + " " + m2.group(2));
					adiciona(m2.group(1), Integer.parseInt(m2.group(2)), lista);
				} else
					JOptionPane.showMessageDialog(null, cont + "nao confirma |"
							+ valor + "|");
			}
			cont++;
		}
		escreve();
	}

	public String arquivoToString(File arquivo) {
		StringBuffer texto = new StringBuffer();
		try {
			BufferedReader leitor = new BufferedReader(new FileReader(arquivo));

			String linha;
			while ((linha = leitor.readLine()) != null)
				texto.append(linha).append("\n");
			leitor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return texto.toString();
	}

	public class Analise implements Comparable<Analise> {
		private String metrica;
		private int qnt = 0;

		public Analise(String metrica) {
			this.metrica = metrica;
			qnt = 1;
		}

		public Analise(String metrica, int qnt) {
			this.metrica = metrica;
			this.qnt = qnt;
		}

		public String getMetrica() {
			return metrica;
		}

		public void setMetrica(String metrica) {
			this.metrica = metrica;
		}

		public int getQnt() {
			return qnt;
		}

		public void setQnt(int qnt) {
			this.qnt = qnt;
		}

		public int compareTo(Analise a) {
			if (getMetrica().length() < a.getMetrica().length())
				return -1;
			if (getMetrica().length() > a.getMetrica().length())
				return 1;
			return getMetrica().compareTo(a.getMetrica());
		}

	}

	// public static void main(String[] args) {
	// // new Teste();
	// // String[][] abc = new String[5][3];
	// // abc[4][2] = "asdasd";
	// // System.out.println(abc.length);
	//
	// double d = 3;
	// System.out.println(d);
	// double b = d/2;
	// System.out.println(b);
	// System.out.println((int)b);
	// }

	public void mexe(StringBuffer sbf, Double num) {
		if (num == null)
			sbf.append("-;");
		else
			sbf.append(num).append(";");
	}

	public void testa() {
		StringBuffer sbf = new StringBuffer();
		Double num;

		num = 7d;
		mexe(sbf, num);

		num = 10d;
		mexe(sbf, num);

		num = null;
		mexe(sbf, num);

		num = 15d;
		mexe(sbf, num);

		System.out.println(sbf);

	}

	public static void main(String[] args) {
		
		File folder = new File("/home/david/Documentos/UFPA/2011/MetriX/Analise Jazz/RTC-Client-2.0.0.2-Linux/FullJars/nomepacotes");
//		List<File> jars = new ArrayList<File>();
//		List<File> folders  = new ArrayList<File>();
//		File[] files = folder.listFiles();
		JarFile jarFile;
		URI uriClass;
		File path;
		JarEntry je;
		
//		for (File f : files) {
//			if (f.getName().endsWith(".jar")) jars.add(f);
//			else if ( f.isDirectory() ) folders.add(f);
//		}
//		
//		while (folders.size() > 0){
//			File current = folders.get(folders.size()-1);
//			files = current.listFiles(); 
//			for (File f : files) {
//				if (f.getName().endsWith(".ja		r")) jars.add(f);
//				else if ( f.isDirectory() ) folders.add(f);
//			}	
//			folders.remove(current);
//		}
		
		for (File f : folder.listFiles())
			try {
				jarFile = new JarFile(f);
				Enumeration<JarEntry> e = jarFile.entries();
				
				while (e.hasMoreElements()) {
					je = e.nextElement();
					if (je.getName().endsWith("class") && !je.getName().contains("$")) {
						uriClass = new File(getPath(je)).toURI();
						path = new File(uriClass.getPath().substring(0, uriClass.getPath().lastIndexOf("/")));
					if (!path.exists() && !path.mkdirs()) {
						JOptionPane.showMessageDialog(null, Configurations
								.getString("message.error.classDir"));
						System.exit(0);
					}
					System.out.println("copiando: " + je.getName());
					Files.copyClass(jarFile, je, uriClass);
					}
				}
//				Files.copyFile(f, "/home/david/Documentos/UFPA/2011/MetriX/Analise Jazz/RTC-Client-2.0.0.2-Linux/FullJars");
			} catch (IOException e) {
				System.out.println(e);
				System.out.println("error to copy " + f);
			}
		
		String s1, s2;
		
		s1 = "casa";
		s2 = "casa";
		
		System.out.println(s1 == s2);
		System.out.println(s1.equals(s2));
		
		List<Integer> is = new ArrayList<Integer>();
		
		adiciona(1, is);
		
		adiciona(2, is);
		
		adiciona(3, is);
		
		for (Integer i : is) {
			System.out.println(i);
		}
		
		
//		new Teste().testa();
	}
		
	private static String getPath(JarEntry je){
		if ( MetriX.tempDir == null ) MetriX.tempDir = Configurations.getTempDir();
		return MetriX.tempDir.getAbsolutePath()
		+ System.getProperty("file.separator")
		+ je.getName().replace('/', System.getProperty("file.separator").charAt(0));
	}

	static void adiciona(int i, List<Integer> is) {
		is.add(i);
	}
}
