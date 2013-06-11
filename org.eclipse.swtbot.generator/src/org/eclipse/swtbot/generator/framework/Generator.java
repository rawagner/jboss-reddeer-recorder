/*******************************************************************************
 * Copyright (c) 2012 Red Hat Inc..
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Mickael Istria (Red Hat) - initial API and implementation
 *    Rastislav Wagner (Red Hat) - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.generator.framework;

import java.util.List;

import org.eclipse.swt.graphics.Image;

public interface Generator {

	public List<GenerationSimpleRule> createSimpleRules();
	public List<GenerationComplexRule> createComplexRules();
	public List<GenerationStackRule> createStackRules();
	public String getLabel();
	public Image getImage();
	public List<AnnotationRule> createAnnotationRules();

}