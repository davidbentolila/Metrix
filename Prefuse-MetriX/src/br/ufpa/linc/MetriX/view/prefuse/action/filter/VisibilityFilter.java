package br.ufpa.linc.MetriX.view.prefuse.action.filter;

import java.util.Iterator;

import br.ufpa.linc.MetriX.view.prefuse.Visualization;
import br.ufpa.linc.MetriX.view.prefuse.action.Action;
import br.ufpa.linc.MetriX.view.prefuse.action.GroupAction;
import br.ufpa.linc.MetriX.view.prefuse.data.expression.OrPredicate;
import br.ufpa.linc.MetriX.view.prefuse.data.expression.Predicate;
import br.ufpa.linc.MetriX.view.prefuse.util.PrefuseLib;
import br.ufpa.linc.MetriX.view.prefuse.visual.VisualItem;
import br.ufpa.linc.MetriX.view.prefuse.visual.expression.VisiblePredicate;


/**
 * Filter Action that sets visible all items that meet a given Predicate
 * condition and sets all other items invisible.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public class VisibilityFilter extends GroupAction {
    
    private Predicate m_filter;
    private Predicate m_predicate;
    
    /**
     * Create a new VisibilityFilter.
     * @param p the test predicate used to determine visibility
     */
    public VisibilityFilter(Predicate p) {
        setPredicate(p);
    }

    /**
     * Create a new VisibilityFilter.
     * @param group the data group to process
     * @param p the test predicate used to determine visibility
     */
    public VisibilityFilter(String group, Predicate p) {
        super(group);
        setPredicate(p);
    }

    /**
     * Create a new VisibilityFilter.
     * @param vis the Visualization to process
     * @param group the data group to process
     * @param p the test predicate used to determine visibility
     */
    public VisibilityFilter(Visualization vis, String group, Predicate p) {
        super(vis, group);
        setPredicate(p);
    }

    /**
     * Set the test predicate used to determine visibility.
     * @param p the test predicate to set
     */
    protected void setPredicate(Predicate p) {
        m_predicate = p;
        m_filter = new OrPredicate(p, VisiblePredicate.TRUE);
    }
    
    /**
     * @see Action#run(double)
     */
    public void run(double frac) {
        Iterator<?> items = m_vis.items(m_group, m_filter);
        while ( items.hasNext() ) {
            VisualItem item = (VisualItem)items.next();
            PrefuseLib.updateVisible(item, m_predicate.getBoolean(item));
        }
    }

} // end of class VisibilityAction
