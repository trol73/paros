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


import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.parosproxy.paros.Constant;
import org.parosproxy.paros.extension.ExtensionPopupMenu;
import org.parosproxy.paros.extension.ViewDelegate;



/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class View implements ViewDelegate {
	
	private static View view;
//	private FindDialog findDialog;
	private SessionDialog sessionDialog;
	private OptionsDialog optionsDialog;
	
	//private LogPanel logPanel = null;
	private MainFrame mainFrame;
	private HttpPanel requestPanel;
	private HttpPanel responsePanel;
	private SiteMapPanel siteMapPanel;
	/**
	 * Statistic panel at bottom
	 */
	private OutputPanel outputPanel;
	private List<ExtensionPopupMenu> popupList = new ArrayList<ExtensionPopupMenu>();
	
	/**
	 * @return Returns the mainFrame.
	 */
	public MainFrame getMainFrame() {
		return mainFrame;
	}
	/**
	 * @return Returns the requestPanel.
	 */
	//public HttpPanel getRequestPanel() {
	//	return requestPanel;
	//}
	/**
	 * @return Returns the responsePanel.
	 */
	//public HttpPanel getResponsePanel() {
	//	return responsePanel;
	//}

	
	public void init() {
		mainFrame = new MainFrame();

		siteMapPanel = new SiteMapPanel();
		outputPanel = new OutputPanel();

		// do not allow editable in request panel
		getWorkbench().getTabbedWork().add(getRequestPanel());
		getWorkbench().getTabbedWork().add(getResponsePanel());
		
		//logPanel.setDisplayPanel(requestPanel, responsePanel);
		//getWorkbench().getTabbedStatus().add(logPanel, "URLs");
		
		getWorkbench().getTabbedSelect().add(siteMapPanel, "Sites");
		
		getWorkbench().getTabbedWork().setAlternativeParent(mainFrame.getPaneDisplay());
		getWorkbench().getTabbedStatus().setAlternativeParent(mainFrame.getPaneDisplay());
		getWorkbench().getTabbedSelect().setAlternativeParent(mainFrame.getPaneDisplay());

	}
	
	public void postInit() {
	    getWorkbench().getTabbedStatus().add(outputPanel);
	    
	}
	
	public int showConfirmDialog(String msg) {
		return JOptionPane.showConfirmDialog(getMainFrame(), msg, Constant.PROGRAM_NAME, JOptionPane.OK_CANCEL_OPTION);
	}
	
	public int showYesNoCancelDialog(String msg) {
		return JOptionPane.showConfirmDialog(getMainFrame(), msg, Constant.PROGRAM_NAME, JOptionPane.YES_NO_CANCEL_OPTION);
	}
	
	public void showWarningDialog(String msg) {
		JOptionPane.showMessageDialog(getMainFrame(), msg, Constant.PROGRAM_NAME, JOptionPane.WARNING_MESSAGE);
	}

	public void showMessageDialog(String msg) {
		JOptionPane.showMessageDialog(getMainFrame(), msg, Constant.PROGRAM_NAME, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static View getSingleton() {
		if (view == null) {
			view = new View();
			view.init();
		}
		return view;
	}
	
//	public void showFindDialog() {
//	    if (findDialog == null) {
//	        findDialog = new FindDialog(mainFrame, false);
//	    }
//	    
//	    findDialog.setVisible(true);
//	}

	
    /**
     * @return Returns the siteTreePanel.
     */
    public SiteMapPanel getSiteTreePanel() {
        return siteMapPanel;
    }
    
    public OutputPanel getOutputPanel() {
        return outputPanel;
    }

    public HttpPanel getRequestPanel() {
        if (requestPanel == null) {
            requestPanel = new HttpPanel(false);
            requestPanel.setName("Request");
        }
        return requestPanel;
    }
    
    public HttpPanel getResponsePanel() {
        if (responsePanel == null) {
            responsePanel = new HttpPanel(false);
            responsePanel.setName("Response");
            responsePanel.setMessage("","",false);
        }
        return responsePanel;
    }
    
    public SessionDialog getSessionDialog(String title) {
        String[] ROOT = {};
        if (sessionDialog == null) {
            sessionDialog = new SessionDialog(getMainFrame(), true, title, "Session");
            sessionDialog.addParamPanel(ROOT, new SessionGeneralPanel());

        }
        
        sessionDialog.setTitle(title);
        return sessionDialog;
    }
    
    public OptionsDialog getOptionsDialog(String title) {
//        String[] ROOT = {};
        if (optionsDialog == null) {
            optionsDialog = new OptionsDialog(getMainFrame(), true, title);
//            optionsDialog.addParamPanel(ROOT, new OptionsConnectionPanel());
//            optionsDialog.addParamPanel(ROOT, new OptionsLocalProxyPanel());
//            optionsDialog.addParamPanel(ROOT, new OptionsAuthenticationPanel());
//            optionsDialog.addParamPanel(ROOT, new OptionsSpiderPanel());
//            optionsDialog.addParamPanel(ROOT, new OptionsScannerPanel());
//            optionsDialog.addParamPanel(ROOT, new OptionsCertificatePanel());
//            optionsDialog.addParamPanel(ROOT, new OptionsViewPanel());
//            optionsDialog.addParamPanel(ROOT, new OptionsTrapPanel());

        }
        
        optionsDialog.setTitle(title);
        return optionsDialog;
    }
    
    public WorkbenchPanel getWorkbench() {
        return mainFrame.getWorkbench();
    }
    
    public void setStatus(String msg) {
        if (msg == null || msg.equals("")) {
            msg = " ";
        }
        mainFrame.setStatus(msg);
    }
    
    public MainPopupMenu getPopupMenu() {
        MainPopupMenu popup = new MainPopupMenu(popupList);
        return popup;
    }
    
    public List<ExtensionPopupMenu> getPopupList() {
        return popupList;
    }
    
    public WaitMessageDialog getWaitMessageDialog(String s) {
        WaitMessageDialog dialog = new WaitMessageDialog(getMainFrame(), true);
        dialog.setText(s);
        dialog.centreDialog();
        return dialog;
    }
    
    
}
