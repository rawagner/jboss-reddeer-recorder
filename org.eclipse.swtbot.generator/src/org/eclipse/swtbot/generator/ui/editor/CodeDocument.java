package org.eclipse.swtbot.generator.ui.editor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.MultiTextEdit;

public class CodeDocument extends Document {

	private Set<String> imports;
	private List<Method> methods;

	public CodeDocument(String className) {
		super();
		super.set("\npublic class " + className + "{\n\n}");
		imports = new HashSet<String>();
		methods = new ArrayList<Method>();
	}
	
	public void addMethod(String methodName){
		int offset = this.imports.size()+3;
		for(Method m: methods){
			offset = offset+m.getCode().size()+3;
			if(m.isTest()){
				offset = offset+1;
			}
		}
		Method method = new Method(methodName);
		this.methods.add(method);
		for(Method m: methods){
			if(m.isActive() && !m.equals(method)){
				m.setActive(false);
			}
		}
		
		MultiTextEdit edit = new MultiTextEdit();
		
		try {
			edit.addChild(new InsertEdit(getLineOffset(offset), "	public void "+methodName+"(){" + "\n"));
			edit.addChild(new InsertEdit(getLineOffset(offset), "	}\n\n"));
			edit.apply(this);
		} catch (MalformedTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addCode(String code) {
		Method activeMethod = null;
		for(Method m: methods){
			if(m.isActive()){
				activeMethod=m;
			}
		}
		if(activeMethod == null){
			return;
		}
		int offset = this.imports.size()+4;
		
		int i =0;
		Method m = methods.get(i);
		while(!m.equals(activeMethod)){
			i++;
			offset = offset+m.getCode().size()+3;
			if(m.isTest()){
				offset = offset+1;
			}
			m=methods.get(i);
		}
		if(m.isTest()){
			offset=offset+1;
		}
		MultiTextEdit edit = new MultiTextEdit();
		offset = offset+activeMethod.getCode().size();
		activeMethod.addCode(code);
		try {
			edit.addChild(new InsertEdit(getLineOffset(offset), "		"+code + ";\n"));
			edit.apply(this);
		} catch (MalformedTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addImport(String importCode) {
		if (this.imports.add(importCode)) {
			MultiTextEdit edit = new MultiTextEdit();
			try {
				edit.addChild(new InsertEdit(getLineOffset(this.imports.size() - 1), "import "+importCode + ";\n"));
				edit.apply(this);
			} catch (MalformedTreeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void removeImport(String importCode){
		if(this.imports.contains(importCode)){
			String[] lines = this.get().split("\n");
			for(int i=0;i<lines.length;i++){
				if(lines[i].contains(importCode)){
					this.imports.remove(importCode);
					MultiTextEdit edit = new MultiTextEdit();
					try {
						edit.addChild(new DeleteEdit(getLineOffset(i),getLineLength(i)));
						edit.apply(this);
					} catch (MalformedTreeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
				}
			}
		}
	}
	
	public void setActiveMethod(String methodName){
		for(Method m: methods){
			if(m.getName().equals(methodName)){
				m.setActive(true);
			}
		}
		for(Method m: methods){
			if(!m.getName().equals(methodName) && m.isActive()){
				m.setActive(false);
			}
		}
	}
	
	public void markCurrentMethodAsTest(boolean mark){
		int i =0;
		int offset=0;
		Method activeMethod = methods.get(i);
		while(!activeMethod.isActive()){
			i++;
			offset = offset+activeMethod.getCode().size()+3;
			if(activeMethod.isTest()){
				offset = offset+1;
			}
			activeMethod = methods.get(i);
		}
		activeMethod.setTest(mark);
		boolean hasTestMethod = false;
		for(Method m:methods){
			if(m.isTest()){
				hasTestMethod=true;
				break;
			}
		}
		if(hasTestMethod){
			addImport("org.junit.Test");
		}
		offset = offset+this.imports.size()+3;
		MultiTextEdit edit = new MultiTextEdit();
		
		try {
			if(mark){
				edit.addChild(new InsertEdit(getLineOffset(offset), "	@Test\n"));
			} else {
				if(!hasTestMethod){
					removeImport("org.junit.Test");
					edit.addChild(new DeleteEdit(getLineOffset(offset-1),getLineLength(offset-1)));
				} else {
					edit.addChild(new DeleteEdit(getLineOffset(offset),getLineLength(offset)));
				}
			}
			edit.apply(this);
		} catch (MalformedTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Method getActiveMethod(){
		for(Method m: methods){
			if(m.isActive()){
				return m;
			}
		}
		return null;
	}

}
