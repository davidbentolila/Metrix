package br.ufpa.linc.MetriX.report;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import br.ufpa.linc.MetriX.configurations.Configurations;

public class MyJtable extends DefaultTableCellRenderer {
	
	private static final long serialVersionUID = 8407338514964995195L;

	int row1 = -1;
	int row2 = -1;

	public MyJtable () {
		setHorizontalAlignment(SwingConstants.RIGHT);   
	}
	
	public Component getTableCellRendererComponent( JTable aTable, Object object, boolean aIsSelected, boolean aHasFocus, int aRow, int aColumn ) {
		if (object == null) return this;
		if ( aRow == aTable.getRowCount() && aColumn == aTable.getColumnCount() ){
			row1 = -1;
			row2 = -1;
		}
		
		Component renderer = super.getTableCellRendererComponent( aTable, object, aIsSelected, aHasFocus, aRow, aColumn );
		    	
		if (  object.toString().equals(Configurations.getString("table.title.total_full") + ":") || row1 == aRow) {
			renderer.setForeground(new Color(0,0,0));
			renderer.setBackground(new Color(102,204,153));
			row1 = aRow;
		}
		else if ( object.toString().equals(Configurations.getString("table.title.total_partial") + ":") || row2 == aRow) {
			renderer.setForeground(new Color(0,0,0));
			renderer.setBackground(new Color(102,204,204));
			row2 = aRow;
		}
		else {
			renderer.setForeground(Color.BLACK);
			renderer.setBackground(Color.white);
		}
		return this;
	}
}