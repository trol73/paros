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
package org.parosproxy.paros.extension;

import java.util.List;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.parosproxy.paros.CommandLine;
import org.parosproxy.paros.common.AbstractParam;
import org.parosproxy.paros.control.Proxy;
import org.parosproxy.paros.core.proxy.ProxyListener;
import org.parosproxy.paros.model.Model;
import org.parosproxy.paros.model.OptionsParam;
import org.parosproxy.paros.model.Session;
import org.parosproxy.paros.view.AbstractParamDialog;
import org.parosproxy.paros.view.AbstractParamPanel;
import org.parosproxy.paros.view.TabbedPanel;
import org.parosproxy.paros.view.View;


/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ExtensionLoader {

    private List<Extension> extensionList = new ArrayList<Extension>();
    private List<ExtensionHook> hookList = new ArrayList<ExtensionHook>();
    private Model model;

    private View view;

    public ExtensionLoader(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void addExtension(Extension extension) {
        extensionList.add(extension);
    }
    
    public void destroyAllExtension() {
        for (int i=0; i<getExtensionCount(); i++) {
            getExtension(i).destroy();
        }
        
    }
    
    public Extension getExtension(int i) {
        return extensionList.get(i);
    }
    
    public Extension getExtension(String name) {
        if (name == null) {
            return null;
        }

        for ( Extension p : extensionList ) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }
    
    public int getExtensionCount() {
        return extensionList.size();
    }
    
    public void hookProxyListener(Proxy proxy) {
    	for ( ExtensionHook hook : hookList ) {
            List<ProxyListener> listenerList = hook.getProxyListenerList();
            for ( ProxyListener listener : listenerList ) {
                try {
                    if (listener != null) {
                        proxy.addProxyListener(listener);
                    }
                } catch (Exception e) {}
            }
        }
        
    }
    
    public void optionsChangedAllPlugin(OptionsParam options) {
        for (int i=0; i<getExtensionCount(); i++) {
            ExtensionHook hook = hookList.get(i);
            List<OptionsChangedListener> listenerList = hook.getOptionsChangedListenerList();
            for (int j=0; j<listenerList.size(); j++) {
                try {
                    OptionsChangedListener listener = listenerList.get(j);
                    if (listener != null) {
                        listener.OptionsChanged(options);
                    }
                } catch (Exception e) {}
            }
            
        }
    }
    
    public void runCommandLine() {
        for (int i=0; i<getExtensionCount(); i++) {
        	Extension ext = getExtension(i);
            ExtensionHook hook = hookList.get(i);
            if (ext instanceof CommandLineListener) {
                CommandLineListener listener = (CommandLineListener) ext;
                listener.execute(hook.getCommandLineArgument());
            }
        }
    }
    
    public void sessionChangedAllPlugin(Session session) {
        for (int i=0; i<getExtensionCount(); i++) {
            ExtensionHook hook = hookList.get(i);
            List<SessionChangedListener> listenerList = hook.getSessionListenerList();
            for (int j=0; j<listenerList.size(); j++) {
                try {
                    SessionChangedListener listener = listenerList.get(j);
                    if (listener != null) {
                        listener.sessionChanged(session);
                    }
                } catch (Exception e) {}
            }
            
        }
    }
    
    /**
     * 
     */
    public void startAllExtension() {
    	for ( Extension ext : extensionList) {
            ext.start();
        }
    }
    
    public void startLifeCycle() {
        initAllExtension();
        initModelAllExtension(model);
        initXMLAllExtension(model.getSession(), model.getOptionsParam());
        initViewAllExtension(view);
        
        hookAllExtension();
        startAllExtension();
    }
    
    public void stopAllExtension() {
    	for ( Extension ext : extensionList) {
            ext.stop();
        }
        
    }
    
    private void addParamPanel(List<AbstractParamPanel> panelList, AbstractParamDialog dialog) {
        final String[] ROOT = {};
        for (int i=0; i<panelList.size(); i++) {
            try {
            	AbstractParamPanel panel = panelList.get(i);
                dialog.addParamPanel(ROOT, panel);
            } catch (Exception e) {
                
            }
        }
        
    }
    
    private void addTabPanel(List<AbstractPanel> panelList, TabbedPanel tab) {
        for (int i=0; i<panelList.size(); i++) {
            try {
            	AbstractPanel panel = panelList.get(i);
                tab.add(panel, panel.getName());
            } catch (Exception e) {
                
            }
        }
    }


    
    
    private void hookAllExtension() {
        ExtensionHook extHook = null;
        for (int i=0; i<getExtensionCount(); i++) {
            extHook = new ExtensionHook(model, view);
            getExtension(i).hook(extHook);
            hookList.add(extHook);
            
            if (view != null) {
                // no need to hook view if no GUI
                hookView(view, extHook);
                hookMenu(view, extHook);

            }
            hookOptions(extHook);
        }
        
        if (view != null) {
            view.getMainFrame().getMainMenuBar().validate();
            view.getMainFrame().validate();
        }

    }
    
    /**
     * Hook command line listener with the command line processor
     * @param cmdLine
     */
    public void hookCommandLineListener (CommandLine cmdLine) throws Exception {
        List<CommandLineArgument[]> allCommandLineList = new ArrayList<CommandLineArgument[]>();
        for ( ExtensionHook hook : hookList ) {
            CommandLineArgument[] arg = hook.getCommandLineArgument();
            if (arg.length > 0) {
                allCommandLineList.add(arg);
            }
        }
        cmdLine.parse(allCommandLineList);
    }

    
    private void hookMenu(View view, ExtensionHook hook) {
        if (view == null) {
            return;
        }
        
        if (hook.getHookMenu() == null) {
            return;
        }
        
        ExtensionHookMenu hookMenu = hook.getHookMenu();
        
        // init menus
        JMenu menuFile = view.getMainFrame().getMainMenuBar().getMenuFile();
        JMenu menuEdit = view.getMainFrame().getMainMenuBar().getMenuEdit();
        JMenu menuView = view.getMainFrame().getMainMenuBar().getMenuView();
        JMenu menuAnalyse = view.getMainFrame().getMainMenuBar().getMenuAnalyse();
        JMenu menuTools = view.getMainFrame().getMainMenuBar().getMenuTools();
        
        // process new menus
        JMenuBar bar = view.getMainFrame().getMainMenuBar();
        for ( JMenu menu : hookMenu.getNewMenus() ) {
            bar.add(menu, bar.getMenuCount()-2);	// 2 menus at the back (Tools/Help)
        }

        // process menu
        processMenu(menuFile, hookMenu.getFile(), 2);
        processMenu(menuTools, hookMenu.getTools(), 2);
        processMenu(menuEdit, hookMenu.getEdit(), 0);
        processMenu(menuView, hookMenu.getView(), 0);
        processMenu(menuAnalyse, hookMenu.getAnalyse(), 0);
        
        // process popup menus
        for ( ExtensionPopupMenu item : hookMenu.getPopupMenus() ) {
            if (item == null) {
            	continue;
            }
            view.getPopupList().add(item);
        }

    }
    
    private void hookOptions(ExtensionHook hook) {
        List<AbstractParam> list = hook.getOptionsParamSetList();
        for (int i=0; i<list.size(); i++) {
            try {
                AbstractParam paramSet = (AbstractParam) list.get(i);
                model.getOptionsParam().addParamSet(paramSet);
            } catch (Exception e) {
                
            }
        }
    }


    
    private void hookView(View view, ExtensionHook hook) {
        if (view == null) {
            return;
        }
        
        ExtensionHookView pv = hook.getHookView();
        if (pv == null) {
            return;
        }
        
        addTabPanel(pv.getSelectPanel(), view.getWorkbench().getTabbedSelect());
        addTabPanel(pv.getWorkPanel(), view.getWorkbench().getTabbedWork());
        addTabPanel(pv.getStatusPanel(), view.getWorkbench().getTabbedStatus());
 
        addParamPanel(pv.getSessionPanel(), view.getSessionDialog(""));
        addParamPanel(pv.getOptionsPanel(), view.getOptionsDialog(""));
    }
    
    private void initAllExtension() {
    	for ( Extension extension : extensionList ) {
            extension.init();
        }
    }
    

    
    private void initModelAllExtension(Model model) {
    	for ( Extension extension : extensionList ) {
            extension.initModel(model);
        }
        
    }

    private void initViewAllExtension(View view) {
        if (view == null) {
            return;
        }
        
        for ( Extension extension : extensionList ) {
            extension.initView(view);
        }
    }

    private void initXMLAllExtension(Session session, OptionsParam options) {
    	for ( Extension extension : extensionList ) {
            extension.initXML(session, options);
        }        
    }
    
    private void processMenu(JMenu menu, List<JMenuItem> list, int existingCount) {
        for ( JMenuItem item : list ) {
            if ( item == null ) {
            	continue;
            }
            if ( item == ExtensionHookMenu.MENU_SEPARATOR ) {
                menu.addSeparator();
            } else {
            	menu.add(item, menu.getItemCount() - existingCount);
            }
        }
    	
    }
}
