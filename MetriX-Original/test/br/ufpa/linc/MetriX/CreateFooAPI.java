package br.ufpa.linc.MetriX;

import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Attribute;
import br.ufpa.linc.MetriX.api.model.Class;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Method;
import br.ufpa.linc.MetriX.api.model.Package;
import br.ufpa.linc.MetriX.api.model.Primitive;

/**
 *@author david
 *Date: 27/03/2011
 */
public class CreateFooAPI {
	
	public API generateAPI(){
		API api = new API();
		api.setAPIFileName("api_test.jar");
		Package p = new Package("package1", api);
		Entity e = new Class("Classe1");
		Method m = new Method();
		m.setEntidade(e);
		m.setModifier("public");
		m.setName("method1");
		m.setReturn(new Attribute(new Primitive("boolean")));
		
		
		return api;
	}
	
	public void writeAPI(API api){
		System.out.println(api.getNome());
		for (Package p : api.getPackages()) {
			System.out.println("\t" + p);
			for (Entity e : p.getEntities()) {
				System.out.println("\t\t" + p);
				for (Attribute att : e.getAttributes()) 
					System.out.println("\t\t\t\t"+att);
				for (Method m : e.getMethods()) {
					System.out.println("\t\t\t"+m.getStringStructure() );
				}
			}
		}
	}

}
