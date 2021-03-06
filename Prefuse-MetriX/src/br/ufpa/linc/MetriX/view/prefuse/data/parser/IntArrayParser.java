package br.ufpa.linc.MetriX.view.prefuse.data.parser;

import java.util.StringTokenizer;

/**
 * DataParser instance the parses an array of int values from a text string.
 * Values are expected to be comma separated and can be within brackets,
 * parentheses, or curly braces.
 *  
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
@SuppressWarnings("rawtypes")
public class IntArrayParser implements DataParser {
    
    /**
     * Returns int[].class.
     * @see DataParser#getType()
     */
    public Class getType() {
        return int[].class;
    }
    
    /**
     * @see DataParser#format(java.lang.Object)
     */
    public String format(Object value) {
        if ( value == null ) return null;
        if ( !(value instanceof int[]) )
            throw new IllegalArgumentException(
              "This class can only format Objects of type int[].");
        
        int[] values = (int[])value;
        StringBuffer sbuf = new StringBuffer();
        sbuf.append('[');
        for ( int i=0; i<values.length; ++i ) {
            if ( i > 0 ) sbuf.append(", ");
            sbuf.append(values[i]);
        }
        sbuf.append(']');
        return sbuf.toString();
    }
    
    /**
     * @see DataParser#canParse(java.lang.String)
     */
    public boolean canParse(String text) {
        try {
            StringTokenizer st = new StringTokenizer(text, "\"[](){}, ");
            while ( st.hasMoreTokens() ) {
                Integer.parseInt(st.nextToken());
            }
            return true;
        } catch ( NumberFormatException e ) {
            return false;
        }
    }
    
    /**
     * Parse an int array from a text string.
     * @param text the text string to parse
     * @return the parsed integer array
     * @throws DataParseException if an error occurs during parsing
     */
    public Object parse(String text) throws DataParseException {
        try {
            StringTokenizer st = new StringTokenizer(text, "\"[](){}, ");
            int[] array = new int[st.countTokens()];
            for ( int i=0; st.hasMoreTokens(); ++i ) {
                String tok = st.nextToken();
                array[i] = Integer.parseInt(tok);
            }
            return array;
        } catch ( NumberFormatException e ) {
            throw new DataParseException(e);
        }
    }
    
} // end of class IntArrayParser
