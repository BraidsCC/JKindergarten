package braids.game.util.kindergarten;

import static braids.util.UFunctions.*;

import braids.util.NamedThing;
import braids.util.python.port.ValueError;

public interface Participant extends NamedThing, KIDAware {

    default String defaultParticipantToString() {
        try {
            Integer kid = getKID();

            return String.format("Participant %s (#%d)",
                repr(getName()), kid);
        }
        catch ( ValueError err ) {
            return String.format("Participant %s (KID UNKNOWN)",
                repr(getName()));
        }
    }
    
}