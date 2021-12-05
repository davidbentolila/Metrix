package br.ufpa.linc.MetriX.view.gui.help;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.view.gui.MainWindow;

@SuppressWarnings("serial")
public class AboutMetriX extends JDialog {

	private JLabel iconLabel = null;
	private JPanel panelContent = null;
	private JLabel nameLabel = null;
	private JLabel aboutLabel = null;
	
	public AboutMetriX() {
		super ( MainWindow.getInstance() );
		setTitle(Configurations.getString("menu.help.about"));
		setLayout(new BorderLayout());
		add( getIconLabel(), BorderLayout.NORTH );
		add( getPanelContent(), BorderLayout.CENTER );
		setSize(400,500);
		setLocationRelativeTo( MainWindow.getInstance() );
		setVisible(true);
	}
	
	private JLabel getIconLabel(){
		if ( iconLabel == null ){
			iconLabel = new JLabel( new ImageIcon( getClass().getResource("/images/aboutIcon.png") ));
		}
		return iconLabel;
	}

	public JPanel getPanelContent() {
		if ( panelContent == null ){
			panelContent = new JPanel(new BorderLayout());
			panelContent.add( getNameLabel(), BorderLayout.NORTH );
			panelContent.add( getAboutLabel(), BorderLayout.CENTER );
		}
		return panelContent;
	}
	
	private JLabel getNameLabel(){
		if ( nameLabel == null ){
			nameLabel = new JLabel("<html><center><font size=6>MetriX</font><br><font size=4>LINC - 2009</font></center></html>");
			nameLabel.setHorizontalAlignment( JLabel.CENTER );
		}
		return nameLabel;
	}
	
	private JLabel getAboutLabel(){
		if ( aboutLabel == null ){
			String about = "<html>" +
							"<font size=4>" + 
							"&Eacute; uma ferramenta utilizada para auxiliar a avalia&ccedil;&atilde;o da usabilidade de uma API. Para auxiliar nesta tarefa a MetriX utiliza m&eacute;tricas de complexidade e t&eacute;cnicas de visualiza&ccedil;&atilde;o. Estas s&atilde;o exibidas da seguinte forma: os resultado das m&eacute;tricas &eacute; apresentado atrav&eacute;s de visualiza&ccedil;&otilde;es que apresentam as informa&ccedil;&otilde;es de maneira intuitiva para o usu&aacute;rio." + 
							"<br><br>" + 
							"Para interpreta&ccedil;&atilde;o dos dados assume-se uma corela&ccedil;&atilde;o direta entre usabilidade e complexidade, isto &eacute;, se uma APIs, ou partes dela, &eacute; complexa, ela ser&aacute; mais dif&iacute;cil de ser utilizada que APIs menos complexas." +
							"</font>" +

							"<br><br><br><center><h2>D&uacute;vidas, Cr&iacute;ticas e Sugest&otilde;es</h2>" +
							"<font size=4>David Bentolila - davidbentolila *at* gmail *dot* com</font></center>" +
							
						"</html>";
			aboutLabel = new JLabel( about );
		}
		return aboutLabel;
	}
	
}
