/*
 * Created by JFormDesigner on Wed Mar 30 17:36:35 BRT 2011
 */

package br.ufpa.linc.MetriX.view.gui.insert;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ResourceBundle;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.configurations.Files;
import br.ufpa.linc.MetriX.view.gui.MainWindow;

import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author David Bentolila
 */
public class FormInsertJarProjectsFromFolder extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1777267969237412119L;
	
	public FormInsertJarProjectsFromFolder() {
		super(MainWindow.getInstance(), true);
		dfm = new DefaultListModel();
		initComponents();
		setSize(470,250);
		setLocationRelativeTo(MainWindow.getInstance());
		setVisible(true);
	}
	
	private void listProjects(){
		if (pjsFolder != null){
			File[] files = pjsFolder.listFiles();
			dfm.removeAllElements();
			
			for (File f : files) {
				if ( f.getName().endsWith(".jar") ) dfm. addElement(f.getName());
			}
		}
	}

	private void pathButtonActionPerformed() {
		pjsFolder = Files.getFile();
		
		if (pjsFolder != null) {
			Configurations.setLastOpenPath(pjsFolder.getPath());
			projectsFolderPath.setText(pjsFolder.getAbsolutePath());
			listProjects();
			if ( dfm.size() > 0 ) okButton.setEnabled(true);
		} else okButton.setEnabled(false);
		
		requestFocus();
	}

	private void okButtonActionPerformed() {
		MetriX.getInstance().insertApiFolder(pjsFolder);
		dispose();
	}

	private void cancelButtonActionPerformed() {
		MetriX.getInstance().setApi(null);
		dispose();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - David Bentolila
		ResourceBundle bundle = ResourceBundle.getBundle("language.MetriX");
		JPanel panel1 = new JPanel();
		JPanel contentPanel = new JPanel();
		JLabel label2 = new JLabel();
		projectsFolderPath = new JTextField();
		pathButton = new JButton();
		JLabel label1 = new JLabel();
		scrollPane1 = new JScrollPane();
		projectsList = new JList(dfm);
		JPanel buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(new FormLayout(
			"4dlu, default:grow, 4dlu",
			"$rgap, fill:default:grow, $rgap"));

		//======== panel1 ========
		{

			// JFormDesigner evaluation mark
//			panel1.setBorder(new javax.swing.border.CompoundBorder(
//				new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
//					"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
//					javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
//					java.awt.Color.red), panel1.getBorder())); panel1.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

			panel1.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new FormLayout(
					"default, $lcgap, default:grow, $lcgap, $rgap, $lcgap, default",
					"2dlu, 3*($lgap, default)"));

				//---- label2 ----
				label2.setText(bundle.getString("FormInsertProjectsFromFolder.label2.text"));
				contentPanel.add(label2, cc.xy(1, 3));

				//---- projectsFolderPath ----
				projectsFolderPath.setEditable(false);
				projectsFolderPath.setToolTipText(bundle.getString("FormInsertProjectsFromFolder.projectsFolderPath.toolTipText"));
				contentPanel.add(projectsFolderPath, cc.xywh(3, 3, 2, 1));

				//---- pathButton ----
				pathButton.setIcon(UIManager.getIcon("Tree.openIcon"));
				pathButton.setToolTipText(bundle.getString("FormInsertProjectsFromFolder.pathButton.toolTipText"));
				pathButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						pathButtonActionPerformed();
					}
				});
				contentPanel.add(pathButton, cc.xy(7, 3));

				//---- label1 ----
				label1.setText(bundle.getString("FormInsertProjectsFromFolder.label1.text"));
				contentPanel.add(label1, cc.xy(1, 5));

				//======== scrollPane1 ========
				{

					//---- projectsList ----
					projectsList.setBorder(null);
					projectsList.setEnabled(false);
					scrollPane1.setViewportView(projectsList);
				}
				contentPanel.add(scrollPane1, cc.xywh(1, 7, 7, 1));
			}
			panel1.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(Borders.BUTTON_BAR_GAP_BORDER);
				buttonBar.setLayout(new FormLayout(
					"$glue, $button, $rgap, $button",
					"pref"));

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
				cancelButton.setText("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						cancelButtonActionPerformed();
					}
				});
				buttonBar.add(cancelButton, cc.xy(4, 1));
			}
			panel1.add(buttonBar, BorderLayout.SOUTH);
		}
		contentPane.add(panel1, cc.xy(2, 2));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - David Bentolila
	private JTextField projectsFolderPath;
	private JButton pathButton;
	private JScrollPane scrollPane1;
	private JList projectsList;
	private JButton okButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	private File pjsFolder;
	private DefaultListModel dfm;
}
