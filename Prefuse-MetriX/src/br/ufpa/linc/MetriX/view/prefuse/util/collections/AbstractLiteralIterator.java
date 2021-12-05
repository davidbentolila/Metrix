package br.ufpa.linc.MetriX.view.prefuse.util.collections;

/**
 * Abstract base class for a LiteralIterator implementations.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public abstract class AbstractLiteralIterator implements LiteralIterator {

    /**
     * @see LiteralIterator#nextInt()
     */
    public int nextInt() {
        throw new UnsupportedOperationException("int type unsupported");
    }

    /**
     * @see LiteralIterator#nextLong()
     */
    public long nextLong() {
        throw new UnsupportedOperationException("long type unsupported");
    }

    /**
     * @see LiteralIterator#nextFloat()
     */
    public float nextFloat() {
        throw new UnsupportedOperationException("float type unsupported");
    }

    /**
     * @see LiteralIterator#nextDouble()
     */
    public double nextDouble() {
        throw new UnsupportedOperationException("double type unsupported");
    }

    /**
     * @see LiteralIterator#nextBoolean()
     */
    public boolean nextBoolean() {
        throw new UnsupportedOperationException("boolean type unsupported");
    }

    /**
     * @see LiteralIterator#isBooleanSupported()
     */
    public boolean isBooleanSupported() {
        return false;
    }

    /**
     * @see LiteralIterator#isDoubleSupported()
     */
    public boolean isDoubleSupported() {
        return false;
    }

    /**
     * @see LiteralIterator#isFloatSupported()
     */
    public boolean isFloatSupported() {
        return false;
    }

    /**
     * @see LiteralIterator#isIntSupported()
     */
    public boolean isIntSupported() {
        return false;
    }

    /**
     * @see LiteralIterator#isLongSupported()
     */
    public boolean isLongSupported() {
        return false;
    }
    
} // end of class AbstractLiteralIterator
