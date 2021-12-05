/*
 * Created by JFormDesigner on Sat Apr 16 21:45:48 BRT 2011
 */

package br.ufpa.linc.MetriX.view.gui.analises;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author David Bentolila
 */
public class UseCasePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UseCasePanel() {
		initComponents();
	}

	private void addEntry() {
		JPanel panel = new JPanel(new BorderLayout());

		JLabel removeUseCase = new JLabel(
				UIManager.getIcon("OptionPane.errorIcon"));
		removeUseCase.setHorizontalAlignment(SwingConstants.CENTER);
		removeUseCase.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println(arg0.getClickCount());
				if (arg0.getClickCount() == 2)
					removeUseCase((JPanel) arg0.getComponent().getParent());
			}
		});
		panel.add(removeUseCase, BorderLayout.EAST);

		panel.setMaximumSize(new Dimension(Short.MAX_VALUE, 40));
		panel.add(new UseCaseEntryPanel(), BorderLayout.CENTER);
		entriesPanel.add(panel);
		// useCaseListPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		entriesPanel.validate();
		scrollPane1.validate();
	}

	private void removeUseCase(JPanel panel) {
		System.out.println("removendo");
		panel.removeAll();
		panel.validate();
		entriesPanel.remove(panel);
		entriesPanel.validate();
		scrollPane1.validate();
		validate();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - David Bentolila
		ResourceBundle bundle = ResourceBundle.getBundle("language.MetriX");
		JLabel label1 = new JLabel();
		useCaseNametextField = new JTextField();
		panel1 = new JPanel();
		scrollPane1 = new JScrollPane();
		entriesPanel = new JPanel();
		JButton addEntryButton = new JButton();
		CellConstraints cc = new CellConstraints();

		//======== this ========

		// JFormDesigner evaluation mark
//		setBorder(new javax.swing.border.CompoundBorder(
//			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
//				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
//				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
//				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

		setLayout(new FormLayout(
			"2*(default, $lcgap), default:grow",
			"6dlu, default, $lgap, fill:default:grow, 5dlu"));

		//---- label1 ----
		label1.setText(bundle.getString("UseCasePanel.label1.text"));
		add(label1, cc.xy(1, 2));
		add(useCaseNametextField, cc.xy(5, 2));

		//======== panel1 ========
		{
			panel1.setLayout(new BorderLayout());

			//======== scrollPane1 ========
			{

				//======== entriesPanel ========
				{
					entriesPanel.setLayout(new BoxLayout(entriesPanel, BoxLayout.PAGE_AXIS));
				}
				scrollPane1.setViewportView(entriesPanel);
			}
			panel1.add(scrollPane1, BorderLayout.CENTER);

			//---- addEntryButton ----
			addEntryButton.setText(bundle.getString("UseCasePanel.addEntryButton.text"));
			addEntryButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					addEntry();
				}
			});
			panel1.add(addEntryButton, BorderLayout.SOUTH);
		}
		add(panel1, cc.xywh(1, 4, 5, 1));
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - David Bentolila
	private JTextField useCaseNametextField;
	private JPanel panel1;
	private JScrollPane scrollPane1;
	private JPanel entriesPanel;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
