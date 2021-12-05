package br.ufpa.linc.MetriX.configurations;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

///////////////////////import sun.audio.AudioDevice;
import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.analysis.Metric;
import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Package;
import br.ufpa.linc.MetriX.view.gui.MainWindow;
import br.ufpa.linc.MetriX.view.prefuse.util.ColorLib;

public class Configurations {
	
	private static Properties prop;
	private static int attempt = 0;
	private static ResourceBundle rb;
	
	public static int[] getTreeMapColors(){
		return new int[]{
			ColorLib.rgb(0,139,69),
			ColorLib.rgb(25,114,44),
			ColorLib.rgb(50,89,19),
			ColorLib.rgb(75,64,0),
			ColorLib.rgb(100,39,0),
			ColorLib.rgb(125,14,0),
			ColorLib.rgb(150,0,0),
			ColorLib.rgb(175,0,0),
			ColorLib.rgb(200,0,0),
			ColorLib.rgb(225,0,0),
			ColorLib.rgb(250,0,0)
		};
//			palette = ColorLib.getInterpolatedPalette(11, ColorLib.rgb(102,153,102), ColorLib.rgb(153,0,0));
	}
	
	public static Color[] getStarPlotColors(){
//			palette = ColorLib.getInterpolatedPalette(11, ColorLib.rgb(102,153,102), ColorLib.rgb(153,0,0));
		return new Color[]{
				Color.red,
				Color.yellow,
				Color.white,
				Color.gray,
				Color.green,
//				Color.orange,
				Color.blue
		};
	}
	
	public static Color[] getGraphColors(){
		return new Color[]{
					Color.BLUE,
					Color.ORANGE,
					Color.CYAN,
					Color.RED,
					Color.DARK_GRAY,
					Color.MAGENTA,
					Color.YELLOW,
					Color.PINK,
					Color.LIGHT_GRAY,
					Color.GRAY,
					Color.BLACK,
					Color.WHITE,
					Color.GREEN
		};
	}

	public static double[] getDectril(int type){
		
		//normal distribution
		double[] normal = new double[]{
				-7046.72,
				-4476.33,
				-1905.95,
				  664.43,
				 3234.82,
				  5805.2,
				 8375.59
				};
		//Quatrile sum ALL metric
		double[] quatrile = new double[]{
				0, //Min
				425, //Q1
				1017.5, //Q2
				2504, //Q3
				306001 //Max
				};
		
		//Septile sum ALL metric
		double[] septil = new double[]{
				0, //Min
				240, //S1
				490, //S2
				817, //S3
				1266, //S4
				2131, //S5
				4863, //S6
				306001 //S7
				};
		
		//Dectile sum ALL metric
		double[] dectil = new double[]{
				0, //Min
				168, //D1
				336, //D2
				517, //D3
				735, //D4
				1017, //D5
				1387, //D6
				2020, //D7
				3176, //D8
				7331, //D9
				306001 //Max
		};
		
		//Dectile2 sum ALL metric
		double[] dectil2 = new double[]{
				0, //Min
				244, //D1
				600, //D2
				1402, //D3
				5268, //D4
				36268, //D5
				1688094, //D6
				2.147484244E+009, //D7
				4.498507340E+009, //D8
				1.3035189028E+010, //D9
				1.68536602084978E+024 //Max
		};
		double[] dectilValorMemoria = new double[]{
				0,
				256,
				517,
				822.5,
				1241,
				1889,
				3119,
				5591,
				9967,
				34619,
				7958875
		};
		
		double[] dectilValorMemoria2 = new double[]{
				0,
				647,
				1418,
				2621,
				4855,
				8600,
				20210,
				58432,
				168719,
				738366,
				1.1660712718771E13
			};
		
		if ( type == 0 ) return normal;
		if ( type == 0 ) return dectil2;
		else if ( type == 4 ) return quatrile;
		else if ( type == 7 ) return septil;
		else if ( type == 10 ) return dectil;
		else if ( type == 11 ) return dectilValorMemoria;
		return dectilValorMemoria2;		
	}
	
	public static double[] getDectrilCBO(){
		return new double[]{
				0,
				12,
				26,
				40,
				54,
				68,
				81,
				96,
				114,
				148.0,
				410.0
			};
	}
	public static double[] getDectrilWeight(){
		
		double[] weight = new double[]{
				0.1,
				0.2,
				0.3,
				0.4,
				0.5,
				0.6,
				0.7,
				0.8,
				0.9,
				1,
				1.1
				};
		return weight;
	}
	public static double[] getDectrilISRelative(API api){

		List<Double> metrics = new ArrayList<Double>();
		
		double is = 0;
		
		for (Package p : api.getPackages())
			if ( p.isShow() ) 
				for (Entity e : p.getEntities()){
					is = e.getMetricsValues().getIS();
					if ( !metrics.contains( is )) metrics.add( is );
				}

		Collections.sort(metrics);
		
		return MetriX.getInstance().getDecril(metrics);
	}
	
	public static double[] getDectrilCBORelative(API api){

		List<Double> metrics = new ArrayList<Double>();
		
		double cbo = 0;
		
		for (Package p : api.getPackages())
			if ( p.isShow() ) 
				for (Entity e : p.getEntities()){
					cbo = e.getMetricsValues().getCBO();
					if ( !metrics.contains( cbo )) metrics.add( cbo );
				}

		Collections.sort(metrics);
		
		return MetriX.getInstance().getDecril(metrics);
	}
	
	public static void addMetric(double value, List<Metric> metrics){
		for (Metric m : metrics)
			if ( m.getValue() == value ) {
				m.setFrequency( m.getFrequency() + 1 );
				return;
			}
		metrics.add( new Metric( value ) );
	}

	public static void copyToClipboard(String content){
		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();  
	    StringSelection ss = new StringSelection (content);  
	    clip.setContents (ss, ss);
	}
	    
	private static ResourceBundle getLanguage(){
		if ( rb == null ) rb = ResourceBundle.getBundle("language.MetriX");
		return rb;
	}
	
	public static String getString(String key){
		return getLanguage().getString(key);
	}
	
	public static File getAppDir() {
		File appDir = null;
		
		appDir = new File( new File(System.getProperty("user.home") + System.getProperty("file.separator") + ".MetriX").toURI().getPath() );
		
		if ( !appDir.mkdirs() && !appDir.exists() ) appDir = new File( new File(".MetriX").toURI().getPath() );
		appDir.mkdirs();
		
		return appDir;
	}
	
	public static File getAnalysisDir() {
		File analysisDir = null;
		
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH.mm");
		Date date = new Date();
		
		analysisDir = new File(getAppDir() + System.getProperty("file.separator") + "apiAnalysis_" + dateFormat.format(date) + System.getProperty("file.separator"));
		
		if ( !analysisDir.exists() ) analysisDir.mkdirs();
		
		return analysisDir;
	}
	
	public static String getDBDir() {
		File DBDir = null;
		DBDir = new File(getAppDir().getAbsolutePath() + System.getProperty("file.separator") + "database" );
		if ( !DBDir.exists() ) DBDir.mkdirs();
		return DBDir.getAbsolutePath();
	}
	
 	public static File getTempDir(){
		File tempDir = new File (getAppDir().getAbsolutePath() + System.getProperty("file.separator") + "temp");

		if ( tempDir.exists() ) clearDir(tempDir);
		else if ( !tempDir.mkdirs() ) {
			JOptionPane.showMessageDialog(null, getLanguage().getString("message.error.classDir") ); 
			return null;
		}
 		return tempDir;
	} 
	
 	public static boolean clearDatabase(){
 		int n = JOptionPane.showConfirmDialog(
			    MainWindow.getInstance(),
			    getLanguage().getString("message.error.databaseVersion.p1") + 
			    Configurations.getDBDir() +
			    getLanguage().getString("message.error.databaseVersion.p2"),
			    getLanguage().getString("window.title.removeDatabase"),
			    JOptionPane.YES_NO_OPTION);
			if ( n == 0  && clearDir( new File(Configurations.getDBDir()) )){
				JOptionPane.showMessageDialog(MainWindow.getInstance(), getLanguage().getString("message.removeDatabase.success"));
				return true;
			}
			return false;
 	}
 	
 	public static boolean clearDir(File path){
	    if( path.exists() )
	    	if (path.isDirectory()) {
	 	        File[] files = path.listFiles();
		        for(int i=0; i<files.length; i++)
		           if(files[i].isDirectory()) clearDir(files[i]);
		           else files[i].delete();
	   	}
        return(path.delete());
 	}
 	
 	public static List<URI> copyDir(File from, File to){
 		List<URI> uris = null;
	    if( from.exists() )
	    	if (from.isDirectory()) {
	    		uris = new ArrayList<URI>();
	    		File newPath = new File(to.getAbsolutePath() + System.getProperty("file.separator") + from.getName()),
	    			newFile;
	    		if ( !newPath.exists() && !newPath.mkdirs() ) return null;
	 	        File[] files = from.listFiles();
		        for(int i=0; i<files.length; i++)
		           if( files[i].isDirectory() ) { if ( copyDir(files[i], newPath) == null) return null; }
		           else if ( files[i].getName().endsWith(".class") ) {
		        	   newFile = copyFile(files[i], newPath);
		        	   if (newFile != null) uris.add( newFile.toURI() );
		        	   else return null;
		           }
	   	}
        return uris;
 	}
 	
// 	public static File[] copyDirFiles(File from, File To){
//	    if( from.exists() )
//	    	if (from.isDirectory()) {
//	 	        File[] files = from.listFiles();
//		        for(int i=0; i<files.length; i++)
//		           if(files[i].isDirectory()) copyDir(files[i]);
//		           else files[i].delete();
//	   	}
//        return(path.delete());
// 	}
	
 	private static File copyFile(File fromFile, File toFolder) {
 		byte buffer[] = null;
		InputStream in = null;
		DataOutputStream dos = null;
		File newFile = new File(toFolder + System.getProperty("file.separator") + fromFile.getName());
		int c, i = 0;
		
		buffer = new byte[(int) fromFile.length()];
		try {
			in = new FileInputStream( fromFile );
			while ((c = in.read()) != -1)
				buffer[i++] = (byte) c;
			dos = new DataOutputStream(new FileOutputStream(newFile));
			dos.write(buffer);
			dos.flush();
			dos.close();
			return newFile;
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return null;
	}
 	
	public InputStream getResourceAsStream(String resource) {
 		String stripped = resource.startsWith("/") ? 
 				resource.substring(1) : resource;

 		InputStream stream = null; 
 		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
 		if (classLoader!=null) {
 			stream = classLoader.getResourceAsStream( stripped );
 		}
 		if ( stream == null ) {
 			Configurations.class.getResourceAsStream( resource );
 		}
 		if ( stream == null ) {
 			stream = Configurations.class.getClassLoader().getResourceAsStream( stripped );
 		}
 		if ( stream == null ) {
 			throw new RuntimeException( resource + " not found" );
 		}
 		return stream;
 	}
 	
	/**
	 * Read configuration file (properties)
	 * @return {@link Properties} with configuration data
	 */
	public static Properties getProperties() {
		if ( prop == null ){
			prop = new Properties();
			try {
				prop.load(new FileInputStream( getDefinitionsFile() ));
			} catch (FileNotFoundException e) {
				System.err.println("Propertie File Not Found: " + getDefinitionsFile().getAbsolutePath());
				if ( attempt++ == 2 ) System.exit(1);
				createDefinitionsFile();
				return getProperties();
			} catch (IOException e) {
				System.err.println(Configurations.getLanguage().getString("message.error.readXMLTryAgain") + "\n" + getDefinitionsFile().getAbsolutePath());
				if ( attempt++ == 2 ) System.exit(1);
			}
		}
		return prop;
	}
	
	public static void setProperties(Properties prop){
		Configurations.prop = prop;
	}
	
	public static void saveProperties(Properties prop){
		try {
			prop.store(new FileOutputStream(getDefinitionsFile()),"MetriX Definitions");
			setProperties(prop);
		} catch (FileNotFoundException e) {
			System.err.println("Propertie File Not Found: " + getDefinitionsFile().getAbsolutePath());
			if ( attempt++ == 2 ) System.exit(1);
			createDefinitionsFile();
			saveProperties(prop);
		} catch (IOException e) {
			System.err.println(Configurations.getLanguage().getString("message.error.writeXMLTryAgain") + "\n" + getDefinitionsFile().getAbsolutePath());
			if ( attempt++ == 2 ) System.exit(1);
			createDefinitionsFile();
			saveProperties(prop);
		}
	}
	
 	public static Locale getLocale(){
 		String language = getProperties().getProperty("language", "en");
 		String country = getProperties().getProperty("country", "US");
 		return new Locale(language, country);
 	}
 	
 	public static void setLocale(Locale locale){
 		prop = null; // to ensure that the language
 		rb = null;   // will be changed dynamically
 		getProperties().setProperty("language", locale.getLanguage());
 		getProperties().setProperty("country", locale.getCountry());	
 		saveProperties(getProperties());
 	}
 	
 	public static String[] getDatabaseConfiguration(){
 		String database[] = new String[6];
		 		
 		if ( getProperties().getProperty("database.vendor").equals("") ) createDefinitionsFile();
 		database[0] = getProperties().getProperty("database.vendor", MetriX.VendorDB.Derby.toString());
 		database[1] = getProperties().getProperty("database.host", "localhost");
 		database[2] = getProperties().getProperty("database.port", "3306");
 		database[3] = getProperties().getProperty("database.dataBaseName", "metrix");
 		database[4] = getProperties().getProperty("database.dataBaseUser", "metrix");
 		database[5] = getProperties().getProperty("database.password", "metrix");
 		
 		return database;
 	}
 	
 	public static void setDatabaseConfiguration(String[] database){
 		prop = null; // to ensure that the language
 		rb = null;   // will be changed dynamically
 		
 		if ( database.length < 6 ) return;
 		
 		getProperties().setProperty("database.vendor", database[0]);
 		getProperties().setProperty("database.host", database[1]);
 		getProperties().setProperty("database.port", database[2]);
 		getProperties().setProperty("database.dataBaseName", database[3]);
 		getProperties().setProperty("database.dataBaseUser", database[4]);
 		getProperties().setProperty("database.password", database[5]);

 		saveProperties(getProperties());
 	}
 	
 	public static String getLastOpenPath(){
 		String path = getProperties().getProperty("openPath", System.getProperty("user.home"));
 		
 		return path;
 	}
 	
 	public static void setLastOpenPath(String path){
 		getProperties().setProperty("openPath", path);
 		saveProperties(getProperties());
 	}
 	
 	public static String getLastSavePath(){
 		String path = getProperties().getProperty("savePath", System.getProperty("user.home"));
 		
 		return path;
 	}
 	
 	public static void setLastSavePath(String path){
 		getProperties().setProperty("savePath", path);
 		saveProperties(getProperties());
 	}
 	
 	public static double getVersion(){
 		double version = Double.parseDouble(getProperties().getProperty("version", "0"));
 		
 		return version;
 	}
 	
	public static void  setVersion(double version){
		getProperties().setProperty("version", String.valueOf(version));
 		saveProperties(getProperties());
 	}
	
 	public static int getRelease(){
 		int release = Integer.parseInt(getProperties().getProperty("release", "0"));
 		
 		return release;
 	}
 	
 	public static void setRelease(int release){
 		getProperties().setProperty("release", String.valueOf(release));
 		saveProperties(getProperties());
 	}
 	
 	private static File getDefinitionsFile(){
 		File definitions = new File( getAppDir() + System.getProperty("file.separator") + "config.properties" );
 		if ( !definitions.exists() ) createDefinitionsFile();
 		return definitions;
 	}
 	
 	private static void createDefinitionsFile(){
 		
 		Properties prop = new Properties();
 		
 		File definitions = new File( getAppDir() + System.getProperty("file.separator") + "config.properties" );
 		if ( definitions.exists() ) definitions.delete();
 		
 		Locale l = Locale.getDefault();
 		
 		prop.setProperty("language", l.getLanguage());
 		prop.setProperty("country", l.getCountry());
 		
 		prop.setProperty("openPath", System.getProperty("user.home"));
 		prop.setProperty("savePath",  System.getProperty("user.home"));
 		
 		prop.setProperty("version", String.valueOf(MetriX._Version));
 		prop.setProperty("release", String.valueOf( MetriX._Release));
 		
 		prop.setProperty("database.vendor", MetriX.VendorDB.Derby.toString());
 		prop.setProperty("database.host", "localhost");
 		prop.setProperty("database.port", "3306");
 		prop.setProperty("database.dataBaseName", "metrix");
 		prop.setProperty("database.dataBaseUser", "metrix");
 		prop.setProperty("database.password", "metrix");
 		
 		setProperties(prop);
 		
 		try {
			prop.store(new FileOutputStream(definitions),"MetriX Definitions");
		}
 		catch (FileNotFoundException e) { e.printStackTrace(); }
		catch (IOException e) { e.printStackTrace(); }
 	}

	public static void playErrorSound() {
		//////////////////////////AudioDevice.device.openChannel( Configurations.class.getResourceAsStream("/sounds/error.wav"));
	}
 	
// 	 public static void main(String[] args) {
//// 		int[] palette = new Configurations().getColors();
//
// 		JFrame jf = new JFrame();
//		jf.setLayout(new FlowLayout());
//		JLabel jl = null;
// 		Color c = null;
//// 		int index = ColorLib.rgb(153,204,50),
// 		int index = ColorLib.rgb(253,251,102),
// 			red = 0,
// 			green = 0,
// 			blue = 0;
// 		
//// 		int maxRed = 255, maxBlue = 0, maxGreen = 0;
// 		int maxRed = 119;//, maxBlue = 0, maxGreen = 0; 
//// 		for (int i = 0 ; i <  palette.length; i++) {
//// 			c = ColorLib.getColor(palette[i]);
//// 			jl = new JLabel("    ");
////			jl.setOpaque(true);
////			jl.setBackground(c);
////			jl.setToolTipText(i + ": " + red + "," + green + "," + blue + "  " + index);
////			jf.add(jl);
////		}
// 		for ( int i = 0 ; i < 11 ; i++){
//			c = ColorLib.getColor(index);
//			red = c.getRed();
//			green = c.getGreen();
//			blue = c.getBlue();
//			System.out.println("ColorLib.rgb(" + red + "," + green + "," + blue + "),");
//			jl = new JLabel("    ");
//			jl.setOpaque(true);
//			jl.setBackground(c);
//			jl.setToolTipText(i + ": " + red + "," + green + "," + blue + "  " + index);
//			jf.add(jl);
//			if (red == maxRed )break;
////			if ( red != 255 ) red++;
////			else if ( green > 125 ) green--;
////			else {
////				System.out.println(green);
////				if ( green > 0 ) green--;
////				if ( blue > 0 ) blue--;
////			}
//			
//			if ( red < 255 ) red += 25;
//			if ( red > 255 ) red = 255;
//			
//			if ( green > 0 ) green-=25;
//			if ( green < 0 ) green = 0;
//			
//			if ( blue > 0 ) blue-=25;
//			if ( blue < 0 ) blue = 0;
//			
////			if ( red < 255 ) red+=50;
////			if ( red > 255 ) red = 255;
////			
////			if ( red == 255)
////				if( green > 125 ) green-=50;
////				else {
////					if ( green > 0 ) green-=50;
////					if ( green < 0 ) green = 0;
////					if ( blue > 0 ) blue-=50;
////					if ( blue < 0 ) blue = 0;
////				}
//			index = ColorLib.rgb(red,green,blue);
//		}
// 		
////		for ( int i = 0 ; i < 300 ; i++){
////			if ( i < 181 ) index = -16777216 + i;
////			else if ( i < 511 ) index += 256;
////			else index += 65536;
////			c = ColorLib.getColor(index);
////			System.out.println(i + ": " + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + "  " + (-16777216 + i));
////			jl = new JLabel("    ");
////			jl.setOpaque(true);
////			jl.setBackground(c);
////			jf.add(jl);
////		}
//
//		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//		jf.setSize(200,200);
//		jf.setVisible(true);
//
//	}
}
