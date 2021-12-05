package br.ufpa.linc.MetriX.view.prefuse.visual.expression;

import br.ufpa.linc.MetriX.view.prefuse.data.expression.ColumnExpression;
import br.ufpa.linc.MetriX.view.prefuse.data.expression.Expression;
import br.ufpa.linc.MetriX.view.prefuse.data.expression.Function;
import br.ufpa.linc.MetriX.view.prefuse.data.expression.NotPredicate;
import br.ufpa.linc.MetriX.view.prefuse.data.expression.Predicate;
import br.ufpa.linc.MetriX.view.prefuse.visual.VisualItem;

/**
 * Expression that indicates if an item's visible flag is set.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public class VisiblePredicate extends ColumnExpression
    implements Predicate, Function
{

    /** Convenience instance for the visible == true case. */
    public static final Predicate TRUE = new VisiblePredicate();
    /** Convenience instance for the visible == false case. */
    public static final Predicate FALSE = new NotPredicate(TRUE);
    
    /**
     * Create a new VisiblePredicate.
     */
    public VisiblePredicate() {
        super(VisualItem.VISIBLE);
    }

    /**
     * @see Function#getNome()
     */
    public String getName() {
        return "VISIBLE";
    }

    /**
     * @see Function#addParameter(Expression)
     */
    public void addParameter(Expression e) {
        throw new IllegalStateException("This function takes 0 parameters");
    }

    /**
     * @see Function#getParameterCount()
     */
    public int getParameterCount() {
        return 0;
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return getName()+"()";
    }

} // end of class VisiblePredicate
