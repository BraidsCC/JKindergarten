package braids.game.util.kindergarten;

import static braids.game.util.kindergarten.KinFunctions.isKID;

public interface Ownable {

	public Integer getOwnerKID();
    
	public void setOwnerKID(Integer ownerKID);
    
	
    default Object getOwner(IDResolverImpl resolver) {
    	return resolver.dref(getOwnerKID());
    }

    default void setOwner(Object ownerOrOwnerKID) {
        Integer ownerKID;
        
		if (ownerOrOwnerKID == null || isKID(ownerOrOwnerKID)) {
            ownerKID = (Integer) ownerOrOwnerKID;
        }
        else {
            ownerKID = ((KIDAware) ownerOrOwnerKID).getKID();
        }
        
        setOwnerKID(ownerKID);
    }
    
}
