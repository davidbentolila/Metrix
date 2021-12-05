package br.ufpa.linc.MetriX.analysis;

//import javax.swing.JFrame;

//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.axis.CategoryAxis;
//import org.jfree.chart.axis.CategoryLabelPositions;
//import org.jfree.chart.plot.CategoryPlot;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.chart.renderer.category.BarRenderer;
//import org.jfree.chart.renderer.category.CategoryItemRenderer;
//import org.jfree.data.category.CategoryDataset;
//import org.jfree.data.statistics.HistogramDataset;
//import org.jfree.data.statistics.HistogramType;
//import org.jfree.ui.RefineryUtilities;
//
//import br.ufpa.linc.MetriX.api.model.API;
//import br.ufpa.linc.MetriX.api.model.Entity;
//import br.ufpa.linc.MetriX.api.model.Package;
//import br.ufpa.linc.MetriX.dao.Database;

public class HistogramExample {
//	public static void main(String[] args) {
//		JFreeChart chart = null;
//
//		API api = Database.getInstance().getAllAPI().get(0);
//		int count = 0;
//		HistogramDataset dataset = new HistogramDataset();
//		dataset.setType(HistogramType.RELATIVE_FREQUENCY);
//		double[] values = new double[api.getAllEntities().size()];
//		double[] values2 = new double[api.getAllEntities().size()];
//
//		for (Package p : api.getPackages())
//			for (Entity e : p.getEntities()){
//				values[count] = e.getMetricsValues().getIS();
//				values2[count++] = e.getMetricsValues().getCBO();
//			}
//
//		dataset.addSeries(api, values, 3);
//		dataset.addSeries(api, values2, 3);
//		
//		String plotTitle = api.toString();
//		String xaxis = "number";
//		String yaxis = "value";
//		PlotOrientation orientation = PlotOrientation.VERTICAL;
//		boolean show = false;
//		boolean toolTips = false;
//		boolean urls = false;
//		chart = ChartFactory.createHistogram(plotTitle, xaxis, yaxis, dataset,
//				orientation, show, toolTips, urls);
//
//		JFrame jf = new JFrame();
//
//		final ChartPanel chartPanel = new ChartPanel(chart);
//		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
//		jf.setContentPane(chartPanel);
//		jf.pack();
//		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		RefineryUtilities.centerFrameOnScreen(jf);
//		jf.setVisible(true);
//	}
//
//	@SuppressWarnings({ "unused", "deprecation" })
//	private JFreeChart createChart(final CategoryDataset dataset) {
//
//		final JFreeChart chart = ChartFactory.createBarChart3D(
//				"3D Bar Chart Demo", // chart title
//				"Category", // domain axis label
//				"Value", // range axis label
//				dataset, // data
//				PlotOrientation.VERTICAL, // orientation
//				true, // include legend
//				true, // tooltips
//				false // urls
//				);
//
//		final CategoryPlot plot = chart.getCategoryPlot();
//		final CategoryAxis axis = plot.getDomainAxis();
//		axis.setCategoryLabelPositions(CategoryLabelPositions
//				.createUpRotationLabelPositions(Math.PI / 8.0));
//
//		final CategoryItemRenderer renderer = plot.getRenderer();
//		renderer.setItemLabelsVisible(true);
//		final BarRenderer r = (BarRenderer) renderer;
//		r.setMaximumBarWidth(0.05);
//
//		return chart;
//
//	}
}