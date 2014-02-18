/*
 * Created on May 18, 2004
 *
 * Paros and its related class files.
 * 
 * Paros is an HTTP/HTTPS proxy for assessing web application security.
 * Copyright (C) 2003-2005 Chinotec Technologies Company
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
package org.parosproxy.paros;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.parosproxy.paros.model.FileCopier;

/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public final class Constant {
    public static final String PROGRAM_NAME     = "Paros";
    
//  ************************************************************
//  the config.xml MUST be set to be the same as the version_tag
//  otherwise the config.xml will be overwritten everytime.
//  ************************************************************
    public static final String PROGRAM_VERSION = "3.2.13";
    public static final long VERSION_TAG = 30020013;
//  ************************************************************
//  note the above
//  ************************************************************
    
    public static final String PROGRAM_TITLE = PROGRAM_NAME + " " + PROGRAM_VERSION;
    public static final String SYSTEM_PAROS_USER_LOG = "paros.user.log";
    
//  public static final String FILE_CONFIG = "xml/config.xml";
//  public static final String FOLDER_PLUGIN = "plugin";
//  public static final String FOLDER_FILTER = "filter";
//  public static final String FOLDER_SESSION = "session";
//    public static final String DBNAME_TEMPLATE = "db/parosdb";
//  public static final String DBNAME_UNTITLED = FOLDER_SESSION + "/untitled";
    
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String OS_NAME = System.getProperty("os.name");
    

    public static final String FILE_CONFIG_DEFAULT = "xml/config.xml";
    public String FILE_CONFIG = "config.xml";
    public static final String FOLDER_PLUGIN = "plugin";
    public static final String FOLDER_FILTER = "filter";
    public static final String FOLDER_SESSION_DEFAULT = "session";
    public String FOLDER_SESSION = "session";
    public static final String DBNAME_TEMPLATE = "db" + FILE_SEPARATOR + "parosdb";

    public static final String DBNAME_UNTITLED_DEFAULT = FOLDER_SESSION_DEFAULT + FILE_SEPARATOR + "untitled";

    public String DBNAME_UNTITLED = FOLDER_SESSION + FILE_SEPARATOR + "untitled";
    public String ACCEPTED_LICENSE_DEFAULT = "AcceptedLicense";
    public String ACCEPTED_LICENSE = ACCEPTED_LICENSE_DEFAULT;
    
    private static Constant instance;
    
    public static final int MAX_HOST_CONNECTION = 5;
    public static final String USER_AGENT = PROGRAM_NAME + "/" + PROGRAM_VERSION;

    private static String staticEyeCatcher = PROGRAM_NAME;
    private static boolean staticSP = false;
    private static Pattern patternWindows = Pattern.compile("window", Pattern.CASE_INSENSITIVE);
    private static Pattern patternLinux = Pattern.compile("linux", Pattern.CASE_INSENSITIVE);
    
    
    public static String getEyeCatcher() {
        return staticEyeCatcher;
    }
    
    public static void setEyeCatcher(String eyeCatcher) {
        staticEyeCatcher = eyeCatcher;
    }
    
    public static void setSP(boolean isSP) {
        staticSP = isSP;
    }

    public static boolean isSP() {
        return staticSP;
    }


    public Constant() {
        FileCopier copier = new FileCopier();
        File f = null;
        Log log = null;
        
        String userhome = System.getProperty("user.home");
        
        // default to use application directory 'log'
        System.setProperty(SYSTEM_PAROS_USER_LOG, "log");

        if (userhome != null && !userhome.equals("")) {
        	String parosDirName = isWindows() ? "paros" : ".paros";
            userhome += FILE_SEPARATOR + parosDirName;
            f = new File(userhome);
            userhome += FILE_SEPARATOR;
            FILE_CONFIG = userhome + FILE_CONFIG;
            FOLDER_SESSION = userhome + FOLDER_SESSION;
            DBNAME_UNTITLED = userhome + DBNAME_UNTITLED;
            ACCEPTED_LICENSE = userhome + ACCEPTED_LICENSE;

            try {
                
                System.setProperty(SYSTEM_PAROS_USER_LOG, userhome);
                System.setProperty("log4j.configuration","xml/log4j.properties");
                
                if (!f.isDirectory()) {
                    f.mkdir();
                    log = LogFactory.getLog(Constant.class);
                    log.info("Created directory "+userhome);

                } else {
                    log = LogFactory.getLog(Constant.class);
                    
                }
                
                f = new File(FILE_CONFIG);
                if ( !f.isFile() ) {
                    log.info("Copying defaults from "+FILE_CONFIG_DEFAULT+" to "+FILE_CONFIG);
                    copier.copy(new File(FILE_CONFIG_DEFAULT), f);
                } else {
                    try {
                        
                        XMLConfiguration config = new XMLConfiguration(FILE_CONFIG);
                        config.setAutoSave(false);
                        config.load();

                        long ver = config.getLong("version");
                        if ( VERSION_TAG > ver ) {
                            // overwrite previous configuration file
                            copier.copy(new File(FILE_CONFIG_DEFAULT),f);                        
                        }
                    } catch (ConfigurationException e) {
                        //  if there is any error in config file (eg config file not exist),
                        //  overwrite previous configuration file 
                        copier.copy(new File(FILE_CONFIG_DEFAULT),f);                        

                    } catch (NoSuchElementException e) {
                        //  overwrite previous configuration file if config file corrupted
                        copier.copy(new File(FILE_CONFIG_DEFAULT),f);                        
                    }
                }
                
                f = new File(FOLDER_SESSION);
                if (!f.isDirectory()) {
                    log.info("Creating directory "+FOLDER_SESSION);
                    f.mkdir();
                }
            } catch (Exception e) {
                System.err.println("Unable to initialize home directory! " + e.getMessage());
                e.printStackTrace(System.err);
                System.exit(1);
            }
            
        } else {
        	System.setProperty("log4j.configuration","xml/log4j.properties");

        	FILE_CONFIG = FILE_CONFIG_DEFAULT;
        	FOLDER_SESSION = FOLDER_SESSION_DEFAULT;
        	DBNAME_UNTITLED = DBNAME_UNTITLED_DEFAULT;
        	ACCEPTED_LICENSE = ACCEPTED_LICENSE_DEFAULT;
        }
    }
    
    public static Constant getInstance() {
        if (instance==null) {
            instance=new Constant();
        }
        return instance;

    }
    
    public static boolean isWindows() {
        return patternWindows.matcher(OS_NAME).find();
    }
    
    public static boolean isLinux() {
        return patternLinux.matcher(OS_NAME).find();
    }
    
}
