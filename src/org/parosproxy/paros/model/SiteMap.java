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
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.parosproxy.paros.network.HttpMalformedHeaderException;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpRequestHeader;
import org.parosproxy.paros.network.HttpStatusCode;


/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SiteMap extends DefaultTreeModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Model model;
    
    public static SiteMap createTree(Model model) {
        SiteNode root = new SiteNode("Sites");
        return new SiteMap(root, model);        
    }
    
    public SiteMap(SiteNode root, Model model) {        
        super(root);
        this.model = model;
    }

    /**
     * Return the a HttpMessage of the same type under the tree path.
     * @param msg
     * @return	null = not found
     */
    public synchronized HttpMessage pollPath(HttpMessage msg) {
        SiteNode resultNode = null;
        URI uri = msg.getRequestHeader().getURI();
        
        String scheme = null;
        String host = null;
        String path = null;
        int port = 80;
        SiteNode parent = (SiteNode) getRoot();
        StringTokenizer tokenizer = null;
        String folder = "";
        
        try {
            
            scheme = uri.getScheme();
            host = scheme + "://" + uri.getHost();
            port = uri.getPort();
            if (port != -1) {
                host = host + ":" + port;
            }
            
            // no host yet
            parent = findChild(parent, host);
            if (parent == null) {
                return null;
        	}
            
            path = uri.getPath();
            if (path == null) {
                path = "";
            }
                        
            tokenizer = new StringTokenizer(path, "/");
            while (tokenizer.hasMoreTokens()) {
                
                folder = tokenizer.nextToken();
                if (folder != null && !folder.equals("")) {
                    if (tokenizer.countTokens() == 0) {
                        String leafName = getLeafName(folder, msg);
                        resultNode = findChild(parent, leafName);
                    } else {
                        parent = findChild(parent, folder);
                        if (parent == null)
                            return null;
                    }
                    
                }
                
            }
        } catch (URIException e) {
            e.printStackTrace();
        }
        
        if (resultNode == null) {
            return null;
        }
        
        HttpMessage nodeMsg = null;
        try {
            nodeMsg = resultNode.getHistoryReference().getHttpMessage();
        } catch (Exception e) {
        }
        return nodeMsg;
    }
    
    /**
     * Add the HistoryReference into the SiteMap.
     * This method will rely on reading message from the History table.
     * @param ref
     */
    public synchronized void addPath(HistoryReference ref) {

        HttpMessage msg = null;
        try {
            msg = ref.getHttpMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
        addPath(ref, msg);
    }
    
    /**
     * Add the HistoryReference with the corresponding HttpMessage into the SiteMap.
     * This method saves the msg to be read from the reference table.  Use 
     * this method if the HttpMessage is known.
     * @param msg
     * @return 
     */
    public synchronized void addPath(HistoryReference ref, HttpMessage msg) {
        
        
        URI uri = msg.getRequestHeader().getURI();
        
        String scheme = null;
        String host = null;
        String path = null;
        int port = 80;
        SiteNode parent = (SiteNode) getRoot();
        StringTokenizer tokenizer = null;
        String folder = "";
        
        try {
            
            scheme = uri.getScheme();
            host = scheme + "://" + uri.getHost();
            port = uri.getPort();
            if (port != -1) {
                host = host + ":" + port;
            }
            
            // add host
            parent = findAndAddChild(parent, host, ref, msg);
            path = uri.getPath();
            
            if (path == null) {
                path = "";
            }
                        
            tokenizer = new StringTokenizer(path, "/");
            while (tokenizer.hasMoreTokens()) {
                
                folder = tokenizer.nextToken();
                if (folder != null && !folder.equals("")) {
                    if (tokenizer.countTokens() == 0) {
                        // leaf - path name
                        findAndAddLeaf(parent, folder, ref, msg);
                    } else {
                        parent = findAndAddChild(parent, folder, ref, msg);
                    }
                    
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
 
    
    private SiteNode findAndAddChild(SiteNode parent, String nodeName, HistoryReference baseRef, HttpMessage baseMsg) throws URIException, HttpMalformedHeaderException, NullPointerException, SQLException {
        SiteNode result = findChild(parent, nodeName);
        if (result == null) {
            SiteNode newNode = new SiteNode(nodeName);
            int pos = parent.getChildCount();
            for (int i=0; i< parent.getChildCount(); i++) {
                if (nodeName.compareTo(parent.getChildAt(i).toString()) < 0) {
                    pos = i;
                    break;
                }
            }
            insertNodeInto(newNode, parent, pos);
            result = newNode;
            result.setHistoryReference(createReference(result, baseRef, baseMsg));
            
        }
        return result;
    }
    
    private SiteNode findChild(SiteNode parent, String nodeName) {
        for (int i=0; i<parent.getChildCount(); i++) {
            SiteNode child = (SiteNode) parent.getChildAt(i);
            if (child.toString().equals(nodeName)) {
                return child;
            }
        }
        return null;
    }
    
    private SiteNode findAndAddLeaf(SiteNode parent, String nodeName, HistoryReference ref, HttpMessage msg) {

        String leafName = getLeafName(nodeName, msg);
        SiteNode node = findChild(parent, leafName);
        if (node == null) {
            node = new SiteNode(leafName);
            node.setHistoryReference(ref);
            int pos = parent.getChildCount();
            for (int i=0; i< parent.getChildCount(); i++) {
                if (leafName.compareTo(parent.getChildAt(i).toString()) < 0) {
                    pos = i;
                    break;
                }
            }

            insertNodeInto(node, parent, pos);
        } else {
           
            // do not replace if
            // - use local copy (304). only use if 200

            if (msg.getResponseHeader().getStatusCode() == HttpStatusCode.OK) {
                // replace node HistoryReference to this new child if this is a spidered record.
                node.setHistoryReference(ref);
                ref.setSiteNode(node);
            } else {
                node.getPastHistoryReference().add(ref);
                ref.setSiteNode(node);
            }
        }
        return node;
    }
    
    private String getLeafName(String nodeName, HttpMessage msg) {
        // add \u007f to make GET/POST node appear at the end.
        //String leafName = "\u007f" + msg.getRequestHeader().getMethod()+":"+nodeName;
        String leafName = msg.getRequestHeader().getMethod()+":"+nodeName;
        
        String query = "";

        try {
            query = msg.getRequestHeader().getURI().getQuery();
        } catch (URIException e) {
            e.printStackTrace();
        }
        if (query == null) {
            query = "";
        }
        leafName = leafName + getQueryParamString(msg.getParamNameSet(query));
        
        // also handle POST method query in body
        query = "";
        if (msg.getRequestHeader().getMethod().equalsIgnoreCase(HttpRequestHeader.POST)) {
            query = msg.getRequestBody().toString();
            leafName = leafName + getQueryParamString(msg.getParamNameSet(query));
        }
        
        return leafName;
        
    }
    
    private String getQueryParamString(Set<String> querySet) {
        StringBuffer sb = new StringBuffer();
        Iterator<String> iterator = querySet.iterator();
        for ( int i=0; iterator.hasNext(); i++ ) {
            String name = iterator.next();
            if (name == null)
                continue;
            if (i > 0)
                sb.append(',');
            sb.append(name);
        }

        String result = "";
        if (sb.length()>0) {
            result = "(" + sb.toString() + ")";
        } 
        
        return result;
    }
    
    private HistoryReference createReference(SiteNode node, HistoryReference baseRef, HttpMessage base) throws HttpMalformedHeaderException, SQLException, URIException, NullPointerException {
        TreeNode[] path = node.getPath();
        StringBuffer sb = new StringBuffer();
        for (int i=1; i<path.length; i++) {
            sb.append(path[i].toString());
            if (i<path.length-1) {
                sb.append('/');
            }
        }
        HttpMessage newMsg = base.cloneRequest();
        
        URI uri = new URI(sb.toString(), true);
        newMsg.getRequestHeader().setURI(uri);
        newMsg.getRequestHeader().setMethod(HttpRequestHeader.GET);
        newMsg.getRequestBody().setBody("");
        newMsg.getRequestHeader().setContentLength(0);
        
		//HistoryReference historyRef = new HistoryReference(model.getSession(), baseRef.getHistoryType(), newMsg);
		HistoryReference historyRef = new HistoryReference(model.getSession(), HistoryReference.TYPE_TEMPORARY, newMsg);
		
        return historyRef;
    }
}
