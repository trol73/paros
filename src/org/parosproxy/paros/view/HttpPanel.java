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

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.parosproxy.paros.extension.AbstractPanel;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.utils.I18N;
import org.parosproxy.paros.view.viewers.HexViewer;
import org.parosproxy.paros.view.viewers.ImageViewer;
import org.parosproxy.paros.view.viewers.TableViewer;
import org.parosproxy.paros.view.viewers.TextViewer;

/**
 *
 * Panel to display HTTP request/response with headers and body.
 * 
 * Future: to support different view.
 * 
 */
public class HttpPanel extends AbstractPanel {
	private static final long serialVersionUID = 1L;
	
	private static final String VIEW_TEXT = I18N.get("httppanel.textView");
    private static final String VIEW_TABULAR = I18N.get("httppanel.tabularView");
    private static final String VIEW_IMAGE = I18N.get("httppanel.imageView");
    private static final String VIEW_HEX = I18N.get("httppanel.hexView");
    
	private JSplitPane splitVert;
	private JPanel panelView;
	private JPanel jPanel;
	private JComboBox cbViewType;
	private JPanel panelOption;
	private String currentView = VIEW_TEXT;
	
	private ImageViewer viewerImage = new ImageViewer(VIEW_IMAGE);
	private TextViewer viewerText = new TextViewer(VIEW_TEXT);
	private TableViewer viewerTable = new TableViewer(VIEW_TABULAR);
	private HexViewer viewerHex = new HexViewer(VIEW_HEX);
	
	private TextViewer viewerHeaders = new TextViewer("");
	
		
	/**
	 * This is the default constructor. 
	 * Creates a read only HttpPanel
	 */
	public HttpPanel() {
		super();
		
		GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();

		setLayout(new GridBagLayout());
		setSize(403, 296);
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.weightx = 1.0;
		gridBagConstraints1.weighty = 1.0;
		gridBagConstraints1.fill = GridBagConstraints.BOTH;
		gridBagConstraints1.ipadx = 0;
		gridBagConstraints1.ipady = 0;
		gridBagConstraints4.anchor = GridBagConstraints.SOUTHWEST;
		gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints4.gridx = 0;
		gridBagConstraints4.gridy = 1;
		gridBagConstraints4.weightx = 1.0D;
		
		splitVert = new JSplitPane();
		splitVert.setDividerLocation(220);
		splitVert.setDividerSize(3);
		splitVert.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitVert.setPreferredSize(new Dimension(400, 400));
		splitVert.setResizeWeight(0.5D);
		
		
		splitVert.setTopComponent(viewerHeaders.create());
		splitVert.setContinuousLayout(false);
		panelView = new JPanel();
		panelView.setLayout(new CardLayout());
		panelView.setPreferredSize(new Dimension(278, 10));
		panelView.add(viewerText.create(), viewerText.getName());
		panelView.add(viewerImage.create(), viewerImage.getName());
		panelView.add(viewerTable.create(), viewerTable.getName());
		panelView.add(viewerHex.create(), viewerHex.getName());

		splitVert.setBottomComponent(panelView);
		
		show(VIEW_TEXT);

		add(splitVert, gridBagConstraints1);
		add(getJPanel(), gridBagConstraints4);
	}
	
	/**
	 * 
	 * Creates a read only or editable HttpPanel
	 * @param isEditable
	 */
	public HttpPanel(boolean isEditable) {
		this();
		viewerHeaders.setEditable(isEditable);
		viewerText.setEditable(isEditable);
		viewerHex.setEditable(isEditable);
		viewerTable.setEditable(isEditable);
	}


	
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 0;
			gridBagConstraints5.weightx = 0.0D;
			gridBagConstraints5.fill = GridBagConstraints.NONE;
			gridBagConstraints5.ipadx = 0;
			gridBagConstraints5.anchor = GridBagConstraints.WEST;
			gridBagConstraints5.insets = new Insets(2, 0, 2, 0);
			gridBagConstraints6.anchor = GridBagConstraints.SOUTHEAST;
			gridBagConstraints6.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints6.gridx = 2;
			gridBagConstraints6.gridy = 0;
			gridBagConstraints6.weightx = 1.0D;
			gridBagConstraints7.gridx = 1;
			gridBagConstraints7.gridy = 0;
			gridBagConstraints7.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints7.anchor = GridBagConstraints.WEST;
			
			JLabel jLabel = new JLabel();
			jLabel.setText("      ");
			
			panelOption = new JPanel();
			panelOption.setLayout(new CardLayout());

			
			jPanel.add(getComboView(), gridBagConstraints5);
			jPanel.add(jLabel, gridBagConstraints7);
			jPanel.add(panelOption, gridBagConstraints6);
		}
		return jPanel;
	}
	
	
	/**
	 * This method initializes comboView	
	 * 	
	 * @return JComboBox	
	 */    
	private JComboBox getComboView() {
		if (cbViewType == null) {
			cbViewType = new JComboBox();
			cbViewType.setSelectedIndex(-1);
			cbViewType.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
				    String item = (String) cbViewType.getSelectedItem();
				    if ( item == null || item.equals(currentView) ) {
				        return;		// no change
				    }
			        if ( currentView.equals(VIEW_TABULAR) ) {
			            // do not use getTxtBody() here to avoid setting text
                        String s = viewerTable.getText();
				        if ( s != null && s.length() > 0 ) {
                            // set only if model is not empty because binary data not work for tabularModel
				        	viewerText.loadData(s);
                        }
				    }
				    if ( item.equals(VIEW_TABULAR) ) {
				    	viewerTable.loadData(viewerText.getText());

				    }
				    currentView = item;
				    show(item);
				}
			});

			cbViewType.addItem(VIEW_TEXT);
			cbViewType.addItem(VIEW_TABULAR);
		}
		return cbViewType;
	}

	
	private void show(String viewName) {
		CardLayout card = (CardLayout) panelView.getLayout();
		card.show(panelView, viewName);
	}
	
	public void setMessage(String header, String body, boolean enableViewSelect) {
	    getComboView().setEnabled(enableViewSelect);

        validate();
        if ( enableViewSelect ) {
        	viewerTable.loadData(body);
	    } else {
		    getComboView().setSelectedItem(VIEW_TEXT);
		    currentView = VIEW_TEXT;

	        show(VIEW_TEXT);
	        viewerTable.clearData();
	    }
        viewerHeaders.loadData(header);
        viewerText.loadData(body);
	}
	
	public void setMessage(HttpMessage msg, boolean isRequest) {
	    getComboView().removeAllItems();
	    getComboView().setEnabled(false);
	    getComboView().addItem(VIEW_TEXT);
	    getComboView().addItem(VIEW_HEX);
	    if ( msg == null ) {
	        // perform clear display
	    	viewerHeaders.clearData();
		    viewerText.clearData();

	        getComboView().setSelectedItem(VIEW_TEXT);
		    currentView = VIEW_TEXT;

	        show(VIEW_TEXT);
	        viewerTable.clearData();
	        return;
	    }
    
	    if (isRequest) {
	        setDisplayRequest(msg);
	    } else {
	        setDisplayResponse(msg);
	    }
        validate();
	}

	private void setDisplayRequest(HttpMessage msg) {
	    String header = replaceHeaderForJTextArea(msg.getRequestHeader().toString());
	    String body = msg.getRequestBody().toString();
	    
	    viewerHex.loadData(body);
	    
	    viewerTable.loadData(msg.getRequestBody().toString());

	    viewerHeaders.loadData(header);

        viewerText.loadData(body);

        getComboView().addItem(VIEW_TABULAR);
	    getComboView().setEnabled(true);

	}
	
	private void setDisplayResponse(HttpMessage msg) {
	    if (msg.getResponseHeader().isEmpty()) {
	    	viewerHeaders.clearData();
	        viewerText.clearData();
	        viewerImage.clearData();
	        return;
	    }
	    
	    String header = replaceHeaderForJTextArea(msg.getResponseHeader().toString());
	    String body = msg.getResponseBody().toString();
	    
	    viewerHex.loadData(body);
	    viewerHeaders.loadData(header);
	    viewerText.loadData(body);

        getComboView().removeAllItems();
        getComboView().addItem(VIEW_TEXT);
        getComboView().addItem(VIEW_HEX);

	    getComboView().setEnabled(true);

	    if (msg.getResponseHeader().isImage()) {
	        getComboView().addItem(VIEW_IMAGE);
	        viewerImage.loadData(msg.getResponseBody().getBytes());
	    }
	    
	    if (msg.getResponseHeader().isImage()) {
		    getComboView().setSelectedItem(VIEW_IMAGE);	        
	    } else {
		    getComboView().setSelectedItem(VIEW_TEXT);	        	        
	    }

	    
	}
	
	private String getHeaderFromJTextArea() {
		String msg = viewerHeaders.getText();
		String result = msg.replaceAll("\\n", "\r\n");
		result = result.replaceAll("(\\r\\n)*\\z", "") + "\r\n\r\n";
		return result;
	}
	
	private String replaceHeaderForJTextArea(String msg) {
		return msg.replaceAll("\\r\\n", "\n");
	}
	
	
	public void getMessage(HttpMessage msg, boolean isRequest) {
	    try {
	        if (isRequest) {
	            if ( isHeadersEmpty() ) {
	                msg.getRequestHeader().clear();
	                msg.getRequestBody().setBody("");
	            } else {
	                msg.getRequestHeader().setMessage(getHeaderFromJTextArea());
	                String body = viewerText.getText();
	                msg.getRequestBody().setBody(body);
	                msg.getRequestHeader().setContentLength(msg.getRequestBody().length());
	            }
	        } else {
	            if ( isHeadersEmpty() ) {
	                msg.getResponseHeader().clear();
	                msg.getResponseBody().setBody("");
	            } else {
	                msg.getResponseHeader().setMessage(getHeaderFromJTextArea());
	                String body = viewerText.getText();
	                msg.getResponseBody().setBody(body);
	                msg.getResponseHeader().setContentLength(msg.getResponseBody().length());
	            }
	        }
	    } catch (Exception e) {
	    }

	}
	
	/**
	 * This method initializes panelOption	
	 * 	
	 * @return	
	 */    
	public JPanel getPanelOption() {
		return panelOption;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public boolean isHeadersEmpty() {
		return viewerHeaders.isEmpty();
		
	}
	
}
