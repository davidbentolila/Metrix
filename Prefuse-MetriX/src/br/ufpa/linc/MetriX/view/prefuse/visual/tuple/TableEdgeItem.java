package br.ufpa.linc.MetriX.view.prefuse.visual.tuple;

import br.ufpa.linc.MetriX.view.prefuse.data.Edge;
import br.ufpa.linc.MetriX.view.prefuse.data.Graph;
import br.ufpa.linc.MetriX.view.prefuse.data.Node;
import br.ufpa.linc.MetriX.view.prefuse.data.Table;
import br.ufpa.linc.MetriX.view.prefuse.visual.EdgeItem;
import br.ufpa.linc.MetriX.view.prefuse.visual.NodeItem;

/**
 * EdgeItem implementation that used data values from a backing
 * VisualTable of edges.
 *  
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public class TableEdgeItem extends TableVisualItem implements EdgeItem {

    protected Graph m_graph;
    
    /**
     * Initialize a new TableEdgeItem for the given graph, table, and row.
     * This method is used by the appropriate TupleManager instance, and
     * should not be called directly by client code, unless by a
     * client-supplied custom TupleManager.
     * @param table the backing VisualTable
     * @param graph the backing VisualGraph
     * @param row the row in the node table to which this Edge instance
     *  corresponds.
     */
    protected void init(Table table, Graph graph, int row) {
        m_table = table;
        m_graph = graph;
        m_row = m_table.isValidRow(row) ? row : -1;
    }
    
    /**
     * @see Edge#getGraph()
     */
    public Graph getGraph() {
        return m_graph;
    }
    
    /**
     * @see Edge#isDirected()
     */
    public boolean isDirected() {
        return m_graph.isDirected();
    }

    /**
     * @see Edge#getSourceNode()
     */
    public Node getSourceNode() {
        return m_graph.getSourceNode(this);
    }

    /**
     * @see Edge#getTargetNode()
     */
    public Node getTargetNode() {
        return m_graph.getTargetNode(this);
    }

    /**
     * @see Edge#getAdjacentNode(Node)
     */
    public Node getAdjacentNode(Node n) {
        return m_graph.getAdjacentNode(this, n);
    }
    
    /**
     * @see EdgeItem#getSourceItem()
     */
    public NodeItem getSourceItem() {
        return (NodeItem)getSourceNode();
    }

    /**
     * @see EdgeItem#getTargetItem()
     */
    public NodeItem getTargetItem() {
        return (NodeItem)getTargetNode();
    }

    /**
     * @see EdgeItem#getAdjacentItem(NodeItem)
     */
    public NodeItem getAdjacentItem(NodeItem n) {
        return (NodeItem)getAdjacentNode(n);
    }

} // end of class TableEdgeItem
