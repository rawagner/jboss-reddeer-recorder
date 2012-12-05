package org.jboss.reddeer.recorder.core.listener;

import java.util.Calendar;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;

public class JobListener implements IJobChangeListener{

	Long time;

	public void aboutToRun(IJobChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void awake(IJobChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void done(IJobChangeEvent arg0) {
		if(arg0.getJob().isUser()){
			Long lenght = Calendar.getInstance().getTimeInMillis() - time;
			//if(lenght>=1000){
				System.out.println(lenght+ " lenght, name: "+arg0.getJob().getName());
			//}
		}
		
	}

	public void running(IJobChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void scheduled(IJobChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void sleeping(IJobChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
