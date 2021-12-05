package br.ufpa.linc.MetriX.view.graph;

//import java.awt.BasicStroke;
//import java.awt.Font;
//import java.awt.Shape;
//import java.awt.Stroke;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.geom.Point2D;

//import org.apache.commons.collections15.Transformer;
//
//import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
//import edu.uci.ics.jung.graph.Graph;
//import edu.uci.ics.jung.visualization.VisualizationViewer;
//import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
//import edu.uci.ics.jung.visualization.decorators.AbstractVertexShapeTransformer;
/**
 * Using no more dependency graph visualization
 */
public class MyTransformer {
//
//	public final static class VertexFontTransformer<V> implements Transformer<MyVertex,Font> {
//	    Font f = new Font("Helvetica", Font.PLAIN, 14);
//	    public Font transform(final MyVertex mv) {
//            return f;
//	    }
//    }
//    
//	@SuppressWarnings("hiding")
//	public final static class VertexShapeSizeAspect<MyVertex ,E> extends AbstractVertexShapeTransformer <MyVertex> implements Transformer<MyVertex,Shape>  {
//		Graph<MyVertex,E> graph = null;
//        public VertexShapeSizeAspect(Graph<MyVertex,E> graphIn) {
//        	this.graph = graphIn;
//            setSizeTransformer( new Transformer<MyVertex,Integer>() {
//				public Integer transform(MyVertex mv) {
//						return 30 + graph.getIncidentEdges(mv).size(); 
//					}
//			});
//        }
//        
//		public Shape transform(MyVertex v) {
//			return factory.getEllipse(v);
////			if ( ((MyVertex)v).getObject() instanceof Entidade ) return factory.getEllipse(v);
////			else return factory.getRoundRectangle(v);
//		}
//    }
//    
//    public class EdgeStroke<V,E> implements Transformer<MyEdge,Stroke>
//    {
//    	protected Stroke heavy = new BasicStroke(3);
//        protected Stroke medium = new BasicStroke(2);
//        protected Stroke light = new BasicStroke(1);
//    	public Stroke transform(MyEdge arg0) {
//			return medium;
//		}
//    }
//    
//    public class VertexStroke<V,E> implements Transformer<MyVertex,Stroke>
//    {        
//    	protected Stroke heavy = new BasicStroke(3);
//        protected Stroke medium = new BasicStroke(2);
//        protected Stroke light = new BasicStroke(1);
//        
//		public Stroke transform(MyVertex arg0) {
//			return medium;
//		}
//    }
//
//    public class VertexTooltip<V,E> implements Transformer<MyVertex,String>
//    {        
//		public String transform(MyVertex mv) {
//			return mv.getToolTip();
//		}
//    }
//    
//    public class GraphMousePlugin extends PickingGraphMousePlugin<MyVertex, MyEdge> implements MouseListener {
//
//        public void mouseReleased(MouseEvent e){
//        	mouseAction(e);
//        }
//
//        public void mouseClicked(MouseEvent e){
//        	mouseAction(e);
//        }
//
//        @SuppressWarnings("unchecked")
//		private void mouseAction(MouseEvent e){
//        	VisualizationViewer<MyVertex, MyEdge> vv = (VisualizationViewer<MyVertex, MyEdge>) e.getSource();
//        	Iterable<MyVertex> vertices = null;
//            Point2D p = e.getPoint();
//            GraphElementAccessor<MyVertex, MyEdge> pickSupport = vv.getPickSupport();
//            if (pickSupport != null) {
//            	vertices = vv.getGraphLayout().getGraph().getVertices();
//            	for (MyVertex mv : vertices) {
//					mv.setStatus(0);
//				}
//                MyVertex v = pickSupport.getVertex(vv.getGraphLayout(), p.getX(), p.getY());
//                if (v != null) {
//                	v.setStatus(1);
//                	vertices = vv.getGraphLayout().getGraph().getNeighbors(v);
//                	for (MyVertex mv : vertices) {
//						mv.setStatus(2);
//					}
//                	vv.repaint();
//                }
//            }
//        }
//    }

}
