package org.parosproxy.paros.utils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class I18N {
	
	private static Properties properties;
	private static String LANG_FILE = "/paros.ru.properties";
	
	static {
		try {			
		    properties = loadProperties(new FileInputStream(Utils.getRootPath() + LANG_FILE), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final String get(String id) {
		String result = properties.getProperty(id);
		if ( result != null ) {
			return properties.getProperty(id);
		}
		System.out.println("!!!! " + properties.keySet() + "   " + id + " -> " + properties.getProperty(id));
		return id;
	}
	
	
	 private static Properties loadProperties(InputStream is, String encoding) throws IOException {
	      StringBuilder sb = new StringBuilder();
	      InputStreamReader isr = new InputStreamReader(is, encoding);
	      while ( true ) {
	         int temp = isr.read();
	         if ( temp < 0 )
	            break;
	         char c = (char) temp;
	         sb.append(c);
	      }

	      String inputString = escapifyStr(sb.toString());
	      byte[] bs = inputString.getBytes("ISO-8859-1");
	      ByteArrayInputStream bais = new ByteArrayInputStream(bs);

	      Properties ps = new Properties();
	      ps.load(bais);
	      return ps;
	   }

	 private static char hexDigit(char ch, int offset) {
		 int val = (ch >> offset) & 0xF;
		 if ( val <= 9 ) {
	         return (char) ('0' + val);
		 }
		 return (char) ('A' + val - 10);
	 }

	   
	 private static String escapifyStr(String str) {      
	      StringBuilder result = new StringBuilder();

	      int len = str.length();
	      for(int x = 0; x < len; x++)
	      {
	         char ch = str.charAt(x);
	         if(ch <= 0x007e)
	         {
	            result.append(ch);
	            continue;
	         }
	         
	         result.append('\\');
	         result.append('u');
	         result.append(hexDigit(ch, 12));
	         result.append(hexDigit(ch, 8));
	         result.append(hexDigit(ch, 4));
	         result.append(hexDigit(ch, 0));
	      }
	      return result.toString();
	   }
	
	
}
