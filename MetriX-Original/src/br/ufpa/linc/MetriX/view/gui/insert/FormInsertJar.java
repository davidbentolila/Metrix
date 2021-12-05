package br.ufpa.linc.MetriX.view.gui.insert;

import java.io.File;

import br.ufpa.linc.MetriX.configurations.Files;
import br.ufpa.linc.MetriX.parsers.Reader;
import br.ufpa.linc.MetriX.parsers.c.CReader;
import br.ufpa.linc.MetriX.parsers.java.ClassReader;

/**
 *@author david
 *Date: 30/03/2011
 */
public class FormInsertJar extends FormAPIData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4919347128943986687L;

	public FormInsertJar() {
		setVisible(true);
	}
	
	public void pathButtonActionPerformed() {
		apiFile = Files.getFile("h", "jar");
		if ( apiFile != null ){
			if ( apiNameTextField.getText().isEmpty() ) apiNameTextField.setText( apiFile.getName().replace(".jar", "").replace(".", " ") );
			apiFilePathTextField.setText( apiFile.getAbsolutePath() );
		}
		changeOkButtonStatus();
		requestFocus();
	}
	
	public void okButtonActionPerformed() {
		dispose();
		Reader reader = null;
		if(apiFile.getAbsolutePath().endsWith(".jar")) reader = new ClassReader();
		else if(apiFile.getAbsolutePath().endsWith(".h")) reader = new CReader();
		if(reader==null)
			try {
				throw new Exception("Unsupported File Type");
			} catch (Exception e1) {
				e1.printStackTrace();
				System.out.println("Unsuported File Type");
			}
		reader.setApiFile(apiFile);
		reader.setApiName(apiNameTextField .getText());
		reader.setRelease(releaseTextField.getText());
		reader.setReleaseDate(releaseDateTextField.getText());
		reader.setApiDownloadURL(downloadURLTextField.getText());
		reader.verify();
	}	
	private File apiFile;

	@Override
	void changeOkButtonStatus() {
		if (apiFile == null || apiNameTextField.getText().isEmpty() ) okButton.setEnabled(false);
		else okButton.setEnabled(true);
	}
}
