/*
 * Created on May 29, 2004
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
package org.parosproxy.paros.network;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javax.net.ssl.SSLSocket;

/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class HttpInputStream extends BufferedInputStream {

	private static final int	BUFFER_SIZE = 4096;
	private static final String CRLF = "\r\n";
	private static final String CRLF2 = CRLF + CRLF;
	private static final String LF = "\n";
	private static final String LF2 = LF + LF;

//	private BufferedInputStream in = null;
	private byte[] mBuffer = new byte[BUFFER_SIZE];
	private Socket mSocket = null;
	
	public HttpInputStream(Socket socket) throws IOException {
	    super(socket.getInputStream(), BUFFER_SIZE);
		setSocket(socket);
		//this.in = new BufferedInputStream(mSocket.getInputStream());
	}
	
	public HttpRequestHeader readRequestHeader(boolean isSecure) throws HttpMalformedHeaderException, IOException {
		return new HttpRequestHeader(readHeader(), isSecure);
	}

    
	public synchronized String readHeader() throws IOException {
		String msg = "";
        int		oneByte = -1;
        boolean eoh = false;
        boolean neverReadOnce = true;
        StringBuffer sb = new StringBuffer(200);
        
        do {
            oneByte = super.read();
        	
        	if (oneByte == -1) {
        		eoh = true;
        		if (neverReadOnce) {
        			HttpUtil.sleep(50);
        			continue;
        		}
				break;
        	} else {
        		neverReadOnce = false;
        	}
            sb.append((char) oneByte);

            if (((char) oneByte) == '\n' && isHeaderEnd(sb)) {
                eoh = true;
                msg = sb.toString();
            }
		} while (!eoh || neverReadOnce);

        return msg;

	}

	/**
	 * Check if the current StringBuffer trailing characters is an HTTP header end (empty CRLF).
	 * @param sb
	 * @return true - if end of HTTP header.
	 */
	private boolean isHeaderEnd(StringBuffer sb) {
		int len = sb.length();
		if (len > 2) {
			if (LF2.equals(sb.substring(len-2))) {
				return true;
			}
		}
	
		if (len > 4) {
			if (CRLF2.equals(sb.substring(len-4))) {
				return true;
			}
		}
	
		return false;
	}

	/**
	 * Read Http body from input stream as a string basing on the content length on the method.
	 * @param method
	 * @return Http body
	 */
	public synchronized HttpBody readBody(HttpHeader httpHeader) {

		int contentLength = httpHeader.getContentLength();	// -1 = default to unlimited length until connection close
		int readBodyLength = 0;
		int len = 0;
		
		HttpBody body = (contentLength > 0) ? new HttpBody(contentLength) : new HttpBody();
		
		try {
			while (contentLength == -1 || readBodyLength < contentLength) {
				len = readBody(contentLength, readBodyLength, mBuffer);	// use mBuffer to avoid locally create too many data buffer
                if (len > 0) {
					readBodyLength += len;
				}
				body.append(mBuffer, len);
			}
		} catch (IOException e) {
			// read until IO error occur - eg connection close
		}
		
        
		return body;
	}

	/**
	 * 
	 * @param contentLength		Content length read to be read.  -1 = unlimited until connection close.
	 * @param readBodyLength 	Body length read so far
	 * @param data				Buffer storing the read bytes.
	 * @return					Numfer of bytes read in buffer
	 * @throws IOException
	 */
	private int readBody(int contentLength, int readBodyLength, byte[] buffer) throws IOException {

		int len = 0;
		int remainingLen = 0;

		if (contentLength == -1) {
//			len = in.read(buffer);
			len = super.read(buffer);

		} else {
			remainingLen = contentLength - readBodyLength;
			if (remainingLen < buffer.length && remainingLen > 0) {
//				len = in.read(buffer,0,remainingLen);
				len = super.read(buffer,0,remainingLen);

			} else if (remainingLen > buffer.length) {
//				len = in.read(buffer);
				len = super.read(buffer);

			}

		}

		return len;
	}
	
	public void setSocket(Socket socket) {
		mSocket = socket;
	}

	public int available() throws IOException {
		int avail = 0;
//		int oneByte = -1;
		int timeout = 0;

		avail = super.available();

		if (avail == 0 && mSocket != null && mSocket instanceof SSLSocket) {
			try {
				timeout = mSocket.getSoTimeout();
				mSocket.setSoTimeout(1);
				super.mark(256);
				super.read();
				super.reset();
				avail = super.available();
			} catch (SocketTimeoutException e) {
				avail = 0;
			} finally {
				mSocket.setSoTimeout(timeout);
			}
		}
		
		return avail;
	}
	
	public int read() throws IOException {
		//return in.read();
		return super.read();

	}
	public int read(byte[] b) throws IOException {

		return super.read(b);

	}
	
	public int read(byte[] b, int off, int len) throws IOException {

		return super.read(b, off, len);

	}

	public void close() {
		try {

		    super.close();
		} catch (Exception e) {
			
		}
	}
}
