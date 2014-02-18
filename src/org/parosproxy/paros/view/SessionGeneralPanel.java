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

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import org.parosproxy.paros.model.Session;
import org.parosproxy.paros.utils.Res;
/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SessionGeneralPanel extends AbstractParamPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel panelSession;  //  @jve:decl-index=0:visual-constraint="10,320"
	private JTextField txtSessionName;
	private JTextArea txtDescription;
//	private Session session;
	
    public SessionGeneralPanel() {
        super();
 		initialize();
   }
    /**
     * @param parent
     * @param modal
     * @param title
     * @throws HeadlessException
     */


//    private static final String[] ROOT = {};
//    private static final String[] GENERAL = {"General"};
//    private static final String[] MISCELLENOUS = {"Miscellenous"};
    
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        setLayout(new CardLayout());
        setName("General");
        add(getPanelSession(), getPanelSession().getName());
	}
	/**
	 * This method initializes panelSession	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getPanelSession() {
		if (panelSession == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();

			panelSession = new JPanel();
			JLabel jLabel = new JLabel();

			JLabel jLabel1 = new JLabel();

			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();

			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();

			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();

			panelSession.setLayout(new GridBagLayout());
			jLabel.setText("Session Name");
			jLabel1.setText("Description");
			gridBagConstraints9.gridx = 0;
			gridBagConstraints9.gridy = 1;
			gridBagConstraints9.weightx = 1.0;
			gridBagConstraints9.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints9.insets = new java.awt.Insets(2,0,2,0);
			gridBagConstraints9.anchor = java.awt.GridBagConstraints.NORTHWEST;
			gridBagConstraints10.gridx = 0;
			gridBagConstraints10.gridy = 2;
			gridBagConstraints10.insets = new java.awt.Insets(2,0,2,0);
			gridBagConstraints10.anchor = java.awt.GridBagConstraints.NORTHWEST;
			gridBagConstraints10.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.gridy = 3;
			gridBagConstraints11.weightx = 1.0;
			gridBagConstraints11.weighty = 1.0;
			gridBagConstraints11.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints11.insets = new java.awt.Insets(2,0,2,0);
			gridBagConstraints11.anchor = java.awt.GridBagConstraints.NORTHWEST;
			panelSession.setName("General");
			panelSession.setSize(180, 101);
			gridBagConstraints2.anchor = java.awt.GridBagConstraints.NORTHWEST;
			panelSession.add(jLabel, gridBagConstraints2);
			panelSession.add(getTxtSessionName(), gridBagConstraints9);
			panelSession.add(jLabel1, gridBagConstraints10);
			panelSession.add(getTxtDescription(), gridBagConstraints11);
		}
		return panelSession;
	}
	/**
	 * This method initializes txtSessionName	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtSessionName() {
		if (txtSessionName == null) {
			txtSessionName = new JTextField();
		}
		return txtSessionName;
	}
	/**
	 * This method initializes txtDescription	
	 * 	
	 * @return javax.swing.JTextArea	
	 */    
	private JTextArea getTxtDescription() {
		if (txtDescription == null) {
			txtDescription = new JTextArea();
			txtDescription.setBorder(javax.swing.BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			txtDescription.setLineWrap(true);
			txtDescription.setFont(Res.FONT_DIALOG_11);
		}
		return txtDescription;
	}
	
	public void initParam(Object obj) {
	    Session session = (Session) obj;
	    getTxtSessionName().setText(session.getSessionName());
	    getTxtDescription().setText(session.getSessionDesc());
	}
	
	public void validateParam(Object obj) {
	    // no validation needed
	}
	
	public void saveParam (Object obj) throws Exception {
	    Session session = (Session) obj;
	    session.setSessionName(getTxtSessionName().getText());
	    session.setSessionDesc(getTxtDescription().getText());
	}
	
     }  //  @jve:decl-index=0:visual-constraint="10,10"
