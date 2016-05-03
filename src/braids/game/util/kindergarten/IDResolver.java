package braids.game.util.kindergarten;

public interface IDResolver {

	public Object dref(Integer kID);
	
	public Integer getOrCreateKID(Object obj);

	public boolean hasKID(Object obj);
	
	public Integer createKIDFor(Object obj);

	public void delete();

	public void deleteKID(Integer kID);
	
	public void validateAllKIDs();

	/**
	 * Creates a KID for the given object and tells that object what its KID is.
	 * 
	 * @param obj  to be given a new KID
	 * 
	 * @return the new KID
	 */
	default Integer createKIDFor(KIDAware obj) {
		// TODO BUG this is not being called correctly at run-time for KIDAware objects!
		
		int kID = createKIDFor((Object) obj);
		obj.setKID(kID);
		
		return kID;
	}

}
