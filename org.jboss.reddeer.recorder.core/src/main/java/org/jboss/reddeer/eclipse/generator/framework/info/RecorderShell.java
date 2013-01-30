package org.jboss.reddeer.eclipse.generator.framework.info;

public class RecorderShell {
	
	private String shellName;
	private boolean active;
	
	public RecorderShell(String shellName){
		this.shellName=shellName;
	}

	public String getShellName() {
		return shellName;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((shellName == null) ? 0 : shellName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecorderShell other = (RecorderShell) obj;
		if (shellName == null) {
			if (other.shellName != null)
				return false;
		} else if (!shellName.equals(other.shellName))
			return false;
		return true;
	}
	
	

}
