package braids.game.util.kindergarten;

import static braids.game.util.kindergarten.KinFunctions.*;

public class OwnableImpl implements Ownable {
	public OwnableImpl(Object ownerOrOwnerKID) {
        setOwner(ownerOrOwnerKID);
    }
    
    private Integer __ownerKID;

    public Integer getOwnerKID() {
        return __ownerKID;
    }
    
    public void setOwnerKID(Integer ownerKID) {
        checkTypeIsNoneOrKID("ownerKID", ownerKID);
        __ownerKID = ownerKID;
    }
}
