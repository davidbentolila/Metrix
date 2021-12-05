package br.ufpa.linc.MetriX.view.starplot;

//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.event.MouseWheelEvent;
//import java.awt.event.MouseWheelListener;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.swing.JLabel;
import javax.swing.JPanel;
//
//import br.ufpa.linc.MetriX.MetriX;
//import br.ufpa.linc.MetriX.api.model.API;
//import br.ufpa.linc.MetriX.api.model.Entity;
//import br.ufpa.linc.MetriX.api.model.Method;
//import br.ufpa.linc.MetriX.api.model.Package;
//import br.ufpa.linc.MetriX.configurations.Configurations;
//import br.ufpa.linc.MetriX.view.treemap.MyTreeMap;
//import br.ufpa.linc.sargas.core.SargasNetwork;
//import br.ufpa.linc.sargas.core.SargasProject;
//import br.ufpa.linc.sargas.core.User;
//import br.ufpa.linc.sargas.statistics.ZScore;
//import br.ufpa.linc.sargas.util.Metric;
//import br.ufpa.linc.sargas.visualization.Star;

public class MyStarPlot extends JPanel {
//
//	/**
//	 * 
//	 */
	private static final long serialVersionUID = 4912836642809796210L;
//	private SargasProject project = null;
//	private SargasNetwork snIS = null,
////						  snOAC = null,
//						  snNA = null,
//						  snNM = null,
//						  snAPP = null,
//						  snTOTAL = null;
//	
//	private ZScore zscoreIS = null,
////				   zscoreOAC = null,
//				   zscoreNA = null,
//				   zscoreNM = null,
//				   zscoreAPP = null,
//				   zscoreTOTAL = null;
//	
//	private JPanel colorPanelStarPlot = null;
//	private JLabel labelColorsStarPlot[] = null;
//	private Color colorsStarPlot[] = null;
//	
//	private double is, total, na, nm, app;//oac, 
//	
//	private List<MyUser> users = null;
//	
//	
//	public MyStarPlot(){
//		colorsStarPlot = Configurations.getStarPlotColors();
//	}
//	
//	private void initSargas( Object o ){
//		
//		if ( project == null ){
//			if ( o instanceof API ) project = new SargasProject(((API)o).getNome());
//			else if ( o instanceof Package ) project = new SargasProject(((Package)o).getNome());
//			else if ( o instanceof Entity ) project = new SargasProject(((Entity)o).getNome());
//			else if ( o instanceof Method ) project = new SargasProject(((Method)o).getNome());
//		
////			project.getVisualization().setLayout(new FlowLayout());
//			
//			project.getVisualization().addMouseListener( new MouseListener(){
//				public void mouseClicked(MouseEvent e) {}
//				public void mouseEntered(MouseEvent e) {}
//				public void mouseExited(MouseEvent e) {}
//				public void mousePressed(MouseEvent e) {}
//				public void mouseReleased(MouseEvent e) {}
//			});
//		
//			project.getVisualization().addMouseWheelListener(new MouseWheelListener(){
//				public void mouseWheelMoved(MouseWheelEvent e) {}
//			});
//		
//			snIS = new SargasNetwork(Configurations.getStarPlotColors()[1], Metric.DEGREE, "IS");
////			snOAC = new SargasNetwork(Configurations.getStarPlotColors()[2], Metric.DEGREE, "OAC");
//			snTOTAL = new SargasNetwork(Configurations.getStarPlotColors()[2], Metric.DEGREE, "TOTAL");
//			snNA = new SargasNetwork(Configurations.getStarPlotColors()[3], Metric.DEGREE, "NA");
//			snNM = new SargasNetwork(Configurations.getStarPlotColors()[4], Metric.DEGREE, "NM");
//			snAPP = new SargasNetwork(Configurations.getStarPlotColors()[5], Metric.DEGREE, "APP");
//		
//			project.addNetwork(snIS);
////			project.addNetwork(snOAC);
//			project.addNetwork(snTOTAL);
//			if ( !(o instanceof Method ) ) {
//				project.addNetwork(snNA);
//				project.addNetwork(snNM);
//				project.addNetwork(snAPP);
//			}
//			users = new ArrayList<MyUser>();
//		}
//	}
//	
//	private void initZscore(){
//		if ( zscoreIS == null ){
//			zscoreIS = new ZScore();
////			zscoreOAC = new ZScore();
//			zscoreNA = new ZScore();
//			zscoreNM = new ZScore();
//			zscoreAPP = new ZScore();
//			zscoreTOTAL = new ZScore();
//		}
//	}
//
//	public void generateVisualization(){
//		int x = 0,
//		y = 0,
//		w = 0,
//		h = 0,
//		starInLine = 8;
//		Star star = null;
//		if (users != null)
//		for (MyUser u : users) {
//			star = addStar(u);
//			w = star.getWidth();
//			h = star.getHeight();
//
//			project.getVisualization().add(star);
//			
//			star.setBounds(x, y, w, h);
//			
//			x += w;
//			if ( x > w * starInLine) {
//				x = 0;
//				y+=h;
//			}
//		}
//		setLayout(new BorderLayout() );
//		project.getVisualization().validate();
//		add( project.getVisualization(), BorderLayout.CENTER);
//		add( getColorPanelStarPlot(), BorderLayout.SOUTH);
//		/* *******************************************
//		 * MetriX.getInstance().setStarPlotPanel(this);
//		 */
//	}
//	
//	public void addObject(Object o){
//		initSargas(o);
//		initZscore();
//		if ( o instanceof Package ) addPackageMetrics((Package)o);
//		else if ( o instanceof Entity ) addEntityMetrics((Entity)o);
//		else if ( o instanceof Method) addMethodMetrics((Method)o);
//		users.add( new MyUser(o, project ));
//	}
//	
//	private void addPackageMetrics(Package p){
////		il = p.getIL(main.isUsePrivateMethods());
//		is = p.getMetricsValues().getIS();
////		oac = p.getOAC();
//		na = p.getMetricsValues().getNP();
//		nm = p.getMetricsValues().getNM();
//		app = p.getMetricsValues().getPPM();
//		total = is;
//		
//		zscoreIS.add( is );
////		zscoreOAC.add( oac );
//		zscoreTOTAL.add( total );
//		zscoreNA.add( na );
//		zscoreNM.add( nm );
//		zscoreAPP.add( app );
//	}
//	
//	private void addEntityMetrics(Entity e){
////		il = e.getIL();
//		is = e.getMetricsValues().getIS();
////		oac = e.getOAC();
//		na = e.getMetricsValues().getNP();
//		nm = e.getMetricsValues().getNM();
//		app = e.getMetricsValues().getPPM();
//		total = is;// + oac;
//		
//		zscoreIS.add( is );
////		zscoreOAC.add( oac );
//		zscoreTOTAL.add( total );	
//		zscoreNA.add( na );
//		zscoreNM.add( nm );
//		zscoreAPP.add( app );
//	}
//	
//	private void addMethodMetrics(Method m){
//		is = m.getMetricsValues().getIS();
//		total = is;
//
//		zscoreIS.add( is );
////		zscoreOAC.add( oac );
//		zscoreTOTAL.add( total );
//	}
//	
//	private void setMetrics(Object o){
//		if ( o instanceof Package ) setPackageMetrics((Package)o);
//		else if ( o instanceof Entity ) setEntityMetrics((Entity)o);
//		else if ( o instanceof Method) setMethodMetrics((Method)o);
//	}
//	
//	private void setPackageMetrics(Package p){
////		il = p.getIL();
//		is = p.getMetricsValues().getIS(  );
////		oac = p.getOAC();
//		na = p.getMetricsValues().getNP();
//		nm = p.getMetricsValues().getNM();
//		app = p.getMetricsValues().getPPM();
//		total = is;// + oac;
//		
//	}
//	
//	private void setEntityMetrics(Entity e){
////		il = e.getIL();
//		is = e.getMetricsValues().getIS(  );
////		oac = e.getOAC();
//		na = e.getMetricsValues().getNP();
//		nm = e.getMetricsValues().getNM();
//		app = e.getMetricsValues().getPPM();
//		total = is;// + oac;
//	}
//	
//	private void setMethodMetrics(Method m){
//		is = m.getMetricsValues().getIS(  );
////		oac = m.getOAC();
//		total = is;// + oac;
//	}
//	
//	private Star addStar(MyUser user){
//		Star star = null;
//
//		setMetrics(user.getObject());
//		
//		star = new Star(user);
//		star.addNetwork(snIS, zscoreIS.zScore(is));
////		star.addNetwork(snOAC, zscoreOAC.zScore(oac));
//		star.addNetwork(snTOTAL, zscoreTOTAL.zScore(total));
//		if ( !(user.getObject() instanceof Method) ){
//			star.addNetwork(snNA, zscoreNA.zScore(na));
//			star.addNetwork(snNM, zscoreNM.zScore(nm));
//			star.addNetwork(snAPP, zscoreAPP.zScore(app));
//		}
//		
//		star.addMouseListener( new MouseListener(){
//			public void mouseClicked(MouseEvent e) {
//				if ( e.getButton() == 1 && e.getClickCount() == 2 ) {
//					MetriX.getInstance().setSelectedTab(1);
//					new MyTreeMap().getTreeMap(((MyUser)((Star)e.getComponent()).getUser()).getObject());
//				}
//			}
//			public void mouseEntered(MouseEvent e){}
//			public void mouseExited(MouseEvent e){}
//			public void mousePressed(MouseEvent e){}
//			public void mouseReleased(MouseEvent e){}
//		});
//
//		String toolTip = "<html><table><tr  bgcolor=\"#BEBEBE\">" +
//				"<td><font color=\"#000000\"><b>";
//
//		if ( user.getObject() instanceof Package ) toolTip += Configurations.getLanguage().getString("word.package");
//		else if ( user.getObject() instanceof Entity ) toolTip += Configurations.getLanguage().getString("word.class"); 
//		else if ( user.getObject() instanceof Method ) toolTip += Configurations.getLanguage().getString("word.method");
//		
//		toolTip += "</b></font></td>" +
//				"<td><font color=\"#000000\">" + user.getObject().toString() + "</font></td></tr>" +
//				
//				"<tr bgcolor=\"#D3D3D3\">" +
//				"<td><li><b><font color=\"#000000\">IS</font></b></li></td>" +
//				"<td><font color=\"#000000\">"+ is +"</font></td></tr>" ;//+
//
////				"<tr bgcolor=\"#D3D3D3\">" +
////				"<td><li><b><font color=\"#000000\">OAC</font></b></li></td>" +
////				"<td><font color=\"#000000\">"+ oac +"</font></td></tr>" ;
//				
//		if ( !( user.getObject() instanceof Method ) ) 
//			toolTip += "<tr bgcolor=\"#D3D3D3\">" +
//				"<td><li><b><font color=\"#000000\">NA</font></b></li></td>" +
//				"<td><font color=\"#000000\">"+ na +"</font></td></tr>" +
//
//				"<tr bgcolor=\"#D3D3D3\">" +
//				"<td><li><b><font color=\"#000000\">NM</font></b></li></td>" +
//				"<td><font color=\"#000000\">"+ nm +"</font></td></tr>" +
//				
//				"<tr bgcolor=\"#D3D3D3\">" +
//				"<td><li><b><font color=\"#000000\">APP</font></b></li></td>" +
//				"<td><font color=\"#000000\">"+ app +"</font></td></tr>" ;
//				
//		toolTip +=	"<tr bgcolor=\"#D3D3D3\">" +
//				"<td><li><b><font color=\"#000000\">TOTAL</font></b></li></td>" +
//				"<td><font color=\"#000000\">"+ total +"</font></td></tr>" +
//				"</table></html>";
//		star.setToolTipText(toolTip);
//		return star;
//	}
//	
//	private JPanel getColorPanelStarPlot(){
//		if ( colorPanelStarPlot == null ){
//			colorPanelStarPlot = new JPanel();
//			colorPanelStarPlot.setLayout(new FlowLayout());
//			String[] metrics = new String[]{"IL", "IS", "TOTAL", "NA", "NM", "APP"};//"OAC", 
//			for ( int i = 0 ; i < getLabelColorsStarPlot().length ; i++){
//				colorPanelStarPlot.add( new JLabel( "<html><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + metrics[i] + "</b>:</html> " ));
//				colorPanelStarPlot.add( getLabelColorsStarPlot()[i] );
//			}
//		}
//		return colorPanelStarPlot;
//	}
//	
//	private JLabel[] getLabelColorsStarPlot(){
//		if ( labelColorsStarPlot == null ){
//			int size = colorsStarPlot.length ;
//			labelColorsStarPlot = new JLabel[ size ];
//			for ( int i = 0 ; i < size ; i++){
//				labelColorsStarPlot[i] = new JLabel();
//				labelColorsStarPlot[i].setBackground(colorsStarPlot[i]);
//				labelColorsStarPlot[i].setPreferredSize(new Dimension(30,15));
//				labelColorsStarPlot[i].setOpaque(true);
//			}
//		}
//		return labelColorsStarPlot;
//	}
//	
//	private class MyUser extends User{
//		
//		Object object = null;
//		
//		public MyUser( Object object ){ 
//			super(object.toString());
//			this.object = object;
//		}
//		
//		public MyUser( Object object, SargasProject sp  ){ 
//			super(object.toString(), sp);
//			this.object = object;
//		}
//
//		public Object getObject() {
//			return object;
//		}
//
////		public void setObject(Object object) {
////			this.object = object;
////		}
//	}
}