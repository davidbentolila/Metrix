
/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2004, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * --------------------
 * BarChart3DDemo3.java
 * --------------------
 * (C) Copyright 2003, 2004, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: BarChart3DDemo3.java,v 1.9 2004/04/26 19:11:53 taqua Exp $
 *
 * Changes
 * -------
 * 24-Nov-2003 : Version 1 (DG);
 *
 */

package br.ufpa.linc.MetriX.view.chart;

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
//import org.jfree.data.category.DefaultCategoryDataset;
//import org.jfree.ui.ApplicationFrame;
//import org.jfree.ui.RefineryUtilities;

/**
 * This demonstration shows a 3D bar chart with item labels displayed.
 *
 */
public class BarChart3DDemo3 {
//extends ApplicationFrame {
//
//    /**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//
//	public BarChart3DDemo3(final String title, CategoryDataset dataset) {
//
//        super(title);
//        final JFreeChart chart = createChart(dataset);
//        final ChartPanel chartPanel = new ChartPanel(chart);
//        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
//        setContentPane(chartPanel);
//        pack();
//        RefineryUtilities.centerFrameOnScreen(this);
//        setVisible(true);
//    }
//
//    // ****************************************************************************
//    // * JFREECHART DEVELOPER GUIDE                                               *
//    // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
//    // * to purchase from Object Refinery Limited:                                *
//    // *                                                                          *
//    // * http://www.object-refinery.com/jfreechart/guide.html                     *
//    // *                                                                          *
//    // * Sales are used to provide funding for the JFreeChart project - please    * 
//    // * support us so that we can continue developing free software.             *
//    // ****************************************************************************
//    
//    /**
//     * Creates a sample dataset.
//     *
//     * @return a sample dataset.
//     */
//   @SuppressWarnings("unused")
//private CategoryDataset createDataset() {
//
//        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        dataset.addValue(25.0, "Series 1", "Category 1");   
//        dataset.addValue(34.0, "Series 1", "Category 2");   
//        dataset.addValue(19.0, "Series 2", "Category 1");   
//        dataset.addValue(29.0, "Series 2", "Category 2");   
//        dataset.addValue(41.0, "Series 3", "Category 1");   
//        dataset.addValue(33.0, "Series 3", "Category 2");   
//        return dataset;
//
//    }
//    
//    /**
//     * Creates a chart.
//     * 
//     * @param dataset  the dataset.
//     * 
//     * @return The chart.
//     */
//    @SuppressWarnings("deprecation")
//	private JFreeChart createChart(final CategoryDataset dataset) {
//        
//        final JFreeChart chart = ChartFactory.createBarChart3D(
//            "3D Bar Chart Demo",      // chart title
//            "Category",               // domain axis label
//            "Value",                  // range axis label
//            dataset,                  // data
//            PlotOrientation.VERTICAL, // orientation
//            true,                     // include legend
//            true,                     // tooltips
//            false                     // urls
//        );
//
//        final CategoryPlot plot = chart.getCategoryPlot();
//        final CategoryAxis axis = plot.getDomainAxis();
//        axis.setCategoryLabelPositions(
//            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 8.0)
//        );
//        
//        final CategoryItemRenderer renderer = plot.getRenderer();
//        renderer.setItemLabelsVisible(true);
//        final BarRenderer r = (BarRenderer) renderer;
//        r.setMaximumBarWidth(0.05);
//        
//        return chart;
//
//    }
}