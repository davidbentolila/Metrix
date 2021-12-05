package br.ufpa.linc.MetriX.view.jtreeCheckBox;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Package;

class CheckRenderer extends JPanel implements TreeCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DefaultTreeCellRenderer nonLeafRenderer = new DefaultTreeCellRenderer();

	protected JCheckBox check;

	protected TreeLabel label;
	private Icon apiIcon = null, packageIcon = null, packageOpenIcon = null;

	public CheckRenderer() {
		setLayout(null);
		add(check = new JCheckBox());
		add(label = new TreeLabel());
		check.setBackground(UIManager.getColor("Tree.textBackground"));
		label.setForeground(UIManager.getColor("Tree.textForeground"));
		apiIcon = new ImageIcon(getClass().getResource("/images/api.png"));
		packageIcon = new ImageIcon(getClass().getResource(
				"/images/package.png"));
		packageOpenIcon = new ImageIcon(getClass().getResource(
				"/images/package_open.png"));
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean isSelected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		Component returnValue;

		if (!(value instanceof CheckNode)){
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			if (node.getUserObject() instanceof API) {
				nonLeafRenderer.getTreeCellRendererComponent(tree,value, isSelected, expanded, leaf, row, hasFocus);
				nonLeafRenderer.setIcon(apiIcon);
			}
			returnValue = nonLeafRenderer;
		}
		else {

			CheckNode node = (CheckNode) value;

			String stringValue = tree.convertValueToText(value, isSelected,
					expanded, leaf, row, hasFocus);

			setEnabled(tree.isEnabled());
			check.setSelected(((CheckNode) value).isSelected());
			label.setFont(tree.getFont());
			label.setText(stringValue);
			label.setSelected(isSelected);
			label.setFocus(hasFocus);
			if (node.getUserObject() instanceof Package) {
				if (leaf) {
					label.setIcon(packageIcon);
				} else if (expanded) {
					label.setIcon(packageOpenIcon);
				} else {
					label.setIcon(packageIcon);
				}
			}
			returnValue = this;
		}

		return returnValue;
	}

	public Dimension getPreferredSize() {
		Dimension d_check = check.getPreferredSize();
		Dimension d_label = label.getPreferredSize();
		return new Dimension(d_check.width + d_label.width,
				(d_check.height < d_label.height ? d_label.height
						: d_check.height));
	}

	public void doLayout() {
		Dimension d_check = check.getPreferredSize();
		Dimension d_label = label.getPreferredSize();
		int y_check = 0;
		int y_label = 0;
		if (d_check.height < d_label.height) {
			y_check = (d_label.height - d_check.height) / 2;
		} else {
			y_label = (d_check.height - d_label.height) / 2;
		}
		check.setLocation(0, y_check);
		check.setBounds(0, y_check, d_check.width, d_check.height);
		label.setLocation(d_check.width, y_label);
		label.setBounds(d_check.width, y_label, d_label.width, d_label.height);
	}

	public void setBackground(Color color) {
		if (color instanceof ColorUIResource)
			color = null;
		super.setBackground(color);
	}
}