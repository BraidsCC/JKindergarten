package braids.game.util.kindergarten;

import java.util.*;

import braids.util.*;

/**
 * Root class for things within the game state.  Provides implementation of
 * multiple useful interfaces.
 * 
 * @author Braids
 *
 */
public class KRoot 
implements KIDAware, NamedThing, Ownable, VisibilityTracker
{
	public int kID = 0;
	public VisibilityTracker visibilityTracker = new VisibilityTrackerImpl();
	public Ownable ownable = new OwnableImpl(null);
	public NamedThing namedThing = new NamedThingImpl("");

	/**
	 * Constructor.  Sets most values to null, "", 0, etc.
	 */
	public KRoot() {
		// For initializations, see fields.
	}

	@Override
    public void clearVisibilityLists() {
		visibilityTracker.clearVisibilityLists();
    }


	@Override
    public boolean getDefaultVisibility() {
		return visibilityTracker.getDefaultVisibility();
    }


	@Override
    public boolean isVisibleTo(Object playerOrKID) {
		return visibilityTracker.isVisibleTo(playerOrKID);
    }


	@Override
    public void setDefaultVisibility(Boolean visible) {
		visibilityTracker.setDefaultVisibility(visible);
    }


	@Override
    public void setVisibleFor(Object playerOrKID) {
		visibilityTracker.setVisibleFor(playerOrKID);
    }


	@Override
    public void setInvisibleFor(Object playerOrKID) {
		visibilityTracker.setInvisibleFor(playerOrKID);
    }


	@Override
    public Set<Integer> getAllowedViewerKIDSet() {
		return visibilityTracker.getAllowedViewerKIDSet();
    }


	@Override
    public Set<Integer> getDeniedViewerKIDSet() {
		return visibilityTracker.getDeniedViewerKIDSet();
    }


	@Override
    public void setAllowedViewerKIDSet(Set<Integer> value) {
		visibilityTracker.setAllowedViewerKIDSet(value);
    }


	@Override
    public void setDeniedViewerKIDSet(Set<Integer> value) {
		visibilityTracker.setDeniedViewerKIDSet(value);
    }


	@Override
    public Integer getOwnerKID() {
		return ownable.getOwnerKID();
    }


	@Override
    public void setOwnerKID(Integer ownerKID) {
		ownable.setOwnerKID(ownerKID);
    }


	@Override
    public void setKID(int kID) {
		this.kID = kID;
    }


	@Override
    public Integer getKID() {
		return kID;
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