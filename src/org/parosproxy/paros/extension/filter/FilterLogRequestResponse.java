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
package org.parosproxy.paros.extension.filter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.parosproxy.paros.network.HttpMessage;


/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class FilterLogRequestResponse extends FilterAdaptor {

    private static final String logFile = "filter/message.txt";
    private static final String delim = "====================================";   
    private static final String CRLF = "\r\n";
    private File outFile = new File(logFile);
    private BufferedWriter writer = null;
    private long lastWriteTime = System.currentTimeMillis();
    private int counter = 1;
    
    /* (non-Javadoc)
     * @see com.proofsecure.paros.extension.filter.AbstractFilter#getId()
     */
    public int getId() {
        return 40;
    }

    /* (non-Javadoc)
     * @see com.proofsecure.paros.extension.filter.AbstractFilter#getName()
     */
    public String getName() {
        return "Log request and response into file (" + logFile + ")";
    }

    /* (non-Javadoc)
     * @see com.proofsecure.paros.core.proxy.ProxyListener#onHttpRequestSend(com.proofsecure.paros.network.HttpMessage)
     */
    public void onHttpRequestSend(HttpMessage httpMessage) {

    }

    /* (non-Javadoc)
     * @see com.proofsecure.paros.core.proxy.ProxyListener#onHttpResponseReceive(com.proofsecure.paros.network.HttpMessage)
     */
    public void onHttpResponseReceive(HttpMessage httpMessage) {

        if (!httpMessage.getRequestHeader().isText() || httpMessage.getRequestHeader().isImage() || httpMessage.getResponseHeader().isImage()) {
            return;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(httpMessage.getRequestHeader().toString());
        sb.append(httpMessage.getRequestBody().toString() + CRLF);
        
        if (!httpMessage.getResponseHeader().isEmpty()) {
            sb.append(httpMessage.getResponseHeader().toString());
            sb.append(httpMessage.getResponseBody().toString() + CRLF);
        }
        
        writeLogFile(sb.toString());
    }
    
    private synchronized void writeLogFile(String line) {
        try{
            
            if (getWriter() != null) {
                getWriter().write("===== " + counter + " " + delim + CRLF);
                getWriter().write(line + CRLF);
                counter++;
            }
            // avoid close file frequently
            //getWriter().close();
        }catch(Exception e){
        }
        
    }
    
    public synchronized void timer() {
        if (writer != null && System.currentTimeMillis() > lastWriteTime + 5000) {
            // 5s elapse and no more write.  close file.

            try {
                writer.close();
                writer = null;
            } catch (IOException e) {
            }            
        }
    }
    
    private synchronized BufferedWriter getWriter() throws IOException {
        if (writer == null) {
            writer = new BufferedWriter(new FileWriter(outFile,true));            
        }
        return writer;
    }
    
    

}
