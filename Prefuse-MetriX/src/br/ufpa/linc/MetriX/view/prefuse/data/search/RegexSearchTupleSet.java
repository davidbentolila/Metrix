package br.ufpa.linc.MetriX.view.prefuse.data.search;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import br.ufpa.linc.MetriX.view.prefuse.data.Tuple;
import br.ufpa.linc.MetriX.view.prefuse.data.query.SearchQueryBinding;
import br.ufpa.linc.MetriX.view.prefuse.data.tuple.DefaultTupleSet;
import br.ufpa.linc.MetriX.view.prefuse.data.tuple.TupleSet;
import br.ufpa.linc.MetriX.view.prefuse.util.StringLib;


/**
 * SearchTupleSet implementation that treats the query as a regular expression
 * to match against all indexed Tuple data fields.
 * The regular expression engine provided by the
 * standard Java libraries
 * ({@link java.util.regex.Pattern java.util.regex.Pattern}) is used; please
 * refer to the documentation for that class for more about the regular
 * expression syntax.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 * @see SearchQueryBinding
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class RegexSearchTupleSet extends SearchTupleSet {
    
    private String m_query = "";
    private boolean m_caseSensitive;
    private LinkedHashMap m_source = new LinkedHashMap();
    
    /**
     * Create a new, case-insensitive regular expression search tuple set.
     */
    public RegexSearchTupleSet() {
        this(false);
    }
    
    /**
     * Create a new regular expression search tuple set.
     * @param caseSensitive true to make the indexing case sensitive, false
     * otherwise.
     */
    public RegexSearchTupleSet(boolean caseSensitive) {
        m_caseSensitive = caseSensitive;
    }
    
    /**
     * @see SearchTupleSet#getQuery()
     */
    public String getQuery() {
        return m_query;
    }

    /**
     * @see SearchTupleSet#search(java.lang.String)
     */
    public void search(String query) {
        if ( query == null )
            query = "";
        if ( !m_caseSensitive )
            query = query.toLowerCase();
        if ( query.equals(m_query) )
            return;
        
        Pattern pattern = null;
        try {
            pattern = Pattern.compile(query);
        } catch ( Exception e ) {
            Logger logger = Logger.getLogger(this.getClass().getName());
            logger.warning("Pattern compile failed."
                    + "\n" + StringLib.getStackTrace(e));
            return;
        }
        
        Tuple[] rem = clearInternal();    
        m_query = query;
        Iterator fields = m_source.keySet().iterator();
        while ( fields.hasNext() ) {
            String field = (String)fields.next();
            TupleSet ts = (TupleSet)m_source.get(field);
            
            Iterator tuples = ts.tuples();
            while ( tuples.hasNext() ) {
                Tuple t = (Tuple)tuples.next();
                String text = t.getString(field);
                if ( !m_caseSensitive )
                    text = text.toLowerCase();
                
                if ( pattern.matcher(text).matches() )
                    addInternal(t);
            }
        }
        Tuple[] add = getTupleCount() > 0 ? toArray() : null;
        fireTupleEvent(add, rem);
    }

    /**
     * @see SearchTupleSet#index(Tuple, java.lang.String)
     */
    public void index(Tuple t, String field) {
        TupleSet ts = (TupleSet)m_source.get(field);
        if ( ts == null ) {
            ts = new DefaultTupleSet();
            m_source.put(field, ts);
        }
        ts.addTuple(t);
    }

    /**
     * @see SearchTupleSet#unindex(Tuple, java.lang.String)
     */
    public void unindex(Tuple t, String field) {
        TupleSet ts = (TupleSet)m_source.get(field);
        if ( ts != null ) {
            ts.removeTuple(t);
        }
    }

    /**
     * Returns true, as unidexing is supported by this class.
     * @see SearchTupleSet#isUnindexSupported()
     */
    public boolean isUnindexSupported() {
        return true;
    }
    
    /**
     * Removes all search hits and clears out the index.
     * @see TupleSet#clear()
     */
    public void clear() {
        m_source.clear();
        super.clear();
    }

} // end of class RegexSearchTupleSet
