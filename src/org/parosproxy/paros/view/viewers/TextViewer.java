package org.parosproxy.paros.view.viewers;

import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.parosproxy.paros.utils.Res;
import org.parosproxy.paros.view.View;

public class TextViewer extends AbstractViewer {
	
	private JScrollPane scrollPane;
	private JTextArea textArea;

	public TextViewer(String name) {
		super(name);
	}

	@Override
	protected JComponent init() {
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setName(getName());
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setFont(Res.FONT_DEFAULT);
		textArea.setName("");
		textArea.setTabSize(4);
		textArea.setVisible(true);
		textArea.setEditable(isEditable());
		textArea.addMouseListener(new MouseAdapter() { 
	    	public void mousePressed(MouseEvent e) {    
			    if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {  // right mouse button
			        View.getSingleton().getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
			    }			    	
			}
		});
		
		scrollPane.setViewportView(textArea);
		
		return scrollPane;
	}

	@Override
	public boolean loadData(byte[] data) {
		textArea.setText(data == null ? null : new String(data));
		textArea.setCaretPosition(0);
		return true;
	}
	
	@Override
	public boolean loadData(String data) {
		textArea.setText(data);
		textArea.setCaretPosition(0);
		return true;
	}

	@Override
	public boolean isAvailable(String contentType, boolean request) {
		return true;
	}
	
	
	public boolean isEmpty() {
		return textArea.getText().length() == 0;
	}

	@Override
	public String getText() {		
		return textArea.getText();
	}
	
	@Override
	public void setEditable(boolean editable) {
		super.setEditable(editable);
		textArea.setEditable(editable);
	}


}
