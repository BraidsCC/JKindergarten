package braids.game.util.kindergarten;

import java.util.Set;

public interface VisibilityTracker {

    public void clearVisibilityLists();

    public boolean getDefaultVisibility();

    public boolean isVisibleTo(Object playerOrKID);
        
    default boolean isGenerallyVisible() {
        return getDefaultVisibility();
    }

    public void setDefaultVisibility(Boolean visible);
    
    public void setVisibleFor(Object playerOrKID);

    public void setInvisibleFor(Object playerOrKID);
    
    public Set<Integer> getAllowedViewerKIDSet();

    public Set<Integer> getDeniedViewerKIDSet();

    public void setAllowedViewerKIDSet(Set<Integer> value);

    public void setDeniedViewerKIDSet(Set<Integer> value);

}
