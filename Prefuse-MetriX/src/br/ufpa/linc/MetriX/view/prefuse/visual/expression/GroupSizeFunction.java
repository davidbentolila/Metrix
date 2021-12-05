package br.ufpa.linc.MetriX.view.prefuse.visual.expression;

import br.ufpa.linc.MetriX.view.prefuse.data.Schema;
import br.ufpa.linc.MetriX.view.prefuse.data.Tuple;
import br.ufpa.linc.MetriX.view.prefuse.data.expression.Expression;
import br.ufpa.linc.MetriX.view.prefuse.data.expression.Function;
import br.ufpa.linc.MetriX.view.prefuse.data.tuple.TupleSet;
import br.ufpa.linc.MetriX.view.prefuse.visual.VisualItem;

/**
 * GroupExpression that returns the size of a data group.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
@SuppressWarnings("rawtypes")
public class GroupSizeFunction extends GroupExpression {

    /**
     * Create a new, uninitialized GroupSizeFunction. The parameter for
     * this Function needs to be set.
     */
    public GroupSizeFunction() {
    }
    
    /**
     * Create a new GroupSizeFunction using the given data group name
     * as the Function parameter.
     * @param group the data group name to use as a parameter
     */
    public GroupSizeFunction(String group) {
        super(group);
    }
    
    /**
     * @see Function#getNome()
     */
    public String getName() {
        return "GROUPSIZE";
    }

    /**
     * @see Expression#getType(Schema)
     */
    public Class getType(Schema s) {
        return int.class;
    }

    /**
     * @see Expression#get(Tuple)
     */
    public Object get(Tuple t) {
        return new Integer(getInt(t));
    }

    /**
     * @see Expression#getDouble(Tuple)
     */
    public double getDouble(Tuple t) {
        return getInt(t);
    }

    /**
     * @see Expression#getFloat(Tuple)
     */
    public float getFloat(Tuple t) {
        return getInt(t);
    }

    /**
     * @see Expression#getInt(Tuple)
     */
    public int getInt(Tuple t) {
        String group = getGroup(t);
        if ( group == null ) { return -1; }
        TupleSet ts = ((VisualItem)t).getVisualization().getGroup(group);
        return ( ts==null ? 0 : ts.getTupleCount() );
    }

    /**
     * @see Expression#getLong(Tuple)
     */
    public long getLong(Tuple t) {
        return getInt(t);
    }

} // end of class GroupSizeFunction
