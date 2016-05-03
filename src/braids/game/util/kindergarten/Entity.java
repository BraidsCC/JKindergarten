package braids.game.util.kindergarten;

import static braids.util.UFunctions.*;

import braids.util.python.port.*;

/**
 * An Entity is loosely defined; it usually represents a card, tile, or other
 * game piece. Each entity instance resides in exactly one zone. (Entities may
 * temporarily exist outside of a zone during creation.)
 * 
 * For rules-enforced implementations, an entity represents something that can
 * respond to queries for possible operations.
 **/
public class Entity extends KRoot 
//implements Vetoer, Surgeon
{

    private Integer __locationKID;
	//private KRoot data = new KRoot(new VisibilityTrackerImpl());


	public Entity(IDResolver resolver, String name) {
    	setName(name);
    	setResolver(resolver);
    	setLocationKID(null);
    }

    
    public Entity(IDResolver resolver) {
    	this(resolver, "");
    }


	private void setResolver(IDResolver resolver) {
		if (!resolver.hasKID(this)) {
			resolver.createKIDFor(this);
		}
    }

	@Override
	public String toString() {
    	String result = "";
        try {
        	result = "Entity " + repr(getName()) + "(#" + getKID() + ")";
        }
        catch (ValueError err) {
            result = "Entity " + repr(getName()) + " (KID UNKNOWN)";
        }
        
        return result;
    }

    
    public String toDebugString() {
        return String.format("<%s(%s)>", "Entity", repr(this));
    }

    public int getLocationKID() {
        return __locationKID;
    }
    
    public Zone getLocation(IDResolverImpl resolver) {
        return (Zone) resolver.dref(getLocationKID());
    }

    public void setLocationKID(Integer zoneKID) {
        __locationKID = zoneKID;
    }
    
    public void setLocation(IDResolverImpl resolver, Zone zone) {
        setLocationKID(resolver.getOrCreateKID(zone));
    }


}
