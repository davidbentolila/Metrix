package br.ufpa.linc.MetriX.view.prefuse.visual.expression;

import br.ufpa.linc.MetriX.view.prefuse.data.Schema;
import br.ufpa.linc.MetriX.view.prefuse.data.Tuple;
import br.ufpa.linc.MetriX.view.prefuse.data.expression.Expression;
import br.ufpa.linc.MetriX.view.prefuse.data.expression.Function;
import br.ufpa.linc.MetriX.view.prefuse.data.expression.Predicate;
import br.ufpa.linc.MetriX.view.prefuse.visual.VisualItem;

/**
 * Expression that indicates if an item is currently a member of a particular
 * data group. The data group name is provided by a String-valued
 * sub-expression.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
@SuppressWarnings("rawtypes")
public class InGroupPredicate extends GroupExpression implements Predicate {
    
    /**
     * Create a new InGroupPredicate.
     */
    public InGroupPredicate() {
    }
    
    /**
     * Create a new InGroupPredicate.
     * @param group @param group the data group name to use as a parameter
     */
    public InGroupPredicate(String group) {
        super(group);
    }
    
    /**
     * @see Expression#get(Tuple)
     */
    public Object get(Tuple t) {
        return getBoolean(t) ? Boolean.TRUE : Boolean.FALSE;
    }
    
    /**
     * @see Expression#getBoolean(Tuple)
     */
    public boolean getBoolean(Tuple t) {
        if ( !(t instanceof VisualItem) )
            return false;
        
        String group = getGroup(t);
        if ( group == null ) {
            return false;
        }
        VisualItem item = (VisualItem)t;
        return item.getVisualization().isInGroup(item, group);
    }

    /**
     * @see Function#getNome()
     */
    public String getName() {
        return "INGROUP";
    }

    /**
     * @see Expression#getType(Schema)
     */
    public Class getType(Schema s) {
        return boolean.class;
    }
    
} // end of class InGroupPredicate
