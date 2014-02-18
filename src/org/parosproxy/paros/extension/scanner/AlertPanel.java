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

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.InputEvent;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.parosproxy.paros.core.scanner.Alert;
import org.parosproxy.paros.extension.AbstractPanel;
import org.parosproxy.paros.extension.ViewDelegate;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.view.HttpPanel;

/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class AlertPanel extends AbstractPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ViewDelegate view;
	private JTree treeAlert;
	private TreePath rootTreePath;	

	
	private JScrollPane paneScroll;
	
    /**
     * 
     */
    public AlertPanel() {
        super();
 		initialize();
    }

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.setLayout(new CardLayout());
        this.setSize(474, 251);
        this.setName("Alerts");
        this.add(getPaneScroll(), getPaneScroll().getName());
			
	}
	/**
	 * This method initializes treeAlert	
	 * 	
	 * @return javax.swing.JTree	
	 */    
	JTree getTreeAlert() {
		if (treeAlert == null) {
			treeAlert = new JTree();
			treeAlert.setName("treeAlert");
			treeAlert.setShowsRootHandles(true);
			treeAlert.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			treeAlert.addMouseListener(new java.awt.event.MouseAdapter() { 
				public void mousePressed(java.awt.event.MouseEvent e) {    
				    if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {  // right mouse button
				        view.getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
				    }	
				}
			});
			treeAlert.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() { 
				public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
				    DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeAlert.getLastSelectedPathComponent();
				    if (node.getUserObject() != null) {
				        Object obj = node.getUserObject();
				        if (obj instanceof Alert) {
				            Alert alert = (Alert) obj;
						    setMessage(alert.getMessage());

				        }
				    }
				}
			});
		}
		return treeAlert;
	}
	
	
	
	
	/**
	 * This method initializes paneScroll	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getPaneScroll() {
		if (paneScroll == null) {
			paneScroll = new JScrollPane();
			paneScroll.setName("paneScroll");
			paneScroll.setViewportView(getTreeAlert());
		}
		return paneScroll;
	}
	
	void setView(ViewDelegate view) {
	    this.view = view;
	}
	
    /**
     * @return Returns the view.
     */
    private ViewDelegate getView() {
        return view;
    }

    
	public void expandRoot() {
        TreeNode root = (TreeNode) getTreeAlert().getModel().getRoot();
        if (rootTreePath == null || root != rootTreePath.getPathComponent(0)) {
            rootTreePath = new TreePath(root);
        }
	    
		if (EventQueue.isDispatchThread()) {
		    getTreeAlert().expandPath(rootTreePath);
		    return;
		}
		try {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
				    getTreeAlert().expandPath(rootTreePath);
				}
			});
		} catch (Exception e) {
		}
	}
	
	private void setMessage(HttpMessage msg) {
	    HttpPanel requestPanel = getView().getRequestPanel();
	    HttpPanel responsePanel = getView().getResponsePanel();
	    requestPanel.setMessage("","", true);
	    if (!msg.getRequestHeader().isEmpty()) {
	        requestPanel.setMessage(msg.getRequestHeader().toString(), msg.getRequestBody().toString(), true);
	    }

	    responsePanel.setMessage("","", false);
	    if (!msg.getResponseHeader().isEmpty()) {
	        responsePanel.setMessage(msg.getResponseHeader().toString(), msg.getResponseBody().toString(), false);
	    }

	}
    }  //  @jve:decl-index=0:visual-constraint="10,10"
