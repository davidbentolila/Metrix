package br.ufpa.linc.MetriX.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.ufpa.linc.MetriX.api.model.Package;

@SuppressWarnings("serial")
public class MyJList extends JList implements ListSelectionListener {

	private HashSet<Integer> selectionCache = new HashSet<Integer>();
	private DefaultListModel defModel = null;
	private boolean init;
	
//    private List<JCheckBox> 
	public MyJList( List<?> objects ) {
		super();
		init = true;
        setCellRenderer ( new CheckBoxListCellRenderer() );
		addListSelectionListener( this );
		defModel = new DefaultListModel();
		setModel (defModel);
        for (Object o : objects) defModel.addElement( o );
        
        if ( objects.get(0) instanceof Package )
        	for ( int i = 0 ; i < defModel.size() ; i++) if ( ((Package) defModel.get(i)).isShow() ) setSelectedIndex(i);
        init = false;
	}
	
	class CheckBoxListCellRenderer extends JComponent implements ListCellRenderer {
		DefaultListCellRenderer defaultComp;
	    JCheckBox checkbox;
	    public CheckBoxListCellRenderer() {
	        setLayout (new BorderLayout());
	        defaultComp = new DefaultListCellRenderer();
	        checkbox = new JCheckBox();
	        add (checkbox, BorderLayout.WEST);
	        add (defaultComp, BorderLayout.CENTER);
	    }

	    public Component getListCellRendererComponent(JList list, Object  value, int index, boolean isSelected, boolean cellHasFocus){
	        defaultComp.getListCellRendererComponent (list, value, index, isSelected, cellHasFocus);
	        
	        if ( value instanceof Package && !init) ((Package) value).setShow( isSelected );
	        
	        checkbox.setSelected (isSelected);

	        Component[] comps = getComponents();
            for (int i=0; i<comps.length; i++) {
            	comps[i].setBackground (Color.WHITE);
            	comps[i].setForeground(Color.BLACK);
            }
                
	        return this;
	    }
    }

	@Override
	public void valueChanged(ListSelectionEvent lse) {
		if ( !lse.getValueIsAdjusting() ) {
	        removeListSelectionListener (this);

	        // remember everything selected as a result of this action
	        HashSet<Integer> newSelections = new HashSet<Integer>();
	        int size = getModel().getSize();
	        
	        for (int i=0; i<size; i++) if (getSelectionModel().isSelectedIndex(i)) newSelections.add (new Integer(i));

	        // turn on everything that was previously selected
	        Iterator<Integer> it = selectionCache.iterator();
	        while (it.hasNext()) {
	            int index = ((Integer) it.next()).intValue();
	            getSelectionModel().addSelectionInterval(index, index);
	        }

	        // add or remove the delta
	        it = newSelections.iterator();
	        while (it.hasNext()) {
	            Integer nextInt = (Integer) it.next();
	            int index = nextInt.intValue();
	            if (selectionCache.contains (nextInt))
	                getSelectionModel().removeSelectionInterval (index, index);
	            else
	                getSelectionModel().addSelectionInterval (index, index);
	        }

	        // save selections for next time
	        selectionCache.clear();
	        for (int i=0; i<size; i++) if (getSelectionModel().isSelectedIndex(i)) selectionCache.add (new Integer(i));

	        addListSelectionListener (this);
		}
	}
}
