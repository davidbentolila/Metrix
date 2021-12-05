package br.ufpa.linc.MetriX.view.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

import br.ufpa.linc.MetriX.MetriX;


public class Splash extends JWindow {

	private final static long serialVersionUID = 42L;
	private JLabel splashImage = null;
	private JProgressBar progressBar = null;
	private BufferedImage background;
//    private Color borda = new Color(207,207,207);
    private static Splash splash = null;
    
    private Splash() {
        setSize(new Dimension(410,280));
        add(getSplashImage(), BorderLayout.CENTER);
        add(getProgressBar(), BorderLayout.SOUTH);
        getProgressBar().setMaximum(5 + 10);
		getProgressBar().setValue(0);
        setLocationRelativeTo(null);
        updateBackground();
        setVisible(true);
        MetriX.changeMouse(true, this);
    }
    
    public synchronized static Splash getInstance() {
		if (splash == null) splash = new Splash();
		return splash;
	}		
    
    private JLabel getSplashImage(){
    	if ( splashImage == null ){
    		splashImage = new JLabel(new ImageIcon( getClass().getResource("/images/splash.png")));
    	}
    	return splashImage;
    }
    
    public JProgressBar getProgressBar(){
    	if ( progressBar == null ){
    		progressBar = new JProgressBar();
    		progressBar.setStringPainted(true);
    	}
    	return progressBar;
    }
    @Override
    public void dispose() {
    	super.dispose();
    	MetriX.changeMouse(false, this);
    }
    
	public void updateBackground() {
		try {
			Robot rbt = new Robot();
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension dim = tk.getScreenSize();
			background = rbt.createScreenCapture(new Rectangle(0, 0-30, (int) dim
					.getWidth(), (int) dim.getHeight()));
		} catch (Exception ex) {
//			p(ex.toString());
			ex.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g) {
		Point pos = this.getLocationOnScreen();
		Point offset = new Point(-pos.x, -pos.y);
		g.drawImage(background, offset.x, offset.y, null);
	}
}