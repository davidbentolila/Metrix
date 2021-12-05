package br.ufpa.linc.MetriX.view.gui.menuToolBar;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.analysis.Histogram;
import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.dao.Database;
import br.ufpa.linc.MetriX.report.Report;
import br.ufpa.linc.MetriX.view.gui.FormConfigureDatabase;
import br.ufpa.linc.MetriX.view.gui.FormRemoveAPI;
import br.ufpa.linc.MetriX.view.gui.MainWindow;
import br.ufpa.linc.MetriX.view.gui.analises.FormAnalysisAPIs;
import br.ufpa.linc.MetriX.view.gui.analises.FormChooseAPIAnaliseUseCase;
import br.ufpa.linc.MetriX.view.gui.help.AboutMetriX;
import br.ufpa.linc.MetriX.view.gui.help.HowTo;
import br.ufpa.linc.MetriX.view.gui.insert.FormAPIChooseImport;
import br.ufpa.linc.MetriX.view.gui.insert.FormInsertClass;
import br.ufpa.linc.MetriX.view.gui.insert.FormInsertJarProjectsFromFolder;
import br.ufpa.linc.MetriX.view.gui.insert.FormInsertProjectFolder;
import br.ufpa.linc.MetriX.view.gui.view.FormChooseAPIView;

public class ActionListenerMenuToolBar implements ActionListener,MouseListener {

	private MyToolBar myToolBar = null;
	private MenuBar menuBar = null;
	
	protected ActionListenerMenuToolBar(MyToolBar myToolBar) {
		this.myToolBar = myToolBar;
	}
	
	protected ActionListenerMenuToolBar(MenuBar menuBar) {
		this.menuBar = menuBar;
	}
	
	public void mouseEntered(MouseEvent e) {
		if ( myToolBar != null ){
			if ( e.getSource().equals( myToolBar.getBtAddProjectJar() )) changeIcon(myToolBar.getBtAddProjectJar(), "/images/add_api40_hover.png");
			else if ( e.getSource().equals( myToolBar.getBtAddClass() )) changeIcon(myToolBar.getBtAddClass(), "/images/add_class40_hover.png");
			else if ( e.getSource().equals( myToolBar.getBtAddProjectFolder() )) changeIcon(myToolBar.getBtAddProjectFolder(), "/images/add_pjFolder40_hover.png");
			else if ( e.getSource().equals( myToolBar.getBtImportProject() )) changeIcon(myToolBar.getBtImportProject(), "/images/import_api40_hover.png");
			else if ( e.getSource().equals( myToolBar.getBtRemProject() )) changeIcon(myToolBar.getBtRemProject(), "/images/rem_api40_hover.png");
			else if ( e.getSource().equals( myToolBar.getBtCompare() )) changeIcon(myToolBar.getBtCompare(), "/images/compare_api40_hover.png");
			else if ( e.getSource().equals( myToolBar.getBtVisualization() )) changeIcon(myToolBar.getBtVisualization(), "/images/analysis40_hover.png");
			else if ( e.getSource().equals( myToolBar.getBtReport() )) changeIcon(myToolBar.getBtReport(), "/images/report40_hover.png");
		}
		else if ( menuBar != null ){
			if ( e.getSource().equals( menuBar.getFileMenu())) changeColor(menuBar.getFileMenu());
			else if ( e.getSource().equals( menuBar.getViewMenu() )) changeColor(menuBar.getViewMenu());
			else if ( e.getSource().equals( menuBar.getMenuHelp() )) changeColor(menuBar.getMenuHelp());
		}
	}

	public void mouseExited(MouseEvent e) {
		if ( myToolBar != null ){
			if ( e.getSource().equals( myToolBar.getBtAddProjectJar() )) changeIcon(myToolBar.getBtAddProjectJar(), "/images/add_api40.png");
			else if ( e.getSource().equals( myToolBar.getBtAddClass() )) changeIcon(myToolBar.getBtAddClass(), "/images/add_class40.png");
			else if ( e.getSource().equals( myToolBar.getBtAddProjectFolder() )) changeIcon(myToolBar.getBtAddProjectFolder(), "/images/add_pjFolder40.png");
			else if ( e.getSource().equals( myToolBar.getBtImportProject() )) changeIcon(myToolBar.getBtImportProject(), "/images/import_api40.png");
			else if ( e.getSource().equals( myToolBar.getBtRemProject() )) changeIcon(myToolBar.getBtRemProject(), "/images/rem_api40.png");
			else if ( e.getSource().equals( myToolBar.getBtVisualization() )) changeIcon(myToolBar.getBtVisualization(), "/images/analysis40.png");
			else if ( e.getSource().equals( myToolBar.getBtCompare() )) changeIcon(myToolBar.getBtCompare(), "/images/compare_api40.png");
			else if ( e.getSource().equals( myToolBar.getBtReport() )) changeIcon(myToolBar.getBtReport(), "/images/report40.png");
		}
		if ( menuBar != null ){
			if ( e.getSource().equals( menuBar.getFileMenu())) changeColor(menuBar.getFileMenu());
			else if ( e.getSource().equals( menuBar.getViewMenu() )) changeColor(menuBar.getViewMenu());
			else if ( e.getSource().equals( menuBar.getMenuHelp() )) changeColor(menuBar.getMenuHelp());
		}
	}

	public void mousePressed(MouseEvent mouseevent) {}
	public void mouseReleased(MouseEvent mouseevent) {}

	public void mouseClicked(MouseEvent aevt) {
		if ( myToolBar != null ){
			if ( aevt.getSource().equals( myToolBar.getBtRemProject() )) new FormRemoveAPI();
			else if ( aevt.getSource().equals( myToolBar.getBtAddProjectJar() )) addProjectJar();
			else if ( aevt.getSource().equals( myToolBar.getBtAddClass() )) addClass();
			else if ( aevt.getSource().equals( myToolBar.getBtAddProjectFolder() )) addProjectFolder();
			else if ( aevt.getSource().equals( myToolBar.getBtImportProject() )) new FormAPIChooseImport();
			else if ( aevt.getSource().equals( myToolBar.getBtVisualization() )) new FormChooseAPIView();
			else if ( aevt.getSource().equals( myToolBar.getBtCompare() )) MainWindow.getInstance().compareVisualizations();
			else if ( aevt.getSource().equals( myToolBar.getBtReport() )) new Report();
		}
	}
	
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if ( menuBar != null ){
			if ( source.equals( menuBar.getMenuAnalysisMetricsReportCSV() )) new Histogram("CSV" );
			else if ( source.equals( menuBar.getMenuAnalysisMetricsReportArena() )) new Histogram( "ARENA" );
			else if ( source.equals( menuBar.getMenuAnalysisHistogram() )) new Histogram( "HISTOGRAM" );
			else if ( source.equals( menuBar.getMenuItemInsertProjectJar() )) addProjectJar();
			else if ( source.equals( menuBar.getMenuItemInsertClass() )) addClass();
			else if ( source.equals( menuBar.getMenuItemInsertProjectFolder() )) addProjectFolder();
			else if ( source.equals( menuBar.getMenuItemImportProject() )) new FormAPIChooseImport();
			else if ( source.equals( menuBar.getMenuItemAnalisysAPISet() )) analisysAPISet();
			else if ( source.equals( menuBar.getMenuItemAnalisysAPIUseCase() )) new FormChooseAPIAnaliseUseCase();
			else if ( source.equals( menuBar.getMenuItemInsertProjectsFromFolder() ) ) new FormInsertJarProjectsFromFolder();
			else if ( source.equals( menuBar.getMenuItemRemoveProject() )) new FormRemoveAPI();
			else if ( source.equals( menuBar.getMenuItemConfigureDB() )) new FormConfigureDatabase();
	//		else if ( source.equals( getMenuItemShowToolBar() ) ) MetriX.getInstance().getMainWindow().setVisiblePanels(menuItemShowToolbar.isSelected());
			else if ( source.equals( menuBar.getMenuItemPTBR() )) MetriX.getInstance().changeLanguage( new Locale("pt","BR") );
			else if ( source.equals( menuBar.getMenuItemENUS()) ) MetriX.getInstance().changeLanguage( new Locale("en","US") );
			else if ( source.equals( menuBar.getMenuItemReport()) ) new Report();
			else if ( source.equals( menuBar.getMenuQuit()) ) System.exit(0);
			else if ( source.equals( menuBar.getMenuItemTreeMap() )) new FormChooseAPIView();
			else if ( source.equals( menuBar.getMenuItemCompareView() )) MainWindow.getInstance().compareVisualizations();
			else if ( source.equals( menuBar.getMenuHelpHowTo() )) new HowTo();
			else if ( source.equals( menuBar.getMenuHelpAbout() )) new AboutMetriX();
//			else if ( source.equals( menuBar.getMenuItemClearDatabase() )) {
//				
//			}
			/* ****************************************************
			else if ( source.equals( menuBar.getMenuLayoutCIRCLE())){
				MetriX.getInstance().setGraphLayout("CIRCLE");
				MetriX.getInstance().getMyGraph().updateLayout();
			}
			else if ( source.equals( menuBar.getMenuLayoutFR() )){
				MetriX.getInstance().setGraphLayout("FR");
				MetriX.getInstance().getMyGraph().updateLayout();
			}
			else if ( source.equals( menuBar.getMenuLayoutSPRING() )){
				MetriX.getInstance().setGraphLayout("SPRING");
				MetriX.getInstance().getMyGraph().updateLayout();
			}
			else if ( source.equals( menuBar.getMenuLayoutKK() )){
				MetriX.getInstance().setGraphLayout("KK");
				MetriX.getInstance().getMyGraph().updateLayout();
			}
			else if ( source.equals( menuBar.getMenuLayoutISOM() )){
				MetriX.getInstance().setGraphLayout("ISOM");
				MetriX.getInstance().getMyGraph().updateLayout();
			}
			else if ( source.equals( menuBar.getMenuMousePICKING() )){
				MetriX.getInstance().setMouseMode(Mode.PICKING);
				MetriX.getInstance().getMyGraph().updateMouse();
			}
			else if ( source.equals( menuBar.getMenuMouseTRANSFORMING() )){
				MetriX.getInstance().setMouseMode(Mode.TRANSFORMING);
				MetriX.getInstance().getMyGraph().updateMouse();
			}
			else if ( source.equals( menuBar.getMenuHelpAbout() )) new AboutMetriX();
			else if ( source.equals( menuBar.getMenuHelpHowTo() )) new HowTo();
			**************************************************/
		}
	}
	
	//Acoes
	private void changeIcon(JLabel jb, String iconPath){
		jb.setIcon( new ImageIcon( getClass().getResource(iconPath)) );
	}
	
	private void changeColor(JMenuItem jmi){
		if ( jmi.getBackground().equals( Color.DARK_GRAY )){
			jmi.setBackground( Color.GRAY );
			jmi.setForeground( Color.BLACK );
		}
		else {
			jmi.setBackground( Color.DARK_GRAY );
			jmi.setForeground( Color.WHITE );
		}
	}
	
	private void addProjectJar(){ MetriX.getInstance().insertProjectJar(); }
	
	private void addProjectFolder(){ new FormInsertProjectFolder(); }
	
	private void addClass(){ new FormInsertClass(); }
	
	private void analisysAPISet(){
		List<API> dbAPIs = Database.getInstance().getAllAPI();
		if ( dbAPIs.size() > 0 ){ 
			ArrayList<API> apis = new ArrayList<API>();
			for (int i = 0 ; i < dbAPIs.size() ; i++) apis.add( dbAPIs.get(i) );
			new FormAnalysisAPIs(apis);
		}
		else JOptionPane.showMessageDialog(MainWindow.getInstance(), Configurations.getString("message.noAPI"));
	}
}