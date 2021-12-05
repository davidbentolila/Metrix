package br.ufpa.linc.MetriX.view.gui.insert;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.configurations.Files;
import br.ufpa.linc.MetriX.metricModel.MetricModel;
import br.ufpa.linc.MetriX.metrics.Metric;
import br.ufpa.linc.MetriX.parsers.java.CsvReader;
import br.ufpa.linc.MetriX.view.gui.MainWindow;
import br.ufpa.linc.MetriX.view.gui.Status;

public class FormAPIChooseImport extends JDialog implements ActionListener, ChangeListener {

	private class MetricsListModel implements ComboBoxModel{
		int index;
		List<Object> metrics;
		private Set<ListDataListener> listDataListeners;
		
		MetricsListModel(){
			metrics = new ArrayList<Object>();
			metrics.add("[None]");
			index = 0;
			listDataListeners = new HashSet<ListDataListener>();
		}
		
		@Override
		public Object getSelectedItem() {
			return metrics.get(index);
		}

		@Override
		public void setSelectedItem(Object arg0) {
			index = metrics.indexOf(arg0);
		}

		@Override
		public void addListDataListener(ListDataListener arg0) {
			listDataListeners.add(arg0);
		}

		@Override
		public Object getElementAt(int arg0) {
			return metrics.get(arg0);
		}

		@Override
		public int getSize() {
			return metrics.size();
		}

		@Override
		public void removeListDataListener(ListDataListener arg0) {
			listDataListeners.remove(arg0);
		}
		
		public void alterMetrics(String[] objs){
			metrics.clear();
			metrics.add("{None}");
			metrics.addAll(Arrays.asList(objs));
			for (ListDataListener listDataListener : listDataListeners) {
				listDataListener.intervalAdded(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, 1, metrics.size()));
			}
		}
		
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -2492742290963401711L;

	private JTextField tfFile = null;
	
	private JLabel lbPackageSelector, lbEntitySelector, lbMethodSelector;
	
	private JButton btOk, btCancel, btFile;

	private File csvFile;

	private JSpinner spMaxNum;

	private JCheckBox cbUseMaxNum;

	private CsvReader csvreader;

	private JPanel pnChooseFile, pnLimitLines, pnCorrespondence, pnButtons ;

	private JScrollPane spCorrespondence;

	private JComboBox cbPackageSelector, cbMethodSelector, cbEntitySelector;

	private List<JLabel> lbMetrics;

	private List<JComboBox> cbMetrics;

	private List<MetricsListModel> metricsListModels;

	private List<Metric> metrics;
	
	private Map<Metric,Integer> metricsCorrespondence;

//	private JPanel pnMaxNum;
	
	public FormAPIChooseImport() {
		super(MainWindow.getInstance());
		metricsListModels =  new LinkedList<MetricsListModel>();
		setModal(true);
		initComponents();
		setLocationRelativeTo( MainWindow.getInstance() );
		setVisible(true);
	}

	private void initComponents() {
		setTitle("Import API");
		setSize(500,320);
		setResizable(false);
		setMaximumSize(getSize());
		setLayout(null);

		add( getPnChooseFile() );
		add( getPnLimitLines() );
		add( getSpCorrespondence() );
		add( getPnButtons() );
	}

	private JPanel getPnChooseFile() {
		if(pnChooseFile==null){
			pnChooseFile = new JPanel();
			pnChooseFile.setBorder( BorderFactory.createTitledBorder("Choose File"));
			pnChooseFile.setBounds(0, 0, getWidth(), 60);
			pnChooseFile.add( getTfFile() );
			pnChooseFile.add( getBtFile() );
		}
		return pnChooseFile;
	}

	private JButton getBtFile() {
		if(btFile==null){
			btFile = new JButton("Choose File");
			btFile.addActionListener( this );
		}
		return btFile;
	}

	private JTextField getTfFile() {
		if(tfFile==null){
			tfFile = new JTextField();
			tfFile.setEditable(false);
			tfFile.setPreferredSize(new Dimension( getSize().width - 120, 20));
		}
		return tfFile;
	}
	
	private JPanel getPnLimitLines() {
		if(pnLimitLines==null){
			pnLimitLines = new JPanel();
			((FlowLayout)pnLimitLines.getLayout()).setAlignment(FlowLayout.LEFT);
			pnLimitLines.setBounds(0, getPnChooseFile().getY() + getPnChooseFile().getHeight() + 5 , getWidth(), 30);
			pnLimitLines.add( getCbUseMaxNum() );
			pnLimitLines.add( getSpMaxNum() );
		}
		return pnLimitLines;
	}
	
	private JCheckBox getCbUseMaxNum() {
		if(cbUseMaxNum==null){
			cbUseMaxNum = new JCheckBox("Read limited number of lines");
			cbUseMaxNum.setSelected(true);
			cbUseMaxNum.addChangeListener(this);
		}
		return cbUseMaxNum;
	}

	private JSpinner getSpMaxNum() {
		if(spMaxNum == null){
			spMaxNum = new JSpinner(new SpinnerNumberModel(2000,50,20000,50));
			spMaxNum.setPreferredSize(new Dimension(100,20));
		}
		return spMaxNum;
	}

	private JScrollPane getSpCorrespondence() {
		if( spCorrespondence == null){
			spCorrespondence = new JScrollPane(getPnCorrespondence());
			spCorrespondence.setBorder( BorderFactory.createTitledBorder("Choose Correspondence:") );
			spCorrespondence.setBounds(0, getPnLimitLines().getY() + getPnLimitLines().getHeight() + 10 , getWidth(), 155);
		}
		return spCorrespondence;
	}

	private JPanel getPnCorrespondence() {
		if(pnCorrespondence==null){
			setupCorresponce();
//			pnCorrespondence.setSize(new Dimension(50,50));
		}
		return pnCorrespondence;
	}
	
	private void setupCorresponce() {
		pnCorrespondence = new JPanel(new GridLayout(0,2));
		pnCorrespondence.add(getLbPackageSelector());
		pnCorrespondence.add(getCbPackageSelector());
		pnCorrespondence.add(getLbEntitySelector());
		pnCorrespondence.add(getCbEntitySelector());
		pnCorrespondence.add(getLbMethodSelector());
		pnCorrespondence.add(getCbMethodSelector());
		List<JLabel>metricLabels = getLbMetrics();
		List<JComboBox>metricCombos = getCbMetrics();
//		int size = metricLabels.size();
		Iterator<JLabel> it = metricLabels.iterator();
		Iterator<JComboBox> jt = metricCombos.iterator();
		while (it.hasNext() && jt.hasNext()) {
			JLabel jLabel = (JLabel) it.next();
			JComboBox jComboBox = (JComboBox) jt.next();
			pnCorrespondence.add(jLabel);
			pnCorrespondence.add(jComboBox);
		}
	}


	private List<JLabel> getLbMetrics() {
		if(lbMetrics==null){
			lbMetrics = new LinkedList<JLabel>();
			for (Metric metric : getMetrics()) {
				lbMetrics.add(new JLabel(metric.getLabel()));
			}
		}
		return lbMetrics;
	}

	private List<Metric> getMetrics() {
		if(metrics==null){
			metrics = new LinkedList<Metric>();
			for (MetricModel mModel : MetriX.getInstance().getMetricModels()) {
				metrics.addAll(mModel.getMetrics());
			}
		}
		return metrics ;
	}

	private JLabel getLbMethodSelector() {
		if(lbMethodSelector==null){
			lbMethodSelector = new JLabel("Method: ");
		}
		return lbMethodSelector;
	}

	private JLabel getLbEntitySelector() {
		if(lbEntitySelector==null){
			lbEntitySelector = new JLabel("Entity: ");
		}
		return lbEntitySelector;
	}

	private JLabel getLbPackageSelector() {
		if(lbPackageSelector==null){
			lbPackageSelector = new JLabel("Package: ");
		}
		return lbPackageSelector;
	}

	private JComboBox getCbMethodSelector() {
		if(cbMethodSelector==null){
			cbMethodSelector = new JComboBox(getOptions());
//			cbMethodSelector.addActionListener(this);
		}
		return cbMethodSelector;
	}

	private JComboBox getCbEntitySelector() {
		if(cbEntitySelector==null){
			cbEntitySelector = new JComboBox(getOptions());
//			cbEntitySelector.addActionListener(this);
		}
		return cbEntitySelector;
	}

	private JComboBox getCbPackageSelector() {
		if(cbPackageSelector==null){
			cbPackageSelector = new JComboBox(getOptions());
//			cbEntitySelector.addActionListener(this);
		}
		return cbPackageSelector;
	}

	private List<JComboBox> getCbMetrics() {
		if(cbMetrics == null){
			cbMetrics = new LinkedList<JComboBox>();
			for (Metric metric : getMetrics()) {
				JComboBox cb = new JComboBox(getOptions());
				cb.setName(metric.getLabel());
				cb.addActionListener(this);
				cbMetrics.add(cb);
			}
		}
		return cbMetrics;
	}

	private MetricsListModel getOptions() {
		MetricsListModel model = new MetricsListModel();
		metricsListModels.add(model);
		return model;
	}
	
	private JPanel getPnButtons() {
		if(pnButtons == null){
			pnButtons = new JPanel();
			((FlowLayout)pnButtons.getLayout()).setAlignment(FlowLayout.RIGHT);
			pnButtons.add( getBtOk() );
			pnButtons.add( getBtCancel() );
			pnButtons.setBounds(0, getSpCorrespondence().getY() + getSpCorrespondence().getHeight() + 3 , getWidth(), 30);
		}
		return pnButtons;
	}

	private JButton getBtOk() {
		if(btOk == null){
			btOk = new JButton("Ok");
			btOk.addActionListener(this);
			btOk.setPreferredSize(new Dimension(70, 20));
			btOk.setEnabled(false);
		}
		return btOk;
	}

	private JButton getBtCancel() {
		if(btCancel==null){
			btCancel = new JButton("Cancel");
			btCancel.addActionListener(this);
			btCancel.setPreferredSize(new Dimension(70, 20));
		}
		return btCancel;
	}

	public void actionPerformed(ActionEvent action) {
		if(action.getSource().equals( getBtOk() )){
			setVisible(false);
			if ( csvFile == null ) return;
			MetriX.getInstance().setStatus( new Status("Inserting", 2));
//			final Thread t = new Thread(MetriX.getInstance().getStatus());
//			t.start();
			Thread t2 = new Thread(){
				@Override
				public void run() {
					System.out.println("Inserting");
//					MetriX.getInstance().getStatus().updateProgressBar("Inserting to Database");
//					MetriX.getInstance().getStatus().getProgress().setIndeterminate(true);
//					MetriX.getInstance().getStatus().getProgress().setString("");
//					csvreader = new CsvReader(getTfFile().getText(),main);
					csvreader.setPackageCollumn(getCbPackageSelector().getSelectedIndex()-1);
					csvreader.setEntityCollumn(getCbEntitySelector().getSelectedIndex()-1);
					csvreader.setMethodCollumn(getCbMethodSelector().getSelectedIndex()-1);
					csvreader.setMetricsCollumns(metricsCorrespondence);
					csvreader.parse((Integer)getSpMaxNum().getValue());
//					t.interrupt();
//					MetriX.getInstance().getStatus().dispose();
//					interrupt();
//					this.interrupt();
//					JOptionPane.showMessageDialog(null, Configurations.getLanguage().getString("message.dao.inserted"));
					
				}
			};
			t2.start();
			dispose();
		}
		
		if(action.getSource().equals( getBtCancel() )) dispose();

		
		if ( action.getSource().equals( getBtFile() )){
			csvFile = Files.getFile("csv"); 
			if ( csvFile != null ){
//				if ( tfAPIName.getText().isEmpty() ) tfAPIName.setText( csvFile.getName() );
				tfFile.setText( csvFile.getAbsolutePath() );
				csvreader = new CsvReader(csvFile.getAbsolutePath());
//				setupCorresponce();
				actualizeCorrespondence();
				getMetricsCorrespondence().clear();
				getBtOk().setEnabled(true);
			}
		}
		else if ( cbMetrics.contains(action.getSource())){
			JComboBox cbMetric = (JComboBox) action.getSource();
			int index = cbMetrics.indexOf(cbMetric);
			Metric metric = metrics.get(index);
			Map<Metric, Integer> metricMap = getMetricsCorrespondence();
			int value = cbMetric.getSelectedIndex();
			if(value==0){
				metricMap.remove(metric);
			}
			else {
				metricMap.put(metric, value-1);
			}
		}
	}

	private void actualizeCorrespondence() {
		String[] objs = {};
		if(csvreader != null){
			objs= csvreader.getTitles();
//			objs = titles; //{"None","meticA","metricB"};
		}
		for (MetricsListModel metricsListModel : metricsListModels) {
			metricsListModel.alterMetrics(objs);
		}
	}

	public void stateChanged(ChangeEvent ce) {
		if ( ce.getSource().equals( getCbUseMaxNum() ) ) getSpMaxNum().setEnabled( getCbUseMaxNum().isSelected() );
	}

	public Map<Metric,Integer> getMetricsCorrespondence() {
		if(metricsCorrespondence==null){
			metricsCorrespondence = new HashMap<Metric, Integer>();
		}
		return metricsCorrespondence;
	}
}
