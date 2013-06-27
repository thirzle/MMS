package sysconfig;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;

/**
 * This class stores static information about the system's configuration.
 * <p>
 * The following settings are currently stored in this class and are
 * accessible as static objects:
 * <ul>
 * 	<li>
 * 		{@link #system_path}
 * 	<li>
 * 		{@link #system_setting_path}
 * 	<li>
 * 		{@link #system_pdf_path}
 * 	<li>
 * 		{@link #system_database_url}
 * 	<li>
 * 		{@link #system_database_user}
 * 	<li>
 * 		{@link #system_database_password}
 * 	<li>
 * 		{@link #system_database_driver}
 * </ul>
 * 
 * <p>
 * Example to gain access to a specific setting:
 * <p>
 * <code>
 * 	//returns the absolute path, the system is running in.<br>
 * 	sysconfig.Config.system_path.getValue();<br>
 * 	<br>
 * 	//returns the relative path (relative to the system_path),<br>
 * 	//the pdf documents are stored in.<br>
 * 	sysconfig.Config.system_pdf_path.getValue();<br>
 * <code>
 * 
 * @see Setting
 * 
 * @author AJ
 *
 */
public class Config {

    // the only real final setting in this system...
    /**
     * The absolute system path, this system runs in.
     */
    public final static Setting system_path = new Setting();
    /**
     * The relative path to the settings file folder ("configuration.ini")
     * (in relation to the {@link #system_path}).
     */
    public final static Setting system_setting_path = new Setting();
    
    /**
     * The relative path to the PDF document folder
     * (in relation to the {@link #system_path}).
     */
    public final static Setting system_pdf_path = new Setting();
    /**
     * The url to the database.
     */
    public final static Setting system_database_url = new Setting();
    /**
     * The user name of the database.
     */
    public final static Setting system_database_user = new Setting();
    /**
     * The password of the database.
     */
    public final static Setting system_database_password = new Setting();
    /**
     * The driver of the database.
     */
    public final static Setting system_database_driver = new Setting();

    // not so final settings:
    /**
     * Loads the config file and initalizes the system settings.
     * <p>
     * (Paths tested on Apache only)
     */
    public void load_settings() {
	System.out.println("	loading config...");
	//dirty hack... (jumping a few folder up from the class crap ...)
	URL sys_path = this.getClass().getResource("../../../");
	String decoded_sys_path;
	try {
	    decoded_sys_path = URLDecoder.decode(sys_path.getFile(), "UTF-8");
	    if (decoded_sys_path.startsWith("/")) {
		decoded_sys_path = decoded_sys_path.replaceFirst("/", "");
	    }
	} catch (UnsupportedEncodingException e1) {
	    System.out.println("	sys_path URL decoding failed... (unsupported)");
	    return;
	}
	//dirty hack end
	
	system_path.setValue(decoded_sys_path);
	System.out.println("	system_path: " + decoded_sys_path);

	try {
	    FileInputStream fstream = new FileInputStream(system_path.getValue() + "/config/configuration.ini");
	    DataInputStream in = new DataInputStream(fstream);
	    BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    String line;
	    while ((line = br.readLine()) != null) {
		if(line.startsWith(";")){
		    System.out.println("	" + line);
		}
		else{
		    String[] val = line.split("=");  
		    if(val.length >= 2){
			
			String type = val[0];
			String value = "";
			for(int i = 1; i < val.length; i++){
			    value = value.concat(val[i]);
			    if(i != val.length-1){
				value = value.concat("=");
			    }
			}
			
			if (type.compareTo("system_pdf_path") == 0) {
			    system_pdf_path.setValue(value);
			    System.out.println("	system_pdf_path: " + value);
			} else if (type.compareTo("system_database_url") == 0) {
			    system_database_url.setValue(value);
			    System.out.println("	system_database_url: " + value);
			} else if (type.compareTo("system_database_user") == 0) {
			    system_database_user.setValue(value);
			    System.out.println("	system_database_user: " + value);
			} else if (type.compareTo("system_database_password") == 0) {
			    system_database_password.setValue(value);
			    System.out.println("	system_database_password: " + value);
			} else if (type.compareTo("system_database_driver") == 0) {
			    system_database_driver.setValue(value);
			    System.out.println("	system_database_driver: " + value);
			}
		    }
		    else{
			System.out.println("	The following configuraion.ini line is invalid:\n" + line);
		    }

		}
	    }
	    in.close();
	} catch (Exception e) {
	    System.err.println("	Couldn't load sysconfig completelly:\n" + e.getMessage());
	    return;
	}
	System.out.println("	loaded config.");

    }

}
