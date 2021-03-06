package br.ufpa.linc.MetriX.view.prefuse.data.util;

import br.ufpa.linc.MetriX.view.prefuse.data.CascadedTable;
import br.ufpa.linc.MetriX.view.prefuse.data.Table;
import br.ufpa.linc.MetriX.view.prefuse.data.column.IntColumn;
import br.ufpa.linc.MetriX.view.prefuse.util.collections.IntIntSortedMap;
import br.ufpa.linc.MetriX.view.prefuse.util.collections.IntIntTreeMap;

/**
 * RowManager that additionally manages mappings between the managed
 * rows and those of a parent table.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public class FilteredRowManager extends RowManager {

    protected IntColumn       m_childToParent;
    protected IntIntSortedMap m_parentToChild;
    
    /**
     * Create a new FilteredRowManager.
     * @param table the table to manage
     */
    public FilteredRowManager(Table table) {
        super(table);
        m_childToParent = new IntColumn(table.getRowCount());
        m_parentToChild = new IntIntTreeMap(false);
        clear();
    }
    
    /**
     * @see RowManager#clear()
     */
    public void clear() {
        super.clear();
        m_parentToChild.clear();
        for ( int i=0; i<m_childToParent.getRowCount(); ++i ) {
            m_childToParent.setInt(-1, i);
        }
    }
    
    /**
     * Add a new row backed by the given parent row.
     * @param parentRow the backing parent row
     * @return the index of the newly added row
     */
    public int addRow(int parentRow) {
        int r = super.addRow();
        put(r, parentRow);
        return r;
    }
    
    /**
     * @see RowManager#releaseRow(int)
     */
    public boolean releaseRow(int row) {
        if ( super.releaseRow(row) ) {
            remove(row);
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * @see RowManager#getColumnRow(int, int)
     */
    public int getColumnRow(int row, int col) {
        return ((CascadedTable)m_table).getParentTable()
                    .getColumnRow(getParentRow(row), col);
    }
    
    /**
     * @see RowManager#getTableRow(int, int)
     */
    public int getTableRow(int columnRow, int col) {
        return getChildRow(columnRow);
    }
    
    // ------------------------------------------------------------------------
    
    /**
     * Given a row managed by this manager, return the corresponding row
     * in the parent table.
     * @param childRow a row managed by this manager
     * @return the parent table row
     */
    public int getParentRow(int childRow) {
        if ( childRow >= m_childToParent.getRowCount() ) {
            return -1;
        } else {
            return m_childToParent.getInt(childRow);
        }
    }

    /**
     * Given a row in the parent table, return the corresponding row managed
     * by this manager.
     * @param parentRow a row in the parent table
     * @return the managed row corresponding to the parent row
     */
    public int getChildRow(int parentRow) {
        int val = m_parentToChild.get(parentRow);
        return ( val == Integer.MIN_VALUE ? -1 : val );
    }
    
    /**
     * Add a mapping between the given managed row and parent row.
     * @param childRow a row managed by this manager
     * @param parentRow a row in the parent table
     */
    public void put(int childRow, int parentRow) {
        // ensure capacity of IntColumn
        if ( childRow >= m_childToParent.getRowCount() )
            m_childToParent.setMaximumRow(childRow+1);
        
        // add mapping
        m_childToParent.setInt(parentRow, childRow);
        m_parentToChild.put(parentRow, childRow);
    }
    
    /**
     * Remove a mapping between the given managed row and the corresponding
     * parent row.
     * @param childRow a row managed by this manager
     */
    public void remove(int childRow) {
        int parentRow = m_childToParent.getInt(childRow);
        m_childToParent.setInt(-1, childRow);
        m_parentToChild.remove(parentRow);
    }

} // end of class FilteredRowManager
