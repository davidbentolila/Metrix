package br.ufpa.linc.MetriX.view.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.analysis.Histogram;
import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.configurations.Configurations;

@SuppressWarnings("serial")
public class FormOptionsAnalysis extends JDialog implements ActionListener, MouseListener {

	private JButton buttonOK = null, buttonCancel = null, selectAllAPIs = null, selectAllPackages = null;
	private JScrollPane scrollAPIs = null, scrollPackages = null;
	private MyJList APIlist = null, currentPackageList = null;
	private List<API> apis = null;
	private List<APIPakcageList> apiPackage = null;
	private JCheckBox checkBoxIL = null, checkBoxIS = null, checkBoxNM = null, checkBoxNP = null, checkBoxPPM = null;
	private JRadioButton radioPublicMethods = null, radioAllMethods = null;
	private JPanel mainPanel = null, panelWhichMetrics = null, panelWhichType = null, panelAPIs = null, panelPackages = null, panelButtons = null;
	private Histogram histogram = null;
	/**
	 * 
	 * @param apis - objects that will be showed in Jlist
	 * @param main - The {@link MetriX} class
	 * @param histogram - 
	 * 
	 */
	public FormOptionsAnalysis( List<API> apis, Histogram histogram) {
		super( MainWindow.getInstance() );
		setModal(true); 
		this.histogram = histogram;
		this.apis = apis;
		apiPackage = new ArrayList<APIPakcageList>();
		
		for (API api : this.apis) apiPackage.add( new APIPakcageList(api) );
		
		setTitle( Configurations.getString("window.title.chooseAPI") );
		setSize(640,480);
		setResizable(false);
		setLocationRelativeTo( MainWindow.getInstance() );

		setLayout( new BorderLayout() );
		add ( getMainPanel(), BorderLayout.CENTER );
		add ( getPanelButtons(), BorderLayout.SOUTH );
        setVisible(true);
	}

	private JPanel getMainPanel(){
		if ( mainPanel == null ){
			mainPanel = new JPanel( new GridLayout(0,2) );
			mainPanel.add( getPanelWhichMetrics() );
			mainPanel.add( getPanelWhichType() );
			mainPanel.add( getPanelAPIs() );
			mainPanel.add( getPanelPackages() );
		}
		return mainPanel;
	}
	
	private JPanel getPanelWhichMetrics(){
		if ( panelWhichMetrics == null ){
			panelWhichMetrics = new JPanel( new GridLayout(0,1));
			panelWhichMetrics.setBorder( new TitledBorder( Configurations.getString("message.whichMetricsUse")));
//			panelWhichMetrics.add( getCheckBoxAll() );
			panelWhichMetrics.add( getCheckBoxIL() );
			panelWhichMetrics.add( getCheckBoxIS() );
			panelWhichMetrics.add( getCheckBoxNM() );
			panelWhichMetrics.add( getCheckBoxNP() );
			panelWhichMetrics.add( getCheckBoxPPM() );
		}
		return panelWhichMetrics;
	}
	
//	private JCheckBox getCheckBoxAll(){
//		if ( checkBoxAll == null ){
//			checkBoxAll = new JCheckBox( Configurations.getString("word.all") );
//			checkBoxAll.addItemListener( this );
//		}
//		return checkBoxAll;
//	}
	
	private JCheckBox getCheckBoxIL(){
		if ( checkBoxIL == null ) checkBoxIL = new JCheckBox( Configurations.getString("metrics.IL") );
		return checkBoxIL;
	}
	
	private JCheckBox getCheckBoxIS(){
		if ( checkBoxIS == null )checkBoxIS = new JCheckBox( Configurations.getString("metrics.IS") );
		return checkBoxIS;
	}
	
	private JCheckBox getCheckBoxNM(){
		if ( checkBoxNM == null ) checkBoxNM = new JCheckBox( Configurations.getString("metrics.NM") );
		return checkBoxNM;
	}
	
	private JCheckBox getCheckBoxNP(){
		if ( checkBoxNP == null ) checkBoxNP = new JCheckBox( Configurations.getString("metrics.NP") );
		return checkBoxNP;
	}
	
	private JCheckBox getCheckBoxPPM(){
		if ( checkBoxPPM == null ) checkBoxPPM = new JCheckBox( Configurations.getString("metrics.PPM") );
		return checkBoxPPM;
	}
	
	private JPanel getPanelWhichType(){
		if ( panelWhichType == null ){
			panelWhichType = new JPanel( new GridLayout(0,1) );
			panelWhichType.setBorder( new TitledBorder( Configurations.getString("message.whichMethodsUse")));
			ButtonGroup bg = new ButtonGroup();
			
			bg.add( getRadioPublicMethods() );
			bg.add( getRadioAllMethods() );
			
			panelWhichType.add( getRadioPublicMethods() );
			panelWhichType.add( getRadioAllMethods() );
		}
		return panelWhichType;
	}
	
	private JRadioButton getRadioPublicMethods(){
		if ( radioPublicMethods == null ){
			radioPublicMethods = new JRadioButton( Configurations.getString("word.Public"));
			radioPublicMethods.setSelected( true );
		}
		return radioPublicMethods;
	}
	
	private JRadioButton getRadioAllMethods(){
		if ( radioAllMethods == null ){
			radioAllMethods = new JRadioButton( Configurations.getString("word.allMethods"));
		}
		return radioAllMethods;
	}
	
	private JPanel getPanelAPIs(){
		if ( panelAPIs == null ){
			panelAPIs = new JPanel( new BorderLayout() );
			panelAPIs.setBorder( new TitledBorder( Configurations.getString("window.title.chooseAPI")));
			panelAPIs.add ( getScrollAPIs(), BorderLayout.CENTER );
			panelAPIs.add ( getSelectAllAPIs(), BorderLayout.SOUTH );
		}
		return panelAPIs;
	}
	
	private JScrollPane getScrollAPIs(){
		if ( scrollAPIs == null ){
			scrollAPIs = new JScrollPane ( getAPIList(),
					ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		}
		return scrollAPIs;
	}
	
	private JButton getSelectAllAPIs(){
		if ( selectAllAPIs == null ){
			selectAllAPIs = new JButton( Configurations.getString("word.selectAllAPI") );
			selectAllAPIs.addActionListener( this );
		}
		return selectAllAPIs;
	}
	
	private MyJList getAPIList(){
		if ( APIlist == null ){
			APIlist = new MyJList( apis );
			APIlist.addMouseListener( this );
		}
        return APIlist;
	}
	
	private JPanel getPanelPackages(){
		if ( panelPackages == null ){
			panelPackages = new JPanel( new BorderLayout() );
			panelPackages.setBorder( new TitledBorder( Configurations.getString("window.title.choosePackages")));
			panelPackages.add ( getScrollPackages(), BorderLayout.CENTER );
			panelPackages.add ( getSelectAllPackages(), BorderLayout.SOUTH );
		}
		return panelPackages;
	}
	
	private JButton getSelectAllPackages(){
		if ( selectAllPackages == null ){
			selectAllPackages = new JButton( Configurations.getString("word.selectAllPackage"));
			selectAllPackages.addActionListener( this );
		}
		return selectAllPackages;
	}
	
	private JScrollPane getScrollPackages(){
		if ( scrollPackages == null ){
			scrollPackages = new JScrollPane ();
			scrollPackages.setHorizontalScrollBarPolicy( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );
			scrollPackages.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED );
		}
		return scrollPackages;
	}
	
	private JPanel getPanelButtons(){
		if ( panelButtons == null ){
			panelButtons = new JPanel();
			panelButtons.add( new JLabel() );
			panelButtons.add( getButtonOK() );
			panelButtons.add( new JLabel() );
			panelButtons.add( getButtonCancel() );
			panelButtons.add( new JLabel() );
		}
		return panelButtons;
	}
	
	private JButton getButtonOK(){
		if ( buttonOK == null ){
			buttonOK = new JButton( Configurations.getString("button.ok"));
			buttonOK.addActionListener( this );
		}
		return buttonOK;
	}
	
	private JButton getButtonCancel(){
		if ( buttonCancel == null ){
			buttonCancel = new JButton( Configurations.getString("button.cancel"));
			buttonCancel.addActionListener( this );
		}
		return buttonCancel;
	}

	private List<Integer> getSelectedMetrics(){
		List<Integer> metrics = new ArrayList<Integer>(); 
			if ( getCheckBoxIL().isSelected() ) metrics.add( 0 ) ;
			if ( getCheckBoxIS().isSelected() ) metrics.add( 1 ) ;
			if ( getCheckBoxNM().isSelected() ) metrics.add( 2 ) ;
			if ( getCheckBoxNP().isSelected() ) metrics.add( 3 ) ;
			if ( getCheckBoxPPM().isSelected() ) metrics.add( 4 ) ;
		
		return metrics;
	}
//	---------------------------------------------
//	                 Listeners
//	---------------------------------------------
	
//	public void itemStateChanged(ItemEvent ievt) {
//		if ( ievt.getSource() instanceof JCheckBox ){
//			if ( ievt.getSource().equals(getCheckBoxAll()) && getCheckBoxAll().isSelected() ) {
//				getCheckBoxIL().setSelected(true);
//				getCheckBoxIS().setSelected(true);
//				getCheckBoxNM().setSelected(true);
//				getCheckBoxNP().setSelected(true);
//				getCheckBoxPPM().setSelected(true);
//			}
//			else if (ievt.getSource().equals( getCheckBoxIL() ) || ievt.getSource().equals( getCheckBoxIS() ) ||
//					ievt.getSource().equals( getCheckBoxNM() ) || ievt.getSource().equals( getCheckBoxNP() ) || ievt.getSource().equals( getCheckBoxPPM() ) )
//					if ( !((JCheckBox)ievt.getSource()).isSelected() ) getCheckBoxAll().setSelected(false);
//		}
//	}

	@Override
	public void actionPerformed(ActionEvent aevt) {
		if ( aevt.getSource() instanceof JButton ){
			
			//click Cancel Button
			if ( aevt.getSource().equals( getButtonCancel() )) dispose();

			//click OK Button
			else if ( aevt.getSource().equals( getButtonOK() )) {
				int[] indexSelectedApis = getAPIList().getSelectedIndices();
				if ( indexSelectedApis.length == 0 ) JOptionPane.showMessageDialog(this, Configurations.getString("message.chooseAtLeast"), Configurations.getString("message.chooseAtLeast.titlle"), JOptionPane.ERROR_MESSAGE);
				else {
					
					boolean usePrivateMethods = getRadioAllMethods().isSelected();
					
					List<API> selectedApis = new ArrayList<API>();
					for (int i : indexSelectedApis) selectedApis.add( apis.get(i) );

					histogram.action( selectedApis, usePrivateMethods, getSelectedMetrics() );
//					dispose();
				}
			}
			
			//click Select All APIs Button
			else if ( aevt.getSource().equals( getSelectAllAPIs() )) {
				for ( int i : getAPIList().getSelectedIndices() ) getAPIList().removeSelectionInterval(i, i);
				getAPIList().setSelectionInterval(0, getAPIList().getModel().getSize());
			}
			
			//click Select All Packages Button
			else if ( aevt.getSource().equals( getSelectAllPackages() )) {
				for ( int i : currentPackageList.getSelectedIndices() ) currentPackageList.removeSelectionInterval(i, i);
				currentPackageList.setSelectionInterval(0, currentPackageList.getModel().getSize());
			}
		}
	}
	
	public void mouseEntered(MouseEvent mouseevent) {}
	public void mouseExited(MouseEvent mouseevent) {}
	public void mousePressed(MouseEvent mouseevent) {}
	public void mouseReleased(MouseEvent mouseevent) {}
	
	public void mouseClicked(MouseEvent mevt) {
		if ( mevt.getSource().equals( APIlist ) ) {
			currentPackageList = apiPackage.get( APIlist.locationToIndex(mevt.getPoint())).getPackages();
			getScrollPackages().setViewportView( currentPackageList );
		}
	}
	
	private class APIPakcageList {
//		private API api = null;
		private MyJList packages = null;
		
		public APIPakcageList( API api ) {
//			this.api = api;
			Collections.sort(api.getPackages());
			this.packages = new MyJList( api.getPackages() );
//			this.packages.selectAll();
		}
		
//		public API getApi() {
//			return api;
//		}

		public MyJList getPackages() {
			return packages;
		}

//		public void setApi(API api) {
//			this.api = api;
//		}

//		public void setPackages(MyJList packages) {
//			this.packages = packages;
//		}
		
	}
}
