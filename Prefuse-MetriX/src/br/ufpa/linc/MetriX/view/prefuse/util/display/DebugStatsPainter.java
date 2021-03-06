package br.ufpa.linc.MetriX.view.prefuse.util.display;

import java.awt.Graphics2D;

import br.ufpa.linc.MetriX.view.prefuse.Display;
import br.ufpa.linc.MetriX.view.prefuse.util.PrefuseLib;


/**
 * PinatListener that paints useful debugging statistics over a prefuse
 * display. This includes the current frame rate, the number of visible
 * items, memory usage, and display navigation information.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public class DebugStatsPainter implements PaintListener {

    /**
     * Does nothing.
     * @see PaintListener#prePaint(Display, java.awt.Graphics2D)
     */
    public void prePaint(Display d, Graphics2D g) {
        
    }
    
    /**
     * Prints a debugging statistics string in the Display.
     * @see PaintListener#postPaint(Display, java.awt.Graphics2D)
     */
    public void postPaint(Display d, Graphics2D g) {
        g.setFont(d.getFont());
        g.setColor(d.getForeground());
        g.drawString(PrefuseLib.getDisplayStats(d), 5, 15);
    }

} // end of class DebugStatsPainter
