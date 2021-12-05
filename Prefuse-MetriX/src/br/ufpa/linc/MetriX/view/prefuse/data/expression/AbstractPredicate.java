package br.ufpa.linc.MetriX.view.prefuse.data.expression;

import br.ufpa.linc.MetriX.view.prefuse.data.Schema;
import br.ufpa.linc.MetriX.view.prefuse.data.Tuple;

/**
 * Abstract base class for dedicated Predicate instances.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractPredicate extends AbstractExpression 
    implements Predicate
{

    /**
     * Returns boolean.class.
     * @see br.ufpa.linc.view.prefuse.data.expression.Expression#getType(br.ufpa.linc.view.prefuse.data.Schema)
     */
    public Class getType(Schema s) {
        return boolean.class;
    }

    /**
     * Returns the wrapper Object type for the result of
     * {@link Expression#getBoolean(Tuple)}.
     * @see br.ufpa.linc.view.prefuse.data.expression.Expression#get(br.ufpa.linc.view.prefuse.data.Tuple)
     */
    public Object get(Tuple t) {
        return ( getBoolean(t) ? Boolean.TRUE : Boolean.FALSE );
    }
    
} // end of class AbstractPredicate
