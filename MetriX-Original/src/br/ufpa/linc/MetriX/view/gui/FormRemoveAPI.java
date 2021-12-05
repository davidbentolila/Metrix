/*
 * Created by JFormDesigner on Wed Mar 30 12:54:53 BRT 2011
 */

package br.ufpa.linc.MetriX.view.gui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.dao.Database;

import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author David Bentolila
 */
public class FormRemoveAPI extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4365807932809982937L;
	public FormRemoveAPI() {
		super(MainWindow.getInstance(),true);
		bundle = ResourceBundle.getBundle("language.MetriX", new Locale("en_US"));
		apis = Database.getInstance().getAllAPI();
		if (apis.size() == 0) {
			JOptionPane.showMessageDialog(MainWindow.getInstance(),bundle.getString("FormChooseAPIRemove.noAPI"));
			return;
		}
		initComponents();
		setSize(375,120);
		setLocationRelativeTo(MainWindow.getInstance());
		setVisible(true);
	}

	private void okButtonActionPerformed() {
		dispose();
		MetriX.getInstance().setApi( (API) comboAPIs.getSelectedItem() );
		MetriX.getInstance().removeProject();
	}

	private void cancelButtonActionPerformed() {
		dispose();
		MetriX.getInstance().setApi( null );
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - David Bentolila
		ResourceBundle bundle = ResourceBundle.getBundle("language.MetriX");
		comboBoxPanel = new JPanel();
		comboAPIs = new JComboBox(apis.toArray());
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setTitle(bundle.getString("FormChooseAPIRemove.this.title"));
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setLayout(new FormLayout(
			"$rgap, default:grow, $rgap",
			"$rgap, 2*(fill:default), $rgap"));

		//======== comboBoxPanel ========
		{
			comboBoxPanel.setBorder(new TitledBorder(bundle.getString("FormChooseAPIRemove.comboBoxPanel.border")));

			// JFormDesigner evaluation mark
//			comboBoxPanel.setBorder(new javax.swing.border.CompoundBorder(
//				new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
//					"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
//					javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
//					java.awt.Color.red), comboBoxPanel.getBorder())); comboBoxPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

			comboBoxPanel.setLayout(new FormLayout(
				"$rgap, default:grow, $rgap",
				"fill:default:grow, $rgap"));
			comboBoxPanel.add(comboAPIs, cc.xy(2, 1));
		}
		contentPane.add(comboBoxPanel, cc.xy(2, 2));

		//======== buttonBar ========
		{
			buttonBar.setBorder(Borders.BUTTON_BAR_GAP_BORDER);
			buttonBar.setLayout(new FormLayout(
				"2*($lcgap, default), $glue, $button, $rgap, $button",
				"pref"));

			//---- okButton ----
			okButton.setText(bundle.getString("FormChooseAPIRemove.okButton.text"));
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					okButtonActionPerformed();
				}
			});
			buttonBar.add(okButton, cc.xy(6, 1));

			//---- cancelButton ----
			cancelButton.setText(bundle.getString("FormChooseAPIRemove.cancelButton.text"));
			cancelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					cancelButtonActionPerformed();
				}
			});
			buttonBar.add(cancelButton, cc.xy(8, 1));
		}
		contentPane.add(buttonBar, cc.xy(2, 3));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - David Bentolila
	private JPanel comboBoxPanel;
	private JComboBox comboAPIs;
	private JPanel buttonBar;
	private JButton okButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	private ResourceBundle bundle;
	private List<API> apis;
}
