package br.ufpa.linc.MetriX.view.jtreeCheckBox;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;

import br.ufpa.linc.MetriX.api.model.Package;

class CheckNode extends DefaultMutableTreeNode{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static int SINGLE_SELECTION = 0;

	public final static int DIG_IN_SELECTION = 4;

	protected int selectionMode;

	protected boolean isSelected;
	
	protected Package packagee;

	public CheckNode() {
		this(null);
	}

//	public CheckNode(Object userObject) {
//		this(userObject, true, false);
//	}
	
	public CheckNode(Package p) {
		this(p, true, p.isShow());
		this.packagee = p;
	}

	public CheckNode(Object userObject, boolean allowsChildren,
			boolean isSelected) {
		super(userObject, allowsChildren);
		this.isSelected = isSelected;
		setSelectionMode(DIG_IN_SELECTION);
	}

	public void setSelectionMode(int mode) {
		selectionMode = mode;
	}

	public int getSelectionMode() {
		return selectionMode;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
		
		if ( getUserObject() instanceof Package) ((Package) getUserObject()).setShow(isSelected);
		
		if ((selectionMode == DIG_IN_SELECTION) && (children != null)) {
			Enumeration<?> e = children.elements();
			while (e.hasMoreElements()) {
				CheckNode node = (CheckNode) e.nextElement();
				node.setSelected(isSelected);
				if ( node.getUserObject() instanceof Package) ((Package) node.getUserObject()).setShow(isSelected);
			}
		}
	}

	public boolean isSelected() {
		return isSelected;
	}
	
	public void setPackage(Package p) {
		this.packagee = p;
	}

	public Package getPackage() {
		return packagee;
	}

	// If you want to change "isSelected" by CellEditor,
	/*
	 * public void setUserObject(Object obj) { if (obj instanceof Boolean) {
	 * setSelected(((Boolean)obj).booleanValue()); } else {
	 * super.setUserObject(obj); } }
	 */
}