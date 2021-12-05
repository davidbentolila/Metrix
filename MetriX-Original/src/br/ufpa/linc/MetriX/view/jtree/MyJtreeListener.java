package br.ufpa.linc.MetriX.view.jtree;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.tree.DefaultMutableTreeNode;

import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Method;
import br.ufpa.linc.MetriX.api.model.Package;
import br.ufpa.linc.MetriX.view.prefuse.util.ui.JSearchPanel;
import br.ufpa.linc.MetriX.view.treemap.MyTreeMap;
import br.ufpa.linc.MetriX.view.treemap.TreeMapView;

public class MyJtreeListener implements MouseListener{
	
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	
	public void mouseClicked(MouseEvent e) {
		if ( e.getButton() == 1){
			if( ((MyJTree)e.getComponent()).getSelectionPath() != null ){
				DefaultMutableTreeNode n = (DefaultMutableTreeNode) ((MyJTree)e.getComponent()).getSelectionPath().getLastPathComponent();
				int dpth = n.getDepth();

				if ( e.getClickCount() == 1 ){
					JSearchPanel searchTreeMap = getSearchTreeMap(e);
					/* **********
					 * MyGraph myGraph = getSearchGraph(e);
					 **************************/
					if ( dpth == 0 ) {
						searchTreeMap.setQuery("");
						/* ***********************
						myGraph.setSearch("");
						**************************/
					}
					else {
						Object o = n.getUserObject();
						String searchString = o.toString(); 
						if ( o instanceof Package ) searchString = ((Package)o).getName();
						if ( o instanceof Entity ) searchString = ((Entity)o).getName();
						if ( o instanceof Method ) searchString = ((Method)o).getEntity().getName();
						
						searchTreeMap.setQuery( searchString );
						/* *****************************************
						 * if ( n.getUserObject() instanceof Entity ) myGraph.setSearch(((Entity)n.getUserObject()).getFullName() );
						 
						else if ( n.getUserObject() instanceof Method ) myGraph.setSearch(((Method)n.getUserObject()).getEntidade().getFullName() + ":"+ ((Method)n.getUserObject()).getNome() );
						else myGraph.setSearch("");
						
						*******************************************/
					}
				}
				if ( e.getClickCount() == 2 && dpth > 0) {
					MetriX.getInstance().setSelectedTab(getTabbedPane(e).getSelectedIndex());
					if ( n.getUserObject() instanceof Package ) new MyTreeMap().getTreeMap(n.getUserObject());
				}
			}
		}
		else if ( e.getButton() == 3){
			
		}
	}
	
	private JTabbedPane getTabbedPane(MouseEvent e){
		MyJTree tree = (MyJTree) e.getComponent();
		/* *********************************************************
		JSplitPane jspp = (JSplitPane) tree.getParent().getParent().getParent().getParent().getParent();
		*******************************************/
		JSplitPane jspp = (JSplitPane) tree.getParent().getParent().getParent().getParent();
		return (JTabbedPane) jspp.getRightComponent();
	}
	
	private JSearchPanel getSearchTreeMap(MouseEvent e){
		JTabbedPane jtp = getTabbedPane(e);
		JPanel jp = (JPanel)jtp.getComponentAt(0);
		TreeMapView tmv = (TreeMapView) jp.getComponent(0);
		return tmv.getSearch();
	}
	
//	private MyStarPlot getSearchStarPlot(MouseEvent e){
//		MyJTree tree = (MyJTree) e.getComponent();
//		JViewport jvp = (JViewport) tree.getParent();
//		JScrollPane jspa = (JScrollPane) jvp.getParent();
//		JSplitPane jspp = (JSplitPane) jspa.getParent();
//		JTabbedPane jtp = (JTabbedPane) jspp.getRightComponent();
//		return (MyStarPlot) jtp.getComponentAt(1);
//	}
	
	/* ******************************************************
	private MyGraph getSearchGraph(MouseEvent e){
		MyJTree tree = (MyJTree) e.getComponent();
		JSplitPane jspp = (JSplitPane) tree.getParent().getParent().getParent().getParent().getParent();
		JTabbedPane jtp = (JTabbedPane) jspp.getRightComponent();
		return (MyGraph) jtp.getComponentAt(2);
	}
	*********************************************************/
}
