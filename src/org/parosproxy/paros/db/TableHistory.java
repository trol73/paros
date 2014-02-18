/*
*
* Paros and its related class files.
* 
* Paros is an HTTP/HTTPS proxy for assessing web application security.
* Copyright (C) 2003-2006 Chinotec Technologies Company
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
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.parosproxy.paros.model.HistoryReference;
import org.parosproxy.paros.network.HttpBody;
import org.parosproxy.paros.network.HttpMalformedHeaderException;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpStatusCode;


/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TableHistory extends AbstractTable {

	private static final String	HISTORYID	= "HISTORYID";
	private static final String SESSIONID	= "SESSIONID";
	private static final String HISTTYPE	= "HISTTYPE";
	private static final String METHOD 		= "METHOD";
	private static final String URI			= "URI";
    private static final String STATUSCODE  = "STATUSCODE";
	private static final String TIMESENTMILLIS = "TIMESENTMILLIS";
	private static final String TIMEELAPSEDMILLIS = "TIMEELAPSEDMILLIS";
	private static final String REQHEADER	= "REQHEADER";
	private static final String REQBODY		= "REQBODY";
	private static final String RESHEADER	= "RESHEADER";
	private static final String RESBODY		= "RESBODY";
    private static final String TAG         = "TAG";

    private PreparedStatement psRead;
    private PreparedStatement psWrite1;
    private CallableStatement psWrite2;
    private PreparedStatement psDelete;
    private PreparedStatement psDeleteTemp;
    private PreparedStatement psContainsURI;
//    private PreparedStatement psAlterTable;
    private PreparedStatement psUpdateTag;
    
    private static boolean isExistStatusCode = false;
    
    public TableHistory() {
    }
    
    protected void reconnect(Connection conn) throws SQLException {
        psRead = conn.prepareStatement("SELECT TOP 1 * FROM HISTORY WHERE " + HISTORYID + " = ?");
        // updatable recordset does not work in hsqldb jdbc impelementation!
        //psWrite = mConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        psDelete = conn.prepareStatement("DELETE FROM HISTORY WHERE " + HISTORYID + " = ?");
        psDeleteTemp = conn.prepareStatement("DELETE FROM HISTORY WHERE " + HISTTYPE + " = " + HistoryReference.TYPE_TEMPORARY);
        psContainsURI = conn.prepareStatement("SELECT TOP 1 HISTORYID FROM HISTORY WHERE URI = ? AND  METHOD = ? AND REQBODY = ? AND SESSIONID = ? AND HISTTYPE = ?");

        isExistStatusCode = false;
        ResultSet rs = conn.getMetaData().getColumns(null, null, "HISTORY", "STATUSCODE");
        if (rs.next()) {
            isExistStatusCode = true;
        }
        rs.close();
        if (isExistStatusCode) {
            psWrite1= conn.prepareStatement("INSERT INTO HISTORY ("
                    + SESSIONID + "," + HISTTYPE + "," + TIMESENTMILLIS + "," + TIMEELAPSEDMILLIS + "," + METHOD + "," + URI + "," + REQHEADER + "," + REQBODY + "," + RESHEADER + "," + RESBODY + "," + STATUSCODE
                    + ") VALUES (?, ? ,?, ?, ?, ?, ?, ? ,? , ?, ?)");
        } else {
            psWrite1= conn.prepareStatement("INSERT INTO HISTORY ("
                    + SESSIONID + "," + HISTTYPE + "," + TIMESENTMILLIS + "," + TIMEELAPSEDMILLIS + "," + METHOD + "," + URI + "," + REQHEADER + "," + REQBODY + "," + RESHEADER + "," + RESBODY
                    + ") VALUES (?, ? ,?, ?, ?, ?, ?, ? ,? , ?)");
            
        }
        psWrite2 = conn.prepareCall("CALL IDENTITY();");

        rs = conn.getMetaData().getColumns(null, null, "HISTORY", "TAG");
        if (!rs.next()) {
            PreparedStatement stmt = conn.prepareStatement("ALTER TABLE HISTORY ADD COLUMN TAG VARCHAR DEFAULT ''");
            stmt.execute();
        }
        rs.close();
        
        psUpdateTag = conn.prepareStatement("UPDATE HISTORY SET TAG = ? WHERE HISTORYID = ?");
        
    }
    
    
    	
	public synchronized RecordHistory read(int historyId) throws HttpMalformedHeaderException, SQLException {
	    psRead.setInt(1, historyId);
		psRead.execute();
		ResultSet rs = psRead.getResultSet();
		RecordHistory result = build(rs);
		rs.close();

		return result;
	}
	
	public synchronized RecordHistory write(long sessionId, int histType, HttpMessage msg) throws HttpMalformedHeaderException, SQLException {
	    
	    String reqHeader = "";
	    String reqBody = "";
	    String resHeader = "";
	    String resBody = "";
	    String method = "";
	    String uri = "";
        int statusCode = 0;
	    
	    if (!msg.getRequestHeader().isEmpty()) {
	        reqHeader = msg.getRequestHeader().toString();
	        reqBody = msg.getRequestBody().toString(HttpBody.STORAGE_CHARSET);
	        method = msg.getRequestHeader().getMethod();
	        uri = msg.getRequestHeader().getURI().toString();
	    }

	    if (!msg.getResponseHeader().isEmpty()) {
	        resHeader = msg.getResponseHeader().toString();
	        resBody = msg.getResponseBody().toString(HttpBody.STORAGE_CHARSET);
            statusCode = msg.getResponseHeader().getStatusCode();
	    }
	    
	    return write(sessionId, histType, msg.getTimeSentMillis(), msg.getTimeElapsedMillis(), method, uri, statusCode, reqHeader, reqBody, resHeader, resBody);
	    
	}
	
	private synchronized RecordHistory write(long sessionId, int histType, long timeSentMillis, int timeElapsedMillis,
	        String method, String uri, int statusCode,
	        String reqHeader, String reqBody, String resHeader, String resBody) throws HttpMalformedHeaderException, SQLException {

		psWrite1.setLong(1, sessionId);
		psWrite1.setInt(2, histType);
		psWrite1.setLong(3, timeSentMillis);
		psWrite1.setInt(4, timeElapsedMillis);
		psWrite1.setString(5, method);
		psWrite1.setString(6, uri);        
		psWrite1.setString(7, reqHeader);
		psWrite1.setString(8, reqBody);
		psWrite1.setString(9, resHeader);
		psWrite1.setString(10, resBody);

        if (isExistStatusCode) {
            psWrite1.setInt(11, statusCode);
        }
		psWrite1.executeUpdate();
				
		/*
        String sql = "INSERT INTO HISTORY ("
        		+ REQHEADER + "," + REQBODY + "," + RESHEADER + "," + RESBODY +
				") VALUES ('"+ reqHeader + "','" + reqBody + "','" + resHeader + "','" + resBody + "'); CALL IDENTITY();";
		Statement stmt = mConn.createStatement();
		stmt.executeQuery(sql);
		ResultSet rs = stmt.getResultSet();
		*/
		
		psWrite2.executeQuery();
		ResultSet rs = psWrite2.getResultSet();
		rs.next();
		int id = rs.getInt(1);
		rs.close();
		return read(id);
	}
	
	private RecordHistory build(ResultSet rs) throws HttpMalformedHeaderException, SQLException {
		RecordHistory history = null;
		if (rs.next()) {
			history = new RecordHistory(
					rs.getInt(HISTORYID),
					rs.getInt(HISTTYPE),
                    rs.getLong(SESSIONID),
					rs.getLong(TIMESENTMILLIS),
					rs.getInt(TIMEELAPSEDMILLIS),
					rs.getString(REQHEADER),
					rs.getString(REQBODY),
					rs.getString(RESHEADER),
					rs.getString(RESBODY),
                    rs.getString(TAG)
			);
			
		}
		return history;
	
	}
	
	public List<Integer> getHistoryList(long sessionId, int histType) throws SQLException {
	    PreparedStatement psReadSession = getConnection().prepareStatement("SELECT " + HISTORYID + " FROM HISTORY WHERE " + SESSIONID + " = ? AND " + HISTTYPE + " = ? ORDER BY " + HISTORYID);
        
	    List<Integer> v = new ArrayList<Integer>();
	    psReadSession.setLong(1, sessionId);
	    psReadSession.setInt(2, histType);
	    psReadSession.executeQuery();
	    ResultSet rs = psReadSession.getResultSet();
	    
	    while (rs.next()) {
	        int last = rs.getInt(HISTORYID);
	        v.add(new Integer(last));
	    }
	    rs.close();
	    psReadSession.close();
	    
		return v;
	}

	public List<Integer> getHistoryList(long sessionId, int histType, String filter, boolean isRequest) throws SQLException {
        PreparedStatement psReadSearch = getConnection().prepareStatement("SELECT * FROM HISTORY WHERE " + SESSIONID + " = ? AND " + HISTTYPE + " = ? ORDER BY " + HISTORYID);

	    Pattern pattern = Pattern.compile(filter, Pattern.MULTILINE| Pattern.CASE_INSENSITIVE);
		Matcher matcher = null;

		List<Integer> v = new ArrayList<Integer>();
		psReadSearch.setLong(1, sessionId);
		psReadSearch.setInt(2, histType);
		psReadSearch.executeQuery();
		ResultSet rs = psReadSearch.getResultSet();
		while (rs.next()) {
		    if (isRequest) {
		        matcher = pattern.matcher(rs.getString(REQHEADER));
		        if (matcher.find()) {
		            v.add(new Integer(rs.getInt(HISTORYID)));
		            continue;
		        }
		        matcher = pattern.matcher(rs.getString(REQBODY));
		        if (matcher.find()) {
		            v.add(new Integer(rs.getInt(HISTORYID)));
		            continue;
		        }
		    } else {
		        matcher = pattern.matcher(rs.getString(RESHEADER));
		        if (matcher.find()) {
		            v.add(new Integer(rs.getInt(HISTORYID)));
		            continue;
		        }
		        matcher = pattern.matcher(rs.getString(RESBODY));
		        if (matcher.find()) {
		            v.add(new Integer(rs.getInt(HISTORYID)));
		            continue;
		        }
		    }
		    
		}
		rs.close();
	    psReadSearch.close();

	    return v;
	}

//	public List getHistoryList(long sessionId, int histType, String filter) throws SQLException {
//	    Pattern pattern = Pattern.compile(filter, Pattern.MULTILINE| Pattern.CASE_INSENSITIVE);
//		Matcher matcher = null;
//
//		Vector v = new Vector();
//		psReadSearch.setLong(1, sessionId);
//		psReadSearch.setInt(2, histType);
//		psReadSearch.executeQuery();
//		ResultSet rs = psReadSearch.getResultSet();
//		while (rs.next()) {
//		    matcher = pattern.matcher(rs.getString(REQHEADER));
//		    if (matcher.find()) {
//				v.add(new Integer(rs.getInt(HISTORYID)));
//				continue;
//		    }
//		    matcher = pattern.matcher(rs.getString(REQBODY));
//		    if (matcher.find()) {
//				v.add(new Integer(rs.getInt(HISTORYID)));
//				continue;
//		    }
//		    matcher = pattern.matcher(rs.getString(RESHEADER));
//		    if (matcher.find()) {
//				v.add(new Integer(rs.getInt(HISTORYID)));
//				continue;
//		    }
//		    matcher = pattern.matcher(rs.getString(RESBODY));
//		    if (matcher.find()) {
//				v.add(new Integer(rs.getInt(HISTORYID)));
//				continue;
//		    }
//		    
//		}
//	    return v;
//	}
	
	public void deleteHistorySession(long sessionId) throws SQLException {
        Statement stmt = getConnection().createStatement();
        stmt.executeUpdate("DELETE FROM HISTORY WHERE " + SESSIONID + " = " + sessionId);
        stmt.close();
	}
	
	public void deleteHistoryType(long sessionId, int historyType) throws SQLException {
        Statement stmt = getConnection().createStatement();
        stmt.executeUpdate("DELETE FROM HISTORY WHERE " + SESSIONID + " = " + sessionId + " AND " + HISTTYPE + " = " + historyType);
        stmt.close();
	}

	public void delete(int historyId) throws SQLException {
		psDelete.setInt(1, historyId);
		psDelete.executeUpdate();
		
	}
	
	public void deleteTemporary() throws SQLException {
	    psDeleteTemp.execute();
	}
	
	public boolean containsURI(long sessionId, int historyType, String method, String uri, String body) throws SQLException {
	    psContainsURI.setString(1, uri);
        psContainsURI.setString(2, method);
	    psContainsURI.setString(3, body);
	    psContainsURI.setLong(4, sessionId);
	    psContainsURI.setInt(5, historyType);
	    psContainsURI.executeQuery();
		ResultSet rs = psContainsURI.getResultSet();
	    if (rs.next()) {
	        return true;
	    }
	    rs.close();
	    return false;
	    
	}
    
    public RecordHistory getHistoryCache(HistoryReference ref, HttpMessage reqMsg) throws SQLException , HttpMalformedHeaderException {

        //  get the cache from provided reference.
        //  naturally, the obtained cache should be AFTER AND NEARBY to the given reference.
        //  - historyId up to historyId+200
        //  - match sessionId
        //  - history type can be MANUEL or hidden (hidden is used by images not explicitly stored in history)
        //  - match URI
        PreparedStatement psReadCache = null;
        
        if (isExistStatusCode) {
//          psReadCache = getConnection().prepareStatement("SELECT TOP 1 * FROM HISTORY WHERE URI = ? AND METHOD = ? AND REQBODY = ? AND " + HISTORYID + " >= ? AND " + HISTORYID + " <= ? AND SESSIONID = ? AND (HISTTYPE = " + HistoryReference.TYPE_MANUAL + " OR HISTTYPE = " + HistoryReference.TYPE_HIDDEN + ") AND STATUSCODE != 304");
            psReadCache = getConnection().prepareStatement("SELECT TOP 1 * FROM HISTORY WHERE URI = ? AND METHOD = ? AND REQBODY = ? AND " + HISTORYID + " >= ? AND " + HISTORYID + " <= ? AND SESSIONID = ? AND STATUSCODE != 304");

        } else {
//          psReadCache = getConnection().prepareStatement("SELECT * FROM HISTORY WHERE URI = ? AND METHOD = ? AND REQBODY = ? AND " + HISTORYID + " >= ? AND " + HISTORYID + " <= ? AND SESSIONID = ? AND (HISTTYPE = " + HistoryReference.TYPE_MANUAL + " OR HISTTYPE = " + HistoryReference.TYPE_HIDDEN + ")");
            psReadCache = getConnection().prepareStatement("SELECT * FROM HISTORY WHERE URI = ? AND METHOD = ? AND REQBODY = ? AND " + HISTORYID + " >= ? AND " + HISTORYID + " <= ? AND SESSIONID = ?)");            
            
        }
        psReadCache.setString(1, reqMsg.getRequestHeader().getURI().toString());
        psReadCache.setString(2, reqMsg.getRequestHeader().getMethod());
        psReadCache.setString(3, reqMsg.getRequestBody().toString(HttpBody.STORAGE_CHARSET));

        psReadCache.setInt(4, ref.getHistoryId());        
        psReadCache.setInt(5, ref.getHistoryId()+200);
        psReadCache.setLong(6, ref.getSessionId());
        
        psReadCache.executeQuery();
        ResultSet rs = psReadCache.getResultSet();
        RecordHistory rec = null;
       
        try {
            do {
                rec = build(rs);
                // for retrieval from cache, the message requests nature must be the same.
                // and the result should NOT be NOT_MODIFIED for rendering by browser
                if (rec != null && rec.getHttpMessage().equals(reqMsg) && rec.getHttpMessage().getResponseHeader().getStatusCode() != HttpStatusCode.NOT_MODIFIED) {
                    return rec;
                }

            } while (rec != null);
            
        } finally {
            try {
                rs.close();
                psReadCache.close();
            } catch (Exception e) {}
        }

        // if cache not exist, probably due to NOT_MODIFIED,
        // lookup from cache BEFORE the given reference

        if (isExistStatusCode) {
//            psReadCache = getConnection().prepareStatement("SELECT TOP 1 * FROM HISTORY WHERE URI = ? AND METHOD = ? AND REQBODY = ? AND SESSIONID = ? AND STATUSCODE != 304 AND (HISTTYPE = " + HistoryReference.TYPE_MANUAL + " OR HISTTYPE = " + HistoryReference.TYPE_HIDDEN  + ")");
            psReadCache = getConnection().prepareStatement("SELECT TOP 1 * FROM HISTORY WHERE URI = ? AND METHOD = ? AND REQBODY = ? AND SESSIONID = ? AND STATUSCODE != 304");

        } else {
//            psReadCache = getConnection().prepareStatement("SELECT * FROM HISTORY WHERE URI = ? AND METHOD = ? AND REQBODY = ? AND SESSIONID = ? AND (HISTTYPE = " + HistoryReference.TYPE_MANUAL + " OR HISTTYPE = " + HistoryReference.TYPE_HIDDEN  + ")");
            psReadCache = getConnection().prepareStatement("SELECT * FROM HISTORY WHERE URI = ? AND METHOD = ? AND REQBODY = ? AND SESSIONID = ?");

        }
        psReadCache.setString(1, reqMsg.getRequestHeader().getURI().toString());
        psReadCache.setString(2, reqMsg.getRequestHeader().getMethod());
        psReadCache.setString(3, reqMsg.getRequestBody().toString(HttpBody.STORAGE_CHARSET));
        psReadCache.setLong(4, ref.getSessionId());
        
        psReadCache.executeQuery();
        rs = psReadCache.getResultSet();
        rec = null;
       
        try {
            do {
                rec = build(rs);
                if (rec != null && rec.getHttpMessage().equals(reqMsg) && rec.getHttpMessage().getResponseHeader().getStatusCode() != HttpStatusCode.NOT_MODIFIED) {
                    return rec;
                }

            } while (rec != null);
            
        } finally {
            try {
                rs.close();
                psReadCache.close();
            } catch (Exception e) {}
            
        }
        
        return null;
    }
    
    public void updateTag(int historyId, String tag) throws SQLException {
        psUpdateTag.setString(1, tag);
        psUpdateTag.setInt(2, historyId);
        psUpdateTag.execute();
    }

}
