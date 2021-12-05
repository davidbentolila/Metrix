package br.ufpa.linc.MetriX.view.jtreeCheckBox;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Package;

/**
 * @author david Date: 30/10/2010
 */
public class JTreeCheckBox extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6679244812127753466L;

	private List<CheckNode> packages = null;
	private JTree checkBoxTree;
	private DefaultMutableTreeNode root ;
	private List<Package> packages_;

	public JTreeCheckBox(API api) {
		
		packages = new ArrayList<CheckNode>();
		packages_ = new ArrayList<Package>();
		
		root = new DefaultMutableTreeNode(api);

		for (Entity e : api.getAllEntities())
			if ( !packages_.contains(e.getPackage()) ){
				packages_.add(e.getPackage());
				packages.add( new CheckNode(e.getPackage()) );
			}
		
		relatePackages();
		
		setViewportView(getTree(api));
	}
	
	private void relatePackages() {

		for (CheckNode cn : packages) {
//			System.out.println("1 " + cn);
//			System.out.println(cn.getPackage());
//			System.out.println(getPackageInJtree(cn.getPackage()));
//			System.out.println("5 " + getPackageInJtree(cn.getPackage().getSuperPackage()));
			CheckNode superPackage = getPackageInJtree(((Package) cn.getUserObject()).getSuperPackage());
			if (superPackage != null) superPackage.add(cn);
			else root.add( cn );
		}
	}

	private CheckNode getPackageInJtree(Package p) {
		for (CheckNode cbn : packages)
			if (cbn == null || ((Package) cbn.getUserObject()).equals(p)) return cbn;
		return null;
	}
	
	public JTree getTree(API api) {
		if ( checkBoxTree == null ){
			checkBoxTree = new JTree(root);
			checkBoxTree.getSelectionModel().setSelectionMode( TreeSelectionModel.SINGLE_TREE_SELECTION);
			checkBoxTree.putClientProperty("JTree.lineStyle", "Angled");
			checkBoxTree.addMouseListener(new NodeSelectionListener(checkBoxTree));
			
			checkBoxTree.setCellRenderer( new CheckRenderer() );
	
			checkBoxTree.setEditable(false);
		}
		return checkBoxTree;
	}
}
