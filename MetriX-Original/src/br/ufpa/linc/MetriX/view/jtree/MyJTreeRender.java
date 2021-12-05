package br.ufpa.linc.MetriX.view.jtree;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Class;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Interface;
import br.ufpa.linc.MetriX.api.model.Method;
import br.ufpa.linc.MetriX.api.model.Package;

public class MyJTreeRender extends DefaultTreeCellRenderer implements TreeCellRenderer{
	
	private static final long serialVersionUID = -5031188978776461457L;
	private Icon apiIcon = null,
				 classIcon = null,
				 interfaceIcon = null,
				 methodIcon = null,
				 packageIcon = null,
				 packageOpenIcon = null,
				 metricIcon = null;

	public MyJTreeRender(){
		super();
		CustomIconRenderer();
	}
	
	public void CustomIconRenderer() {
		apiIcon = new ImageIcon( getClass().getResource("/images/api.png"));
		classIcon = new ImageIcon( getClass().getResource("/images/class.png"));
		interfaceIcon = new ImageIcon( getClass().getResource("/images/interface.png"));
		methodIcon = new ImageIcon( getClass().getResource("/images/method.png"));
		packageIcon = new ImageIcon( getClass().getResource("/images/package.png"));
		packageOpenIcon = new ImageIcon( getClass().getResource("/images/package_open.png"));
		metricIcon = new ImageIcon( getClass().getResource("/images/metric.png"));
	}
	
	public Component getTreeCellRendererComponent(JTree tree, Object value,boolean sel,boolean expanded,boolean leaf, int row,boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

		if ( node.getUserObject() instanceof API ) setIcon(apiIcon);
		else if ( node.getUserObject() instanceof Package ) {
			if ( expanded ) setIcon(packageOpenIcon);
			else setIcon(packageIcon);
		}
		else if ( node.getUserObject() instanceof Class ) setIcon(classIcon);
		else if ( node.getUserObject() instanceof Interface ) setIcon(interfaceIcon);
		else if ( node.getUserObject() instanceof Entity ) setIcon(classIcon);
		else if ( node.getUserObject() instanceof Method ) setIcon(methodIcon);
		else setIcon(metricIcon);

		return this;
	}
}
