package br.ufpa.linc.MetriX.view.prefuse.action.layout.graph;

import br.ufpa.linc.MetriX.view.prefuse.action.layout.Layout;
import br.ufpa.linc.MetriX.view.prefuse.data.Graph;
import br.ufpa.linc.MetriX.view.prefuse.data.Tree;
import br.ufpa.linc.MetriX.view.prefuse.data.tuple.TupleSet;
import br.ufpa.linc.MetriX.view.prefuse.visual.NodeItem;

/**
 * Abstract base class providing convenience methods for tree layout algorithms.
 *
 * @version 1.0
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public abstract class TreeLayout extends Layout {

    protected NodeItem m_root;

    /**
     * Create a new TreeLayout.
     */
    public TreeLayout() {
        super();
    }

    /**
     * Create a new TreeLayout.
     * @param group the data group to layout. This must resolve to a graph
     * instance, otherwise an exception will result when subclasses attempt
     * to retrieve the layout root.
     */
    public TreeLayout(String group) {
        super(group);
    }
    
    // ------------------------------------------------------------------------

    /**
     * Explicitly set the node to use as the layout root.
     * @param root the node to use as the root.  A null value is legal, and
     * indicates that the root of the spanning tree of the backing graph will
     * be used as the layout root. If the node is not a member of this layout's
     * data group, an exception will be thrown.
     * @throws IllegalArgumentException if the provided root is not a member of
     * this layout's data group.
     */
    public void setLayoutRoot(NodeItem root) {
        if ( !root.isInGroup(m_group) )
            throw new IllegalArgumentException("Input node is not a member "
                    + "of this layout's data group");
        m_root = root;
    }
    
    /**
     * Return the NodeItem to use as the root for this tree layout.
     * @return the root node to use for this tree layout.
     * @throws IllegalStateException if the action's data group does not
     * resolve to a {@link br.ufpa.linc.view.prefuse.data.Graph} instance.
     */
    public NodeItem getLayoutRoot() {
        if ( m_root != null )
            return m_root;
        
        TupleSet ts = m_vis.getGroup(m_group);
        if ( ts instanceof Graph ) {
            Tree tree = ((Graph)ts).getSpanningTree();
            return (NodeItem)tree.getRoot();
        } else {
            throw new IllegalStateException("This action's data group does" +
                    "not resolve to a Graph instance.");
        }
    }

} // end of abstract class TreeLayout
