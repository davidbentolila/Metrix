package br.ufpa.linc.MetriX.view.prefuse.visual.tuple;

import br.ufpa.linc.MetriX.view.prefuse.data.Graph;
import br.ufpa.linc.MetriX.view.prefuse.data.Table;
import br.ufpa.linc.MetriX.view.prefuse.visual.DecoratorItem;
import br.ufpa.linc.MetriX.view.prefuse.visual.VisualItem;
import br.ufpa.linc.MetriX.view.prefuse.visual.VisualTable;

/**
 * DecoratorItem implementation that uses data values from a backing
 * VisualTable.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public class TableDecoratorItem extends TableVisualItem 
    implements DecoratorItem
{   
    /**
     * Initialize a new TableDecoratorItem for the given table and row. This
     * method is used by the appropriate TupleManager instance, and should
     * not be called directly by client code, unless by a client-supplied
     * custom TupleManager.
     * @param table the data Table
     * @param graph ignored by this class
     * @param row the table row index
     */
    protected void init(Table table, Graph graph, int row) {
        m_table = table;
        m_row = m_table.isValidRow(row) ? row : -1;
    }
    
    /**
     * @see DecoratorItem#getDecoratedItem()
     */
    public VisualItem getDecoratedItem() {
        VisualTable vt = (VisualTable)getTable();
        int prow = vt.getParentRow(getRow());
        return (VisualItem)vt.getParentTable().getTuple(prow);
    }

} // end of class TableDecoratorItem
