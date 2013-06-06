package org.eclipse.swtbot.generator.ui.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.source.SourceViewer;

public class Method {
	
	private List<String> code;
	private String name;
	private boolean active;
	private boolean test;
	
	public Method(String name){
		this.test=false;
		this.name = name;
		code = new ArrayList<String>();
		active = true;
	}
	
	public void addCode(String code){
		this.code.add(code);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public List<String> getCode(){
		return code;
	}

	public String getName() {
		return name;
	}

	public boolean isTest() {
		return test;
	}

	public void setTest(boolean test) {
		this.test = test;
	}
	

}
