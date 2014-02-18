/*
 * Created on May 18, 2004
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

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.parosproxy.paros.utils.I18N;
import org.parosproxy.paros.utils.Res;


/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MainFrame extends AbstractFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel paneContent;
	private JLabel txtStatus;
	private WorkbenchPanel paneStandard;
	private MainMenuBar mainMenuBar;
	private JPanel paneDisplay;

	
	/**
	 * This method initializes 
	 * 
	 */
	public MainFrame() {
		super("mainframe");
		
		mainMenuBar = new MainMenuBar();
        setJMenuBar(mainMenuBar);
        
		paneContent = new JPanel();
		paneContent.setLayout(new BoxLayout(paneContent, BoxLayout.Y_AXIS));
		paneContent.setEnabled(true);
		paneContent.setPreferredSize(new Dimension(800, 600));
		paneContent.setFont(Res.FONT_DIALOG);
		paneContent.add(getPaneDisplay(), null);
		
		txtStatus = new JLabel();
		txtStatus.setName("txtStatus");
		txtStatus.setText(I18N.get("mainframe.initializing"));
		txtStatus.setHorizontalAlignment(SwingConstants.LEFT);
		txtStatus.setHorizontalTextPosition(SwingConstants.LEFT);
		txtStatus.setPreferredSize(new Dimension(800, 18));

		paneContent.add(txtStatus, null);

        setContentPane(paneContent);

        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e) {
        		mainMenuBar.getMenuFileControl().exit();
        	}
        });
        setVisible(false);
	}
	
	public MainMenuBar getMainMenuBar() {
		return mainMenuBar;
	}


	/**
	 * This method initializes the Workbench pane	
	 * 	
	 * @return	
	 */    
	WorkbenchPanel getWorkbench() {
		if (paneStandard == null) {
			paneStandard = new WorkbenchPanel();
			paneStandard.setLayout(new java.awt.CardLayout());
			paneStandard.setName("paneStandard");		// don't delete this
		}
		return paneStandard;
	}


	
	public void setStatus(final String msg) {
		if (EventQueue.isDispatchThread()) {
			txtStatus.setText(msg);
			return;
		}
		try {
			EventQueue.invokeAndWait(new Runnable() {
				public void run() {
					txtStatus.setText(msg);
				}
			});
		} catch (Exception e) {
		}
	}

	/**
	 * This method initializes 	
	 * 	
	 * @return JPanel	
	 */    
	public JPanel getPaneDisplay() {
		if (paneDisplay == null) {
			paneDisplay = new JPanel();
			paneDisplay.setLayout(new CardLayout());
			paneDisplay.setName("paneDisplay");			// don't delete this
			paneDisplay.add(getWorkbench(), getWorkbench().getName());
		}
		return paneDisplay;
	}
}
