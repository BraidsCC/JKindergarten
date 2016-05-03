package braids.game.util.kindergarten;

import braids.game.util.kindergarten.exception.*;
import braids.util.python.port.*;

public class KIDAwareImpl implements KIDAware {

		private boolean idHasBeenSet = false;
		private Integer __kID;
		
	    public KIDAwareImpl() {
	    	this(null);
	    }
	
	   	public KIDAwareImpl(IDResolverImpl resolver) {
	        __kID = null;
	        
	        if (resolver != null) {
	            setKID(resolver.getOrCreateKID(this));
	        }
	    }

	    public void setKID(int kID) {
			if (__kID != null || idHasBeenSet) {
	            throw new ObjectAlreadyHasKIDError(this, __kID);
	        }
	        else {
	        	__kID = kID;
	        	idHasBeenSet = true;
	        }	
	    }
	        
        /** 
		 * @throw ValueError if setKID hasn't yet been called
         */
	    public Integer getKID() {
	    	if (idHasBeenSet) {
	    		return __kID;
	    	}
	    	else {
	    		throw new ValueError("setKID has not yet been called");
        	}
	    }
}
