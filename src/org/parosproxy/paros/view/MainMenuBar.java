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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.parosproxy.paros.control.Control;
import org.parosproxy.paros.control.MenuFileControl;
import org.parosproxy.paros.control.MenuToolsControl;
import org.parosproxy.paros.utils.I18N;
/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MainMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	
	private JMenu menuEdit;
	private JMenu menuTools;
	private JMenu menuView;
	private JMenuItem menuToolsOptions;
	private JMenu menuFile;
	private JMenuItem menuFileNewSession;
	private JMenuItem menuFileOpen;
	private JMenuItem menuFileSaveAs;
	private JMenuItem menuFileExit;
	private JMenuItem menuFileProperties;
	private JMenuItem menuFileSave;
	private JMenu menuHelp;
	private JMenuItem menuHelpAbout;
    private JMenu menuAnalyse;
	/**
	 * This method initializes 
	 * 
	 */
	public MainMenuBar() {
		super();
		initialize();
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        add(getMenuFile());
        add(getMenuEdit());
        add(getMenuView());
        add(getMenuAnalyse());
        add(getMenuTools());
        add(getMenuHelp());
	}
	
	/**
	 * This method initializes menuEdit	
	 * 	
	 * @return JMenu	
	 */    
	public javax.swing.JMenu getMenuEdit() {
		if (menuEdit == null) {
			menuEdit = new JMenu();
			menuEdit.setText(I18N.get("mainmenu.edit"));
//			menuEdit.add(getJMenuItem());
		}
		return menuEdit;
	}

	/**

	 * This method initializes menuTools	

	 * 	

	 * @return javax.swing.JMenu	

	 */    
	public JMenu getMenuTools() {
		if (menuTools == null) {
			menuTools = new JMenu();
			menuTools.setText(I18N.get("mainmenu.tools"));
			menuTools.addSeparator();
			menuTools.add(getMenuToolsOptions());
		}
		return menuTools;
	}

	/**

	 * This method initializes menuView	

	 * 	

	 * @return javax.swing.JMenu	

	 */    
	public JMenu getMenuView() {
		if (menuView == null) {
			menuView = new JMenu();
			menuView.setText(I18N.get("mainmenu.view"));
		}
		return menuView;
	}

	/**
	 * This method initializes menuToolsOptions	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getMenuToolsOptions() {
		if (menuToolsOptions == null) {
			menuToolsOptions = new JMenuItem();
			menuToolsOptions.setText(I18N.get("mainmenu.options"));
			menuToolsOptions.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getMenuToolsControl().options();
				}
			});
		}
		return menuToolsOptions;
	}

	/**
	 * This method initializes menuFile	
	 * 	
	 * @return JMenu	
	 */    
	public javax.swing.JMenu getMenuFile() {
		if (menuFile == null) {
			menuFile = new javax.swing.JMenu();
			menuFile.setText(I18N.get("mainmenu.file"));
			menuFile.setMnemonic(java.awt.event.KeyEvent.VK_F);
			menuFile.add(getMenuFileNewSession());
			menuFile.add(getMenuFileOpen());
			menuFile.addSeparator();
			menuFile.add(getMenuFileSave());
			menuFile.add(getMenuFileSaveAs());
			menuFile.addSeparator();
			menuFile.add(getMenuFileProperties());
			menuFile.addSeparator();
			menuFile.addSeparator();
			menuFile.add(getMenuFileExit());
		}
		return menuFile;
	}

	/**
	 * This method initializes menuFileNewSession	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private javax.swing.JMenuItem getMenuFileNewSession() {
		if (menuFileNewSession == null) {
			menuFileNewSession = new javax.swing.JMenuItem();
			menuFileNewSession.setText(I18N.get("mainmenu.newSession"));
			menuFileNewSession.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
                        getMenuFileControl().newSession(true);
                        getMenuFileSave().setEnabled(false);
                    } catch (Exception e1) {
                        View.getSingleton().showWarningDialog(I18N.get("mainframe.errorCreatingNewSession"));
                        e1.printStackTrace();
                    }
				}
			});
		}
		return menuFileNewSession;
	}

	/**
	 * This method initializes menuFileOpen	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private javax.swing.JMenuItem getMenuFileOpen() {
		if (menuFileOpen == null) {
			menuFileOpen = new JMenuItem();
			menuFileOpen.setText(I18N.get("mainmenu.openSession"));
			menuFileOpen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getMenuFileControl().openSession();
			        getMenuFileSave().setEnabled(true);
				}
			});
		}
		return menuFileOpen;
	}

	/**
	 * This method initializes menuFileSaveAs	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private javax.swing.JMenuItem getMenuFileSaveAs() {
		if (menuFileSaveAs == null) {
			menuFileSaveAs = new JMenuItem();
			menuFileSaveAs.setText(I18N.get("mainmenu.saveAs"));
			menuFileSaveAs.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
				    getMenuFileControl().saveAsSession();
				}
			});
		}
		return menuFileSaveAs;
	}

	/**
	 * This method initializes menuFileExit	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getMenuFileExit() {
		if (menuFileExit == null) {
			menuFileExit = new JMenuItem();
			menuFileExit.setText(I18N.get("mainmenu.exit"));
			menuFileExit.addActionListener(new ActionListener() { 
				public void actionPerformed(ActionEvent e) {    
					getMenuFileControl().exit();
				}
			});
		}
		return menuFileExit;
	}

	/**
	 * This method initializes menuFileControl	
	 * 	
	 * @return com.proofsecure.paros.view.MenuFileControl	
	 */    
	public MenuFileControl getMenuFileControl() {
		return Control.getSingleton().getMenuFileControl();
	}

	/**
	 * This method initializes menuToolsControl	
	 * 	
	 * @return com.proofsecure.paros.view.MenuToolsControl	
	 */    
	private MenuToolsControl getMenuToolsControl() {
		return Control.getSingleton().getMenuToolsControl();
	}
	/**
	 * This method initializes menuFileProperties	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getMenuFileProperties() {
		if (menuFileProperties == null) {
			menuFileProperties = new JMenuItem();
			menuFileProperties.setText(I18N.get("mainmenu.properties"));
			menuFileProperties.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {    
				    getMenuFileControl().properties();
				}
			});
		}
		return menuFileProperties;
	}
	

	/**
	 * This method initializes menuFileSave	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	public JMenuItem getMenuFileSave() {
		if (menuFileSave == null) {
			menuFileSave = new JMenuItem();
			menuFileSave.setText(I18N.get("mainmenu.save"));
			menuFileSave.setEnabled(false);
			menuFileSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getMenuFileControl().saveSession();
				}
			});
		}
		return menuFileSave;
	}
	
	
	/**
	 * This method initializes menuHelp	
	 * 	
	 * @return javax.swing.JMenu	
	 */    
	private JMenu getMenuHelp() {
		if (menuHelp == null) {
			menuHelp = new JMenu();
			menuHelp.setText(I18N.get("mainmenu.help"));
			menuHelp.add(getMenuHelpAbout());
		}
		return menuHelp;
	}
	
	
	/**
	 * This method initializes menuHelpAbout	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getMenuHelpAbout() {
		if (menuHelpAbout == null) {
			menuHelpAbout = new JMenuItem();
			menuHelpAbout.setText(I18N.get("mainmenu.about"));
			menuHelpAbout.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AboutDialog dialog = new AboutDialog(View.getSingleton().getMainFrame(), true);
					dialog.setVisible(true);
				}
			});
		}
		return menuHelpAbout;
	}
	
	
    /**
     * This method initializes jMenu1	
     * 	
     * @return javax.swing.JMenu	
     */
	public JMenu getMenuAnalyse() {
		if (menuAnalyse == null) {
			menuAnalyse = new JMenu();
			menuAnalyse.setText(I18N.get("mainmenu.analyse"));
		}
        return menuAnalyse;
    }
}
