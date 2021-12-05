package br.ufpa.linc.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *@author david
 *Date: 23/09/2010
 */
public class Files {

	public static List<File> listClasses(File root) {
		List<File> classes = new ArrayList<File>();
		for (File f : root.listFiles()) {
			if (f.isDirectory())
				for (File file : listClasses(f))
					classes.add(file);
			else if (f.getName().endsWith("class"))
				classes.add(f);
		}
		return classes;
	}
	
	public static List<File> listDir(File root) {
		List<File> dirs = new ArrayList<File>();
		for (File f : root.listFiles()) {
			if (f.isDirectory()){
				dirs.add(f);
				for (File file : listDir(f))
					dirs.add(file);
			}
		}
		return dirs;
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
}
