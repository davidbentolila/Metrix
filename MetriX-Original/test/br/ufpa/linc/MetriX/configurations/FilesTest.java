package br.ufpa.linc.MetriX.configurations;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import br.ufpa.linc.MetriX.analysis.UseCase;


/**
 *@author david
 *Date: 27/03/2011
 */
public class FilesTest {

	/*
	 * <xml>
	 * <useCases api="API_Exemplo">
	 * 	<useCase name="lerXML">
	 * 		<class name="br.ufpa.pacote1.Classe1">
	 * 		<class name="br.ufpa.pacote1.Classe2">
	 * 		<method name="br.ufpa.pacote1.Classe2.method1">
	 * 		<method name="br.ufpa.pacote1.Classe2.method2">
	 * 		<package name="br.ufpa.pacote2">
	 * 		<package name="br.ufpa.pacote3">
	 *  </useCase>
	 * </useCases>
	 */
	@Test
	public void testgetUseCasesDOc() throws ParserConfigurationException{
		System.out.println("Test: testgetUseCasesDOc");
		List<UseCase> usecasesResult = Files.getUseCases(getXML());
		List<UseCase> usecasesExpected = new ArrayList<UseCase>();
		Set<String> methods = new HashSet<String>();
		Set<String> classes = new HashSet<String>();
		Set<String> packages = new HashSet<String>();
		methods.add("br.ufpa.pacote1.Classe1.method1");
		methods.add("br.ufpa.pacote1.Classe2.method2");
		classes.add("br.ufpa.pacote1.Classe1");
		classes.add("br.ufpa.pacote1.Classe2");
		packages.add("br.ufpa.pacote2");
		packages.add("br.ufpa.pacote3");
		usecasesExpected.add(new UseCase(usecasesExpected.size(),"ler XML",packages, classes, methods));
		
		Assert.assertEquals(usecasesExpected.iterator().next().toString(),usecasesResult.iterator().next().toString());
	}
	
	@Test
	public void testgetUseCasesFile() throws ParserConfigurationException{
		System.out.println("Test: testgetUseCasesFile");
		List<UseCase> usecasesResult = Files.getUseCases(new File("/home/david/workspace/MetriX/test/useCasesTest.xml"));
		List<UseCase> usecasesExpected = new ArrayList<UseCase>();
		Set<String> methods = new HashSet<String>();
		Set<String> classes = new HashSet<String>();
		Set<String> packages = new HashSet<String>();
		methods.add("br.ufpa.pacote1.Classe1.method1");
		methods.add("br.ufpa.pacote1.Classe2.method2");
		classes.add("br.ufpa.pacote1.Classe1");
		classes.add("br.ufpa.pacote1.Classe2");
		packages.add("br.ufpa.pacote2");
		packages.add("br.ufpa.pacote3");
		usecasesExpected.add(new UseCase(usecasesExpected.size(),"ler XML",packages, classes, methods));
		
		Assert.assertEquals(usecasesExpected.iterator().next().toString(),usecasesResult.iterator().next().toString());
	}
	
	@Test @Ignore("ok")
	public void testXMLcreator() throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException{
		Transformer transform = TransformerFactory.newInstance().newTransformer();
		transform.setOutputProperty(OutputKeys.INDENT, "yes");
		transform.transform(new DOMSource(getXML()), new StreamResult(System.out));
	}
	
	@Test @Ignore("ok")
	public void testeGetFileFolderType(){
		File f = Files.getFile();
		assert(f.isDirectory());
	}
	
	@Test @Ignore("ok")
	public void testeGetFileRestrictFileType(){
		File f = Files.getFile(new String[]{"xml"});
		assert(f.getName().endsWith("xml"));
		
		f = Files.getFile("xml");
		assert(f.getName().endsWith("xml"));
	}
	
	@Test @Ignore("ok")
	public void testeGetFileCancel(){
		Assert.assertNull(Files.getFile(new String[]{""}));
	}
	
	private Document getXML() throws ParserConfigurationException{
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = db.newDocument();
		Element root, e, e2;
		
		root = doc.createElement("useCases");
		root.setAttribute("api", "API_Exemplo");
		doc.appendChild(root);
		
		e = doc.createElement("useCase");
		e.setAttribute("name", "ler XML");
		root.appendChild(e);
		
		e2 = doc.createElement("method");
		e2.setAttribute("name", "br.ufpa.pacote1.Classe1.method1");
		e.appendChild(e2);
		
		e2 = doc.createElement("method");
		e2.setAttribute("name", "br.ufpa.pacote1.Classe2.method2");
		e.appendChild(e2);

		e2 = doc.createElement("class");
		e2.setAttribute("name", "br.ufpa.pacote1.Classe1");
		e.appendChild(e2);
		
		e2 = doc.createElement("class");
		e2.setAttribute("name", "br.ufpa.pacote1.Classe2");
		e.appendChild(e2);
		
		e2 = doc.createElement("package");
		e2.setAttribute("name", "br.ufpa.pacote2");
		e.appendChild(e2);
		
		e2 = doc.createElement("package");
		e2.setAttribute("name", "br.ufpa.pacote3");
		e.appendChild(e2);
		
		root.appendChild(e);
		
		return doc;
	}
}
