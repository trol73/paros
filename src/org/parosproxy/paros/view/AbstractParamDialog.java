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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.parosproxy.paros.extension.AbstractDialog;
import org.parosproxy.paros.utils.I18N;
import org.parosproxy.paros.utils.Res;
/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class AbstractParamDialog extends AbstractDialog {
	private static final long serialVersionUID = 1L;
	
	private Object paramObject;
    private Map<String, AbstractParamPanel> tablePanel = new HashMap<String, AbstractParamPanel>();
    private int exitResult = JOptionPane.CANCEL_OPTION;
    
	private JPanel jContentPane;
	private JButton btnOK;
	private JButton btnCancel;
	private JPanel jPanel;
	private JSplitPane jSplitPane;
	private JTree treeParam;
	private JPanel jPanel1;
	private JPanel panelParam;
	private JTextField txtHeadline;

	private DefaultTreeModel treeModel;
	private DefaultMutableTreeNode rootNode;
	private JScrollPane jScrollPane;
	private JScrollPane jScrollPane1;
	
	private static final String TXT_CANCEL = I18N.get("cancel");
	private static final String TXT_OK = I18N.get("ok");
	
	/**
	 * 
	 */
	public AbstractParamDialog() {
	    super();
	    initialize();
	}
	
	
	/**
	 * @param arg0
	 * @throws HeadlessException
	 */
	public AbstractParamDialog(Frame parent, boolean modal, String title, String rootName) throws HeadlessException {
		super(parent, modal);
		initialize();
		setTitle(title);
		getRootNode().setUserObject(rootName);
	}
	
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        setFont(Res.FONT_DIALOG);
        setSize(500, 375);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setContentPane(getJContentPane());
	}
	
	
	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return JPanel	
	 */    
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();

			JLabel jLabel = new JLabel();

			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			//jLabel.setName("jLabel");
			jLabel.setText("");
			gridBagConstraints12.gridx = 0;
			gridBagConstraints12.gridy = 1;
			gridBagConstraints12.ipadx = 0;
			gridBagConstraints12.ipady = 0;
			gridBagConstraints12.anchor = GridBagConstraints.WEST;
			gridBagConstraints12.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints12.insets = new Insets(2,2,2,2);
			gridBagConstraints12.weightx = 1.0D;
			gridBagConstraints13.gridx = 1;
			gridBagConstraints13.gridy = 1;
			gridBagConstraints13.ipadx = 0;
			gridBagConstraints13.ipady = 0;
			gridBagConstraints13.fill = GridBagConstraints.NONE;
			gridBagConstraints13.anchor = GridBagConstraints.EAST;
			gridBagConstraints13.insets = new Insets(2,2,2,2);
			gridBagConstraints14.gridx = 2;
			gridBagConstraints14.gridy = 1;
			gridBagConstraints14.ipadx = 0;
			gridBagConstraints14.ipady = 0;
			gridBagConstraints14.anchor = GridBagConstraints.EAST;
			gridBagConstraints14.insets = new Insets(2,2,2,2);
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.weighty = 1.0;
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints1.gridwidth = 3;
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			jContentPane.add(getJSplitPane(), gridBagConstraints1);
			jContentPane.add(jLabel, gridBagConstraints12);
			jContentPane.add(getBtnOK(), gridBagConstraints13);
			jContentPane.add(getBtnCancel(), gridBagConstraints14);
		}
		return jContentPane;
	}
	

	/**
	 * This method initializes btnOK	
	 * 	
	 * @return JButton	
	 */    
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			//btnOK.setName("btnOK");
			btnOK.setText(TXT_OK);
			btnOK.addActionListener(new ActionListener() { 

				public void actionPerformed(ActionEvent e) {    
					try {
					    validateParam();
					    saveParam();
					    exitResult = JOptionPane.OK_OPTION;
					    AbstractParamDialog.this.setVisible(false);
					} catch (Exception ex) {
					    View.getSingleton().showWarningDialog(ex.getMessage());
					}
				}
			});

		}
		return btnOK;
	}
	
	
	/**
	 * This method initializes btnCancel	
	 * 	
	 * @return JButton	
	 */    
	protected JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			//btnCancel.setName("btnCancel");
			btnCancel.setText(TXT_CANCEL);
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {    

				   exitResult = JOptionPane.CANCEL_OPTION;
				   AbstractParamDialog.this.setVisible(false);
				}
			});

		}
		return btnCancel;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();

			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			//jPanel.setName("jPanel");
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.weighty = 1.0;
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 1;
			gridBagConstraints5.ipadx = 0;
			gridBagConstraints5.ipady = 0;
			gridBagConstraints5.fill = GridBagConstraints.BOTH;
			gridBagConstraints5.weightx = 1.0D;
			gridBagConstraints5.weighty = 1.0D;
			gridBagConstraints5.insets = new Insets(2,5,2,5);
			gridBagConstraints5.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints7.weightx = 1.0;
			gridBagConstraints7.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints7.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.gridy = 0;
			gridBagConstraints7.insets = new Insets(2,5,2,5);
			jPanel.add(getTxtHeadline(), gridBagConstraints7);
			jPanel.add(getPanelParam(), gridBagConstraints5);
		}
		return jPanel;
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return JSplitPane	
	 */    
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setContinuousLayout(true);
			jSplitPane.setVisible(true);
			jSplitPane.setRightComponent(getJPanel1());
			jSplitPane.setDividerLocation(175);
			jSplitPane.setDividerSize(3);
			jSplitPane.setResizeWeight(0.3D);
			jSplitPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			jSplitPane.setLeftComponent(getJScrollPane());
		}
		return jSplitPane;
	}
	/**
	 * This method initializes treeParam	
	 * 	
	 * @return JTree	
	 */    
	private JTree getTreeParam() {
		if (treeParam == null) {
			treeParam = new JTree();
			treeParam.setModel(getTreeModel());
			treeParam.setShowsRootHandles(true);
			treeParam.setRootVisible(true);
			treeParam.addTreeSelectionListener(new TreeSelectionListener() { 

				public void valueChanged(TreeSelectionEvent e) {    

			        DefaultMutableTreeNode node = (DefaultMutableTreeNode) getTreeParam().getLastSelectedPathComponent();
			        if (node == null) return;
			        String name = (String) node.getUserObject();
			        showParamPanel(name);
				}
			});
			DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
			renderer.setLeafIcon(null);
			renderer.setOpenIcon(null);
			renderer.setClosedIcon(null);
			treeParam.setCellRenderer(renderer);

			treeParam.setRowHeight(18);
		}
		return treeParam;
	}
	
	
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return JPanel	
	 */    
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new CardLayout());
			jPanel1.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
			jPanel1.add(getJScrollPane1(), getJScrollPane1().getName());
		}
		return jPanel1;
	}
	
	
	/**
	 * This method initializes panelParam	
	 * 	
	 * @return JPanel	
	 */    
	protected JPanel getPanelParam() {
		if (panelParam == null) {
			panelParam = new JPanel();
			panelParam.setLayout(new CardLayout());
			panelParam.setPreferredSize(new Dimension(300,300));
			panelParam.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		}
		return panelParam;
	}
	
	
	/**
	 * This method initializes txtHeadline	
	 * 	
	 * @return JTextField	
	 */    
	private JTextField getTxtHeadline() {
		if (txtHeadline == null) {
			txtHeadline = new JTextField();
			txtHeadline.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			txtHeadline.setEditable(false);
			txtHeadline.setEnabled(false);
			txtHeadline.setBackground(Color.white);
			txtHeadline.setFont(Res.FONT_DEFAULT_BOLD);
		}
		return txtHeadline;
	}
	

	/**
	 * This method initializes treeModel	
	 * 	
	 * @return tree.DefaultTreeModel	
	 */    
	private DefaultTreeModel getTreeModel() {
		if (treeModel == null) {
			treeModel = new DefaultTreeModel(getRootNode());
			treeModel.setRoot(getRootNode());
		}
		return treeModel;
	}
	
	
	/**
	 * This method initializes rootNode	
	 * 	
	 * @return tree.DefaultMutableTreeNode	
	 */    
	protected DefaultMutableTreeNode getRootNode() {
		if (rootNode == null) {
			rootNode = new DefaultMutableTreeNode("Root");
		}
		return rootNode;
	}
	
	private DefaultMutableTreeNode addParamNode(String[] paramSeq) {
	    DefaultMutableTreeNode parent = getRootNode();
	    DefaultMutableTreeNode child = null;
	    DefaultMutableTreeNode result = null;

	    for ( String param : paramSeq ) {
	        result = null;
	        for (int j=0; j<parent.getChildCount(); j++) {
	            child = (DefaultMutableTreeNode) parent.getChildAt(j);
	            if (child.toString().equalsIgnoreCase(param)) {
	                result = child;
	                break;
	            }
	        }
	        
	        if (result == null) {
	            result = new DefaultMutableTreeNode(param);
	            parent.add(result);
	        }

	        parent = result;
	    }
	    
	    return parent;
	        
	 
	}
	
	/**
	 * If multiple name use the same panel
	 * @param parentParams
	 * @param name
	 * @param panel
	 */
	public void addParamPanel(String[] parentParams, String name, AbstractParamPanel panel) {
	    if (parentParams != null) {
	        DefaultMutableTreeNode parent = addParamNode(parentParams);
	        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(name);
	        parent.add(newNode);
	    } else {
	        // No need to create node.  This is the root panel.
	    }
        getPanelParam().add(panel, panel.getName());
        tablePanel.put(name, panel);
	    
	}
	
	public void addParamPanel(String[] parentParams, AbstractParamPanel panel) {
	    addParamPanel(parentParams, panel.getName(), panel);
	}
	
	protected void showParamPanel(String name) {
	    if ( name == null || name.equals("") ) 
	    	return;
	    
	    // exit if panel name not found. 
	    JPanel panel = tablePanel.get(name);
	    if ( panel == null ) {
	    	return;
	    }
	    
        getTxtHeadline().setText(name);
        CardLayout card = (CardLayout) getPanelParam().getLayout();
        card.show(getPanelParam(), panel.getName());
	}

	public void initParam(Object obj) {
	    paramObject = obj;
	    for ( AbstractParamPanel panel : tablePanel.values() ) {
	        panel.initParam(obj);
	    }
	    
	}

	/**
	 * This method is to be overrided by subclass.
	 *
	 */
	public void validateParam() throws Exception {
		for ( AbstractParamPanel panel : tablePanel.values() ) {
	        panel.validateParam(paramObject);
	    }
	}

	
	/**
	 * This method is to be overrided by subclass.
	 *
	 */
	public void saveParam() throws Exception {
		for ( AbstractParamPanel panel : tablePanel.values() ) {
	        panel.saveParam(paramObject);
	    }
	}
	
	
	protected void expandRoot() {
	    getTreeParam().expandPath(new TreePath(getRootNode()));
	}
	
	public int showDialog(boolean showRoot) {
        expandRoot();
        try {
            DefaultMutableTreeNode firstNode = null;
            if (showRoot) {
                firstNode = (DefaultMutableTreeNode) getTreeModel().getRoot();
            } else {
                firstNode = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) getTreeModel().getRoot()).getChildAt(0);
            }
            showParamPanel(firstNode.toString());
            getTreeParam().setSelectionPath(new TreePath(firstNode.getPath()));
        } catch (Exception e) {
        }
        
        setVisible(true);
	    return exitResult;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return JScrollPane	
	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTreeParam());
			jScrollPane.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return JScrollPane	
	 */    
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setName("jScrollPane1");
			jScrollPane1.setViewportView(getJPanel());
			jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		}
		return jScrollPane1;
	}
}
