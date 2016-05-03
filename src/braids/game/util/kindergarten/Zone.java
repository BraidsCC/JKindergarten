package braids.game.util.kindergarten;

import java.util.*;

import braids.util.*;
import braids.util.python.port.IndexError;

import static braids.game.util.kindergarten.KinFunctions.*;
import static braids.util.UFunctions.*;
import static braids.util.python.port.PyFunctions.*;

/**
 * Contains an ordered list of entities referenced by their KIDs.
 * Use this to represent decks, discard piles, deployment-areas, etc.
 */
public class Zone extends KRoot 
implements HasReprMethod 
{
	// If this zone is a stack, the top is at the end of this list.
	private List<Integer> __contentsBottomIsFirst = new ArrayList<>();

	/** 
	 * @param ownerOrOwnerKID - can be an object or a KID
	 */
	public Zone(IDResolver resolver, Object ownerOrOwnerKID) {

		setResolver(resolver);
		setName("");

		ownable = new OwnableImpl(ownerOrOwnerKID);
	}

	private void setResolver(IDResolver resolver) {
    }

	public Zone(IDResolver resolver) {
		this(resolver, null);
	}

	
	public String toString() {
		//return "%s(KID=%s, ownerKID=%s, kind=%s)" %\
		return String.format("%s(KID=%s, ownerKID=%s)",
		 getClass().getName(), repr(getKID()), repr(getOwnerKID()));
	}	

	public String __repr__() {
		return "<" + toString() + ">";
	}
		
	
	public List<Object> _aggregateContents(IDResolver resolver, String methodName, Object[] argumentTuple) {
		throw new NYIException();  // TODO code _aggregateContents
		
		/*-commented out-*
		result = new ArrayList<>();
		for (entityKID in __contentsBottomIsFirst) {
			entity = resolver.dref(entityKID);
			
			subresult = apply(
			 getattr(entity, methodName),
			 argumentTuple);

			result.extend(subresult);
		}
		
		return result;
		*/
	}

	/**
	 * Move the entity at targetIx (in __contentsBottomIsFirst) to the bottom 
	 * of the target zone.
	 */
	public void _moveIndexToBottomOfOtherZone(IDResolver resolver, int targetIx, 
			Zone targetZone) 
	{
		if (targetIx < 0) {
			targetIx += __contentsBottomIsFirst.size();
		}
		
		// Register the change in location with the entity being moved.
		int entityKID = __contentsBottomIsFirst.get(targetIx);
		Entity entity = (Entity) resolver.dref(entityKID);

		// Add the entity to the target zone object.
		targetZone.addEntityToBottom(entity);

		// Remove the entity from this zone object.
		__contentsBottomIsFirst.remove(targetIx);
	}

	public void addEntityToBottom(Entity entity) {
		__contentsBottomIsFirst.add(0, entity.getKID());
		assimilateEntity(entity);		
	}
	
	public void addEntityToBottomByKID(IDResolver resolver, int entityKID) {
		Entity entity = (Entity) resolver.dref(entityKID);
		/*return*/ addEntityToBottom(entity);
	}

	public void addEntityToTop(Entity entity) {
		__contentsBottomIsFirst.add(entity.getKID());
		assimilateEntity(entity);		
	}	

	public void addEntityToTopByKID(IDResolver resolver, int entityKID) {
		Entity entity = (Entity) resolver.dref(entityKID);
		/*return*/ addEntityToTop(entity);
	}
	
	/** 
	 * Move count entities from the top (or bottom) of this zone to
	 * the target zone.
	 */
	public void draw(IDResolver resolver, Zone targetZone, 
			int count /*=1*/, 
			boolean fromBottom /*=false*/) 
	{

		int index;

		if (!fromBottom) {
			index = -1;  // last item is the top.
		}
		else {
			index = 0; // first item is the bottom.
		}
		
		for (int ignored = 0; ignored < count; ignored++) {
			_moveIndexToBottomOfOtherZone(resolver, index, targetZone);
		}
	}
	
	public Iterable<Entity> getContentsAsEntities(IDResolver resolver) {
		return map(resolver::dref, __contentsBottomIsFirst, Entity.class);
	}

	/**
	 * @return a loop iterator over the contents of this zone, which
	 * is a sequence of entity KIDs.  The sequence is in bottom-to-top order.
	 */
	public Iterable<Integer> getContentsAsKIDs() {		
		return __contentsBottomIsFirst;
	}

	/*-commented out-
	public void getKind() {
		throw new NYIExceptionX();
	}
	*/

	/*-commented out-
	public void getOperationList(GameState gameState, List<Object> vetoes) {
		return _aggregateContents(gameState, "getOperationList", 
									   (gameState, vetoes));
	}
	*/
	
	public int getSize() {
		return __contentsBottomIsFirst.size();
	}

	/**
	 * @return the KID of the entity at the top or bottom of this zone.
	 * 
	 * @param bottomFlag - if True, we return the KID at the bottom, otherwise, we
	 * return the one at the top.
	 * 
	 * @throw IndexError if this zone is empty.
	 */
	public int getTopOrBottomKID(boolean bottomFlag) {
		
		throw new NYIException(); //TODO code getTopOrBottomKID

		/*-commented out-
		if (bottomFlag) {
			result = __contentsBottomIsFirst.get(0);
		}
		else {
			result = __contentsBottomIsFirst.get(-1);
		}
		
		return result;
		*/
	}	
		
	public List<Object> getTriggerList(GameState gameState, Operation operation) {
		return _aggregateContents(gameState, "getTriggerList", 
									   new Object[] {gameState, operation});
	}

	public List<Object> getVetoList(GameState gameState) {
		return _aggregateContents(gameState, "getVetoList", 
				new Object[] {gameState});
	}

	public int indexByName(IDResolver resolver, String cardName) {
		int cardIx = 0;
		
		for (Integer cardKID : getContentsAsKIDs()) {
			Entity card = (Entity) resolver.dref(cardKID);
			
			if (card.getName() == cardName) {
				return cardIx;
			}
			else {
				cardIx = cardIx + 1;
			}
		}
		
		throw new IndexError(String.format(
				"could not find card named %s", repr(cardName)));
	}
	
	public void move(IDResolver resolver, Object entityOrKID, 
			Object destZoneOrKID, boolean toBottom) 
	{
		throw new NYIException();  // TODO code move method

		/*-commented out-
        Entity entity;
		
		if (isKID(entityOrKID, resolver)) {
			entity = (Entity) resolver.dref((Integer) entityOrKID);
		}
		else {
			entity = (Entity) entityOrKID;
		}
		
        Zone destZone;

		if (isKID(destZoneOrKID, resolver)) {
			destZone = (Zone) resolver.dref((Integer) destZoneOrKID);
		} else {
			destZone = (Zone) destZoneOrKID;	
		}
		
		// If the following raises an IndexError, then the kID was not in this 
		// zone.
		int cardIx = __contentsBottomIsFirst.indexOf(entity.getKID());
		
		__contentsBottomIsFirst = __contentsBottomIsFirst.get(:cardIx) +\
		 __contentsBottomIsFirst.get(cardIx+1:);
		
		if (toBottom) {
			destZone.addEntityToBottom(entity);
			
		} else {
			destZone.addEntityToTop(entity);
		}
		*/
	}
	
	/**
	 * Moves all entities in this zone to the target zone in an indeterminate
	 * order.
	 */
	public void moveAll(IDResolver resolver, Zone targetZone) {		
		draw(resolver, targetZone, getSize(), false);
	}

	public void moveByKIDToBottomOfSelf(int kID) {
		// If the following raises an IndexError, then the kID wasn"t in this 
		// zone.
		int cardIx = __contentsBottomIsFirst.indexOf(kID);
		
		if (cardIx == len(__contentsBottomIsFirst) - 1) {
			// It"s already at the top.  Avoid expensive list operation.
			return;
		}
		
		throw new NYIException(); // TODO finish moveByKIDToBottomOfSelf

		/*-commented out-
		__contentsBottomIsFirst = __contentsBottomIsFirst.get(:cardIx) +\
		 __contentsBottomIsFirst.get(cardIx+1:) + .get(kID);
		 */
	}
		
	public void moveByKIDToTopOfSelf(int kID) {
		// If the following raises an IndexError, then the kID wasn"t in this 
		// zone.
		int cardIx = __contentsBottomIsFirst.indexOf(kID);
		
		if (cardIx == len(__contentsBottomIsFirst) - 1) {
			// It"s already at the top.  Avoid expensive list operation.
			return;
		}
		
		throw new NYIException();  // TODO finish moveByKIDToTopOfSelf
		
		/*-commented out-*
		__contentsBottomIsFirst = __contentsBottomIsFirst.get(:cardIx) +\
		 __contentsBottomIsFirst.get(cardIx+1:) + .get(kID);
		 */
	}
		
	/**
	Moves one of the entities with the given name from this zone to the;
	bottom of another.
	
	Raises IndexError if an entity with the given name does not exist in;
	this zone.
	*/
	public void moveByNameToBottom(IDResolver resolver, Zone destination, 
			String cardName) 
	{

		int cardIx = indexByName(resolver, cardName);
		_moveIndexToBottomOfOtherZone(resolver, cardIx, destination);
	}

	public void moveByNameToBottomOfSelf(IDResolver resolver, String cardName) {
		int cardIx = indexByName(resolver, cardName);
		_moveIndexToBottomOfOtherZone(resolver, cardIx, this);
	}

	/**
	 * Move the entity from this zone to the bottom of the target zone.
	 */
	public void moveEntityToBottom(Entity entity, Zone targetZone, int count /*=1*/) {

		// If this throws ValueError, you tried to move an entity from a zone
		// in which it wasn't present.
		//
		//int targetIx = __contentsBottomIsFirst.indexOf(entity);

		throw new NYIException();  // TODO code moveEntityToBottom

		/*-commented out-*
		 _moveIndexToBottomOfOtherZone(targetIx, targetZone);
		 */
	}

	/** 
	 * Reset the entity"s location and visibility permissions to that of this 
	 * zone.  Call this when the entity changes zones.
	 */		
	public void assimilateEntity(Entity entity) {
		entity.setLocationKID(getKID());
		entity.setDefaultVisibility(isGenerallyVisible());
		
		// Use set() here to make copies.
		entity.setAllowedViewerKIDSet(set(getAllowedViewerKIDSet()));
		entity.setDeniedViewerKIDSet(set(getDeniedViewerKIDSet()));
	}

	public void shuffle() {
		// This shuffles in place, so there's no need to assign it.  Yay!
		 Collections.shuffle(__contentsBottomIsFirst);  
	}

	public void sortByName(Object resolver) {
		throw new NYIException(); // TODO code sortByName
		/*-commented out-*
		__contentsBottomIsFirst.sort(
		 key=lambda x: resolver.dref(x).getName());
		 */
	}

	/*- commented out temporarily; KRoot should handle these. 
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
		return this.ownable.getOwnerKID();
	}

	@Override
	public void setOwnerKID(Integer ownerKID) {
	    this.ownable.setOwnerKID(ownerKID);
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
		this.name = name;
    }

	@Override
    public String getName() {
		return name;
    }
	*/
}
