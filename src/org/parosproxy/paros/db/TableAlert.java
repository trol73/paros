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

package org.parosproxy.paros.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TableAlert extends AbstractTable {

	private static final String	ALERTID		= "ALERTID";
	private static final String SCANID		= "SCANID";
	private static final String PLUGINID	= "PLUGINID";
	private static final String ALERT		= "ALERT";
	private static final String RISK		= "RISK";
	private static final String RELIABILITY	= "RELIABILITY";
	private static final String DESCRIPTION	= "DESCRIPTION";
	private static final String URI			= "URI";
	private static final String PARAM 		= "PARAM";
	private static final String OTHERINFO	= "OTHERINFO";
	private static final String SOLUTION	= "SOLUTION";
	private static final String REFERENCE	= "REFERENCE";
	private static final String HISTORYID	= "HISTORYID";

    private PreparedStatement psRead;
    private PreparedStatement psInsert1;
    private CallableStatement psInsert2;

    private PreparedStatement psDeleteAlert;
//    private PreparedStatement psDeleteScan;
    
    public TableAlert() {
    }
    
    protected void reconnect(Connection conn) throws SQLException {
        psRead = conn.prepareStatement("SELECT TOP 1 * FROM ALERT WHERE " + ALERTID + " = ?");
        
        psInsert1 = conn.prepareStatement("INSERT INTO ALERT ("
        		+ SCANID + "," + PLUGINID + "," + ALERT + "," + RISK + "," + RELIABILITY + "," + DESCRIPTION + ","
        		+ URI + "," + PARAM + "," + OTHERINFO + "," + SOLUTION + "," + REFERENCE + "," + HISTORYID        		
				+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        psInsert2 = conn.prepareCall("CALL IDENTITY();");
        psDeleteAlert = conn.prepareStatement("DELETE FROM ALERT WHERE " + ALERTID + " = ?");
        //psDeleteScan = conn.prepareStatement("DELETE FROM ALERT WHERE " + SCANID + " = ?");

    }
    
    
    	
	public synchronized RecordAlert read(int alertId) throws SQLException {
		psRead.setInt(1, alertId);
		psRead.execute();
		ResultSet rs = psRead.getResultSet();
		return build(rs);
	}
	

	public synchronized RecordAlert write(int scanId, int pluginId, String alert, int risk, int reliability, String description, String uri, String query, String otherInfo, String solution, String reference, int historyId) throws SQLException {
	    
		psInsert1.setInt(1, scanId);
		psInsert1.setInt(2, pluginId);
		psInsert1.setString(3, alert);
		psInsert1.setInt(4, risk);
		psInsert1.setInt(5, reliability);
		psInsert1.setString(6, description);
		psInsert1.setString(7, uri);
		psInsert1.setString(8, query);
		psInsert1.setString(9, otherInfo);
		psInsert1.setString(10, solution);
		psInsert1.setString(11, reference);
		psInsert1.setInt(12, historyId);
		psInsert1.executeUpdate();
		
		psInsert2.executeQuery();
		ResultSet rs = psInsert2.getResultSet();
		rs.next();
		int id = rs.getInt(1);
		return read(id);
	}
	
	private RecordAlert build(ResultSet rs) throws SQLException {
		RecordAlert alert = null;
		if (rs.next()) {
			alert = new RecordAlert(
					rs.getInt(ALERTID),
					rs.getInt(SCANID),
					rs.getInt(PLUGINID),
					rs.getString(ALERT),
					rs.getInt(RISK),
					rs.getInt(RELIABILITY),
					rs.getString(DESCRIPTION),
					rs.getString(URI),
					rs.getString(PARAM),
					rs.getString(OTHERINFO),
					rs.getString(SOLUTION),
					rs.getString(REFERENCE),
					rs.getInt(HISTORYID)
			);
			
		}
		return alert;
	
	}
	
	public List<Integer> getAlertListByScan(int scanId) throws SQLException {
	    PreparedStatement psReadScan = getConnection().prepareStatement("SELECT ALERTID FROM ALERT WHERE " + SCANID + " = ?");
        
	    List<Integer> v = new ArrayList<Integer>();
		psReadScan.setInt(1, scanId);
		psReadScan.executeQuery();
		ResultSet rs = psReadScan.getResultSet();
		while ( rs.next() ) {
			v.add(new Integer(rs.getInt(ALERTID)));
		}
		rs.close();
		psReadScan.close();
		return v;
	}

	public List<Integer> getAlertListBySession(long sessionId) throws SQLException {

	    PreparedStatement psReadSession = getConnection().prepareStatement("SELECT ALERTID FROM ALERT INNER JOIN SCAN ON ALERT.SCANID = SCAN.SCANID WHERE SESSIONID = ?");
        
	    List<Integer> v = new ArrayList<Integer>();
		psReadSession.setLong(1, sessionId);
		psReadSession.executeQuery();
		ResultSet rs = psReadSession.getResultSet();
		while (rs.next()) {
		    int alertId = rs.getInt(ALERTID);
			v.add(new Integer(alertId));
		}
		rs.close();
		psReadSession.close();
		return v;
	}

	
	public void deleteAlert(int alertId) throws SQLException {
	    psDeleteAlert.setInt(1, alertId);
	    psDeleteAlert.executeQuery();
	}
	
	
}
