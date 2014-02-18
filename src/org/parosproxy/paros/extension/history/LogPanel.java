/*
 * Created on 2004
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.parosproxy.paros.extension.AbstractPanel;
import org.parosproxy.paros.model.HistoryReference;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.utils.Res;
import org.parosproxy.paros.view.HttpPanel;
import org.parosproxy.paros.view.View;

/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class LogPanel extends AbstractPanel implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JScrollPane scrollLog;
	private JList listLog;
	
	private HttpPanel requestPanel;
	private HttpPanel responsePanel;
    private ExtensionHistory extension;
	
	/**
	 * This is the default constructor
	 */
	public LogPanel() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private  void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(600, 200);
		this.add(getScrollLog(), java.awt.BorderLayout.CENTER);
	}
    
    void setExtension(ExtensionHistory extension) {
        this.extension = extension;
    }
    
	/**

	 * This method initializes scrollLog	

	 * 	

	 * @return JScrollPane	

	 */    
	private JScrollPane getScrollLog() {
		if (scrollLog == null) {
			scrollLog = new JScrollPane();
			scrollLog.setViewportView(getListLog());
			scrollLog.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollLog.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scrollLog.setPreferredSize(new Dimension(800, 200));
			scrollLog.setName("scrollLog");
		}
		return scrollLog;
	}

	/**

	 * This method initializes listLog	

	 * 	

	 * @return JList	

	 */    
	public JList getListLog() {
		if (listLog == null) {
			listLog = new JList();
			listLog.setDoubleBuffered(true);
            listLog.setCellRenderer(getLogPanelCellRenderer());
			listLog.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			listLog.setName("ListLog");
			listLog.setFont(Res.FONT_DEFAULT);
			listLog.addMouseListener(new java.awt.event.MouseAdapter() { 
				public void mousePressed(java.awt.event.MouseEvent e) {    
				    if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {  // right mouse button
				        View.getSingleton().getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
				        return;
				    }	
				    
				    if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0 && e.getClickCount() > 1) {  // double click
						requestPanel.setTabFocus();
						return;
				    }
				}
			});
			
			listLog.addListSelectionListener(new ListSelectionListener() { 

				public void valueChanged(ListSelectionEvent e) {
				    if (listLog.getSelectedValue() == null) {
				        return;
				    }

					final HistoryReference historyRef = (HistoryReference)listLog.getSelectedValue();
					readAndDisplay(historyRef);
				}

			});

		}
		return listLog;
	}
	
//    private void readAndDisplay(HistoryReference historyRef) {
//
//        HttpMessage msg = null;
//        try {
//            msg = historyRef.getHttpMessage();
//            if (msg.getRequestHeader().isEmpty()) {
//                requestPanel.setMessage(null, true);
//            } else {
//                requestPanel.setMessage(msg, true);
//            }
//            
//            if (msg.getResponseHeader().isEmpty()) {
//                responsePanel.setMessage(null, false);
//            } else {
//                responsePanel.setMessage(msg, false);
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        
//    }

    

    
    private List<HistoryReference> displayQueue = new ArrayList<HistoryReference>();
    private Thread thread;
    private LogPanelCellRenderer logPanelCellRenderer;
    
    private void readAndDisplay(final HistoryReference historyRef) {

        synchronized(displayQueue) {
            if (!ExtensionHistory.isEnableForNativePlatform() || !extension.getBrowserDialog().isVisible()) {
                // truncate queue if browser dialog is displayed to have better response
                if (displayQueue.size()>0) {
                    // replace all display queue because the newest display overrides all previous one
                    // pending to be rendered.
                    displayQueue.clear();
                }
            }
            displayQueue.add(historyRef);

        }
        
        if (thread != null && thread.isAlive()) {
            return;
        }
        
        thread = new Thread(this);

        thread.setPriority(Thread.NORM_PRIORITY);
        thread.start();
    }
    
    
    public void setDisplayPanel(HttpPanel requestPanel, HttpPanel responsePanel) {
        this.requestPanel = requestPanel;
        this.responsePanel = responsePanel;

    }
    
    private void displayMessage(HttpMessage msg) {
        
        if (msg.getRequestHeader().isEmpty()) {
            requestPanel.setMessage(null, true);
        } else {
            requestPanel.setMessage(msg, true);
        }
        
        if (msg.getResponseHeader().isEmpty()) {
            responsePanel.setMessage(null, false);
        } else {
            responsePanel.setMessage(msg, false);
        }
    }

    public void run() {
        HistoryReference ref = null;
        int count = 0;
        
        do {
            synchronized(displayQueue) {
                count = displayQueue.size();
                if (count == 0) {
                    break;
                }
                
                ref = displayQueue.get(0);
                displayQueue.remove(0);
            }
            
            try {
                final HistoryReference finalRef = ref;
                final HttpMessage msg = ref.getHttpMessage();
                EventQueue.invokeAndWait(new Runnable() {
                    public void run() {
                        displayMessage(msg);
                        checkAndShowBrowser(finalRef, msg);
                        listLog.requestFocus();

                    }
                });
                
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            
            // wait some time to allow another selection event to be triggered
            try {
                Thread.sleep(200);
            } catch (Exception e) {}
        } while (true);
        
        
    }
    
    private void checkAndShowBrowser(HistoryReference ref, HttpMessage msg) {
        if (!ExtensionHistory.isEnableForNativePlatform() || !extension.getBrowserDialog().isVisible()) {
            return;
        }
        
        extension.browserDisplay(ref, msg);
    }
    /**
     * This method initializes logPanelCellRenderer	
     * 	
     * @return 	
     */
    private LogPanelCellRenderer getLogPanelCellRenderer() {
        if (logPanelCellRenderer == null) {
            logPanelCellRenderer = new LogPanelCellRenderer();
            logPanelCellRenderer.setSize(new Dimension(328, 21));
            logPanelCellRenderer.setBackground(Color.white);
            logPanelCellRenderer.setFont(Res.FONT_SANSSERIF);
        }
        return logPanelCellRenderer;
    }

    
}

