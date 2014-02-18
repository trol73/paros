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

import java.sql.SQLException;

import org.parosproxy.paros.db.RecordHistory;
import org.parosproxy.paros.db.TableHistory;
import org.parosproxy.paros.network.HttpMalformedHeaderException;
import org.parosproxy.paros.network.HttpMessage;


/**
* 
* This class abstracts a reference to a http message stored in database.
* It read the whole http message from database when getHttpMessage() is called.
*/
public class HistoryReference {

   /**
    * Temporary type = not retrieved from history.  To be deleted.
    */
   public static final int TYPE_TEMPORARY = 0;
   public static final int TYPE_MANUAL = 1;
   public static final int TYPE_SPIDER = 2;
   public static final int TYPE_SCANNER = 3;
   public static final int TYPE_SPIDER_SEED = 4;
   public static final int TYPE_SPIDER_VISITED = 5;
   public static final int TYPE_HIDDEN = 6;
   
   // -ve means unsaved message;
   public static final int TYPE_SPIDER_UNSAVE = -TYPE_SPIDER;

   private static java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("##0.###");
	private static TableHistory staticTableHistory = null;

	private int historyId = 0;
	private int historyType = TYPE_MANUAL;
	private SiteNode siteNode = null;
    private String display = null;
    private long sessionId = 0;
	
	/**
     * @return Returns the sessionId.
     */
    public long getSessionId() {
        return sessionId;
    }

    public HistoryReference(int historyId) throws HttpMalformedHeaderException, SQLException {
		RecordHistory history = null;		
		history = staticTableHistory.read(historyId);
		HttpMessage msg = history.getHttpMessage();
		build(history.getSessionId(), history.getHistoryId(), history.getHistoryType(), msg);

	}
	
	public HistoryReference(Session session, int historyType, HttpMessage msg) throws HttpMalformedHeaderException, SQLException {
		RecordHistory history = null;		
		history = staticTableHistory.write(session.getSessionId(), historyType, msg);		
		build(session.getSessionId(), history.getHistoryId(), history.getHistoryType(), msg);

		
	}

	private void build(long sessionId, int historyId, int historyType, HttpMessage msg) {
	    this.sessionId = sessionId;
	    this.historyId = historyId;
		this.historyType = historyType;
		if (historyType == TYPE_MANUAL) {
		    this.display = getDisplay(msg);
		}
	}
	
	public static void setTableHistory(TableHistory tableHistory) {
		staticTableHistory = tableHistory;
	}
	/**
	 * @return Returns the historyId.
	 */
	public int getHistoryId() {
		return historyId;
	}

	public HttpMessage getHttpMessage() throws HttpMalformedHeaderException, SQLException {
		// fetch complete message
		RecordHistory history = staticTableHistory.read(historyId);
		return history.getHttpMessage();
	}
	
	public String toString() {

        if (display != null) {
            return display;
        }
        
	    HttpMessage msg = null;
	    try {
	        msg = getHttpMessage();
            display = getDisplay(msg);	        
	    } catch (HttpMalformedHeaderException e1) {
	        display = "";
	    } catch (SQLException e) {
	        display = "";
	    }
        return display;
	}
	
   /**
    * @return Returns the historyType.
    */
   public int getHistoryType() {
       return historyType;
   }
   
   /**
    * Delete this HistoryReference from database
    *
    */
   public void delete() {
       if (historyId > 0) {
           try {
               staticTableHistory.delete(historyId);
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
   }
   
   
   /**
    * @return Returns the siteNode.
    */
   public SiteNode getSiteNode() {
       return siteNode;
   }
   /**
    * @param siteNode The siteNode to set.
    */
   public void setSiteNode(SiteNode siteNode) {
       this.siteNode = siteNode;
   }
   
   private String getDisplay(HttpMessage msg) {
       StringBuffer sb = new StringBuffer(Integer.toString(historyId) + " ");
       sb.append(msg.getRequestHeader().getPrimeHeader());
       if (!msg.getResponseHeader().isEmpty()) {
           sb.append(" \t=> " + msg.getResponseHeader().getPrimeHeader());
           String diffTimeString = "\t [" + decimalFormat.format((double) (msg.getTimeElapsedMillis()/1000.0)) + " s]";
           sb.append(diffTimeString);
       }
       
       return sb.toString();
       
   }
   
   public void setTag(String tag) {
       try {
           staticTableHistory.updateTag(historyId, tag);
       } catch (SQLException e) {
           e.printStackTrace();
       }
       
   }
}
