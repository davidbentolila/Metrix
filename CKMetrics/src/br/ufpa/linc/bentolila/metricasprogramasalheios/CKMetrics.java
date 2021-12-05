package br.ufpa.linc.bentolila.metricasprogramasalheios;

import java.io.File;
import java.util.List;

import br.ufpa.linc.bentolila.metricasprogramasalheios.metricasCK.gr.spinellis.ckjm.CkjmOutputHandler;
import br.ufpa.linc.bentolila.metricasprogramasalheios.metricasCK.gr.spinellis.ckjm.ClassMetricsContainer;
import br.ufpa.linc.bentolila.metricasprogramasalheios.metricasCK.gr.spinellis.ckjm.MetricsFilter;
import br.ufpa.linc.bentolila.metricasprogramasalheios.metricasCK.gr.spinellis.ckjm.PrintPlainResults;
import br.ufpa.linc.configuration.Files;

/**
 * @author david Date: 22/09/2010
 */
public class CKMetrics {

	private static ClassMetricsContainer cmc;

	public static ClassMetricsContainer getCMC(){
		if (cmc == null ) cmc = new ClassMetricsContainer();
		return cmc;
	}
	
	public static void calculateCK(List<?> classes, boolean includeSDK,
			boolean onlyPublic) {
//		int count = 0;
		for (Object c : classes){
			if( c instanceof String) calculateCK((String)c,includeSDK, onlyPublic);
			else if( c instanceof File) {
//				System.out.println(((File)c).getAbsolutePath());
				calculateCK(((File)c).getAbsolutePath(),includeSDK, onlyPublic);
			}
//			if ( count++ == 5 ) System.exit(0);
		}		
	}

	public static void calculateCK(String classFile, boolean includeSDK, boolean onlyPublic) {
		MetricsFilter.processClass(getCMC(), classFile);
	}
	
	public static void printCKResult(){
		CkjmOutputHandler handler = new PrintPlainResults(System.out);
		getCMC().printMetrics(handler);
	}
	
	public static void main(String[] args) {
		
		File root = new File("/home/david/Documentos/UFPA/teste/uima-core_2.2.2/");
				
		List<File> classes = Files.listClasses(root);
		
		calculateCK(classes, false, true);
		
//		calculateCK("/home/david/Documentos/UFPA/teste/uima-core_2.2.2/org/apache/uima/cas_data/impl/ReferenceArrayFSImpl.class", false, true);
		
//		printCKResult();
		for (File file : classes) {
			String className = file.getAbsolutePath().replace(root.getAbsolutePath()+System.getProperty("file.separator"), "").replace(System.getProperty("file.separator"), ".").replace(".class", "");
			System.out.println(className);			
			System.out.println(getCMC().getMetrics(className));			
		}
		
		
		
//		LinuxCommand lc = new LinuxCommand();
//
//		List<File> classes = new ArrayList<File>(), dirs = new ArrayList<File>();
//		File root = new File(
//				"/home/david/Documentos/UFPA/teste/uima-core_2.2.2/org/apache/uima");
//
//		classes = Files.listClasses(root);
//		
//		File tempDir = new File(System.getProperty("user.home") + System.getProperty("file.separator") + ".MetriX" + System.getProperty("file.separator") + "temp");
//		
//		Files.clearDir(tempDir);
//		
//		tempDir.mkdirs();
//		
//		
//		dirs = Files.listDir(root);
//		
//		System.out.println(dirs.size());
//		
//		for (File f : dirs){
//			System.out.println("java -jar /home/david/downloads/Ferramenta_Metricas/ckjm-1.9/build/ckjm-1.9.jar " +f + "/*.class");
//			lc.execute("java -jar /home/david/downloads/Ferramenta_Metricas/ckjm-1.9/build/ckjm-1.9.jar " +f + "/*.class");
//			System.out.println(lc.getOutput());
////			if ( lc.getError() == "") System.out.println(lc.getOutput());
////			else System.out.println(lc.getError());
//		}

		
	}
}
