package br.ufpa.linc.MetriX.view.gui.insert;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.metricModel.MetricModel;
import br.ufpa.linc.MetriX.view.gui.MainWindow;

public class FormChooseMetric extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3876567692082206163L;
	private JScrollPane spMetrics =null;
	private JPanel pnMetrics = null;
	private List<JCheckBox> cbMetrics =null;
	private JPanel pnButtons = null;
	private JButton btCancel;
	private JButton btOk;
	private JDialog jd;
	
	public FormChooseMetric(JDialog jd) {
		super(MainWindow.getInstance(),Configurations.getString("window.title.metrics"));
		add(getSpMetrics(), BorderLayout.CENTER);
		add(getPnButtons(), BorderLayout.SOUTH);
		setSize(350,180);
		this.jd = jd;
		jd.setVisible(false);
		setLocationRelativeTo( jd );
		setVisible(true);
	}
	
	private JPanel getPnButtons() {
		if(pnButtons== null){
			pnButtons = new JPanel();
			((FlowLayout)pnButtons.getLayout()).setAlignment(FlowLayout.RIGHT);
			pnButtons.add(getBtCancel());
			pnButtons.add( new JLabel() );
			pnButtons.add(getBtOk());
		}
		return pnButtons;
	}

	private JButton getBtCancel() {
		if(btCancel==null){
			btCancel = new JButton(Configurations.getString("button.cancel"));
			btCancel.addActionListener(this);
		}
		return btCancel;
	}

	private JButton getBtOk() {
		if(btOk==null){
			btOk = new JButton(Configurations.getString("button.ok"));
			btOk.addActionListener(this);
		}
		return btOk;
	}

	private JScrollPane getSpMetrics() {
		if(spMetrics==null){
			spMetrics = new JScrollPane(getPnMetrics());
			spMetrics.setBorder( BorderFactory.createTitledBorder( Configurations.getString("button.choosemetric") ));
		}
		return spMetrics;
	}

	private JPanel getPnMetrics() {
		if(pnMetrics==null){
			pnMetrics = new JPanel(new GridLayout(0,1));
			List<JCheckBox> cbMetrics = getCbMetrics();
			for(JCheckBox metric: cbMetrics){
				pnMetrics.add(metric);
			}
		}
		return pnMetrics;
	}

	private List<JCheckBox> getCbMetrics() {
		if(cbMetrics==null){
			cbMetrics=new ArrayList<JCheckBox>();
			List<MetricModel> avMetrics = MetriX.getInstance().getMetricModelsAvailable();
			for(MetricModel metric: MetriX.getInstance().getMetricModels()){
				JCheckBox ck = new JCheckBox(metric.getLabel( ));
				if(avMetrics.contains(metric))ck.setSelected(true);
				cbMetrics.add(ck);
			}
		}
		return cbMetrics ;
	}

	public void actionPerformed(ActionEvent action) {
		if(action.getSource().equals( getBtOk() )){
			List<MetricModel> l = new ArrayList<MetricModel>();
			List<MetricModel> metrics = MetriX.getInstance().getMetricModels();
			for(int i=0; i< cbMetrics.size();++i)
				if(cbMetrics.get(i).isSelected()) l.add(metrics.get(i));
			MetriX.getInstance().setMetricModelsAvailable(l);
			this.dispose();
			jd.setVisible(true);
		}
		else if(action.getSource().equals( getBtCancel() )) dispose();
	}

}
