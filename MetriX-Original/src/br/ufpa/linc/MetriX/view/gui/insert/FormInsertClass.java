package br.ufpa.linc.MetriX.view.gui.insert;

import java.awt.event.ActionEvent;
import java.io.File;

import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.configurations.Files;
import br.ufpa.linc.MetriX.parsers.Reader;
import br.ufpa.linc.MetriX.parsers.java.ClassReader;

/**
 *@author david
 *Date: 04/02/2011
 */
public class FormInsertClass extends FormAPIData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4652415731585705935L;

	public FormInsertClass() {
		setTitle(bundle.getString("FormAPIData.this.title.class"));
		label1.setText(bundle.getString("FormAPIData.label1.text.class"));
		label2.setText(bundle.getString("FormAPIData.label2.text.class"));
		setVisible(true);
	}

	@Override
	public void pathButtonActionPerformed() {
		classFile = Files.getFile("class");
		if (classFile != null){
			if (apiNameTextField.getText().isEmpty()) apiNameTextField.setText(classFile.getName());
			apiFilePathTextField.setText(classFile.getAbsolutePath());
		}
		changeOkButtonStatus();
		requestFocus();
	}
	
	@Override
	public void okButtonActionPerformed() {
		dispose();
		
		if ( Files.createJarFromClass(classFile) )
			jarFile = new File ( Configurations.getAppDir() + System.getProperty("file.separator") + classFile.getName() + ".jar" );
		
		Reader reader  = new ClassReader();
//			if (reader == null) System.out.println("Unsupported File Type");
		reader.setApiFile(jarFile);
		reader.setApiName(apiNameTextField.getText());
		reader.setRelease(releaseTextField.getText());
		reader.setReleaseDate(releaseDateTextField.getText());
		reader.setApiDownloadURL(downloadURLTextField.getText());
		reader.verify();
		if ( jarFile.exists() ) jarFile.delete();
	}
	
	
	public void actionPerformed(ActionEvent e) {
//		if (e.getSource() == getBtMetricsOptions()) {
//			new FormChooseMetric(this);
//		} else if (e.getSource() == getBtOk()) {
//			
//		} else if (e.getSource() == getBtCancel()) {
//			dispose();
//		}

	}
	private File jarFile = null, classFile = null;

	@Override
	void changeOkButtonStatus() {
		if (classFile == null || apiNameTextField.getText().isEmpty() ) okButton.setEnabled(false);
		else okButton.setEnabled(true);
	}
}
