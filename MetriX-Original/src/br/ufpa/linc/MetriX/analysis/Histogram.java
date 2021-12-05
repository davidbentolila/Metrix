package br.ufpa.linc.MetriX.analysis;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Package;
import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.configurations.Files;
import br.ufpa.linc.MetriX.dao.Database;
import br.ufpa.linc.MetriX.dao.prevayler.DatabasePrevayler;
import br.ufpa.linc.MetriX.view.gui.FormOptionsAnalysis;
import br.ufpa.linc.MetriX.view.gui.MainWindow;
import br.ufpa.linc.MetriX.view.gui.Status;

public class Histogram {

	private List<API> apis = null;
    private String format = null;
    
	public Histogram(String format){
		this.format = format;
		
		List<API> dbAPIs = Database.getInstance().getAllAPI();
		if ( dbAPIs.size() > 0 ){ 
			apis = new ArrayList<API>();
			for (int i = 0 ; i < dbAPIs.size() ; i++) apis.add( dbAPIs.get(i) );
			new FormOptionsAnalysis(apis, this);
		}
		else JOptionPane.showMessageDialog(MainWindow.getInstance(), Configurations.getString("message.noAPI"));
	}

	public void action(final List<API> apis, final boolean usePrivateMethods, final List<Integer> metrics ){
		if ( format.equals("HISTOGRAM")) generateHistogram(apis, usePrivateMethods, metrics);
		else generateMetricData(apis, usePrivateMethods, metrics);
	}
	/**
	 * Generate metric Data and write in csv
	 * 
	 * @param apis - the list of API that will be used
	 * @param usePrivateMethods - true: use all methods (publics and privates) | false use just publics 
	 * @param metrics - which metrics use: 
	 * 
	 * metrics = 0 - all (il)
	 * metrics = 1 - all (is)
	 * metrics = 2 - all (np)
	 * metrics = 3 - all (nm)
	 * metrics = 4 - all (ppm)
	 * 
	 */
	public void generateMetricData(final List<API> apis, final boolean usePrivateMethods, final List<Integer> metrics ){
		int count = 0;
		
		for (API api : apis)
			for (Package p : api.getPackages())
				if ( p.isShow() ) count += p.getEntities().size();
		
		MetriX.getInstance().setStatus( new Status(Configurations.getString("analysis.start"), count ));

		Thread t = new Thread(MetriX.getInstance().getStatus());
		t.start();
		Thread t1 = new Thread(){
			public void run() {
				MetriX.getInstance().getStatus().setVisible(true);
				List<Metric> metricsList = new ArrayList<Metric>(); 
				StringBuffer metricsString = new StringBuffer();
				File save = null;
				double metric = 0;
				int count = 0;
				
				for (API api : apis) {
					for (Package p : api.getPackages()) {
						if ( p.isShow() ) 
							for (Entity e : p.getEntities()){
								MetriX.getInstance().getStatus().updateProgressBar(e.getPackage().getName() + "." + e.getName());
								metric = 0;
								//nao pode ser 0 a soma
//								if ( metrics.contains(0) ) metric += usePrivateMethods ? e.getILPrivate() : e.getILPublic();
								if ( metrics.contains(1) ) metric += e.getMetricsValues().getIS();
								if ( metrics.contains(2) ) metric += e.getMetricsValues().getNP();
								if ( metrics.contains(3) ) metric += e.getMetricsValues().getNM();
								if ( metrics.contains(4) ) metric += e.getMetricsValues().getPPM(); 
								addMetric( metric , metricsList );
							}
					}
					DatabasePrevayler.getInstance().update( api );
				}
				
				Collections.sort(metricsList);
				
				for (Metric m : metricsList) {
					if ( format.equals("CSV") ) metricsString.append( m.getValue() ).append(";").append( m.getFrequency() ).append("\n");
					else {
						metricsString.append( m.getValue() ).append(" ");
						
						if ( count == 10 ) metricsString.append("\n");
						
						count = (count == 11) ? count = 0  : count+1;
					}
				}
				MetriX.getInstance().getStatus().dispose();
				
				if ( format.equals("CSV") ) save = Files.saveToFile("csv", "histograma"); 
				else if ( format.equals("ARENA") ) save = Files.saveToFile("txt", "histograma"); 
								
				if ( save != null ) Files.createFile(save, metricsString.toString().replaceAll("\\.", ","), true);
			}
		};
		t1.start();
	}
	
//     aprivate JFreeChart createChart(IntervalXYDataset intervalxydataset)  
//     {  
//         JFreeChart jfreechart = ChartFactory.createHistogram("Histogram Metrics", null, null, intervalxydataset, PlotOrientation.VERTICAL, true, true, true);  
//         jfreechart.getXYPlot().setForegroundAlpha(0.75F);  
//         return jfreechart;  
//     }  
	   
	public void generateHistogram(final List<API> apis, final boolean usePrivateMethods, final List<Integer> metrics ){
		int count = 0;
		
		for (API api : apis)
			for (Package p : api.getPackages())
				if ( p.isShow() ) count += p.getEntities().size();
		
		final double[] ad = new double[count];
		
		MetriX.getInstance().setStatus( new Status(Configurations.getString("analysis.start"), count ));

		Thread t = new Thread(MetriX.getInstance().getStatus());
		t.start();
		Thread t1 = new Thread(){
			public void run() {
				MetriX.getInstance().getStatus().setVisible(true);
				List<Metric> metricsList = new ArrayList<Metric>();
				double metric = 0;
				int count = 0;
//				HistogramDataset histogramdataset = new HistogramDataset();  
		        double min = 9999999, max = -1;
//		        int bin = 0;
		        
		        
		        
		        
				for (API api : apis) {
					for (Package p : api.getPackages()) {
						if ( p.isShow() ) 
							for (Entity e : p.getEntities()){
								MetriX.getInstance().getStatus().updateProgressBar(e.getPackage().getName() + "." + e.getName());
								metric = 0;
//								if ( metrics.contains(0) ) metric += usePrivateMethods ? e.getILPrivate() : e.getILPublic();
								if ( metrics.contains(1) ) metric += e.getMetricsValues().getIS();
								if ( metrics.contains(2) ) metric += e.getMetricsValues().getNP();
								if ( metrics.contains(3) ) metric += e.getMetricsValues().getNM();
								if ( metrics.contains(4) ) metric += e.getMetricsValues().getPPM(); 
								ad[count++] = metric;
								addMetric(metric, metricsList);
								if ( metric < min ) min = metric;
								if ( metric > max ) max = metric;
								
							}
					}
					DatabasePrevayler.getInstance().update( api );
				}
				MetriX.getInstance().getStatus().dispose();
				
//				Random r = new Random();
//				int teste = 0;
//				for (int i = 0; i < ad.length; i++) {
//					teste = r.nextInt(10); 
//					ad[i] = teste;
//					min = 0;
//					max = 10;
//					addMetric(teste, metricsList);
//				}
				
//				bin = metricsList.size();
//		        histogramdataset.addSeries("Values", ad, bin, min, max);  
//		  
//				IntervalXYDataset intervalxydataset = histogramdataset;
//				
//				JFreeChart jfreechart = createChart(intervalxydataset);
//				
//				ChartPanel chartpanel = new ChartPanel(jfreechart);  
//				
////				final LayeredBarRenderer renderer = (LayeredBarRenderer) .getRenderer();
////		        renderer.setSeriesBarWidth(0, 1.0);
//				chartpanel.setPreferredSize(new Dimension(500, 270));
//				XYItemRenderer xyr = jfreechart.getXYPlot().getRenderer();  
//				xyr.setSeriesPaint(0, Color.BLUE);
				JDialog jd = new JDialog(MainWindow.getInstance(), true);
				jd.setSize(600,300);
//				jd.setContentPane(chartpanel);
				jd.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				jd.setVisible(true);
			}
		};
		t1.start();
	}
	
	public void addMetric(double value, List<Metric> metrics){
		for (Metric m : metrics)
			if ( m.getValue() == value ) {
				m.setFrequency( m.getFrequency() + 1 );
				return;
			}
		metrics.add( new Metric( value ) );
	}
}
