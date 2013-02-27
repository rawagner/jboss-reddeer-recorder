package org.jboss.reddeer.recorder.core.wizard;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

public class ChooseProjectPage extends WizardNewFileCreationPage {

    public ChooseProjectPage(IStructuredSelection selection) {
        super("ChooseProjectPage", selection);
        setTitle("Config File");
        setDescription("Creates a new Config File");
        setFileExtension("config");
    }

    @Override
    protected InputStream getInitialContents() {
    	   return null;
    }
}