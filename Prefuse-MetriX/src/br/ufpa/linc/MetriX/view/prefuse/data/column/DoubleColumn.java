package br.ufpa.linc.MetriX.view.prefuse.data.column;

import java.util.Arrays;

import br.ufpa.linc.MetriX.view.prefuse.data.DataReadOnlyException;
import br.ufpa.linc.MetriX.view.prefuse.data.DataTypeException;


/**
 * Column implementation for storing double values.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public class DoubleColumn extends AbstractColumn {

    private double[] m_values;
    private int      m_size;
    
    /**
     * Create a new empty DoubleColumn. 
     */
    public DoubleColumn() {
        this(0, 10, 0);
    }
    
    /**
     * Create a new DoubleColumn. 
     * @param nrows the initial size of the column
     */
    public DoubleColumn(int nrows) {
        this(nrows, nrows, 0);
    }
    
    /**
     * Create a new DoubleColumn. 
     * @param nrows the initial size of the column
     * @param capacity the initial capacity of the column
     * @param defaultValue the default value for the column
     */
    public DoubleColumn(int nrows, int capacity, double defaultValue) {
        super(double.class, new Double(defaultValue));
        if ( capacity < nrows ) {
            throw new IllegalArgumentException(
                "Capacity value can not be less than the row count.");
        }
        m_values = new double[capacity];
        Arrays.fill(m_values, defaultValue);
        m_size = nrows;
    }
    
    // ------------------------------------------------------------------------
    // Column Metadata
    
    /**
     * @see Column#getRowCount()
     */
    public int getRowCount() {
        return m_size;
    }
    
    /**
     * @see Column#setMaximumRow(int)
     */
    public void setMaximumRow(int nrows) {
        if ( nrows > m_values.length ) {
            int capacity = Math.max((3*m_values.length)/2 + 1, nrows);
            double[] values = new double[capacity];
            System.arraycopy(m_values, 0, values, 0, m_size);
            Arrays.fill(values, m_size, capacity,
                    ((Double)m_defaultValue).doubleValue());
            m_values = values;
        }
        m_size = nrows;
    }

    // ------------------------------------------------------------------------
    // Data Access Methods    
    
    /**
     * @see Column#get(int)
     */
    public Object get(int row) {
        return new Double(getDouble(row));
    }

    /**
     * @see Column#set(java.lang.Object, int)
     */
    public void set(Object val, int row) throws DataTypeException {
        if ( m_readOnly ) {
            throw new DataReadOnlyException();
        } else if ( val != null ) {
            if ( val instanceof Number ) {
                setDouble(((Number)val).doubleValue(), row);
            } else if ( val instanceof String ) {
                setString((String)val, row);
            } else {
                throw new DataTypeException(val.getClass());
            }
        } else {
            throw new DataTypeException("Column does not accept null values");
        }
    }

    // ------------------------------------------------------------------------
    // Data Type Convenience Methods
    
    /**
     * @see AbstractColumn#getDouble(int)
     */
    public double getDouble(int row) throws DataTypeException {
        if ( row < 0 || row > m_size ) {
            throw new IllegalArgumentException("Row index out of bounds: "+row);
        }
        return m_values[row];
    }

    /**
     * @see AbstractColumn#setDouble(double, int)
     */
    public void setDouble(double val, int row) throws DataTypeException {
        if ( m_readOnly ) {
            throw new DataReadOnlyException();
        } else if ( row < 0 || row >= m_size ) {
            throw new IllegalArgumentException("Row index out of bounds: "+row);
        }
        // get the previous value
        double prev = m_values[row];
        
        // exit early if no change
        if ( prev == val ) return;
        
        // set the new value
        m_values[row] = val;
        
        // fire a change event
        fireColumnEvent(row, prev);
    }
    
//    /**
//     * @see prefuse.data.column.AbstractColumn#getString(int)
//     */
//    public String getString(int row) throws DataTypeException {
//        return String.valueOf(getDouble(row));
//    }
//
//    /**
//     * @see prefuse.data.column.AbstractColumn#setString(java.lang.String, int)
//     */
//    public void setString(String val, int row) throws DataTypeException {
//        setDouble(Double.parseDouble(val), row);
//    }

    // ------------------------------------------------------------------------
    
    /**
     * @see Column#getInt(int)
     */
    public int getInt(int row) throws DataTypeException {
        return (int)getDouble(row);
    }
    
    /**
     * @see Column#setInt(int, int)
     */
    public void setInt(int val, int row) throws DataTypeException {
        setDouble(val, row);
    }
    
    /**
     * @see Column#getLong(int)
     */
    public long getLong(int row) throws DataTypeException {
        return (long)getDouble(row);
    }
    
    /**
     * @see Column#setLong(long, int)
     */
    public void setLong(long val, int row) throws DataTypeException {
        setDouble(val, row);
    }
    
    /**
     * @see Column#getFloat(int)
     */
    public float getFloat(int row) throws DataTypeException {
        return (float)getDouble(row);
    }
    
    /**
     * @see Column#setFloat(float, int)
     */
    public void setFloat(float val, int row) throws DataTypeException {
        setDouble(val, row);
    }
    
} // end of class DoubleColumn
