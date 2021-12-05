package br.ufpa.linc.MetriX.view.prefuse.action.animate;

import java.awt.Font;

import br.ufpa.linc.MetriX.view.prefuse.action.ItemAction;
import br.ufpa.linc.MetriX.view.prefuse.util.FontLib;
import br.ufpa.linc.MetriX.view.prefuse.visual.VisualItem;



/**
 * Animator that interpolates between starting and ending Fonts for VisualItems
 * during an animation. Font sizes are interpolated linearly. If the
 * animation fraction is under 0.5, the face and style of the starting
 * font are used, otherwise the face and style of the second font are
 * applied.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public class FontAnimator extends ItemAction {

    /**
     * Create a new FontAnimator that processes all data groups.
     */
    public FontAnimator() {
        super();
    }

    /**
     * Create a new FontAnimator that processes the specified group.
     * @param group the data group to process.
     */
    public FontAnimator(String group) {
        super(group);
    }

    /**
     * @see br.ufpa.linc.view.prefuse.action.ItemAction#process(br.ufpa.linc.view.prefuse.visual.VisualItem, double)
     */
    public void process(VisualItem item, double frac) {
        Font f1 = item.getStartFont(), f2 = item.getEndFont();
        item.setFont(FontLib.getIntermediateFont(f1,f2,frac));
    }

} // end of class FontAnimator
