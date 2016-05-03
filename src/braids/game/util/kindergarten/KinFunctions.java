package braids.game.util.kindergarten;

import static braids.util.UFunctions.*;

import java.util.*;

import braids.util.python.port.KeyError;

/** Miscellaneous helpful functions.
 * 
 * @author Braids
 *
 */

public abstract class KinFunctions {
	public static void checkTypeIsKID(String argName, Object testee) {
		checkTypeIsKID(argName, testee, null);
	}

	public static void checkTypeIsKID(String argName, Object testee, IDResolverImpl resolver) {
		if (!isKID(testee, resolver)) {
			// type() usually starts with '<type '.

			throw new RuntimeException("Argument '" + argName + "' must be a valid KID, not " + 
					repr(testee) + "; error is at least two stack frames up.");
		}
	}
	
	public static void checkTypeIsNoneOrKID(String argName, Object testee) {
		checkTypeIsNoneOrKID(argName, testee, null);
	}

	public static void checkTypeIsNoneOrKID(String argName, Object testee, 
			IDResolverImpl resolver /*= null*/) 
	{
		if (testee == null) {
			return;
		}
		else {
			checkTypeIsKID(argName, testee, resolver);
		}
	}
	
	public static void checkTypeIsNoneOrNotKID(String argName, Object testee, 
			IDResolverImpl resolver /*=None*/) 
	{
		if (testee == null) {
			return;
		}
		else {
			checkTypeIsNotKID(argName, testee, resolver);
		}
	}
	
	public static void checkTypeIsNotKID(String argName, Object testee, 
			IDResolverImpl resolver /*=None*/) {

		if (isKID(testee, resolver)) {
			throw new RuntimeException(String.format("Argument '%s' must not be a KID; " +
					"error is at least two stack frames up.", argName));
		}
	}

    /**
     * Removes actions that were vetoed.  Modifies
     * operationList.  Returns nothing.
     */
	/*-commented out
	public static void filterVetoedOperations(IDResolverImpl resolver, 
	        List<Veto> vetoes,
			List<Operation> operationList
			)
	{
		 for (index, operation in enumerate(operationList)) {
		 
            for (veto in vetoes) {
                if (veto.matches(resolver, operation)) {
                    del operationList[index];
                }
            }
        }
	}
    */
	
	/**
	 * Returns true if the testee is a KID.
	 *
	 * resolver (optional) - looks up the KID; pass this if possible.
	 **/
	public static boolean isKID(Object testee) {
		return isKID(testee, null);
	}
	
	/**
	 * Returns true if the testee is a KID.
	 *
	 * resolver (optional) - looks up the KID; pass this if possible.
	 **/
	public static boolean isKID(Object testee, IDResolver resolver /*=None*/) {
    
        	if (resolver == null) {
        		
        		@SuppressWarnings("unused")
                int foo = (Integer) testee;
        		
        		// Previous line's type check succeeded.
        		return true;
        	}
        	else {
        		try {
        			resolver.dref((Integer) testee);
        		}
        		catch (KeyError err) {
        			return false;
        		}

        		return true;
        	}
	}
        
	public static Integer toKID(Object objOrKID) {
		if (isKID(objOrKID, null)) {
			return (Integer) objOrKID;
		}
		else {
			return ((KIDAware) objOrKID).getKID();
		}
	}
	
	/**
	 * Shallowly copy all items of c into a new Set object.
	 * 
	 * @param c  the collection to copy
	 * @return  a new set
	 */
	public static <T> Set<T> set(Collection<T> c) {
		HashSet<T> result = new HashSet<>(c.size());
		
		for (T item : c) {
			result.add(item);
		}
		
		return result;
	}
}
