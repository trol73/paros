package org.parosproxy.paros.view.viewers;

import javax.swing.JComponent;

/**
 * Base class for viewer's on request nadr response content
 * 
 * @author trol
 *
 */
abstract public class AbstractViewer {
	private String name;
	private boolean editable;
	
	public AbstractViewer(String name) {
		this.name = name;
	}
	
	public JComponent create() {
		JComponent result = init();
		result.setName(name);
		return result;
	}
	
	public String getName() {
		return name;
	}
	
	abstract protected JComponent init();
	
	abstract public boolean loadData(byte []data);
	
	abstract public boolean isAvailable(String contentType, boolean request);
	
	abstract public String getText();
	
	
	public void clearData() {
		byte[] data = null;
		loadData(data);
	}
	
	public boolean loadData(String data) {
		return loadData(data.getBytes());
	}
	
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
	public boolean isEditable() {
		return editable;
	}

}
