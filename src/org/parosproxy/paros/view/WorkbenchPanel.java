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

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


/**
 * WorkbenchPanel contains all areas of main window
 */
public class WorkbenchPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JSplitPane splitVert;
	private JSplitPane splitHoriz;
	private JPanel paneStatus;
	private JPanel paneSelect;
	private JPanel paneWork;
	
	/**
	 * Status pane (at screen bottom) 
	 */
	private TabbedPanel tabbedStatus;
	
	/**
	 * Work pane (right top)
	 */
	private TabbedPanel tabbedWork;
	
	/**
	 * Selett pane (left top)
	 */
	private TabbedPanel tabbedSelect;
	
	/**
	 * This is the default constructor
	 */
	public WorkbenchPanel() {
		super();
		initialize();
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private  void initialize() {
		GridBagConstraints consGridBagConstraints1 = new GridBagConstraints();

		setLayout(new GridBagLayout());
		setSize(800, 600);
		setPreferredSize(new Dimension(800, 600));
		consGridBagConstraints1.gridx = 0;
		consGridBagConstraints1.gridy = 0;
		consGridBagConstraints1.weightx = 1.0;
		consGridBagConstraints1.weighty = 1.0;
		consGridBagConstraints1.fill = GridBagConstraints.BOTH;
		add(getSplitVert(), consGridBagConstraints1);
	}

	/**
	 * This method initializes splitVert that contains top horizontal split pane and bottom status pane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */    
	private javax.swing.JSplitPane getSplitVert() {
		if ( splitVert == null ) {
			splitVert = new JSplitPane();
			splitVert.setDividerLocation(480);
			splitVert.setDividerSize(3);
			splitVert.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitVert.setResizeWeight(0.5D);
			splitVert.setPreferredSize(new Dimension(800, 400));
			splitVert.setTopComponent(getSplitHoriz());
			splitVert.setBottomComponent(getPaneStatus());
			splitVert.setContinuousLayout(false);
		}
		return splitVert;
	}

	/**

	 * This method initializes splitHoriz	

	 * 	

	 * @return javax.swing.JSplitPane	

	 */    
	private JSplitPane getSplitHoriz() {
		if (splitHoriz == null) {
			splitHoriz = new JSplitPane();
			splitHoriz.setLeftComponent(getPaneSelect());
			splitHoriz.setRightComponent(getPaneWork());
			splitHoriz.setDividerLocation(280);
			splitHoriz.setDividerSize(3);
			splitHoriz.setResizeWeight(0.3D);
			splitHoriz.setPreferredSize(new Dimension(800, 400));
			splitHoriz.setContinuousLayout(false);
			splitHoriz.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		}
		return splitHoriz;
	}

	/**
	 * This method initializes the bottom status pane (with 4 tabs)	
	 * 	
	 * @return 	
	 */    
	private JPanel getPaneStatus() {
		if (paneStatus == null) {
			paneStatus = new JPanel();
			paneStatus.setLayout(new CardLayout());
			paneStatus.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			paneStatus.add(getTabbedStatus(), getTabbedStatus().getName());
		}
		return paneStatus;
	}

	/**
	 * This method initializes paneSelect	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getPaneSelect() {
		if (paneSelect == null) {
			paneSelect = new JPanel();
			paneSelect.setLayout(new CardLayout());
			paneSelect.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			paneSelect.add(getTabbedSelect(), getTabbedSelect().getName());
		}
		return paneSelect;
	}

	/**
	 * This method initializes paneWork	
	 * 	
	 * @return	
	 */    
	private JPanel getPaneWork() {
		if (paneWork == null) {
			paneWork = new JPanel();
			paneWork.setLayout(new CardLayout());
			paneWork.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
			paneWork.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			paneWork.add(getTabbedWork(), getTabbedWork().getName());
		}
		return paneWork;
	}

	/**
	 * This method initializes the tabbed status pane (with 4 tabs - History, Spider, Alerts, Output)	
	 * 	
	 * @return com.proofsecure.paros.view.ParosTabbedPane	
	 */    
	public TabbedPanel getTabbedStatus() {
		if ( tabbedStatus == null ) {
			tabbedStatus = new TabbedPanel();
			tabbedStatus.setPreferredSize(new Dimension(800, 200));
			tabbedStatus.setTabPlacement(JTabbedPane.BOTTOM);
			tabbedStatus.setName("tabbedStatus");
			tabbedStatus.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		}
		return tabbedStatus;
	}

	/**

	 * This method initializes the tabbed work pane (with 3 tabs - Request, Response, Trap)	

	 * 	

	 * @return com.proofsecure.paros.view.ParosTabbedPane	

	 */    
	public TabbedPanel getTabbedWork() {
		if (tabbedWork == null) {
			tabbedWork = new TabbedPanel();
			tabbedWork.setPreferredSize(new Dimension(600, 400));
			tabbedWork.setName("tabbedWork");
			tabbedWork.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		}
		return tabbedWork;
	}

	/**

	 * This method initializes tabbedSelect	

	 * 	

	 * @return com.proofsecure.paros.view.ParosTabbedPane	

	 */    
	public org.parosproxy.paros.view.TabbedPanel getTabbedSelect() {
		if (tabbedSelect == null) {
			tabbedSelect = new TabbedPanel();
			tabbedSelect.setPreferredSize(new Dimension(200, 400));
			tabbedSelect.setName("tabbedSelect");
			tabbedSelect.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		}
		
		return tabbedSelect;
	}

        }
