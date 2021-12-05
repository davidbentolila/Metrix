/*
 * Created by JFormDesigner on Thu Mar 31 14:20:34 BRT 2011
 */

package br.ufpa.linc.MetriX.view.gui.analises;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.analysis.Analysis;
import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.configurations.Files;
import br.ufpa.linc.MetriX.metricModel.MetricModel;
import br.ufpa.linc.MetriX.metrics.Metric;
import br.ufpa.linc.MetriX.view.gui.MainWindow;
import br.ufpa.linc.MetriX.view.gui.MyJList;

import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author David Bentolila
 */
public class FormAnalysisAPIs extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5544900229501891256L;
	public FormAnalysisAPIs(List<API> apis) {
		super( MainWindow.getInstance(), true); 
		this.apis = apis;
		initComponents();
		setSize(500,300);
		setLocationRelativeTo( MainWindow.getInstance() );
		getRootPane().setDefaultButton(okButton);
        setVisible(true);
	}

	private void okButtonActionPerformed() {
		indexSelectedApis = apisList.getSelectedIndices();
		indexSelectedMetrics = metricsList.getSelectedIndices();
		
		if ( indexSelectedApis.length == 0 ) {
			JOptionPane.showMessageDialog(this, Configurations.getString("message.chooseAtLeastAPI"), Configurations.getString("message.chooseAtLeast.titlle"), JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if ( indexSelectedMetrics.length == 0 ) {
			JOptionPane.showMessageDialog(this, Configurations.getString("message.chooseAtLeastMetric"), Configurations.getString("message.chooseAtLeast.titlle"), JOptionPane.ERROR_MESSAGE);
			return;
		}
		dispose();
		new Analysis(apis, indexSelectedApis, metrics, indexSelectedMetrics, dir);
	}

	private void cancelButtonActionPerformed() {
		dispose();
	}

	private void pathButtonActionPerformed() {
		dir = Files.getFolder();

		if (dir != null ) {
			if ( !dir.canWrite()) {
				JOptionPane.showMessageDialog(
						MainWindow.getInstance(),
						Configurations.getString(
								"message.error.saveFile.writePermission"),
						Configurations.getString(
								"window.title.error"),
						JOptionPane.ERROR_MESSAGE);
				dir = null;
			}
			else pathTextField.setText(dir.getAbsolutePath());
		}
		validForm();
	}

	private void validForm(){
		if ( dir != null && apisList.getSelectedIndices().length > 0 && metricsList.getSelectedIndices().length > 0) okButton.setEnabled(true);
		else okButton.setEnabled(false);
	}

	private void apisListValueChanged() {
		validForm();
	}

	private void metricsListValueChanged() {
		validForm();
	}
	
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - David Bentolila
		ResourceBundle bundle = ResourceBundle.getBundle("language.MetriX");
		JPanel buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();
		JScrollPane scrollPane1 = new JScrollPane ( apisList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		apisList = new MyJList( apis );
		JScrollPane scrollPane2 = new JScrollPane ( metricsList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		metrics = new ArrayList<Metric>();
		for(MetricModel metricM: MetriX.getInstance().getMetricModels())
		for(Metric metric: metricM.getMetrics()) metrics.add(metric);
		metricsList = new MyJList( metrics );
		JPanel panel1 = new JPanel();
		pathTextField = new JTextField();
		pathButton = new JButton();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(new FormLayout(
			"2*($rgap, default:grow), $rgap",
			"$lgap, fill:default:grow, $rgap, default, fill:default"));

		//======== buttonBar ========
		{
			buttonBar.setBorder(Borders.BUTTON_BAR_GAP_BORDER);

			// JFormDesigner evaluation mark
//			buttonBar.setBorder(new javax.swing.border.CompoundBorder(
//				new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
//					"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
//					javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
//					java.awt.Color.red), buttonBar.getBorder())); buttonBar.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

			buttonBar.setLayout(new FormLayout(
				"$glue, $button, $rgap, $button",
				"pref, $rgap"));

			//---- okButton ----
			okButton.setText("OK");
			okButton.setEnabled(false);
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					okButtonActionPerformed();
				}
			});
			buttonBar.add(okButton, cc.xy(2, 1));

			//---- cancelButton ----
			cancelButton.setText("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					cancelButtonActionPerformed();
				}
			});
			buttonBar.add(cancelButton, cc.xy(4, 1));
		}
		contentPane.add(buttonBar, cc.xywh(2, 5, 3, 1));

		//======== scrollPane1 ========
		{
			scrollPane1.setViewportBorder(new TitledBorder(bundle.getString("FormAnalysisAPIs.scrollPane1.viewportBorder")));

			//---- apisList ----
			apisList.setBorder(null);
			apisList.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					apisListValueChanged();
				}
			});
			scrollPane1.setViewportView(apisList);
		}
		contentPane.add(scrollPane1, cc.xy(4, 2));

		//======== scrollPane2 ========
		{
			scrollPane2.setViewportBorder(new TitledBorder(bundle.getString("FormAnalysisAPIs.scrollPane2.viewportBorder")));

			//---- metricsList ----
			metricsList.setBorder(null);
			metricsList.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					metricsListValueChanged();
				}
			});
			scrollPane2.setViewportView(metricsList);
		}
		contentPane.add(scrollPane2, cc.xy(2, 2));

		//======== panel1 ========
		{
			panel1.setBorder(new TitledBorder(bundle.getString("FormAnalysisAPIs.panel1.border")));
			panel1.setLayout(new FormLayout(
				"default:grow, $lcgap, default",
				"default, $rgap"));

			//---- pathTextField ----
			pathTextField.setEditable(false);
			panel1.add(pathTextField, cc.xy(1, 1));

			//---- pathButton ----
			pathButton.setIcon(UIManager.getIcon("Tree.openIcon"));
			pathButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pathButtonActionPerformed();
				}
			});
			panel1.add(pathButton, cc.xy(3, 1));
		}
		contentPane.add(panel1, cc.xywh(2, 4, 3, 1));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - David Bentolila
	private JButton okButton;
	private JButton cancelButton;
	private JList apisList;
	private JList metricsList;
	private JTextField pathTextField;
	private JButton pathButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	private List<API> apis = null;
	private List<Metric> metrics = null;
	private int[] indexSelectedApis, indexSelectedMetrics;
	private File dir;
}
