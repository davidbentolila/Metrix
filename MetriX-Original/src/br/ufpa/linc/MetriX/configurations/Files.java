package br.ufpa.linc.MetriX.configurations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.ufpa.linc.MetriX.analysis.UseCase;
import br.ufpa.linc.MetriX.view.gui.MainWindow;

public class Files {

	public static void changeFieldsFileChoose() {
		UIManager.put("FileChooser.lookInLabelMnemonic", new Integer('E'));
		UIManager.put("FileChooser.lookInLabelText", Configurations
				.getString("fileChooser.lookInLabelText"));
		UIManager.put("FileChooser.saveInLabelText", Configurations
				.getString("fileChooser.saveInLabelText"));

		UIManager.put("FileChooser.fileNameLabelMnemonic", new Integer('N')); // N
		UIManager.put("FileChooser.fileNameLabelText", Configurations
				.getString("fileChooser.fileNameLabelText"));

		UIManager.put("FileChooser.filesOfTypeLabelMnemonic", new Integer('T')); // T
		UIManager.put("FileChooser.filesOfTypeLabelText", Configurations
				.getString("fileChooser.filesOfTypeLabelText"));

		UIManager.put("FileChooser.upFolderToolTipText", Configurations
				.getString("fileChooser.upFolderToolTipText"));
		UIManager.put("FileChooser.upFolderAccessibleName", Configurations
				.getString("fileChooser.upFolderAccessibleName"));

		UIManager.put("FileChooser.homeFolderToolTipText", Configurations
				.getString("fileChooser.homeFolderToolTipText"));
		UIManager.put("FileChooser.homeFolderAccessibleName", Configurations
				
				.getString("fileChooser.homeFolderAccessibleName"));

		UIManager.put("FileChooser.newFolderToolTipText", Configurations
				.getString("fileChooser.newFolderToolTipText"));
		UIManager
				.put("FileChooser.newFolderAccessibleName",
						Configurations.getString(
								"fileChooser.newFolderAccessibleName"));

		UIManager.put(
				"FileChooser.listViewButtonToolTipText",
				Configurations.getString(
						"fileChooser.listViewButtonToolTipText"));
		UIManager.put(
				"FileChooser.listViewButtonAccessibleName",
				Configurations.getString(
						"fileChooser.listViewButtonAccessibleName"));

		UIManager.put(
				"FileChooser.detailsViewButtonToolTipText",
				Configurations.getString(
						"fileChooser.detailsViewButtonToolTipText"));
		UIManager.put(
				"FileChooser.detailsViewButtonAccessibleName",
				Configurations.getString(
						"fileChooser.detailsViewButtonAccessibleName"));

		UIManager.put("FileChooser.fileNameHeaderText", Configurations
				.getString("fileChooser.fileNameHeaderText"));
		UIManager.put("FileChooser.fileSizeHeaderText", Configurations
				.getString("fileChooser.fileSizeHeaderText"));
		UIManager.put("FileChooser.fileTypeHeaderText", Configurations
				.getString("fileChooser.fileTypeHeaderText"));
		UIManager.put("FileChooser.fileDateHeaderText", Configurations
				.getString("fileChooser.fileDateHeaderText"));
		UIManager.put("FileChooser.fileAttrHeaderText", Configurations
				.getString("fileChooser.fileAttrHeaderText"));

		UIManager.put("FileChooser.openButtonText", Configurations
				.getString("fileChooser.openButtonText"));
		UIManager.put("FileChooser.cancelButtonText", Configurations
				.getString("fileChooser.cancelButtonText"));
		UIManager.put("FileChooser.openButtonToolTipText", Configurations
				.getString("fileChooser.openButtonToolTipText"));
		UIManager
				.put("FileChooser.cancelButtonToolTipText",
						Configurations.getString(
								"fileChooser.cancelButtonToolTipText"));
		UIManager.put("FileChooser.approveButtonToolTipText", Configurations
				
				.getString("fileChooser.approveButtonToolTipText"));
		UIManager.put("FileChooser.CANCEL_OPTION", Configurations
				.getString("fileChooser.CANCEL_OPTION"));
		UIManager.put("FileChooser.lookInLabelText", Configurations
				.getString("fileChooser.lookInLabelText"));
	}

	public static File getFile(final String... types) {
		changeFieldsFileChoose();

		JFileChooser fileChooser = new JFileChooser(
				Configurations.getLastOpenPath());
		
		if (types.length == 0) {
			fileChooser.addChoosableFileFilter(new FileFilter() {
				public String getDescription() {
					return "choose a folder";
				}

				public boolean accept(File f) {
					if (f.isDirectory())
						return true;
					return false;
				}
			});
		} else
			for (int i = 0; i < types.length; i++) {
				final int index = i;
				fileChooser.addChoosableFileFilter(new FileFilter() {

					public String getDescription() {
						return types[index] + " libraries";
					}

					public boolean accept(File f) {
						if (f.isDirectory())
							return true;
						if (f.getName().endsWith("." + types[index]))
							return true;
						return false;
					}
				});
			}
		// fileChooser.setFileFilter(new FileFilter() {
		// public String getDescription() {
		// return extensionFilter;
		// }
		// public boolean accept(File f) {
		// if(f.isDirectory()) return true;
		// if(f.getName().endsWith("." + extensionFilter )) return true;
		// return false;
		// }
		// });
		fileChooser
				.setFileSelectionMode(types.length == 0 ? JFileChooser.DIRECTORIES_ONLY
						: JFileChooser.FILES_ONLY);

		int result = fileChooser.showOpenDialog(null);
		// if user choose cancell return user home
		if (result == JFileChooser.CANCEL_OPTION) {
			return null;
		}

		File fileName = fileChooser.getSelectedFile(); // get selected file

		boolean acceptable = false;
		// show error if is invalid file
		if (types.length == 0)
			acceptable = fileName.isDirectory() ? true : false;
		else
			for (String string : types) {
				if (fileName.getName().endsWith(string))
					acceptable = true;
			}
		if ((fileName == null) || fileName.getName().equals("") || !acceptable) {

			JOptionPane.showMessageDialog(
					null,
					Configurations.getString(
							"message.file.noExtension" + " " + types),
					Configurations.getString(
							"message.invalidFile"), JOptionPane.ERROR_MESSAGE);
			return getFile(types);
		} // end if

		Configurations.setLastOpenPath(fileName.getPath().replace(
				fileName.getName(), ""));
		return fileName;
	} // end method getFile

	public static File getFolder() {
		changeFieldsFileChoose();
		JFileChooser fileChooser = new JFileChooser(
				Configurations.getLastOpenPath());
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int result = fileChooser.showOpenDialog(null);
		// if user choose cancell return user home
		if (result == JFileChooser.CANCEL_OPTION)
			return null;

		File fileName = fileChooser.getSelectedFile(); // get selected file

		Configurations.setLastOpenPath(fileName.getPath());
		return fileName;
	} // end method getFile

	public static File saveToFile(String extension, String suggestedName) {
		changeFieldsFileChoose();
		JFileChooser fc = new JFileChooser(Configurations.getLastSavePath());
		fc.setSelectedFile(new File(suggestedName));
		final String extension_ = extension.startsWith(".") ? extension : "."
				+ extension;
		fc.setFileFilter(new FileFilter() {
			public String getDescription() {
				return extension_;
			}

			public boolean accept(File f) {
				if (f.isDirectory())
					return true;
				if (f.getName().endsWith(extension_))
					return true;
				return false;
			}

		});

		File view = null;
		int returnVal = fc.showSaveDialog(MainWindow.getInstance());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			view = fc.getSelectedFile();
		} else
			return null;

		if (!view.getName().endsWith(extension_))
			view = new File(view.getAbsolutePath() + extension_);
		return view;
	}

	public static String fileToString(File arquivo) {
		StringBuffer texto = new StringBuffer();
		try {
			BufferedReader leitor = new BufferedReader(new FileReader(arquivo));

			String linha;
			while ((linha = leitor.readLine()) != null)
				texto.append(linha).append("\n");
			leitor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return texto.toString();
	}

	public static boolean createFile(File file, String text,
			boolean showConfirmation) {
		try {
			if (file.exists())
				file.delete();
			FileWriter writer = new FileWriter(file, true);
			BufferedWriter bufWriter = new BufferedWriter(writer);
			bufWriter.write(text);
			bufWriter.close();
			writer.close();
			if (showConfirmation)
				JOptionPane.showMessageDialog(
						MainWindow.getInstance(),
						Configurations.getString(
								"message.createdSuccess"), Configurations
								.getString("message.success"),
						JOptionPane.INFORMATION_MESSAGE);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void writeInFile(File file, StringBuffer text) {
		try {
			if (!file.exists())
				file.createNewFile();
			FileWriter writer = new FileWriter(file, true);
			BufferedWriter bufWriter = new BufferedWriter(writer);
			bufWriter.write(text.toString());
			bufWriter.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean saveCSV(StringBuffer csv, String suggestedName) {
		File csvFile = saveToFile("csv", suggestedName);
		if (csvFile == null)
			return false;
		File path = new File(csvFile.getAbsolutePath().substring(
				0,
				csvFile.getAbsolutePath().lastIndexOf(
						System.getProperty("file.separator"))));
		if (!path.canWrite()) {
			JOptionPane.showMessageDialog(
					MainWindow.getInstance(),
					Configurations.getString(
							"message.error.saveFile.writePermission"),
					Configurations
							.getString("window.title.error"),
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		writeInFile(csvFile, csv);
		Configurations.setLastSavePath(csvFile.getParent());
		JOptionPane.showMessageDialog(MainWindow.getInstance(), Configurations
				.getString("message.CSVExported"), Configurations
				.getString("message.success"),
				JOptionPane.INFORMATION_MESSAGE);
		return true;
	}

	public static boolean createJarFromFolderProject(File projectFolder) {
		File jar = new File(Configurations.getAppDir()
				+ System.getProperty("file.separator")
				+ projectFolder.getName() + ".jar");
		try {
			zipFolder(projectFolder.getAbsolutePath(), jar.getAbsolutePath());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean createJarFromClass(File classFile) {
		File jar = new File(Configurations.getAppDir()
				+ System.getProperty("file.separator") + classFile.getName()
				+ ".jar");
		try {
			ZipOutputStream zip = null;
			FileOutputStream fileWriter = null;

			fileWriter = new FileOutputStream(jar);
			zip = new ZipOutputStream(fileWriter);

			addFileToZip("", classFile.getAbsolutePath(), zip);

			zip.flush();
			zip.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	static private void zipFolder(String srcFolder, String destZipFile)
			throws Exception {
		ZipOutputStream zip = null;
		FileOutputStream fileWriter = null;

		fileWriter = new FileOutputStream(destZipFile);
		zip = new ZipOutputStream(fileWriter);

		// addFolderToZip("", srcFolder, zip);

		File[] files = new File(srcFolder).listFiles();

		for (File f : files) {
			if (f.isDirectory())
				addFolderToZip("", f.getAbsolutePath(), zip);
			else
				addFileToZip("", srcFolder + "/" + f.getName(), zip);
		}

		zip.flush();
		zip.close();
	}

	static private void addFileToZip(String path, String srcFile,
			ZipOutputStream zip) throws Exception {

		File folder = new File(srcFile);
		if (folder.isDirectory()) {
			addFolderToZip(path, srcFile, zip);
		} else {
			byte[] buf = new byte[1024];
			int len;
			FileInputStream in = new FileInputStream(srcFile);
			zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
			while ((len = in.read(buf)) > 0) {
				zip.write(buf, 0, len);
			}
		}
	}

	static private void addFolderToZip(String path, String srcFolder,
			ZipOutputStream zip) throws Exception {
		File folder = new File(srcFolder);

		for (String fileName : folder.list()) {
			if (path.equals("")) {
				addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
			} else {
				addFileToZip(path + "/" + folder.getName(), srcFolder + "/"
						+ fileName, zip);
			}
		}
	}

	public static boolean copyFile(File from, String pathTo) throws IOException {
		File toFile = new File(pathTo + File.separator + from.getName());

		if (!from.exists())
			throw new IOException("FileCopy: " + "no such source file: " + from);
		if (!from.isFile())
			throw new IOException("FileCopy: " + "can't copy directory: "
					+ from);
		if (!from.canRead())
			throw new IOException("FileCopy: " + "source file is unreadable: "
					+ from);

		if (toFile.isDirectory())
			toFile = new File(toFile, from.getName());

		if (toFile.exists()) {
			if (!toFile.canWrite())
				throw new IOException("FileCopy: "
						+ "destination file is unwriteable: " + toFile);
			System.out.print("Overwrite existing file " + toFile.getName()
					+ "? (Y/N): ");
			System.out.flush();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			String response = in.readLine();
			if (!response.equals("Y") && !response.equals("y"))
				throw new IOException("FileCopy: "
						+ "existing file was not overwritten.");
		} else {
			String parent = toFile.getParent();
			if (parent == null)
				parent = System.getProperty("user.dir");
			File dir = new File(parent);
			if (!dir.exists())
				throw new IOException("FileCopy: "
						+ "destination directory doesn't exist: " + parent);

			if (dir.isFile())
				throw new IOException("FileCopy: "
						+ "destination is not a directory: " + parent);
			if (!dir.canWrite())
				throw new IOException("FileCopy: "
						+ "destination directory is unwriteable: " + parent);
		}

		FileInputStream fromStream = null;
		FileOutputStream toStream = null;
		try {
			fromStream = new FileInputStream(from);
			toStream = new FileOutputStream(toFile);
			byte[] buffer = new byte[4096];
			int bytesRead;

			while ((bytesRead = fromStream.read(buffer)) != -1)
				toStream.write(buffer, 0, bytesRead); // write
		} finally {
			if (from != null)
				try {
					fromStream.close();
				} catch (IOException e) {
					return false;
				}
			if (toStream != null)
				try {
					toStream.close();
				} catch (IOException e) {
					;
				}
		}
		return true;
	}

	public static boolean copyClass(JarFile jarFile, JarEntry from, URI to) {
		InputStream in = null;
		DataOutputStream dos = null;
		int c, i = 0;

		byte[] buffer = new byte[(int) from.getSize()];
		try {
			in = jarFile.getInputStream(from);
			while ((c = in.read()) != -1)
				buffer[i++] = (byte) c;
			dos = new DataOutputStream(new FileOutputStream(new File(to)));
			dos.write(buffer);
			dos.flush();
			dos.close();
			return true;
		} catch (IOException e2) {
			e2.printStackTrace();
			return false;
		}
	}
	
	// public void saveView(MainWindow main){
	// try {
	// File view = saveToFile(main);
	// ObjectOutputStream outputStream = new ObjectOutputStream(new
	// FileOutputStream( view ));
	// outputStream.writeObject(main.getCurrentInternalFrame());
	// outputStream.flush();
	// outputStream.close();
	// JOptionPane.showMessageDialog(main,
	// Configurations.getString("message.success"),
	// Configurations.getString("message.saveView"),
	// JOptionPane.INFORMATION_MESSAGE );
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// public JInternalFrame openView(){
	// try {
	// File view = getFile("metrix");
	// ObjectInputStream inputStream = new ObjectInputStream(new
	// FileInputStream(view));
	// JInternalFrame internalFrame = (JInternalFrame) inputStream.readObject();
	// return internalFrame;
	// } catch (Exception e) {
	// e.printStackTrace();
	// return null;
	// }
	// }
	
	public static List<UseCase> getUseCases(File xmlUseCases){
		//TODO fazer melhor
		if ( xmlUseCases == null ) return new ArrayList<UseCase>();
		
		assert xmlUseCases.getName().endsWith(".xml");
		
		DocumentBuilder db;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = db.parse(xmlUseCases);
			return getUseCases(doc);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static List<UseCase> getUseCases(Document doc){
		//TODO fazer melhor
		if ( doc == null ) return new ArrayList<UseCase>();
		
		List<UseCase> useCases = new ArrayList<UseCase>();
		UseCase uc;
		
		//get the root element
		Element docEle = doc.getDocumentElement();

		//get a nodelist of elements
		NodeList nl = getNodeList(docEle, "useCase");

		if(nl != null && nl.getLength() > 0) 
			for(int i = 0 ; i < nl.getLength();i++) {
				//get the "objects" element
				Element el = (Element)nl.item(i);
				//get the "objects" object
				uc = getUseCase(useCases.size(), el);
				//add it to list
				useCases.add(uc);
			}
	
		return useCases;
	}
	
	private static UseCase getUseCase(int id, Element useCaseEl) {
		UseCase uc = new UseCase(id);
		Element el;
		NodeList nl;
		uc.setLabel( useCaseEl.getAttribute("name") );
		
		nl = getNodeList(useCaseEl, "method");
		if(nl != null && nl.getLength() > 0)
			for(int i = 0 ; i < nl.getLength();i++) {
				el = (Element)nl.item(i);
				uc.addMethod(el.getAttribute("name"));
//				String name = el.getAttribute("name");
//				uc.addClass(name.substring(0,name.lastIndexOf(".")));
			}
		
		nl = getNodeList(useCaseEl, "class");
		if(nl != null && nl.getLength() > 0)
			for(int i = 0 ; i < nl.getLength();i++) {
				el = (Element)nl.item(i);
				uc.addClass(el.getAttribute("name"));
			}
		
		nl = getNodeList(useCaseEl, "package");
		if(nl != null && nl.getLength() > 0)
			for(int i = 0 ; i < nl.getLength();i++) {
				el = (Element)nl.item(i);
				uc.addPackage(el.getAttribute("name"));
			}
		return uc;
	}
	
	private static NodeList getNodeList(Element root, String nodeName){
		NodeList nl = root.getElementsByTagName(nodeName);
		return nl;
	}
}