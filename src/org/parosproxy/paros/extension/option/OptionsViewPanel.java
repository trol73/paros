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
package org.parosproxy.paros.extension.option;

import java.awt.CardLayout;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import org.parosproxy.paros.model.OptionsParam;
import org.parosproxy.paros.utils.I18N;
import org.parosproxy.paros.view.AbstractParamPanel;

/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class OptionsViewPanel extends AbstractParamPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel panelMisc;
	private JCheckBox chkProcessImages;
	
	public OptionsViewPanel() {
		super();
		setLayout(new CardLayout());
		setName(I18N.get("options.view"));
		
		panelMisc = new JPanel();
		GridLayout gridLayout2 = new GridLayout();

		panelMisc.setLayout(gridLayout2);
		panelMisc.setSize(114, 132);
		panelMisc.setName("Miscellenous");
		gridLayout2.setRows(1);
		
		chkProcessImages = new JCheckBox();
		chkProcessImages.setText(I18N.get("options.view.processImagesInHttpRequestsResponses"));
		chkProcessImages.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		chkProcessImages.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
		
		panelMisc.add(chkProcessImages, null);

		add(panelMisc, panelMisc.getName());
	}

/*
    private static final String[] ROOT = {};
    private static final String[] GENERAL = {"General"};
    private static final String[] MISCELLENOUS = {"Miscellenous"};
*/
	
	public void initParam(Object obj) {
		OptionsParam options = (OptionsParam) obj;
		chkProcessImages.setSelected(options.getViewParam().getProcessImages() > 0);
	}

	public void validateParam(Object obj) {
		// no validation needed
	}

	public void saveParam(Object obj) throws Exception {
		OptionsParam options = (OptionsParam) obj;
		options.getViewParam().setProcessImages(chkProcessImages.isSelected() ? 1 : 0);
	}
	
}
