package br.ufpa.linc.MetriX.analysis;

import java.io.File;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Package;
import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.configurations.Files;
import br.ufpa.linc.MetriX.metrics.Metric;
import br.ufpa.linc.MetriX.view.gui.MainWindow;
import br.ufpa.linc.MetriX.view.gui.Status;

/**
 * @author david Date: 22/10/2010
 */
public class Analysis {

	private Status status;
	private File dir;
	private NumberFormat nf;
	
	public Analysis(List<API> apis, int[] indexSelectedApis,
			List<Metric> metrics, int[] indexSelectedMetrics, File dir) {
		this.dir = dir;
		List<API> selectedApis = new ArrayList<API>();
		List<Metric> selectedMetrics = new ArrayList<Metric>();

		for (int i : indexSelectedApis)
			selectedApis.add(apis.get(i));
		for (int i : indexSelectedMetrics)
			selectedMetrics.add(metrics.get(i));

		nf = NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(0);
		nf.setMaximumFractionDigits(3);
		
		generateAnalysis(selectedApis, selectedMetrics);
	}

	private void generateAnalysis(final List<API> selectedApis,
			final List<Metric> selectedMetrics) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm");
		final String date = dateFormat.format(new Date());

		JOptionPane.showMessageDialog( MainWindow.getInstance(), Configurations.getString("message.choosePackagesUse"), Configurations.getString("window.title.choosePackages"), JOptionPane.INFORMATION_MESSAGE);
		int size = 0;
		
		for (int i = 0; i < selectedApis.size(); i++) {
			API api = selectedApis.get(i);
			System.out.println(api);
			size += api.getAllEntities().size();
//			new RestrictAPIPackages(selectedApis.get(i));
		}
		final int statusSize = size;
		
		
		status = new Status(Configurations.getString(
				"menu.analysis"), statusSize);

		Thread t = new Thread(status);
		t.start();
		Thread t1 = new Thread() {
			public void run() {
				File saveFile = null;
//*********************************				DefaultCategoryDataset dataset = new DefaultCategoryDataset();

				int countClass = 0, countClassAPI = 0, TotalMetricAnalysis = 0;
				int count = 0;
//				int apiEntities = 1;
//				//isAPI = analise interna  // isAPI2 = analise com todas as classes de tds as APIS  // ANalise entre os valores finais das API
//				MyList isAPI;
				// make sure that temp dir is empty
				// to don't count CBO metric Again.
				MetriX.tempDir = null;

				/*
				 * Uncomment if want get new decil
				 * 
				 */
				List<Double> metrics = new ArrayList<Double>();
				 /* 
				 */
				try {
					for (int i = 0; i < selectedApis.size(); i++) {
						countClassAPI = 0;
						API api = selectedApis.get(i);
						countClass = 0;

						status.updateProgressBar(api.getNome());
						
						StringBuffer APIMetrics = new StringBuffer("Entity;");
						for (Metric metric : selectedMetrics)
							APIMetrics.append(metric + ";");
						APIMetrics.append("\n");

						for (Package p : api.getPackages()) {
							if ( p.isShow() )
							for (Entity e : p.getEntities()) {
								countClass++;
								countClassAPI++;
								status.updateProgressBar(Configurations.getString("message.Readclass") + " " + (count++) + "/" + statusSize + " : " +  e.getFullName());

								APIMetrics.append(e.getFullName() + ";");
								for (Metric m : selectedMetrics) {
									APIMetrics.append(nf.format(m.getValue(e))
											+ ";");
								}
								APIMetrics.append("\n");
								
								/*
								 * Uncomment if want get new decil
								 * 
								 */
//								double metricDecil = e.getMetricsValues().getIS();
//								if ( !metrics.contains( metricDecil )) metrics.add( metricDecil );
								/*
								 */
							}
						}

						APIMetrics.append("Total :  " + countClassAPI
								+ " classes\n");

						saveFile = new File(dir + System.getProperty("file.separator") + api.getNome() + "_" + date + ".csv");
						Files.createFile( saveFile, APIMetrics.toString(), false);
					}
					
					double dec[] = MetriX.getInstance().getDecril(metrics);
					for (Double d : dec) System.out.println(d);
					
//					new BarChart3DDemo3("Teste", dataset);

					System.out
							.println("Total de classes analisadas: " + countClass);

					System.out
							.println("Total IS Analysis:  " + TotalMetricAnalysis);

					status.dispose();
					 JOptionPane.showMessageDialog( MainWindow.getInstance(), "Analysis files generated in: \n" + saveFile );
				}catch (Exception ex) {
					status.dispose();
					JOptionPane.showMessageDialog(MainWindow.getInstance(), "Error to read files.", "Error",
							JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
			}
		};
		t1.start();
	}
}
