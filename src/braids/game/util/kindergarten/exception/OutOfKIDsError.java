package braids.game.util.kindergarten.exception;

public class OutOfKIDsError extends RuntimeException {
	private static final long serialVersionUID = 3695198013564146040L;

	public static String INDETERMINATE_VALUE="<<indeterminateValue>>";

	public OutOfKIDsError() {
	}
	
    public String toString() {
        return "Out of KIDs; must implement garbage collector";
    }
}
