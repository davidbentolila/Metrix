package br.ufpa.linc.MetriX.view.prefuse.data.expression;

import br.ufpa.linc.MetriX.view.prefuse.data.Schema;
import br.ufpa.linc.MetriX.view.prefuse.data.Tuple;

/**
 * Expression instance that returns the value stored in a Tuple data field.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
@SuppressWarnings("rawtypes")
public class ColumnExpression extends AbstractExpression implements Predicate {

    protected final String m_field;
    
    /**
     * Create a new ColumnExpression.
     * @param field the column / data field name to use
     */
    public ColumnExpression(String field) {
        m_field = field;
    }
    
    /**
     * Get the column / data field name used by this expression.
     * @return the column / data field name
     */
    public String getColumnName() {
        return m_field;
    }
    
    // ------------------------------------------------------------------------
    // Expression Interface
    
    /**
     * @see br.ufpa.linc.view.prefuse.data.expression.Expression#getType(br.ufpa.linc.view.prefuse.data.Schema)
     */
    public Class getType(Schema s) {
        return s.getColumnType(m_field);
    }

    /**
     * @see br.ufpa.linc.view.prefuse.data.expression.Expression#get(br.ufpa.linc.view.prefuse.data.Tuple)
     */
    public Object get(Tuple t) {
        return t.get(m_field);
    }

    /**
     * @see br.ufpa.linc.view.prefuse.data.expression.Expression#getInt(br.ufpa.linc.view.prefuse.data.Tuple)
     */
    public int getInt(Tuple t) {
        return t.getInt(m_field);
    }

    /**
     * @see br.ufpa.linc.view.prefuse.data.expression.Expression#getLong(br.ufpa.linc.view.prefuse.data.Tuple)
     */
    public long getLong(Tuple t) {
        return t.getLong(m_field);
    }

    /**
     * @see br.ufpa.linc.view.prefuse.data.expression.Expression#getFloat(br.ufpa.linc.view.prefuse.data.Tuple)
     */
    public float getFloat(Tuple t) {
        return t.getFloat(m_field);
    }

    /**
     * @see br.ufpa.linc.view.prefuse.data.expression.Expression#getDouble(br.ufpa.linc.view.prefuse.data.Tuple)
     */
    public double getDouble(Tuple t) {
        return t.getDouble(m_field);
    }

    /**
     * @see br.ufpa.linc.view.prefuse.data.expression.Expression#getBoolean(br.ufpa.linc.view.prefuse.data.Tuple)
     */
    public boolean getBoolean(Tuple t) {
        return t.getBoolean(m_field);
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "["+m_field+"]";
    }
    
} // end of class ColumnExpression
