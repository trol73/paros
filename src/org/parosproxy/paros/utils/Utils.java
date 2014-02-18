package org.parosproxy.paros.utils;

import java.io.File;

public class Utils {
	
	private static String rootPath; 
	
	/**
	 * Get application root path 
	 * 
	 * @return
	 */
	public static String getRootPath() {
		if ( rootPath == null ) {
			rootPath = new File(Utils.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getAbsolutePath();
			int index = rootPath.lastIndexOf("/");
			if ( index >= 0 ) {
				rootPath = rootPath.substring(0, index);
			}
		}
		System.out.println("root " + rootPath);
		return rootPath;
	}

}
