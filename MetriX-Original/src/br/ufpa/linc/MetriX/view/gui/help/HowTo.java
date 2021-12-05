package br.ufpa.linc.MetriX.view.gui.help;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.view.gui.MainWindow;

public class HowTo extends JDialog {

	private static final long serialVersionUID = -8946382210601909469L;

	private JLabel howTo ;
	
	public HowTo() {
		super( MainWindow.getInstance() );
		setTitle(Configurations.getString("menu.help.howTo"));
		setModal( true );
		setResizable(false);
		setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
		setSize(800,600);
		setLocationRelativeTo( MainWindow.getInstance());
		add( new JScrollPane( getHowTo() ) );
		setVisible(true);
	}
	
	public JLabel getHowTo() {
		if ( howTo == null ) {
			String howuse = "<html>" +
			"<body>" +
			"<h1>1 - Inser&ccedil;&atilde;o de API</h1>" +
			"<font size=4>" +
			"Ap&oacute;s configurada a ferramenta, a primeira tarefa deve ser a de inserir uma API. Para isto, exitem 4 op&ccedil;&otilde;es:<br><br>" +
			"<b>Inserir uma API Java</b> - Selecione um arquivo do tipo <i>Jar</i>.<br><br>" +
			"<b>Inserir v&aacute;rias APIs Java de uma pasta</b> - Selecione uma pasta que contenha um ou mais arquivos <i>Jar</i>.<br><br>" +
			"<b>Inserir uma API C</b> - Selecionar um arquivo do tipo <i>h</i> que cont&eacute;m a defini&ccedil;&atilde;o de todos os outros <i>.h</i>.<br>" +
			"Ou seja, que aponte para o local dos demais arquivos da API.<br><br>" +
			"<b>Importar dados de m&eacute;tricas de um CSV (Avan&ccedil;ado)</b> - &Eacute; necess&aacute;rio um arquivos <i>csv</i> que contenham dados de<br>" +
			"m&eacute;tricas utilizado pela MetriX. &Eacute; necess&aacute;rio que os arquivo possua op seguinte formato:<br><br>" +
			"<center border=1><table>" +
			"<tr><td align=\"center\"><b>FILE</b></td><td align=\"center\"><b>Metrica1</b></td><td align=\"center\"><b>Metrica2</b></td><td align=\"center\"><b>...</b><td align=\"center\"><b>MetricaN</b></td></tr>" + 
			"<tr><Td>classe1</td><td>ValorMetrica1</td><td>ValorMetrica2</td><td>...</td><td>ValorMetricaN</td></tr>" +
			"<tr><Td>classe2</td><td>ValorMetrica1</td><td>ValorMetrica2</td><td>...</td><td>ValorMetricaN</td></tr>" +
			"<tr><Td>classe3</td><td>ValorMetrica1</td><td>ValorMetrica2</td><td>...</td><td>ValorMetricaN</td></tr>" +
			"<tr><Td colspan=5 align=center>...</td></tr>" +
			"<tr><Td>classeX</td><td>ValorMetrica1</td><td>ValorMetrica2</td><td>...</td><td>ValorMetricaN</td></tr>" +
			"</table></center><br><br>" +
			"Para representar a tabela acima, o arquivo deveria ter o seguinte conteúdo:<br><br>" +
			"FILE,Metrica1,Metrica2,...,MetricaN<br>" + 
			"classe1,ValorMetrica1,ValorMetrica2,...,ValorMetricaN<br>" +
			"classe2,ValorMetrica1,ValorMetrica2,...,ValorMetricaN<br>" +
			"classe3,ValorMetrica1,ValorMetrica2,...,ValorMetricaN<br>" +
			"...<br>" +
			"classeX,ValorMetrica1,ValorMetrica2,...,ValorMetricaN"+
			
			
			"</font><br><br>" +
			
			"<h1>2 - Visualiza&ccedil;&atilde;o de API</h1>" +
			"<font size=4>" +
			"Ap&oacute;s inseridas as APIs, para visualizar suas informa&ccedil;&otilde;es, &eacute; necess&aacute;rio escolher a op&ccedil;&atilde;o <i>Gerar Visualiza&ccedil;&atilde;o</i> no<br>" +
			"menu <i>Visualiza&ccedil;&atilde;o</i>  em seguida escolher a API a se visualizada bem como a m&eacute;trica que ir&aacute; representar o tamanho<br>" +
			"dos quadrados na visualiza&ccedil;&atilde;o TreeMap.<br><br>" +
			"Ainda nesta tela &eacute; poss&iacute;vel escolher a op&ccedil;&atilde;o <i>Comparar apenas Internamente</i>, esta op&ccedil;&atilde;o faz com que a<br>" +
			"complexidade das classes da API seja comparada apenas com as complexidade das demais classes da API.<br>" +
			"Caso contr&aacute;rio, a complexidade de classe ser&aacute; comparada com a complexidade de classes de outras APIs<br>" +
			"previamente avaliadas.<br><br>" +
			"Outra funcionalidade da ferramenta &eacute; permitir que sejam comparadas as visualiza&ccedil;&otilde;es duas APIs.<br>" +
			"Para isto &eacute; necess&aacute;rio gerar a visualiza&ccedil;&atilde;o de duas APIs e em seguida clicar em<br>" +
			"<i>Visualiza&ccedil;&atilde;o</i> > <i>Comparar Visualiza&ccedil;&otilde;es</i>." +
			"</font><br><br>" +
			
			"<h1>3 - Relat&oacute;rios da Ferramenta</h1>" +
			"<font size=4>" +
			"A ferramenta &eacute; capaz de gerar dois tipos de relat&oacute;rios. Um deles &eacute; gerado na Tela (op&ccedil;&atilde;o <i>Arquivo</i> > <i>Relat&oacute;rio</i>.<br>" +
			"Este relat&oacute;rio pode ser gerado contendo todas as classes da API (selecionando-se a API no lado esquerdo da tela)<br>" +
			"ou um pacote, classe ou m&eacute;todo espec&iacute;fico (selecionando cada um dos desejados).<br><br>" +
			"O Relat&oacute;rio que pode ser salvo e impresso, &eacute; composto por arquivos do tipo <i>csv</i> e pode agregar todas as APIs<br>" +
			"contidas no Banco de Dados. Este tipo de relat&oacute;rio pode ser obtido na op&ccedil;&atilde;o <i>Arquivo</i> > <i>An&aacute;lise</i>." +
			"</font><br><br><br>" +
			"</html>";
			howTo = new JLabel(howuse);
		}
		return howTo;
	}
}
