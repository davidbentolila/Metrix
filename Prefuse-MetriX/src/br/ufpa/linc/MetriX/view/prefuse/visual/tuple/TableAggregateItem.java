/**
 * Copyright (c) 2004-2006 Regents of the University of California.
 * See "license-prefuse.txt" for licensing terms.
 */
package br.ufpa.linc.MetriX.view.prefuse.visual.tuple;

import java.util.Iterator;

import br.ufpa.linc.MetriX.view.prefuse.data.Graph;
import br.ufpa.linc.MetriX.view.prefuse.data.Node;
import br.ufpa.linc.MetriX.view.prefuse.data.Table;
import br.ufpa.linc.MetriX.view.prefuse.data.expression.Predicate;
import br.ufpa.linc.MetriX.view.prefuse.data.util.FilterIterator;
import br.ufpa.linc.MetriX.view.prefuse.visual.AggregateItem;
import br.ufpa.linc.MetriX.view.prefuse.visual.AggregateTable;
import br.ufpa.linc.MetriX.view.prefuse.visual.VisualItem;


/**
 * AggregateItem implementation that uses data values from a backing
 * AggregateTable.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
@SuppressWarnings("rawtypes")
public class TableAggregateItem extends TableVisualItem 
    implements AggregateItem
{   
    /**
     * Initialize a new TableAggregateItem for the given table and row. This
     * method is used by the appropriate TupleManager instance, and should not
     * be called directly by client code, unless by a client-supplied custom
     * TupleManager.
     * @param table the data Table
     * @param graph ignored by this class
     * @param row the table row index
     */
    protected void init(Table table, Graph graph, int row) {
        m_table = table;
        m_row = m_table.isValidRow(row) ? row : -1;
    }

    /**
     * @see AggregateItem#getAggregateSize()
     */
    public int getAggregateSize() {
        return ((AggregateTable)m_table).getAggregateSize(m_row);
    }

    /**
     * @see AggregateItem#containsItem(VisualItem)
     */
    public boolean containsItem(Node item) {
        return ((AggregateTable)m_table).aggregateContains(m_row, item);
    }

    /**
     * @see AggregateItem#addItem(VisualItem)
     */
    public void addItem(VisualItem item) {
        ((AggregateTable)m_table).addToAggregate(m_row, item);
    }
    
    public void addItem(Node item) {
        ((AggregateTable)m_table).addToAggregate(m_row, item);
    }

    /**
     * @see AggregateItem#removeItem(VisualItem)
     */
    public void removeItem(Node item) {
        ((AggregateTable)m_table).removeFromAggregate(m_row, item);
    }

    /**
     * @see AggregateItem#removeAllItems()
     */
    public void removeAllItems() {
        ((AggregateTable)m_table).removeAllFromAggregate(m_row);
    }

    /**
     * @see AggregateItem#items()
     */
    public Iterator items() {
        return ((AggregateTable)m_table).aggregatedTuples(m_row);
    }
    
    /**
     * @see AggregateItem#items()
     */
    public Iterator items(Predicate filter) {
        return new FilterIterator(
            ((AggregateTable)m_table).aggregatedTuples(m_row), filter);
    }

	public boolean containsItem(VisualItem item) {
		return false;
	}

	public void removeItem(VisualItem item) {
	}

} // end of class TableAggregateItem
