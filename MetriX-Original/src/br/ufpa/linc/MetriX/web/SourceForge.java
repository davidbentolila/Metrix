package br.ufpa.linc.MetriX.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

/**
 *@author david
 *Date: 19/02/2010
 */
public class SourceForge {

		static final long serialVersionUID = 1;
		static String palavra;
		static int proxPagina = 0;
		static List<String> projetos = new ArrayList<String>();
    	static List<String> linguagens = new ArrayList<String>();
	//<a href="/project/memberlist.php?group_id=(\\d+)"(.*?)>Members\\s[(](\\d+)[)]<(?:.*?)>
//	    projetos.encontraProjeto("<a href=\"/project/memberlist.php?group_id=54425\" class=\"member_md\">Members (2)</a>");
	    public static void escreve (String texto) {
			try {
				String caminho = System.getProperty("user.home") +
				System.getProperty("file.separator") +
				"Desktop" +
				System.getProperty("file.separator");
				FileWriter writer = new FileWriter(new File(caminho+palavra + ".csv"), true);
				BufferedWriter bufWriter = new BufferedWriter(writer);
				bufWriter.write(texto);
				bufWriter.close();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	    
	    private static void encontraProjeto(String s, int numeroDeDownloadsMinimo){
	    	Pattern pdownLink = Pattern.compile("title=\"Download this file from SourceForge.net\" href=\"(.*?)\">");
//	    	Pattern pdownLanguage = Pattern.compile("<li>Programming Language: <a href=\"/softwaremap/trove_list\\.php\\?form_cat=198\">Java</a></li>(?si)<a href=\"(.*?)\" title=\"Download this file from SourceForge\\.net");
	    	Pattern pLanguage = Pattern.compile("<a href=\"/softwaremap/\\?&amp;fq%5B%5D=trove%3A198\">Java</a></div>");

//	    	Pattern pProject = Pattern.compile("<td class=\"project\">(.*?)</tbody>");
//	    	Pattern pa = Pattern.compile("<td class=\"project\">(?:.*?)<h2><a href=\"/projects/(.+?)/\">(.+?)</a></h2>(?:.*?)<td class=\"select\">(.+?)</td>");
	    	Pattern pNext = Pattern.compile("<a href=\"/softwaremap/\\?words=&amp;sort=num_downloads_week&amp;sortdir=desc&amp;offset=(\\d+)&amp;type_of_search=soft&amp;pmode=0&amp;fq%5B%5D=trove%3A198\">Next");
	    									  
//	    	Pattern pa = Pattern.compile("<td class=\"project\">(?:.*?)<h3><a href=\"/projects/(.*?)/\"(?:.*?)<a(?:.*?)memberlist(?:.*?)id=(.+?)\"(?:.*?)>(?:.*?)(\\d+)(?:.*?)>");
	    	Matcher m = pdownLink.matcher(s), m2;
	    	while (m.find()){
	    		String link ="http://sourceforge.net" + m.group(1).replace("/files/latest", ""); 
    			String detalhe = lerPagina(link);
    			escreve(detalhe);
    			m2 = pLanguage.matcher( detalhe );
    	    	if (m2.find()) {
    	    		System.out.println(link);
    	    		projetos.add( link );
    	    	}
    			
	    	}

	    	m = pNext.matcher(s);
	    	if ( m.find() ) proxPagina = Integer.parseInt(m.group(1));
	    	else proxPagina = 0;
	    }
	    
	    public static String lerPagina(String palavra, int id){
	    	String texto = "";
			String linha;
			try {
//				String endereco = "http://sourceforge.net/search/?words=" + palavra + "&sort=num_downloads&sortdir=desc&offset="+(id-1)*10+"&type_of_search=soft&pmode=0";
//				String endereco = "http://sourceforge.net/softwaremap/trove_list.php?stquery=&sort=num_downloads&sortdir=desc&offset="+id+"&form_cat=198";
				String endereco = "http://sourceforge.net/softwaremap/?words=&sort=num_downloads_week&sortdir=desc&offset="+id+"&type_of_search=soft&pmode=0&fq[]=trove%3A198";
//				System.out.println(endereco);
		        URL url = new URL( endereco ); // instancia a classe url
		        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));// cria um leitor de buffer e recebe o input strem e o urlstrem que e o resultado da consulta.
				BufferedReader leitor = new BufferedReader(reader, 1 * 1024 * 1024);
	
				linha = reader.readLine();
				while ( linha != null) {
					texto += linha; 
					linha = reader.readLine();
		        }//while
		
				leitor.close();
				reader.close();
				
		        } catch (IOException e) {
				e.printStackTrace();
			}
		     return texto;
	    }
	    
	    public static String lerPagina(String endereco){
	    	String texto = "";
			String linha;
			try {
		        URL url = new URL( endereco ); // instancia a classe url
		        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));// cria um leitor de buffer e recebe o input strem e o urlstrem que e o resultado da consulta.
				BufferedReader leitor = new BufferedReader(reader, 1 * 1024 * 1024);
	
				linha = reader.readLine();
				while ( linha != null) {
					texto += linha + "\n"; 
					linha = reader.readLine();
		        }//while
		
				leitor.close();
				reader.close();
				
		        } catch (IOException e) {
				e.printStackTrace();
			}
		     return texto;
	    }
	    
	    public static void main(String[] args) {
	    	args = new String[4];
//	    	PalavraDeBusca
	    	args[0] = "java";
//	    	paginaInicial
	    	args[1] = "0";
//	    	paginaFinal 

	    	args[2] = "40";
//	    	numeroDeDownloadsMinimo
	    	args[3] = "100000";

	    	if ( args.length == 4 ) {
		    	String palavraBusca = args[0];
		    	palavra = palavraBusca;

		    	int maximo = Integer.parseInt(args[2]);
		    	int numeroDeDownloadsMinimo = Integer.parseInt(args[3]);
		    	
				while ( true ){
					String tudo = SourceForge.lerPagina(palavraBusca,proxPagina);
//					escreve(tudo);
//					System.exit(0);
//					System.out.println(tudo);
					SourceForge.encontraProjeto(tudo, numeroDeDownloadsMinimo);
					System.out.println(proxPagina);
//					System.out.println(tudo);
//					System.exit(0);
//					System.out.println("lendo Projetos da pagina " + pagina);
					
//					pagina++;
					if ( proxPagina == 0 || projetos.size() >= maximo ) break;
					System.out.println(projetos.size() + "  " + linguagens.size());
				}
				JOptionPane.showMessageDialog(null,"fim da analise");
	    	}
	    	else System.out.println("para usar o programa digite \n           java -jar sourceForge.jar PalavraDeBusca paginaInicial paginaFinal numeroDeMembrosMinimo numeroMaximoMembros\n");
		}
	            
}