package org.parosproxy.paros.extension.history;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

import org.apache.commons.httpclient.URI;
import org.parosproxy.paros.extension.ExtensionPopupMenu;
import org.parosproxy.paros.model.HistoryReference;


public class PopupMenuCopyUrl extends ExtensionPopupMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ExtensionHistory extension;
	
    /**
     * 
     */
    public PopupMenuCopyUrl() {
        super();
 		initialize();
    }

    /**
     * @param label
     */
    public PopupMenuCopyUrl(String label) {
        super(label);
    }
    
    void setExtension(ExtensionHistory extension) {
        this.extension = extension;
    }
    

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.setText("Copy link to clipboard...");

        this.addActionListener(new ActionListener() { 

        	public void actionPerformed(ActionEvent e) {
                JList listLog = extension.getLogPanel().getListLog();
        	    Object[] obj = listLog.getSelectedValues();
        	    if (obj.length == 0) {
                    extension.getView().showWarningDialog("Select HTTP message in History panel before export to file.");        	        
                    return;
        	    }

                if (obj.length > 1) {
                    extension.getView().showWarningDialog("Only one url can be copied at a time.");                   
                    return;
                }

                HistoryReference ref = (HistoryReference) obj[0];
                try {
                	URI uri = ref.getHttpMessage().getRequestHeader().getURI();
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				    clipboard.setContents(new StringSelection(uri.toString()), null);
				} catch (Exception ex) {
				}
        	}
        });

			
	}

    public boolean isEnableForComponent(Component invoker) {
        if (invoker.getName() != null && invoker.getName().equals("ListLog")) {
            try {
                JList list = (JList) invoker;
                if (list.getSelectedIndex() >= 0) {
                    this.setEnabled(true);
                } else {
                    this.setEnabled(false);
                }
            } catch (Exception e) {
            	
            }
            return true;
            
            
        }
        return false;
    }
}
