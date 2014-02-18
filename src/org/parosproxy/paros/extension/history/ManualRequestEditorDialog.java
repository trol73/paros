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
package org.parosproxy.paros.extension.history;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.parosproxy.paros.extension.AbstractDialog;
import org.parosproxy.paros.extension.Extension;
import org.parosproxy.paros.model.Model;
import org.parosproxy.paros.network.HttpMalformedHeaderException;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpSender;
import org.parosproxy.paros.view.HttpPanel;
import javax.swing.JCheckBox;
/**
*
* To change the template for this generated type comment go to
* Window - Preferences - Java - Code Generation - Code and Comments
*/
public class ManualRequestEditorDialog extends AbstractDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HttpPanel requestPanel;
	private JPanel panelCommand;
	private JButton btnSend;
	private JLabel jLabel;
	private JTabbedPane panelTab;
	private HttpPanel responsePanel;
	private Extension extension;
	private HttpSender httpSender;
	private boolean isSendEnabled;

	private JPanel jPanel;
	private JCheckBox chkFollowRedirect;
	private JCheckBox chkUseTrackingSessionState;
   /**
    * @throws HeadlessException
    */
   public ManualRequestEditorDialog() throws HeadlessException {
       super();
		initialize();
       
   }

   /**
    * @param arg0
    * @param arg1
    * @throws HeadlessException
    */
   public ManualRequestEditorDialog(Frame parent, boolean modal, boolean isSendEnabled, Extension extension) throws HeadlessException {
       super(parent, modal);
       this.isSendEnabled = isSendEnabled;
       this.extension = extension;
       initialize();

   }

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
	    getRequestPanel().getPanelOption().add(getPanelCommand(), "");

	    addWindowListener(new java.awt.event.WindowAdapter() { 
	    	public void windowClosing(java.awt.event.WindowEvent e) {
	    	    getHttpSender().shutdown();
	    	    getResponsePanel().setMessage("","", false);
	    	}
	    });

	    setContentPane(getJPanel());
	    //this.setVisible(true);
	    
	}
	
	/**
	 * This method initializes requestPanel	
	 * 	
	 * @return org.parosproxy.paros.view.HttpPanel	
	 */    
	public HttpPanel getRequestPanel() {
		if (requestPanel == null) {
			requestPanel = new HttpPanel(true);
		}
		return requestPanel;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getPanelCommand() {
		if (panelCommand == null) {
			panelCommand = new JPanel();
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			jLabel = new JLabel();
			panelCommand.setLayout(new GridBagLayout());
			jLabel.setText("");
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.ipadx = 0;
			gridBagConstraints2.ipady = 0;
			gridBagConstraints2.anchor = java.awt.GridBagConstraints.NORTHWEST;
			gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.weightx = 1.0D;
			gridBagConstraints3.gridx = 3;
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.anchor = java.awt.GridBagConstraints.NORTHEAST;
			gridBagConstraints3.insets = new java.awt.Insets(2,2,2,2);
			gridBagConstraints1.gridx = 2;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints11.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.gridy = 0;
			gridBagConstraints11.insets = new java.awt.Insets(0,0,0,0);
			panelCommand.add(jLabel, gridBagConstraints2);
			panelCommand.add(getChkUseTrackingSessionState(), gridBagConstraints11);
			panelCommand.add(getChkFollowRedirect(), gridBagConstraints1);
			panelCommand.add(getBtnSend(), gridBagConstraints3);
		}
		return panelCommand;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getBtnSend() {
		if (btnSend == null) {
			btnSend = new JButton();
			btnSend.setText("Send");
			btnSend.setEnabled(isSendEnabled);
			btnSend.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
                    btnSend.setEnabled(false);
			        HttpMessage msg = new HttpMessage();
			        getRequestPanel().getMessage(msg, true);
			        msg.getRequestHeader().setContentLength(msg.getRequestBody().length());
			        send(msg);
				    
				}
			});
		}
		return btnSend;
	}
	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */    
	private JTabbedPane getPanelTab() {
		if (panelTab == null) {
			panelTab = new JTabbedPane();
			panelTab.setDoubleBuffered(true);
			panelTab.addTab("Request", null, getRequestPanel(), null);
			panelTab.addTab("Response", null, getResponsePanel(), null);
		}
		return panelTab;
	}
	/**
	 * This method initializes httpPanel	
	 * 	
	 * @return org.parosproxy.paros.view.HttpPanel	
	 */    
	public HttpPanel getResponsePanel() {
		if (responsePanel == null) {
			responsePanel = new HttpPanel(false);
		}
		return responsePanel;
	}

   public void setExtension(Extension extension) {
       this.extension = extension;
   }
   
   private Extension getExtention() {
       return extension;
   }
   
   public void setVisible(boolean show) {
       if (show) {
           try {
               if (httpSender != null) {
                   httpSender.shutdown();
                   httpSender = null;
               }
           } catch (Exception e) {}
           getPanelTab().setSelectedIndex(0);
       }

       boolean isSessionTrackingEnabled = Model.getSingleton().getOptionsParam().getConnectionParam().isHttpStateEnabled();
       getChkUseTrackingSessionState().setEnabled(isSessionTrackingEnabled);
       super.setVisible(show);       

   }
	
   private HttpSender getHttpSender() {
       if (httpSender == null) {
           httpSender = new HttpSender(Model.getSingleton().getOptionsParam().getConnectionParam(), getChkUseTrackingSessionState().isSelected());
           
       }
       return httpSender;
   }
   
   public void setMessage(HttpMessage msg) {
       getPanelTab().setSelectedIndex(0);
       getRequestPanel().setMessage(msg, true);
       getResponsePanel().setMessage("", "", false);
       getBtnSend().setEnabled(true);
       
   }
   
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			gridBagConstraints31.gridx = 0;
			gridBagConstraints31.gridy = 0;
			gridBagConstraints31.weightx = 1.0;
			gridBagConstraints31.weighty = 1.0;
			gridBagConstraints31.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints31.anchor = java.awt.GridBagConstraints.NORTHWEST;
			jPanel.add(getPanelTab(), gridBagConstraints31);
		}
		return jPanel;
	}
	/**
	 * This method initializes chkFollowRedirect	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */    
	private JCheckBox getChkFollowRedirect() {
		if (chkFollowRedirect == null) {
			chkFollowRedirect = new JCheckBox();
			chkFollowRedirect.setText("Follow redirect");
			chkFollowRedirect.setSelected(true);
		}
		return chkFollowRedirect;
	}
	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */    
	private JCheckBox getChkUseTrackingSessionState() {
		if (chkUseTrackingSessionState == null) {
			chkUseTrackingSessionState = new JCheckBox();
			chkUseTrackingSessionState.setText("Use current tracking session");
		}
		return chkUseTrackingSessionState;
	}
    
    private void send(final HttpMessage msg) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    getHttpSender().sendAndReceive(msg, getChkFollowRedirect().isSelected());
                    
                    EventQueue.invokeAndWait(new Runnable() {
                        public void run() {
                        if (!msg.getResponseHeader().isEmpty()) {
                            getResponsePanel().setMessage(msg, false);
                        }
                        getPanelTab().setSelectedIndex(1);
                        }
                    });
                } catch (NullPointerException npe) {
                    getExtention().getView().showWarningDialog("Malformed header error.");                      
                } catch (HttpMalformedHeaderException mhe) {
                    getExtention().getView().showWarningDialog("Malformed header error.");                      
                } catch (IOException ioe) {
                    getExtention().getView().showWarningDialog("IO error in sending request.");
                } catch (Exception e) {
                    
                } finally {
                    btnSend.setEnabled(true);
                }
            }
        });
        t.setPriority(Thread.NORM_PRIORITY);
        t.start();
    }
    
    
        }
