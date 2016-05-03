package braids.game.util.kindergarten;

import java.util.HashSet;
import java.util.Set;

public class VisibilityTrackerImpl implements VisibilityTracker {
	private Set<Integer> __allowedViewerKIDSet;
	private Set<Integer> __deniedViewerKIDSet;
	private boolean __defaultVisibility;

	public VisibilityTrackerImpl() {
        clearVisibilityLists();
        setDefaultVisibility(true);
    }

    public void clearVisibilityLists() {
        __allowedViewerKIDSet = new HashSet<Integer>();
        __deniedViewerKIDSet = new HashSet<Integer>();
    }

    public boolean getDefaultVisibility() {
        return __defaultVisibility;
    }

    public boolean isVisibleTo(Object playerOrKID) {
        Integer playerKID = KinFunctions.toKID(playerOrKID);
        if (__allowedViewerKIDSet.contains(playerKID)) {
            return true;
        }
        else if (__deniedViewerKIDSet.contains(playerKID)) {
            return false;
        }
        else {
            return __defaultVisibility;
        }
    }
        
    public void setDefaultVisibility(Boolean visible) {
        __defaultVisibility = visible;
    }

    public void setVisibleFor(Object playerOrKID) {
        Integer playerKID = KinFunctions.toKID(playerOrKID);
        __allowedViewerKIDSet.add(playerKID);
        __deniedViewerKIDSet.remove(playerKID);
    }


    public void setInvisibleFor(Object playerOrKID) {
        Integer playerKID = KinFunctions.toKID(playerOrKID);
        __deniedViewerKIDSet.add(playerKID);
        __allowedViewerKIDSet.remove(playerKID);
    }

    public Set<Integer> getAllowedViewerKIDSet() {
        return __allowedViewerKIDSet;
    }

    public Set<Integer> getDeniedViewerKIDSet() {
        return __deniedViewerKIDSet;
    }

    public void setAllowedViewerKIDSet(Set<Integer> value) {
        __allowedViewerKIDSet = value;
    }

    public void setDeniedViewerKIDSet(Set<Integer> value) {
        __deniedViewerKIDSet = value;
    }

}
