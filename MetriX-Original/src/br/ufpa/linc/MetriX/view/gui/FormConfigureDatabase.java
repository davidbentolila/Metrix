/*
 * Created by JFormDesigner on Thu Mar 31 23:54:50 BRT 2011
 */

package br.ufpa.linc.MetriX.view.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.dao.Database;

import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author David Bentolila
 */
@SuppressWarnings("deprecation")
public class FormConfigureDatabase extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7550623086529104657L;
	public FormConfigureDatabase() {
		super(MainWindow.getInstance());
		initComponents();
		validForm();
		updateFormEdit();
		getRootPane().setDefaultButton( okButton );
		setSize(340,230);
		setLocationRelativeTo(MainWindow.getInstance());
		setVisible(true);
	}

	private void updateFormEdit(){
		if ( dbVendorComboBox.getSelectedItem().equals(MetriX.VendorDB.MySQL) ) {
			dbHostTextField.setEditable(true);
			dbPortTextField.setEditable(true);
			dbNameTextField.setEditable(true);
			dbUserTextField.setEditable(true);
			dbPasswordTextField.setEditable(true);
		}
		else {
			dbHostTextField.setEditable(false);
			dbPortTextField.setEditable(false);
			dbNameTextField.setEditable(true);
			dbUserTextField.setEditable(false);
			dbPasswordTextField.setEditable(false);
		}
	}

	private void validForm(){
		if ( dbVendorComboBox.getSelectedItem().equals(MetriX.VendorDB.MySQL) )
			if ( dbHostTextField.getText().isEmpty() || dbPortTextField.getText().isEmpty() ||
			dbNameTextField.getText().isEmpty() || dbUserTextField.getText().isEmpty() || 
			dbPasswordTextField.getText().isEmpty() )
				okButton.setEnabled(false);
			else okButton.setEnabled(true);
		else {
			if ( dbNameTextField.getText().isEmpty() )
						okButton.setEnabled(false);
					else okButton.setEnabled(true);
		}
	}
	
	private void dbVendorComboBoxActionPerformed() {
		updateFormEdit();
		validForm();
	}

	private void okButtonActionPerformed() {
		String[] DBConfig = new String[6];
		
		if ( 	dbVendorComboBox.getSelectedItem().equals(MetriX.VendorDB.Derby) ||
//				getComboVendor().getSelectedItem().equals(MetriX.VendorDB.Prevayler) ||
				dbVendorComboBox.getSelectedItem().equals(MetriX.VendorDB.HSQLDB)) {
			dbHostTextField.setText("");
			dbPortTextField.setText("");
			dbUserTextField.setText("");
			dbPasswordTextField.setText("");
		}
		DBConfig[0] = dbVendorComboBox.getSelectedItem().toString();
		DBConfig[1] = dbHostTextField.getText();
		DBConfig[2] = dbPortTextField.getText();
		DBConfig[3] = dbNameTextField.getText();
		DBConfig[4] = dbUserTextField.getText();
		DBConfig[5] = dbPasswordTextField.getText();
		
		Configurations.setDatabaseConfiguration(DBConfig);
		Database.closeDatabase();
		Database.getInstance();
		dispose();
	}

	private void cancelButtonActionPerformed() {
		dispose();
	}

	private void dbNameTextFieldKeyTyped() {
		validForm();
	}

	private void dbHostTextFieldKeyTyped() {
		validForm();
	}

	private void dbPortTextFieldKeyTyped() {
		validForm();
	}

	private void dbUserTextFieldKeyTyped() {
		validForm();
	}

	private void dbPasswordTextFieldKeyTyped() {
		validForm();
	}
	
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - David Bentolila
		ResourceBundle bundle = ResourceBundle.getBundle("language.MetriX");
		contentPanel = new JPanel();
		label1 = new JLabel();
		dbVendorComboBox = new JComboBox(MetriX.VendorDB.values());
		for (Object vendor : MetriX.VendorDB.values())
			if ( Configurations.getDatabaseConfiguration()[0].equals(vendor.toString()) ) dbVendorComboBox.setSelectedItem(vendor);
		panel1 = new JPanel();
		label2 = new JLabel();
		dbNameTextField = new JTextField(Configurations.getDatabaseConfiguration()[3]);
		label3 = new JLabel();
		dbHostTextField = new JTextField(Configurations.getDatabaseConfiguration()[1]);
		label4 = new JLabel();
		dbPortTextField = new JTextField(Configurations.getDatabaseConfiguration()[2]);
		label5 = new JLabel();
		dbUserTextField = new JTextField(Configurations.getDatabaseConfiguration()[4]);
		label6 = new JLabel();
		dbPasswordTextField = new JPasswordField(Configurations.getDatabaseConfiguration()[5]);
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle(bundle.getString("FormConfigureDatabase.this.title"));
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== contentPanel ========
		{

			// JFormDesigner evaluation mark
//			contentPanel.setBorder(new javax.swing.border.CompoundBorder(
//				new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
//					"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
//					javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
//					java.awt.Color.red), contentPanel.getBorder())); contentPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

			contentPanel.setLayout(new FormLayout(
				"$rgap, default, $lcgap, default:grow, $lcgap",
				"$rgap, 5*(default, $lgap), default"));

			//---- label1 ----
			label1.setText(bundle.getString("FormConfigureDatabase.label1.text"));
			contentPanel.add(label1, cc.xy(2, 2));

			//---- dbVendorComboBox ----
			dbVendorComboBox.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dbVendorComboBoxActionPerformed();
				}
			});
			contentPanel.add(dbVendorComboBox, cc.xy(4, 2));

			//======== panel1 ========
			{
				panel1.setLayout(new FlowLayout(FlowLayout.LEADING));

				//---- label2 ----
				label2.setText(bundle.getString("FormConfigureDatabase.label2.text"));
				panel1.add(label2);
			}
			contentPanel.add(panel1, cc.xy(2, 4));

			//---- dbNameTextField ----
			dbNameTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					dbNameTextFieldKeyTyped();
				}
			});
			contentPanel.add(dbNameTextField, cc.xy(4, 4));

			//---- label3 ----
			label3.setText(bundle.getString("FormConfigureDatabase.label3.text"));
			contentPanel.add(label3, cc.xy(2, 6));

			//---- dbHostTextField ----
			dbHostTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					dbHostTextFieldKeyTyped();
				}
			});
			contentPanel.add(dbHostTextField, cc.xy(4, 6));

			//---- label4 ----
			label4.setText(bundle.getString("FormConfigureDatabase.label4.text"));
			contentPanel.add(label4, cc.xy(2, 8));

			//---- dbPortTextField ----
			dbPortTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					dbPortTextFieldKeyTyped();
				}
			});
			contentPanel.add(dbPortTextField, cc.xy(4, 8));

			//---- label5 ----
			label5.setText(bundle.getString("FormConfigureDatabase.label5.text"));
			contentPanel.add(label5, cc.xy(2, 10));

			//---- dbUserTextField ----
			dbUserTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					dbUserTextFieldKeyTyped();
				}
			});
			contentPanel.add(dbUserTextField, cc.xy(4, 10));

			//---- label6 ----
			label6.setText(bundle.getString("FormConfigureDatabase.label6.text"));
			contentPanel.add(label6, cc.xy(2, 12));

			//---- dbPasswordTextField ----
			dbPasswordTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					dbPasswordTextFieldKeyTyped();
				}
			});
			contentPanel.add(dbPasswordTextField, cc.xy(4, 12));
		}
		contentPane.add(contentPanel, BorderLayout.NORTH);

		//======== buttonBar ========
		{
			buttonBar.setBorder(Borders.BUTTON_BAR_GAP_BORDER);
			buttonBar.setLayout(new FormLayout(
				"$lcgap, default, $glue, $button, $rgap, $button",
				"default, $lgap, pref, $rgap"));

			//---- okButton ----
			okButton.setText("OK");
			okButton.setEnabled(false);
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					okButtonActionPerformed();
				}
			});
			buttonBar.add(okButton, cc.xy(4, 3));

			//---- cancelButton ----
			cancelButton.setText("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					cancelButtonActionPerformed();
				}
			});
			buttonBar.add(cancelButton, cc.xy(6, 3));
		}
		contentPane.add(buttonBar, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - David Bentolila
	private JPanel contentPanel;
	private JLabel label1;
	private JComboBox dbVendorComboBox;
	private JPanel panel1;
	private JLabel label2;
	private JTextField dbNameTextField;
	private JLabel label3;
	private JTextField dbHostTextField;
	private JLabel label4;
	private JTextField dbPortTextField;
	private JLabel label5;
	private JTextField dbUserTextField;
	private JLabel label6;
	private JPasswordField dbPasswordTextField;
	private JPanel buttonBar;
	private JButton okButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
