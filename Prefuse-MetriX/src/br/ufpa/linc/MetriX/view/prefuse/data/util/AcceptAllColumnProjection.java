package br.ufpa.linc.MetriX.view.prefuse.data.util;

import br.ufpa.linc.MetriX.view.prefuse.data.column.Column;

/**
 * ColumnProjection that simply includes all columns.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public class AcceptAllColumnProjection extends AbstractColumnProjection {

    /**
     * Always returns true, accepting all columns.
     * @see ColumnProjection#include(Column, java.lang.String)
     */
    public boolean include(Column col, String name) {
        return true;
    }

} // end of class AcceptAllColumnProjection
