/*
 *
 * Paros and its related class files.
 * 
 * Paros is an HTTP/HTTPS proxy for assessing web application security.
 * Copyright (C) 2003-2005 Chinotec Technologies Company
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
package org.parosproxy.paros.extension.filter;

import java.awt.Frame;
import java.awt.HeadlessException;

import org.parosproxy.paros.extension.AbstractDialog;
import org.parosproxy.paros.extension.ViewDelegate;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class FilterChangeUserAgentDialog extends AbstractDialog {


    
    
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jPanel;
	private JComboBox cmbUserAgent;
	private JLabel jLabel;
	private JLabel jLabel1;
	private JButton btnOK;
	private JButton btnCancel;
	private JPanel jPanel1;
	private JLabel jLabel2;
	private int exitCode = JOptionPane.CANCEL_OPTION;
//	private ViewDelegate view;

    /**
     * @throws HeadlessException
     */
    public FilterChangeUserAgentDialog() throws HeadlessException {
        super();
 		initialize();
       // TODO Auto-generated constructor stub
    }

    /**
     * @param arg0
     * @param arg1
     * @throws HeadlessException
     */
    public FilterChangeUserAgentDialog(Frame arg0, boolean arg1)
            throws HeadlessException {
        super(arg0, arg1);
 		initialize();
    }

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.setTitle("Change User Agent");
        this.setContentPane(getJPanel());
        this.setSize(375, 173);
        
        for (int i=0; i<FilterChangeUserAgent.userAgentName.length; i++) {
            cmbUserAgent.addItem(FilterChangeUserAgent.userAgentName[i]);
        }
			
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jLabel.setText("Select user agent to be used:");
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.insets = new java.awt.Insets(20,5,2,5);
			gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints1.gridwidth = 2;
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.insets = new java.awt.Insets(2,5,2,5);
			gridBagConstraints2.anchor = java.awt.GridBagConstraints.NORTHWEST;
			gridBagConstraints2.gridwidth = 2;
			jLabel1.setText("");
			gridBagConstraints3.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 2;
			gridBagConstraints3.insets = new java.awt.Insets(2,5,2,5);
			gridBagConstraints3.weightx = 1.0D;
			gridBagConstraints3.weighty = 1.0D;
			gridBagConstraints3.gridwidth = 2;
			gridBagConstraints6.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.gridy = 4;
			gridBagConstraints6.insets = new java.awt.Insets(2,5,2,5);
			gridBagConstraints6.weightx = 1.0D;
			jPanel.add(jLabel, gridBagConstraints1);
			jPanel.add(getCmbUserAgent(), gridBagConstraints2);
			jPanel.add(jLabel1, gridBagConstraints3);
			jPanel.add(getJPanel1(), gridBagConstraints6);
		}
		return jPanel;
	}
	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getCmbUserAgent() {
		if (cmbUserAgent == null) {
			cmbUserAgent = new JComboBox();
		}
		return cmbUserAgent;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setText("OK");
			btnOK.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			btnOK.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) { 
				    FilterChangeUserAgentDialog.this.dispose();
				    exitCode = JOptionPane.OK_OPTION;

				}
			});
		}
		return btnOK;
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("Cancel");
			btnCancel.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
				    FilterChangeUserAgentDialog.this.dispose();
				    exitCode = JOptionPane.CANCEL_OPTION;
				}
			});
		}
		return btnCancel;
	}
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel2 = new JLabel();
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			gridBagConstraints7.gridx = 1;
			gridBagConstraints7.gridy = 0;
			gridBagConstraints7.insets = new java.awt.Insets(5,5,5,5);
			gridBagConstraints7.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints8.gridx = 2;
			gridBagConstraints8.gridy = 0;
			gridBagConstraints8.insets = new java.awt.Insets(5,5,5,5);
			gridBagConstraints8.anchor = java.awt.GridBagConstraints.EAST;
			jLabel2.setText(" ");
			gridBagConstraints9.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints9.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints9.insets = new java.awt.Insets(5,2,5,2);
			gridBagConstraints9.weightx = 1.0D;
			gridBagConstraints9.gridx = 0;
			gridBagConstraints9.gridy = 0;
			jPanel1.add(jLabel2, gridBagConstraints9);
			jPanel1.add(getBtnOK(), gridBagConstraints7);
			jPanel1.add(getBtnCancel(), gridBagConstraints8);
		}
		return jPanel1;
	}
	
    public void setView(ViewDelegate view) {
//        this.view = view;
    }
    
	public int showDialog() {
	    setVisible(true);
	    return exitCode;
	}
	
	public int getUserAgentItem() {
	    return getCmbUserAgent().getSelectedIndex();
	}
     }  //  @jve:decl-index=0:visual-constraint="10,10"
