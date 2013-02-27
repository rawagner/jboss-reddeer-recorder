package org.jboss.reddeer.recorder.core.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class ChooseProjectWizard extends Wizard implements INewWizard {

    private IStructuredSelection selection;
    private ChooseProjectPage chooseProjectWizardPage;
    private IWorkbench workbench;
 

    public ChooseProjectWizard() {

        setWindowTitle("New Config File");

    } 





    @Override
    public void addPages() {

        chooseProjectWizardPage = new ChooseProjectPage(selection);
        addPage(chooseProjectWizardPage);
    }
    
    @Override
    public boolean performFinish() {
        
        IFile file = chooseProjectWizardPage.createNewFile();
        if (file != null)
            return true;
        else
            return false;
    }

    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.workbench = workbench;
        this.selection = selection;
    }
}

