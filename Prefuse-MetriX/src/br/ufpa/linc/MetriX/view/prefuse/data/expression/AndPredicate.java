package br.ufpa.linc.MetriX.view.prefuse.data.expression;

import java.util.Iterator;

import br.ufpa.linc.MetriX.view.prefuse.data.Tuple;


/**
 * Predicate representing an "and" clause of sub-predicates.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
@SuppressWarnings("rawtypes")
public class AndPredicate extends CompositePredicate {
    
    /**
     * Create an empty AndPredicate. Empty AndPredicates return false
     * by default.
     */
    public AndPredicate() {
    }
    
    /**
     * Create a new AndPredicate.
     * @param p1 the sole clause of this predicate
     */
    public AndPredicate(Predicate p1) {
        add(p1);
    }
    
    /**
     * Create a new AndPredicate.
     * @param p1 the first clause of this predicate
     * @param p2 the second clause of this predicate
     */
    public AndPredicate(Predicate p1, Predicate p2) {
        super(p1, p2);
    }
    
    /**
     * @see br.ufpa.linc.view.prefuse.data.expression.Expression#getBoolean(br.ufpa.linc.view.prefuse.data.Tuple)
     */
    public boolean getBoolean(Tuple t) {
        if ( m_clauses.size() == 0 )
            return false;
        
        Iterator iter = m_clauses.iterator();
        while ( iter.hasNext() ) {
            Predicate p = (Predicate)iter.next();
            if ( !p.getBoolean(t) ) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return ( size() == 0 ? "FALSE" : toString("AND") );
    }

} // end of class AndPredicate
