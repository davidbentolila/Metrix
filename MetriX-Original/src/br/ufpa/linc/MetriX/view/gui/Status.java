package br.ufpa.linc.MetriX.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import br.ufpa.linc.MetriX.MetriX;

public class Status extends JDialog implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 861715261335568605L;
	private JProgressBar progress = null;
	private JLabel labelCurrent = null;
	private JDialog status = null;
	private int max = 0;

	public Status(String title, int progressMaximun){
		super(MainWindow.getInstance(), true);
		MetriX.changeMouse(true, MainWindow.getInstance(), this);
		max = progressMaximun;
		initComponent(title);
	}
	
	private void initComponent(String title){
		status = this;
		status.setTitle(title);
//		status.setAlwaysOnTop(true);
		status.setResizable(false);
		status.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		status.setLayout(new BorderLayout());
		status.add( getLabelCurrent(), BorderLayout.CENTER);
		status.add( getProgress(), BorderLayout.SOUTH);
		status.setSize(605,80);
		status.setLocationRelativeTo(MainWindow.getInstance());
	}
	
	private JLabel getLabelCurrent(){
		if ( labelCurrent == null ){
			labelCurrent = new JLabel();
			labelCurrent.setOpaque(true);
			labelCurrent.setBackground(Color.white);
			labelCurrent.setIcon(  new ImageIcon( getClass().getResource("/images/analisys.gif")));
		}
		return labelCurrent;
	}
	
	public JProgressBar getProgress(){
		if ( progress == null ){
			progress = new JProgressBar(0,max);
			progress.setValue(0);
			progress.setStringPainted(true);
		}
		return progress;
	}

	public int updateProgressBar(String current){
		labelCurrent.setText(current);
		progress.setValue(progress.getValue()+1);
		progress.setString((progress.getValue() * 100)/progress.getMaximum() + "%");
		return progress.getValue();
	}
	
	public void restart(){
		progress.setValue(0);
	}
	
	public void run() {
		setVisible(true);
	}

	public void restart(int size) {
		progress = new JProgressBar(0, size);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		MetriX.changeMouse(false, MainWindow.getInstance(), this);
	}
}
