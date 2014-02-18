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
package org.parosproxy.paros.view;


import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.parosproxy.paros.extension.AbstractPanel;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class OutputPanel extends AbstractPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JScrollPane jScrollPane;
	private JTextArea txtOutput;
    /**
     * 
     */
    public OutputPanel() {
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
        this.setName("Output");
        this.setSize(243, 119);
        this.add(getJScrollPane(), getJScrollPane().getName());
			
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTxtOutput());
			jScrollPane.setName("jScrollPane");
			jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			jScrollPane.setFont(new java.awt.Font("Dialog", Font.PLAIN, 11));
		}
		return jScrollPane;
	}
	/**
	 * This method initializes txtOutput	
	 * 	
	 * @return	
	 */    
	private JTextArea getTxtOutput() {
		if (txtOutput == null) {
			txtOutput = new JTextArea();
			txtOutput.setEditable(false);
			txtOutput.setLineWrap(true);
			txtOutput.setFont(new java.awt.Font("Dialog", Font.PLAIN, 12));
			txtOutput.setName("");
			txtOutput.addMouseListener(new MouseAdapter() { 
				public void mousePressed(MouseEvent e) {    
				    if ( (e.getModifiers() & InputEvent.BUTTON3_MASK) != 0 ) {  // right mouse button
				        View.getSingleton().getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
				    }	
				}
			});
		}
		return txtOutput;
	}
	
	public void append(final String msg) {
		if (EventQueue.isDispatchThread()) {
			getTxtOutput().append(msg);
			return;
		}
		try {
			EventQueue.invokeAndWait(new Runnable() {
				public void run() {
					getTxtOutput().append(msg);
				}
			});
		} catch (Exception e) {
		}
	}
	
	public void clear() {
	    getTxtOutput().setText("");
	}
	
	
  }
