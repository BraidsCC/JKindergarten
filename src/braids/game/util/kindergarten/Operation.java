package braids.game.util.kindergarten;

import static braids.util.UFunctions.*;

import java.util.*;

import braids.util.*;

// Not every operation has an initiating participant -- for example,
// triggered effects.


/** 
 * Must remain immutable!
 */
public class Operation implements NamedThing {
	private Integer __surgeonKID;
	private Integer __enactingParticipantKID;
	//private Map<String, Object> __argumentDict;
	private String name;
	private Map<String, Object> parametersUsingKIDs;
	private Map<String, Object> parametersSansKIDs;

	/**
	 * @param surgeon is the object that is responsible for
	 * executing the operation.
	 * 
	 * @param name is the of the name of the operation, which often
	 * corresponds to the name of a method associated with it.
	 * 
	 * @param enactingParticipantKID is the participant who
	 * initiated the operation; it may be null.
	 * 
	 * @param argumentDict contains the keyword arguments to pass to
	 * the operation when it executes.
	 */
	public Operation(IDResolver resolver, KIDAware surgeon, String name, 
			Participant enactingParticipant, Map<String,Object> keywordArguments)
	{
		this(resolver, 
				(surgeon == null ? null : surgeon.getKID()), 
				name, 
				(enactingParticipant == null ? null : enactingParticipant.getKID()),
				keywordArguments);
	}

	/**
	 * Convenience constructor.
	 * 
	 * @see #Operation(IDResolver, KIDAware, String, Participant, Map)
	 */
	public Operation(IDResolver resolver, KIDAware surgeon, String name, 
			Participant enactingParticipant)
    {
		this(resolver, surgeon, name, enactingParticipant, 
    			new HashMap<String,Object>(0));
    }

	/**
	 * Convenience constructor.
	 * 
	 * @see #Operation(IDResolver, KIDAware, String, Participant, Map)
	 */
	public Operation(IDResolver resolver, KIDAware surgeon, String name) {
	    this(resolver, surgeon, name, null);
    }


	/**
	 * @param surgeonKID is the KID of the object that is responsible for
	 * executing the operation.
	 * 
	 * @param name is the of the name of the operation, which often
	 * corresponds to the name of a method associated with it.
	 * 
	 * @param enactingParticipantKID is the KID of the participant who
	 * initiated the operation; it may be None.
	 * 
	 * @param argumentDict contains the keyword arguments to pass to
	 * the operation when it executes.
	 */
	Operation(IDResolver resolver, Integer surgeonKID, String name, 
			Integer enactingParticipantKID /*=None*/,
			Map<String, Object> argumentDict) 
	{
		setName(name);

		assert (argumentDict != null);

		parametersUsingKIDs = new HashMap<>();
		parametersSansKIDs = new HashMap<>();
		
		// Operations must only use KIDs, not references to actual
		// objects.  This is important because Operations exist
		// outside of individual game-states.  As such, they must only
		// use KIDs to refer to objects within a (any) game-state.
		//
		for (String key : argumentDict.keySet()) {
			
			Object val = argumentDict.get(key);
			
			if (resolver.hasKID(val)) {

				// Use the KID instead of the object.  Place into a special
				// map field that uses KIDs.

				parametersUsingKIDs.put(key, resolver.getOrCreateKID(val));  

			} else {
    			parametersSansKIDs.put(key, val);			
            }

		}
		
		__surgeonKID = surgeonKID;
		__enactingParticipantKID = enactingParticipantKID;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (obj == this) {
			return true;
		}
		
		Operation that;
		try {
			that = (Operation) obj;
		}
		catch (ClassCastException exn) {
			return false;
		}
		
		boolean parametersSansKIDsMatch = parametersSansKIDs
		        .equals(that.parametersSansKIDs);

		boolean parametersUsingKIDsMatch = parametersUsingKIDs
		        .equals(that.parametersUsingKIDs);
		
		return (parametersSansKIDsMatch
		        && parametersUsingKIDsMatch
		        && getEnactingParticipantKID() == that
		                .getEnactingParticipantKID()
		        && getName().equals(that.getName()) && getSurgeonKID() == that
		            .getSurgeonKID());
	}

	@Override
	public int hashCode() {
		return (__surgeonKID.hashCode() ^ getName().hashCode()
		 ^ __enactingParticipantKID.hashCode()
		 ^ parametersSansKIDs.hashCode()
		 ^ parametersUsingKIDs.hashCode());
	}

	/*-commented out-*
	public void __repr__() {
		return ("kindergarten.operation.Operation(r, %s, %s, %s, %s)" %
		 (repr(getSurgeonKID()),
		  repr(getName()),
		  repr(getEnactingParticipantKID()),
		  repr(getKeywordArguments());
		  ));
	}
	*/

	/**
	 * Show this operation's contents in semi-human-readable format. This is
	 * mostly helpful for debugging.
	 */
	public String toString() {
		return String.format("%s %s %s courtesy object %s as participant %s",
		        getName(), repr(parametersSansKIDs), repr(parametersUsingKIDs),
		        getSurgeonKID(), getEnactingParticipantKID());
	}

	/*-commented out
	 * Do not modify the return value from this!
	 * 
	 * @return the keyword arguments (converted to KIDs where possible) -- DO
	 *         NOT MODIFY!
	Map<String, Object> getKeywordArguments() {
		assert(__argumentDict != null);
		return __argumentDict;
	}
	 */
	
	/**
	 * Pull a value from keyword arguments.
	 * 
	 * @param name
	 *            the name of the key to find in parameter maps
	 *            
	 * @param defaultValue
	 *            use this value if the keyword is not found in any map; this
	 *            may also provide a hint for what type of value to pull.
	 * 
	 * @return the value from one of the maps
	 */
	@SuppressWarnings("unchecked")
    public <T> T getParameter(IDResolver res, String name, T defaultValue)
	{
		// First, check the parameters that don't use KIDs.
        T val = (T) parametersSansKIDs.get(name);

        if (val == null) {
        	// Check the ones that use KIDs, then dereference them.
        	Integer kid = (Integer) parametersUsingKIDs.get(name);

        	if (kid != null) {
        		val = (T) res.dref(kid);
        	}
        }
        
		if (val == null) {
			val = defaultValue;
		}
		
		return val;
	}


	public Integer getEnactingParticipantKID() {
		return __enactingParticipantKID;
	}

	public Integer getSurgeonKID() {
		return __surgeonKID;
	}

	@Override
    public void setName(String name) {
		this.name = name;
    }

	@Override
    public String getName() {
		return name;
    }

	public Participant getEnactingParticipant(IDResolver res) {
		if (__enactingParticipantKID == null) { 
			return null;
		}
		
		return (Participant) res.dref(__enactingParticipantKID);
    }

}
