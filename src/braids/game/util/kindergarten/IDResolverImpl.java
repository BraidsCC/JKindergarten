package braids.game.util.kindergarten;

import static braids.util.UFunctions.*;

import java.util.HashMap;
import java.util.Map;

import braids.game.util.kindergarten.exception.ObjectAlreadyHasKIDError;
import braids.game.util.kindergarten.exception.OutOfKIDsError;
import braids.util.python.port.KeyError;

public class IDResolverImpl implements IDResolver {
	private Integer __latestKID = 1;
	private Map<Integer, Object> __kIDToObjectMap;
	private Map<Object, Integer> __objectToKIDMap;

	public IDResolverImpl() {
		__kIDToObjectMap = new HashMap<Integer,Object>();
		__objectToKIDMap = new HashMap<Object, Integer>();
	}
	
	public Object dref(Integer kID) {
		// If you get a KeyError here, make sure you passed a kID and not
		// an instance!
		Object result = __kIDToObjectMap.get(kID);
		
		if (result == null) {
			throw new KeyError("Could not find key " + repr(kID) + " in __kIDToObjectMap");
		}
		
		return result;
	} 
	
	public Integer getOrCreateKID(Object obj) {
		Integer result = __objectToKIDMap.get(obj);

		if (result == null) {
			result = createKIDFor(obj);
		}
		
		return result;
	}

	public boolean hasKID(Object obj) {
		Object search = __objectToKIDMap.get(obj);
		if (search == null) {
			return false;
		}

		return true;
	}

	public void _rebuildObjectToKIDMap() {
		// Is this even needed?
		throw new RuntimeException("NotImplemented");
	}

	
	public Integer createKIDFor(Object obj) {
		//print (":Creating KID for %s\n" % repr(obj));
		
		if (__kIDToObjectMap.get(__latestKID) != null) {
			throw new AssertionError("__latestKID must never exist in KID to object map.");
		}

		Integer kID = __objectToKIDMap.get(obj);
		if (kID != null) {
			throw new ObjectAlreadyHasKIDError(obj, kID);
		}
		
		kID = __latestKID;
		
		__kIDToObjectMap.put(kID, obj);
		__objectToKIDMap.put(obj, kID);

		__latestKID += 1;

		if (__latestKID < kID) {
			// The integer rolled over.
			throw new OutOfKIDsError();
			
			// If this happens, consider adding management of a free list in this.deleteKID.
		}
		
		return kID;
	}

	public void delete() {
		// Because we use KIDs just about everywhere, the chances of circular
		// references are slim.  This class has the greatest chance of
		// being a source of memory leaks, so we decided to help out.
		
		__kIDToObjectMap.clear();
		__objectToKIDMap.clear();
	}

	public void deleteKID(Integer kID) {
		Object obj = dref(kID);
		__objectToKIDMap.remove(obj);
		__kIDToObjectMap.remove(kID);

		// Add kID to free list if memory becomes a problem.
	}

	/**
	 * Validate the object to KID map, the KID to object map, and all KIDs of
	 * all KIDAware objects we track.
	 * 
	 * @throw AssertionError if the validation fails
	 */
	@Override
    public void validateAllKIDs() throws AssertionError {
		for (Object obj : __objectToKIDMap.keySet()) {
			int kid = __objectToKIDMap.get(obj);
			
			assert(__kIDToObjectMap.get(kid) == obj);
			
			if (obj.getClass().isAssignableFrom(KIDAware.class)) {
				KIDAware objKA = (KIDAware) obj;
				
				assert(objKA.getKID() == kid);
			}
		}
		
		for (int kid : __kIDToObjectMap.keySet()) {
			Object obj = __kIDToObjectMap.get(kid);
			
			assert(__objectToKIDMap.get(obj) == kid);
		}
		
		
    }
}
