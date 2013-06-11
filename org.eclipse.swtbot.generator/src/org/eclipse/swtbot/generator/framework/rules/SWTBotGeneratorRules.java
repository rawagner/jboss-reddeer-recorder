/*******************************************************************************
 * Copyright (c) 2012 Red Hat Inc..
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Mickael Istria (Red Hat) - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.generator.framework.rules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swtbot.generator.framework.AnnotationRule;
import org.eclipse.swtbot.generator.framework.GenerationComplexRule;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.eclipse.swtbot.generator.framework.Generator;

public class SWTBotGeneratorRules implements Generator {

	public List<GenerationSimpleRule> createSimpleRules() {
		List<GenerationSimpleRule> res = new ArrayList<GenerationSimpleRule>();

		
		return res;
		
	}

	public String getLabel() {
		return "SWTBot";
	}

	public List<GenerationComplexRule> createComplexRules() {
		List<GenerationComplexRule> cres = new ArrayList<GenerationComplexRule>();
		return cres;
	}

	public List<GenerationStackRule> createStackRules() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<AnnotationRule> createAnnotationRules() {
		// TODO Auto-generated method stub
		return null;
	}

	public Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}
}