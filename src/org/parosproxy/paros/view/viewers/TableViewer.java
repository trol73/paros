package org.parosproxy.paros.view.viewers;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.parosproxy.paros.view.HttpPanelTabularModel;

public class TableViewer extends AbstractViewer {
	private JScrollPane scrollPane;
	private JTable table;
	private HttpPanelTabularModel model;

	public TableViewer(String name) {
		super(name);
	}

	@Override
	protected JComponent init() {
		scrollPane = new JScrollPane();
		scrollPane.setName(getName());
		
		table = new JTable();
		table.setName("");
		model = new HttpPanelTabularModel();
		model.setEditable(isEditable());
		table.setModel(model);

		table.setGridColor(Color.gray);
		table.setIntercellSpacing(new Dimension(1, 1));
		table.setRowHeight(18);

		
		scrollPane.setViewportView(table);

		return scrollPane;
	}

	@Override
	public boolean loadData(byte[] data) {
		model.setText(data == null ? "" : new String(data));
		return true;
	}
	
	@Override
	public boolean loadData(String data) {
		model.setText(data);
		return true;
	}


	@Override
	public boolean isAvailable(String contentType, boolean request) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getText() {
		return model.getText();
	}

}
