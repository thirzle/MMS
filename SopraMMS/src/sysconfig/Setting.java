package sysconfig;

public class Setting {
    private String value = "";
    
    public Setting(){
	
    }
    
    public void setValue(String value){
	this.value = value;
    }
    
    public String getValue(){
	return this.value;
    }
}
