package org.jboss.reddeer.eclipse.generator.framework.rules.annotation;

import org.eclipse.swtbot.generator.framework.AnnotationRule;

public class TestAnnotationRule extends AnnotationRule{
	
	public TestAnnotationRule(){
		setAnnotation("Test");
		setImportText("org.junit.Test");
	}

}
