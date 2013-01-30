package org.jboss.reddeer.recorder.core.wizard;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.jboss.reddeer.recorder.core.action.RecordingState;

public class RecordTestWizardPage extends WizardPage {
	
	private Text text1;
	private Composite container;
	private RecordingState recordingStateService;
	
	public RecordTestWizardPage(RecordingState recordingStateService) {
		super("Record RedDeer Test");
		this.recordingStateService=recordingStateService;
		setTitle("Record RedDeer Test");
	}

	public String getText1(){
		return text1.getText();
	}
	
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		Label label1 = new Label(container, SWT.NULL);
		label1.setText("Test name: ");

		text1 = new Text(container, SWT.BORDER | SWT.SINGLE);
		text1.setText("");
		text1.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			public void keyReleased(KeyEvent e) {
				if (!text1.getText().isEmpty()) {
					recordingStateService.setTestName(text1.getText());
					setPageComplete(true);
				}
			}

		});
		/*
		Button chooseProjectButton = new Button(container, SWT.PUSH);
		chooseProjectButton.setText("Choose project");
		chooseProjectButton.addMouseListener(new MouseListener() {
			
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseDown(MouseEvent arg0) {
				WizardDialog dialog = new WizardDialog(container.getShell(), new ChooseProjectWizard());
				dialog.open(); 
			}
			
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		*/
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		text1.setLayoutData(gd);
		// Required to avoid an error in the system
		setControl(container);
		setPageComplete(false);
	}

}
