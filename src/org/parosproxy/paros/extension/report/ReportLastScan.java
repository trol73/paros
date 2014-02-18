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
package org.parosproxy.paros.extension.report;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.parosproxy.paros.core.scanner.Alert;
import org.parosproxy.paros.db.Database;
import org.parosproxy.paros.db.RecordAlert;
import org.parosproxy.paros.db.RecordScan;
import org.parosproxy.paros.extension.ViewDelegate;
import org.parosproxy.paros.model.Model;


/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ReportLastScan {

    
    public ReportLastScan() {
        
    }

    

    private String getAlertXML(Database db, RecordScan recordScan) throws SQLException {

        Connection conn = null;
        PreparedStatement psAlert = null;
        StringBuffer sb = new StringBuffer();
        
        // prepare table connection
        try {
            conn = db.getDatabaseServer().getNewConnection();
            conn.setReadOnly(true);
            psAlert = conn.prepareStatement("SELECT ALERT.ALERTID FROM ALERT JOIN SCAN ON ALERT.SCANID = SCAN.SCANID WHERE SCAN.SCANID = ? ORDER BY PLUGINID");
            psAlert.setInt(1, recordScan.getScanId());
            psAlert.executeQuery();
            ResultSet rs = psAlert.getResultSet();

            RecordAlert recordAlert = null;
            Alert alert = null;
            Alert lastAlert = null;

            StringBuffer sbURLs = new StringBuffer(100);
            String s = null;
            
            // get each alert from table
            while (rs.next()) {
                int alertId = rs.getInt(1);
                recordAlert = db.getTableAlert().read(alertId);
                alert = new Alert(recordAlert);

                if (lastAlert != null && alert.getPluginId() != lastAlert.getPluginId()) {
                    s = lastAlert.toPluginXML(sbURLs.toString());
                    sb.append(s);
                    sbURLs.setLength(0);
                }

                s = alert.getUrlParamXML();
                sbURLs.append(s);

                lastAlert = alert;

            }

            if (lastAlert != null) {
                sb.append(lastAlert.toPluginXML(sbURLs.toString()));
            }
                

            
        } catch (SQLException e) {
        } finally {
            if (conn != null) {
                conn.close();
            }
            
        }
        
        //exit
        return sb.toString();
    }
    
    public File generate(String fileName, Model model) throws Exception {
        
	    StringBuffer sb = new StringBuffer(500);
	    RecordScan scan = null;
	        
	    scan = model.getDb().getTableScan().getLatestScan();
	    if (scan == null) {
	        // view.showMessageDialog("Scan result is not available.  No report is generated.");
	        return null;
	    }
	    sb.append("<?xml version=\"1.0\"?>");
	    sb.append("<report>\r\n");
	    sb.append("Report generated at " + ReportGenerator.getCurrentDateTimeString() + ".\r\n");
	    sb.append(getAlertXML(model.getDb(), scan));
	    sb.append("</report>");	
	    
	    if (!fileName.endsWith(".htm")) {
	        fileName = fileName + ".htm";		        
	    }
	    
	    File report = ReportGenerator.stringToHtml(sb.toString(), "xml" + File.separator + "reportLatestScan.xsl", fileName);
	    
	    
	    return report;
    }
    
	public void generate(ViewDelegate view, Model model) {		
	    try {
	    	String output = model.getSession().getSessionFolder() + File.separator + "LatestScannedReport.htm";
    		File report = generate(output, model);
    		if (report == null) {
    		    return;
    		}
		    view.showMessageDialog("Scanning report generated.  If it does not show up after clicking OK,\r\nplease browse the file at " + report.getAbsolutePath()); 
  			ReportGenerator.openBrowser(report.getAbsolutePath());
    	} catch (Exception e){
    	    e.printStackTrace();
      		view.showWarningDialog("File creation error."); 
    	}
	}
	

		
    
}
