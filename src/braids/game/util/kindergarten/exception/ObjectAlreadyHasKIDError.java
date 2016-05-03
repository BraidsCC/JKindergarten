package braids.game.util.kindergarten.exception;

import static braids.util.UFunctions.*;

public class ObjectAlreadyHasKIDError extends RuntimeException {

	private static final long serialVersionUID = 6441705137168723974L;
	
	public ObjectAlreadyHasKIDError(Object obj, Integer kid) {
		super("Object " + repr(obj) + 
				" has already registered a KID " + repr(kid));
    }
}
