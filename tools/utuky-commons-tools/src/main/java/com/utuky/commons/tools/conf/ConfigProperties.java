package com.utuky.commons.tools.conf;

import java.io.InputStreamReader;
import java.util.Properties;

public class ConfigProperties {
	private static ConfigProperties cp = null ;
    private static Properties pro ;
    private ConfigProperties() {}
    public synchronized static ConfigProperties getInstance() {
        if(cp==null) {
            cp = new ConfigProperties() ;
            InputStreamReader in = null ;
            try {
//              InputStream in = new BufferedInputStream(new FileInputStream(new File(ConfigProperties.class.getClassLoader().getResource("config.properties").toURI()))) ;
                in = new InputStreamReader(ConfigProperties.class.getClassLoader().getResourceAsStream("config.properties"), "UTF-8");
                pro = new Properties() ;
                pro.load(in);
            } catch (Exception e) {
                e.printStackTrace() ;
                try {
                	 if(in!=null) in.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
            }
        }
        return cp ;
    }
     
    public static String getValue(String name) throws Exception {
        if(pro==null) {
            ConfigProperties.getInstance() ;
        }
        String value = pro.getProperty(name) ;
        return value ;
    }
     
    public static void setValue(String name,String value) throws Exception {
        if(pro==null) {
            ConfigProperties.getInstance() ;
        }
        pro.setProperty(name, value) ;
    }
     
    public static void main(String[] args) throws Exception {
        ConfigProperties.getInstance() ;
        String id = ConfigProperties.getValue("test") ;
        System.out.println(id);
    }
}
