package org.jboss.reddeer.recorder.core.event;

import java.util.Set;

public class StyledTextRecorderEvent extends RecorderEvent{

	@Override
	public Set<String> start(StringBuilder testBuilder, RecorderEvent previousEvent) {
		return setOfImports;
		
	}

}
