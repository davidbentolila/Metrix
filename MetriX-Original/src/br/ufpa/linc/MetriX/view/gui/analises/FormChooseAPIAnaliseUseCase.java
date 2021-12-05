/*
 * Created by JFormDesigner on Thu Mar 31 13:46:52 BRT 2011
 */

package br.ufpa.linc.MetriX.view.gui.analises;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.analysis.AnalisesUseCase;
import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.configurations.Files;
import br.ufpa.linc.MetriX.dao.Database;
import br.ufpa.linc.MetriX.view.gui.MainWindow;

import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author David Bentolila
 */
public class FormChooseAPIAnaliseUseCase extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8463878342299559251L;
	public FormChooseAPIAnaliseUseCase() {
		super(MainWindow.getInstance(),true);
		apis = Database.getInstance().getAllAPI();

		if (apis.size() == 0){
			JOptionPane.showMessageDialog(MainWindow.getInstance(),Configurations.getString("message.noAPI"));
			return;
		}
		initComponents();
		setSize(500,250);
		setLocationRelativeTo(MainWindow.getInstance());
		setVisible(true);
	}

	private void cancelButtonActionPerformed() {
		MetriX.getInstance().setApi( null );
		dispose();
	}

	private void okButtonActionPerformed() {
//		String errormsg = null;
		dispose();
		MetriX.getInstance().setApi( (API) apisComboBox.getSelectedItem() );
		new AnalisesUseCase(MetriX.getInstance().getApi(),  xmlFile, saveFolder);

//		else errormsg = Configurations.getString("message.error.saveFile.informChooseFolder");
//		else errormsg = Configurations.getString("message.error.chooseXML");
//		
//		if ( errormsg != null ) JOptionPane.showMessageDialog(MainWindow.getInstance(), errormsg, Configurations.getString("message.chooseAtLeast.titlle"), JOptionPane.ERROR_MESSAGE);
	}

	private void xmlPathButtonActionPerformed() {
		xmlFile = Files.getFile("xml");
		if ( xmlFile != null ) xmlPathTextField.setText( xmlFile.getAbsolutePath() );
		validForm();
		requestFocus();
	}

	private void savePathButtonActionPerformed() {
		saveFolder = Files.getFolder();
		if ( saveFolder != null ) savePathTextField.setText( saveFolder.getAbsolutePath() );
		validForm();
		requestFocus();
	}

	private void validForm(){
		okButton.setEnabled(false);
		String tooltip = "";
		if ( xmlFile == null || !xmlFile.getName().endsWith("xml") ) tooltip = Configurations.getString("message.error.chooseXML");  
		else if ( saveFolder == null || !saveFolder.canWrite() || !saveFolder.isDirectory() ) tooltip = Configurations.getString("message.error.saveFile.writePermission");
		else okButton.setEnabled(true);
		okButton.setToolTipText(tooltip);
	}
	
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - David Bentolila
		ResourceBundle bundle = ResourceBundle.getBundle("language.MetriX");
		contentPanel = new JPanel();
		panel1 = new JPanel();
		apisComboBox = new JComboBox(apis.toArray());
		panel2 = new JPanel();
		helpXML = new JLabel();
		xmlPathTextField = new JTextField();
		JButton xmlPathButton = new JButton();
		panel3 = new JPanel();
		savePathTextField = new JTextField();
		JButton savePathButton = new JButton();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setTitle(bundle.getString("FormChooseAPIAnaliseUseCase.this.title"));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(new FormLayout(
			"$rgap, default:grow, $rgap",
			"$rgap, fill:default, $lgap, fill:default, $rgap"));

		//======== contentPanel ========
		{

			// JFormDesigner evaluation mark
//			contentPanel.setBorder(new javax.swing.border.CompoundBorder(
//				new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
//					"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
//					javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
//					java.awt.Color.red), contentPanel.getBorder())); contentPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

			contentPanel.setLayout(new FormLayout(
				"default:grow",
				"2*(default, $lgap), default"));

			//======== panel1 ========
			{
				panel1.setBorder(new TitledBorder(bundle.getString("FormChooseAPIAnaliseUseCase.panel1.border")));
				panel1.setLayout(new FormLayout(
					"$rgap, default:grow, $rgap",
					"fill:default, $rgap"));
				panel1.add(apisComboBox, cc.xy(2, 1));
			}
			contentPanel.add(panel1, cc.xy(1, 1));

			//======== panel2 ========
			{
				panel2.setBorder(new TitledBorder(bundle.getString("FormChooseAPIAnaliseUseCase.panel2.border")));
				panel2.setLayout(new FormLayout(
					"$rgap, default, $lcgap, default:grow, $rgap, default, $lcgap",
					"fill:default:grow, $rgap"));

				//---- helpXML ----
				helpXML.setIcon(new ImageIcon(getClass().getResource("/images/help.png")));
				helpXML.setToolTipText("<html>\n<center><h1>Exemplo de XML</h1></center>\n&nbsp;&nbsp;&lt;?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?&gt;<br>\n&nbsp;&nbsp;&nbsp;&nbsp;&lt;useCases api=\"api\"&gt;<br>\n&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\t&lt;useCase name=\"name use case 1\"&gt;<br>\n&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\t\t&lt;package name=\"br.ufpa.linc.package1\"/&gt;<br>\n&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\t\t&lt;class name=\"br.ufpa.linc.package2.Class1/&gt;<br>\n&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\t\t&lt;method name=\"br.ufpa.linc.package2.Class2.method1\" /&gt;<br>\n&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\t\t&lt;method name=\"br.ufpa.linc.package2.Class2.method2\" /&gt;<br>\n&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\t\t&lt;method name=\"br.ufpa.linc.package2.Class3.method1\" /&gt;<br>\n&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\t&lt;/useCase&gt;<br><br>\n&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\t&lt;useCase name=\"name use case 2\"&gt;<br>\n&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\t\t&lt;package name=\"br.ufpa.linc.package2\"/&gt;<br>\n&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\t\t&lt;class name=\"br.ufpa.linc.package3.Class1\"/&gt;<br>\n&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\t\t&lt;method name=\"br.ufpa.linc.package4.Class1.method1\"/&gt;<br>\n&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\t&lt;/useCase&gt;<br>\n&nbsp;&nbsp;&lt;/useCases&gt;<br>\n</html>");
				panel2.add(helpXML, cc.xy(2, 1));
				panel2.add(xmlPathTextField, cc.xy(4, 1));

				//---- xmlPathButton ----
				xmlPathButton.setIcon(UIManager.getIcon("Tree.openIcon"));
				xmlPathButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						xmlPathButtonActionPerformed();
					}
				});
				panel2.add(xmlPathButton, cc.xy(6, 1));
			}
			contentPanel.add(panel2, cc.xy(1, 3));

			//======== panel3 ========
			{
				panel3.setBorder(new TitledBorder(bundle.getString("FormChooseAPIAnaliseUseCase.panel3.border")));
				panel3.setLayout(new FormLayout(
					"$rgap, default:grow, $rgap, default, $lcgap",
					"fill:default:grow, $rgap"));
				panel3.add(savePathTextField, cc.xy(2, 1));

				//---- savePathButton ----
				savePathButton.setIcon(UIManager.getIcon("Tree.openIcon"));
				savePathButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						savePathButtonActionPerformed();
					}
				});
				panel3.add(savePathButton, cc.xy(4, 1));
			}
			contentPanel.add(panel3, cc.xy(1, 5));
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
		contentPane.add(buttonBar, cc.xy(2, 4));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - David Bentolila
	private JPanel contentPanel;
	private JPanel panel1;
	private JComboBox apisComboBox;
	private JPanel panel2;
	private JLabel helpXML;
	private JTextField xmlPathTextField;
	private JPanel panel3;
	private JTextField savePathTextField;
	private JPanel buttonBar;
	private JButton okButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	private File xmlFile;
	private File saveFolder;
	private List<API> apis;
}
