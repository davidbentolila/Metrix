package br.ufpa.linc.MetriX.view.prefuse.util.collections;

/**
 * Abstract LiteralIterator implementation that supports an iteration over
 * int values. Subclasses need only implement the {@link #nextInt()} method.
 * The {@link #nextLong()}, {@link #nextFloat()}, and {@link #nextDouble()}
 * methods all simply cast the output of {@link #nextInt()}. The
 * {@link #next()} method simply wraps the output of {@link #nextInt()} in
 * an {@link java.lang.Integer} object.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public abstract class IntIterator extends AbstractLiteralIterator {

    /**
     * @see java.util.Iterator#next()
     */
    public Object next() {
        return new Integer(nextInt());
    }

    /**
     * @see LiteralIterator#isDoubleSupported()
     */
    public boolean isDoubleSupported() {
        return true;
    }

    /**
     * @see LiteralIterator#isFloatSupported()
     */
    public boolean isFloatSupported() {
        return true;
    }

    /**
     * @see LiteralIterator#isIntSupported()
     */
    public boolean isIntSupported() {
        return true;
    }

    /**
     * @see LiteralIterator#isLongSupported()
     */
    public boolean isLongSupported() {
        return true;
    }

    /**
     * @see LiteralIterator#nextDouble()
     */
    public double nextDouble() {
        return nextInt();
    }

    /**
     * @see LiteralIterator#nextFloat()
     */
    public float nextFloat() {
        return nextInt();
    }

    /**
     * @see LiteralIterator#nextLong()
     */
    public long nextLong() {
        return nextInt();
    }

    /**
     * @see LiteralIterator#nextInt()
     */
    public abstract int nextInt();
    
} // end of abstract class IntIterator
