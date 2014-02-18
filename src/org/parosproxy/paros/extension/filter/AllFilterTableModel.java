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

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class AllFilterTableModel extends DefaultTableModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String[] columnNames = {"Filter Name", "Enabled", ""};
    private List<Filter> allFilters;
    
    /**
     * @param allPlugins The allPlugins to set.
     */
    private void setAllFilters(List<Filter> allFilters) {
        this.allFilters = allFilters;
    }
    /**
     * 
     */
    public AllFilterTableModel() {
        allFilters = new ArrayList<Filter>();
    }
    
    public void setTable(List<Filter> allFilters) {
        setAllFilters(allFilters);
        fireTableDataChanged();        
    }

    public Class<?> getColumnClass(int c) {
        if (c == 1) {
            return Boolean.class;
        }
        return String.class;
        
    }
    
    public String getColumnName(int col) {
        return columnNames[col];
    }
    
    public boolean isCellEditable(int row, int col) {
        boolean result = false;
        Filter filter = (Filter) getAllFilters().get(row);
        switch (col) {
        	case 0:	result = false;
        			break;
        	case 1: result = true;
        			break;
        	case 2: if (filter.isPropertyExists()) {
        	    		result = true;
        			}	 else {
        			    result = false;
        			}
        }
        return result;
    }
    
    public void setValueAt(Object value, int row, int col) {
        Filter filter = allFilters.get(row);
        switch (col) {
        	case 0:	break;
        	case 1: filter.setEnabled(((Boolean) value).booleanValue());
        			break;
        	case 2: break;
        }
        fireTableCellUpdated(row, col);
    }
    
    public int getColumnCount() {
        return 3;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        return getAllFilters().size();
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int row, int col) {
        Object result = null;
        Filter filter = (Filter) getAllFilters().get(row);
        switch (col) {
        	case 0:	result = filter.getName();
        			break;
        	case 1: result = new Boolean(filter.isEnabled());
        			break;
        	case 2: if (filter.isPropertyExists()) {
        	    		result = "...";
        			} else {
        			    result = "";
        			}
        			break;
        	default: result = "";
        }
        return result;
    }
    
    void setAllFilterEnabled(boolean enabled) {
        for (int i=0; i<getAllFilters().size(); i++) {
            Filter filter = (Filter) getAllFilters().get(i);
            filter.setEnabled(enabled);            
        }
        fireTableDataChanged();        

    }
    
    /**
     * @return Returns the allFilters.
     */
    public List<Filter> getAllFilters() {
        if (allFilters == null) {
            allFilters = new ArrayList<Filter>();
        }
        return allFilters;
    }
}


