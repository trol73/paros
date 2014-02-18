package org.parosproxy.paros.utils;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

public class Res {
	public static final Font FONT_DEFAULT = new Font("Default", Font.PLAIN, 12);
	public static final Font FONT_DEFAULT_BOLD = new Font("Default", Font.BOLD, 12);
	public static final Font FONT_SANSSERIF = new Font("MS Sans Serif", Font.PLAIN, 12);
	public static final Font FONT_COURIERNEW = new Font("Courier New", Font.PLAIN, 12);
	public static final Font FONT_DIALOG = new Font("Dialog", Font.PLAIN, 12);
	public static final Font FONT_DIALOG_11 = new Font("Dialog", Font.PLAIN, 11);
	
	public static final Image IMAGE_LOGO = Toolkit.getDefaultToolkit().getImage(new Res().getClass().getResource("/resource/paros_logo48x48.gif"));

}
