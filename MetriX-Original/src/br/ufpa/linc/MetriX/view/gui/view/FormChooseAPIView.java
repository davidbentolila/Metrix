/*
 * Created by JFormDesigner on Wed Mar 30 16:29:08 BRT 2011
 */

package br.ufpa.linc.MetriX.view.gui.view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.dao.Database;
import br.ufpa.linc.MetriX.view.gui.MainWindow;

import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author David Bentolila
 */
public class FormChooseAPIView extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4068700593410337243L;
	public FormChooseAPIView() {
		super(MainWindow.getInstance(), true);
		apis = Database.getInstance().getAllAPI();

		if (apis.size() == 0) {
			JOptionPane.showMessageDialog(MainWindow.getInstance(),Configurations.getString("message.noAPI"));
			return;
		}
		initComponents();
		setSize(500,200);
		setResizable(false);
		setVisible(true);
	}

	private void okButtonActionPerformed() {
		dispose();
		MetriX.getInstance().setApi( (API) comboAPIs.getSelectedItem() );
		MetriX.getInstance().setDefineColor(comboMetricColor.getSelectedIndex());
		MetriX.getInstance().setDefineSize(comboMetricSize.getSelectedIndex());
		MetriX.getInstance().setRelativeInternal(relativeCheckBox.isSelected());
		MetriX.getInstance().openView();
	}

	private void cancelButtonActionPerformed() {
		MetriX.getInstance().setApi( null );
		dispose();
	}
	
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - David Bentolila
		ResourceBundle bundle = ResourceBundle.getBundle("language.MetriX");
		contentPanel = new JPanel();
		label1 = new JLabel();
		comboAPIs = new JComboBox(apis.toArray());
		label2 = new JLabel();
		String[] modelMetricColor = new String[2];
		modelMetricColor[0] =bundle.getString("metrics.IS");
		modelMetricColor[1] = bundle.getString("metrics.CBO");
		comboMetricColor = new JComboBox(modelMetricColor);
		comboMetricColor.setSelectedIndex(MetriX.getInstance().getDefineColor());
		label3 = new JLabel();
		Object[] modelMetricSize = new String[6];
		modelMetricSize[0] = bundle.getString("metrics.NM");
		modelMetricSize[1] = bundle.getString("metrics.Call");
		modelMetricSize[2] = bundle.getString("metrics.ClassLink");
		modelMetricSize[3] = bundle.getString("metrics.BugCount");
		modelMetricSize[4] = bundle.getString("metrics.CBO");
		modelMetricSize[5] = bundle.getString("metrics.DIT");
		comboMetricSize = new JComboBox(modelMetricSize);
		comboMetricSize.setSelectedIndex(MetriX.getInstance().getDefineSize());
		relativeCheckBox = new JCheckBox();
		buttonBar = new JPanel();
		okButton = new JButton();
		getRootPane().setDefaultButton(okButton);
		cancelButton = new JButton();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setTitle(bundle.getString("FormChooseAPIView.this.title"));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(new FormLayout(
			"$rgap, default:grow, $rgap",
			"$rgap, 2*(fill:default), $rgap"));

		//======== contentPanel ========
		{
			contentPanel.setBorder(new TitledBorder(bundle.getString("FormChooseAPIView.contentPanel.border")));

			// JFormDesigner evaluation mark
//			contentPanel.setBorder(new javax.swing.border.CompoundBorder(
//				new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
//					"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
//					javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
//					java.awt.Color.red), contentPanel.getBorder())); contentPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

			contentPanel.setLayout(new FormLayout(
				"$lcgap, default, $lcgap, default:grow",
				"4*($lgap, default)"));

			//---- label1 ----
			label1.setText(bundle.getString("FormChooseAPIView.label1.text"));
			contentPanel.add(label1, cc.xy(2, 2));
			contentPanel.add(comboAPIs, cc.xy(4, 2));

			//---- label2 ----
			label2.setText(bundle.getString("FormChooseAPIView.label2.text"));
			contentPanel.add(label2, cc.xy(2, 4));
			contentPanel.add(comboMetricColor, cc.xy(4, 4));

			//---- label3 ----
			label3.setText(bundle.getString("FormChooseAPIView.label3.text"));
			contentPanel.add(label3, cc.xy(2, 6));
			contentPanel.add(comboMetricSize, cc.xy(4, 6));

			//---- relativeCheckBox ----
			relativeCheckBox.setText(bundle.getString("FormChooseAPIView.relativeCheckBox.text"));
			relativeCheckBox.setToolTipText(bundle.getString("FormChooseAPIView.relativeCheckBox.toolTipText"));
			contentPanel.add(relativeCheckBox, cc.xy(2, 8));
		}
		contentPane.add(contentPanel, cc.xy(2, 2));

		//======== buttonBar ========
		{
			buttonBar.setBorder(Borders.BUTTON_BAR_GAP_BORDER);
			buttonBar.setLayout(new FormLayout(
				"$glue, $button, $rgap, $button",
				"pref"));

			//---- okButton ----
			okButton.setText("OK");
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					okButtonActionPerformed();
				}
			});
			buttonBar.add(okButton, cc.xy(2, 1));

			//---- cancelButton ----
			cancelButton.setText("Cancelar");
			cancelButton.setSelectedIcon(null);
			cancelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					cancelButtonActionPerformed();
				}
			});
			buttonBar.add(cancelButton, cc.xy(4, 1));
		}
		contentPane.add(buttonBar, cc.xy(2, 3));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - David Bentolila
	private JPanel contentPanel;
	private JLabel label1;
	private JComboBox comboAPIs;
	private JLabel label2;
	private JComboBox comboMetricColor;
	private JLabel label3;
	private JComboBox comboMetricSize;
	private JCheckBox relativeCheckBox;
	private JPanel buttonBar;
	private JButton okButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	private List<API> apis;
}
