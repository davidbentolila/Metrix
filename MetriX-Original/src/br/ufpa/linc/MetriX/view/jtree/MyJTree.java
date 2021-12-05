package br.ufpa.linc.MetriX.view.jtree;


import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Method;
import br.ufpa.linc.MetriX.api.model.Package;


public class MyJTree extends JTree {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1796364793002014700L;
	
	public MyJTree(TreeModel treemodel){
//		super(treemodel);
		setModel(treemodel);
		setCellRenderer(new MyJTreeRender());
		addMouseListener( new MyJtreeListener() );
		MetriX.getInstance().setMyJtree(this);
		setToolTipText("");
	}
		
	/**
	 * @see JTree#getToolTipText(MouseEvent)
	 */
	public String getToolTipText(MouseEvent evt) {
		if ( getRowForLocation(evt.getX(), evt.getY()) == -1 ) return null;
		Object o = ((DefaultMutableTreeNode) getPathForLocation(evt.getX(), evt.getY()).getLastPathComponent()).getUserObject();
		
		if ( o instanceof API ) return ((API) o).getMetricData();
		if ( o instanceof Package ) return ((Package) o).getMetricData();
		if ( o instanceof Entity ) return ((Entity)o).getMetricData(  );
		if ( o instanceof Method ) return ((Method)o).getMetricData();
		return "";
	}
	
	public List<Object> getSelectedObjects(){
		List<Object> selecteds = new ArrayList<Object>();
		DefaultMutableTreeNode node = null;
		TreePath[] paths = getSelectionPaths();
		if ( paths == null ) return selecteds;
		for (TreePath treePath : paths) {
			node = (DefaultMutableTreeNode) treePath.getLastPathComponent();
			if ( node.getUserObject() instanceof API || node.getUserObject() instanceof Package || node.getUserObject() instanceof Entity || node.getUserObject() instanceof Method ) selecteds.add( node.getUserObject() );
		}
		return selecteds;
	}
}
