package br.ufpa.linc.MetriX;

import java.awt.Component;
import java.awt.Cursor;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.dao.Database;
import br.ufpa.linc.MetriX.metricModel.Bandi2003Model;
import br.ufpa.linc.MetriX.metricModel.BokowskyEtAll2010Model;
import br.ufpa.linc.MetriX.metricModel.BoxallAraban2004Model;
import br.ufpa.linc.MetriX.metricModel.BritoEAbreuAndMelo1996Model;
import br.ufpa.linc.MetriX.metricModel.CK1994Model;
import br.ufpa.linc.MetriX.metricModel.Harrison1997Model;
import br.ufpa.linc.MetriX.metricModel.MetricModel;
import br.ufpa.linc.MetriX.metricModel.Stylos2007Model;
import br.ufpa.linc.MetriX.metricModel.Stylos2008Model;
import br.ufpa.linc.MetriX.parsers.java.ClassReader;
import br.ufpa.linc.MetriX.view.gui.MainWindow;
import br.ufpa.linc.MetriX.view.gui.Splash;
import br.ufpa.linc.MetriX.view.gui.Status;
import br.ufpa.linc.MetriX.view.gui.insert.FormInsertJar;
import br.ufpa.linc.MetriX.view.jtree.JTreeCheckBox;
import br.ufpa.linc.MetriX.view.jtree.MyJTree;
import br.ufpa.linc.MetriX.view.prefuse.data.Tree;
import br.ufpa.linc.MetriX.view.treemap.MyTreeMap;

//import com.jgoodies.looks.Options;

public class MetriX {

	private API api = null;
//	private ZScore zscore = null;
	private Status status = null;
	private Tree tree = null;
	private MyJTree myJtree = null;
	/* **********************
	 * private JPanel starPlotPanel = null;
	 * private JPanel myGraphPanel = null;
	 * private MyGraph myGraph = null;
	 * private String graphLayout = null;
	 * private Mode mouseMode = null;
	 * 
	 **************************/
	private Object showObject = null;
	private int selectedTab = 0;
	private File jarDir = null;
	private JTreeCheckBox jTreeCheckBox = null;
	private List<MetricModel> metricModelsAvailable;
	private static MetriX metrix = null;
	private List<MetricModel> metricModels = null;
	//0 - number of methods
	//1 - number of calls
	//2 - number of references
	//3 - number of bugs
	private int defineSize = 0;
	//0 - IS
	//1 - CBO
	private int defineColor = 0;
	private boolean showPackageName = false;
	private boolean showClassName = false;
	private int count = 0;
//	private boolean pasta;
	private boolean relativeInternal;
	public final static double _Version = 1.4;
	public final static int _Release = 2;
	public static File tempDir;
	public static boolean useFullPath;
	public static enum VendorDB { Derby, HSQLDB, MySQL, Prevayler}//PostgreeSQL
	
	private MetriX(){
        
		try {
//			Skin skin = SkinLookAndFeel.loadThemePack( getClass().getResource("/themes/quickSilverRthemepack.zip"));
////			Skin skin = SkinLookAndFeel.loadThemePack( getClass().getResource("/themes/coronaHthemepack.zip"));
////			Skin skin = SkinLookAndFeel.loadThemePack( getClass().getResource("/themes/iBarthemepack.zip"));
////			Skin skin = SkinLookAndFeel.loadThemePack( getClass().getResource("/themes/themepack_original.zip"));
////			Skin skin = SkinLookAndFeel.loadThemePack( getClass().getResource("/themes/tigerthemepack.zip"));
//			SkinLookAndFeel.setSkin(skin);
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
			
//*******************			UIManager.put(Options.USE_SYSTEM_FONTS_APP_KEY, Boolean.TRUE);
//*******************	        Options.setDefaultIconSize(new Dimension(18, 18));

//			UIManager.setLookAndFeel( new SubstanceOfficeBlue2007LookAndFeel() );
//			SubstanceLookAndFeel.setSkin(new OfficeBlue2007Skin() );
		} catch (Exception e) {
			Configurations.playErrorSound();
			System.out.println("Can't Load L&F"); 
		}
		Locale.setDefault(Configurations.getLocale());
		if ( checkVersion() ) System.out.println("Version ok");
		else {
			if ( new File(Configurations.getDBDir()).listFiles().length == 0 ){ // || !Configurations.getDatabaseConfiguration()[0].equals(VendorDB.Prevayler.toString())) {
				Configurations.setVersion(_Version);
				Configurations.setRelease(_Release);
			} else{
//				String message = ". The database version is diferente of your." +
//								"\nIf want to backup you database, click \"No\" in  copy the folder " + Configurations.getDBDir() +
//								"\n\nTo fix this, please run Metrix using this command:\njava -jar MetriX.jar cleardatabase";
				
				if ( Configurations.clearDatabase() ) {
					Configurations.setVersion(_Version);
					Configurations.setRelease(_Release);
				}
				else System.exit(1);
			}
		}
		
		Splash.getInstance().getProgressBar().setValue(Splash.getInstance().getProgressBar().getValue()+1);
		Splash.getInstance().getProgressBar().setString(Configurations.getString("splash.database"));
		Database.getInstance();
		/* ****************
		Splash.getInstance().getProgressBar().setValue(Splash.getInstance().getProgressBar().getValue()+1);

		setGraphLayout("CIRCLE");
		
		Splash.getInstance().getProgressBar().setValue(Splash.getInstance().getProgressBar().getValue()+1);
		setMouseMode(Mode.PICKING);
		**********************/
		Splash.getInstance().getProgressBar().setValue(Splash.getInstance().getProgressBar().getValue()+1);
		Splash.getInstance().getProgressBar().setString(Configurations.getString("splash.gui"));
		MainWindow.getInstance();
	}

	public synchronized static MetriX getInstance() {
		if (metrix == null) metrix = new MetriX();
		return metrix;
	}		
		
	private boolean checkVersion(){
		System.out.println("Default dir: " + Configurations.getAppDir());
		if ( _Version == Configurations.getVersion() && _Release == Configurations.getRelease() ) return true;
		return false;
	}
	
	public void insertApiFolder (File dir){
		jarDir = dir;
		nextJar();
	}
	
	public void nextJar(){
		File jars[] = getJarDir().listFiles();
		File jar = null;
		
		if ( count == jars.length ) {
			JOptionPane.showMessageDialog(MainWindow.getInstance(), "all jars inserted");
			count = 0;
//			pasta = false;
			return;
		}
		
		jar = jars[count++];
		if ( jar != null && jar.getName().endsWith(".jar") ) insertProjectJar( jar );
		else nextJar();
	}
	
	public void openView(){ if ( api != null ) new MyTreeMap().getTreeMap(api); }
	
//	public void openStarPlot(){
//		if ( api!= null ) new MyStarPlot(this).getStarplot(api);
//	}
	
	public void insertProjectJar(){ new FormInsertJar(); }
	
	public void insertProjectJar(File dir ){ new ClassReader(dir); }
		
	public void removeProject( ){
//		Configurations.getString("analysis.start")
		setStatus( new Status("Remove", 2));
		//Criar Thread aki!
		Thread t = new Thread(getStatus());
		t.start();
		Thread t2 = new Thread(){
			public void run() {
				System.out.println("Removing");
				getStatus().updateProgressBar("Removing from the Data Base");
				
				boolean removed = Database.getInstance().remove( getApi() );
				getStatus().updateProgressBar("Done");
				getStatus().getProgress().setString("");
//				try {
//					sleep(1300);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
				getStatus().dispose();
				if ( removed ) JOptionPane.showMessageDialog(MainWindow.getInstance(), Configurations.getString("message.dao.removed"), Configurations.getString("window.title.success"), JOptionPane.INFORMATION_MESSAGE);
				else JOptionPane.showMessageDialog(MainWindow.getInstance(), Configurations.getString("message.dao.errorRemove"), Configurations.getString("window.title.error"), JOptionPane.INFORMATION_MESSAGE);
			}
		};
		t2.start();
	}
	
	public void changeLanguage(Locale l){
		Configurations.setLocale(l);
		Locale.setDefault(l);
		MainWindow.getInstance().changeLanguage();
	}
	
//	public API openChooseApi(){
//		List<API> apis = getDataBase().getAllAPI();
//
//		if ( apis.size() == 0 ) {
//			JOptionPane.showMessageDialog(mainWindow, language.getString("message.noAPI"));
//			return null;
//		}
//		
//		Object[] modelApi = new API[apis.size()];
//		int count = 0;
//		for (API api : apis)
//			modelApi[count++] = api;
//		
//		Object o = JOptionPane.showInputDialog(
//		                    mainWindow,
//		                    language.getString("comboBox.api.title"),
//		                    language.getString("comboBox.api.msg"),
//		                    JOptionPane.PLAIN_MESSAGE,
//		                    new ImageIcon(),
//		                    modelApi,
//		                    language.getString("comboBox.choose"));
//		if ( o == null ) return null;
//		return (API) o;
//	}
	
//	public void clearWeights(){
//		oacWeight = 1 ;
//		ilWeight = 1 ;
//		isWeight = 1 ;
//		topPanel.clearWeights();
//	}

//	public double getOacWeight() {
//		return oacWeight;
//	}
//
//	public void setOacWeight(double oacWeight) {
//		this.oacWeight = oacWeight;
//	}
//
//	public double getIlWeight() {
//		return ilWeight;
//	}
//
//	public void setIlWeight(double ilWeight) {
//		this.ilWeight = ilWeight;
//	}
//
//	public double getIsWeight() {
//		return isWeight;
//	}
//
//	public void setIsWeight(double isWeight) {
//		this.isWeight = isWeight;
//	}

	

//	public TopPanel getTopPanel() {
//		return topPanel;
//	}
//
//	public void setTopPanel(TopPanel topPanel) {
//		this.topPanel = topPanel;
//	}

//	public boolean isUpdateStatus() {
//		return updateStatus;
//	}
//
//	public void setUpdateStatus(boolean updateStatus) {
//		this.updateStatus = updateStatus;
//	}

	public double[] getDecril(List<Double> values){
		Collections.sort(values);
		double dectril[] = new double[10+1];
		int n = values.size()+1;
		if ( n < 11 ) return new double[n];
		
		int index = 0;
		dectril[0] = values.get( index );
		
		for ( int i = 1 ; i < dectril.length ; i++){
			index = Math.round(i * n/10 -1) ;
			if ( i == dectril.length - 1) index-=1;
			dectril[i] = values.get( index );
			if ( i == 2 && n % 2 != 0 ){
				dectril[i] += values.get( index-1 );
				dectril[i] /= 2;
			}
		}
		
//		for( int i = 0 ; i < dectril.length ; i++) System.out.println("D" + i + ": " + dectril[i]);
		return dectril;
	}
	
	//GET e SET
//////////////////	public void setPasta( boolean pasta ){ this.pasta = pasta; }
/////////////////////////////////////	public boolean isPasta(){ return pasta; }
	
	public API getApi() { return api; }

	public void setApi(API api) { this.api = api; }
	
//	public ZScore getZscore() { return zscore; }
//
//	public void setZscore(ZScore zscore) { this.zscore = zscore; }
	
	public Status getStatus() { return status; }

	public void setStatus(Status status) { this.status = status; }
	
	public Tree getTree() { return tree; }

	public void setTree(Tree tree) { this.tree = tree; }
	
	public Object getShowObject() { return showObject; }

	public void setShowObject(Object showObject) { this.showObject = showObject; }
	
	public MyJTree getMyJtree() { return myJtree; }

	public void setMyJtree(MyJTree myJtree) { this.myJtree = myJtree; }

	/* ******************************
	 * public JPanel getStarPlotPanel() { return starPlotPanel; }

	public void setStarPlotPanel(JPanel starPlotPanel) { this.starPlotPanel = starPlotPanel; }

	public String getGraphLayout() { return graphLayout; }

	public Mode getMouseMode() { return mouseMode; }

	public void setGraphLayout(String graphLayout) { this.graphLayout = graphLayout; }

	public void setMouseMode(Mode mouseMode) { this.mouseMode = mouseMode; }

	public JPanel getMyGraphPanel() { return myGraphPanel; }

	public void setMyGraphPanel(JPanel myGraphPanel) { this.myGraphPanel = myGraphPanel; }

	public MyGraph getMyGraph() { return myGraph; }
	
	public void setMyGraph(MyGraph myGraph) { this.myGraph = myGraph; }

	***************************/
	public int getSelectedTab() { return selectedTab; }

	public void setSelectedTab(int selectedTab) { this.selectedTab = selectedTab; }
	
	public File getJarDir() { return jarDir; }

	public void setJarDir(File jarDir) { this.jarDir = jarDir; }
	
	public JTreeCheckBox getJTreeCheckBox() { return jTreeCheckBox; }

	public void setJTreeCheckBox(JTreeCheckBox treeCheckBox) { jTreeCheckBox = treeCheckBox; }
	
	/**
	 * 0 - number of methods
	 * 1 - number of calls
	 * 2 - class Link
	 * 3 - Bug Count
	 * 4 - CBO
	 * @return 0..4
	 */
	public int getDefineSize() {
		return defineSize;
	}

	/**
	 * 0 - number of methods
	 * 1 - number of calls
	 * 2 - class Link
	 * 3 - Bug Count
	 * 4 - CBO
	 * @param defineSize 0..4
	 */
	public void setDefineSize(int defineSize) { this.defineSize = defineSize; }
	
	/**
	 * 0 - IS
	 * 1 - CBO
	 * @return 0..1
	 */
	public int getDefineColor() {
		return defineColor;
	}
	/**
	 * 0 - IS
	 * 1 - CBO
	 * @param defineSize 0..1
	 */
	public void setDefineColor(int defineColor) { this.defineColor = defineColor; }
	public boolean isShowPackageName() { return showPackageName; }

	public void setShowPackageName(boolean showPackageName) { this.showPackageName = showPackageName; }

	public boolean isShowClassName() { return showClassName; }

	public void setShowClassName(boolean showClassName) { this.showClassName = showClassName; }
	
	public void setMetricModelsAvailable(List<MetricModel> metricModelAvaible) { this.metricModelsAvailable = metricModelAvaible; }

	public List<MetricModel> getMetricModelsAvailable() {
		if(metricModelsAvailable==null) metricModelsAvailable = getMetricModels();
		return metricModelsAvailable;
	}

	public void setRelativeInternal(boolean selected) { relativeInternal = selected; }
	
	public boolean isRelativeInternal() { return relativeInternal; }
	
	public List<MetricModel> getMetricModels() {
		if ( metricModels == null ){
			metricModels = new ArrayList<MetricModel>();
			metricModels.add( new Bandi2003Model() );
			metricModels.add( new BoxallAraban2004Model() );
//			metricModels.add( new CallModel() );
			metricModels.add(new Stylos2007Model());
			metricModels.add(new Stylos2008Model());
//			metricModels.add( new BugCountModel() );
			metricModels.add(new CK1994Model());
			metricModels.add(new Harrison1997Model());
			metricModels.add(new BritoEAbreuAndMelo1996Model());
			metricModels.add(new BokowskyEtAll2010Model());
		}
		return metricModels;
	}
	
	public static void changeMouse(boolean wait, Component...components){
		for (Component c : components) {
			if ( wait ) c.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			else c.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	public static void main(String[] args) {
		if (args.length == 1 && args[0].equals("cleardatabase") &&  Configurations.clearDatabase() ) System.out.println("database removed successfully");
			
		MetriX.getInstance(); 
	}
}