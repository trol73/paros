/*
 * Created on 22 Jun 2004.
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
package org.parosproxy.paros.network;

import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;


/**
 * Representation of a HTTP message request (header and body) and response (header and body) pair.
 * 
 */
public class HttpMessage {

	private static Pattern staticPatternParam = Pattern.compile("&", Pattern.CASE_INSENSITIVE);

	private HttpRequestHeader mReqHeader = new HttpRequestHeader();
	private HttpBody mReqBody = new HttpBody();
	private HttpResponseHeader mResHeader = new HttpResponseHeader();
	private HttpBody mResBody = new HttpBody();
	private Object userObject;
	private int timeElapsed = 0;
	private long timeSent = 0;
    private String tag = "";

    /**
	 * Constructor for a empty HTTP message.
	 *
	 */
	public HttpMessage() {
	}
	
	public HttpMessage(URI uri) throws HttpMalformedHeaderException {
	    setRequestHeader(new HttpRequestHeader(HttpRequestHeader.GET, uri, HttpHeader.HTTP11));
	}

	public HttpMessage(HttpRequestHeader reqHeader) {
	    setRequestHeader(reqHeader);
	}
	
	public HttpMessage(HttpRequestHeader reqHeader, HttpBody reqBody) {
		setRequestHeader(reqHeader);
		setRequestBody(reqBody);	    
	}

	/**
	 * Constructor for a HTTP message with given request and response pair.
	 * @param reqHeader
	 * @param reqBody
	 * @param resHeader
	 * @param resBody
	 */
	public HttpMessage(HttpRequestHeader reqHeader, HttpBody reqBody,
			HttpResponseHeader resHeader, HttpBody resBody) {
		setRequestHeader(reqHeader);
		setRequestBody(reqBody);
		setResponseHeader(resHeader);
		setResponseBody(resBody);		
	}
	
	public HttpMessage(String reqHeader, String reqBody, String resHeader, String resBody) throws HttpMalformedHeaderException {
		setRequestHeader(reqHeader);
		setRequestBody(reqBody);
		if (resHeader != null && !resHeader.equals("")) {
		    setResponseHeader(resHeader);
		    setResponseBody(resBody);
		}
	}

	/**
	 * Get the request header of this message.
	 * @return
	 */
	public HttpRequestHeader getRequestHeader() {
		return mReqHeader;
	}
	
	/**
	 * Set the request header of this message.
	 * @param reqHeader
	 */
	public void setRequestHeader(HttpRequestHeader reqHeader) {
		mReqHeader = reqHeader;
	}

	/**
	 * Get the response header of this message.
	 * @return response.  if getResponseHeader().toString() is "" then it has not been read yet.
	 */
	public HttpResponseHeader getResponseHeader() {
		return mResHeader;
	}

	/**
	 * Set the response header of this message.
	 * @param resHeader
	 */
	public void setResponseHeader(HttpResponseHeader resHeader) {
		mResHeader = resHeader;
	}

	/**
	 * Get the requeset body of this message.
	 * @return.  Null = response header not exist yet.
	 */
	public HttpBody getRequestBody() {
		return mReqBody;
	}

	/**
	 * Set the request body of this message.
	 * @param reqBody
	 */
	public void setRequestBody(HttpBody reqBody) {
		mReqBody = reqBody;
	}

	/**
	 * Get the response body of this message.
	 * @return
	 */
	public HttpBody getResponseBody() {
		return mResBody;
	}

	/**
	 * Set the response body of this message.
	 * @param resBody
	 */
	public void setResponseBody(HttpBody resBody) {
		mResBody = resBody;
	    getResponseBody().setCharset(getResponseHeader().getCharset());

	}
	
	public void setRequestHeader(String reqHeader) throws HttpMalformedHeaderException {
		HttpRequestHeader newHeader = new HttpRequestHeader(reqHeader);
		setRequestHeader(newHeader);
	}
	
	public void setResponseHeader(String resHeader) throws HttpMalformedHeaderException {
		HttpResponseHeader newHeader = new HttpResponseHeader(resHeader);
		setResponseHeader(newHeader);

	}

	public void setRequestBody(String body) {
		getRequestBody().setBody(body);
	    getRequestBody().setCharset(getRequestHeader().getCharset());

	}

	public void setResponseBody(String body) {
	    if (mReqBody == null) {
	        mReqBody = new HttpBody();
	    }
		getResponseBody().setBody(body);
	    getResponseBody().setCharset(getResponseHeader().getCharset());

	}

	/**
	 * Compare if 2 message is the same.  2 messages are the same if:
	 * Host, port, path and query param and VALUEs are the same.  For POST request, the body must be the same.
	 * @param msg
	 * @return
	 */
	public boolean equals(Object object) {

	    if (!(object instanceof HttpMessage)) {
	        return false;
	    }
	    
	    HttpMessage msg = (HttpMessage) object;
	    boolean result = false;
	    
	    // compare method
	    if (!this.getRequestHeader().getMethod().equalsIgnoreCase(msg.getRequestHeader().getMethod())) {
	        return false;
	    }
	    
	    // compare host, port and URI
	    URI uri1 = this.getRequestHeader().getURI();
	    URI uri2 = msg.getRequestHeader().getURI();

	    
	    try {
            if (uri1.getHost() == null || uri2.getHost() == null || !uri1.getHost().equalsIgnoreCase(uri2.getHost())) {
                return false;
            }
            
            if (uri1.getPort() != uri2.getPort()) {
                return false;
            }
            
            String pathQuery1 = uri1.getPathQuery();
            String pathQuery2 = uri2.getPathQuery();

            if (pathQuery1 == null && pathQuery2 == null) {
                return true;
            } else if (pathQuery1 != null && pathQuery2 != null) {
                return pathQuery1.equalsIgnoreCase(pathQuery2);
            } else if (pathQuery1 == null || pathQuery2 == null) {
                return false;
            }
            
            if (this.getRequestHeader().getMethod().equalsIgnoreCase(HttpRequestHeader.POST)) {
                return this.getRequestBody().toString(HttpBody.STORAGE_CHARSET).equalsIgnoreCase(msg.getRequestBody().toString(HttpBody.STORAGE_CHARSET));
            }
            
            result = true;
            
        } catch (URIException e) {
            try {
                result = this.getRequestHeader().getURI().toString().equalsIgnoreCase(msg.getRequestHeader().getURI().toString());
            } catch (Exception e1) {}
        }

        return result;
	}

	/**
	 * 2 messages are equal type if the host, port, path and query names are equal.
	 * Even though the query value may differ.
	 * @param msg
	 * @return
	 */
	public boolean equalType(HttpMessage msg) {
	    boolean result = false;

	    // compare method
	    if (!this.getRequestHeader().getMethod().equalsIgnoreCase(msg.getRequestHeader().getMethod())) {
	        return false;
	    }
	    
	    // compare host, port and URI
	    URI uri1 = this.getRequestHeader().getURI();
	    URI uri2 = msg.getRequestHeader().getURI();

	    
	    try {
            if (uri1.getHost() == null || uri2.getHost() == null || !uri1.getHost().equalsIgnoreCase(uri2.getHost())) {
                return false;
            }
            
            if (uri1.getPort() != uri2.getPort()) {
                return false;
            }
            
            String path1 = uri1.getPath();
            String path2 = uri2.getPath();

            if (path1 == null && path2 == null) {
                return true;
            }
            
            if (path1 != null && path2 != null && !path1.equalsIgnoreCase(path2)) {
                return false;
            } else {
                if (path1 == null || path2 == null) {
                    return false;
                }
            }

            if (!queryEquals(msg)) {
                return false;
            }
            
            result = true;
            
        } catch (URIException e) {
            e.printStackTrace();
        }
        
	    return result;
	}
	
	private boolean queryEquals(HttpMessage msg) throws URIException {
	    boolean result = false;
	    
	    URI uri1 = this.getRequestHeader().getURI();
	    URI uri2 = msg.getRequestHeader().getURI();

	    String query1 = "";
	    String query2 = "";

	    
        // compare the URI query part.  2 msg is consider same param set here.
        if (uri1.getQuery() != null) query1 = uri1.getQuery();
        if (uri2.getQuery() != null) query2 = uri2.getQuery();

        Set<String> set1 = getParamNameSet(query1);
        Set<String> set2 = getParamNameSet(query2);

	    if (!set1.equals(set2)) {
	        return false;
	    }


        // compare here if this is a POST
        //the POST body part must also be the same set
	    if (getRequestHeader().getMethod().equalsIgnoreCase(HttpRequestHeader.POST)) {
	        
	        query1 = this.getRequestBody().toString(HttpBody.STORAGE_CHARSET);
	        query2 = msg.getRequestBody().toString(HttpBody.STORAGE_CHARSET);
	        set1 = getParamNameSet(query1);
		    set2 = getParamNameSet(query2);	        

		    if (!set1.equals(set2)) {
		        return false;
		    }
	    }

	    result = true;
	    

	    
	    return result;
	}
	
	public TreeSet<String> getParamNameSet(String params) {
		TreeSet<String> set = new TreeSet<String>();
	    String[] keyValue = staticPatternParam.split(params);
		int pos = 0;
		for (int i=0; i<keyValue.length; i++) {
			String key;
			pos = keyValue[i].indexOf('=');
			try {
				if (pos > 0) {
					// param found
					key = keyValue[i].substring(0,pos);
					//value = keyValue[i].substring(pos+1);
					//!!! note: this means param not separated by & and = is not parsed
				} else {
					key = null;
					//value = null;
				}
				
				set.add(key);
			} catch (Exception e) {
			}
		}
		
		return set;
	}
	
    /**
     * @return Returns the userObject.
     */
    public Object getUserObject() {
        return userObject;
    }
    /**
     * @param userObject The userObject to set.
     */
    public void setUserObject(Object userObject) {
        this.userObject = userObject;
    }
    
    public HttpMessage cloneAll() {
        HttpMessage newMsg = cloneRequest();
        
        if (!this.getResponseHeader().isEmpty()) {
            try {
                newMsg.getResponseHeader().setMessage(this.getResponseHeader().toString());
            } catch (HttpMalformedHeaderException e) {
            }
            newMsg.setResponseBody(this.getResponseBody().toString(HttpBody.STORAGE_CHARSET));
        }

        return newMsg;
    }
    
    public HttpMessage cloneRequest() {
        HttpMessage newMsg = new HttpMessage();
        if (!this.getRequestHeader().isEmpty()) {
            try {
                newMsg.getRequestHeader().setMessage(this.getRequestHeader().toString());
            } catch (HttpMalformedHeaderException e) {
                e.printStackTrace();
            }
            newMsg.setRequestBody(this.getRequestBody().toString(HttpBody.STORAGE_CHARSET));
        }
        return newMsg;
    }
    /**
     * @return Get the elapsed time (time difference) between the request is sent and all response is received.  In millis.
     * The value is zero if the response is not received.
     */
    public int getTimeElapsedMillis() {
        return timeElapsed;
    }

    /**
     * Set the elapsed time (time difference) between the request is sent and all response is received.  In millis.
     * @param timeElapsed
     */
    public void setTimeElapsedMillis(int timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    /**
     * Get the starting time when the request is going to be sent.  This is the System.currentTimeMillis before the message is sent.
     * The value is zero if the request is not sent yet.
     * @param timeElapsed
     */
    public long getTimeSentMillis() {
        return timeSent;
    }
    /**
     * Set the time when the request is sent.
     * @param timeSent The timeSent to set.
     */
    public void setTimeSentMillis(long timeSent) {
        this.timeSent = timeSent;
    }

    /**
     * @return Returns the tag.
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag The tag to set.
     */
    public void setTag(String tag) {
        this.tag = tag;
    }
}
