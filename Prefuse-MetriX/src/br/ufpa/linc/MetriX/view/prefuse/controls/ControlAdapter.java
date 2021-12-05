package br.ufpa.linc.MetriX.view.prefuse.controls;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import br.ufpa.linc.MetriX.view.prefuse.visual.VisualItem;



/**
 * Adapter class for processing prefuse interface events. Subclasses can
 * override the desired methods to perform user interface event handling.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public class ControlAdapter implements Control {

    private boolean m_enabled = true;
    
    /**
     * @see Control#isEnabled()
     */
    public boolean isEnabled() {
        return m_enabled;
    }
    
    /**
     * @see Control#setEnabled(boolean)
     */
    public void setEnabled(boolean enabled) {
        m_enabled = enabled;
    }
    
    // ------------------------------------------------------------------------
    
    /**
     * @see Control#itemDragged(VisualItem, java.awt.event.MouseEvent)
     */
    public void itemDragged(VisualItem item, MouseEvent e) {
    } 

    /**
     * @see Control#itemMoved(VisualItem, java.awt.event.MouseEvent)
     */
    public void itemMoved(VisualItem item, MouseEvent e) {
    } 

    /**
     * @see Control#itemWheelMoved(VisualItem, java.awt.event.MouseWheelEvent)
     */
    public void itemWheelMoved(VisualItem item, MouseWheelEvent e) {
    } 

    /**
     * @see Control#itemClicked(VisualItem, java.awt.event.MouseEvent)
     */
    public void itemClicked(VisualItem item, MouseEvent e) {
    } 

    /**
     * @see Control#itemPressed(VisualItem, java.awt.event.MouseEvent)
     */
    public void itemPressed(VisualItem item, MouseEvent e) {
    } 

    /**
     * @see Control#itemReleased(VisualItem, java.awt.event.MouseEvent)
     */
    public void itemReleased(VisualItem item, MouseEvent e) {
    } 

    /**
     * @see Control#itemEntered(VisualItem, java.awt.event.MouseEvent)
     */
    public void itemEntered(VisualItem item, MouseEvent e) {
    } 

    /**
     * @see Control#itemExited(VisualItem, java.awt.event.MouseEvent)
     */
    public void itemExited(VisualItem item, MouseEvent e) {
    } 

    /**
     * @see Control#itemKeyPressed(VisualItem, java.awt.event.KeyEvent)
     */
    public void itemKeyPressed(VisualItem item, KeyEvent e) {
    } 

    /**
     * @see Control#itemKeyReleased(VisualItem, java.awt.event.KeyEvent)
     */
    public void itemKeyReleased(VisualItem item, KeyEvent e) {
    } 

    /**
     * @see Control#itemKeyTyped(VisualItem, java.awt.event.KeyEvent)
     */
    public void itemKeyTyped(VisualItem item, KeyEvent e) {
    } 

    /**
     * @see Control#mouseEntered(java.awt.event.MouseEvent)
     */
    public void mouseEntered(MouseEvent e) {    
    } 

    /**
     * @see Control#mouseExited(java.awt.event.MouseEvent)
     */
    public void mouseExited(MouseEvent e) {     
    } 

    /**
     * @see Control#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent e) {    
    } 

    /**
     * @see Control#mouseReleased(java.awt.event.MouseEvent)
     */
    public void mouseReleased(MouseEvent e) {
    } 

    /**
     * @see Control#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(MouseEvent e) {
    } 

    /**
     * @see Control#mouseDragged(java.awt.event.MouseEvent)
     */
    public void mouseDragged(MouseEvent e) {
    } 

    /**
     * @see Control#mouseMoved(java.awt.event.MouseEvent)
     */
    public void mouseMoved(MouseEvent e) {
    } 

    /**
     * @see Control#mouseWheelMoved(java.awt.event.MouseWheelEvent)
     */
    public void mouseWheelMoved(MouseWheelEvent e) {
    } 

    /**
     * @see Control#keyPressed(java.awt.event.KeyEvent)
     */
    public void keyPressed(KeyEvent e) {
    } 

    /**
     * @see Control#keyReleased(java.awt.event.KeyEvent)
     */
    public void keyReleased(KeyEvent e) {
    } 

    /**
     * @see Control#keyTyped(java.awt.event.KeyEvent)
     */
    public void keyTyped(KeyEvent e) {
    } 

} // end of class ControlAdapter
