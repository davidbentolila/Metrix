package br.ufpa.linc.MetriX.view.prefuse.data.expression;

import br.ufpa.linc.MetriX.view.prefuse.data.Tuple;
import br.ufpa.linc.MetriX.view.prefuse.data.event.ExpressionListener;
import br.ufpa.linc.MetriX.view.prefuse.util.collections.CopyOnWriteArrayList;

/**
 * Abstract base class for Expression implementations. Provides support for
 * listeners and defaults every Expression evaluation method to an
 * unsupported operation.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public abstract class AbstractExpression
    implements Expression, ExpressionListener
{
    private CopyOnWriteArrayList m_listeners = new CopyOnWriteArrayList();
    
    /**
     * @see Expression#visit(ExpressionVisitor)
     */
    public void visit(ExpressionVisitor v) {
        v.visitExpression(this);
    }

    /**
     * @see Expression#addExpressionListener(ExpressionListener)
     */
    public final void addExpressionListener(ExpressionListener lstnr) {
        if ( !m_listeners.contains(lstnr) ) {
            m_listeners.add(lstnr);
            addChildListeners();
        }
    }
    
    /**
     * @see Expression#removeExpressionListener(ExpressionListener)
     */
    public final void removeExpressionListener(ExpressionListener lstnr) {
        m_listeners.remove(lstnr);
        if ( m_listeners.size() == 0 )
            removeChildListeners();
    }

    /**
     * Indicates if any listeners are registered with this Expression.
     * @return true if listeners are registered, false otherwise
     */
    protected final boolean hasListeners() {
        return m_listeners != null && m_listeners.size() > 0;
    }
    
    /**
     * Fire an expression change.
     */
    protected final void fireExpressionChange() {
        Object[] lstnrs = m_listeners.getArray();
        for ( int i=0; i<lstnrs.length; ++i ) {
            ((ExpressionListener)lstnrs[i]).expressionChanged(this);
        }
    }
    
    /**
     * Add child listeners to catch and propagate sub-expression updates.
     */
    protected void addChildListeners() {
        // nothing to do
    }

    /**
     * Remove child listeners for sub-expression updates.
     */
    protected void removeChildListeners() {
        // nothing to do
    }

    /**
     * Relay an expression change event.
     * @see ExpressionListener#expressionChanged(Expression)
     */
    public void expressionChanged(Expression expr) {
        fireExpressionChange();
    }
    
    // ------------------------------------------------------------------------
    // Default Implementation
    
    /**
     * By default, throws an UnsupportedOperationException.
     * @see Expression#get(Tuple)
     */
    public Object get(Tuple t) {
        throw new UnsupportedOperationException();
    }

    /**
     * By default, throws an UnsupportedOperationException.
     * @see Expression#getInt(Tuple)
     */
    public int getInt(Tuple t) {
        throw new UnsupportedOperationException();
    }

    /**
     * By default, throws an UnsupportedOperationException.
     * @see Expression#getLong(Tuple)
     */
    public long getLong(Tuple t) {
        throw new UnsupportedOperationException();
    }

    /**
     * By default, throws an UnsupportedOperationException.
     * @see Expression#getFloat(Tuple)
     */
    public float getFloat(Tuple t) {
        throw new UnsupportedOperationException();
    }

    /**
     * By default, throws an UnsupportedOperationException.
     * @see Expression#getDouble(Tuple)
     */
    public double getDouble(Tuple t) {
        throw new UnsupportedOperationException();
    }

    /**
     * By default, throws an UnsupportedOperationException.
     * @see Expression#getBoolean(Tuple)
     */
    public boolean getBoolean(Tuple t) {
        throw new UnsupportedOperationException();
    }
    
} // end of abstract class AbstractExpression
