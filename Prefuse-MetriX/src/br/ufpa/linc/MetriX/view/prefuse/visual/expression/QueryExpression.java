package br.ufpa.linc.MetriX.view.prefuse.visual.expression;

import br.ufpa.linc.MetriX.view.prefuse.Visualization;
import br.ufpa.linc.MetriX.view.prefuse.data.Schema;
import br.ufpa.linc.MetriX.view.prefuse.data.Tuple;
import br.ufpa.linc.MetriX.view.prefuse.data.expression.Expression;
import br.ufpa.linc.MetriX.view.prefuse.data.expression.Function;
import br.ufpa.linc.MetriX.view.prefuse.data.search.SearchTupleSet;
import br.ufpa.linc.MetriX.view.prefuse.visual.VisualItem;

/**
 * Expression that returns the current query string of a data group of the type
 * {@link SearchTupleSet}. The data group name is provided
 * by a String-valued sub-expression.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
@SuppressWarnings("rawtypes")
public class QueryExpression extends GroupExpression {

    /**
     * Create a new QueryExpression.
     */
    public QueryExpression() {
        super();
    }
    
    /**
     * Create a new {@link QueryExpression}.
     * @param group @param group the data group name to use as a parameter
     */
    public QueryExpression(String group) {
        super(group);
    }
    
    /**
     * @see Function#getName()
     */
    public String getName() {
        return "QUERY";
    }

    /**
     * @see Expression#getType(Schema)
     */
	public Class getType(Schema s) {
        return String.class;
    }
    
    /**
     * @see Expression#get(Tuple)
     */
    public Object get(Tuple t) {
        VisualItem item = (VisualItem)t;
        Visualization vis = item.getVisualization();
        String group = getGroup(t);
        SearchTupleSet sts = (SearchTupleSet)vis.getGroup(group);
        return sts.getQuery();
    }

} // end of class QueryExpression
