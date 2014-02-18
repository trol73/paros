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
package org.parosproxy.paros.extension.option;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.parosproxy.paros.model.OptionsParam;
import org.parosproxy.paros.network.ConnectionParam;
import org.parosproxy.paros.utils.Res;
import org.parosproxy.paros.view.AbstractParamPanel;

/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class OptionsConnectionPanel extends AbstractParamPanel {
	private static final long serialVersionUID = 1L;
	
	private JCheckBox chkUseProxyChain;
	private JPanel jPanel;
	private JPanel panelProxyAuth;
	private JScrollPane jScrollPane;
	private JPanel panelProxyChain;
	private JTextField txtProxyChainName;
	private JTextField txtProxyChainPort;
	private JTextArea txtProxyChainSkipName;
	private JTextField txtProxyChainRealm;
	private JTextField txtProxyChainUserName;
	private JTextField txtProxyChainPassword;
	private JCheckBox chkProxyChainAuth;
	
    public OptionsConnectionPanel() {
        super();
 		initialize();
   }

	/**
	 * This method initializes chkUseProxyChain	
	 * 	
	 * @return JCheckBox	
	 */    
	private JCheckBox getChkUseProxyChain() {
		if (chkUseProxyChain == null) {
			chkUseProxyChain = new JCheckBox();
			chkUseProxyChain.setText("Use an outgoing proxy server");
			chkUseProxyChain.addActionListener(new ActionListener() { 
				public void actionPerformed(ActionEvent e) {    
					setProxyChainEnabled(chkUseProxyChain.isSelected());
				}
			});

		}
		return chkUseProxyChain;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints71 = new GridBagConstraints();

			GridBagConstraints gridBagConstraints61 = new GridBagConstraints();

			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();

			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();

			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();

			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();

			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();

			JLabel jLabel7 = new JLabel();

			JLabel jLabel6 = new JLabel();

			JLabel jLabel5 = new JLabel();

			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jLabel5.setText("Address/Domain Name:");
			jLabel6.setText("Port (eg 8080):");
			jLabel7.setText("<html><p>Skip IP address or domain names below (* for wildcard characters, names separate by ;):</p></html>");
			gridBagConstraints15.gridx = 0;
			gridBagConstraints15.gridy = 0;
			gridBagConstraints15.insets = new Insets(2,2,2,2);
			gridBagConstraints15.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints15.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints15.gridwidth = 2;
			gridBagConstraints15.weightx = 1.0D;
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.insets = new Insets(2,2,2,2);
			gridBagConstraints2.anchor = GridBagConstraints.WEST;
			gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.weightx = 0.5D;
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.weightx = 0.5D;
			gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.insets = new Insets(2,2,2,2);
			gridBagConstraints3.anchor = GridBagConstraints.WEST;
			gridBagConstraints3.ipadx = 50;
			gridBagConstraints41.gridx = 0;
			gridBagConstraints41.gridy = 2;
			gridBagConstraints41.insets = new Insets(2,2,2,2);
			gridBagConstraints41.anchor = GridBagConstraints.WEST;
			gridBagConstraints41.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints41.weightx = 0.5D;
			gridBagConstraints51.gridx = 1;
			gridBagConstraints51.gridy = 2;
			gridBagConstraints51.weightx = 0.5D;
			gridBagConstraints51.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints51.insets = new Insets(2,2,2,2);
			gridBagConstraints51.anchor = GridBagConstraints.WEST;
			gridBagConstraints51.ipadx = 50;
			gridBagConstraints61.gridx = 0;
			gridBagConstraints61.gridy = 3;
			gridBagConstraints61.insets = new Insets(2,2,2,2);
			gridBagConstraints61.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints61.weightx = 1.0D;
			gridBagConstraints61.gridwidth = 2;
			gridBagConstraints61.anchor = GridBagConstraints.NORTHEAST;
			gridBagConstraints71.gridx = 0;
			gridBagConstraints71.gridy = 4;
			gridBagConstraints71.weightx = 1.0D;
			gridBagConstraints71.weighty = 0.2D;
			gridBagConstraints71.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints71.insets = new Insets(2,2,2,2);
			gridBagConstraints71.anchor = GridBagConstraints.NORTHEAST;
			gridBagConstraints71.gridwidth = 2;
			gridBagConstraints71.ipady = 20;
			jPanel.setBorder(BorderFactory.createTitledBorder(null, "Use proxy chain", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, Res.FONT_DIALOG_11, Color.black));
			jPanel.add(getChkUseProxyChain(), gridBagConstraints15);
			jPanel.add(jLabel5, gridBagConstraints2);
			jPanel.add(getTxtProxyChainName(), gridBagConstraints3);
			jPanel.add(jLabel6, gridBagConstraints41);
			jPanel.add(getTxtProxyChainPort(), gridBagConstraints51);
			jPanel.add(jLabel7, gridBagConstraints61);
			jPanel.add(getJScrollPane(), gridBagConstraints71);
		}
		return jPanel;
	}
	/**
	 * This method initializes panelProxyAuth	
	 * 	
	 * @return JPanel	
	 */    
	private JPanel getPanelProxyAuth() {
		if (panelProxyAuth == null) {
			GridBagConstraints gridBagConstraints72 = new GridBagConstraints();

			GridBagConstraints gridBagConstraints62 = new GridBagConstraints();

			GridBagConstraints gridBagConstraints52 = new GridBagConstraints();

			GridBagConstraints gridBagConstraints42 = new GridBagConstraints();

			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();

			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();

			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();

			JLabel jLabel11 = new JLabel();

			JLabel jLabel10 = new JLabel();

			JLabel jLabel9 = new JLabel();

			panelProxyAuth = new JPanel();
			panelProxyAuth.setLayout(new GridBagLayout());
			jLabel9.setText("Realm:");
			jLabel10.setText("User name:");
			jLabel11.setText("Password (stored in clear-text):");
			panelProxyAuth.setBorder(BorderFactory.createTitledBorder(null, "Proxy authentication", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, Res.FONT_DIALOG_11, Color.black));
			panelProxyAuth.setFont(new Font("Dialog", Font.PLAIN, 11));
			gridBagConstraints16.gridx = 0;
			gridBagConstraints16.gridy = 0;
			gridBagConstraints16.insets = new Insets(2,2,2,2);
			gridBagConstraints16.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints16.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints16.gridwidth = 2;
			gridBagConstraints16.weightx = 1.0D;
			gridBagConstraints21.gridx = 0;
			gridBagConstraints21.gridy = 1;
			gridBagConstraints21.insets = new Insets(2,2,2,2);
			gridBagConstraints21.anchor = GridBagConstraints.WEST;
			gridBagConstraints21.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints21.weightx = 0.5D;
			gridBagConstraints31.gridx = 1;
			gridBagConstraints31.gridy = 1;
			gridBagConstraints31.weightx = 0.5D;
			gridBagConstraints31.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints31.insets = new Insets(2,2,2,2);
			gridBagConstraints31.anchor = GridBagConstraints.WEST;
			gridBagConstraints31.ipadx = 50;
			gridBagConstraints42.gridx = 0;
			gridBagConstraints42.gridy = 2;
			gridBagConstraints42.insets = new Insets(2,2,2,2);
			gridBagConstraints42.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints42.weightx = 0.5D;
			gridBagConstraints42.anchor = GridBagConstraints.WEST;
			gridBagConstraints52.gridx = 1;
			gridBagConstraints52.gridy = 2;
			gridBagConstraints52.weightx = 0.5D;
			gridBagConstraints52.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints52.insets = new Insets(2,2,2,2);
			gridBagConstraints52.anchor = GridBagConstraints.WEST;
			gridBagConstraints52.ipadx = 50;
			gridBagConstraints62.gridx = 0;
			gridBagConstraints62.gridy = 3;
			gridBagConstraints62.insets = new Insets(2,2,2,2);
			gridBagConstraints62.anchor = GridBagConstraints.WEST;
			gridBagConstraints62.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints62.weightx = 0.5D;
			gridBagConstraints72.gridx = 1;
			gridBagConstraints72.gridy = 3;
			gridBagConstraints72.weightx = 0.5D;
			gridBagConstraints72.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints72.insets = new Insets(2,2,2,2);
			gridBagConstraints72.anchor = GridBagConstraints.WEST;
			gridBagConstraints72.ipadx = 50;
			panelProxyAuth.add(getChkProxyChainAuth(), gridBagConstraints16);
			panelProxyAuth.add(jLabel9, gridBagConstraints21);
			panelProxyAuth.add(getTxtProxyChainRealm(), gridBagConstraints31);
			panelProxyAuth.add(jLabel10, gridBagConstraints42);
			panelProxyAuth.add(getTxtProxyChainUserName(), gridBagConstraints52);
			panelProxyAuth.add(jLabel11, gridBagConstraints62);
			panelProxyAuth.add(getTxtProxyChainPassword(), gridBagConstraints72);
		}
		return panelProxyAuth;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return JScrollPane	
	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTxtProxyChainSkipName());
			jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		}
		return jScrollPane;
	}
	/**
	 * This method initializes panelProxyChain	
	 * 	
	 * @return JPanel	
	 */    
	private JPanel getPanelProxyChain() {
		if (panelProxyChain == null) {
			panelProxyChain = new JPanel();
			GridBagConstraints gridBagConstraints82 = new GridBagConstraints();

			GridBagConstraints gridBagConstraints92 = new GridBagConstraints();

			JLabel jLabel8 = new JLabel();

			GridBagConstraints gridBagConstraints102 = new GridBagConstraints();

			panelProxyChain.setLayout(new GridBagLayout());
			gridBagConstraints82.gridx = 0;
			gridBagConstraints82.gridy = 0;
			gridBagConstraints82.insets = new Insets(2,2,2,2);
			gridBagConstraints82.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints82.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints82.weightx = 1.0D;
			gridBagConstraints92.gridx = 0;
			gridBagConstraints92.gridy = 1;
			gridBagConstraints92.insets = new Insets(2,2,2,2);
			gridBagConstraints92.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints92.fill = GridBagConstraints.HORIZONTAL;
			panelProxyChain.setName("Proxy Chain");
			jLabel8.setText("");
			gridBagConstraints102.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints102.fill = GridBagConstraints.BOTH;
			gridBagConstraints102.gridx = 0;
			gridBagConstraints102.gridy = 2;
			gridBagConstraints102.weightx = 1.0D;
			gridBagConstraints102.weighty = 1.0D;
			panelProxyChain.add(getJPanel(), gridBagConstraints82);
			panelProxyChain.add(getPanelProxyAuth(), gridBagConstraints92);
			panelProxyChain.add(jLabel8, gridBagConstraints102);
		}
		return panelProxyChain;
	}
	/**
	 * This method initializes txtProxyChainName	
	 * 	
	 * @return JTextField	
	 */    
	private JTextField getTxtProxyChainName() {
		if (txtProxyChainName == null) {
			txtProxyChainName = new JTextField();
		}
		return txtProxyChainName;
	}
	/**
	 * This method initializes txtProxyChainPort	
	 * 	
	 * @return JTextField	
	 */    
	private JTextField getTxtProxyChainPort() {
		if (txtProxyChainPort == null) {
			txtProxyChainPort = new JTextField();
		}
		return txtProxyChainPort;
	}
	/**
	 * This method initializes txtProxyChainSkipName	
	 * 	
	 * @return JTextArea	
	 */    
	private JTextArea getTxtProxyChainSkipName() {
		if (txtProxyChainSkipName == null) {
			txtProxyChainSkipName = new JTextArea();
			txtProxyChainSkipName.setFont(new Font("Dialog", Font.PLAIN, 11));
			txtProxyChainSkipName.setMinimumSize(new Dimension(0,32));
			txtProxyChainSkipName.setRows(2);
		}
		return txtProxyChainSkipName;
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.setLayout(new CardLayout());
        this.setName("Connection");
        this.add(getPanelProxyChain(), getPanelProxyChain().getName());


	}
	
	public void initParam(Object obj) {
	    
	    OptionsParam optionsParam = (OptionsParam) obj;
	    ConnectionParam connectionParam = optionsParam.getConnectionParam();
	    
	    // set Proxy Chain parameters
	    if (connectionParam.getProxyChainName().equals("")) {
	        chkUseProxyChain.setSelected(false);
	        setProxyChainEnabled(false);
	    } else {
	        chkUseProxyChain.setSelected(true);
	        setProxyChainEnabled(true);
		    txtProxyChainName.setText(connectionParam.getProxyChainName());
		    txtProxyChainPort.setText(Integer.toString(connectionParam.getProxyChainPort()));
		    txtProxyChainSkipName.setText(connectionParam.getProxyChainSkipName());
		    
		    if (connectionParam.getProxyChainUserName().equals("")) {
		        chkProxyChainAuth.setSelected(false);
		        setProxyChainAuthEnabled(false);
		    } else {
		        chkProxyChainAuth.setSelected(true);
		        setProxyChainAuthEnabled(true);
		        txtProxyChainRealm.setText(connectionParam.getProxyChainRealm());
		        txtProxyChainUserName.setText(connectionParam.getProxyChainUserName());
		        txtProxyChainPassword.setText(connectionParam.getProxyChainPassword());
		    }
		    
	    }
	}
	
	private void setProxyChainEnabled(boolean isEnabled) {
	    txtProxyChainName.setEnabled(isEnabled);
	    txtProxyChainPort.setEnabled(isEnabled);
	    txtProxyChainSkipName.setEnabled(isEnabled);
	    chkProxyChainAuth.setEnabled(isEnabled);
	    Color color = Color.WHITE;
	    if (!isEnabled) {
	        txtProxyChainName.setText("");
	        txtProxyChainPort.setText("");
	        txtProxyChainSkipName.setText("");
	        chkProxyChainAuth.setSelected(false);
	        setProxyChainAuthEnabled(false);
	        color = panelProxyChain.getBackground();
	    }
	    txtProxyChainName.setBackground(color);
	    txtProxyChainPort.setBackground(color);
	    txtProxyChainSkipName.setBackground(color);
	    
	}
	
	private void setProxyChainAuthEnabled(boolean isEnabled) {

	    txtProxyChainRealm.setEnabled(isEnabled);
	    txtProxyChainUserName.setEnabled(isEnabled);
	    txtProxyChainPassword.setEnabled(isEnabled);
	    
	    Color color = Color.WHITE;
	    if (!isEnabled) {
	        txtProxyChainRealm.setText("");
	        txtProxyChainUserName.setText("");
	        txtProxyChainPassword.setText("");
	        color = panelProxyChain.getBackground();
	    }
	    txtProxyChainRealm.setBackground(color);
	    txtProxyChainUserName.setBackground(color);
	    txtProxyChainPassword.setBackground(color);
	    
	}
	
	public void validateParam(Object obj) throws Exception {
//	    int proxyChainPort = 8080;

	    if (chkUseProxyChain.isSelected()) {
            try {
                //proxyChainPort = 
                	Integer.parseInt(txtProxyChainPort.getText());
            } catch (NumberFormatException nfe) {
                txtProxyChainPort.requestFocus();
                throw new Exception("Invalid proxy chain port number.");
            }

        }
	    
	}

	public void saveParam(Object obj) throws Exception {
	    OptionsParam optionsParam = (OptionsParam) obj;
	    ConnectionParam connectionParam = optionsParam.getConnectionParam();
	    int proxyChainPort = 8080;

	    if (chkUseProxyChain.isSelected()) {
            try {
                proxyChainPort = Integer.parseInt(txtProxyChainPort.getText());
            } catch (NumberFormatException nfe) {
                txtProxyChainPort.requestFocus();
                throw new Exception("Invalid proxy chain port number.");
            }
 
        }
	    connectionParam.setProxyChainName(txtProxyChainName.getText());
	    connectionParam.setProxyChainPort(proxyChainPort);
	    connectionParam.setProxyChainSkipName(txtProxyChainSkipName.getText());

	    connectionParam.setProxyChainRealm(txtProxyChainRealm.getText());
	    connectionParam.setProxyChainUserName(txtProxyChainUserName.getText());
	    connectionParam.setProxyChainPassword(txtProxyChainPassword.getText());
	    
	    
	}
	/**
	 * This method initializes txtProxyChainRealm	
	 * 	
	 * @return JTextField	
	 */    
	private JTextField getTxtProxyChainRealm() {
		if (txtProxyChainRealm == null) {
			txtProxyChainRealm = new JTextField();
		}
		return txtProxyChainRealm;
	}
	/**
	 * This method initializes txtProxyChainUserName	
	 * 	
	 * @return JTextField	
	 */    
	private JTextField getTxtProxyChainUserName() {
		if (txtProxyChainUserName == null) {
			txtProxyChainUserName = new JTextField();
		}
		return txtProxyChainUserName;
	}
	/**
	 * This method initializes txtProxyChainPassword	
	 * 	
	 * @return JTextField	
	 */    
	private JTextField getTxtProxyChainPassword() {
		if (txtProxyChainPassword == null) {
			txtProxyChainPassword = new JTextField();
		}
		return txtProxyChainPassword;
	}
	/**
	 * This method initializes chkProxyChainAuth	
	 * 	
	 * @return JCheckBox	
	 */    
	private JCheckBox getChkProxyChainAuth() {
		if (chkProxyChainAuth == null) {
			chkProxyChainAuth = new JCheckBox();
			chkProxyChainAuth.setText("Outgoing proxy server requires authentication");
			chkProxyChainAuth.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setProxyChainAuthEnabled(chkProxyChainAuth.isSelected());
				}
			});

		}
		return chkProxyChainAuth;
	}
}
