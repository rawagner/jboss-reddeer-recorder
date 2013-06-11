package org.eclipse.swtbot.generator.ui.editor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.generator.framework.AnnotationRule;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.MultiTextEdit;

public class ClassDocument extends Document {

	private Set<String> imports;
	private List<Method> methods;
	private int lastOffset;
	private List<AnnotationRule> classAnnotations;
	private SourceViewer viewer;

	public ClassDocument(String className) {
		super();
		super.set("\npublic class " + className + "{\n\n}");
		imports = new HashSet<String>();
		methods = new ArrayList<Method>();
		classAnnotations = new ArrayList<AnnotationRule>();
	}
	
	private void addText(int offset, List<String> text){
		MultiTextEdit edit = new MultiTextEdit();
		
		try {
			for(String t: text){
				edit.addChild(new InsertEdit(getLineOffset(offset), t));
			}
			edit.apply(this);
		} catch (MalformedTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateColoring();
	}
	
	
	private void removeText(int offset){
		MultiTextEdit edit = new MultiTextEdit();
		try {
			edit.addChild(new DeleteEdit(getLineOffset(offset),getLineLength(offset)));
			edit.apply(this);
		} catch (MalformedTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateColoring();
	}
	
	public void addMethod(String methodName){
		int offset = this.imports.size()+3;
		offset = offset + this.classAnnotations.size();
		for(Method m: methods){
			offset = offset+m.getCode().size()+3;
			offset = offset+ m.getAnnotations().size();
		}
		Method method = new Method(methodName);
		this.methods.add(method);
		
		List<String> text = new ArrayList<String>();
		text.add("	public void "+methodName+"(){" + "\n");
		text.add("	}\n\n");
		
		addText(offset, text);
		setActiveMethod(methodName);
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
		offset = offset + this.classAnnotations.size();
		
		int i =0;
		Method m = methods.get(i);
		while(!m.equals(activeMethod)){
			i++;
			offset = offset+ m.getCode().size()+3;
			offset = offset+ m.getAnnotations().size();
			m=methods.get(i);
		}
		offset = offset+ m.getAnnotations().size() + m.getCode().size();
		
		List<String> text = new ArrayList<String>();
		text.add("		"+code + ";\n");
		m.addCode(code);
		
		addText(offset, text);
	}

	public void addImport(String importCode) {
		if (this.imports.add(importCode)) {
			List<String> text = new ArrayList<String>();
			text.add("import "+importCode + ";\n");
			
			addText(this.imports.size() - 1, text);
		}
	}
	
	public void removeImport(String importCode){
		if(this.imports.contains(importCode)){
			String[] lines = this.get().split("\n");
			for(int i=0;i<lines.length;i++){
				if(lines[i].contains(importCode)){
					this.imports.remove(importCode);
					removeText(i);
					return;
				}
			}
		}
	}
	
	public void setActiveMethod(String methodName){
		for(Method m: methods){
			m.setActive(false);
		}
		
		for(Method m: methods){
			if(m.getName().equals(methodName)){
				m.setActive(true);
			}
		}
		updateColoring();
	}
	
	public void removeAnnotation(AnnotationRule rule){
		int i =0;
		int offset=this.imports.size()+3 + this.classAnnotations.size();
		Method activeMethod = methods.get(i);
		while(!activeMethod.isActive()){
			i++;
			offset = offset+activeMethod.getCode().size()+3;
			offset = offset+ activeMethod.getAnnotations().size();
			activeMethod = methods.get(i);
		}
		offset = offset + activeMethod.getAnnotations().size()- activeMethod.getAnnotations().indexOf(rule)-1;
		
		if(activeMethod.removeAnnotation(rule)){
			removeText(offset);
			if(!isAnnotationPresent(rule)){
				removeImport(rule.getImportText());
			}
		}
	}
	
	public void removeClassAnnotation(AnnotationRule rule){
		if(classAnnotations.contains(rule)){
			int offset=this.imports.size()+1 + classAnnotations.indexOf(rule);
			removeText(offset);
			classAnnotations.remove(rule);
			if(!isAnnotationPresent(rule)){
				removeImport(rule.getImportText());
			}
		}
	}

	public void addClassAnnotation(AnnotationRule rule){
		addImport(rule.getImportText());
		int offset=this.imports.size()+1 + this.classAnnotations.size();
		List<String> text = new ArrayList<String>();
		text.add("@"+rule.getAnnotation()+"\n");
		classAnnotations.add(rule);
		addText(offset, text);
	}
	
	
	public void addAnnotation(AnnotationRule rule){
		int i =0;
		int offset=0;
		Method activeMethod = methods.get(i);
		while(!activeMethod.isActive()){
			i++;
			offset = offset+activeMethod.getCode().size()+3;
			offset = offset+ activeMethod.getAnnotations().size();
			activeMethod = methods.get(i);
		}
		activeMethod.addAnnotation(rule);
		addImport(rule.getImportText());

		offset = offset+this.imports.size()+3 + this.classAnnotations.size();
		
		List<String> text = new ArrayList<String>();
		text.add("	@"+rule.getAnnotation()+"\n");
		
		addText(offset, text);
	}
	
	public Method getActiveMethod(){
		for(Method m: methods){
			if(m.isActive()){
				return m;
			}
		}
		return null;
	}

	public int getLastOffset() {
		return lastOffset;
	}

	public void setLastOffset(int lastOffset) {
		this.lastOffset = lastOffset;
	}
	
	

	public List<AnnotationRule> getClassAnnotations() {
		return classAnnotations;
	}

	private boolean isAnnotationPresent(AnnotationRule rule){
		if(this.classAnnotations.contains(rule)){
			return true;
		}
		for(Method m: methods){
			for(AnnotationRule r: m.getAnnotations()){
				if(r.equals(rule)){
					return true;
				}
			}
		}
		return false;
	}

	public int getMethodLinesB() {
		int i =0;
		int methodLinesB = this.imports.size()+3 + this.classAnnotations.size();
		
		Method activeMethod = methods.get(i);
		while(!activeMethod.isActive()){
			i++;
			methodLinesB = methodLinesB+activeMethod.getCode().size()+3;
			methodLinesB = methodLinesB+ activeMethod.getAnnotations().size();
			activeMethod = methods.get(i);
		}
		return methodLinesB;
	}
	
	public void updateColoring(){
		if(viewer != null){
			if(getActiveMethod() != null){
				viewer.getTextWidget().setLineBackground(0,viewer.getTextWidget().getLineCount() , viewer.getTextWidget().getLineBackground(0));
				Color orange = new Color(Display.getCurrent(), 205, 205, 201);
				viewer.getTextWidget().setLineBackground(getMethodLinesB(), getActiveMethod().getAnnotations().size() + getActiveMethod().getCode().size() +2, orange);
					setLastOffset(getMethodLinesB()+getActiveMethod().getAnnotations().size() + getActiveMethod().getCode().size());
			}
		}
	}

	public void setViewer(SourceViewer generatedCode) {
		viewer = generatedCode;
		
	}
	
	public List<Method> getMethods(){
		return methods;
	}
	
}
