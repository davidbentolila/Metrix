package br.ufpa.linc.MetriX.view.prefuse.data.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import br.ufpa.linc.MetriX.view.prefuse.data.Graph;


/**
 * Abstract base class implementation of the GraphWriter interface. Provides
 * implementations for all but the
 * {@link GraphWriter#writeGraph(Graph, OutputStream)}
 * method.
 *  
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public abstract class AbstractGraphWriter implements GraphWriter {

    /**
     * @see GraphWriter#writeGraph(Graph, String)
     */
    public void writeGraph(Graph graph, String filename) throws DataIOException
    {
        writeGraph(graph, new File(filename));
    }

    /**
     * @see GraphWriter#writeGraph(Graph, File)
     */
    public void writeGraph(Graph graph, File f) throws DataIOException {
        try {
            writeGraph(graph, new FileOutputStream(f));
        } catch ( FileNotFoundException e ) {
            throw new DataIOException(e);
        }
    }

} // end of abstract class AbstractGraphReader
