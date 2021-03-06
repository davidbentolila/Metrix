package br.ufpa.linc.MetriX.view.prefuse.visual.expression;

import br.ufpa.linc.MetriX.view.prefuse.data.expression.ColumnExpression;
import br.ufpa.linc.MetriX.view.prefuse.data.expression.NotPredicate;
import br.ufpa.linc.MetriX.view.prefuse.data.expression.Predicate;
import br.ufpa.linc.MetriX.view.prefuse.visual.VisualItem;

/**
 * Expression that indicates if an item's start visible flag is set.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public class StartVisiblePredicate extends ColumnExpression implements Predicate {

    /** Convenience instance for the start visible == true case. */
    public static final Predicate TRUE = new StartVisiblePredicate();
    /** Convenience instance for the start visible == false case. */
    public static final Predicate FALSE = new NotPredicate(TRUE);
    
    /**
     * Create a new StartVisiblePredicate.
     */
    public StartVisiblePredicate() {
        super(VisualItem.STARTVISIBLE);
    }

} // end of class StartVisiblePredicate
