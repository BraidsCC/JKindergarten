package braids.game.util.kindergarten.exception;

import static braids.util.UFunctions.*;

public class IndeterminateOutcomeError extends RuntimeException {

	private static final long serialVersionUID = -7036434227260459443L;
		private String value;

		/**
		 * @param value
		 *            must be some kind of specific description of the nature of the
		 *            uncertainty.
		 */
		public IndeterminateOutcomeError(String value) {
	        this.value = value;
	    }
	    
	    public String toString() {
	        return repr(value);
	    }
}
