/*
 * Created by JFormDesigner on Wed Mar 30 13:43:11 BRT 2011
 */

package br.ufpa.linc.MetriX.view.gui.insert;

import java.awt.Container;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import br.ufpa.linc.MetriX.view.gui.MainWindow;

import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author David Bentolila
 */
public abstract class FormAPIData extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3425583661078738256L;
	
	public FormAPIData() {
		super(MainWindow.getInstance(), true);
		initComponents();
		getRootPane().setDefaultButton(okButton);
		setSize(600,230);
		setLocationRelativeTo(MainWindow.getInstance());
	}

	private void chooseMetricsButtonActionPerformed() {
		new FormChooseMetric(this);
	}

	abstract void pathButtonActionPerformed();
	
	abstract void okButtonActionPerformed();
	
	private void cancelButtonActionPerformed() {
		dispose();
	}

	abstract void changeOkButtonStatus();
	
	private void apiNameTextFieldKeyReleased() {
		changeOkButtonStatus();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - David Bentolila
		ResourceBundle bundle = ResourceBundle.getBundle("language.MetriX");
		this.bundle = bundle;
		contentPanel = new JPanel();
		label1 = new JLabel();
		apiFilePathTextField = new JTextField();
		pathButton = new JButton();
		label2 = new JLabel();
		apiNameTextField = new JTextField();
		JLabel label3 = new JLabel();
		releaseTextField = new JTextField();
		JLabel label4 = new JLabel();
		releaseDateTextField = new JTextField();
		JLabel label5 = new JLabel();
		downloadURLTextField = new JTextField();
		buttonBar = new JPanel();
		chooseMetricsButton = new JButton();
		okButton = new JButton();
		cancelButton = new JButton();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setTitle(bundle.getString("FormAPIData.this.title"));
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setLayout(new FormLayout(
			"$rgap, default:grow, $rgap",
			"$rgap, fill:default, $lgap, fill:default, $rgap"));

		//======== contentPanel ========
		{
			contentPanel.setBorder(new TitledBorder(bundle.getString("FormAPIData.contentPanel.border")));

			// JFormDesigner evaluation mark
//			contentPanel.setBorder(new javax.swing.border.CompoundBorder(
//				new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
//					"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
//					javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
//					java.awt.Color.red), contentPanel.getBorder())); contentPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

			contentPanel.setLayout(new FormLayout(
				"2*($lcgap, default), $lcgap, default:grow, $lcgap, default, $lcgap",
				"4*(default, $lgap), default, $rgap"));

			//---- label1 ----
			label1.setText(bundle.getString("FormAPIData.label1.text"));
			contentPanel.add(label1, cc.xy(2, 1));

			//---- apiFilePathTextField ----
			apiFilePathTextField.setEditable(false);
			contentPanel.add(apiFilePathTextField, cc.xywh(4, 1, 3, 1));

			//---- pathButton ----
			pathButton.setIcon(UIManager.getIcon("Tree.openIcon"));
			pathButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pathButtonActionPerformed();
				}
			});
			contentPanel.add(pathButton, cc.xy(8, 1));

			//---- label2 ----
			label2.setText(bundle.getString("FormAPIData.label2.text"));
			contentPanel.add(label2, cc.xy(2, 3));

			//---- apiNameTextField ----
			apiNameTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					apiNameTextFieldKeyReleased();
				}
			});
			contentPanel.add(apiNameTextField, cc.xywh(4, 3, 5, 1));

			//---- label3 ----
			label3.setText(bundle.getString("FormAPIData.label3.text"));
			contentPanel.add(label3, cc.xy(2, 5));
			contentPanel.add(releaseTextField, cc.xywh(4, 5, 5, 1));

			//---- label4 ----
			label4.setText(bundle.getString("FormAPIData.label4.text"));
			contentPanel.add(label4, cc.xy(2, 7));
			contentPanel.add(releaseDateTextField, cc.xywh(4, 7, 5, 1));

			//---- label5 ----
			label5.setText(bundle.getString("FormAPIData.label5.text"));
			contentPanel.add(label5, cc.xy(2, 9));
			contentPanel.add(downloadURLTextField, cc.xywh(4, 9, 5, 1));
		}
		contentPane.add(contentPanel, cc.xy(2, 2));

		//======== buttonBar ========
		{
			buttonBar.setBorder(Borders.BUTTON_BAR_GAP_BORDER);
			buttonBar.setLayout(new FormLayout(
				"$lcgap, default, $glue, $lcgap, 2*($button), $lcgap, $button, $rgap, $button",
				"pref"));

			//---- chooseMetricsButton ----
			chooseMetricsButton.setText(bundle.getString("FormAPIData.chooseMetricsButton.text"));
			chooseMetricsButton.setToolTipText(bundle.getString("FormAPIData.cancelButton.tip"));
			chooseMetricsButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					chooseMetricsButtonActionPerformed();
				}
			});
			buttonBar.add(chooseMetricsButton, cc.xy(5, 1));

			//---- okButton ----
			okButton.setText(bundle.getString("FormAPIData.okButton.text"));
			okButton.setEnabled(false);
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					okButtonActionPerformed();
				}
			});
			buttonBar.add(okButton, cc.xy(8, 1));

			//---- cancelButton ----
			cancelButton.setText(bundle.getString("FormAPIData.cancelButton.text"));
			cancelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					cancelButtonActionPerformed();
				}
			});
			buttonBar.add(cancelButton, cc.xy(10, 1));
		}
		contentPane.add(buttonBar, cc.xy(2, 4));
		setSize(545, 240);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - David Bentolila
	protected JPanel contentPanel;
	protected JLabel label1;
	protected JTextField apiFilePathTextField;
	private JButton pathButton;
	protected JLabel label2;
	protected JTextField apiNameTextField;
	protected JTextField releaseTextField;
	protected JTextField releaseDateTextField;
	protected JTextField downloadURLTextField;
	protected JPanel buttonBar;
	private JButton chooseMetricsButton;
	protected JButton okButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	protected ResourceBundle bundle;
}
