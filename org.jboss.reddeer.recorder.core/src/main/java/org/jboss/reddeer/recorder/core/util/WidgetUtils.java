package org.jboss.reddeer.recorder.core.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.PlatformUI;

public class WidgetUtils {
	
	public static String getShellName(Widget w){
		String defaultShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().getText();
		String currentShell = PlatformUI.getWorkbench().getDisplay().getActiveShell().getText();
		if(defaultShell.equals(currentShell)){
			return null;
		}
		return PlatformUI.getWorkbench().getDisplay().getActiveShell().getText();
	}
	
	public static String getViewName(){
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart().getTitle();
	}

	public static String convert(String text) {
		if(text	!= null){
			return text.replaceAll("&", "").split("\t")[0];
		} 
		return null;
	}
	
	public static boolean treeHasDuplicatedItems(Tree tree,List<String> parents){
		int i = 0;
		TreeItem[] items = tree.getItems();
		while(i<parents.size()){
			int itemsCount = tree.getItemCount();
			int j = 0;
			while(j<itemsCount){
				if(items[j].getText().equals(parents.get(i))){
					i++;
					items = items[j].getItems();
					break;
				} else {
					j++;
				}
			}
			//throw new IllegalArgumentException("");
		}
		
		Set<String> setOfTexts = new HashSet<String>();
		for(TreeItem treeItem:items){
			boolean added = setOfTexts.add(treeItem.getText());
			if(!added){
				return true;
			}
		}

		return false;
	}
	
	public static int getSelectedTreeItemIndex(Tree tree,List<String> parents){
		
		int i = 0;
		TreeItem[] items = tree.getItems();
		while(i<parents.size()){
			int itemsCount = tree.getItemCount();
			int j = 0;
			while(j<itemsCount){
				if(items[j].getText().equals(parents.get(i))){
					i++;
					items = items[j].getItems();
					break;
				} else {
					j++;
				}
			}
			//throw new IllegalArgumentException("");
		}
		
		TreeItem selectedIt = tree.getSelection()[0];
		int same=-1;
		for(int x=0;x<items.length;x++){
			if(items[x].getText().equals(selectedIt.getText())){
				same++;
			}
			if(items[x].equals(selectedIt)){
				return same;
			}
		}
		return 0;
	}
	
	public static String getLabel(Widget widget){
		String label = null;
		if(widget instanceof Combo){
			Control[] controls = ((Combo) widget).getParent().getChildren();
			for (int i = 0; i < controls.length; i++) {
				if (controls[i] instanceof Label && controls[i + 1].equals(widget)) {
					label = ((Label) controls[i]).getText();
					break;
				}
			}
		} else if(widget instanceof Text){
			Control[] controls = ((Text) widget).getParent().getChildren();
			for (int i = 0; i < controls.length; i++) {
				if (controls[i] instanceof Label && controls[i + 1].equals(widget)) {
					label = ((Label) controls[i]).getText();
					break;
				}
			}
		} else{
			throw new UnsupportedOperationException("finding label for " +widget.getClass()+ " not yet implemented.");
		}
		return label;
	}

	public static String getGroup(Widget widget){
		Composite parent = null;
		if(widget instanceof Combo){
			parent = ((Combo) widget).getParent();
			while(parent != null){
				if(parent instanceof Group){
					return ((Group)parent).getText();
				}
				parent = parent.getParent();
			}
		} else if(widget instanceof Button){
			parent = ((Button) widget).getParent();
			while(parent != null){
				if(parent instanceof Group){
					return ((Group)parent).getText();
				}
				parent = parent.getParent();
			}
		} else if(widget instanceof Text){
			parent = ((Text) widget).getParent();
			while(parent != null){
				if(parent instanceof Group){
					return ((Group)parent).getText();
				}
				parent = parent.getParent();
			}
		} else if(widget instanceof Table){
			parent = ((Table) widget).getParent();
			while(parent != null){
				if(parent instanceof Group){
					return ((Group)parent).getText();
				}
				parent = parent.getParent();
			}
		} else {
			throw new UnsupportedOperationException("finding group for " +widget.getClass()+ " not yet implemented.");
		}
		return null;

	}
	
	public static int getIndex(Widget widget){
		int index = 0;
		List<Composite> parents= new ArrayList<Composite>();
		Composite parent = null;
		if(widget instanceof Tree){
			parent = ((Tree) widget).getParent();
			
			if(parent != null){
				while(!(parent instanceof Shell)){
					parents.add(parent);
					parent = parent.getParent();
				}

				parents.add(parent);
				for(Composite comp: parents){
					for(Control c: comp.getChildren()){
						if(c instanceof Tree){
							if(c.equals(widget)){
								return index;
							}
						index++;
						}
					}
				}
			}
		} else if(widget instanceof Button){
			parent = ((Button) widget).getParent();
			
			if(parent != null){
				while(!(parent instanceof Shell)){
					parents.add(parent);
					parent = parent.getParent();
				}

				parents.add(parent);
				for(Composite comp: parents){
					for(Control c: comp.getChildren()){
						if(c instanceof Button){
							if(c.equals(widget)){
								return index;
							}
						index++;
						}
					}
				}
			}
		} else if(widget instanceof Text){
			parent = ((Text) widget).getParent();
			
			if(parent != null){
				while(!(parent instanceof Shell)){
					parents.add(parent);
					parent = parent.getParent();
				}

				parents.add(parent);
				for(Composite comp: parents){
					for(Control c: comp.getChildren()){
						if(c instanceof Text){
							if(c.equals(widget)){
								return index;
							}
						index++;
						}
					}
				}
			}
		} else if(widget instanceof Table){
			parent = ((Table) widget).getParent();
			
			if(parent != null){
				while(!(parent instanceof Shell)){
					parents.add(parent);
					parent = parent.getParent();
				}

				parents.add(parent);
				for(Composite comp: parents){
					for(Control c: comp.getChildren()){
						if(c instanceof Table){
							if(c.equals(widget)){
								return index;
							}
						index++;
						}
					}
				}
			}
		} else if(widget instanceof Combo){
			parent = ((Combo) widget).getParent();
			
			if(parent != null){
				while(!(parent instanceof Shell)){
					parents.add(parent);
					parent = parent.getParent();
				}

				parents.add(parent);
				for(Composite comp: parents){
					for(Control c: comp.getChildren()){
						if(c instanceof Combo){
							if(c.equals(widget)){
								return index;
							}
						index++;
						}
					}
				}
			}
		} else if(widget instanceof Table){
			parent = ((Table) widget).getParent();
			
			if(parent != null){
				while(!(parent instanceof Shell)){
					parents.add(parent);
					parent = parent.getParent();
				}

				parents.add(parent);
				for(Composite comp: parents){
					for(Control c: comp.getChildren()){
						if(c instanceof Table){
							if(c.equals(widget)){
								return index;
							}
						index++;
						}
					}
				}
			}
		}
		throw new UnsupportedOperationException("not supported for "+widget.getClass());
	}
}
