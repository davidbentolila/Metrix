package br.ufpa.linc.MetriX.view.gui.menuToolBar;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import br.ufpa.linc.MetriX.configurations.Configurations;

public class MyToolBar extends JToolBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3108198550089554091L;
	private JLabel btAddProject = null,
					btImportProject = null,
					btAddClass = null,
					btAddProjectFolder = null,
					btRemProject = null,
					btVisualization = null,
					btCompare = null,
//					btOpenView = null,
//					btSaveView = null,
					btReport = null;
	private ActionListenerMenuToolBar action = null;
	
	public MyToolBar(){
		super();
		action = new ActionListenerMenuToolBar(this);
		setFloatable(false);
		add( getBtAddProjectJar() );
//		addSeparator(new Dimension(7,0));
//		add( getBtImportProject() );
		addSeparator(new Dimension(7,0));
		add( getBtAddClass() );
		addSeparator(new Dimension(7,0));
		add( getBtAddProjectFolder() );
		addSeparator(new Dimension(7,0));
		add( getBtRemProject() );
		addSeparator(new Dimension(7,0));
		add( getBtCompare() );
		addSeparator(new Dimension(7,0));
		add( getBtVisualization() );
		addSeparator(new Dimension(7,0));
		add( getBtReport() );
//		addSeparator(new Dimension(30,10));
//		this.add( getBtSaveView() );
//		this.add( getBtOpenView() );		
	}
	
	protected JLabel getBtAddProjectJar(){
		if ( btAddProject == null ){
			btAddProject =  new JLabel();
			btAddProject.setIcon( new ImageIcon( getClass().getResource("/images/add_api40.png")) );
			btAddProject.setToolTipText( Configurations.getString("menu.insertProject") );
			btAddProject.addMouseListener( action );
		}
		return btAddProject;
	}
	
	protected JLabel getBtAddClass(){
		if ( btAddClass == null ){
			btAddClass =  new JLabel();
			btAddClass.setIcon( new ImageIcon( getClass().getResource("/images/add_class40.png")) );
			btAddClass.setToolTipText( Configurations.getString("menu.insertClass") );
			btAddClass.addMouseListener( action );
		}
		return btAddClass;
	}
	
	protected JLabel getBtAddProjectFolder(){
		if ( btAddProjectFolder == null ){
			btAddProjectFolder =  new JLabel();
			btAddProjectFolder.setIcon( new ImageIcon( getClass().getResource("/images/add_pjFolder40.png")) );
			btAddProjectFolder.setToolTipText( Configurations.getString("menu.insertPjFolder") );
			btAddProjectFolder.addMouseListener( action );
		}
		return btAddProjectFolder;
	}
	
	protected JLabel getBtImportProject(){
		if ( btImportProject == null ){
			btImportProject =  new JLabel();
			btImportProject.setIcon( new ImageIcon( getClass().getResource("/images/import_api40.png")) );
			btImportProject.setToolTipText( Configurations.getString("menu.importProject") );
			btImportProject.addMouseListener( action );
		}
		return btImportProject;
	}
	
	protected JLabel getBtRemProject(){
		if ( btRemProject == null ){
			btRemProject = new JLabel();
			btRemProject.setIcon( new ImageIcon( getClass().getResource("/images/rem_api40.png")) );
			btRemProject.setToolTipText( Configurations.getString("menu.removeProject") );
			btRemProject.addMouseListener( action );
		}
		return btRemProject;
	}
	
	protected JLabel getBtVisualization(){
		if ( btVisualization == null ){
			btVisualization = new JLabel();
			btVisualization.setIcon( new ImageIcon( getClass().getResource("/images/analysis40.png")) );
			btVisualization.setToolTipText( Configurations.getString("menu.view.generateView") );
			btVisualization.addMouseListener( action );
		}
		return btVisualization;
	}
	
	protected JLabel getBtCompare(){
		if ( btCompare == null ){
			btCompare = new JLabel();
			btCompare.setIcon( new ImageIcon( getClass().getResource("/images/compare_api40.png")) );
			btCompare.setToolTipText( Configurations.getString("menu.view.compareView") );
			btCompare.addMouseListener( action );
		}
		return btCompare;
	}
	
//	private JLabel getBtOpenView(){
//		if ( btOpenView == null ){
//			btOpenView = new JLabel();
//			btOpenView.setIcon( new ImageIcon( getClass().getResource("/images/open24.png")) );
//			btOpenView.addActionListener( new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					main.openView();
//				}
//			});
//		}
//		return btOpenView;
//	}
//	
//	private JLabel getBtSaveView(){
//		if ( btSaveView == null ){
//			btSaveView = new JLabel();
//			btSaveView.setIcon( new ImageIcon( getClass().getResource("/images/save24.png")) );
//			btSaveView.addActionListener( new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					main.saveCurrentView();
//				}
//			});
//		}
//		return btSaveView;
//	}
	
	protected JLabel getBtReport(){
		if ( btReport == null ){
			btReport = new JLabel();
			btReport.setIcon( new ImageIcon( getClass().getResource("/images/report40.png")) );
			btReport.setToolTipText( Configurations.getString("menu.report") );
			btReport.addMouseListener( action );
		}
		return btReport;
	}
}