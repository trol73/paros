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
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.parosproxy.paros.Constant;
import org.parosproxy.paros.core.proxy.ProxyParam;
import org.parosproxy.paros.model.OptionsParam;
import org.parosproxy.paros.utils.Res;
import org.parosproxy.paros.view.AbstractParamPanel;
/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class OptionsLocalProxyPanel extends AbstractParamPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel panelLocalProxy;
	private JPanel panelReverseProxy;

	private JPanel panelProxy;
	private JTextField txtProxyIp;
	private JTextField txtReverseProxyIp;
    
	private JTextField txtProxyPort;
	private JTextField txtReverseProxyHttpPort;

	
	private JLabel jLabel6;
	private JCheckBox chkReverseProxy;
	private JLabel jLabel5;
	private JTextField txtReverseProxyHttpsPort;
	private JLabel jLabel7;
    public OptionsLocalProxyPanel() {
        super();
 		initialize();
   }
    
	/**
	 * This method initializes panelLocalProxy	
	 * 	
	 * @return JPanel	
	 */    
	private JPanel getPanelLocalProxy() {
		if (panelLocalProxy == null) {
			jLabel6 = new JLabel();
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();

			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();

			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();

			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();

			JLabel jLabel = new JLabel();

			JLabel jLabel1 = new JLabel();
			
			panelLocalProxy = new JPanel();
			panelLocalProxy.setLayout(new GridBagLayout());
			panelLocalProxy.setBorder(BorderFactory.createTitledBorder(null, "Local proxy", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 11), Color.black));
			jLabel.setText("Address (eg localhost, 127.0.0.1)");
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 0;
			gridBagConstraints4.ipadx = 0;
			gridBagConstraints4.ipady = 0;
			gridBagConstraints4.anchor = GridBagConstraints.WEST;
			gridBagConstraints4.insets = new Insets(2,2,2,2);
			gridBagConstraints4.weightx = 0.5D;
			gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.gridx = 1;
			gridBagConstraints5.gridy = 0;
			gridBagConstraints5.weightx = 0.5D;
			gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.ipadx = 50;
			gridBagConstraints5.ipady = 0;
			gridBagConstraints5.anchor = GridBagConstraints.EAST;
			gridBagConstraints5.insets = new Insets(2,2,2,2);
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.gridy = 1;
			gridBagConstraints6.ipadx = 0;
			gridBagConstraints6.ipady = 0;
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints6.insets = new Insets(2,2,2,2);
			gridBagConstraints6.weightx = 0.5D;
			gridBagConstraints7.gridx = 1;
			gridBagConstraints7.gridy = 1;
			gridBagConstraints7.weightx = 0.5D;
			gridBagConstraints7.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints7.ipadx = 50;
			gridBagConstraints7.ipady = 0;
			gridBagConstraints7.anchor = GridBagConstraints.EAST;
			gridBagConstraints7.insets = new Insets(2,2,2,2);
			jLabel1.setText("Port (eg 8080)");
			jLabel6.setText("<html><body><br><p>Set your browser proxy setting using the above.  The http port and https port must be the same port as above.</p></body></html>");
			gridBagConstraints15.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints15.gridx = 0;
			gridBagConstraints15.gridy = 4;
			gridBagConstraints15.insets = new Insets(2,2,2,2);
			gridBagConstraints15.weightx = 1.0D;
			gridBagConstraints15.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints15.gridwidth = 2;
			panelLocalProxy.add(jLabel, gridBagConstraints4);
			panelLocalProxy.add(getTxtProxyIp(), gridBagConstraints5);
			panelLocalProxy.add(jLabel1, gridBagConstraints6);
			panelLocalProxy.add(getTxtProxyPort(), gridBagConstraints7);
			panelLocalProxy.add(jLabel6, gridBagConstraints15);
		}
		return panelLocalProxy;
	}
	/**
	 * This method initializes panelLocalProxySSL	
	 * 	
	 * @return JPanel	
	 */    
	private JPanel getPanelReverseProxy() {
		if (panelReverseProxy == null) {
			jLabel7 = new JLabel();
			jLabel5 = new JLabel();
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			JLabel jLabel3 = new JLabel();

			JLabel jLabel2 = new JLabel();

			panelReverseProxy = new JPanel();
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();

			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();

			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();

			GridBagConstraints gridBagConstraints101 = new GridBagConstraints();

			panelReverseProxy.setLayout(new GridBagLayout());
			panelReverseProxy.setSize(114, 132);
			panelReverseProxy.setName("Miscellenous");
			panelReverseProxy.setBorder(
					BorderFactory.createTitledBorder(null, "Reverse proxy", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, Res.FONT_DIALOG_11, Color.black));
			panelReverseProxy.setFont(Res.FONT_DIALOG_11);
			gridBagConstraints101.gridx = 0;
			gridBagConstraints101.gridy = 0;
			gridBagConstraints101.ipadx = 0;
			gridBagConstraints101.ipady = 0;
			gridBagConstraints101.insets = new Insets(2,2,2,2);
			gridBagConstraints101.weightx = 0.5D;
			gridBagConstraints101.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints101.anchor = GridBagConstraints.WEST;
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.gridy = 0;
			gridBagConstraints11.weightx = 0.5D;
			gridBagConstraints11.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.ipadx = 50;
			gridBagConstraints11.anchor = GridBagConstraints.WEST;
			gridBagConstraints11.insets = new Insets(2,2,2,2);
			gridBagConstraints12.gridx = 0;
			gridBagConstraints12.gridy = 1;
			gridBagConstraints12.ipadx = 0;
			gridBagConstraints12.ipady = 0;
			gridBagConstraints12.anchor = GridBagConstraints.WEST;
			gridBagConstraints12.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints12.insets = new Insets(2,2,2,2);
			gridBagConstraints12.weightx = 0.5D;
			gridBagConstraints13.gridx = 1;
			gridBagConstraints13.gridy = 1;
			gridBagConstraints13.weightx = 0.5D;
			gridBagConstraints13.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints13.ipadx = 50;
			gridBagConstraints13.insets = new Insets(2,2,2,2);
			gridBagConstraints13.anchor = GridBagConstraints.WEST;
			jLabel2.setText("Address (eg 192.168.0.1)");
			jLabel3.setText("HTTP Port (eg 80)");
			panelReverseProxy.add(jLabel2, gridBagConstraints101);
			panelReverseProxy.add(getTxtReverseProxyIp(), gridBagConstraints11);
			panelReverseProxy.add(getTxtReverseProxyHttpPort(), gridBagConstraints13);
			panelReverseProxy.setVisible(true);
			jLabel5.setText("HTTPS port (eg 443)");
			gridBagConstraints3.weightx = 0.5D;
			gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.anchor = GridBagConstraints.EAST;
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.gridy = 2;
			gridBagConstraints3.insets = new Insets(2,2,2,2);
			gridBagConstraints3.ipadx = 50;
			gridBagConstraints41.anchor = GridBagConstraints.WEST;
			gridBagConstraints41.gridx = 0;
			gridBagConstraints41.gridy = 2;
			gridBagConstraints41.insets = new Insets(2,2,2,2);
			gridBagConstraints41.weightx = 0.5D;
			gridBagConstraints41.fill = GridBagConstraints.HORIZONTAL;
			jLabel7.setText("<html><body><p>The address should not be \"localhost\" because a reverse proxy should be accessed by browser from another computer.</p></body></html>");
			gridBagConstraints51.gridx = 0;
			gridBagConstraints51.gridy = 3;
			gridBagConstraints51.insets = new Insets(2,2,2,2);
			gridBagConstraints51.gridwidth = 2;
			gridBagConstraints51.weightx = 1.0D;
			gridBagConstraints51.anchor = GridBagConstraints.WEST;
			gridBagConstraints51.fill = GridBagConstraints.BOTH;
			panelReverseProxy.add(jLabel3, gridBagConstraints12);
			panelReverseProxy.add(jLabel7, gridBagConstraints51);
			panelReverseProxy.add(jLabel5, gridBagConstraints41);
			panelReverseProxy.add(getTxtReverseProxyHttpsPort(), gridBagConstraints3);
		}
		return panelReverseProxy;
	}
	/**
	 * This method initializes panelProxy	
	 * 	
	 * @return JPanel	
	 */    
	private JPanel getPanelProxy() {
		if (panelProxy == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			panelProxy = new JPanel();
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();

			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();

			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();

			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();

			JLabel jLabel4 = new JLabel();

			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();

			GridBagConstraints gridBagConstraints91 = new GridBagConstraints();

			GridBagConstraints gridBagConstraints81 = new GridBagConstraints();

			panelProxy.setLayout(new GridBagLayout());

			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.gridy = 0;
			gridBagConstraints8.insets = new Insets(2,0,2,0);
			gridBagConstraints8.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints8.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints8.weightx = 1.0D;
			gridBagConstraints9.gridx = 0;
			gridBagConstraints9.gridy = 1;
			gridBagConstraints9.weightx = 1.0;
			gridBagConstraints9.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints9.insets = new Insets(2,0,2,0);
			gridBagConstraints9.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints10.gridx = 0;
			gridBagConstraints10.gridy = 2;
			gridBagConstraints10.insets = new Insets(2,0,2,0);
			gridBagConstraints10.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints10.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
			panelProxy.setName("Local Proxy");
			panelProxy.setSize(303, 177);
			panelProxy.setFont(new Font("Dialog", Font.PLAIN, 11));
			gridBagConstraints81.gridx = 0;
			gridBagConstraints81.gridy = 0;
			gridBagConstraints81.ipadx = 2;
			gridBagConstraints81.ipady = 4;
			gridBagConstraints81.insets = new Insets(2,2,2,2);
			gridBagConstraints81.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints81.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints81.weightx = 1.0D;
			gridBagConstraints81.weighty = 0.0D;
			gridBagConstraints91.gridx = 0;
			gridBagConstraints91.gridy = 2;
			gridBagConstraints91.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints91.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints91.weightx = 1.0D;
			gridBagConstraints91.weighty = 0.0D;
			gridBagConstraints91.ipady = 4;
			gridBagConstraints91.ipadx = 2;
			jLabel4.setText("");
			gridBagConstraints14.fill = GridBagConstraints.BOTH;
			gridBagConstraints14.gridx = 0;
			gridBagConstraints14.gridy = 2;
			gridBagConstraints14.weightx = 1.0D;
			gridBagConstraints14.weighty = 1.0D;
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.insets = new Insets(2,2,2,2);
			gridBagConstraints2.weightx = 1.0D;
			panelProxy.add(getPanelLocalProxy(), gridBagConstraints81);
		    panelProxy.add(getChkReverseProxy(), gridBagConstraints2);
		    panelProxy.add(getPanelReverseProxy(), gridBagConstraints91);
			panelProxy.add(jLabel4, gridBagConstraints14);
		}
		return panelProxy;
	}

	/**
	 * This method initializes txtProxyIp	
	 * 	
	 * @return JTextField	
	 */    
	private JTextField getTxtProxyIp() {
		if (txtProxyIp == null) {
			txtProxyIp = new JTextField();
			txtProxyIp.setText("");
		}
		return txtProxyIp;
	}
	/**
	 * This method initializes txtProxyIpSSL	
	 * 	
	 * @return JTextField	
	 */    
	private JTextField getTxtReverseProxyIp() {
		if (txtReverseProxyIp == null) {
			txtReverseProxyIp = new JTextField();
		}
		return txtReverseProxyIp;
	}

	
	
	/**
	 * This method initializes txtProxyPort	
	 * 	
	 * @return JTextField	
	 */    
	private JTextField getTxtProxyPort() {
		if (txtProxyPort == null) {
			txtProxyPort = new JTextField();
		}
		return txtProxyPort;
	}
	/**
	 * This method initializes txtProxyPortSSL	
	 * 	
	 * @return JTextField	
	 */    
	private JTextField getTxtReverseProxyHttpPort() {
		if (txtReverseProxyHttpPort == null) {
			txtReverseProxyHttpPort = new JTextField();
		}
		return txtReverseProxyHttpPort;
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        setLayout(new CardLayout());
        setName("Local proxy");
        setSize(391, 320);
        add(getPanelProxy(), getPanelProxy().getName());
        
        getChkReverseProxy().setVisible(Constant.isSP());
        getPanelReverseProxy().setVisible(Constant.isSP());
        
	}
	
	public void initParam(Object obj) {
	    OptionsParam optionsParam = (OptionsParam) obj;
	    ProxyParam proxyParam = optionsParam.getProxyParam();
	    
	    // set Local Proxy parameters
	    txtProxyIp.setText(proxyParam.getProxyIp());
	    txtProxyPort.setText(Integer.toString(proxyParam.getProxyPort()));
	    
	    // set reverse proxy param
        txtReverseProxyIp.setText(proxyParam.getReverseProxyIp());
        txtReverseProxyHttpPort.setText(Integer.toString(proxyParam.getReverseProxyHttpPort()));
        txtReverseProxyHttpsPort.setText(Integer.toString(proxyParam.getReverseProxyHttpsPort()));

        chkReverseProxy.setSelected(proxyParam.isUseReverseProxy());
        setReverseProxyEnabled(proxyParam.isUseReverseProxy());
	}
	
	public void validateParam(Object obj) throws Exception {

	    try {
	        //proxyPort = 
	        	Integer.parseInt(txtProxyPort.getText());
	    } catch (NumberFormatException nfe) {
	        txtProxyPort.requestFocus();
	        throw new Exception("Invalid proxy port number.");
	    }

	    try {
	        //reverseProxyHttpPort = 
	        	Integer.parseInt(txtReverseProxyHttpPort.getText());
	    } catch (NumberFormatException nfe) {
	        txtReverseProxyHttpPort.requestFocus();
	        throw new Exception("Invalid reverse proxy port number.");
	    }
	    
	    try {
	        //reverseProxyHttpsPort = 
	        	Integer.parseInt(txtReverseProxyHttpsPort.getText());
	    } catch (NumberFormatException nfe) {
	        txtReverseProxyHttpsPort.requestFocus();
	        throw new Exception("Invalid reverse proxy port number.");
	    }

	}

	
	public void saveParam(Object obj) throws Exception  {
	    OptionsParam optionsParam = (OptionsParam) obj;
	    ProxyParam proxyParam = optionsParam.getProxyParam();
	    int proxyPort = 0;
	    int reverseProxyHttpPort = 0;
	    int reverseProxyHttpsPort = 0;
	    
	    try {
	        proxyPort = Integer.parseInt(txtProxyPort.getText());
	    } catch (NumberFormatException nfe) {
	        txtProxyPort.requestFocus();
	        throw new Exception("Invalid proxy port number.");
	    }

	    try {
	        reverseProxyHttpPort = Integer.parseInt(txtReverseProxyHttpPort.getText());
	    } catch (NumberFormatException nfe) {
	        txtReverseProxyHttpPort.requestFocus();
	        throw new Exception("Invalid reverse proxy port number.");
	    }

	    try {
	        reverseProxyHttpsPort = Integer.parseInt(txtReverseProxyHttpsPort.getText());
	    } catch (NumberFormatException nfe) {
	        txtReverseProxyHttpsPort.requestFocus();
	        throw new Exception("Invalid reverse proxy port number.");
	    }

	    
	    
	    proxyParam.setProxyIp(txtProxyIp.getText());
	    proxyParam.setProxyPort(proxyPort);

	    proxyParam.setReverseProxyIp(txtReverseProxyIp.getText());
	    proxyParam.setReverseProxyHttpPort(reverseProxyHttpPort);
	    proxyParam.setReverseProxyHttpsPort(reverseProxyHttpsPort);
	    proxyParam.setUseReverseProxy(getChkReverseProxy().isSelected());
	    
	}
	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return JCheckBox	
	 */    
	private JCheckBox getChkReverseProxy() {
		if (chkReverseProxy == null) {
			chkReverseProxy = new JCheckBox();
			chkReverseProxy.setText("Use reverse proxy");
			chkReverseProxy.addActionListener(new ActionListener() { 
				public void actionPerformed(ActionEvent e) {  
				    setReverseProxyEnabled(getChkReverseProxy().isSelected());
				}
			});
		}
		return chkReverseProxy;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return JTextField	
	 */    
	private JTextField getTxtReverseProxyHttpsPort() {
		if (txtReverseProxyHttpsPort == null) {
			txtReverseProxyHttpsPort = new JTextField();
		}
		return txtReverseProxyHttpsPort;
	}
	
	private void setReverseProxyEnabled(boolean isEnabled) {
	    txtProxyIp.setEditable(!isEnabled);
	    txtProxyPort.setEditable(!isEnabled);
	    
	    txtReverseProxyIp.setEditable(isEnabled);
        txtReverseProxyHttpPort.setEditable(isEnabled);
        txtReverseProxyHttpsPort.setEditable(isEnabled);
        
//	    Color color = Color.WHITE;

		if (isEnabled) {
			txtProxyIp.setBackground(panelProxy.getBackground());
			txtProxyPort.setBackground(panelProxy.getBackground());
			txtReverseProxyIp.setBackground(Color.WHITE);
			txtReverseProxyHttpPort.setBackground(Color.WHITE);
			txtReverseProxyHttpsPort.setBackground(Color.WHITE);
		} else {
			txtProxyIp.setBackground(Color.WHITE);
			txtProxyPort.setBackground(Color.WHITE);
			txtReverseProxyIp.setBackground(panelProxy.getBackground());
			txtReverseProxyHttpPort.setBackground(panelProxy.getBackground());
			txtReverseProxyHttpsPort.setBackground(panelProxy.getBackground());

		}
	}
}
