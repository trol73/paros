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

import java.awt.Component;
import java.util.List;

import javax.swing.JPopupMenu;

import org.parosproxy.paros.extension.ExtensionHookMenu;
import org.parosproxy.paros.extension.ExtensionPopupMenu;

/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MainPopupMenu extends JPopupMenu {
	private static final long serialVersionUID = 1L;
	
	private List<ExtensionPopupMenu> itemList;
	private PopupDeleteMenu popupDeleteMenu;
	private PopupPurgeMenu popupPurgeMenu;
    /**
     * 
     */
    public MainPopupMenu() {
        super();
 		initialize();
   }

    /**
     * @param arg0
     */
    public MainPopupMenu(String arg0) {
        super(arg0);
    }
    
    public MainPopupMenu(List<ExtensionPopupMenu> itemList) {
        this();
        this.itemList = itemList;
    }
    

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        //this.setVisible(true);
        
	    // added pre-set popup menu here
//        this.add(getPopupFindMenu());
		popupDeleteMenu = new PopupDeleteMenu();
        add(popupDeleteMenu);
        
        popupPurgeMenu = new PopupPurgeMenu();
        add(popupPurgeMenu);
	}
	
	public synchronized void show(Component invoker, int x, int y) {
	    //boolean isFirst = true;
	    for (int i=0; i<getComponentCount(); i++) {
	        try {
	            if (getComponent(i) != null && getComponent(i) instanceof ExtensionPopupMenu) {
	            	ExtensionPopupMenu menu = (ExtensionPopupMenu) getComponent(i);
	                menu.setVisible(menu.isEnableForComponent(invoker) && menu.isEnabled());
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    for ( ExtensionPopupMenu menu : itemList ) {
	        try {
	            if (menu == ExtensionHookMenu.POPUP_MENU_SEPARATOR) {
	                this.addSeparator();
	                continue;
	            }
	            
	            if (menu.isEnableForComponent(invoker)) {		//ForComponent(invoker)) {
	                add(menu);
//	                isFirst = false;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    super.show(invoker, x, y);
	}


}

