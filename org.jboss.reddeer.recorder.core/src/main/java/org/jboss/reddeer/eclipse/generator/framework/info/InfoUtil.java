package org.jboss.reddeer.eclipse.generator.framework.info;

import java.util.HashSet;
import java.util.Set;

public class InfoUtil {
	
	private Set<RecorderShell> shells = new HashSet<RecorderShell>();

	public Set<RecorderShell> getShells() {
		return shells;
	}

	public void setShells(Set<RecorderShell> shells) {
		this.shells = shells;
	}

}
