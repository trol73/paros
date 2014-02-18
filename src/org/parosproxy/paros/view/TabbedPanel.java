/*
 * Paros and its related class files.
 * 
 * Paros is an HTTP/HTTPS proxy for assessing web application security.
 * Copyright (C) 2003-2004 www.proofsecure.com
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

import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTabbedPane;

/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TabbedPanel extends JTabbedPane {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Container originalParent;
    private Container alternativeParent;
    private Component backupChild;
    
    /**
	 * This is the default constructor
	 */
	public TabbedPanel() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		setSize(225, 145);
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {    
			    if ( e.getClickCount() >= 2 ) {
			        alternateParent();
			    }

			}
		});

	}
	
	public void setAlternativeParent(java.awt.Container alternativeParent) {
	    this.alternativeParent = alternativeParent;
	}
	
	private boolean isAlternative = true;
	
	private void alternateParent() {
	    if ( alternativeParent == null ) {
	    	return;
	    }

	    if ( isAlternative ) {
	        originalParent = getParent();
	        originalParent.remove(this);
	        backupChild = alternativeParent.getComponent(0);
	        alternativeParent.remove(backupChild);
	        alternativeParent.add(this, "");
	    } else {
	        alternativeParent.remove(this);
	        alternativeParent.add(backupChild, "");
	        originalParent.add(this, "");
	    }
        originalParent.validate();
        alternativeParent.validate();
        validate();
	    isAlternative = !isAlternative;
	}
	

}
