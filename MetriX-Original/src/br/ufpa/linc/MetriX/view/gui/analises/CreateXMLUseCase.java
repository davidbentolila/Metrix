/*
 * Created by JFormDesigner on Sat Apr 16 21:45:09 BRT 2011
 */

package br.ufpa.linc.MetriX.view.gui.analises;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.dom4j.Document;

import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.view.gui.MainWindow;

/**
 * @author David Bentolila
 */
public class CreateXMLUseCase extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -78731619040019775L;
	public CreateXMLUseCase() {
		super(MainWindow.getInstance(), true);
		initComponents();
		setSize(600,500);
		setLocationRelativeTo(MainWindow.getInstance());
		setResizable(false);
	}
	
	public Document showDialog(){
		setVisible(true);
		return null;
	}

	private void addUseCase() {
		JPanel panel = new JPanel(new BorderLayout());
		
		JLabel removeUseCase = new JLabel(UIManager.getIcon("OptionPane.errorIcon"));
		removeUseCase.setHorizontalAlignment(SwingConstants.CENTER);
		removeUseCase.addMouseListener( new MouseListener() {
			
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
				if ( arg0.getClickCount() == 2 ) removeUseCase( (JPanel) arg0.getComponent().getParent() );
			}
		});
		panel.add(removeUseCase, BorderLayout.EAST);
		
		panel.setBorder( BorderFactory.createTitledBorder(Configurations.getString("word.useCase")));
		panel.setMaximumSize(new Dimension(Short.MAX_VALUE, 200));
		panel.add(new UseCasePanel(), BorderLayout.CENTER);
		useCaseListPanel.add(panel);
//		useCaseListPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		useCaseListPanel.validate();
		scrollPane1.validate();
	}
	
	private void removeUseCase(JPanel panel){
		System.out.println("removendo");
		panel.removeAll();
		panel.validate();
		useCaseListPanel.remove(panel);
		useCaseListPanel.validate();
		scrollPane1.validate();
		validate();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - David Bentolila
		ResourceBundle bundle = ResourceBundle.getBundle("language.MetriX");
		JButton addUseCaseButton = new JButton();
		scrollPane1 = new JScrollPane();
		useCaseListPanel = new JPanel();

		//======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//---- addUseCaseButton ----
		addUseCaseButton.setText(bundle.getString("CreateXMLUseCase.addUseCaseButton.text"));
		addUseCaseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addUseCase();
			}
		});
		contentPane.add(addUseCaseButton, BorderLayout.SOUTH);

		//======== scrollPane1 ========
		{

			//======== useCaseListPanel ========
			{

				// JFormDesigner evaluation mark
//				useCaseListPanel.setBorder(new javax.swing.border.CompoundBorder(
//					new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
//						"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
//						javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
//						java.awt.Color.red), useCaseListPanel.getBorder())); useCaseListPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

				useCaseListPanel.setLayout(new BoxLayout(useCaseListPanel, BoxLayout.Y_AXIS));
			}
			scrollPane1.setViewportView(useCaseListPanel);
		}
		contentPane.add(scrollPane1, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - David Bentolila
	private JScrollPane scrollPane1;
	private JPanel useCaseListPanel;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
