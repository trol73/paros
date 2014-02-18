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
package org.parosproxy.paros.core.scanner;

import org.parosproxy.paros.db.RecordAlert;
import org.parosproxy.paros.extension.report.ReportGenerator;
import org.parosproxy.paros.model.HistoryReference;
import org.parosproxy.paros.network.HttpMessage;


public class Alert implements Comparable<Alert>  {

	public static final int RISK_INFO 	= 0;
	public static final int RISK_LOW 	= 1;
	public static final int RISK_MEDIUM = 2;
	public static final int RISK_HIGH 	= 3;

	public static final int SUSPICIOUS = 0;
	public static final int WARNING = 1;
	
	public static final String MSG_RISK[] = {"Informational", "Low", "Medium", "High"};
	public static final String MSG_RELIABILITY[] = {"Suspicious", "Warning"};
	
	private int		alertId = 0;
	private int		pluginId = 0;
	private String 	alert = "";
	private int risk = RISK_INFO;
	private int reliability = WARNING;
	private String 	description = "";
	private String 	uri = "";
	private String 	param = "";
	private String 	otherInfo = "";
	private String 	solution = "";
	private String	reference = "";
	private HttpMessage message = null;
	
	public Alert(int pluginId) {
		this.pluginId = pluginId;
		
	}
	
	public Alert(int pluginId, int risk, int reliability, String alert) {
		this(pluginId);
		setRiskReliability(risk, reliability);
		setAlert(alert);
	}

	public Alert(RecordAlert recordAlert) {
	    this(recordAlert.getPluginId(), recordAlert.getRisk(), recordAlert.getReliability(), recordAlert.getAlert());
	    HistoryReference ref = null;
        try {
            ref = new HistoryReference(recordAlert.getHistoryId());
            setDetail(recordAlert.getDescription(), recordAlert.getUri(), recordAlert.getParam(), recordAlert.getOtherInfo(), recordAlert.getSolution(), recordAlert.getReference(), ref.getHttpMessage());

        } catch (Exception e) {
        }
	    
	}
	
	public void setRiskReliability(int risk, int reliability) {
		this.risk = risk;
		this.reliability = reliability;
	}
	
	public void setAlert(String alert) {
	    if (alert == null) return;
	    this.alert = new String(alert);
	}
	
	

	public void setDetail(String description, String uri, String param, String otherInfo, String solution, String reference, HttpMessage msg) {
		setDescription(description);
		setUri(uri);
		setParam(param);
		setOtherInfo(otherInfo);
		setSolution(solution);
		setReference(reference);
		setMessage(msg);
	}

	public void setUri(String uri) {
		this.uri = new String(uri);
	}
	
	
	public void setDescription(String description) {
	    if (description == null) return;
		this.description = new String(description);
	}
	
	public void setParam(String param) {
	    if (param == null) return;
		this.param = new String(param);
	}
	
	
	public void setOtherInfo(String otherInfo) {
	    if (otherInfo == null) return;
		this.otherInfo = new String(otherInfo);
	}

	public void setSolution(String solution) {
	    if (solution == null) return;
		this.solution = new String(solution);
	}

	public void setReference(String reference) {
	    if (reference == null) return;
		this.reference = new String(reference);
	}

	public void setMessage(HttpMessage message) {
	    if (message != null) {
	        this.message = message.cloneAll();
	    } else {
	        this.message = message;
	    }
	}
	
	public int compareTo(Alert alert2) throws ClassCastException {
		int result = 0;
		
		if (risk < alert2.risk) {
			return -1;
		} else if (risk > alert2.risk) {
			return 1;
		}
		
		if (reliability < alert2.reliability) {
			return -1;
		} else if (reliability > alert2.reliability) {
			return 1;
		}
		
		result = alert.compareToIgnoreCase(alert2.alert);
		if (result != 0) {
			return result;
		}
		
		result = alert.compareToIgnoreCase(alert2.uri);
		if (result != 0) {
			return result;
		}
		
		result = alert.compareToIgnoreCase(alert2.param);
		if (result != 0) {
			return result;
		}
		
		return otherInfo.compareToIgnoreCase(alert2.otherInfo);
	} 


	/**
	Override equals.  Alerts are equal if the alert, uri and param is the same.
	*/
	public boolean equals(Object obj) {
		Alert item = null;
		if (obj instanceof Alert) {
			item = (Alert) obj;
			if ((pluginId == item.pluginId) && uri.equalsIgnoreCase(item.uri)
				&& param.equalsIgnoreCase(item.param) && otherInfo.equalsIgnoreCase(item.otherInfo)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	Create a new instance of AlertItem with same members.
	*/
	public Alert newInstance() {
		Alert item = new Alert(this.pluginId);
		item.setRiskReliability(this.risk, this.reliability);
		item.setAlert(this.alert);
		item.setDetail(this.description, this.uri, this.param, this.otherInfo, this.solution, this.reference, this.message);
		return item;
	}
	
	public String toPluginXML(String urls) {
		StringBuffer sb = new StringBuffer(150);
		sb.append("<alertitem>\r\n");
		sb.append("  <pluginid>" + pluginId + "</pluginid>\r\n");
		sb.append("  <alert>" + alert + "</alert>\r\n");
		sb.append("  <riskcode>" + risk + "</riskcode>\r\n");
		sb.append("  <reliability>" + reliability + "</reliability>\r\n");
		sb.append("  <riskdesc>" + replaceEntity(MSG_RISK[risk] + " (" + MSG_RELIABILITY[reliability] + ")") + "</riskdesc>\r\n");
        sb.append("  <desc>" + paragraph(replaceEntity(description)) + "</desc>\r\n");

        sb.append(urls);

        sb.append("  <solution>" + paragraph(replaceEntity(solution)) + "</solution>\r\n");
		sb.append("  <reference>" + paragraph(replaceEntity(reference)) + "</reference>\r\n");
		
		sb.append("</alertitem>\r\n");
		return sb.toString();
	}
   
	public String replaceEntity(String text) {
		String result = null;
		if (text != null) {
			result = ReportGenerator.entityEncode(text);
		}
		return result;
	}
	
	public String paragraph(String text) {
		String result = null;
		result = "<p>" + text.replaceAll("\\r\\n","</p><p>").replaceAll("\\n","</p><p>") + "</p>";
        result = result.replaceAll("&lt;ul&gt;", "<ul>").replaceAll("&lt;/ul&gt;", "</ul>").replaceAll("&lt;li&gt;", "<li>").replaceAll("&lt;/li&gt;", "</li>");
        //result = text.replaceAll("\\r\\n","<br/>").replaceAll("\\n","<br/>");

        return result;
	}
    
    private String breakNoSpaceString(String text) {
        String result = null;
        result = text.replaceAll("&amp;","&amp;<wbr/>");
        return result;
        
    }
		
    /**
     * @return Returns the alert.
     */
    public String getAlert() {
        return alert;
    }
    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }
    /**
     * @return Returns the id.
     */
    public int getPluginId() {
        return pluginId;
    }
    /**
     * @return Returns the message.
     */
    public HttpMessage getMessage() {
        return message;
    }
    /**
     * @return Returns the otherInfo.
     */
    public String getOtherInfo() {
        return otherInfo;
    }
    /**
     * @return Returns the param.
     */
    public String getParam() {
        return param;
    }
    /**
     * @return Returns the reference.
     */
    public String getReference() {
        return reference;
    }
    /**
     * @return Returns the reliability.
     */
    public int getReliability() {
        return reliability;
    }
    /**
     * @return Returns the risk.
     */
    public int getRisk() {
        return risk;
    }
    /**
     * @return Returns the solution.
     */
    public String getSolution() {
        return solution;
    }
    /**
     * @return Returns the uri.
     */
    public String getUri() {
        return uri;
    }
    /**
     * @return Returns the alertId.
     */
    public int getAlertId() {
        return alertId;
    }
    /**
     * @param alertId The alertId to set.
     */
    public void setAlertId(int alertId) {
        this.alertId = alertId;
    }
    
    public String getUrlParamXML() {
        StringBuffer sb = new StringBuffer(200);
        sb.append("  <uri>" + breakNoSpaceString(replaceEntity(uri)) + "</uri>\r\n");
        sb.append("  <param>" + breakNoSpaceString(replaceEntity(param)) + "</param>\r\n");
        sb.append("  <otherinfo>" + breakNoSpaceString(replaceEntity(otherInfo)) + "</otherinfo>\r\n");
        return sb.toString();
    }
}	


