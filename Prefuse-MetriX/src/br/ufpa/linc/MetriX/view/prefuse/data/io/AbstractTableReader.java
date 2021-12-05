package br.ufpa.linc.MetriX.view.prefuse.data.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import br.ufpa.linc.MetriX.view.prefuse.data.Table;
import br.ufpa.linc.MetriX.view.prefuse.util.io.IOLib;



/**
 * Abstract base class implementation of the TableReader interface. Provides
 * implementations for all but the
 * {@link TableReader#readTable(InputStream)} method. 
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public abstract class AbstractTableReader implements TableReader {

    /**
     * @see TableReader#readTable(String)
     */
    public Table readTable(String location) throws DataIOException
    {
        try {
            InputStream is = IOLib.streamFromString(location);
            if ( is == null )
                throw new DataIOException("Couldn't find " + location
                    + ". Not a valid file, URL, or resource locator.");
            return readTable(is);
        } catch ( IOException e ) {
            throw new DataIOException(e);
        }
    }

    /**
     * @see TableReader#readTable(URL)
     */
    public Table readTable(URL url) throws DataIOException {
        try {
            return readTable(url.openStream());
        } catch ( IOException e ) {
            throw new DataIOException(e);
        }
    }

    /**
     * @see TableReader#readTable(File)
     */
    public Table readTable(File f) throws DataIOException {
        try {
            return readTable(new FileInputStream(f));
        } catch ( FileNotFoundException e ) {
            throw new DataIOException(e);
        }
    }

} // end of abstract class AbstractTableReader
