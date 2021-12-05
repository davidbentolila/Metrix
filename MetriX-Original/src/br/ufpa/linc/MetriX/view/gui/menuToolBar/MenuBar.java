package br.ufpa.linc.MetriX.view.gui.menuToolBar;

import java.awt.Color;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.view.gui.MainWindow;
//import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;

@SuppressWarnings("unused")
public class MenuBar extends JMenuBar {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7281588523944959991L;
	private JMenuBar menuBar = null;
	private JMenu fileMenu = null,
				  menuLanguage = null,
				  viewMenu = null,
				  menuLayout = null,
				  menuAnalysis = null,
				  menuMouse = null,
				  menuGraph = null,
				  menuHelp = null;
	
	private JMenuItem menuItemInsertProjectJar = null,
						menuItemInsertClass = null,
						menuItemInsertProjectFolder = null,
						menuItemInsertProjectsFromFolder = null,
						menuItemRemoveProject = null,
						menuItemReport = null,
						menuItemAnalysisAPISet = null,
						
						menuItemAnalysisAPIUseCases = null,
						menuItemConfigureDB = null,
						
						menuItemPTBR = null,
						menuItemENUS = null,
						
						menuItemQuit = null,
						
						menuItemTreeMap = null,
						menuItemCompareView = null,
						
						menuLayoutCIRCLE = null,
						menuLayoutFR = null,
						menuLayoutSPRING = null,
						menuLayoutKK = null,
						menuLayoutISOM = null,
						
						menuAnalysisMetricsReportCSV = null,
						menuAnalysisMetricsReportArena = null,
						menuAnalysisHistogram = null,
						
						menuMousePICKING = null,
						menuMouseTRANSFORMING = null,
						
						menuHelpHowTo = null,
						menuHelpAbout = null;

	private ActionListenerMenuToolBar action = null;
	
private JMenuItem menuItemImportProject=null;
	
	public MenuBar(MainWindow mainWindow){
		super();
		action = new ActionListenerMenuToolBar(this);
		initComponents();
	}
	
	private JMenuBar initComponents(){
		if ( menuBar == null ){
			menuBar = this;
			menuBar.add(getFileMenu());
			menuBar.add(getViewMenu());
			menuBar.add(getMenuHelp());
			menuBar.setOpaque(true);
		}
		return menuBar;
	}
	
	protected JMenu getMenuHelp(){
		if ( menuHelp == null ){
			menuHelp = new JMenu( Configurations.getString("menu.help") );
			menuHelp.add( getMenuHelpHowTo() );
			menuHelp.add( getMenuHelpAbout() );
			menuHelp.addMouseListener( action );
		}
		return menuHelp;
	}
	
	protected JMenuItem getMenuHelpHowTo(){
		if ( menuHelpHowTo == null ){
			menuHelpHowTo = new JMenuItem( Configurations.getString("menu.help.howTo") );
			menuHelpHowTo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
			menuHelpHowTo.addActionListener( action );
		}
		return menuHelpHowTo;
	}
	
	protected JMenuItem getMenuHelpAbout(){
		if ( menuHelpAbout == null ){
			menuHelpAbout = new JMenuItem( Configurations.getString( "menu.help.about") );
			menuHelpAbout.addActionListener( action );
		}
		return menuHelpAbout;
	}
	
	protected JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText( Configurations.getString("menu.file") );
			fileMenu.setBackground(Color.black);
//			fileMenu.addSeparator();
			fileMenu.add(getMenuItemInsertProjectJar());
			fileMenu.add(getMenuItemInsertClass());
			fileMenu.add(getMenuItemInsertProjectFolder());
//			fileMenu.add(getMenuItemInsertCProject());
			fileMenu.add(getMenuItemImportProject());
			fileMenu.add(getMenuItemInsertProjectsFromFolder());
			fileMenu.add(getMenuItemRemoveProject());
//			fileMenu.addSeparator();
//			fileMenu.add(getMenuAnalysis());
			fileMenu.addSeparator();
			fileMenu.add(getMenuItemConfigureDB());
			fileMenu.addSeparator();
			fileMenu.add(getMenuItemReport());
			fileMenu.add(getMenuItemAnalisysAPISet());
			fileMenu.add(getMenuItemAnalisysAPIUseCase());
			fileMenu.addSeparator();
			fileMenu.add( getMenuLanguage() );
			fileMenu.addSeparator();
			fileMenu.add(getMenuQuit());
			fileMenu.setOpaque(true);
			fileMenu.setBackground( Color.GRAY );
			fileMenu.addMouseListener( action );
		}
		return fileMenu;
	}

	protected JMenuItem getMenuItemImportProject() {
		if (menuItemImportProject == null) {
			menuItemImportProject = new JMenuItem();
			menuItemImportProject.setText( Configurations.getString("menu.importProject"));
			menuItemImportProject.setIcon( new ImageIcon( getClass().getResource("/images/import_api.png")));
			menuItemImportProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK));
			menuItemImportProject.addActionListener( action );
		}
		return menuItemImportProject;
	}

	protected JMenuItem getMenuItemAnalisysAPISet() {
		if ( menuItemAnalysisAPISet == null ) {
			menuItemAnalysisAPISet = new  JMenuItem( Configurations.getString("menu.analysis") );
			menuItemAnalysisAPISet.setIcon( new ImageIcon( getClass().getResource("/images/analisis.png")));
			menuItemAnalysisAPISet.addActionListener( action );
			menuItemAnalysisAPISet.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		}
		return menuItemAnalysisAPISet;
	}

	protected JMenuItem getMenuItemAnalisysAPIUseCase(){
		if ( menuItemAnalysisAPIUseCases == null ){
			menuItemAnalysisAPIUseCases = new JMenuItem( Configurations.getString("menu.analysisUseCases"));
			menuItemAnalysisAPIUseCases.setIcon( new ImageIcon( getClass().getResource("/images/analisis.png")));
			menuItemAnalysisAPIUseCases.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_MASK));
			menuItemAnalysisAPIUseCases.addActionListener( action );
		}
		return menuItemAnalysisAPIUseCases;
	}
	
	protected JMenu getMenuAnalysis(){
		if ( menuAnalysis == null ){
			menuAnalysis = new JMenu( Configurations.getString("menu.analysis"));
			menuAnalysis.setIcon( new ImageIcon( getClass().getResource("/images/analisis.png")));
			menuAnalysis.add( getMenuAnalysisMetricsReportCSV() );
			menuAnalysis.add( getMenuAnalysisMetricsReportArena() );
			menuAnalysis.add( getMenuAnalysisHistogram() );
			menuAnalysis.addMouseListener( action );
		}
		return menuAnalysis;
	}
	
	protected JMenuItem getMenuAnalysisMetricsReportCSV(){
		if ( menuAnalysisMetricsReportCSV == null ){
			menuAnalysisMetricsReportCSV = new JMenuItem( Configurations.getString("menu.analysis.MetricsReportCSV"));
			menuAnalysisMetricsReportCSV.setIcon( new ImageIcon( getClass().getResource("/images/report.png")));
			menuAnalysisMetricsReportCSV.addActionListener( action );
			menuAnalysisMetricsReportCSV.addMouseListener( action );
		}
		return menuAnalysisMetricsReportCSV;
	}
	
	protected JMenuItem getMenuAnalysisMetricsReportArena(){
		if ( menuAnalysisMetricsReportArena == null ){
			menuAnalysisMetricsReportArena = new JMenuItem( Configurations.getString("menu.analysis.MetricsReportArena"));
			menuAnalysisMetricsReportArena.setIcon( new ImageIcon( getClass().getResource("/images/arena.png")));
			menuAnalysisMetricsReportArena.addActionListener( action );
			menuAnalysisMetricsReportArena.addMouseListener( action );
		}
		return menuAnalysisMetricsReportArena;
	}
	
	protected JMenuItem getMenuAnalysisHistogram(){
		if ( menuAnalysisHistogram == null ){
			menuAnalysisHistogram = new JMenuItem( Configurations.getString("menu.analysis.Histogram"));
			menuAnalysisHistogram.setIcon( new ImageIcon( getClass().getResource("/images/histogramilis.png")));
			menuAnalysisHistogram.addActionListener( action );
			menuAnalysisHistogram.addMouseListener( action );
		}
		return menuAnalysisHistogram;	
	}
	
//	private JMenuItem getMenuAnalysisHistogramIL(){
//		if ( menuAnalysisHistogramIL == null ){
//			menuAnalysisHistogramIL = new JMenuItem( Configurations.getString("menu.analysis.HistogramIL"));
//			menuAnalysisHistogramIL.setIcon( new ImageIcon( getClass().getResource("/images/histogramil.png")));
//			
//			menuAnalysisHistogramIL.addActionListener( new ActionListener(){
//				public void actionPerformed(ActionEvent e) {
//					new Histogram( main ).getHistogram();
//				}
//			});
//		}
//		return menuAnalysisHistogramIL;
//	}
	
//	private JMenuItem getMenuAnalysisHistogramIS(){
//		if ( menuAnalysisHistogramIS == null ){
//			menuAnalysisHistogramIS = new JMenuItem( Configurations.getString("menu.analysis.HistogramIS"));
//			menuAnalysisHistogramIS.setIcon( new ImageIcon( getClass().getResource("/images/histogramis.png")));
//		}
//		return menuAnalysisHistogramIS;
//	}
	
//	private JMenuItem getMenuAnalysisHistogramOAC(){
//		if ( menuAnalysisHistogramOAC == null ){
//			menuAnalysisHistogramOAC = new JMenuItem( Configurations.getString("menu.analysis.HistogramOAC"));
//		}
//		return menuAnalysisHistogramOAC;
//	}
//	
	protected JMenuItem getMenuItemInsertClass() {
		if (menuItemInsertClass == null) {
			menuItemInsertClass = new JMenuItem();
			menuItemInsertClass.setText( Configurations.getString("menu.insertClass") );
			menuItemInsertClass.setIcon( new ImageIcon( getClass().getResource("/images/add_class.png")));
			menuItemInsertClass.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_MASK));
			menuItemInsertClass.addActionListener( action );
		}
		return menuItemInsertClass;
	}
	
	protected JMenuItem getMenuItemInsertProjectFolder() {
		if (menuItemInsertProjectFolder == null) {
			menuItemInsertProjectFolder = new JMenuItem();
			menuItemInsertProjectFolder.setText( Configurations.getString("menu.insertPjFolder") );
			menuItemInsertProjectFolder.setIcon( new ImageIcon( getClass().getResource("/images/add_pjFolder.png")));
			menuItemInsertProjectFolder.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
			menuItemInsertProjectFolder.addActionListener( action );
		}
		return menuItemInsertProjectFolder;
	}
	
	protected JMenuItem getMenuItemInsertProjectJar() {
		if (menuItemInsertProjectJar == null) {
			menuItemInsertProjectJar = new JMenuItem();
			menuItemInsertProjectJar.setText( Configurations.getString("menu.insertProject") );
			menuItemInsertProjectJar.setIcon( new ImageIcon( getClass().getResource("/images/add_api.png")));
			menuItemInsertProjectJar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
			menuItemInsertProjectJar.addActionListener( action );
		}
		return menuItemInsertProjectJar;
	}

//	public JMenuItem getMenuItemInsertCProject() {
//		if (menuItemInsertCProject == null) {
//			menuItemInsertCProject = new JMenuItem();
//			menuItemInsertCProject.setText( Configurations.getString("menu.insertCProject") );
//			menuItemInsertCProject.setIcon( new ImageIcon( getClass().getResource("/images/add_api.png")));
//			menuItemInsertCProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
//			menuItemInsertCProject.addActionListener( action );
//		}
//		return menuItemInsertCProject;
//	}
	
	protected JMenuItem getMenuItemInsertProjectsFromFolder() {
		if (menuItemInsertProjectsFromFolder == null) {
			menuItemInsertProjectsFromFolder = new JMenuItem();
			menuItemInsertProjectsFromFolder.setText( Configurations.getString("menu.insertProjectFromFolder") );
			menuItemInsertProjectsFromFolder.setIcon( new ImageIcon( getClass().getResource("/images/api.folder.png")));
			menuItemInsertProjectsFromFolder.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
			menuItemInsertProjectsFromFolder.addActionListener( action );
		}
		return menuItemInsertProjectsFromFolder;
	}
	
	protected JMenuItem getMenuItemRemoveProject() {
		if (menuItemRemoveProject == null) {
			menuItemRemoveProject = new JMenuItem();
			menuItemRemoveProject.setText( Configurations.getString("menu.removeProject") );
			menuItemRemoveProject.setIcon( new ImageIcon( getClass().getResource("/images/rem_api.png")));
			menuItemRemoveProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
			menuItemRemoveProject.addActionListener( action );
		}
		return menuItemRemoveProject;
	}
	
	protected JMenuItem getMenuItemConfigureDB(){
		if ( menuItemConfigureDB == null ){
			menuItemConfigureDB = new JMenuItem( Configurations.getString( "menu.configureDatabase" ) );
//			menuItemConfigureDB.setIcon( new ImageIcon( getClass().getResource("/images/report.png")));
			menuItemConfigureDB.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
			menuItemConfigureDB.addActionListener( action );
		}
		return menuItemConfigureDB;
	}
//	public JMenuItem getMenuItemShowToolBar(){
//		if ( menuItemShowToolbar == null ){
//			menuItemShowToolbar = new JCheckBoxMenuItem(Configurations.getString("menu.showToolbars"));
//			menuItemShowToolbar.setEnabled(false);
//			menuItemShowToolbar.setSelected(true);
//			menuItemShowToolbar.addActionListener( this );
//		}
//		return menuItemShowToolbar;
//	}
	
	protected JMenu getMenuLanguage(){
		if ( menuLanguage == null ){
			menuLanguage = new JMenu( Configurations.getString( "menu.language" ) );
			menuLanguage.setIcon( new ImageIcon( getClass().getResource("/images/language.png")));
			menuLanguage.add( getMenuItemPTBR() );
			menuLanguage.add( getMenuItemENUS() );
		}
		return menuLanguage;
	}
	
	protected JMenuItem getMenuItemPTBR(){
		if ( menuItemPTBR == null ){
			menuItemPTBR = new JMenuItem( Configurations.getString( "menu.language.ptBR" ) );
			menuItemPTBR.setIcon( new ImageIcon( getClass().getResource("/images/brasil.png")));
			menuItemPTBR.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
			menuItemPTBR.addActionListener( action );
			
		}
		return menuItemPTBR;
	}

	protected JMenuItem getMenuItemENUS(){
		if ( menuItemENUS == null ){
			menuItemENUS = new JMenuItem( Configurations.getString( "menu.language.enUS" ) );
			menuItemENUS.setIcon( new ImageIcon( getClass().getResource("/images/eua.png")));
			menuItemENUS.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
			menuItemENUS.addActionListener( action );
		}
		return menuItemENUS;
	}
	
	protected JMenuItem getMenuItemReport(){
		if ( menuItemReport == null ){
			menuItemReport = new JMenuItem( Configurations.getString( "menu.report" ) );
			menuItemReport.setIcon( new ImageIcon( getClass().getResource("/images/report.png")));
			menuItemReport.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
			menuItemReport.addActionListener( action );
		}
		return menuItemReport;
	}
	
	protected JMenuItem getMenuQuit() {
		if (menuItemQuit == null) {
			menuItemQuit = new JMenuItem( Configurations.getString("menu.quit") );
			menuItemQuit.setIcon( new ImageIcon( getClass().getResource("/images/exit.png")));
			menuItemQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
			menuItemQuit.addActionListener( action );
		}
		return menuItemQuit;
	}

	protected JMenu getViewMenu(){
		if ( viewMenu == null ){
			viewMenu = new JMenu( Configurations.getString("menu.view") );
			viewMenu.add( getMenuItemTreeMap() );
			viewMenu.add( getMenuItemCompareView() );
//			viewMenu.add( getMenuItemUsePrivateMethods() );
//			viewMenu.add( getMenuItemOpenView() );
//			viewMenu.add( getMenuItemSaveView() );
//			viewMenu.add( getMenuGraph() );
			viewMenu.addMouseListener( action );
		}
		return viewMenu;
	}
		
	protected JMenuItem getMenuItemTreeMap(){
		if ( menuItemTreeMap == null){
			menuItemTreeMap = new JMenuItem( Configurations.getString("menu.view.generateView") );
			menuItemTreeMap.setIcon( new ImageIcon( getClass().getResource("/images/analysis.png")));
			menuItemTreeMap.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
			menuItemTreeMap.addActionListener( action );
		}
		return menuItemTreeMap;
	}
	
	protected JMenuItem getMenuItemCompareView(){
		if ( menuItemCompareView == null){
			menuItemCompareView = new JMenuItem( Configurations.getString("menu.view.compareView") );
			menuItemCompareView.setIcon( new ImageIcon( getClass().getResource("/images/compare_api.png")));
			menuItemCompareView.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_MASK));
			menuItemCompareView.addActionListener( action );
		}
		return menuItemCompareView;
	}
	
//	private JMenuItem getMenuItemUsePrivateMethods(){
//		if ( menuItemUsePrivateMethods == null){
//			menuItemUsePrivateMethods = new JCheckBoxMenuItem( Configurations.getString("message.non-Public") );
//			menuItemUsePrivateMethods.setIcon( new ImageIcon( getClass().getResource("/images/non-public.png")));
//			menuItemUsePrivateMethods.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
//			menuItemUsePrivateMethods.addActionListener( this );
//		}
//		return menuItemUsePrivateMethods;
//	}
	
//	private JMenuItem getMenuItemOpenView(){
//		if ( menuItemOpenView == null){
//			menuItemOpenView = new JMenuItem( Configurations.getString("menu.view.openView") );
//			menuItemOpenView.setIcon( new ImageIcon( getClass().getResource("/images/open.png")));
//			menuItemOpenView.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
//			menuItemOpenView.addActionListener( new ActionListener(){
//				public void actionPerformed(ActionEvent e) {
//					main.openView();
//				}				
//			});
//		}
//		return menuItemOpenView;
//	}
//	
//	private JMenuItem getMenuItemSaveView(){
//		if ( menuItemSaveView == null){
//			menuItemSaveView = new JMenuItem( Configurations.getString("menu.view.saveView") );
//			menuItemSaveView.setIcon( new ImageIcon( getClass().getResource("/images/save.png")));
//			menuItemSaveView.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
//			menuItemSaveView.addActionListener( new ActionListener(){
//				public void actionPerformed(ActionEvent e) {
//					main.saveCurrentView();
//				}				
//			});
//		}
//		return menuItemSaveView;
//	}
	
	protected JMenu getMenuGraph(){
		if ( menuGraph == null){
			menuGraph = new JMenu( Configurations.getString("menu.graph") );
			menuGraph.setIcon( new ImageIcon( getClass().getResource("/images/graph.png")));
			menuGraph.add( getMenuLayout() );
			menuGraph.add( getMenuMouse() );
		}
		return menuGraph;
	}
	
	protected JMenu getMenuLayout(){
		if ( menuLayout == null){
			menuLayout = new JMenu( Configurations.getString("menu.layout") );
			ButtonGroup bg1 = new ButtonGroup();
			
			bg1.add(getMenuLayoutCIRCLE());
			bg1.add(getMenuLayoutFR());
			bg1.add(getMenuLayoutISOM());
			bg1.add(getMenuLayoutKK());
			bg1.add(getMenuLayoutSPRING());
			
			menuLayout.add( getMenuLayoutCIRCLE() );
			menuLayout.add( getMenuLayoutFR() );
			menuLayout.add( getMenuLayoutISOM() );
			menuLayout.add( getMenuLayoutKK() );
			menuLayout.add( getMenuLayoutSPRING() );
		}
		return menuLayout;
	}
	
	protected JMenuItem getMenuLayoutCIRCLE(){
		if ( menuLayoutCIRCLE == null){
			menuLayoutCIRCLE = new JRadioButtonMenuItem( Configurations.getString("menu.layout.CIRCLE") );
			menuLayoutCIRCLE.addActionListener( action );
		}
		return menuLayoutCIRCLE;
	}
	
	protected JMenuItem getMenuLayoutFR(){
		if ( menuLayoutFR == null){
			menuLayoutFR = new JRadioButtonMenuItem( Configurations.getString("menu.layout.FR") );
			menuLayoutFR.addActionListener( action );
		}
		return menuLayoutFR;
	}
	
	protected JMenuItem getMenuLayoutSPRING(){
		if ( menuLayoutSPRING == null){
			menuLayoutSPRING = new JRadioButtonMenuItem( Configurations.getString("menu.layout.SPRING") );
			menuLayoutSPRING.addActionListener( action );
		}
		return menuLayoutSPRING;
	}
	
	protected JMenuItem getMenuLayoutKK(){
		if ( menuLayoutKK == null){
			menuLayoutKK = new JRadioButtonMenuItem( Configurations.getString("menu.layout.KK") );
			menuLayoutKK.addActionListener( action );
		}
		return menuLayoutKK;
	}
	
	protected JMenuItem getMenuLayoutISOM(){
		if ( menuLayoutISOM == null){
			menuLayoutISOM = new JRadioButtonMenuItem( Configurations.getString("menu.layout.ISOM") );
			menuLayoutISOM.addActionListener( action );
		}
		return menuLayoutISOM;
	}
	
	protected JMenu getMenuMouse(){
		if ( menuMouse == null){
			menuMouse = new JMenu( Configurations.getString("menu.mouse") );
			ButtonGroup bg1 = new ButtonGroup();
			
			bg1.add(getMenuMousePICKING());
			bg1.add(getMenuMouseTRANSFORMING());
			
			menuMouse.add( getMenuMousePICKING() );
			menuMouse.add( getMenuMouseTRANSFORMING() );
		}
		return menuMouse;
	}
	
	protected JMenuItem getMenuMousePICKING(){
		if ( menuMousePICKING == null){
			menuMousePICKING = new JRadioButtonMenuItem( Configurations.getString("menu.mouse.PICKING") );
			menuMousePICKING.addActionListener( action );

		}
		return menuMousePICKING;
	}
	
	protected JMenuItem getMenuMouseTRANSFORMING(){
		if ( menuMouseTRANSFORMING == null){
			menuMouseTRANSFORMING = new JRadioButtonMenuItem ( Configurations.getString("menu.mouse.TRANSFORMING") );
			menuMouseTRANSFORMING.addActionListener( action );
		}
		return menuMouseTRANSFORMING;
	}
}