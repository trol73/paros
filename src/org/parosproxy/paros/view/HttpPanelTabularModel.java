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
package org.parosproxy.paros.view;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.table.AbstractTableModel;

/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class HttpPanelTabularModel extends AbstractTableModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String[] columnNames = {"Parameter Name", "Value"};
    private static final Pattern pSeparator	= Pattern.compile("([^=&]+)[=]([^=&]*)"); 
    private Vector<String[]> listPair = new Vector<String[]>();	// TODO LIST
    private boolean editable = true;
    
    /**
     * @return Returns the editable.
     */
    public boolean isEditable() {
        return editable;
    }
    /**
     * @param editable The editable to set.
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    /**
     * 
     */
    public HttpPanelTabularModel() {
        super();
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
        return 2;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        return listPair.size();
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int row, int col) {
        String[] cell = listPair.get(row);
        return cell[col];
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public synchronized void setText(String body) {
        listPair.clear();
        String name = null;
        String value = null;
        Matcher matcher = pSeparator.matcher(body);
//        int row = 0;
  	  	while (matcher.find()){
  	  	    String[] cell = new String[2];
  	  	    try {
                name = URLDecoder.decode(matcher.group(1),"8859_1");
      	  	    value = URLDecoder.decode(matcher.group(2),"8859_1");
      	  	    cell[0] = name;
      	  	    cell[1] = value;
      	  	    //System.out.println("name:" + name + " value:" + value);
      	  	    listPair.add(cell);
            } catch (UnsupportedEncodingException e) {
            } catch (IllegalArgumentException e) {
            }
  	  	}

  	  	fireTableDataChanged();
    }
    
    public synchronized String getText() {
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<listPair.size(); i++) {
            if (i > 0) sb.append('&');
            String[] cell = (String[]) listPair.get(i);
            try {
                sb.append(URLEncoder.encode(cell[0],"UTF8") + "=" + URLEncoder.encode(cell[1],"UTF8"));
            } catch (UnsupportedEncodingException e) {
            } catch (IllegalArgumentException e) {
            }
        }
        return sb.toString();
            
    }
    
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        
        return isEditable();
        
    }
    
    public void setValueAt(String value, int row, int col) {
        String[] cell = null;
        while (row >= listPair.size()) {
            cell = new String[2];
            cell[0] = "";
            cell[1] = "";
            listPair.add(cell);
        }
        
        cell = listPair.get(row);
        cell[col] = value;
        fireTableCellUpdated(row, col);
    }
}