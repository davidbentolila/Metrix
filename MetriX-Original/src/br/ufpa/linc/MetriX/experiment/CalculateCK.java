package br.ufpa.linc.MetriX.experiment;

import java.io.File;
import java.io.IOException;
import java.util.List;

import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.dao.prevayler.DatabasePrevayler;
import br.ufpa.linc.bentolila.metricasprogramasalheios.CKMetrics;
import br.ufpa.linc.configuration.Files;

/**
 *@author david
 *Date: 23/09/2010
 */
public class CalculateCK {
	
	public static void main(String[] args) throws IOException {
		File root = new File("/home/david/Documentos/UFPA/teste/u ima-core_2.2.2/");
		
		List<File> classes = Files.listClasses(root);
		
		List<API> apis = DatabasePrevayler.getInstance().getAllAPI();
		
		API api = apis.get(1);

		List<Entity> entities = api.getAllEntities();
		
		CKMetrics.calculateCK(classes, true, true);
		
		String classData = "";
		StringBuffer csv = new StringBuffer("Class,CBO,IS,NAP\n");
		int countEntity = 0;
		for (Entity e : entities) {
			double is = e.getMetricsValues().getIS();
			/* Classe Name */
			classData = e.getFullName() + " ";
			
			/* Coupling between object classes (CBO) [Chidamber and Kemerer 1994] */
			classData += e.getMetricsValues().getCBO() + " ";
			
			/* Interface Size (IS) [Abbott 1993] */
			classData += String.valueOf((int)is) + " ";
			
			/* Número de Variáveis (atributos) públicos (NAP) [Harrison et al 1997] */
			classData += e.getMetricsValues().getNPA();
			
//			System.out.println(classData);
			csv.append(classData.replace(" ", ",") + "\n");
			countEntity++;
		}		
		
		csv.append("\n\nCorrelação CBO/IS,=correl(B2:B"+(countEntity+1)+";C2:C"+(countEntity+1)+")");
		csv.append("\n\nCorrelação CBO/NAP,=correl(B2:B"+(countEntity+1)+";D2:D"+(countEntity+1)+")");
		csv.append("\n\nCorrelação IS/NAP,=correl(C2:C"+(countEntity+1)+";D2:D"+(countEntity+1)+")");
		System.out.println(csv);
		br.ufpa.linc.MetriX.configurations.Files.writeInFile(new File("/home/david/Desktop/metricas.csv"), csv);		
	}
}
