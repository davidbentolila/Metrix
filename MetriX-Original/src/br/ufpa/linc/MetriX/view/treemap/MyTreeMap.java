package br.ufpa.linc.MetriX.view.treemap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Attribute;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Method;
import br.ufpa.linc.MetriX.api.model.Package;
import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.view.gui.MainWindow;
import br.ufpa.linc.MetriX.view.gui.RestrictAPIPackages;
import br.ufpa.linc.MetriX.view.gui.Status;
import br.ufpa.linc.MetriX.view.jtree.JTreeCheckBox;
import br.ufpa.linc.MetriX.view.jtree.MyJTree;
import br.ufpa.linc.MetriX.view.prefuse.data.Node;
import br.ufpa.linc.MetriX.view.prefuse.data.Table;
import br.ufpa.linc.MetriX.view.prefuse.data.Tree;

public class MyTreeMap {

	// private Files files = null;
	private List<Entity> entitys = null;
	private List<Package> packagesReaded = null;
	private List<DefaultMutableTreeNode> packages = null;
	private Status status = null;
	private TreeModel myJtreeModel = null;
	private DefaultMutableTreeNode classs, method;
	private int totalColors;
	// private double packageIL = 0, packageIS = 0, packageNA = 0, packageNM =
	// 0, packageAPP = 0;//packageOAC = 0,
	private Table table = null;
	public static boolean stop = false;
	/* ********************************************
	 * private MyGraph myGraph = null; private MyStarPlot starPlot = null;
	 * *********************************************
	 */
	private Class<?> stringType = String.class, doubleType = Double.class,
			objectType = Object.class, intType = int.class,
			longType = long.class;
	private StringBuffer stringCSV;
	private boolean useAverange = true;
//	private boolean showRelationship = false;

	// private String csvTitle;
	// teste test test
	// private List<Analise> _L_il = new ArrayList<Analise>(),_L_is = new
	// ArrayList<Analise>(), _L_oac = new ArrayList<Analise>(), _L_total = new
	// ArrayList<Analise>(), _L_na = new ArrayList<Analise>(), _L_nm = new
	// ArrayList<Analise>(), _L_app = new ArrayList<Analise>();
	// private int totalClasses = 0;
	public class Analise implements Comparable<Analise> {
		private double metrica;
		private int qnt = 0;

		public Analise(double metrica) {
			this.metrica = metrica;
			qnt = 1;
		}

		public Analise(double metrica, int qnt) {
			this.metrica = metrica;
			this.qnt = qnt;
		}

		public double getMetrica() {
			return metrica;
		}

		public void setMetrica(double metrica) {
			this.metrica = metrica;
		}

		public int getQnt() {
			return qnt;
		}

		public void setQnt(int qnt) {
			this.qnt = qnt;
		}

		public int compareTo(Analise a) {
			if (metrica < a.getMetrica())
				return -1;
			else if (metrica > a.getMetrica())
				return 1;
			else
				return 0;
		}

	}

	// private void adiciona(double metric, List<Analise> list){
	// for (Analise a : list) {
	// if ( a.getMetrica() == metric) {
	// a.setQnt(a.getQnt()+1);
	// return;
	// }
	// }
	// list.add(new Analise(metric));
	// }

	// private void total ( List<Analise> list){
	// int total = 0;
	// for (Analise a : list) {
	// System.out.println(a.getMetrica() + " " + a.getQnt() +";" );
	// total += a.getMetrica() * a.getQnt();
	// }
	// System.out.println("Total: " + total);
	// }

	public MyTreeMap() {
		stringCSV = new StringBuffer();
		entitys = new ArrayList<Entity>();
		packagesReaded = new ArrayList<Package>();
		packages = new ArrayList<DefaultMutableTreeNode>();
		totalColors = Configurations.getTreeMapColors().length - 1;
		/* ***************************
		 * myGraph = new MyGraph(); starPlot = new MyStarPlot();
		 * **********************
		 */
		MetriX.getInstance().setTree(new Tree());
		table = MetriX.getInstance().getTree().getNodeTable();
	}

	public void getTreeMap(Object o, boolean showRelationship) {
//		this.showRelationship = showRelationship;
		getTreeMap(o);
	}

	public void getTreeMap(Object o) {
		MetriX.getInstance().setShowObject(o);
		// api treemap
		if (o instanceof API) {
			API api = (API) o;

			JOptionPane.showMessageDialog(MainWindow.getInstance(),
					Configurations.getString("message.choosePackagesUse"),
					Configurations.getString("window.title.choosePackages"),
					JOptionPane.INFORMATION_MESSAGE);
			new RestrictAPIPackages(api);

			if (stop)
				return;

			myJtreeModel = new DefaultTreeModel(new DefaultMutableTreeNode(
					MetriX.getInstance().getShowObject()));
			Collections.sort(api.getPackages());

			// List<String> entitiesToRelate = new ArrayList<String>();

			for (Package p : api.getPackages()) {
				// if ( p.getName().equals("com.ibm.team.process.client") ||
				// p.getName().equals("com.ibm.team.repository.client") ||
				// p.getName().equals("com.ibm.team.repository.common") ||
				// p.getName().equals("com.ibm.team.process.common") ||
				// p.getName().equals("com.ibm.team.workitem.client"))
				// for (Entity e : p.getEntities())
				// entitiesToRelate.add(e.getFullName());
				//
				// if ( p.getName().equals("java.net"))
				// for (Entity e : p.getEntities())
				// entitiesToRelate.add(e.getFullName());
				if (p.isShow())
					for (Entity e : p.getEntities()) {
						// if ( !showRelationship &&
						// p.getName().equals("java.net") )
						entitys.add(e);
						// else {
						// Object classToFind[];

						// relationship Date
						// classToFind = new String[]{"java.util.Date"};
						// relationship dom4j
						// classToFind = new String[]{"org.dom4j.Document",
						// "org.dom4j.DocumentException",
						// "org.dom4j.DocumentHelper","org.dom4j.Element",
						// "org.dom4j.io.OutputFormat", "org.dom4j.io.SAXReader"
						// ,"org.dom4j.io.XMLWriter"};
						// relationship jdom
						// classToFind = new String[]{"org.jdom.Document",
						// "org.jdom.Element", "org.jdom.output.XMLOutputter",
						// "org.jdom.input.SAXBuilder"};
						// relationship jedit
						// classToFind = new
						// String[]{"org.gjt.sp.jedit.EBPlugin",
						// "org.gjt.sp.jedit.GUIUtilities",
						// "org.gjt.sp.jedit.View", "org.gjt.sp.jedit.jEdit",
						// "org.gjt.sp.jedit.io.VFSManager",
						// "org.gjt.sp.jedit.gui.OptionsDialog",
						// "org.gjt.sp.util.Log", "org.gjt.sp.jedit.View",
						// "org.gjt.sp.jedit.textarea.JEditTextArea",
						// "org.gjt.sp.util.Log"};
						// relationship jazz
						// classToFind = entitiesToRelate.toArray();
						// relationship Calendar
						// classToFind = new String[]{"java.util.Calendar"};
						// relationship Date and Calendar
						// classToFind = new String[]{"java.util.Date",
						// "java.util.Calendar"};
						// relationship joda
						// classToFind = new String[]{"org.joda.time.DateTime"};
						// classToFind = new
						// String[]{"java.net.HttpURLConnection"};
						// classToFind = new
						// String[]{"org.apache.http.client.HttpClient",
						// "org.apache.http.impl.client.DefaultHttpClient",
						// "org.apache.http.client.methods.HttpGet",
						// "org.apache.http.HttpResponse",
						// "org.apache.http.HttpEntity"};
						// java.net
						// addEntityRelationship(classToFind, e);
						// }

					}
			}
		}
		// package treemap
		// else if ( o instanceof Package ) entitys =
		// ((Package)o).getEntities();
		// entity treemap
		// else if ( o instanceof Entity ) {
		// entitys = new ArrayList<Entity>();
		// entitys.add((Entity) o);
		// }
		else
			return;

		if (entitys.size() == 0)
			return;

		Collections.sort(entitys);

		status = new Status(Configurations.getString("window.status.analysis"),
				entitys.size() + 1);
		MetriX.getInstance().setStatus(status);
		Thread t1 = new Thread(status);
		t1.start();
		Thread t2 = new Thread() {
			public void run() {

				// getting the "totalColor" class subdividing at intervals
				// levels = getIntervalColors( entitys );
				// if ( true ) {
				// status.dispose();
				// return ;
				// }

				status.setTitle(Configurations
						.getString("window.status.visualization.data"));

				long start = System.currentTimeMillis();
				System.out.println("creating treeMap");

				createTreeMap();

				System.out.print("treeMap created");
				long end = System.currentTimeMillis();

				System.out
						.println(" in " + ((end - start) / 1000) + " seconds");

				new MyJTree(myJtreeModel);
				new JTreeCheckBox(myJtreeModel);
				/*
				 *  ************ removida as demais
				 * visualizações************************** myGraph.createView();
				 * starPlot.generateVisualization();
				 * **************************************
				 */
				status.setTitle(Configurations
						.getString("window.status.visualization"));
				status.updateProgressBar(Configurations
						.getString("window.status.visualization"));
				if (MetriX.getInstance().getShowObject() instanceof API)
					MainWindow.getInstance().addInternalFrame(
							MetriX.getInstance().getApi().getNome());
				// package treemap
				else if (MetriX.getInstance().getShowObject() instanceof Package)
					MainWindow.getInstance().addInternalFrame(
							((Package) MetriX.getInstance().getShowObject())
									.getName());
				// entity treemap
				else if (MetriX.getInstance().getShowObject() instanceof Entity)
					MainWindow.getInstance().addInternalFrame(
							((Entity) MetriX.getInstance().getShowObject())
									.getName());
			}
		};
		t2.start();
	}

	private void createTreeMap() {

		table.addColumn(TreeMapView._COLUMN_NAME, stringType);
		table.addColumn(TreeMapView._COLUMN_OBJECT, objectType);
		table.addColumn(TreeMapView._COLUMN_SEARCH, stringType);
		table.addColumn(TreeMapView._COLUMN_TOOLTIP, stringType);
		// table.addColumn(column_oac, doubleType);
		table.addColumn(TreeMapView._COLUMN_IL, doubleType);
		table.addColumn(TreeMapView._COLUMN_IS, doubleType);
		table.addColumn(TreeMapView._COLUMN_TOTAL, doubleType);
		table.addColumn(TreeMapView._COLUMN_LEVEL, intType);
		table.addColumn(TreeMapView._COLUMN_SIZE, longType);

		Node root = getTreeRoot();

		Collections.sort(MetriX.getInstance().getApi().getPackages());
		for (Package p : MetriX.getInstance().getApi().getPackages())
			addPackageToTree(p);
		getTreeEntitysNodes(root);
	}

	private Node getTreeRoot() {
		Node root = MetriX.getInstance().getTree().addRoot();
		root.set(TreeMapView._COLUMN_NAME, MetriX.getInstance().getApi()
				.getNome());
		root.set(TreeMapView._COLUMN_SEARCH, MetriX.getInstance().getApi()
				.getNome());
		root.set(TreeMapView._COLUMN_TOOLTIP, MetriX.getInstance().getApi()
				.getNome());
		root.set(TreeMapView._COLUMN_OBJECT, MetriX.getInstance().getApi());

		return root;
	}

	private DefaultMutableTreeNode addPackageToTree(Package p) {
		DefaultMutableTreeNode packagee = null;

		if (packagesReaded.contains(p))
			return getPackageInJtree(p);

		packagee = new DefaultMutableTreeNode(p);
		// add to jtree (treemap)
		packages.add(packagee);

		packagesReaded.add(p);

		// if is a superpackage add all subpackage to it
		if (p.getSubPackages() != null) {
			Collections.sort(p.getSubPackages());
			for (Package pkg : p.getSubPackages())
				packagee.add(addPackageToTree(pkg));
		}

		// if don't have superpackage (it's absolut superpackage) add to root
		if (p.getSuperPackage() == null)
			((DefaultMutableTreeNode) myJtreeModel.getRoot()).add(packagee);
		else if (p.getSuperPackage() != null)
			addPackageToTree(p.getSuperPackage()).add(packagee);

		return packagee;
	}

	private Node getTreePackagesNodes(Package package_, Node root) {
		// if ( packagee != null && fill ) fillPackageMetrics();
		Node node_package = null;
		node_package = MetriX.getInstance().getTree().addChild(root);
		node_package.set(TreeMapView._COLUMN_NAME, package_.getName());
		node_package.set(TreeMapView._COLUMN_OBJECT, package_);
		node_package.set(TreeMapView._COLUMN_SEARCH, package_.getName());
		node_package.set(TreeMapView._COLUMN_TOOLTIP, package_.getName());
		// node_package.set(column_oac, 0d);
		node_package.set(TreeMapView._COLUMN_IL, 0d);
		node_package.set(TreeMapView._COLUMN_IS, 0d);

		// addPackageToTree(package_);
		/* ******************************************
		 * if ( MetriX.getInstance().getShowObject() instanceof API )
		 * starPlot.addObject(package_);
		 */

		return node_package;
	}

	private DefaultMutableTreeNode getPackageInJtree(Package p) {
		if (p == null)
			return null;
		for (DefaultMutableTreeNode dmt : packages)
			if (((Package) dmt.getUserObject()).equals(p))
				return dmt;
		return null;
	}

	private void getTreeEntitysNodes(Node root) {
		Package lastPackage = null;
		Node node_package = null, node_entity = null;
		DefaultMutableTreeNode pakkage;
		int nm, level;
		double sum = 0, sumWeight = 0;
		double is, cbo;// il total;//oac,
		double[] levels;
		if (MetriX.getInstance().isRelativeInternal())
			// IS
			levels = MetriX.getInstance().getDefineColor() == 0 ? Configurations
					.getDectrilISRelative(MetriX.getInstance().getApi())
					: Configurations.getDectrilCBORelative(MetriX.getInstance()
							.getApi());
		else
			levels = MetriX.getInstance().getDefineColor() == 0 ? Configurations
					.getDectril(12) : Configurations.getDectrilCBO();

		if (levels.length < 11) {
			JOptionPane.showMessageDialog(MainWindow.getInstance(),
					Configurations.getString("message.error.compareInternaly")
							+ " " + levels.length + ").",
					Configurations.getString("window.title.error"),
					JOptionPane.ERROR_MESSAGE);
			MetriX.getInstance().getStatus().dispose();
			return;
		}
		double[] weights = Configurations.getDectrilWeight();
		List<Node> packages = new ArrayList<Node>();
		List<Method> methods = new ArrayList<Method>();
		/* ***********************************
		 * MyVertex v1 = null, v2 = null; MyEdge me = null;
		 * ********************************
		 */
		// totalClasses = 0;

		// criando o cvs
		if (MetriX.getInstance().getDefineSize() == 0)
			stringCSV.append("Pacote.classe;Número de Métodos;IL;IS\n");
		else
			stringCSV
					.append("Pacote.classe;Número de Chamadas (freq de utilização)\n");
		// stringCSV.append("Pacote.classe;Número de Métodos;IL;IS;Número de Chamadas (freq de utilização)\n");

		for (Entity e : entitys) {
			// totalClasses++;
			methods = e.getMethods();
			Collections.sort(methods);
			status.updateProgressBar(e.getName());

			classs = new DefaultMutableTreeNode(e);

			/* *********************************************************
			 * if (MetriX.getInstance().getShowObject() instanceof Package)
			 * starPlot.addObject(e);
			 */

			if (!(MetriX.getInstance().getShowObject() instanceof Entity)) {
				if (!e.getPackage().equals(lastPackage) || node_package == null) {
					lastPackage = e.getPackage();
					node_package = existPackage(lastPackage, packages);
					if (node_package == null) {
						node_package = getTreePackagesNodes(lastPackage, root);
						packages.add(node_package);
					}
				}
				pakkage = getPackageInJtree(e.getPackage());
				if (pakkage == null)
					pakkage = addPackageToTree(e.getPackage());
				pakkage.add(classs);
			}
			/* *********************************************************
			 * if ( MetriX.getInstance().getShowObject() instanceof Package ||
			 * MetriX.getInstance().getShowObject() instanceof API){ v1 =
			 * myGraph.existVertex(e, null); if ( v1 == null ) { v1 = new
			 * MyVertex(e); myGraph.addVertex(v1); }
			 * 
			 * for (Entity entity : e.getDependencies()) { v2 =
			 * myGraph.existVertex(entity, null);
			 * 
			 * if ( v2 == null ) { v2 = new MyVertex(e); if (
			 * !v1.getEntity().getFullName
			 * ().equals(v2.getEntity().getFullName()) ) myGraph.addVertex(v2);
			 * }
			 * 
			 * if (
			 * !v1.getEntity().getFullName().equals(v2.getEntity().getFullName
			 * ()) ){ me = new MyEdge(v1,v2); myGraph.addEdge(me); } } }
			 * ********************************************
			 */

			if (myJtreeModel == null)
				myJtreeModel = new DefaultTreeModel(classs);

			// oac = e.getOAC( MetriX.getInstance().isUsePrivateMethods() ) *
			// MetriX.getInstance().getOacWeight();
			// packageOAC += oac;
			// metric = new DefaultMutableTreeNode("OAC: " + oac );
			// classs.add(metric);
			// adiciona(oac, _L_oac);

			// il = e.getIL( MetriX.getInstance().isUsePrivateMethods() ) ;//*
			// MetriX.getInstance().getIlWeight();
			// packageIL += il;
			// metric = new DefaultMutableTreeNode("IL: " + il );
			// classs.add(metric);
			// adiciona(il, _L_il);

			is = e.getMetricsValues().getIS() == 0 ? 0 : e.getMetricsValues()
					.getNM() == 0 ? e.getMetricsValues().getIS()
					: (useAverange) ? e.getMetricsValues().getIS()
							/ e.getMetricsValues().getNM() : e
							.getMetricsValues().getIS();// *
														// MetriX.getInstance().getIsWeight();
			// packageIS += is;
			// metric = new DefaultMutableTreeNode("IS: " + is );
			// classs.add(metric);
			// adiciona(is, _L_is);

			// np = e.getNP( MetriX.getInstance().isUsePrivateMethods() );
			// packageNA += na;
			// metric = new DefaultMutableTreeNode("NA: " + na );
			// classs.add(metric);
			// adiciona(na, _L_na);

			nm = (int) e.getMetricsValues().getNM();
			// packageNM += nm;
			// metric = new DefaultMutableTreeNode("NM: " + nm );
			// classs.add(metric);
			// adiciona(nm, _L_nm);

			// metric = new DefaultMutableTreeNode("APP: " + e.getPPM(
			// MetriX.getInstance().isUsePrivateMethods() ));
			// classs.add(metric);
			// adiciona(e.getAPP( MetriX.getInstance().isUsePrivateMethods() ),
			// _L_app);

			// total = il + is;// + oac;
			// adiciona(total, _L_total);

			cbo = e.getMetricsValues().getCBO();
			if (node_package == null)
				node_entity = MetriX.getInstance().getTree().addChild(root);
			else
				node_entity = MetriX.getInstance().getTree()
						.addChild(node_package);

			node_entity.set(TreeMapView._COLUMN_NAME, e.getName());
			node_entity.set(TreeMapView._COLUMN_OBJECT, e);
			node_entity.set(TreeMapView._COLUMN_SEARCH, e.getName());
			node_entity.set(TreeMapView._COLUMN_TOOLTIP, e.getMetricData());// ,
																			// MetriX.getInstance().getIlWeight(),
																			// MetriX.getInstance().getIsWeight())
																			// );//MetriX.getInstance().getOacWeight(),
			// node_entity.set(column_oac, oac);
			// node_entity.set(column_il, il);
			// node_entity.set(column_is, is);
			// node_entity.set(column_total, total);

			level = 0;
			double metricToLevel = MetriX.getInstance().getDefineColor() == 0 ? is
					: cbo;
			for (int i = 1; i < totalColors + 1; i++)
				if (metricToLevel >= levels[i - 1])
					level = i;
				else
					break;

			sumWeight += is * weights[level - 1];
			sum += is;
			// if ( nm == 0 ) level = 0;
			node_entity.setInt(TreeMapView._COLUMN_LEVEL, level);

			// long size = MetriX.getInstance().getDefineSize() == 0 ? nm :
			// (long) e.getMetricsValues().getCalls();
			long size;
			switch (MetriX.getInstance().getDefineSize()) {
			case 0:
				size = nm;
				break;
			case 1:
				size = (long) e.getMetricsValues().getCalls();
				break;
			case 2:
				size = (long) e.getMetricsValues().getClassLinks();
				break;
			case 3:
				size = (long) e.getMetricsValues().getBugCount();
				break;
			case 4:
				size = (long) e.getMetricsValues().getCBO();
				break;
			case 5:
				size = (long) e.getMetricsValues().getDIT();
				break;
			default:
				size = 0;
				break;
			}
			node_entity.setLong(TreeMapView._COLUMN_SIZE, size + 3);

			for (int i = 0; i < size + 3; i++)
				getTreeMethodNode(node_entity, level, e, is);// , is);

			// System.out.println(node_entity.getDepth());
			// for ( int i = 0 ; i < numberOfMethods ; i++)
			for (Method m : methods)
				if (m.getModifier().equals("public")) {
					method = new DefaultMutableTreeNode(m);
					classs.add(method);

					/* *************************************************************
					 * if (MetriX.getInstance().getShowObject() instanceof
					 * Entity) {
					 * 
					 * starPlot.addObject(m);
					 * 
					 * v1 = myGraph.existVertex(e, m);
					 * 
					 * if ( v1 == null ) { v1 = new MyVertex(e, m);
					 * myGraph.addVertex(v1);
					 * 
					 * }
					 * 
					 * for (Entity entity : m.getDependencies()) {
					 * 
					 * v2 = myGraph.existVertex(entity, null);
					 * 
					 * if ( v2 == null ) { v2 = new MyVertex(entity);
					 * myGraph.addVertex(v2); }
					 * 
					 * me = new MyEdge(v1,v2); myGraph.addEdge(me); } }
					 * **********
					 * ****************************************************
					 */
				}
			if (MetriX.getInstance().getDefineSize() == 0)
				stringCSV.append(e.getFullName() + ";"
						+ e.getMetricsValues().getNM() + ";"
						+ (int) e.getMetricsValues().getIS() + "\n");// + ";" +
																		// (int)e.getILPublic()
			else
				stringCSV.append(e.getFullName() + ";"
						+ e.getMetricsValues().getCalls() + "\n");
			// descomenta esta linha se quiser colocar todas as informações no
			// csv
			// stringCSV.append(e.getFullName() + ";" + e.getNMPublic() + ";" +
			// e.getILPublic() + ";" + e.getISPublic() + ";" + e.getCalls() +
			// "\n");
		}

		System.out.println("\n\n" + MetriX.getInstance().getApi().getNome()
				+ "\nsum: " + sum + "\nsumWeight: " + sumWeight);
		System.out.println("media: " + sum
				/ MetriX.getInstance().getApi().getAllEntities().size()
				+ "\nmedia com Peso: " + sumWeight
				/ MetriX.getInstance().getApi().getAllEntities().size());
		// csvTitle = MetriX.getInstance().getApi().getAPIFileName();
		// if ( MetriX.getInstance().getDefineSize() == 0) {
		// JOptionPane.showMessageDialog(MainWindow.getInstance(),
		// "Escolha onde salvar o csv de NM");
		// csvTitle += ".NM.csv";
		// }
		// else {
		// JOptionPane.showMessageDialog(MainWindow.getInstance(),
		// "Escolha onde salvar o csv de chamadas");
		// csvTitle += ".chamadas.csv";
		// }
		// files.saveCSV(stringCSV, main, csvTitle);

		// il = MetriX.getInstance().getApi().getIL(main);
		// is = MetriX.getInstance().getApi().getIS(main);
		// oac = MetriX.getInstance().getApi().getOAC(main);
		// na = MetriX.getInstance().getApi().getNM(main);
		// nm = MetriX.getInstance().getApi().getNA(main);
		// double app = MetriX.getInstance().getApi().getAPP(main);
		// total = il + is + oac;
		// System.out.println(MetriX.getInstance().getApi().toString());
		// System.out.println("IL " + il);
		// System.out.println("IS " + is);
		// System.out.println("OAC " + oac);
		// System.out.println("NA " + na);
		// System.out.println("NM " + nm);
		// System.out.println("APP " + app);
		// System.out.println("TOTAL " + total);
		// System.out.println("\n\n--------------------------------------\n\n");
		//
		// System.out.println("IL");
		// Collections.sort(_L_il);
		// total(_L_il);
		//
		// System.out.println("\n\n--------------------------------------\n\n");
		//
		// System.out.println("IS");
		// Collections.sort(_L_is);
		// total(_L_is);
		//
		// System.out.println("\n\n--------------------------------------\n\n");
		//
		// System.out.println("OAC");
		// Collections.sort(_L_oac);
		// total(_L_oac);
		//
		// System.out.println("\n\n--------------------------------------\n\n");
		//
		// System.out.println("TOTAL");
		// Collections.sort(_L_total);
		// total(_L_total);
		//
		// System.out.println("\n\n--------------------------------------\n\n");
		//
		// System.out.println("NA");
		// Collections.sort(_L_na);
		// total(_L_na);
		//
		// System.out.println("\n\n--------------------------------------\n\n");
		//
		// System.out.println("NM");
		// Collections.sort(_L_nm);
		// total(_L_nm);
		//
		// System.out.println("\n\n--------------------------------------\n\n");
		//
		// System.out.println("APP");
		// Collections.sort(_L_app);
		// total(_L_app);
		//
		// System.out.println("\n\n--------------------------------------\n\n");
		//
		// System.out.println("total de classes " + totalClasses);

	}

	private void getTreeMethodNode(Node node_entity, int level, Entity e,
			double is) {// , double is){//double oac,
		Node node_method = null;

		node_method = MetriX.getInstance().getTree().addChild(node_entity);

		node_method.set(TreeMapView._COLUMN_NAME, e.getName());
		node_method.set(TreeMapView._COLUMN_OBJECT, e);
		// node_method.set(column_Search, e.getNome() + m);
		node_method.set(TreeMapView._COLUMN_TOOLTIP, e.getMetricData());// ,
																		// MetriX.getInstance().getIlWeight(),
																		// MetriX.getInstance().getIsWeight())
																		// +"</html>");//MetriX.getInstance().getOacWeight(),
		// node_method.set(column_oac, oac);
		// node_method.set(TreeMapView._COLUMN_IL, il);
		node_method.set(TreeMapView._COLUMN_IS, is);
		// node_method.set(TreeMapView._COLUMN_TOTAL, (il + is) );// + oac
		node_method.set(TreeMapView._COLUMN_LEVEL, level);

		// return node_method;
	}

	private Node existPackage(Package p, List<Node> list) {
		for (Node n : list)
			if (n.get("object").equals(p))
				return n;
		return null;
	}

	// private void fillPackageMetrics( ){
	// //fill last package metrics
	// if ( packagee != null ) {
	// metric = new DefaultMutableTreeNode("IL: "+ packageIL);
	// packagee.insert( metric, 0 );
	//
	// metric = new DefaultMutableTreeNode("IS: "+ packageIS);
	// packagee.insert( metric, 1 );
	//
	// // metric = new DefaultMutableTreeNode("OAC: "+ packageOAC);
	// // packagee.insert( metric, 2 );
	//
	// metric = new DefaultMutableTreeNode("NA: "+ (int)packageNA);
	// packagee.insert( metric, 2 );
	//
	// metric = new DefaultMutableTreeNode("NM: "+ (int)packageNM);
	// packagee.insert( metric, 3 );
	//
	// if ( packageNM == 0 ) packageAPP = packageNM == 0 ? 0 : packageNA /
	// packageNM;
	//
	// metric = new DefaultMutableTreeNode("APP: "+ packageAPP );
	// packagee.insert( metric, 4 );
	// packageIL = packageIS = packageNA = packageNM = 0;//packageOAC =
	// }
	// }

	@SuppressWarnings("unused")
	private double getCorrectIS(Entity e) {
		double is = e.getMetricsValues().getIS();

		// if ( e instanceof Class)
		for (Entity iface : e.getInterfaces()) {
			// if ( e.isAbstrakt() ){
			for (Method m : e.getMethods())
				for (Method m2 : iface.getMethods())
					if (compareMethods(m, m2)) {
						is -= m.getMetricsValues().getIS();
						break;
					}
		}
		// else is -= iface.getMetricsValues().getIS();
		// }
		// }
		if (is < 0)
			System.out.println("id: " + e.getId() + " " + e + " menor "
					+ e.getMetricsValues().getIS() + "  " + is);
		// else System.out.println(e + " normal: " +
		// e.getMetricsValues().getIS() + "  " + is);

		return is;
	}

	private boolean compareMethods(Method m1, Method m2) {
		if (m1.getName().equals(m2.getName())
				&& m1.getReturnType().equals(m2.getReturnType())) {
			for (Attribute a : m1.getParameters())
				for (Attribute a2 : m2.getParameters())
					if (!a.getType().equals(a2.getType()))
						return false;
		} else
			return false;
		return true;
	}

	@SuppressWarnings("unused")
	private void addEntityRelationship(Object[] classToFind, Entity e) {
		analisaEntidade(classToFind, e);

		analisaSuperClass(classToFind, e);
		analisaAtributos(classToFind, e);
		analisaMetodos(classToFind, e);
	}

	private void analisaEntidade(Object[] classToFind, Entity e) {
		for (Object s : classToFind)
			if (e.getFullName().equals(s) && !entitys.contains(e))
				entitys.add(e);
	}

	private void analisaSuperClass(Object[] classToFind, Entity e) {
		for (Object s : classToFind)
			if (e.getSuperClass() != null
					&& e.getSuperClass().getFullName().equals(s)
					&& !entitys.contains(e))
				entitys.add(e);
	}

	private void analisaAtributos(Object[] classToFind, Entity e) {
		for (Attribute a : e.getAttributes())
			if (a.getModifier().equals("public"))
				for (Object s : classToFind)
					if (a.getEntityType() != null
							&& a.getEntityType().getFullName().equals(s)
							&& !entitys.contains(e))
						entitys.add(e);
	}

	private void analisaMetodos(Object[] classToFind, Entity e) {

		for (Method m : e.getMethods())
			if (m.getModifier().equals("public"))
				for (Entity clazz : m.getDependencies())
					for (Object s : classToFind)
						if (clazz.getFullName().equals(s)
								&& !entitys.contains(e))
							entitys.add(e);
	}

}