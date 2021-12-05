package br.ufpa.linc.MetriX.analysis;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.JOptionPane;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Attribute;
import br.ufpa.linc.MetriX.api.model.Class;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Interface;
import br.ufpa.linc.MetriX.api.model.Method;
import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.configurations.Files;
import br.ufpa.linc.MetriX.view.gui.MainWindow;
import br.ufpa.linc.MetriX.view.gui.Status;

/**
 * @author david Date: 27/03/2011
 */
public class AnalisesUseCase {
	private Status status;
	private File dir, fileXLS;
	private NumberFormat nf;
	private final DateFormat dateFormat;
	private final String date;
	private List<UseCase> useCases;
	private WritableWorkbook workbook;
	private boolean saveOk;
	
	public AnalisesUseCase(API api, File xml, File saveDir) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
		date = dateFormat.format(new Date());
		nf = new NumberFormat("#.##");
		
		if ( api == null || xml == null ) return;
		this.dir = saveDir;
		
		setSaveDir();
		if ( dir == null ) return;
//		JOptionPane.showMessageDialog(MainWindow.getInstance(),
//				Configurations.getString("message.choose.UseCaseFile"));
//		File xml = Files.getFile("xml");
		
		useCases = Files.getUseCases(xml);
		fileXLS = new File(dir + System.getProperty("file.separator")
				+ MetriX.getInstance().getApi() + "_" + date + ".xls");
		MainWindow.getInstance().requestFocus();
		AnalysisAPI(api);
	}

	/**
	 * Show analises to complete API.
	 * 
	 * @param api
	 * @throws IOException
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	public void AnalysisAPI(final API api) {

		status = new Status(Configurations.getString("menu.analysis"), api
				.getAllEntities().size()*2);

		new Thread(status).start();
		Thread t1 = new Thread() {
			public void run() {
				MyList isAPI = new MyList();
				int countClass = 0;
				int countInterface = 0;
				saveOk = false;

				for (Entity e : api.getAllEntities()) {
					status.updateProgressBar(Configurations
							.getString("message.Readclass")
							+ " : "
							+ e.getFullName());
					if (e instanceof Class) countClass++;
					else if (e instanceof Interface) countInterface++;
					
					isAPI.add(e.getMetricsValues().getIS());
					// isAPI.add(getCorrectIS(e));
					
					boolean analisesMethod = false;
					for (UseCase uc : useCases) if (uc.getMethods().size() > 0 ) analisesMethod = true;
					
					if ( analisesMethod )
						for (Method m : e.getMethods()){
							for (UseCase uc : useCases) 
								if (uc.getMethods().contains(m.getFullName())) {
									uc.addRelateMethods(m);
									uc.getStatistics().add(m.getMetricsValues().getIS());
								}
						}
					
					for (UseCase uc : useCases)
						if (uc.getPackages().size() > 0 && uc.getPackages().contains(e.getPackage().getName()))
							uc.addEntitiesToRelateName(e.getFullName());
				}

				saveOk = saveMyList(api.getNome(), countClass, countInterface, "All API", 0, 1, true, isAPI, new String[]{"Object","IS","IS Average","IS St Dev","IS Min","IS Max","#Class","#Interfaces"});

				AnalysisAPIUseCase(api);
				
				int sheetId = 1;
				
				for (UseCase uc : useCases) status.getProgress().setMaximum( status.getProgress().getMaximum() + uc.getRelatedEntities().size());
				
				if ( saveOk ) for (UseCase uc : useCases) {
					AnalysisRelatedEntities(uc, sheetId);
					sheetId+=2;
					if ( !saveOk ) break; 
				}
				
				saveOk = saveOk ? writeDocXLS() : saveOk;

				status.dispose();
//				System.exit(0);
				String msg = saveOk ? Configurations.getString("message.success.createFile") : Configurations.getString("message.error.writeXLS");
				if ( !saveOk ) Configurations.playErrorSound();
				JOptionPane.showMessageDialog(MainWindow.getInstance(), msg + " :\n" + fileXLS);
				
			}
		};
		t1.start();
	}

	public void AnalysisAPIUseCase(API api) {
		for (Entity e : api.getAllEntities()) {
			status.updateProgressBar(Configurations
					.getString("message.Readclass")
					+ " : "
					+ e.getFullName());
			for (UseCase uc : useCases)
				//if e isn't related yet, see if it's related with some request class
				if ( !uc.getRelatedEntities().contains(e) ) verifyIfEIsRelatedToUseCase(uc, e);
		}
	}
	
	public void AnalysisRelatedEntities(UseCase uc, int sheetId) {
		if ( !saveMyList(uc.getLabel(), uc.getCountClass(), uc.getCountInterfaces(), uc.getLabel(), sheetId++, 1, true, uc.getStatistics(), new String[]{"Object","IS","IS Average","IS St Dev","IS Min","IS Max","#Class","#Interfaces"})) return;
		int row = 1;
		for (Entity e : uc.getRelatedEntities()) {
			status.updateProgressBar(Configurations
					.getString("message.Readclass")
					+ " : "
					+ e.getFullName());
			MyList entityList = new MyList();
			for (Method m : e.getMethods()) entityList.add(m.getMetricsValues().getIS());
			saveOk = saveMyList(e.getFullName(), e.getMethods().size(), 0, uc.getLabel()+"_entities", sheetId, row++, true, entityList,new String[]{"Entity","IS","IS Average","IS St Dev","IS Min","IS Max","#Methods"});
			
			
				
				
				
				
			
//			for (Method m : e.getMethods())  if (uc.getMethods().contains(m.getFullName())) {
//				try {
//					addLabel(getSheet(uc.getLabel()+"_entities", sheetId), 0, row, m.getFullName());
//					addNumber(getSheet(uc.getLabel()+"_entities", sheetId), 1, row++, m.getMetricsValues().getIS());
//					uc.addRelateMethods(m);
//					uc.getStatistics().add(m.getMetricsValues().getIS());
//				} catch (RowsExceededException e1) {
//					e1.printStackTrace();
//				} catch (WriteException e1) {
//					e1.printStackTrace();
//				}
//			}
			if ( !saveOk ) return; 
		}
		for (Method m : uc.getRelateMethods()){
			try {
				addLabel(getSheet(uc.getLabel() + "_entities", sheetId), 0, row, m.getFullName());
				addNumber(getSheet(uc.getLabel() + "_entities", sheetId), 1, row++, m.getMetricsValues().getIS());
			} catch (RowsExceededException e1) {
				saveOk = false;
				e1.printStackTrace();
			} catch (WriteException e1) {
				saveOk = false;
				e1.printStackTrace();
			}
			if ( !saveOk ) return; 
		}
	}

	private void verifyIfEIsRelatedToUseCase(UseCase uc, Entity e) {
		analisaEntidade(uc, e);
		if( !uc.getRelatedEntities().contains(e) ) analisaSuperClass(uc, e);
		if( !uc.getRelatedEntities().contains(e) ) analisaAtributos(uc, e);
		if( !uc.getRelatedEntities().contains(e) ) analisaMetodos(uc, e);
	}

	/**
	 * Verify if e is direct related
	 * @param uc
	 * @param e
	 */
	private void analisaEntidade(UseCase uc, Entity e) {
		if (uc.getEntitiesToRelateName().contains(e.getFullName())) {
			uc.addRelatedEntities(e);
			uc.getStatistics().add(e.getMetricsValues().getIS());
		}
	}

	/**
	 * verify if e's superClass is to be related
	 * @param uc
	 * @param e
	 */
	private void analisaSuperClass(UseCase uc, Entity e) {
		if (e.getSuperClass() != null && uc.getEntitiesToRelateName().contains(e.getSuperClass().getFullName())) {
			uc.addRelatedEntities(e);
			uc.getStatistics().add(e.getMetricsValues().getIS());
		}
	}

	/**
	 * verify if some e's attribute (entity type) is to be related
	 * @param uc
	 * @param e
	 */
	private void analisaAtributos(UseCase uc, Entity e) {
		for (Attribute a : e.getAttributes())
			if (a.getModifier().equals("public") && a.getEntityType() != null && uc.getEntitiesToRelateName().contains(a.getEntityType().getFullName())) {
				uc.addRelatedEntities(e);
				uc.getStatistics().add(e.getMetricsValues().getIS());
				return; //if found some related att end search coz e is related yet.
			}
	}

	/**
	 * verify if some e's method's parameter (entity type) or return (entity type) is to be related
	 * @param uc
	 * @param e
	 */
	private void analisaMetodos(UseCase uc, Entity e) {
		for (Method m : e.getMethods()){
			if (m.getModifier().equals("public"))
				for (Entity clazz : m.getDependencies())
					if (uc.getEntitiesToRelateName().contains(clazz.getFullName())) {
						uc.addRelatedEntities(e);
						uc.getStatistics().add(e.getMetricsValues().getIS());
						return; //if found some related att end search coz e is related yet.
					}
		}
	}

	private void setSaveDir() {
		
	}

	private WritableWorkbook getWorkBook() {
		if (workbook == null) {
			WorkbookSettings wbSettings = new WorkbookSettings();
			wbSettings.setLocale(new Locale("en", "EN"));
			try {
				workbook = Workbook.createWorkbook(fileXLS, wbSettings);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return workbook;
	}

	private boolean writeDocXLS() {
		try {
			getWorkBook().write();
			getWorkBook().close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return false;
	}

	private WritableSheet getSheet(String label, int id) {
		if (getWorkBook().getNumberOfSheets() == id) getWorkBook().createSheet(label, id);
		return getWorkBook().getSheet(id);
	}

	private boolean saveMyList(String label, int countPart, int countInterface, String sheetLabel,
			int sheetid, int row, boolean writeHead, MyList list, String...headFields) {
		WritableSheet sheet = getSheet(sheetLabel, sheetid);
		
		try {
			if (writeHead) writeHeader(sheet, headFields);
			writeValues(label, countPart, countInterface, list, sheet, row);
		} catch (RowsExceededException e) {
			e.printStackTrace();
			return false;
		} catch (WriteException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private void writeHeader(WritableSheet sheet, String...headFields) throws RowsExceededException,
			WriteException {
		int row = 0;
		for (String s : headFields) {
			addLabel(sheet, row++, 0, s);
		}
	}

	private void writeValues(String objectName, int countPart, int countInterface, MyList list,
			WritableSheet sheet, int row) throws RowsExceededException,
			WriteException {
		addLabel(sheet, 0, row, objectName);
		addNumber(sheet, 1, row, list.getSum());
		addNumber(sheet, 2, row, list.getAverage());
		addNumber(sheet, 3, row, list.getStandardDeviation());
		addNumber(sheet, 4, row, list.getMin());
		addNumber(sheet, 5, row, list.getMax());
		addNumber(sheet, 6, row, countPart);
		addNumber(sheet, 7, row, countInterface);
	}

	private void addLabel(WritableSheet sheet, int collum, int row, String label)
			throws RowsExceededException, WriteException {
		Label l = new Label(collum, row, label);
		sheet.addCell(l);
	}

	private void addNumber(WritableSheet sheet, int collum, int row,
			double value) throws RowsExceededException, WriteException {
		WritableCellFormat cnf = new WritableCellFormat(nf);
		Number n = value == 0 ? new Number(collum, row, value) : new Number(
				collum, row, value, cnf);
		sheet.addCell(n);
	}

	@SuppressWarnings("unused")
	private double getCorrectIS(Entity e){
		double is = e.getMetricsValues().getIS();
		
//		if ( e instanceof Class)
			for (Entity iface : e.getInterfaces()){ 
//				if ( e.isAbstrakt() ){
					for (Method m : e.getMethods()) 
						for (Method m2 : iface.getMethods())
							if ( compareMethods(m, m2) ) {
								is -= m.getMetricsValues().getIS();
								break;
							}
				}
//				else is -= iface.getMetricsValues().getIS();
//			}
//			}
		if (is < 0 ) System.out.println( "id: " + e.getId() + " " + e + " menor " + e.getMetricsValues().getIS() + "  " + is);
//		else System.out.println(e + " normal: " + e.getMetricsValues().getIS() + "  " + is);
		
		return is;
	}
	
	private boolean compareMethods(Method m1, Method m2){
		if (m1.getName().equals(m2.getName()) && m1.getReturnType().equals(m2.getReturnType())){
			for (Attribute a : m1.getParameters())
				for (Attribute a2 : m2.getParameters())
					if (!a.getType().equals(a2.getType() )) return false;
		}
		else return false;
		return true;
	}
}
