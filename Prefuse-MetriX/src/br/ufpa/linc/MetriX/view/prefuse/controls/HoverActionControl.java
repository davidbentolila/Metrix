package br.ufpa.linc.MetriX.view.prefuse.controls;

import java.awt.event.MouseEvent;

import br.ufpa.linc.MetriX.view.prefuse.visual.VisualItem;


/**
 * Control that executes an action when the mouse passes over an item.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public class HoverActionControl extends ControlAdapter {

    private String m_action;
   
    /**
     * Create a new HoverActionControl.
     * @param action the action to run upon mouse-over. The action is run
     * both upon entering and upon exiting the item.
     */
    public HoverActionControl(String action) {
        m_action = action;
    }
    
    /**
     * @see Control#itemEntered(VisualItem, java.awt.event.MouseEvent)
     */
    public void itemEntered(VisualItem item, MouseEvent e) {
        item.getVisualization().run(m_action);
    }

    /**
     * @see Control#itemExited(VisualItem, java.awt.event.MouseEvent)
     */
    public void itemExited(VisualItem item, MouseEvent e) {
        item.getVisualization().run(m_action);
    }

} // end of class HoverActionControl
