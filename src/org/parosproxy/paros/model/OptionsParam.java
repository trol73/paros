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
package org.parosproxy.paros.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.FileConfiguration;
import org.parosproxy.paros.common.AbstractParam;
import org.parosproxy.paros.core.proxy.ProxyParam;
import org.parosproxy.paros.extension.option.OptionsParamCertificate;
import org.parosproxy.paros.extension.option.OptionsParamView;
import org.parosproxy.paros.network.ConnectionParam;



/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class OptionsParam extends AbstractParam {
	
//	private static final String ROOT = "Options";
	
	private ProxyParam proxyParam = new ProxyParam();
	private ConnectionParam connectionParam = new ConnectionParam();
	private OptionsParamView viewParam = new OptionsParamView();
	private OptionsParamCertificate certificateParam = new OptionsParamCertificate();

	private List<AbstractParam> paramSetList = new ArrayList<AbstractParam>();
	private FileConfiguration config;
	private boolean gui = true;
	private File userDirectory;
	
    /**
     * @return Returns the connectionParam.
     */
    public ConnectionParam getConnectionParam() {
        return connectionParam;
    }
    
	/**
	 * @return Returns the proxyParam.
	 */
	public ProxyParam getProxyParam() {
		return proxyParam;
	}
	
	
	/**
	 * @param proxyParam The proxyParam to set.
	 */
	public void setProxyParam(ProxyParam proxyParam) {
		this.proxyParam = proxyParam;
	}
	
	public OptionsParam() {
		
	}


    /**
     * @param connectionParam The connectionParam to set.
     */
    public void setConnectionParam(ConnectionParam connectionParam) {
        this.connectionParam = connectionParam;
    }
    
    /**
     * @param viewParam The viewParam to set.
     */
    public void setViewParam(OptionsParamView viewParam) {
        this.viewParam = viewParam;
    }
    
    /**
     * @return Returns the viewParam.
     */
    public OptionsParamView getViewParam() {
        return viewParam;
    }

    /**
     * @param certificateParam The certificateParam to set.
     */
    public void setCertificateParam(OptionsParamCertificate certificateParam) {
        this.certificateParam = certificateParam;
    }
    
    /**
     * @return Returns the certificateParam.
     */
    public OptionsParamCertificate getCertificateParam() {
        return certificateParam;
    }

    public void addParamSet(AbstractParam paramSet) {
        paramSetList.add(paramSet);
	    paramSet.load(getConfig());
    }

    public AbstractParam getParamSet(Class<?> className) {
    	// TODO there was a try-catch block for getting paramSetList 
       for ( AbstractParam obj : paramSetList ) {
            if ( obj.getClass().equals(className) ) {
            	return obj;
            }
        }
        return null;
    }

    
	public FileConfiguration getConfig() {
		if (config == null) {
			config = Model.createConfig();
/*			
			try {
				// config = new XMLConfiguration(Constant.FILE_CONFIG);
				config = new XMLConfiguration(Constant.getInstance().FILE_CONFIG);
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
*/			
		}
		return config;
	}
  

    /* (non-Javadoc)
     * @see org.parosproxy.paros.common.AbstractParam#parse()
     */
    protected void parse() {
		getConnectionParam().load(getConfig());
	    getProxyParam().load(getConfig());
		getCertificateParam().load(getConfig());
		getViewParam().load(getConfig());
		
//		for (int i=0; i<paramSetList.size(); i++) {
//		    AbstractParam param = (AbstractParam) paramSetList.get(i);
//		    param.load(getConfig());
//		}
    }
    
    public boolean isGUI() {
        return gui;
    }
    
    public void setGUI(boolean gui) {
        this.gui = gui;
    }

    /**
     * @return Returns the currentFolder.
     */
    public File getUserDirectory() {
        return userDirectory;
    }

    /**
     * @param currentFolder The currentFolder to set.
     */
    public void setUserDirectory(File currentDirectory) {
        this.userDirectory = currentDirectory;
    }
    
    
}

