package br.ufpa.linc.MetriX.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.dao.Database;
import br.ufpa.linc.MetriX.view.gui.menuToolBar.MenuBar;
import br.ufpa.linc.MetriX.view.gui.menuToolBar.MyToolBar;
import br.ufpa.linc.MetriX.view.jtree.JtreeSearchPanel;
import br.ufpa.linc.MetriX.view.treemap.TreeMapView;
import br.ufpa.linc.configuration.Files;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private static MainWindow mainWindow = null;
	
	private JDesktopPane mainPanel = null;
	
//	private JPanel topPanel = null;
	private JPanel panel = null;
	
	private MenuBar menuBar = null;
	
	private JInternalFrame currentInternalFrame = null;
	private List<JInternalFrame> internalFrames = null;
	
	private Dimension size = null;
	private MyToolBar toolBar = null;
	
	private MainWindow() {
		super();
		size = new Dimension(1000, 700);		
		initialize();
	}
	
	public synchronized static MainWindow getInstance() {
		if (mainWindow == null) mainWindow = new MainWindow();
		return mainWindow;
	}		

	private void initialize() {
		internalFrames = new ArrayList<JInternalFrame>();
		Splash.getInstance().getProgressBar().setValue(Splash.getInstance().getProgressBar().getValue()+1);
		setTitle( Configurations.getString("window.title.mainWindow") );
		
		Splash.getInstance().getProgressBar().setValue(Splash.getInstance().getProgressBar().getValue()+1);
		setJMenuBar( getTopMenuBar() );
		
		Splash.getInstance().getProgressBar().setValue(Splash.getInstance().getProgressBar().getValue()+1);
		setLayout(new BorderLayout());
		
		Splash.getInstance().getProgressBar().setValue(Splash.getInstance().getProgressBar().getValue()+1);
		add( getPanel(), BorderLayout.NORTH );
		
		Splash.getInstance().getProgressBar().setValue(Splash.getInstance().getProgressBar().getValue()+1);
		add( getMainPanel(), BorderLayout.CENTER);
		
		Splash.getInstance().getProgressBar().setValue(Splash.getInstance().getProgressBar().getValue()+1);
		setIconImage( new ImageIcon( getClass().getResource("/images/aboutIcon.png")).getImage() );
		
		Splash.getInstance().getProgressBar().setValue(Splash.getInstance().getProgressBar().getValue()+1);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		Splash.getInstance().getProgressBar().setValue(Splash.getInstance().getProgressBar().getValue()+1);
		pack();
		
		Splash.getInstance().getProgressBar().setValue(Splash.getInstance().getProgressBar().getValue()+1);
		addComponentListener( new ComponentListener(){
			public void componentHidden(ComponentEvent e) {}
			public void componentMoved(ComponentEvent e) {}
			public void componentResized(ComponentEvent e) {
				currentInternalFrame = getSelectedInternalFrame();
				if ( currentInternalFrame != null)
				try {
					currentInternalFrame.setMaximum(true);
				} catch (PropertyVetoException e1) {
					System.out.println("can't maximize");
				}
			}
			public void componentShown(ComponentEvent e) {}
		});

		addWindowListener( new WindowListener() {
			public void windowOpened(WindowEvent arg0) {}
			public void windowIconified(WindowEvent arg0) {}
			public void windowDeiconified(WindowEvent arg0) {}
			public void windowDeactivated(WindowEvent arg0) {}
			public void windowClosing(WindowEvent arg0) {
				Files.clearDir(Configurations.getTempDir());
				Database.closeDatabase();
			}
			public void windowClosed(WindowEvent arg0) {}
			public void windowActivated(WindowEvent arg0) {}
		});
		Splash.getInstance().getProgressBar().setValue(Splash.getInstance().getProgressBar().getValue()+1);
		setSize(size);
		
		Splash.getInstance().getProgressBar().setValue(Splash.getInstance().getProgressBar().getValue()+1);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		Splash.getInstance().getProgressBar().setValue(Splash.getInstance().getProgressBar().getValue()+1);
		setLocationRelativeTo(null);
		
		Splash.getInstance().getProgressBar().setValue(Splash.getInstance().getProgressBar().getValue()+1);
		Splash.getInstance().getProgressBar().setString(Configurations.getString("splash.end"));
		Splash.getInstance().dispose();

		setVisible(true);
	}

	private JMenuBar getTopMenuBar() {
		if (menuBar == null) {
			menuBar = new MenuBar(this);
		}
		return menuBar;
	}
	
	private JPanel getPanel(){
		if ( panel == null ){
			panel = new JPanel();
			panel.setLayout( new BorderLayout());
			panel.add( getToolBar(), BorderLayout.NORTH );
//			panel.add( getTopPanel(), BorderLayout.CENTER);
		}
		return panel;
	}
	
	private MyToolBar getToolBar(){
		if ( toolBar == null ){
			toolBar = new MyToolBar();
		}
		return toolBar;
	}
	
//	private JPanel getTopPanel(){
//		if ( topPanel == null ){
//			topPanel = new TopPanel(MetriX.getInstance(), this);
//		}
//		return topPanel;
//	}

	private JDesktopPane getMainPanel(){
		if ( mainPanel == null ) {
			mainPanel = new JDesktopPane();
			mainPanel.setLayout(new BorderLayout());
			mainPanel.setBackground( Color.DARK_GRAY);
			ImageIcon image = new ImageIcon(getClass().getResource("/images/backLogo_small.png"));
			JLabel bkg = new JLabel( image );
			bkg.setVerticalAlignment(JLabel.TOP);
			bkg.setHorizontalAlignment(JLabel.RIGHT);
			mainPanel.add(bkg, BorderLayout.CENTER);
		}
		return mainPanel;
	}
	
	public void addInternalFrame(String title){
		currentInternalFrame = new JInternalFrame(); 
		
		currentInternalFrame.setLayout( new BorderLayout() );
//		currentInternalFrame.setSize( getMainPanel().getBounds().width, getMainPanel().getBounds().height);
		currentInternalFrame.setName(String.valueOf(internalFrames.size()-1));
		currentInternalFrame.setResizable(true);
		currentInternalFrame.setClosable(true);
//		currentInternalFrame.setMaximizable(true);
		currentInternalFrame.setIconifiable(true);
		currentInternalFrame.setFrameIcon( new ImageIcon( getClass().getResource("/images/analysis.png")) );
		currentInternalFrame.setTitle( MetriX.getInstance().isRelativeInternal() ? title + " (" + Configurations.getString("word.internally") + ")" : title);
		currentInternalFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		currentInternalFrame.addInternalFrameListener( new InternalFrameListener(){
			public void internalFrameActivated(InternalFrameEvent e) {
				setCurrentInternalFrame((JInternalFrame) e.getInternalFrame());
				setTitle( Configurations.getString("window.title.mainWindow") + " - " + e.getInternalFrame().getTitle());
			}
			public void internalFrameClosed(InternalFrameEvent e) {
			}
			public void internalFrameClosing(InternalFrameEvent e) {
				internalFrames.remove(e.getInternalFrame());
				((JInternalFrame)e.getSource()).dispose();
				if ( internalFrames.size() == 0 ) currentInternalFrame = null;
			}
			public void internalFrameDeactivated(InternalFrameEvent e) {
			}
			public void internalFrameDeiconified(InternalFrameEvent e) {
			}
			public void internalFrameIconified(InternalFrameEvent e) {
			}
			public void internalFrameOpened(InternalFrameEvent e) {
			}
		});
		
		JSplitPane sp = new JSplitPane();
		sp.setOneTouchExpandable(true);
		sp.setLeftComponent(getPanelJTree());		
		
		sp.setRightComponent(getTabbedPane());
		currentInternalFrame.add(sp , BorderLayout.CENTER);
		getMainPanel().add( currentInternalFrame );
		currentInternalFrame.requestFocus();
		internalFrames.add( currentInternalFrame );
		currentInternalFrame.setVisible(true);
		MetriX.getInstance().getStatus().dispose();
	}
	
	public void compareVisualizations(){
		if ( internalFrames.size() < 2 ) {
			JOptionPane.showMessageDialog(this, Configurations.getString("message.error.compareView"), Configurations.getString("window.title.error"), JOptionPane.ERROR_MESSAGE);			
			return;
		}
		
		JInternalFrame last = internalFrames.get( internalFrames.size() - 1 ),
		penult = internalFrames.get( internalFrames.size() - 2 );
		int w = getWidth() /2;

		penult.setBounds(0, 0, w, getHeight());
		last.setBounds(penult.getWidth() + 20, 0, w, getHeight());
	}
	
	//treemap functions
	private JInternalFrame getSelectedInternalFrame(){
		for( JInternalFrame ji : internalFrames)
			if ( ji.isSelected() ){
					return ji;
			}
		if ( internalFrames.size() > 0 ) return internalFrames.get( internalFrames.size() -1 );
		
		return null;
	}
	
	public void openInternalFrame(JInternalFrame internalFrame){
		getMainPanel().add( internalFrame );
		setCurrentInternalFrame(internalFrame);
	}
	
	//jTree actions
	private JScrollPane getScrollMyJTree(){
		JScrollPane scrollTree = new JScrollPane();
		scrollTree.setName("scrollTree");
		scrollTree.setMinimumSize(new Dimension(100,80));
		scrollTree.setViewportView( MetriX.getInstance().getMyJtree() );
		return scrollTree;
	}
	
	private JPanel getTreeMap(){
		TreeMapView t = new TreeMapView();
		return t.getTreeMap();
	}
	
	private JTabbedPane getTabbedPane(){
		JTabbedPane tp = new JTabbedPane();
		tp.addTab(Configurations.getString("tab.title.complexity"), new ImageIcon(getClass().getResource("/images/treemap24.png")), getTreeMap());
		tp.setTabComponentAt( tp.getTabCount() -1 , new TabCloseButton( tp ));
		
//		tp.addTab(Configurations.getString("tab.title.starPlot"), new ImageIcon(getClass().getResource("/images/starplot24.png")), MetriX.getInstance().getStarPlotPanel());
//		tp.setTabComponentAt( tp.getTabCount() -1 , new TabCloseButton( tp ));
//		
//		tp.addTab(Configurations.getString("tab.title.dependency"), new ImageIcon(getClass().getResource("/images/graph24.png")), MetriX.getInstance().getMyGraph());
//		tp.setTabComponentAt( tp.getTabCount() -1 , new TabCloseButton( tp ));

		tp.setSelectedIndex(MetriX.getInstance().getSelectedTab());
		
		return tp;
	}
	/* *********************************************
	private JTabbedPane getTabbedPaneJtree(){
		JTabbedPane tp = new JTabbedPane();
		
		JPanel panelJTree = new JPanel(new BorderLayout());
		panelJTree.add( new JLabel(Configurations.getString("tab.title.APIstructure")), BorderLayout.CENTER );
		panelJTree.add( new JtreeSearchPanel(), BorderLayout.SOUTH );
		
		panelJTree.add( getScrollMyJTree(), BorderLayout.CENTER );
		panelJTree.add( new JtreeSearchPanel(), BorderLayout.SOUTH );
//		tp.addTab(Configurations.getString("tab.title.APIstructure"), panelJTree);
//		tp.addTab(Configurations.getString("tab.title.packages"), MetriX.getInstance().getJTreeCheckBox());
		
		tp.setSelectedIndex(0);
		
		return tp;
	}

	**********************************/
	
	private JPanel getPanelJTree(){
		JPanel panelJTree = new JPanel(new BorderLayout());
		panelJTree.setPreferredSize( new Dimension(200,400) );
		panelJTree.add( getScrollMyJTree(), BorderLayout.CENTER );
		panelJTree.add( new JtreeSearchPanel(), BorderLayout.SOUTH );

		return panelJTree;
	}
	
	public void setVisiblePanels(boolean bool){
		toolBar.setVisible(bool);
//		topPanel.setVisible(bool);
		currentInternalFrame.setMaximizable(true);
	}
	
	public JInternalFrame getCurrentInternalFrame() {
		return currentInternalFrame;
	}

	public void setCurrentInternalFrame(JInternalFrame currentInternalFrame) {
		this.currentInternalFrame = currentInternalFrame;
	}
	
	public void changeLanguage(){
		
//		remove(panel);
		size = getSize();
//		mainWindow = null;
		menuBar = null;
//		panel = null;
		toolBar = null;
		initialize();
		
//		SwingUtilities.updateComponentTreeUI(this);
//		MainWindow.getInstance().validate();
	}
}