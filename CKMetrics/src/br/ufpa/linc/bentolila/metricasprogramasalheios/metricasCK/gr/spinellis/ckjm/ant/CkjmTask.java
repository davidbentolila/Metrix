/*
 * $Id: CkjmTask.java 1.3 2007/07/25 15:19:09 dds Exp $
 *
 * (C) Copyright 2005 Diomidis Spinellis, Julien Rentrop
 *
 * Permission to use, copy, and distribute this software and its
 * documentation for any purpose and without fee is hereby granted,
 * provided that the above copyright notice appear in all copies and that
 * both that copyright notice and this permission notice appear in
 * supporting documentation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
 * MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */

package br.ufpa.linc.bentolila.metricasprogramasalheios.metricasCK.gr.spinellis.ckjm.ant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.apache.tools.ant.types.Path;

import br.ufpa.linc.bentolila.metricasprogramasalheios.metricasCK.gr.spinellis.ckjm.MetricsFilter;
import br.ufpa.linc.bentolila.metricasprogramasalheios.metricasCK.gr.spinellis.ckjm.PrintPlainResults;

/**
 * Ant task definition for the CKJM metrics tool.
 *
 * @version $Revision: 1.3 $
 * @author Julien Rentrop
 */
public class CkjmTask extends MatchingTask {
    private File outputFile;

    private File classDir;

    private Path extdirs;

    private String format;

    public CkjmTask() {
        this.format = "plain";
    }

    /**
     * Sets the format of the output file.
     *
     * @param format
     *            the format of the output file. Allowable values are 'plain' or
     *            'xml'.
     */
    public void setFormat(String format) {
        this.format = format;

    }

    /**
     * Sets the outputfile
     *
     * @param outputfile
     *            Location of outputfile
     */
    public void setOutputfile(File outputfile) {
        this.outputFile = outputfile;
    }

    /**
     * Sets the dir which contains the class files that will be analyzed
     *
     * @param classDir
     *            Location of class files
     */
    public void setClassdir(File classDir) {
        this.classDir = classDir;
    }

    /**
     * Sets the extension directories that will be used by ckjm.
     * @param extdirs a path containing .jar files
     */
    public void setExtdirs(Path e) {
        if (extdirs == null) {
            extdirs = e;
        } else {
            extdirs.append(e);
        }
    }

    /**
     * Gets the extension directories that will be used by ckjm.
     * @return the extension directories as a path
     */
    public Path getExtdirs() {
        return extdirs;
    }

    /**
     * Adds a path to extdirs.
     * @return a path to be modified
     */
    public Path createExtdirs() {
        if (extdirs == null) {
            extdirs = new Path(getProject());
        }
        return extdirs.createPath();
    }

    /**
     * Executes the CKJM Ant Task. This method redirects the output of the CKJM
     * tool to a file. When XML format is used it will buffer the output and
     * translate it to the XML format.
     *
     * @throws BuildException
     *             if an error occurs.
     */
    public void execute() throws BuildException {
        if (classDir == null) {
            throw new BuildException("classdir attribute must be set!");
        }
        if (!classDir.exists()) {
            throw new BuildException("classdir does not exist!");
        }
        if (!classDir.isDirectory()) {
            throw new BuildException("classdir is not a directory!");
        }

	if (extdirs != null && extdirs.size() > 0) {
	    if (System.getProperty("java.ext.dirs").length() == 0)
		System.setProperty("java.ext.dirs", extdirs.toString());
	    else
		System.setProperty("java.ext.dirs",
		    System.getProperty("java.ext.dirs") + File.pathSeparator +
		    extdirs);
	}

        DirectoryScanner ds = super.getDirectoryScanner(classDir);

        String files[] = ds.getIncludedFiles();
        if (files.length == 0) {
            log("No class files in specified directory " + classDir);
        } else {
            for (int i = 0; i < files.length; i++) {
                files[i] = classDir.getPath() + File.separatorChar + files[i];
            }

            try {
                OutputStream outputStream = new FileOutputStream(outputFile);

                if (format.equals("xml")) {
                    PrintXmlResults outputXml = new PrintXmlResults(
                            new PrintStream(outputStream));

                    outputXml.printHeader();
                    MetricsFilter.runMetrics(files, outputXml);
                    outputXml.printFooter();
                } else {
                    PrintPlainResults outputPlain = new PrintPlainResults(
                            new PrintStream(outputStream));
                    MetricsFilter.runMetrics(files, outputPlain);
                }

                outputStream.close();

            } catch (IOException ioe) {
                throw new BuildException("Error file handling: "
                        + ioe.getMessage());
            }
        }
    }
}
