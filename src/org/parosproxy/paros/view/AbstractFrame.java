/*
 * Created on May 17, 2004
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

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

import javax.swing.JFrame;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.FileConfiguration;
import org.parosproxy.paros.Constant;
import org.parosproxy.paros.model.Model;
import org.parosproxy.paros.utils.Res;


/**
 * JFrame with default title, icon, font and centered on screen
 * 
 * 
 */
public abstract class AbstractFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static final String PROP_HEIGHT = ".height";
	private static final String PROP_WIDTH = ".width";
	private static final String PROP_TOP = ".top";
	private static final String PROP_LEFT = ".left";

	
	
	/**
	 * Name of frame, used to store frame size and location in config file
	 */
	private String frameName;
	
	/**
	 * This is the default constructor
	 */
	public AbstractFrame(String frameName) {
		super();
		this.frameName = frameName;
		setIconImage(Res.IMAGE_LOGO);
		setVisible(false);
		setTitle(Constant.PROGRAM_NAME);
	    setFont(Res.FONT_DIALOG_11);
	    restoreLocationAdnSize();
/*	    
	    addComponentListener(new ComponentListener() {
			public void componentShown(ComponentEvent paramComponentEvent) {
			}
			
			public void componentResized(ComponentEvent paramComponentEvent) {
				storeLocation();
			}
			
			public void componentMoved(ComponentEvent paramComponentEvent) {
				storeLocation();
			}
			
			public void componentHidden(ComponentEvent paramComponentEvent) {
System.out.println("HIDE " + AbstractFrame.this.frameName);				
			}
		});
*/	    
	    
	    addWindowStateListener(new WindowStateListener() {
			
			public void windowStateChanged(WindowEvent paramWindowEvent) {
				System.out.println("  windowStateChanged " + AbstractFrame.this.frameName);				
			}
		});
	    
	    addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent paramWindowEvent) {}
			public void windowIconified(WindowEvent paramWindowEvent) {}
			public void windowDeiconified(WindowEvent paramWindowEvent) {}
			public void windowDeactivated(WindowEvent paramWindowEvent) {}
			public void windowClosed(WindowEvent paramWindowEvent) {}
			public void windowActivated(WindowEvent paramWindowEvent) {}
			
			public void windowClosing(WindowEvent paramWindowEvent) {
				storeLocation();
			}
			
		});
	}

	
	/**
	 * Centre this frame.
	 *
	 */
	public void centerFrame() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
	    setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}
	
	/**
	 * Set name of frame, that used to store frame size and location in config file 
	 * @param frameName
	 */
	public void setFrameName(String frameName) {
		this.frameName = frameName;
	}
	
	private void storeLocation() {
		if ( frameName == null ) {
			return;
		}
		
		FileConfiguration config = loadConfig();
		String configName = "ui." + frameName;
		Point location = getLocation();
		config.setAutoSave(true);
		config.setProperty(configName + PROP_LEFT, Integer.toString(location.x));
		config.setProperty(configName + PROP_TOP, Integer.toString(location.y));
		config.setProperty(configName + PROP_WIDTH, Integer.toString(getWidth()));
		config.setProperty(configName + PROP_HEIGHT, Integer.toString(getHeight()));
		
		try {
			config.save();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	
System.out.println("STORE " + frameName + "  " + location.x + "  " + location.y + "  " + getWidth() + "  " + getHeight());

	}
	
	private void restoreLocationAdnSize() {
		if ( frameName == null ) {
			return;
		}
		
		FileConfiguration config = loadConfig();
		String configName = "ui." + frameName;
		final int INVALID_VALUE = -10000;
		int left = config.getInt(configName + PROP_LEFT, INVALID_VALUE);
		int top = config.getInt(configName + PROP_TOP, INVALID_VALUE);
		int width = config.getInt(configName + PROP_WIDTH, 800);
		int height = config.getInt(configName + PROP_HEIGHT, 600);
System.out.println("RESTORE " + frameName + "  " + left + "  " + top + "  " + width + "  " + height);
		setSize(width, height);
		if ( left <= INVALID_VALUE || top <= INVALID_VALUE ) {
			centerFrame();
		}

	}

	private FileConfiguration loadConfig() {
		FileConfiguration config = Model.createConfig();
		try {
			config.load();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		return config;
	}
	
	
	
}
