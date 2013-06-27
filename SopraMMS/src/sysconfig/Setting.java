package sysconfig;

/**
 * Wrapper class for system settings.
 * <p>
 * Check the {@link Config} class for more info.
 * 
 * @author AJ
 *
 */
public class Setting {
    /**
     * The setting value.
     */
    private String value = "";
    
    /**
     * Empty constructor.
     */
    public Setting(){
	
    }
    
    /**
     * Sets the setting value.
     * @param value
     */
    public void setValue(String value){
	this.value = value;
    }
    
    /**
     * Retrieves the setting value.
     * @return The setting value.
     */
    public String getValue(){
	return this.value;
    }
}
