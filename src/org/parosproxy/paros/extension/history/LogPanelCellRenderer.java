/*
 *
 * Paros and its related class files.
 * 
 * Paros is an HTTP/HTTPS proxy for assessing web application security.
 * Copyright (C) 2006 Chinotec Technologies Company
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
package org.parosproxy.paros.extension.history;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.JLabel;

import org.parosproxy.paros.model.HistoryReference;
import org.parosproxy.paros.network.HttpMalformedHeaderException;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.utils.Res;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.SystemColor;
import java.sql.SQLException;

public class LogPanelCellRenderer extends JPanel implements ListCellRenderer {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel txtId;
    private JLabel txtMethod;
    private JLabel txtURI;
    private JLabel txtStatus;
    private JLabel txtReason;
    private JLabel txtRTT;
    private JLabel txtTag;

    /**
     * This is the default constructor
     */
    public LogPanelCellRenderer() {
        super();

        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
        gridBagConstraints4.anchor = GridBagConstraints.WEST;
        gridBagConstraints4.gridx = 6;
        gridBagConstraints4.gridy = 0;
        gridBagConstraints4.weightx = 0.25D;
        gridBagConstraints4.ipadx = 4;
        gridBagConstraints4.ipady = 1;
        gridBagConstraints4.insets = new Insets(0,2,0,0);
        gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
        GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
        gridBagConstraints3.anchor = GridBagConstraints.WEST;
        gridBagConstraints3.gridy = 0;
        gridBagConstraints3.weightx = 0.0D;
        gridBagConstraints3.ipadx = 4;
        gridBagConstraints3.ipady = 1;
        gridBagConstraints3.gridx = 5;
        GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
        gridBagConstraints21.gridx = 4;
        gridBagConstraints21.ipadx = 4;
        gridBagConstraints21.ipady = 1;
        gridBagConstraints21.weightx = 0.0D;
        gridBagConstraints21.anchor = GridBagConstraints.WEST;
        gridBagConstraints21.gridy = 0;
        GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
        gridBagConstraints11.anchor = GridBagConstraints.WEST;
        gridBagConstraints11.gridy = 0;
        gridBagConstraints11.weightx = 0.0D;
        gridBagConstraints11.ipadx = 4;
        gridBagConstraints11.ipady = 1;
        gridBagConstraints11.gridx = 3;
        txtTag = new JLabel();
        txtTag.setText(" ");
        txtTag.setBackground(SystemColor.text);
        txtTag.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtTag.setPreferredSize(new Dimension(70,15));
        txtTag.setMinimumSize(new Dimension(70,15));
        txtTag.setFont(Res.FONT_DEFAULT);
        txtTag.setOpaque(true);
        txtRTT = new JLabel();
        txtRTT.setText(" ");
        txtRTT.setBackground(SystemColor.text);
        txtRTT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtRTT.setPreferredSize(new Dimension(55,15));
        txtRTT.setMinimumSize(new Dimension(55,15));
        txtRTT.setFont(Res.FONT_DEFAULT);
        txtRTT.setOpaque(true);
        txtReason = new JLabel();
        txtReason.setText(" ");
        txtReason.setBackground(SystemColor.text);
        txtReason.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtReason.setPreferredSize(new Dimension(85,15));
        txtReason.setMinimumSize(new Dimension(85,15));
        txtReason.setFont(Res.FONT_DEFAULT);
        txtReason.setOpaque(true);
        txtReason.setVisible(true);
        txtStatus = new JLabel();
        txtStatus.setText(" ");
        txtStatus.setBackground(SystemColor.text);
        txtStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtStatus.setPreferredSize(new Dimension(30,15));
        txtStatus.setMinimumSize(new Dimension(30,15));
        txtStatus.setFont(Res.FONT_DEFAULT);
        txtStatus.setOpaque(true);
        GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
        gridBagConstraints2.insets = new Insets(0,0,0,0);
        gridBagConstraints2.gridy = 0;
        gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints2.weightx = 0.75D;
        gridBagConstraints2.anchor = GridBagConstraints.WEST;
        gridBagConstraints2.ipadx = 4;
        gridBagConstraints2.ipady = 1;
        gridBagConstraints2.gridx = 2;
        GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
        gridBagConstraints1.insets = new Insets(0,0,0,0);
        gridBagConstraints1.gridy = 0;
        gridBagConstraints1.anchor = GridBagConstraints.WEST;
        gridBagConstraints1.weightx = 0.0D;
        gridBagConstraints1.ipadx = 4;
        gridBagConstraints1.ipady = 1;
        gridBagConstraints1.fill = GridBagConstraints.NONE;
        gridBagConstraints1.gridx = 1;
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(0,0,0,0);
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.0D;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.gridx = 0;
        txtURI = new JLabel();
        txtURI.setText(" ");
        txtURI.setBackground(SystemColor.text);
        txtURI.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtURI.setPreferredSize(new Dimension(420,15));
        txtURI.setMinimumSize(new Dimension(420,15));
        txtURI.setFont(Res.FONT_DEFAULT);
        txtURI.setOpaque(true);
        txtMethod = new JLabel();
        txtMethod.setText(" ");
        txtMethod.setBackground(SystemColor.text);
        txtMethod.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtMethod.setPreferredSize(new Dimension(45,15));
        txtMethod.setMinimumSize(new Dimension(45,15));
        txtMethod.setFont(Res.FONT_DEFAULT);
        txtMethod.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        txtMethod.setOpaque(true);
        txtId = new JLabel();
        txtId.setText(" ");
        txtId.setBackground(SystemColor.text);
        txtId.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        txtId.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtId.setPreferredSize(new Dimension(40,15));
        txtId.setMinimumSize(new Dimension(40,15));
        txtId.setFont(Res.FONT_DEFAULT);
        txtId.setOpaque(true);
        this.setLayout(new GridBagLayout());
        this.setSize(328, 11);
        this.setFont(Res.FONT_DEFAULT);
        this.add(txtId, gridBagConstraints);
        this.add(txtMethod, gridBagConstraints1);
        this.add(txtURI, gridBagConstraints2);
        this.add(txtStatus, gridBagConstraints11);
        this.add(txtReason, gridBagConstraints21);
        this.add(txtRTT, gridBagConstraints3);
        this.add(txtTag, gridBagConstraints4);
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        
        HistoryReference ref = (HistoryReference) value;
        txtId.setText(Integer.toString(ref.getHistoryId()));
        
        HttpMessage msg;
        try {
            msg = ref.getHttpMessage();
            txtMethod.setText(msg.getRequestHeader().getMethod());
            txtURI.setText(msg.getRequestHeader().getURI().toString());
            txtStatus.setText(Integer.toString(msg.getResponseHeader().getStatusCode()));
            txtReason.setText(msg.getResponseHeader().getReasonPhrase());
            txtRTT.setText(msg.getTimeElapsedMillis()+"ms");
            txtTag.setText(msg.getTag());

        } catch (HttpMalformedHeaderException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        if (isSelected) {
            txtId.setBackground(list.getSelectionBackground());
            txtId.setForeground(list.getSelectionForeground());
            txtMethod.setBackground(list.getSelectionBackground());
            txtMethod.setForeground(list.getSelectionForeground());
            txtURI.setBackground(list.getSelectionBackground());
            txtURI.setForeground(list.getSelectionForeground());
            txtStatus.setBackground(list.getSelectionBackground());
            txtStatus.setForeground(list.getSelectionForeground());
            txtReason.setBackground(list.getSelectionBackground());
            txtReason.setForeground(list.getSelectionForeground());
            txtRTT.setBackground(list.getSelectionBackground());
            txtRTT.setForeground(list.getSelectionForeground());
            txtTag.setBackground(list.getSelectionBackground());
            txtTag.setForeground(list.getSelectionForeground());

        } else {
            Color darker = new Color(list.getBackground().getRGB() & 0xFFECECEC);
            
            txtId.setBackground(list.getBackground());
            txtId.setForeground(list.getForeground());
            txtMethod.setBackground(darker);
            txtMethod.setForeground(list.getForeground());
            txtURI.setBackground(list.getBackground());
            txtURI.setForeground(list.getForeground());
            txtStatus.setBackground(darker);
            txtStatus.setForeground(list.getForeground());
            txtReason.setBackground(list.getBackground());
            txtReason.setForeground(list.getForeground());
            txtRTT.setBackground(darker);
            txtRTT.setForeground(list.getForeground());
            txtTag.setBackground(list.getBackground());
            txtTag.setForeground(list.getForeground());

        }
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        return this;
        
    }

}  //  @jve:decl-index=0:visual-constraint="10,10"
