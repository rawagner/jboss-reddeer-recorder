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
package org.eclipse.swtbot.generator.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swtbot.generator.framework.GenerationComplexRule;
import org.eclipse.swtbot.generator.framework.GenerationRule;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.eclipse.swtbot.generator.framework.Generator;
import org.eclipse.swtbot.generator.framework.WidgetUtils;

public class BotGeneratorEventDispatcher implements Listener {

	public static interface CodeGenerationListener {
		public void handleCodeGenerated(List<String> code, String importCode);
	}

	private List<GenerationSimpleRule> listOfSimpleRules = new ArrayList<GenerationSimpleRule>();
	private List<GenerationRule> listOfRules = new ArrayList<GenerationRule>();

	private Generator generator;
	private List<CodeGenerationListener> listeners = new ArrayList<CodeGenerationListener>();
	private Shell ignoredShell;
	private boolean recording;

	private GenerationComplexRule longestMatchedComplex;
	private GenerationStackRule longestMatchedStack;

	private boolean newGenerationComplexRules = true;
	private boolean newGenerationStackRules = true;
	private List<GenerationComplexRule> modifGenerationComplexRules = new ArrayList<GenerationComplexRule>();
	private List<GenerationStackRule> modifGenerationStackRules = new ArrayList<GenerationStackRule>();
	private GenerationStackRule openedStack;

	public void setGenerator(Generator generator) {
		this.generator = generator;
	}

	public void addListener(CodeGenerationListener listener) {
		this.listeners.add(listener);
	}

	public void ignoreShell(Shell shell) {
		this.ignoredShell = shell;
	}

	public boolean isRecording() {
		return this.recording;
	}
	
	public void setRecording(boolean record) {
		this.recording = record;
	}


	public Generator getCurrentGenerator() {
		return this.generator;
	}

	public void handleEvent(Event event) {
		if (!recording) {
			return;
		}
		if (event.widget instanceof Control) {
			Shell shell = WidgetUtils.getShell((Control) event.widget);
			if (shell.getParent() instanceof Shell) {
				if (this.ignoredShell != null && this.ignoredShell.equals(shell.getParent())){
					return;
				}
			}
		} else if(event.widget instanceof Item){
			Shell shell=null;
			if(event.widget instanceof MenuItem){
				shell = WidgetUtils.getShell(((MenuItem) event.widget).getParent().getParent());
			} else if(event.widget instanceof TabItem){
				shell = WidgetUtils.getShell(((TabItem) event.widget).getParent());
			} else if(event.widget instanceof ToolItem){
				shell = WidgetUtils.getShell(((ToolItem) event.widget).getParent());
			}
			if (shell != null && shell.getParent() instanceof Shell) {
				if (this.ignoredShell != null && this.ignoredShell.equals(shell.getParent())){
					return;
				}
			}
		}
		
		if (event.widget instanceof Control) {
			Shell shell = WidgetUtils.getShell((Control) event.widget);
			if (shell.getText().equals("Add new method")){
					return;
			}
		}
		
		if (!(event.widget instanceof Shell)
				&& event.widget instanceof Control
				&& !(((Control) event.widget).isFocusControl()
						&& ((Control) event.widget).isVisible() && ((Control) event.widget)
							.isEnabled())) {
			return;
		}
		processRules(event);
	}

	private void processRules(Event event) {
		if (event != null) {
			for (GenerationSimpleRule rule : generator.createSimpleRules()) {
				if (rule.appliesTo(event)) {
					rule.initializeForEvent(event);
					listOfSimpleRules.add(rule); // store simple rule for
													// further processing
				}
			}
		}
		if (!listOfSimpleRules.isEmpty()) { // find complex rules

			if (newGenerationComplexRules) {
				modifGenerationComplexRules.addAll(generator
						.createComplexRules());
			}
			newGenerationComplexRules = true;
			Set<GenerationComplexRule> toDelete = matchComplexRules(modifGenerationComplexRules);
			modifGenerationComplexRules.removeAll(toDelete);
			if (modifGenerationComplexRules.isEmpty()) {
				if (longestMatchedComplex != null) {
					processComplexMatch();
				} else {
					processComplexNoMatch();
				}
			} else {
				newGenerationComplexRules = false; // get next event
				return;
			}
		}
		if (!listOfRules.isEmpty()) {
			if (newGenerationStackRules) {
				if (generator.createStackRules() != null) {
					modifGenerationStackRules.addAll(generator
							.createStackRules());
					if (openedStack != null) {
						modifGenerationStackRules.addAll(openedStack
								.getMethods());
					}
				}
			}
			newGenerationStackRules = true;
			Set<GenerationStackRule> toDelete = matchStackRules(modifGenerationStackRules);
			modifGenerationStackRules.removeAll(toDelete);
			if (modifGenerationStackRules.isEmpty()) {
				if (longestMatchedStack != null) {
					processStackMatch();
				} else {
					processStackNoMatch();
				}
			} else {
				newGenerationStackRules = false; // get next event
				return;
			}
		}

	}

	private void processComplexMatch() {
		listOfSimpleRules.removeAll(longestMatchedComplex
				.getInitializationRules());
		// dispatchCodeGenerated(WidgetUtils.cleanText(longestMatchedComplex.generateCode()));
		listOfRules.add(longestMatchedComplex);
		longestMatchedComplex = null;
		processRules(null); // process rest of the listOfSimpleRules
	}

	private void processComplexNoMatch() {
		listOfRules.add(listOfSimpleRules.get(0));
		// dispatchCodeGenerated(WidgetUtils.cleanText(listOfSimpleRules.get(0).generateCode()));
		// //generate code for first simple rule
		listOfSimpleRules.remove(0);
		processRules(null); // proces listOfSimpleRules without the first
							// element
	}

	private Set<GenerationComplexRule> matchComplexRules(
			List<GenerationComplexRule> modifGenerationComplexRules) {
		Set<GenerationComplexRule> toDelete = new HashSet<GenerationComplexRule>();
		for (int i = 0; i < listOfSimpleRules.size(); i++) {
			for (GenerationComplexRule complexRule : modifGenerationComplexRules) {
				if (!complexRule
						.appliesToPartially(listOfSimpleRules.get(i), i)) {
					toDelete.add(complexRule);
				} else {
					List<GenerationSimpleRule> modifSimpleRules = new ArrayList<GenerationSimpleRule>();
					modifSimpleRules.addAll(listOfSimpleRules);
					GenerationComplexRule matchedComplex = null;
					while (matchedComplex == null
							&& !modifSimpleRules.isEmpty()) {
						if (complexRule.appliesTo(modifSimpleRules)) {
							matchedComplex = complexRule;
							matchedComplex.initializeForRules(modifSimpleRules);
						} else {
							modifSimpleRules
									.remove(modifSimpleRules.size() - 1);
						}
					}
					if (matchedComplex != null) {
						if (longestMatchedComplex == null) {
							longestMatchedComplex = matchedComplex;
						} else {
							// check if we have found a better match
							if (matchedComplex.getInitializationRules().size() > longestMatchedComplex
									.getInitializationRules().size()) {
								longestMatchedComplex = matchedComplex;
							}
						}
					}
					matchedComplex = null;
				}
			}
		}
		return toDelete;
	}

	private void processStackMatch() {
		List<GenerationRule> toKeep = new ArrayList<GenerationRule>();
		int keep = listOfRules.size()
				- longestMatchedStack.getInitializationRules().size();
		for (int i = keep; i > 0; i--) {
			toKeep.add(listOfRules.get(listOfRules.size() - i));
		}
		dispatchCodeGenerated(longestMatchedStack.getActions(),longestMatchedStack.getImport());
		if (longestMatchedStack.getMethods() != null) {
			openedStack = longestMatchedStack;
		}
		listOfRules.clear();
		listOfRules.addAll(toKeep);
		longestMatchedStack = null;
		processRules(null); // process rest of the listRules
	}

	private void processStackNoMatch() {
		dispatchCodeGenerated(listOfRules.get(0).getActions(),listOfRules.get(0).getImport()); // generate code for first simple rule
		listOfRules.remove(0);
		processRules(null); // process listRules without the first element
	}

	private Set<GenerationStackRule> matchStackRules(List<GenerationStackRule> modifGenerationStackRules) {
		Set<GenerationStackRule> toDelete = new HashSet<GenerationStackRule>();
		Map<GenerationStackRule, Integer> ignoredIndex = new HashMap<GenerationStackRule, Integer>();
		for (int i = 0; i < listOfRules.size(); i++) {
			for (GenerationStackRule stackRule : modifGenerationStackRules) {
					int indexCorrection = ignoredIndex.get(stackRule) != null ? i-ignoredIndex.get(stackRule):i;
					if (!stackRule.appliesToPartially(listOfRules.get(i), indexCorrection)) {
						toDelete.add(stackRule);
					} else {
						List<GenerationRule> modifRules = new ArrayList<GenerationRule>();
						modifRules.addAll(listOfRules);
						GenerationStackRule matchedStack = null;
						while (matchedStack == null && !modifRules.isEmpty()) {
							if (stackRule.appliesTo(modifRules)) {
								matchedStack = stackRule;
								matchedStack.initializeForRules(modifRules);
							} else {
								modifRules.remove(modifRules.size() - 1);
							}
						}
						if (matchedStack != null) {
							if (longestMatchedStack == null) {
								longestMatchedStack = matchedStack;
							} else {
								// check if we have found a better match
								if (matchedStack.getInitializationRules()
										.size() > longestMatchedStack
										.getInitializationRules().size()) {
									longestMatchedStack = matchedStack;
								}
							}
						}
						matchedStack = null;
					}
			}
		}
		return toDelete;
	}

	private void dispatchCodeGenerated(List<String> code, String importCode) {
		if (code != null) {
			for (CodeGenerationListener listener : this.listeners) {
				listener.handleCodeGenerated(code,importCode);
			}
		}
	}

}