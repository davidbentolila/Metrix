package br.ufpa.linc.MetriX.view.prefuse.data.expression;

import br.ufpa.linc.MetriX.view.prefuse.data.Schema;
import br.ufpa.linc.MetriX.view.prefuse.data.Tuple;

/**
 * Literal expression of a boolean value.
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
@SuppressWarnings("rawtypes")
public class BooleanLiteral extends Literal implements Predicate
{
    /** The true boolean literal. */
    public static final BooleanLiteral TRUE = new BooleanLiteral(true);
    /** The false boolean literal. */
    public static final BooleanLiteral FALSE = new BooleanLiteral(false);
    
    private final boolean m_value;
    
    /**
     * Create a new BooleanLiteral.
     * @param b the boolean value
     */
    public BooleanLiteral(boolean b) {
        m_value = b;
    }

    /**
     * @see br.ufpa.linc.view.prefuse.data.expression.Expression#getBoolean(br.ufpa.linc.view.prefuse.data.Tuple)
     */
    public boolean getBoolean(Tuple tuple) {
        return m_value;
    }

    /**
     * @see br.ufpa.linc.view.prefuse.data.expression.Expression#getType(br.ufpa.linc.view.prefuse.data.Schema)
     */
    public Class getType(Schema s) {
        return boolean.class;
    }

    /**
     * @see br.ufpa.linc.view.prefuse.data.expression.Expression#get(br.ufpa.linc.view.prefuse.data.Tuple)
     */
    public Object get(Tuple t) {
        return ( getBoolean(t) ? Boolean.TRUE : Boolean.FALSE );
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return String.valueOf(m_value).toUpperCase();
    }
    
} // end of class BooleanLiteral
