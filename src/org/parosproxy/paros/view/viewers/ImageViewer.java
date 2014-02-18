package org.parosproxy.paros.view.viewers;

import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;


public class ImageViewer extends AbstractViewer {

	private JScrollPane scrollPane;
	private JLabel lblIcon;
	private byte[] data;
	
	public ImageViewer(String name) {
		super(name);
	}

	
	@Override
	protected JComponent init() {
		scrollPane = new JScrollPane();
		scrollPane.setName(getName());

		lblIcon = new JLabel();
		lblIcon.setText("");
		lblIcon.setVerticalAlignment(SwingConstants.TOP);
		lblIcon.setBackground(SystemColor.text);
		
		scrollPane.setViewportView(lblIcon);

		return scrollPane;
	}

	@Override
	public boolean loadData(byte[] data) {
		this.data = data;
		lblIcon.setIcon(data == null ? null : new ImageIcon(data));
		return true;
	}

	@Override
	public boolean isAvailable(String contentType, boolean request) {
		if ( request || contentType == null ) {
			return false;
		}
		return contentType.toLowerCase().indexOf("image") >= 0; 
	}


	@Override
	public String getText() {
		return new String(data);
	}

}
