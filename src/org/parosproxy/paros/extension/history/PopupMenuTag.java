/*
 *
 * Paros and its related class files.
 * 
 * Paros is an HTTP/HTTPS proxy for assessing web application security.
 * Copyright (C) 2003-2004 Chinotec Technologies Company
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the Clarified Artistic License
 * as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Clarified Artistic License for more details.
 * 
 * You should have received a copy of the Clarified Artistic License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.parosproxy.paros.extension.history;

import java.awt.Component;
import java.sql.SQLException;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.parosproxy.paros.extension.ExtensionPopupMenu;
import org.parosproxy.paros.model.HistoryReference;
import org.parosproxy.paros.network.HttpMalformedHeaderException;
import org.parosproxy.paros.network.HttpMessage;



/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PopupMenuTag extends ExtensionPopupMenu {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ExtensionHistory extension = null;
    
    /**
     * 
     */
    public PopupMenuTag() {
        super();
 		initialize();
    }

    /**
     * @param label
     */
    public PopupMenuTag(String label) {
        super(label);
    }

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.setText("Tag...");

        this.addActionListener(new java.awt.event.ActionListener() { 

        	public void actionPerformed(java.awt.event.ActionEvent e) {
        	    
        	    JList listLog = extension.getLogPanel().getListLog();
        	    HistoryReference ref = (HistoryReference) listLog.getSelectedValue();
        	    HttpMessage msg = null;
        	    try {
                    msg = ref.getHttpMessage();
                    JTextField[] inputs = new JTextField[1];
                    inputs[0] = new JTextField(msg.getTag());
                    String tag = JOptionPane.showInputDialog(extension.getView().getMainFrame(), "Tag this message as:", msg.getTag());
                    if (tag != null) {
                        ref.setTag(tag);
                        extension.getHistoryList().notifyItemChanged(ref);
                    }
                } catch (HttpMalformedHeaderException e1) {
                    e1.printStackTrace();
                } catch (SQLException e1) {
                    e1.printStackTrace();
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
            } catch (Exception e) {}
            return true;
        }
        return false;
    }
    
    void setExtension(ExtensionHistory extension) {
        this.extension = extension;
    }
    

	
}
