package br.ufpa.linc.MetriX.view.treemap;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Shape;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;

import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Method;
import br.ufpa.linc.MetriX.api.model.Package;
import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.view.prefuse.Display;
import br.ufpa.linc.MetriX.view.prefuse.Visualization;
import br.ufpa.linc.MetriX.view.prefuse.action.ActionList;
import br.ufpa.linc.MetriX.view.prefuse.action.RepaintAction;
import br.ufpa.linc.MetriX.view.prefuse.action.animate.ColorAnimator;
import br.ufpa.linc.MetriX.view.prefuse.action.assignment.ColorAction;
import br.ufpa.linc.MetriX.view.prefuse.action.layout.Layout;
import br.ufpa.linc.MetriX.view.prefuse.action.layout.graph.SquarifiedTreeMapLayout;
import br.ufpa.linc.MetriX.view.prefuse.controls.ControlAdapter;
import br.ufpa.linc.MetriX.view.prefuse.controls.ToolTipControl;
import br.ufpa.linc.MetriX.view.prefuse.data.Schema;
import br.ufpa.linc.MetriX.view.prefuse.data.Tree;
import br.ufpa.linc.MetriX.view.prefuse.data.expression.Predicate;
import br.ufpa.linc.MetriX.view.prefuse.data.expression.parser.ExpressionParser;
import br.ufpa.linc.MetriX.view.prefuse.data.query.SearchQueryBinding;
import br.ufpa.linc.MetriX.view.prefuse.render.AbstractShapeRenderer;
import br.ufpa.linc.MetriX.view.prefuse.render.DefaultRendererFactory;
import br.ufpa.linc.MetriX.view.prefuse.render.LabelRenderer;
import br.ufpa.linc.MetriX.view.prefuse.util.ColorLib;
import br.ufpa.linc.MetriX.view.prefuse.util.FontLib;
import br.ufpa.linc.MetriX.view.prefuse.util.PrefuseLib;
import br.ufpa.linc.MetriX.view.prefuse.util.UpdateListener;
import br.ufpa.linc.MetriX.view.prefuse.util.ui.JFastLabel;
import br.ufpa.linc.MetriX.view.prefuse.util.ui.JSearchPanel;
import br.ufpa.linc.MetriX.view.prefuse.visual.DecoratorItem;
import br.ufpa.linc.MetriX.view.prefuse.visual.NodeItem;
import br.ufpa.linc.MetriX.view.prefuse.visual.VisualItem;
import br.ufpa.linc.MetriX.view.prefuse.visual.VisualTree;
import br.ufpa.linc.MetriX.view.prefuse.visual.expression.InGroupPredicate;
import br.ufpa.linc.MetriX.view.prefuse.visual.sort.TreeDepthItemSorter;

public class TreeMapView extends Display {

	private static final long serialVersionUID = 5092097259385141170L;
	
	private JPanel panel = null;
	private ToolTipControl tooltip;
    private SearchQueryBinding searchQ;
	private final String treeString = "tree";
    private final String treeNodes = "tree.nodes";
    private final String treeEdges = "tree.edges";
    private final String labels = "labels";
    private final Schema LABEL_SCHEMA = PrefuseLib.getVisualItemSchema();
    private JSearchPanel search = null;
//    private JScrollPane jsp = null;
    private JPanel panelColors = null;
	private JLabel labelColors[] = null;
	private int colors[] = null;
	private boolean showComplexity;
	public static String _COLUMN_NAME,
				   _COLUMN_OBJECT,
				   _COLUMN_SEARCH,
				   _COLUMN_TOOLTIP,
				   _COLUMN_IL,
				   _COLUMN_IS,
				   _COLUMN_TOTAL,
				   _COLUMN_LEVEL,
				   _COLUMN_SIZE;
	private Tree tree = null;
	//0 = API, 1 = Package e 2 = Class
//	private int rootType;
	
	static{
			_COLUMN_NAME = "name";
			_COLUMN_OBJECT = "object";
			_COLUMN_SEARCH = "search";
			_COLUMN_TOOLTIP = "tooltip";
			_COLUMN_IL = "il";
			_COLUMN_IS = "is";
			_COLUMN_TOTAL = "total";
			_COLUMN_LEVEL = "level";
			_COLUMN_SIZE = "size";
	}
    
    public SearchQueryBinding getSearchQuery() {
        return searchQ;
    }
    
    public TreeMapView(){
    	super(new Visualization());
    	showComplexity = false;
    	colors = Configurations.getTreeMapColors();
    }
    
    public JPanel getTreeMap(){//final String label) {
    	LABEL_SCHEMA.setDefault(VisualItem.INTERACTIVE, false);
        LABEL_SCHEMA.setDefault(VisualItem.TEXTCOLOR, ColorLib.color(Color.WHITE));
        LABEL_SCHEMA.setDefault(VisualItem.FONT, FontLib.getFont("Tahoma",16));
        
    	tooltip = new ToolTipControl(_COLUMN_TOOLTIP);
    	ToolTipManager.sharedInstance().setDismissDelay(20000);
        tree = MetriX.getInstance().getTree();

//        Object o = ((Node) tree.getRoot().children().next()).get (_COLUMN_OBJECT);
//        if ( o instanceof API ) JOptionPane.showMessageDialog(null, "API");
//        else if ( o instanceof Package ) JOptionPane.showMessageDialog(null, "pacote");
//        else if ( o instanceof Entidade ) JOptionPane.showMessageDialog(null, "entidade");
        getTreeMap(tree);
        
        panel = new JPanel(new BorderLayout());
//        addControlListener(new ZoomControl());
//        addControlListener(new DragControl()); // drag items around
//        addControlListener(new PanControl()); 
//        addControlListener(new RotationControl());
        
//        addMouseWheelListener( new MouseWheelListener(){
//			public void mouseWheelMoved(MouseWheelEvent e) {
//				if ( e.isControlDown() )
//					if ( e.getWheelRotation() == 1 ) {
//						int w =  getSize().width;
//						int h =  getSize().height;
//
//						setSize( w+30, h+30 );
//						validate();
//					}
//					else {
//						int w =  getSize().width;
//						int h =  getSize().height;
//						System.out.println(jsp.getSize() + "  "+ w + "  " + h);
//						if ( jsp.getSize().getWidth() == w && jsp.getSize().getHeight() == h) return;
//						if ( jsp.getSize().getWidth() < w )	w -= 30;
//						if ( jsp.getSize().getHeight() < h )	h -= 30;
//						setSize( w, h );
//						validate();
//					}
//			}
//        });
        
        search = getSearchQuery().createSearchPanel();
        search.setShowResultCount(true);
        search.setBorder(BorderFactory.createEmptyBorder(5,5,4,0));
        search.setFont(FontLib.getFont("Tahoma", Font.PLAIN, 11));
        
        final JFastLabel title = new JFastLabel("                 ");
        title.setPreferredSize(new Dimension(350, 20));
        title.setVerticalAlignment(SwingConstants.BOTTOM);
        title.setBorder(BorderFactory.createEmptyBorder(3,0,0,0));
        title.setFont(FontLib.getFont("Tahoma", Font.PLAIN, 16));
//        Box box = UILib.getBox(new Component[]{title,search}, true, 10, 3, 0);
        
//        jsp = new JScrollPane();
//        jsp.setViewportView(this);
//        panel.add(jsp, BorderLayout.CENTER);
        panel.add(this, BorderLayout.CENTER);
//        panel.add(box, BorderLayout.SOUTH);
        panel.add( getPanelColors(), BorderLayout.SOUTH);
        
//        UILb.setColor(panel, Color.BLACK, Color.GRAY);
        return panel;
    }
    
    public JSearchPanel getSearch(){
    	return search;
    }
    
    public void getTreeMap(Tree t){
        // add the tree to the visualization
    	VisualTree vt = m_vis.addTree(treeString, t);

        m_vis.setVisible(treeEdges, null, false);

        // ensure that only leaf nodes are interactive
        Predicate noLeaf = (Predicate)ExpressionParser.parse("childcount()>0");
        m_vis.setInteractive(treeNodes, noLeaf, false);

        if ( MetriX.getInstance().isShowPackageName() ) {
            // add labels to the visualization
            // first create a filter to show labels only at top-level nodes
        	Predicate labelP = (Predicate)ExpressionParser.parse("treedepth()=1");
            // now create the labels as decorators of the nodes
        	m_vis.addDecorators(labels, treeNodes, labelP, LABEL_SCHEMA);
        }
        if ( MetriX.getInstance().isShowClassName() ) {
            // add labels to the visualization
            // first create a filter to show labels only at top-level nodes
        	Predicate labelC = (Predicate)ExpressionParser.parse("treedepth()=2");
            // now create the labels as decorators of the nodes
        	m_vis.addDecorators(labels, treeNodes, labelC, LABEL_SCHEMA);
        }
       	
       	// set up the renderers - one for nodes and one for labels
        DefaultRendererFactory rf = new DefaultRendererFactory();
        rf.add(new InGroupPredicate(treeNodes), new NodeRenderer());
        rf.add(new InGroupPredicate(labels), new LabelRenderer(_COLUMN_NAME));
        m_vis.setRendererFactory(rf);
        
        // border colors
        final ColorAction borderColor = new BorderColorAction(treeNodes);
        final ColorAction fillColor = new FillColorAction(treeNodes);

        // color settings
        ActionList colors = new ActionList();
        colors.add(fillColor);
        colors.add(borderColor);
        m_vis.putAction("colors", colors);
        
        // animate paint change
        ActionList animatePaint = new ActionList(100);
        animatePaint.add(new ColorAnimator(treeNodes));
        animatePaint.add(new RepaintAction());
        m_vis.putAction("animatePaint", animatePaint);
       
        // create the single filtering and layout action list
        ActionList layout = new ActionList();
        layout.add(new SquarifiedTreeMapLayout(treeString));
//        layout.add(new SquarifiedTreeMapLayout(treeString,5));
        layout.add(colors);
        layout.add(new RepaintAction());
        layout.add(new LabelLayout(labels));
        m_vis.putAction("layout", layout);
             
        // initialize our display
        setItemSorter(new TreeDepthItemSorter());
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
            	m_vis.run("layout");
            }
        });
        addControlListener(new ControlAdapter() {
            public void itemEntered(VisualItem item, MouseEvent e) {
            	tooltip.itemEntered(item, e);
                item.setStrokeColor(borderColor.getColor(item));
                item.getVisualization().repaint();
            }
            public void itemExited(VisualItem item, MouseEvent e) {
                item.setStrokeColor(item.getEndStrokeColor());
                item.getVisualization().repaint();
            }
            public void itemClicked(VisualItem item, MouseEvent e) {
            	/* ***************************************** 
            		if ( e.getClickCount() == 2 && e.getButton() == 1) {
            		
            		MetriX.getInstance().setSelectedTab(0);
            		Entity ent = (Entity)item.get(_COLUMN_OBJECT);
            		new MyTreeMap().getTreeMap(ent);
            		
            	}
            	if ( e.getButton() == 3) {
//            		if (e.isPopupTrigger()) {
            			PopUpMenu popup = new PopUpMenu((NodeItem)item, tree);
                        popup.show(e.getComponent(), e.getX(), e.getY());
//                    }
            	}
            	****************************************/
            } 
        });
        
        searchQ = new SearchQueryBinding( vt.getNodeTable(), _COLUMN_SEARCH);
        m_vis.addFocusGroup(Visualization.SEARCH_ITEMS, searchQ.getSearchSet());
        searchQ.getPredicate().addExpressionListener(new UpdateListener() {
            public void update(Object src) {
                m_vis.cancel("animatePaint");
                m_vis.run("colors");
                m_vis.run("animatePaint");
            }
        });
        
        setHighQuality(true);
    }
    
    /**
     * Set the stroke color for drawing treemap node outlines. A graded
     * grayscale ramp is used, with higer nodes in the tree drawn in
     * lighter shades of gray.
     */
    public class BorderColorAction extends ColorAction {
//    	private ColorMap cmap = new ColorMap(Configurations.getColors(), 0, 9);
        public BorderColorAction(String group) {
            super(group, VisualItem.STROKECOLOR);
        }
        
        public int getColor(VisualItem item) {
            NodeItem nitem = (NodeItem)item;

            BasicStroke strokeSearch = new BasicStroke(4);
            BasicStroke strokePackage = new BasicStroke(3);
            BasicStroke strokeClass = new BasicStroke(2);
            BasicStroke strokeMethod = new BasicStroke(0);
            
            if ( nitem.isHover() ) return 0;
            
            if ( m_vis.isInGroup(item, Visualization.SEARCH_ITEMS)) {
            	item.setStroke(strokeSearch);
            	return ColorLib.rgb(0,255,0);
            }
            
//            if ( nitem.getChildCount() < 3 )  return 0;// no fill for parent nodes

            if (nitem.get(_COLUMN_OBJECT) instanceof API) return ColorLib.rgb(127, 127, 127);

            if (nitem.get(_COLUMN_OBJECT) instanceof Package) {
            	item.setStroke(strokePackage);	
            	return ColorLib.rgb(0, 0, 0);
            }
            //class without methods
            if (nitem.get(_COLUMN_OBJECT) instanceof Entity) {
            	item.setStroke(strokeClass);
            	return ColorLib.rgb(255,255,255);
            }
            if (nitem.get(_COLUMN_OBJECT) instanceof Method){
	            item.setStroke(strokeMethod);
	            int[] palette = Configurations.getTreeMapColors();
	            int level = (Integer) nitem.get(_COLUMN_LEVEL);
	        	return palette[level];
            }
            return 0;
        }
    }
    
    /**
     * Set fill colors for treemap nodes. Search items are colored
     * in pink, while normal nodes are shaded according to their
     * depth in the tree.
     */
    public class FillColorAction extends ColorAction {
    	
//    	private ColorMap cmap = new ColorMap(Configurations.getColors(), 2, Configurations.getColors().length);

        public FillColorAction(String group) {
            super(group, VisualItem.FILLCOLOR);
        }
        
        public int getColor(VisualItem item) {
        	int[] palette = Configurations.getTreeMapColors();
//        	double value = 306001. / 256,
//        	metric = 0;
        	
            if ( item instanceof NodeItem ) {
                NodeItem nitem = (NodeItem)item;
//                if ( nitem.isHover() ) return item.getFillColor();
//                if ( m_vis.isInGroup(item, Visualization.SEARCH_ITEMS)) return ColorLib.rgb(0,255,0);
                
//                if (nitem.get(_COLUMN_OBJECT) instanceof API) return 0;
//
//                if (nitem.get(_COLUMN_OBJECT) instanceof Package) return ColorLib.rgb(0, 0, 0);

                //class with/without methods
                if (nitem.get(_COLUMN_OBJECT) instanceof Entity) return palette[nitem.getInt(_COLUMN_LEVEL)];
//
//                if ( nitem.get(_COLUMN_OBJECT) instanceof Metodo ) return 0;
            }
            return 0;
        }
        
    } // end of inner class TreeMapColorAction
    
    /**
     * Set label positions. Labels are assumed to be DecoratorItem instances,
     * decorating their respective nodes. The layout simply gets the bounds
     * of the decorated node and assigns the label coordinates to the center
     * of those bounds.
     */
    public class LabelLayout extends Layout {
        public LabelLayout(String group) {
            super(group);
        }
		public void run(double frac) {
            Iterator<?> iter = m_vis.items(m_group);
            while ( iter.hasNext() ) {
                DecoratorItem item = (DecoratorItem)iter.next();
                VisualItem node = item.getDecoratedItem();
                Rectangle2D bounds = node.getBounds();
                setX(item, null, bounds.getCenterX());
                setY(item, null, bounds.getCenterY());
            }
        }
    } // end of inner class LabelLayout
    
    /**
     * A renderer for treemap nodes. Draws simple rectangles, but defers
     * the bounds management to the layout.
     */
    public class NodeRenderer extends AbstractShapeRenderer {
        private Rectangle2D m_bounds = new Rectangle2D.Double();
        
        public NodeRenderer() {
            m_manageBounds = false;
        }

        protected Shape getRawShape(VisualItem item) {
//        	NodeItem nitem = (NodeItem)item;
//        	int itemSize ;
//        	if (nitem.get(_COLUMN_OBJECT) instanceof API){
//        		itemSize = getItemSize (((API)nitem.get(_COLUMN_OBJECT)).getAllEntities());
//        		m_bounds.setRect(item.getBounds().getX(), item.getBounds().getY(), itemSize, itemSize);
//           	}
//        	if (nitem.get(_COLUMN_OBJECT) instanceof Package){
//        		itemSize = getItemSize (((Package)nitem.get(_COLUMN_OBJECT)).getEntities());
//        		m_bounds.setRect(item.getBounds().getX(), item.getBounds().getY(), itemSize, itemSize);
//           	}
//        	if (nitem.get(_COLUMN_OBJECT) instanceof Entity){
//        		itemSize = ((Entity)nitem.get(_COLUMN_OBJECT)).getMethods().size();
//        		m_bounds.setRect(item.getBounds().getX(), item.getBounds().getY(), itemSize, itemSize);
//           	}
//        	if (nitem.get(_COLUMN_OBJECT) instanceof Method){
////        		m_bounds.setRect(item.getBounds().getX(), item.getBounds().getY(), itemNumDbl, itemNumDbl);
//           	}
////        	else 
        	m_bounds.setRect( item.getBounds() );
            
            return m_bounds;
        }        
    } // end of inner class NodeRenderer
    
//    private int getItemSize(List<Entity> entities){
//    	int itemSize = 0;
//		for (Entity e : entities) itemSize += e.getMethods().size();
//		return itemSize;
//    }
    private JPanel getPanelColors(){
		if ( panelColors == null ){
			panelColors = new JPanel();
			panelColors.setLayout(new FlowLayout());
			String lessComplexity = MetriX.getInstance().getDefineColor() == 0 ? Configurations.getString("message.lessComplexity") : Configurations.getString("message.lessCoupled");
			String moreComplexity = MetriX.getInstance().getDefineColor() == 0 ? Configurations.getString("message.moreComplexity") : Configurations.getString("message.moreCoupled");
			if ( showComplexity ) panelColors.add( new JLabel( "<html><b>" + Configurations.getString("message.Complexity") + "</b></html>" ));
			else panelColors.add( new JLabel( "<html><b>" + lessComplexity  + "</b></html>" ));
			for ( int i = 0 ; i < getLabelColors().length ; i++){
				panelColors.add( getLabelColors()[i] );
			}
			if ( !showComplexity ) panelColors.add( new JLabel( "<html><b>" + moreComplexity + "</b></html>" ));
		}
		return panelColors;
	}
	
	private JLabel[] getLabelColors(){
		if ( labelColors == null ){

			if ( showComplexity ) {
				int count = 0;
//				double[] metrics = Configurations.getDectril( 11 );
				double[] metrics = Configurations.getDectrilISRelative( MetriX.getInstance().getApi() );
				System.err.println("Olhar a classe br.ufpa.linc.MetriX.view.treemap.TreeMapView linha 431/432");
				System.err.println("A versão anterior usava a linha 429 ao inves da 430");
//				System.exit(0);
				int size = colors.length * 2;
				labelColors = new JLabel[ size ];
				int i = 0;
				while( i < size ){
					if ( i == 0 ) labelColors[i] = new JLabel((int)metrics[count] + " <");
					else labelColors[i] = new JLabel( "< " +(int)metrics[count] + " <");
					i++;
					labelColors[i] = new JLabel();
					labelColors[i].setBackground(new Color(colors[count]));
					labelColors[i].setPreferredSize(new Dimension(20,15));
					labelColors[i].setOpaque(true);
					i++;
					count++;
				}
			}
			else {			
				int size = colors.length ;
				labelColors = new JLabel[ size ];
				for ( int i = 0 ; i < size ; i++){
					labelColors[i] = new JLabel();
					labelColors[i].setBackground(new Color(colors[i]));
					labelColors[i].setPreferredSize(new Dimension(30,15));
					labelColors[i].setOpaque(true);
				}
			}
		}
		return labelColors;
	}
}
