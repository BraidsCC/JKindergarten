package braids.game.util.kindergarten;

import braids.util.*;

public class ParticipantImpl implements Participant {
	
	private KIDAware kidAware = new KIDAwareImpl();
	private NamedThing namedThing = new NamedThingImpl();

	public ParticipantImpl() {
		super();  // only exists as a debugging breakpoint anchor
	}

	public String toString() {
    	return defaultParticipantToString();
    }

	
    public Integer getKID() {
    	return kidAware.getKID();
    }

	@Override
    public void setKID(int kID) {
	    kidAware.setKID(kID);
    }

	@Override
    public void setName(String name) {
		namedThing.setName(name);
    }

	@Override
    public String getName() {
		return namedThing.getName();
    }

}
