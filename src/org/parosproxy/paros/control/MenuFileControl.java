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
package org.parosproxy.paros.control;
 
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.parosproxy.paros.Constant;
import org.parosproxy.paros.model.Model;
import org.parosproxy.paros.model.Session;
import org.parosproxy.paros.model.SessionListener;
import org.parosproxy.paros.view.SessionDialog;
import org.parosproxy.paros.view.View;
import org.parosproxy.paros.view.WaitMessageDialog;


/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MenuFileControl implements SessionListener {

    private static Log log = LogFactory.getLog(MenuFileControl.class);

    private View view;
    private Model model;
    private Control control;
    private WaitMessageDialog waitMessageDialog;
    
    public MenuFileControl(Model model, View view, Control control) {
        this.view = view;
        this.model = model;
        this.control = control;
    }
    
	public void exit() {
	    boolean isNewState = model.getSession().isNewState();
	    if (isNewState) {
			if (view.showConfirmDialog("The current session is not saved.  Discard?") != JOptionPane.OK_OPTION) {
				return;
			}
			model.getSession().discard();
	    }

	    WaitMessageDialog dialog = view.getWaitMessageDialog("Shutting down server and database...");

	    Thread t = new Thread(new Runnable() {
	        public void run() {
	            control.shutdown(false);
	    	    log.info(Constant.PROGRAM_TITLE + " terminated.");
	    		System.exit(0);   
	        }
	    });
	    t.start();
	    dialog.setVisible(true);
	}
	
	public void newSession(boolean isPromptNewSession) throws ClassNotFoundException, Exception {
		if (isPromptNewSession) {
		    if (model.getSession().isNewState()) {
				if (view.showConfirmDialog("The current session is not saved.  Discard and create new session?") != JOptionPane.OK_OPTION) {
					return;
				}
				model.getSession().discard();
		    } else if (view.showConfirmDialog("The current session will be closed.  Create new session?") != JOptionPane.OK_OPTION) {
				return;
			}
			model.createAndOpenUntitledDb();
		}
		
		Session session = new Session(model);
	    log.info("new session file created");
	    model.setSession(session);

		view.getSiteTreePanel().getTreeSite().setModel(session.getSiteTree());

		// comment code below so new session use default untitled first.  
//		if (isPromptNewSession) {
//		    SessionDialog dialog = view.getSessionDialog("New Session");
//		    dialog.initParam(session);
//		    dialog.showDialog(false);
//		    saveAsSession();
//		}

		control.getExtensionLoader().sessionChangedAllPlugin(session);
		// refresh display
		
		view.getMainFrame().setTitle(session.getSessionName() + " - " + Constant.PROGRAM_NAME);
		view.getOutputPanel().clear();
		
	}
	
	public void openSession() {
		JFileChooser chooser = new JFileChooser(model.getOptionsParam().getUserDirectory());
		File file = null;
	    chooser.setFileFilter(new FileFilter() {
	           public boolean accept(File file) {
	        	   return file.isDirectory() || file.getName().endsWith(".session");
/*	        	   
	                if (file.isDirectory()) {
	                    return true;
	                } else if (file.isFile() && file.getName().endsWith(".session")) {
	                    return true;
	                }
	                return false;
*/	                
	            }
	           public String getDescription() {
	               return "Paros session";
	           }
	    });
	    int rc = chooser.showOpenDialog(view.getMainFrame());
	    if(rc == JFileChooser.APPROVE_OPTION) {
			try {
	    		file = chooser.getSelectedFile();
	    		if (file == null) {
	    			return;
	    		}
                model.getOptionsParam().setUserDirectory(chooser.getCurrentDirectory());
	    		Session session = model.getSession();
	    	    log.info("opening session file " + file.getAbsolutePath());
	    	    waitMessageDialog = view.getWaitMessageDialog("Loading session file.  Please wait ...");
	    		session.open(file, this);
	    		waitMessageDialog.setVisible(true);
			} catch (Exception e) {
			    e.printStackTrace();
			}
	    }
	}
	public void saveSession() {
	    Session session = model.getSession();

	    if (session.isNewState()) {
		    view.showWarningDialog("Please use Save As...");
		    return;
	    }
	    
		try {
    	    waitMessageDialog = view.getWaitMessageDialog("Saving session file.  Please wait ...");		    
    		session.save(session.getFileName(), this);
    	    log.info("saving session file " + session.getFileName());
    	    waitMessageDialog.setVisible(true);
    	    
		} catch (Exception e) {
		    view.showWarningDialog("Error saving session file.");
    	    log.error("error saving session file " + session.getFileName());
    	    log.error(e.getMessage());
    	    
		}
	    
	}
	
	public void saveAsSession() {
	    Session session = model.getSession();

	    JFileChooser chooser = new JFileChooser(model.getOptionsParam().getUserDirectory());
	    chooser.setFileFilter(new FileFilter() {
	           public boolean accept(File file) {
	                if (file.isDirectory()) {
	                    return true;
	                } else if (file.isFile() && file.getName().endsWith(".session")) {
	                    return true;
	                }
	                return false;
	            }
	           public String getDescription() {
	               return "Paros session";
	           }
	    });
		File file = null;
	    int rc = chooser.showSaveDialog(view.getMainFrame());
	    if(rc == JFileChooser.APPROVE_OPTION) {
    		file = chooser.getSelectedFile();
    		if (file == null) {
    			return;
    		}
            model.getOptionsParam().setUserDirectory(chooser.getCurrentDirectory());
    		String fileName = file.getAbsolutePath();
    		if (!fileName.endsWith(".session")) {
    		    fileName += ".session";
    		}
    		
    		try {
	    	    waitMessageDialog = view.getWaitMessageDialog("Saving new session file.  Please wait ...");
	    	    session.save(fileName, this);
        	    log.info("save as session file " + session.getFileName());
        	    waitMessageDialog.setVisible(true);
    		} catch (Exception e) {
    		    e.printStackTrace();
    		}
	    }
	}
	
	public void properties() {
	    SessionDialog dialog = view.getSessionDialog("Session Properties");
	    dialog.initParam(model.getSession());
	    dialog.showDialog(false);
//		view.getMainFrame().setTitle(Constant.PROGRAM_NAME + " " + Constant.PROGRAM_VERSION + " - " + model.getSession().getSessionName());
	}

    /* (non-Javadoc)
     * @see org.parosproxy.paros.model.SessionListener#sessionOpened(java.lang.Exception)
     */
    public void sessionOpened(File file, Exception e) {
        if (e == null) {
            control.getExtensionLoader().sessionChangedAllPlugin(model.getSession());
            view.getMainFrame().setTitle(file.getName().replaceAll(".session\\z","") + " - " + Constant.PROGRAM_NAME);
        } else {
            view.showWarningDialog("Error opening session file");
            log.error("error opening session file " + file.getAbsolutePath());
            log.error(e.getMessage());
        }

        if (waitMessageDialog != null) {
            waitMessageDialog.setVisible(false);
            waitMessageDialog = null;
        }
    }

    /* (non-Javadoc)
     * @see org.parosproxy.paros.model.SessionListener#sessionSaved(java.lang.Exception)
     */
    public void sessionSaved(Exception e) {
        if (e == null) {
            view.getMainFrame().getMainMenuBar().getMenuFileSave().setEnabled(true);
            File file = new File(model.getSession().getFileName());
            view.getMainFrame().setTitle(file.getName().replaceAll(".session\\z","") + " - " + Constant.PROGRAM_NAME);
        } else {
		    view.showWarningDialog("Error saving session file.");
		    e.printStackTrace();
    	    log.error("error saving session file " + model.getSession().getFileName());
    	    log.error(e.getStackTrace());

        }
        
        if (waitMessageDialog != null) {
            waitMessageDialog.setVisible(false);
            waitMessageDialog = null;
        }

    }
}
