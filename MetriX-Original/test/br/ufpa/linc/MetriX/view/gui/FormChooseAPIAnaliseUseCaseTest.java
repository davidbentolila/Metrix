package br.ufpa.linc.MetriX.view.gui;

import javax.swing.JFrame;

import org.junit.Test;

import br.ufpa.linc.MetriX.view.gui.analises.FormChooseAPIAnaliseUseCase;


/**
 *@author david
 *Date: 30/03/2011
 */
public class FormChooseAPIAnaliseUseCaseTest {

	@Test
	public void testeGUI(){
		JFrame jf = new JFrame();
		jf.setSize(800,600);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new FormChooseAPIAnaliseUseCase();
		jf.setVisible(true);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
