package br.ufpa.linc.MetriX.view.gui.insert;

import java.io.File;

import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.configurations.Files;
import br.ufpa.linc.MetriX.parsers.Reader;
import br.ufpa.linc.MetriX.parsers.java.ClassReader;

/**
 * @author david Date: 04/02/2011
 */
public class FormInsertProjectFolder extends FormAPIData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5180527805417023720L;
	public FormInsertProjectFolder() {
		setTitle(bundle.getString("FormAPIData.this.title.projectFolder"));
		label1.setText(bundle.getString("FormAPIData.label1.text.projectFolder"));
		label2.setText(bundle.getString("FormAPIData.label2.text.projectFolder"));
		setVisible(true);
	}
	
	@Override
	void pathButtonActionPerformed() {
		pjFolder = Files.getFile();
		
		if (pjFolder != null){
			if ( apiNameTextField.getText().isEmpty() ) apiNameTextField.setText( pjFolder.getName() );
			apiFilePathTextField.setText( pjFolder.getAbsolutePath() );
		}
		changeOkButtonStatus();
		requestFocus();
	}

	@Override
	void okButtonActionPerformed() {
		dispose();

		if (Files.createJarFromFolderProject(pjFolder))
			jarFile = new File(Configurations.getAppDir()
					+ System.getProperty("file.separator")
					+ pjFolder.getName() + ".jar");

		Reader reader = new ClassReader();
		// if (reader == null) System.out.println("Unsupported File Type");
		reader.setApiFile(jarFile);
		reader.setApiName(apiNameTextField.getText());
		reader.setRelease(releaseTextField.getText());
		reader.setReleaseDate(releaseDateTextField.getText());
		reader.setApiDownloadURL(downloadURLTextField.getText());
		reader.verify();
		Configurations.setLastOpenPath(pjFolder.getAbsolutePath());
		if (jarFile.exists())
			jarFile.delete();
	}
	private File pjFolder, jarFile;
	
	@Override
	void changeOkButtonStatus() {
		if (pjFolder != null && !apiNameTextField.getText().isEmpty() ) okButton.setEnabled(true);
		else okButton.setEnabled(false);
	}
}
