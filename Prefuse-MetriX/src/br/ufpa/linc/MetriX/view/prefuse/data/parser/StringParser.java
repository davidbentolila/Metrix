package br.ufpa.linc.MetriX.view.prefuse.data.parser;

/**
 * DataParser instance that "parses" a String value from a text string, this
 * is the default fallback parser, which simply returns the string value
 * to be parsed.
 *  
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
@SuppressWarnings("rawtypes")
public class StringParser implements DataParser {
    
    /**
     * Returns String.class.
     * @see DataParser#getType()
     */
    public Class getType() {
        return String.class;
    }
    
    /**
     * @see DataParser#format(java.lang.Object)
     */
    public String format(Object value) {
        if ( value == null ) return null;
        if ( !(value instanceof String) )
            throw new IllegalArgumentException(
              "This class can only format Objects of type String.");
        return (String)value;
    }
    
    /**
     * @see DataParser#canParse(java.lang.String)
     */
    public boolean canParse(String text) {
        return true;
    }
    
    /**
     * @see DataParser#parse(java.lang.String)
     */
    public Object parse(String text) throws DataParseException {
        return text;
    }
    
    /**
     * Simply returns the input string.
     * @param text the text string to "parse"
     * @return the input text string
     * @throws DataParseException never actually throws an exception
     */
    public String parseString(String text) throws DataParseException {
        return text;
    }
    
} // end of class StringParser
