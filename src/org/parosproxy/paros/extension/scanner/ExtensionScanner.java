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

import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import org.apache.commons.configuration.ConfigurationException;
import org.parosproxy.paros.core.scanner.Alert;
import org.parosproxy.paros.core.scanner.HostProcess;
import org.parosproxy.paros.core.scanner.Scanner;
import org.parosproxy.paros.core.scanner.ScannerListener;
import org.parosproxy.paros.core.scanner.ScannerParam;
import org.parosproxy.paros.db.RecordAlert;
import org.parosproxy.paros.db.RecordScan;
import org.parosproxy.paros.db.TableAlert;
import org.parosproxy.paros.extension.CommandLineArgument;
import org.parosproxy.paros.extension.CommandLineListener;
import org.parosproxy.paros.extension.ExtensionAdaptor;
import org.parosproxy.paros.extension.ExtensionHook;
import org.parosproxy.paros.extension.SessionChangedListener;
import org.parosproxy.paros.extension.history.ManualRequestEditorDialog;
import org.parosproxy.paros.model.HistoryReference;
import org.parosproxy.paros.model.Session;
import org.parosproxy.paros.model.SiteMap;
import org.parosproxy.paros.model.SiteNode;
import org.parosproxy.paros.network.HttpMalformedHeaderException;
/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ExtensionScanner extends ExtensionAdaptor implements ScannerListener, SessionChangedListener, CommandLineListener {
    
    private static final int ARG_SCAN_IDX = 0;
    
	private JMenuItem menuItemScanAll;
//	private ExtensionHookMenu pluginMenu;
	private Scanner scanner;
	private SiteMap siteTree;
	private SiteNode startNode;	
	private AlertTreeModel treeAlert;
	
//	private JMenu menuScanner;
	private JMenuItem menuItemPolicy;
	private ProgressDialog progressDialog;  //  @jve:decl-index=0:visual-constraint="10,10"
	private JMenuItem menuItemScan;
	private AlertPanel alertPanel;  //  @jve:decl-index=0:visual-constraint="61,102"
	private RecordScan recordScan;
	
	private ManualRequestEditorDialog manualRequestEditorDialog;
	private PopupMenuResend popupMenuResend;
	private OptionsScannerPanel optionsScannerPanel;
	private ScannerParam scannerParam;   //  @jve:decl-index=0:
	private CommandLineArgument[] arguments = new CommandLineArgument[1];
	private long startTime = 0;

    private PopupMenuScanHistory popupMenuScanHistory;
    
    /**
     * 
     */
    public ExtensionScanner() {
        super();
 		initialize();
    }

    /**
     * @param name
     */
    public ExtensionScanner(String name) {
        super(name);
    }

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.setName("ExtensionScanner");
			
	}
	/**
	 * This method initializes menuItemScanAll	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getMenuItemScanAll() {
		if (menuItemScanAll == null) {
			menuItemScanAll = new JMenuItem();
			menuItemScanAll.setText("Scan All");
			menuItemScanAll.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {
				    menuItemScan.setEnabled(false);
				    menuItemScanAll.setEnabled(false);
				    getAlertPanel().setTabFocus();
				    startScan();
				    
				}
			});

		}
		return menuItemScanAll;
	}
		
	public void hook(ExtensionHook extensionHook) {
	    super.hook(extensionHook);
	    if (getView() != null) {
	        //extensionHook.getHookMenu().addNewMenu(getMenuScanner());
            extensionHook.getHookMenu().addAnalyseMenuItem(getMenuItemScanAll());
            extensionHook.getHookMenu().addAnalyseMenuItem(getMenuItemScan());
            extensionHook.getHookMenu().addAnalyseMenuItem(getMenuItemPolicy());

            extensionHook.getHookMenu().addPopupMenuItem(getPopupMenuResend());
            extensionHook.getHookMenu().addPopupMenuItem(getPopupMenuScanHistory());

            extensionHook.getHookView().addStatusPanel(getAlertPanel());
	        extensionHook.getHookView().addOptionPanel(getOptionsScannerPanel());
	    }
        extensionHook.addSessionListener(this);
        extensionHook.addOptionsParamSet(getScannerParam());
        extensionHook.addCommandLine(getCommandLineArguments());


	}
	
	
	void startScan() {
        siteTree = getModel().getSession().getSiteTree();

	    if (startNode == null) {
	        startNode = (SiteNode) siteTree.getRoot();
	    }
	    
        startScan(startNode);
	}
	
	void startScan(SiteNode startNode) {

	    scanner = new Scanner(getScannerParam(), getModel().getOptionsParam().getConnectionParam());
	    scanner.addScannerListener(this);

	    if (getView() != null) {
	        getProgressDialog().setVisible(true);
		    getProgressDialog().setPluginScanner(this);
	        menuItemScanAll.setEnabled(false);
	        menuItemScan.setEnabled(false);
	        getMenuItemPolicy().setEnabled(false);
            getPopupMenuScanHistory().setEnabled(false);
            getAlertPanel().setTabFocus();

	    }
	    
	    try {
	        recordScan = getModel().getDb().getTableScan().insert(getModel().getSession().getSessionId(), getModel().getSession().getSessionName());
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
        startTime = System.currentTimeMillis();
	    scanner.start(startNode);
        
	}
	

	


    /**
     * @return Returns the startNode.
     */
    public SiteNode getStartNode() {
        return startNode;
    }

    
	/**
	 * This method initializes menuScanner	
	 * 	
	 * @return javax.swing.JMenu	
	 */    
//	private JMenu getMenuScanner() {
//		if (menuScanner == null) {
//			menuScanner = new JMenu();
//			menuScanner.setText("Scanner");
//			menuScanner.add(getMenuItemScanAll());
//			menuScanner.add(getMenuItemScan());
//			menuScanner.addSeparator();
//			menuScanner.add(getMenuItemPolicy());
//		}
//		return menuScanner;
//	}


    public void scannerComplete() {
	    try {
	        Thread.sleep(1000);
	    } catch (Exception e) {}

        final long scanTime = System.currentTimeMillis() - startTime;
        
	    if (getView() != null) {
	        getMenuItemScanAll().setEnabled(true);
	        getMenuItemScan().setEnabled(true);
	        getMenuItemPolicy().setEnabled(true);
            popupMenuScanHistory.setEnabled(true);

	    }

	    if (getView() != null && progressDialog != null) {
	        if (EventQueue.isDispatchThread()) {
	            progressDialog.dispose();
                progressDialog = null;
                getView().showMessageDialog("Scanning completed in " + scanTime/1000 + "s.  The result can be obtained from Report>Last Scan Result.");
	            return;
	        }
	        try {
	            EventQueue.invokeAndWait(new Runnable() {
	                public void run() {
	                    progressDialog.dispose();
	                    progressDialog = null;
                        getView().showMessageDialog("Scanning completed in " + scanTime/1000 + "s.  The result can be obtained from Report>Last Scan Result.");
	                }
	            });
	        } catch (Exception e) {
	        }
	    }

    }
	
	/**
	 * This method initializes menuItemPolicy	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getMenuItemPolicy() {
		if (menuItemPolicy == null) {
			menuItemPolicy = new JMenuItem();
			menuItemPolicy.setText("Scan Policy...");
			menuItemPolicy.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					PolicyDialog dialog = new PolicyDialog(getView().getMainFrame());
				    dialog.initParam(getModel().getOptionsParam());
					int result = dialog.showDialog(false);
					if (result == JOptionPane.OK_OPTION) {
					    try {
			                getModel().getOptionsParam().getConfig().save();
			            } catch (ConfigurationException ce) {
			                ce.printStackTrace();
			                getView().showWarningDialog("Error saving policy.");
			                return;
			            }
					}					
				}
			});

		}
		return menuItemPolicy;
	}

    /* (non-Javadoc)
     * @see com.proofsecure.paros.core.scanner.ScannerListener#ScannerProgress(java.lang.String, com.proofsecure.paros.network.HttpMessage, int)
     */
    public void hostProgress(String hostAndPort, String msg, int percentage) {
        if (getView() != null) {
            getProgressDialog().updateHostProgress(hostAndPort, msg, percentage);
        }
    }

    /* (non-Javadoc)
     * @see com.proofsecure.paros.core.scanner.ScannerListener#HostComplete(java.lang.String)
     */
    public void hostComplete(String hostAndPort) {
        if (getView() != null) {
            getProgressDialog().removeHostProgress(hostAndPort);
        }
    }

    /* (non-Javadoc)
     * @see com.proofsecure.paros.core.scanner.ScannerListener#hostNewScan(java.lang.String)
     */
    public void hostNewScan(String hostAndPort, HostProcess hostThread) {
        if (getView() != null) {
            getProgressDialog().addHostProgress(hostAndPort, hostThread);
        }
    }
    
    public void alertFound(Alert alert) {

        try {
            writeAlertToDB(alert);
            addAlertToDisplay(alert);
        } catch (Exception e) {
        }
    }

    private void addAlertToDisplay(Alert alert) {

        treeAlert.addPath(alert);
        if (getView() != null) {
            getAlertPanel().expandRoot();
        }
        
    }
	/**
	 * This method initializes progressDialog	
	 * 	
	 * @return com.proofsecure.paros.extension.scanner.ProgressDialog	
	 */    
	private ProgressDialog getProgressDialog() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(getView().getMainFrame(), false);
			progressDialog.setSize(500, 460);
		}
		return progressDialog;
	}
	
	
    /**
     * @return Returns the scanner.
     */
    public Scanner getScanner() {
        return scanner;
    }
	/**
	 * This method initializes menuItemScan	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getMenuItemScan() {
		if (menuItemScan == null) {
			menuItemScan = new JMenuItem();
			menuItemScan.setText("Scan");
			menuItemScan.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

				    JTree siteTree = getView().getSiteTreePanel().getTreeSite();
		            SiteNode node = (SiteNode) siteTree.getLastSelectedPathComponent();
		            if (node == null) {
		                getView().showWarningDialog("Please select a site/folder/URL in Sites panel.");
		                return;
		            }
				    menuItemScan.setEnabled(false);
				    menuItemScanAll.setEnabled(false);
	                startScan(node);
	                
				}
			});

		}
		return menuItemScan;
	}
	/**
	 * This method initializes alertPanel	
	 * 	
	 * @return com.proofsecure.paros.extension.scanner.AlertPanel	
	 */    
	AlertPanel getAlertPanel() {
		if (alertPanel == null) {
			alertPanel = new AlertPanel();
			alertPanel.setView(getView());
			alertPanel.setSize(345, 122);
			alertPanel.getTreeAlert().setModel(getTreeModel());
		}
		
		return alertPanel;
	}
	
	private DefaultTreeModel getTreeModel() {
	    if (treeAlert == null) {
	        treeAlert = new AlertTreeModel();
	    }
	    return treeAlert;
	}
	
	private void writeAlertToDB(Alert alert) throws HttpMalformedHeaderException, SQLException {

	    TableAlert tableAlert = getModel().getDb().getTableAlert();
        HistoryReference ref = new HistoryReference(getModel().getSession(), HistoryReference.TYPE_SCANNER, alert.getMessage());
        RecordAlert recordAlert = tableAlert.write(
                recordScan.getScanId(), alert.getPluginId(), alert.getAlert(), alert.getRisk(), alert.getReliability(),
                alert.getDescription(), alert.getUri(), alert.getParam(), alert.getOtherInfo(), alert.getSolution(), alert.getReference(),
        		ref.getHistoryId()
                );
        
        alert.setAlertId(recordAlert.getAlertId());
        
	}
	
	public void sessionChanged(Session session) {
	    AlertTreeModel tree = (AlertTreeModel) getAlertPanel().getTreeAlert().getModel();

	    DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getRoot();
	    
        while (root.getChildCount() > 0) {
            tree.removeNodeFromParent((MutableTreeNode) root.getChildAt(0));
        }
	    
	    try {
            refreshAlert(session);
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	private void refreshAlert(Session session) throws SQLException {

	    TableAlert tableAlert = getModel().getDb().getTableAlert();
	    List<Integer> v = tableAlert.getAlertListBySession(session.getSessionId());
	    
	    for ( Integer alertId : v ) {
	        RecordAlert recAlert = tableAlert.read(alertId);
	        Alert alert = new Alert(recAlert);
	        addAlertToDisplay(alert);
	    }
	}

	/**
	 * This method initializes manualRequestEditorDialog	
	 * 	
	 * @return org.parosproxy.paros.extension.history.ManualRequestEditorDialog	
	 */    
	ManualRequestEditorDialog getManualRequestEditorDialog() {
		if (manualRequestEditorDialog == null) {
			manualRequestEditorDialog = new ManualRequestEditorDialog(getView().getMainFrame(), false, false, this);
			manualRequestEditorDialog.setTitle("Resend");
			manualRequestEditorDialog.setSize(500, 600);
		}
		return manualRequestEditorDialog;
	}
	/**
	 * This method initializes popupMenuResend	
	 * 	
	 * @return org.parosproxy.paros.extension.scanner.PopupMenuResend	
	 */    
	private PopupMenuResend getPopupMenuResend() {
		if (popupMenuResend == null) {
			popupMenuResend = new PopupMenuResend();
			popupMenuResend.setExtension(this);
		}
		return popupMenuResend;
	}
	/**
	 * This method initializes optionsScannerPanel	
	 * 	
	 * @return org.parosproxy.paros.extension.scanner.OptionsScannerPanel	
	 */    
	private OptionsScannerPanel getOptionsScannerPanel() {
		if (optionsScannerPanel == null) {
			optionsScannerPanel = new OptionsScannerPanel();
		}
		return optionsScannerPanel;
	}
	/**
	 * This method initializes scannerParam	
	 * 	
	 * @return org.parosproxy.paros.core.scanner.ScannerParam	
	 */    
	private ScannerParam getScannerParam() {
		if (scannerParam == null) {
			scannerParam = new ScannerParam();
		}
		return scannerParam;
	}
	
    /* (non-Javadoc)
     * @see org.parosproxy.paros.extension.CommandLineListener#execute(org.parosproxy.paros.extension.CommandLineArgument[])
     */
    public void execute(CommandLineArgument[] args) {

        if (arguments[ARG_SCAN_IDX].isEnabled()) {
            System.out.println("Scanner started...");
            startScan();
        } else {
            return;
        }

        while (!getScanner().isStop()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        System.out.println("Scanner completed.");

    }

    private CommandLineArgument[] getCommandLineArguments() {
        arguments[ARG_SCAN_IDX] = new CommandLineArgument("-scan", 0, null, "", "-scan : Run vulnerability scan depending on previously saved policy.");
        return arguments;
    }

    /**
     * This method initializes popupMenuScanHistory	
     * 	
     * @return org.parosproxy.paros.extension.scanner.PopupMenuScanHistory	
     */
    private PopupMenuScanHistory getPopupMenuScanHistory() {
        if (popupMenuScanHistory == null) {
            popupMenuScanHistory = new PopupMenuScanHistory();
            popupMenuScanHistory.setExtension(this);
        }
        return popupMenuScanHistory;
    }
	
          }
