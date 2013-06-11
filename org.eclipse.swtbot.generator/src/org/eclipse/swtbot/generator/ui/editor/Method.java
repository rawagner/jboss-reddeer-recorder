package org.eclipse.swtbot.generator.ui.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.generator.framework.AnnotationRule;

public class Method {
	
	private List<String> code;
	private String name;
	private boolean active;
	private List<AnnotationRule> annotations;
	
	public Method(String name){
		this.name = name;
		code = new ArrayList<String>();
		active = true;
		this.annotations = new ArrayList<AnnotationRule>();
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

	public List<AnnotationRule> getAnnotations() {
		return annotations;
	}

	public void addAnnotation(AnnotationRule annotation) {
		this.annotations.add(annotation);
	}
	
	public boolean removeAnnotation(AnnotationRule annotation){
		return this.annotations.remove(annotation);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Method other = (Method) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
	
	

}
