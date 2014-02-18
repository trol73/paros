package org.parosproxy.paros.view.viewers;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.fife.ui.hex.event.SelectionChangedEvent;
import org.fife.ui.hex.event.SelectionChangedListener;
import org.fife.ui.hex.swing.HexEditor;

public class HexViewer extends AbstractViewer {
	
	private JPanel panel;
	private JLabel txtStatus;
	private HexEditor hexEditor;

	public HexViewer(String name) {
		super(name);
	}

	@Override
	protected JComponent init() {
		panel = new JPanel();
		panel.setName(getName());
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		hexEditor = new HexEditor();
		hexEditor.setAlignmentX(0f);
		hexEditor.setAlternateRowBG(true);
		//hexEditor.setAlternateColumnBG(true);
/*		
		hexEditor.addHexEditorListener(new HexEditorListener() {
			@Override
			public void hexBytesChanged(HexEditorEvent e) {
System.out.println(e.getOffset());				
			}
		});
*/		
		
		hexEditor.addSelectionChangedListener(new SelectionChangedListener() {
			//@Override
			public void selectionChanged(SelectionChangedEvent event) {
				updateStatusLine();				
			}
		});
		panel.add(hexEditor);
		txtStatus = new JLabel("");
		txtStatus.setAlignmentX(0f);
		panel.add(txtStatus);
		updateStatusLine();

		return panel;
	}

	@Override
	public boolean loadData(byte[] data) {
		try {
			hexEditor.open(new ByteArrayInputStream(data));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean isAvailable(String contentType, boolean request) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getText() {
		// TODO
		// FIXME
		return null; //hexEditor.table.model.getBytes();
	}
	
	private void updateStatusLine() {
		txtStatus.setText("<html>"+"Offset: " + "<b>"+ hexEditor.getSmallestSelectionIndex() + "</b>  " + "Size: " + "<b>"+hexEditor.getByteCount());
	}

}
