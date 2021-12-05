package br.ufpa.linc.MetriX.view.jtree;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;

import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.api.model.Package;
import br.ufpa.linc.MetriX.configurations.Configurations;

public class JTreeCheckBox extends JTree implements MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3545283922146008326L;
	private HashSet<Integer> selectionCache = new HashSet<Integer>();
	private Icon packageOpenIcon = null;

	public JTreeCheckBox( TreeModel model){
		packageOpenIcon = new ImageIcon( getClass().getResource("/images/package_open.png"));
		setModel(model);
		addMouseListener( this );
		setCellRenderer( new CheckBoxJTreeCellRenderer() );
		MetriX.getInstance().setJTreeCheckBox(this);
	}
	
	public JTreeCheckBox( List<DefaultMutableTreeNode> nodes){
		DefaultTreeModel defModel = new DefaultTreeModel( new DefaultMutableTreeNode( Configurations.getString("word.package") + "s" ));
		for (DefaultMutableTreeNode node : nodes) ((DefaultMutableTreeNode) defModel.getRoot()).add(node);
		packageOpenIcon = new ImageIcon( getClass().getResource("/images/package_open.png"));
		setModel(defModel);
		addMouseListener( this );
		setCellRenderer( new CheckBoxJTreeCellRenderer() );
		MetriX.getInstance().setJTreeCheckBox(this);
	}
	
	public List<Package> getSelectedPackages(){
		List<Package> packages = new ArrayList<Package>();
		Iterator<Integer> it = selectionCache.iterator();
        while (it.hasNext()) addSelectionRow( it.next() );
        return packages;
	}
	
	class CheckBoxJTreeCellRenderer extends JComponent implements TreeCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2786809017814800780L;
		DefaultTreeCellRenderer defaultComp;
	    JCheckBox checkbox;
	    
	    public CheckBoxJTreeCellRenderer() {
	        setLayout (new BorderLayout());
	        defaultComp = new DefaultTreeCellRenderer();
	        
	        checkbox = new JCheckBox();
	        add (checkbox, BorderLayout.WEST);
	        add (defaultComp, BorderLayout.CENTER);
	    }

	    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
	    	
	        defaultComp.getTreeCellRendererComponent(tree, value, isSelected, expanded, leaf, row, hasFocus);
	        
	        if ( ((DefaultMutableTreeNode) value).getUserObject() instanceof Package ) defaultComp.setIcon(packageOpenIcon);
	        
	        checkbox.setSelected (isSelected);
	        
	        Component[] comps = getComponents();
            for (int i=0; i<comps.length; i++) {
            	comps[i].setBackground (Color.WHITE);
            	comps[i].setForeground(Color.BLACK);
            }
                
	        return this;
	    }
    }
	
	public void mouseClicked(MouseEvent mevt) {
		int row = getRowForLocation(mevt.getX(), mevt.getY());
		
		//if select the first box
		if ( row == 0 ){
			//selec all
			if ( !selectionCache.contains( row ) ){
				selectionCache.add( row );
				DefaultMutableTreeNode node = ((DefaultMutableTreeNode) getModel().getRoot()).getNextNode();
				while ( node != null ){
					selectionCache.add( ++row );
					node = node.getNextNode();
				}
			}
			//unselec all
			else{
				selectionCache.remove( row );
				selectionCache.clear();
			}
		}
		//if select another box
		else if ( !selectionCache.contains( row ) ) selectionCache.add( row );
		else {
			if ( selectionCache.contains( 0 ) ) selectionCache.remove( 0 );
			selectionCache.remove( row );
		}
		
		clearSelection();
		Iterator<Integer> it = selectionCache.iterator();
        while (it.hasNext()) addSelectionRow( it.next() );
	}

	public void mouseEntered(MouseEvent mouseevent) {}
	public void mouseExited(MouseEvent mouseevent) {}
	public void mousePressed(MouseEvent mouseevent) {}
	public void mouseReleased(MouseEvent mouseevent) {}
}
