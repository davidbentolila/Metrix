package br.ufpa.linc.MetriX.view.graph;

//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.Paint;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.swing.Icon;
//import javax.swing.ImageIcon;
//import javax.swing.JLabel;
import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//
//import org.apache.commons.collections15.Transformer;
//
//import br.ufpa.linc.MetriX.api.model.Entity;
//import br.ufpa.linc.MetriX.api.model.Method;
//import br.ufpa.linc.MetriX.configurations.Configurations;
//import br.ufpa.linc.MetriX.view.graph.MyTransformer.EdgeStroke;
//import br.ufpa.linc.MetriX.view.graph.MyTransformer.VertexFontTransformer;
//import br.ufpa.linc.MetriX.view.graph.MyTransformer.VertexShapeSizeAspect;
//import br.ufpa.linc.MetriX.view.graph.MyTransformer.VertexStroke;
//import br.ufpa.linc.MetriX.view.graph.MyTransformer.VertexTooltip;
//import edu.uci.ics.jung.graph.DirectedSparseGraph;
//import edu.uci.ics.jung.graph.Graph;
//import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
//import edu.uci.ics.jung.visualization.LayeredIcon;
//import edu.uci.ics.jung.visualization.VisualizationModel;
//import edu.uci.ics.jung.visualization.VisualizationViewer;
//import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
//import edu.uci.ics.jung.visualization.decorators.DefaultVertexIconTransformer;
//import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
//import edu.uci.ics.jung.visualization.renderers.Renderer;
//import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
/**
 * Using no more dependency graph visualization
 */
public class MyGraph extends JPanel{
//
//	/**
//	 * 
//	 */
	private static final long serialVersionUID = 8677461363359659966L;
//	
//	private Graph<MyVertex, MyEdge> graph = null;//, graph1 = null, graph2 = null;
//	private VisualizationViewer<MyVertex, MyEdge> visualization = null;//, visualization1 = null, visualization2 = null;
//	private GraphZoomScrollPane scrollPane = null;//, scrollPane1 = null, scrollPane2 = null;;
//	private DefaultModalGraphMouse<MyVertex, MyEdge> gm = null;
//	
//	/* *************************************************
//	private Layout<MyVertex, MyEdge> frLayout = null;
//	private Layout<MyVertex, MyEdge> circleLayout = null;
//	private Layout<MyVertex, MyEdge> springLayout = null;
//	private Layout<MyVertex, MyEdge> kkLayout = null;
//	private Layout<MyVertex, MyEdge> isomLayout = null;
//	******************************************/
//	private JPanel panelColors = null;
//	private Color[] groupColor = null;
//	private List<MyVertex> vertexList = null; //getAll vertex network a and network b
//	private List<MyEdge> edgeList = null;//getAll edge that are showing
//	private List<String> packageList = null;
//	private MyTransformer myTransformer = null;
//	private Icon[] icon = null;
//	private Map<MyVertex,Icon> iconMap = null;
//	private String search = "";
//	
//	public MyGraph(){
//		super();
//		groupColor = Configurations.getGraphColors(); 
//		getIcons();
//		iconMap = new HashMap<MyVertex,Icon>();
//		/* **************************
//		 * MetriX.getInstance().setMyGraph(this);
//		 */
//		initGraph();
//	}
//	
//	private void getIcons(){
//	 	icon = new Icon[]{
//	 		new LayeredIcon(new ImageIcon( getClass().getResource("/images/class24.png")).getImage()),
//	 		new LayeredIcon(new ImageIcon( getClass().getResource("/images/interface24.png")).getImage()),
//	 		new LayeredIcon(new ImageIcon( getClass().getResource("/images/method24.png")).getImage())
//	 	};
//	}
//	
//	private void initGraph(){
//		graph = new DirectedSparseGraph<MyVertex, MyEdge>();
//		/* *****************************************
//		circleLayout = new CircleLayout<MyVertex, MyEdge>(graph);
//		frLayout = new FRLayout<MyVertex, MyEdge>(graph);
//		springLayout = new SpringLayout<MyVertex, MyEdge>(graph);
//		kkLayout = new KKLayout<MyVertex, MyEdge>(graph);
//		isomLayout = new ISOMLayout<MyVertex, MyEdge>(graph);
//		*********************************************/
//	}
//
//	public MyVertex existVertex( Entity e, Method m ){
//		if ( vertexList == null ) return null;
//		String search = e.getFullName();
//		if (  m != null ) search += ":"+ m.getNome();
//
//		for (MyVertex mv : vertexList)
//			if (  mv.getData().equals( search ) ) return mv;
//
//		return null;
//	}
//	
//	public int getPackageId(String packageName){
//		int count = 0;
//		if ( packageList == null ) {
//			packageList = new ArrayList<String>();
//			packageList.add(packageName);
//		}
//		else {
//			for (String p : packageList) {
//				if ( p.equals(packageName)) return count;
//				count++;
//			}
//			packageList.add(packageName);
//		}
//		
//		getPanelColors().add( getLabelColors(getGroupID(packageList.size()-1)) );
//		getPanelColors().add( new JLabel( "<html><b>" + packageName + "</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</html>" ));
//		return packageList.size()-1;
//	}
//	
//	public boolean existEdge( MyVertex v1, MyVertex v2 ){
//		if ( edgeList == null ) return false;
//		for (MyEdge me : edgeList) if ( me.getV1() == v1 && me.getV2() == v2 ) return true;
//		return false;
//	}
//	
//	public void addVertex( MyVertex vertex ){
//		if ( vertexList == null ) vertexList = new ArrayList<MyVertex>();
//		int id = getPackageId(((Entity)vertex.getEntity()).getPackage().getNome());
//		vertex.setGroupID(id);
//		vertexList.add( vertex );
////		if ( vertex.getObject() instanceof Classe ) iconMap.put(vertex, icon[0]);
////		else if ( vertex.getObject() instanceof Interface ) iconMap.put(vertex, icon[1]);
//		if ( vertex.getObject() instanceof Method ) iconMap.put(vertex, icon[2]);
//	}
//	
//	public void addEdge( MyEdge me ){
//		if ( edgeList == null ) edgeList = new ArrayList<MyEdge>();
//		if ( !existEdge( me.getV1(), me.getV2() )) edgeList.add( me );
//	}
//	
//	private VisualizationViewer<MyVertex, MyEdge> getVisualization(){
//		EdgeStroke<MyVertex, MyEdge> es = null;
//		VisualizationModel<MyVertex,MyEdge> visualizationModel = null;
//		VertexShapeSizeAspect<MyVertex,MyEdge> vssa = null;
//		VertexFontTransformer<MyVertex> vft = null;
//		VertexStroke<MyVertex, MyEdge> vs = null;
//		Position position = null;
//		Transformer<MyVertex, Paint> vertexPaint = null;
//		DefaultVertexIconTransformer<MyVertex> vertexIconFunction = null;
//		VertexTooltip<MyVertex, MyEdge> tooltip = null;
//
//		myTransformer = new MyTransformer();
//		/* ***************************************
//		 * visualizationModel= new DefaultVisualizationModel<MyVertex,MyEdge>(getlayout( graph ));
//		 */
//		visualization = new VisualizationViewer<MyVertex, MyEdge>( visualizationModel, null );
//		gm = new DefaultModalGraphMouse<MyVertex, MyEdge>();
//		gm.add( myTransformer.new GraphMousePlugin());
//
//		es = myTransformer.new EdgeStroke<MyVertex, MyEdge>();
//		vssa = new VertexShapeSizeAspect<MyVertex, MyEdge>( graph );
//		vs = myTransformer.new VertexStroke<MyVertex, MyEdge>();
//		tooltip = myTransformer.new VertexTooltip<MyVertex, MyEdge>();
//		vertexIconFunction = new DefaultVertexIconTransformer<MyVertex>();
//        vertexIconFunction.setIconMap(iconMap);
//        
//		vft = new VertexFontTransformer<MyVertex>();
//		vertexPaint = new Transformer<MyVertex,Paint>() {
//		    public Paint transform(MyVertex mv) {
//		    	if ( mv.getData().equals(search)) return getVertexColor(12);
//		    	else if (mv.getStatus() == 1 ) return Color.black;
//		    	else if (mv.getStatus() == 2 ) return Color.CYAN;
//		    	return getVertexColor(mv.getGroupID());
//		    }
//		};
//		
//		/* **********************************	
//		 * 	gm.setMode( getMouseStyle() );
//		 */
//
//		position = Renderer.VertexLabel.Position.N;
//		
//		visualization.setBackground(Color.white);
////		visualization.setVertexToolTipTransformer(new ToStringLabeller<MyVertex>());
//		visualization.setVertexToolTipTransformer(tooltip);
//		visualization.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<MyVertex>());
//		visualization.getRenderContext().setVertexShapeTransformer(vssa);
//		visualization.getRenderContext().setVertexFontTransformer(vft);
//		visualization.getRenderContext().setVertexStrokeTransformer(vs);
//		visualization.getRenderer().getVertexLabelRenderer().setPosition(position);
////		visualization.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);
//		visualization.getRenderContext().setEdgeStrokeTransformer(es);
//		visualization.setGraphMouse( gm );
//		visualization.getRenderContext().setVertexIconTransformer(vertexIconFunction);
//		visualization.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
//		
//		return visualization;
//	}
//	
//	/* **********************
//	public Layout<MyVertex, MyEdge> getlayout(Graph<MyVertex, MyEdge> g){
//		
//		String sLayout = MetriX.getInstance().getGraphLayout();
//		if ( sLayout.equals("FR") ) {
//			frLayout.setGraph(g);
//			return frLayout;
//		}
//		
//		if ( sLayout.equals("SPRING") ) {
//			springLayout.setGraph(g);
//			return springLayout;
//		}
//		
//		if ( sLayout.equals("KK") ) {
//			kkLayout.setGraph(g);
//			return kkLayout;
//		}
//		
//		if ( sLayout.equals("ISOM") ) {
//			isomLayout.setGraph(g);
//			return isomLayout;
//		}
//		
//		circleLayout.setGraph(g);
//		return circleLayout;
//	}
//	
//	
////	public Mode getMouseStyle(){
//		
//		return MetriX.getInstance().getMouseMode();
////		if ( main.getMouseStyle().equals("PICKING") ) return ModalGraphMouse.Mode.PICKING;
////		else if ( main.getMouseStyle().equals("EDITING") ) return ModalGraphMouse.Mode.EDITING;
////		else if ( main.getMouseStyle().equals("ANNOTATING") ) return ModalGraphMouse.Mode.ANNOTATING;
////		return ModalGraphMouse.Mode.TRANSFORMING;
////	}
//	
//	************************/
//	
//	
//	private Color getVertexColor(int group){
//		return groupColor[getGroupID(group)];
//	}
//	
//	
//	public int getGroupID(int group){
//		int idG = -1;
//		if ( group > groupColor.length-1 ) {
//			while ( group > groupColor.length-1 ) {
//				group--;
//				idG++;
//				if ( idG > groupColor.length-1 ) idG = 0;
//			}
//			return idG;
////			return (group % (groupColor.length-1))-1;
//		}
//		else return group;
//	}
//	
//	public void updateMouse(){
//		if ( visualization == null ) return;
//		gm = new DefaultModalGraphMouse<MyVertex, MyEdge>();
//		/* **********************************
//		 * gm.setMode( getMouseStyle() );
//		 */
//		visualization.setGraphMouse(gm);
//		visualization.validate();
//	}
//	
//	/* ***********************
//	
//	public void updateLayout(){
//		if ( visualization == null ) return;
//		visualization.setGraphLayout( getlayout( graph ) );
//		visualization.validate();
//	}
//	 **************************/
//	
//	public String getSearch() {
//		return search;
//	}
//
//	public void setSearch(String search) {
//		this.search = search;
//		visualization.repaint();
//	}
//
//	public void createView() {
//		if ( vertexList != null ) for (MyVertex mv : vertexList) graph.addVertex(mv);
//		if ( edgeList != null ) for (MyEdge me : edgeList) graph.addEdge(me, me.getV1(), me.getV2());
//		scrollPane = new GraphZoomScrollPane( getVisualization() );
//		setLayout(new BorderLayout() );
//		
//		add(scrollPane, BorderLayout.CENTER);
//		JScrollPane jsp = new JScrollPane();
//		jsp.setViewportView(getPanelColors());
//		jsp.setMinimumSize(new Dimension(getWidth(), 40));
//		jsp.setPreferredSize(new Dimension(getWidth(), 40));
//		add(jsp, BorderLayout.SOUTH);
//	}
//	
//	private JLabel getLabelColors(int id){
//		JLabel labelColor = new JLabel();
//		labelColor.setBackground(groupColor[id]);
//		labelColor.setPreferredSize(new Dimension(30,15));
//		labelColor.setOpaque(true);
//		return labelColor;
//	}
//	
//	
//	private JPanel getPanelColors(){
//		if ( panelColors == null ){
//			panelColors = new JPanel();
//			panelColors.setLayout(new FlowLayout());
//		}
//		return panelColors;
//	}
}