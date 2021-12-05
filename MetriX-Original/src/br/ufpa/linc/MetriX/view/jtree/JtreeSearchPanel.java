package br.ufpa.linc.MetriX.view.jtree;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.view.prefuse.util.ui.JSearchPanel;
import br.ufpa.linc.MetriX.view.treemap.TreeMapView;

@SuppressWarnings("serial")
public class JtreeSearchPanel extends JPanel implements ActionListener, KeyListener{
	
	private JTextField search = null;
	private JLabel lbFounds = null;
	private JButton btOK = null, btNext = null, btPrevious = null;
	private int id ;
	private JPanel panel1 = null, panel2 = null;
	private List<DefaultMutableTreeNode> nodes = null;
	private MyJTree myJTree = null;
	
	public JtreeSearchPanel() {
		super( new BorderLayout() );
		myJTree = MetriX.getInstance().getMyJtree();
		add( getPanel1(), BorderLayout.NORTH);
		add( getLbFounds(), BorderLayout.CENTER);
		add( getPanel2(), BorderLayout.SOUTH);
	}

	private JPanel getPanel1() {
		if ( panel1 == null ){
			panel1 = new JPanel( new BorderLayout());
			panel1.add( new JLabel(Configurations.getString("word.search") + ": "), BorderLayout.WEST);
			panel1.add( getSearch(), BorderLayout.CENTER);
			panel1.add( getBtOK(), BorderLayout.EAST);
		}
		return panel1;
	}

	private JLabel getLbFounds(){
		if ( lbFounds == null ){
			lbFounds = new JLabel("");
		}
		return lbFounds;
	}
	
	private JPanel getPanel2() {
		if ( panel2 == null ){
			panel2 = new JPanel( );
			panel2.add( new JLabel());
			panel2.add( getBtPrevious() );
			panel2.add( new JLabel());
			panel2.add( getBtNext() );
			panel2.add( new JLabel());
		}
		return panel2;
	}

	private JTextField getSearch() {
		if ( search == null ){
			search = new JTextField();
			search.addKeyListener( this );
		}
		return search;
	}
	
	private JButton getBtOK() {
		if (btOK == null ){
			btOK = new JButton( Configurations.getString("button.ok"));
			btOK.addActionListener( this );
		}
		return btOK;
	}
	
	private JButton getBtNext() {
		if ( btNext == null ){
			btNext = new JButton(">>");
			btNext.setEnabled( false );
			btNext.addActionListener( this );
		}
		return btNext;
	}

	private JButton getBtPrevious() {
		if ( btPrevious == null ){
			btPrevious = new JButton("<<");
			btPrevious.setEnabled( false );
			btPrevious.addActionListener( this );
		}
		return btPrevious;
	}

	private List<DefaultMutableTreeNode> searchNode(String nodeStr){
		List<DefaultMutableTreeNode> nodes = new ArrayList<DefaultMutableTreeNode>();
		DefaultMutableTreeNode node = null;
        Enumeration<?> enume = ((DefaultMutableTreeNode) myJTree.getModel().getRoot()).breadthFirstEnumeration();
        
        while(enume.hasMoreElements()) {
            node = (DefaultMutableTreeNode)enume.nextElement();
            if( node.getUserObject().toString().toLowerCase().contains( nodeStr.toLowerCase()) ) nodes.add( node );                         
        }
        
        return nodes;
    }
	
	public void actionPerformed(ActionEvent e) {
		
		if ( e.getSource().equals(getBtOK())){
			id = 0;
			getBtPrevious().setEnabled( false );
			getBtNext().setEnabled( false );
			nodes = searchNode(getSearch().getText());

			if ( nodes.size() == 0 ) {
				getLbFounds().setText( "<html><font color=\"red\">" + Configurations.getString("word.noMatchesFound") + "</font></html>");
				selectInView(null);
				return;
			}
			
			getLbFounds().setText( "<html><b>" + nodes.size() + "</b> " + Configurations.getString("word.matches") + " " + Configurations.getString("word.found")  + 
					"<br>" + Configurations.getString( "word.showingMatches") + ": <b>" + (id + 1) + "</b></html>");
			
			if ( nodes.size() > 1 ) getBtNext().setEnabled( true );
			
			DefaultMutableTreeNode node = nodes.get(id);
			TreeNode[] node_ = ((DefaultTreeModel) myJTree.getModel()).getPathToRoot( node );
			TreePath path = new TreePath(node_); 
			MetriX.getInstance().getMyJtree().scrollPathToVisible(path); 
			MetriX.getInstance().getMyJtree().setSelectionPath(path);
			selectInView(node);
		}
		
		else if ( e.getSource().equals(getBtNext()) || e.getSource().equals(getBtPrevious())){
			
			if ( e.getSource().equals(getBtNext()) ){
				id++;
				if ( id == nodes.size() - 1 ) getBtNext().setEnabled( false );
				getBtPrevious().setEnabled( true );
			}
			
			if ( e.getSource().equals(getBtPrevious()) ){
				id--;
				if ( id == 0 ) getBtPrevious().setEnabled( false );
				getBtNext().setEnabled( true );
			}
			
			getLbFounds().setText( "<html><b>" + nodes.size() + "</b> " + Configurations.getString("word.matches") + " " + Configurations.getString("word.found") + 
					"<br>" + Configurations.getString( "word.showingMatches") + ": <b>" + (id + 1) + "</b></html>");
			
			DefaultMutableTreeNode node = nodes.get(id);
			TreeNode[] nodePath = ((DefaultTreeModel) myJTree.getModel()).getPathToRoot( node );
			TreePath path = new TreePath(nodePath); 
			MetriX.getInstance().getMyJtree().scrollPathToVisible(path); 
			MetriX.getInstance().getMyJtree().setSelectionPath(path);
			selectInView(node);
		}
	}
	
	private void selectInView (DefaultMutableTreeNode node){
		
//		MyGraph myGraph = getSearchGraph();
		JSearchPanel searchTreeMap = getSearchTreeMap();

		if ( node == null || node.getDepth() == 0 ) {
			searchTreeMap.setQuery("");
//			myGraph.setSearch("");
		}
		else {
			searchTreeMap.setQuery(node.getUserObject().toString());
//			if ( node.getUserObject() instanceof Entity ) myGraph.setSearch(((Entity)node.getUserObject()).getFullName() );
//			else if ( node.getUserObject() instanceof Method ) myGraph.setSearch(((Method)node.getUserObject()).getEntidade().getFullName() + ":"+ ((Method)node.getUserObject()).getNome() );
//			else myGraph.setSearch("");
		}
	}
	
	private JSearchPanel getSearchTreeMap(){
		MyJTree tree = myJTree;
		JSplitPane jspp = (JSplitPane) tree.getParent().getParent().getParent().getParent();
		JTabbedPane jtp = (JTabbedPane) jspp.getRightComponent();
		JPanel jp = (JPanel)jtp.getComponentAt(0);
		TreeMapView tmv = (TreeMapView) jp.getComponent(0);
		return tmv.getSearch();
	}
	
//	private MyGraph getSearchGraph(){
//		MyJTree tree = myJTree;
//		JSplitPane jspp = (JSplitPane) tree.getParent().getParent().getParent().getParent();
//		JTabbedPane jtp = (JTabbedPane) jspp.getRightComponent();
//		return (MyGraph) jtp.getComponentAt(2);
//	}

	@Override
	public void keyPressed(KeyEvent kevt) {
		if ( kevt.getKeyCode() ==  KeyEvent.VK_ENTER) getBtOK().doClick();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		getBtOK().doClick();
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
