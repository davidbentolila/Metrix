package br.ufpa.linc.MetriX.report;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;

import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Class;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Interface;
import br.ufpa.linc.MetriX.api.model.Method;
import br.ufpa.linc.MetriX.api.model.Package;
import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.configurations.Files;
import br.ufpa.linc.MetriX.view.gui.MainWindow;
import br.ufpa.linc.MetriX.view.gui.TabCloseButton;
import br.ufpa.linc.MetriX.view.jtree.MyJTree;

public class ReportBugColor extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5805604910070698806L;
	private List<Object> reportGoals = null;
	private JTabbedPane tabbedPane = null;
	private JPanel panel = null, panelButton = null;
	private JButton buttonOK = null;
	private StringBuffer reportCSV = null;
	
	private double totalIS_partial = 0, totalNA_partial = 0, totalNM_partial = 0,
	totalIS = 0, totalNA = 0, totalNM = 0;// totalOAC_partial = 0,totalOAC = 0, 
	private DefaultTableModel reportModel = null;
	private Icon apiIcon, packageIcon, classIcon, interfaceIcon, methodIcon;
	private double totalCalls_partial=0;
	private double totalCalls =0;

	public ReportBugColor() {
		reportCSV = new StringBuffer();
		
		if (validateReport()) addToTab();
	}

	private boolean validateReport() {
		if (validateVisualization()) {
			if (validateJtreeSelection())
				return true;
			else
				return false;
		} else
			return false;
	}

	private boolean validateVisualization() {
		if (MainWindow.getInstance().getCurrentInternalFrame() == null) {
			JOptionPane.showMessageDialog(MainWindow.getInstance(), Configurations
					.getString("message.error.report.visualization"));
			return false;
		}
		return true;
	}

	private boolean validateJtreeSelection() {
		JRootPane jrp = (JRootPane) MainWindow.getInstance()
				.getCurrentInternalFrame().getComponent(0);
		JLayeredPane jlp = (JLayeredPane) jrp.getComponent(1);
		JPanel jp = (JPanel) jlp.getComponent(0);
		JSplitPane jsp = (JSplitPane) jp.getComponent(0);
		JTabbedPane jtp = (JTabbedPane) jsp.getLeftComponent();
		jp = (JPanel) jtp.getComponent(0);
		JScrollPane jspa = (JScrollPane) jp.getComponent(0);
		JViewport jvp = (JViewport) jspa.getComponent(0);
		MyJTree tree = (MyJTree) jvp.getComponent(0);
		jvp = (JViewport) tree.getParent();
		jspa = (JScrollPane) jvp.getParent();
		jp = (JPanel) jspa.getParent();
		jtp = (JTabbedPane) jp.getParent();
		jsp = (JSplitPane) jtp.getParent();
		tabbedPane = (JTabbedPane) jsp.getRightComponent();

		reportGoals = tree.getSelectedObjects();
		if (reportGoals.size() == 0) {
			JOptionPane.showMessageDialog(MainWindow.getInstance(), Configurations
					.getString("message.error.report.jtree"));
			return false;
		}
		return true;
	}

	@SuppressWarnings("serial")
	public void initTable() {
		
		reportModel = new DefaultTableModel() {
			public java.lang.Class<?> getColumnClass(int column) {
				if ( column == 0) return ImageIcon.class;
				else return Object.class;//getValueAt(0, column).getClass();
			}

			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		
		reportModel.addColumn(Configurations.getString("table.title.type"));
		reportModel.addColumn(Configurations.getString("table.title.name"));
		reportModel.addColumn(Configurations.getString("table.title.is"));
		reportModel.addColumn(Configurations.getString("table.title.na"));
		reportModel.addColumn(Configurations.getString("table.title.nm"));
		reportModel.addColumn(Configurations.getString("table.title.ppm"));
		reportModel.addColumn(Configurations.getString("table.title.calls"));

		apiIcon = new ImageIcon( getClass().getResource("/images/api.png"));
		packageIcon = new ImageIcon( getClass().getResource("/images/package.png"));
		classIcon = new ImageIcon( getClass().getResource("/images/class.png"));
		interfaceIcon = new ImageIcon( getClass().getResource("/images/interface.png"));
		methodIcon = new ImageIcon( getClass().getResource("/images/method.png"));
	}

	private String getName(String name, int level, boolean depedency){
		if ( depedency || level > 1 ) return "<html><i>" + name + "</i></html>";
		else if ( level == 0 ) return "<html><b>" + name + "</b></html>";
		return name;
	}
	
	private void addToTab(){
		tabbedPane.addTab(Configurations.getString("menu.report"),
				new ImageIcon( getClass().getResource("/images/report24.png")),
				getPanel() );
		tabbedPane.setTabComponentAt( tabbedPane.getTabCount() -1 , new TabCloseButton( tabbedPane ));
		
	}
	
	private ReportBugColor generateReport() {
		initTable();
//		reportCSV.append("#MetriX Report\n");
		
		totalIS = 0; totalNA = 0; totalNM = 0;//totalOAC = 0; 
		//Cabeçalho
		reportCSV.
		append(Configurations.getString("table.title.type")).append("; ").
		append(Configurations.getString("table.title.name")).append("; ").
		append(Configurations.getString("table.title.is")).append("; ").
		append(Configurations.getString("table.title.na")).append("; ").
		append(Configurations.getString("table.title.nm")).append("; ").
		append(Configurations.getString("table.title.ppm")).append("; ").
		append(Configurations.getString("table.title.calls")).append("\n");
		for (Object o : reportGoals) {
			totalIS_partial = 0; totalNA_partial = 0; totalNM_partial = 0; totalCalls_partial = 0;//totalOAC_partial = 0;
			try {
				if (o instanceof API) {
					getAPIData((API) o);
					break;
				} else if (o instanceof Package)
					getPackageData((Package) o, 0);
				else if (o instanceof Entity)
					getEntityData((Entity) o, false, 0, false, true, false);
				else if (o instanceof Method)
					getMethodData((Method) o, 0, true);
			}
			catch (Exception e ){
				return null;
			}
			//Totals
			reportCSV.append(" ; ; ; ; ; ; \n");
			reportCSV.append(" ;").
			append(Configurations.getString("table.title.total_partial")).append(":; ").
			append(totalIS_partial).append("; ").
			append(totalNA_partial).append("; ").
			append(totalNM_partial).append("; ").
			append((double) totalNA_partial/totalNM_partial).append("; ").
			append(totalCalls_partial).append("\n");
			
			reportCSV.append(" ; ; ; ; ; ; \n");
			reportModel.addRow(new Object[] {"", "", "", "", "", "", ""});
			reportModel.addRow(new Object[] {"", Configurations.getString("table.title.total_partial") + ":", totalIS_partial, totalNA_partial, totalNM_partial, (double) totalNA_partial/totalNM_partial, totalCalls_partial}); 
			reportModel.addRow(new Object[] {"", "", "", "", "", "", ""});
			 totalIS += totalIS_partial;
			 totalNA += totalNA_partial;
			 totalNM += totalNM_partial;
			 totalCalls += totalCalls_partial;
		}
		reportCSV.append(" ;").
		append(Configurations.getString("table.title.total_full")).append(":").append("; ").
		append(totalIS).append("; ").
		append(totalNA).append("; ").
		append(totalNM).append("; ").
		append((double) totalNA/ totalNM).append("; ").
		append(totalCalls).append("\n");
		
		reportModel.addRow(new Object[] {"", Configurations.getString("table.title.total_full") + ":", totalIS, totalNA, totalNM, (double) totalNA/ totalNM, totalCalls});//totalOAC, 

		JTable tableReport = new JTable(reportModel);
		tableReport.setDefaultRenderer(Object.class, new MyJtable());
		
		setViewportView(tableReport);
		
		return this;
	}
	
	private JPanel getPanel(){
		if ( panel == null ){
			panel = new JPanel( new BorderLayout() );
			panel.add( generateReport(), BorderLayout.CENTER );
			panel.add( getPanelButton(), BorderLayout.SOUTH );
		}
		return panel;
	}
	
	private JPanel getPanelButton(){
		if ( panelButton == null ){
			panelButton = new JPanel();
			panelButton.add( new JLabel() );
			panelButton.add( getButtonOK() );
			panelButton.add( new JLabel() );
		}
		return panelButton;
	}
	
	private JButton getButtonOK(){
		if ( buttonOK == null ){
			buttonOK = new JButton( Configurations.getString("button.exportReport"));
			buttonOK.addActionListener( new ActionListener(){
				public void actionPerformed(ActionEvent actionevent) {
					Files.saveCSV(reportCSV, "repport");
				}
			});
		}
		return buttonOK;
	}
	
	private void getAPIData(API api) throws Exception {
		double is = api.getMetricsValues().getIS(),
		na = api.getMetricsValues().getNP(),
		nm = api.getMetricsValues().getNM(), 
		app = api.getMetricsValues().getPPM();
		double calls = api.getMetricsValues().getCalls();
		
		totalIS_partial += is; 
		totalNA_partial += na; 
		totalNM_partial += nm;//totalOAC_partial += oac; 
		totalCalls_partial += calls;
		
		reportCSV.
		append("API; ").
		append(api.getNome()).append("; ").
		append(is).append("; ").
		append(na).append("; ").
		append(nm).append("; ").
		append(app).append("; ").
		append(calls).append("\n");
		reportModel.addRow(new Object[] {apiIcon, getName(api.getNome(), 0, false), is, na, nm, app, calls });//oac,
		
		for (Package p : api.getPackages()) getPackageData(p, 1);
//		}
	}

	private void getPackageData(Package p, int level) throws Exception {
		double is = p.getMetricsValues().getIS(),
		na = p.getMetricsValues().getNP(),
		nm = p.getMetricsValues().getNM(), 
		app = p.getMetricsValues().getPPM(), 
		calls = p.getMetricsValues().getCalls();
		
		totalIS_partial += is; totalNA_partial += na; totalNM_partial += nm;//totalOAC_partial += oac;
		totalCalls_partial += calls;
		
		reportCSV.
		append("Package; ").
		append(p.getName()).append(	"; ").
        append(is).append("; ").
		append(na).append("; ").
		append(nm).append("; ").
		append(app).append("; ").
		append(calls).append("\n");
		reportModel.addRow(new Object[] {packageIcon, getName(p.getName(), level, false), is, na, nm, app, calls });//,oac

		for (Entity e : p.getEntities()) {
			getEntityData(e, false, level + 1, true, false, false);
		}
	}

	private void getEntityData(Entity e, boolean isDependency, int level, boolean generateMethods,
			boolean generateDependencies, boolean showPackage) throws Exception {
		
		double is = e.getMetricsValues().getIS(),
		na = e.getMetricsValues().getNP(),
		nm = e.getMetricsValues().getNM(), 
		ppm = e.getMetricsValues().getPPM(), calls = e.getMetricsValues().getCalls();
		
		totalIS_partial += is; totalNA_partial += na; totalNM_partial += nm;//totalOAC_partial += oac;
		totalCalls_partial += calls;
		
		String name = "";
		if ( showPackage ) name = e.getFullName();
		else name = e.getName();
		
		if ( e instanceof Class ){
			reportCSV.
			append("Class; ").
			append(name).append("; ").
			append(is).append("; ").
			append(na).append("; ").
			append(nm).append("; ").
			append(ppm).append("; ").
			append(calls).append("\n");
			reportModel.addRow(new Object[] {classIcon, getName(name,level, isDependency), is, na, nm, ppm, calls });//oac,
		}
		else if ( e instanceof Interface ) {
			reportCSV.
			append("Interface; ").
			append(name).append("; ").
			append(is).append("; ").
			append(na).append("; ").
			append(nm).append("; ").
			append(ppm).append("; ").
			append(calls).append("\n");
			reportModel.addRow(new Object[] {interfaceIcon, getName(name,level, isDependency), is, na, nm, ppm, calls });//oac,
		}

		if (generateMethods)
			for (Method m : e.getMethods())
				if (m.getModifier().equals("public"))
					getMethodData(m, level+1, false);

		if (generateDependencies)
			for (Entity e1 : e.getDependencies("public"))
				getEntityData(e1, true, level + 1, false, false, true);
	}

	private void getMethodData(Method m, int level, boolean generateDependencies)
			throws Exception {
		
		double is = m.getMetricsValues().getIS();
	
		totalIS_partial += is; totalNA_partial += 0; totalNM_partial += 0;//totalOAC_partial += oac; 
		
		
		reportCSV.
		append("Method; ").
		append(m.getName()).append("; ").
		append(is).append("; ").
		append("-; ").
		append("-; ").
		append("-; ").
		append("-\n");
		reportModel.addRow(new Object[] {methodIcon, getName(m.getName(), level, false), is,  "-", "-", "-", "-"});//oac, 
		if (generateDependencies)
			for (Entity e : m.getDependencies())
				getEntityData(e, true, level + 1, false, false, true);
	}
	
}
