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
package org.parosproxy.paros.extension.scanner;

import javax.swing.tree.DefaultTreeModel;

import org.parosproxy.paros.core.scanner.Alert;


/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
class AlertTreeModel extends DefaultTreeModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	AlertTreeModel() {
        super(new AlertNode("Alerts"));
        
    }
        
    /**
     * 
     * @param msg
     * @return true if the node is added.  False if not.
     */
    synchronized void addPath(Alert alert) {
        
        AlertNode parent = (AlertNode) getRoot();
        
        try {
            parent = findAndAddChild(parent, Alert.MSG_RISK[alert.getRisk()], null);
            parent = findAndAddChild(parent, alert.getAlert(), null);
            parent = findAndAddLeaf(parent, alert.getUri().toString(), alert);
        } catch (Exception e) {
            
        }
        
    }
    
 
    
    private AlertNode findAndAddChild(AlertNode parent, String nodeName, Alert alert) {
        AlertNode result = findChild(parent, nodeName);
        if (result == null) {
            AlertNode newNode = new AlertNode(nodeName);
            int pos = parent.getChildCount();
            for (int i=0; i< parent.getChildCount(); i++) {
                if (nodeName.compareToIgnoreCase(parent.getChildAt(i).toString()) <= 0) {
                    pos = i;
                    break;
                }
            }
            insertNodeInto(newNode, parent, pos);
            result = newNode;
            result.setUserObject(alert);
        }
        return result;
    }

    private AlertNode findAndAddLeaf(AlertNode parent, String nodeName, Alert alert) {
        AlertNode result = findLeaf(parent, nodeName, alert);
        
        if (result == null) {
            AlertNode newNode = new AlertNode(nodeName);
            int pos = parent.getChildCount();
            for (int i=0; i< parent.getChildCount(); i++) {
                if (nodeName.compareToIgnoreCase(parent.getChildAt(i).toString()) <= 0) {
                    pos = i;
                    break;
                    
                }
            }
            insertNodeInto(newNode, parent, pos);
            result = newNode;
            result.setUserObject(alert);
        }
        return result;
    }

    
    private AlertNode findChild(AlertNode parent, String nodeName) {
        for (int i=0; i<parent.getChildCount(); i++) {
            AlertNode child = (AlertNode) parent.getChildAt(i);
            if (child.toString().equals(nodeName)) {
                return child;
            }
        }
        return null;
    }

    private AlertNode findLeaf(AlertNode parent, String nodeName, Alert alert) {
        for (int i=0; i<parent.getChildCount(); i++) {
            AlertNode child = (AlertNode) parent.getChildAt(i);
            if (child.toString().equals(nodeName)) {
                if (child.getUserObject() == null) {
                    return null;
                }
                
                Alert tmp = (Alert) child.getUserObject();

                if (tmp.getParam().equals(alert.getParam())) {;
                	return child;
                }
            }
        }
        return null;
    }

    
}
