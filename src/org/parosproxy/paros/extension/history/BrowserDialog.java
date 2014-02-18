/*
 *
 * Paros and its related class files.
 * 
 * Paros is an HTTP/HTTPS proxy for assessing web application security.
 * Copyright (C) 2003-2005 Chinotec Technologies Company
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

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

import org.parosproxy.paros.control.Control;
import org.parosproxy.paros.extension.AbstractDialog;

import java.awt.CardLayout;

public class BrowserDialog extends AbstractDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String TITLE = "View in Browser: ";
	private JPanel jContentPane;
	private JPanel jPanelBottom;
	private JLabel jLabel;
	private JButton btnCapture;
	private JButton btnStop;
	private JButton btnClose;
	private List<URL> URLs = new ArrayList<URL>();
    private JPanel jPanel;
    private EmbeddedBrowser embeddedBrowser;
    /**
	 * This is the default constructor
	 */
	public BrowserDialog() {
		super();
		initialize();
	}

	public BrowserDialog(Frame frame, boolean modal) {
		super(frame, modal);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setTitle(TITLE);
		this.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent winEvt) {
				  stop();
                  BrowserDialog.this.setVisible(false);
		    }
		});
        pack();
        this.setSize(640,480);

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanelBottom(), java.awt.BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanelBottom	
	 * 	
	 * @return JPanel	
	 */
	private JPanel getJPanelBottom() {
		if (jPanelBottom == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.insets = new java.awt.Insets(5,3,5,5);
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints2.gridx = 2;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.insets = new java.awt.Insets(5,3,5,2);
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints3.gridx = 3;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.insets = new java.awt.Insets(5,3,5,2);
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints1.gridx = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new java.awt.Insets(10,5,10,2);
			gridBagConstraints.gridy = 0;
			gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints.weighty = 1.0D;
			gridBagConstraints.weightx = 1.0D;
			gridBagConstraints.gridx = 0;
			jLabel = new JLabel();
			jLabel.setText(" ");
			jLabel.setComponentOrientation(java.awt.ComponentOrientation.UNKNOWN);
			jPanelBottom = new JPanel();
			jPanelBottom.setLayout(new GridBagLayout());
			jPanelBottom.add(jLabel, gridBagConstraints);
			jPanelBottom.add(getBtnCapture(), gridBagConstraints1);
			jPanelBottom.add(getBtnStop(), gridBagConstraints2);
			jPanelBottom.add(getBtnClose(), gridBagConstraints3);
		}
		return jPanelBottom;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return JButton	
	 */
	private JButton getBtnCapture() {
		if (btnCapture == null) {
			btnCapture = new JButton();
			btnCapture.setText("Capture");
			btnCapture.setHorizontalAlignment(SwingConstants.CENTER);
			btnCapture.setHorizontalTextPosition(SwingConstants.TRAILING);
//			btnCapture.setPreferredSize(new java.awt.Dimension(73,28));
			btnCapture.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					capture();
				}
			});
		}
		return btnCapture;
	}

	private JButton getBtnStop() {
		if (btnStop == null) {
			btnStop = new JButton();
			btnStop.setText("Stop");
			btnStop.setHorizontalAlignment(SwingConstants.CENTER);
			btnStop.setHorizontalTextPosition(SwingConstants.TRAILING);
//			btnStop.setPreferredSize(new java.awt.Dimension(73,28));
			btnStop.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					/*
					int i=0;
					for (i=0;i<URLs.size();i++){
						System.out.println(URLs.get(i));
					}
					*/
					URLs.clear();
					stop();
				}
			});
		}
		return btnStop;
	}
    
    private void stop() {
        getEmbeddedBrowser().stop();
        Control.getSingleton().getProxy().setEnableCacheProcessing(false);

    }

	private void capture(){
	    try {
//	        this.setAlwaysOnTop(true);
	        BufferedImage screencapture = new Robot().createScreenCapture(
	                new Rectangle( this.getX(), this.getY(), 
	                        this.getWidth(), this.getHeight() - this.jPanelBottom.getHeight()) );
	        // Save as JPEG
	        
	        JFileChooser chooser = new JFileChooser();
	        chooser.addChoosableFileFilter(new FileFilter() {
	            public boolean accept(File file) {
	                String filename = file.getName();
	                return filename.endsWith(".png");
	            }
	            public String getDescription() {
	                return "*.png";
	            }
	        }
	        
	        );
	        
	        chooser.showSaveDialog(this);
	        File file = chooser.getSelectedFile();
	        
	        if (file!=null)
	            ImageIO.write(screencapture, "png", file);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
//			 this.setAlwaysOnTop(false);

	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return JButton	
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("Close");
			btnClose.setVisible(false);
			btnClose.addActionListener(new ActionListener() {     // Close this window
				public void actionPerformed(ActionEvent arg0) {					
				        
                      stop();
                      
				}
		    });
		}
		return btnClose;
	}

	public void addURL(URL arg){
		URLs.add(arg);
	}


	/**
     * This method initializes jPanel	
     * 	
     * @return JPanel	
     */
    private JPanel getJPanel() {
        if (jPanel == null) {
            jPanel = new JPanel();
            jPanel.setLayout(new CardLayout());
            jPanel.setPreferredSize(new java.awt.Dimension(400,300));
            jPanel.add(getEmbeddedBrowser(), getEmbeddedBrowser().getName());
        }
        return jPanel;
    }

    /**
     * This method initializes embeddedBrowser	
     * 	
     * @return org.parosproxy.paros.extension.history.EmbeddedBrowser	
     */
    EmbeddedBrowser getEmbeddedBrowser() {
        if (embeddedBrowser == null) {
            embeddedBrowser = new EmbeddedBrowser();
            embeddedBrowser.setName("embeddedBrowser");
        }
        return embeddedBrowser;
    }

    void setURLTitle(String url) {
        setTitle(TITLE + url);
    }
}
