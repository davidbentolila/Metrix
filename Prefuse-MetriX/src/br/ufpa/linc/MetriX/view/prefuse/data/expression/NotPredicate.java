package br.ufpa.linc.MetriX.view.prefuse.data.expression;

import br.ufpa.linc.MetriX.view.prefuse.data.Tuple;

/**
 * Predicate representing the negation of another predicate.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public class NotPredicate extends AbstractPredicate {

    private Predicate m_predicate;
    
    /**
     * Create a new NotPredicate.
     * @param p the predicate to negate
     */
    public NotPredicate(Predicate p) {
        m_predicate = p;
    }
    
    /**
     * Get the negated predicate.
     * @return the negated predicate
     */
    public Predicate getPredicate() {
        return m_predicate;
    }
    
    /**
     * @see br.ufpa.linc.view.prefuse.data.expression.Expression#getBoolean(br.ufpa.linc.view.prefuse.data.Tuple)
     */
    public boolean getBoolean(Tuple t) {
        return !m_predicate.getBoolean(t);
    }

    /**
     * @see br.ufpa.linc.view.prefuse.data.expression.Expression#visit(br.ufpa.linc.view.prefuse.data.expression.ExpressionVisitor)
     */
    public void visit(ExpressionVisitor v) {
        v.visitExpression(this);
        v.down();
        m_predicate.visit(v);
        v.up();
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "NOT "+m_predicate.toString();
    }
    
    /**
     * @see br.ufpa.linc.view.prefuse.data.expression.AbstractExpression#addChildListeners()
     */
    protected void addChildListeners() {
        m_predicate.addExpressionListener(this);
    }
    
    /**
     * @see br.ufpa.linc.view.prefuse.data.expression.AbstractExpression#removeChildListeners()
     */
    protected void removeChildListeners() {
        m_predicate.removeExpressionListener(this);
    }
    
} // end of class NotPredicate