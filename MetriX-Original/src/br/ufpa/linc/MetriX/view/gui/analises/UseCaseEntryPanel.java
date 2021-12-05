/*
 * Created by JFormDesigner on Sat Apr 16 22:11:54 BRT 2011
 */

package br.ufpa.linc.MetriX.view.gui.analises;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.ufpa.linc.MetriX.configurations.Configurations;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author David Bentolila
 */
public class UseCaseEntryPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UseCaseEntryPanel() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - David Bentolila
		componenteTypeComboBox = new JComboBox(new String[]{Configurations.getString("word.class"), Configurations.getString("word.package"),Configurations.getString("word.method")});
		componentNameTextField = new JTextField();
		CellConstraints cc = new CellConstraints();

		//======== this ========

		// JFormDesigner evaluation mark
//		setBorder(new javax.swing.border.CompoundBorder(
//			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
//				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
//				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
//				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

		setLayout(new FormLayout(
			"default, $rgap, default:grow",
			"default:grow"));
		add(componenteTypeComboBox, cc.xy(1, 1));
		add(componentNameTextField, cc.xy(3, 1));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - David Bentolila
	private JComboBox componenteTypeComboBox;
	private JTextField componentNameTextField;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
