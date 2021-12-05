package br.ufpa.linc.MetriX.view.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.dao.Database;
import br.ufpa.linc.MetriX.view.jtreeCheckBox.JTreeCheckBox;
import br.ufpa.linc.MetriX.view.treemap.MyTreeMap;

/**
 * @author david Date: 29/10/2010
 */
public class RestrictAPIPackages extends JDialog implements ActionListener,
		WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5499823969495885304L;

	private API api = null;
	private JButton btOK = null, btSave = null;

	private JPanel panelButtons = null;
	private JTreeCheckBox treePackages = null;
	/**
	 * 
	 * @param main
	 *            - Main class
	 * @param option
	 *            - 0 = choose API to view ; 1 - Choose API to remove
	 */
	public RestrictAPIPackages(API api) {
		super(MainWindow.getInstance(), true);
		this.api = api;

		if (api == null)
			dispose();

		setTitle(Configurations.getString(
				"comboBox.package.title"));

		setLayout(new BorderLayout());

		add(getTreePackages(), BorderLayout.CENTER);

		add(getPanelButtons(), BorderLayout.SOUTH);

		setSize(350, 500);

		setResizable(false);
		addWindowListener(this);

		setLocationRelativeTo(MainWindow.getInstance());
		getRootPane().setDefaultButton(getBtOK());
		setVisible(true);
	}

	private JPanel getPanelButtons() {
		if (panelButtons == null) {
			panelButtons = new JPanel();
			FlowLayout fl = (FlowLayout) panelButtons.getLayout();
			fl.setAlignment(FlowLayout.RIGHT);
			panelButtons.add(getBtSave());
			panelButtons.add(new JLabel());
			panelButtons.add(getBtOK());
//			panelButtons.add(new JLabel());
		}
		return panelButtons;
	}

	private JButton getBtOK() {
		if (btOK == null) {
			btOK = new JButton(Configurations.getString(
					"button.ok"));
			btOK.addActionListener(this);
		}
		return btOK;
	}

	private JButton getBtSave() {
		if (btSave == null) {
			btSave = new JButton(Configurations.getString("button.save"));
			btSave.addActionListener(this);
		}
		return btSave;
	}

	private JTreeCheckBox getTreePackages() {
		if (treePackages == null) {
			treePackages = new JTreeCheckBox(api);
		}
		return treePackages;
	}


	/*
	 * 
	 * Actions from this class
	 */

	@Override
	public void actionPerformed(ActionEvent aevt) {
//		if (aevt.getSource().equals(getBtOK())) 
		if (aevt.getSource().equals(getBtSave())) Database.getInstance().update(api);
		dispose();
	}

	public void windowOpened(WindowEvent e) {
		MyTreeMap.stop = false;
	}

	public void windowClosing(WindowEvent e) {
		MyTreeMap.stop = true;
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}
}
