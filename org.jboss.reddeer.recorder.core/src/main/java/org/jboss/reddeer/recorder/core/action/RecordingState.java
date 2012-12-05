package org.jboss.reddeer.recorder.core.action;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISources;

public class RecordingState extends AbstractSourceProvider{
	
	public final static String MY_STATE="org.jboss.reddeer.recorder.core.action.RecordingState";
	private final static String STARTED = "started"; 
	private final static String STOPPED = "stopped"; 
	private boolean recording=false;
	private String testName;

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public Map<String,String> getCurrentState() {
		Map<String,String> map = new HashMap<String,String>(2);
        String currentState =  recording?STARTED:STOPPED; 
	    map.put(MY_STATE, currentState);
		return map;
	}

	public String[] getProvidedSourceNames() {
	    return new String[] { MY_STATE};
	}
	
	public void toggleRecording(){
		if(recording){
			recording = false;
		} else {
			recording = true;
		}
        String currentState =  recording?STARTED:STOPPED; 
        fireSourceChanged(ISources.WORKBENCH, MY_STATE, currentState);
		
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

}
