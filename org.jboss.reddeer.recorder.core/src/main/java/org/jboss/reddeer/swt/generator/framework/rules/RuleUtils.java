package org.jboss.reddeer.swt.generator.framework.rules;

public class RuleUtils {
	
	public static final String SECTION_IMPORT = "org.jboss.reddeer.uiforms.section.UIFormSection";
	
	public static String getSectionRule(String sectionName){
		return "new UIFormSection(\""+sectionName+"\")";
	}
	
	public static String getGroupRule(String groupName){
		return null;
	}

}
