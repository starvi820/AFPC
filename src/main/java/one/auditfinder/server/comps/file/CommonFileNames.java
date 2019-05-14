package one.auditfinder.server.comps.file;

import java.util.HashMap;

public class CommonFileNames {

	private HashMap<String, String> mapFileNames;
	
	public CommonFileNames(){
		mapFileNames = new HashMap<String, String>();
	}

	public void setFileName(String key, String name){
		mapFileNames.put(key, name);
	}
	
	public String getFileName(String key){
		return mapFileNames.get(key);
	}
	
}
