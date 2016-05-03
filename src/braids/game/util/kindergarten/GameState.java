package braids.game.util.kindergarten;

import static braids.util.UFunctions.*;
import static braids.util.python.port.PyFunctions.*;

import java.util.*;
import java.util.logging.*;

import braids.util.*;
import braids.util.python.port.ListFrom;
import braids.util.python.port.SetFrom;
import braids.util.python.port.ValueError;
import braids.util.python.port.random;

// A word on KIDs and resolvers.  Why do we use them?  
// That requires some history.
//
// We have been designing this module with predictive
// capability in mind.  In the short run, this streamlines game interaction by
// presenting the user with minimal options and by taking immediate action
// when only one option is available.  In the long run, this can lend
// itself to a minimax-style AI -- slow, but potentially very formidable,
// especially given the AI's ability to count cards and make statistical
// predictions.
//
// To enable this predictive capability and still maintain easy
// extensibility with future entities, we need to be able to create copies
// (or perhaps, one day, deltas)
// of the game's entire state.  However, data structures outside of the
// game's state must still make sense when referring to parts of that
// state.  In those data structures, we must use a
// reference mechanism that is not the same as
// the one used by the programming language.  For example, when testing out an 
// Operation, 
// we must not modify the current, actual game state, but rather, a copy of
// it (or parts thereof).
//
// The idea is, that by keeping the AI flexible, we can add new entities
// without touching the AI code.  While this is certainly computationally
// tractable, it may prove to be a performance nightmare.

public class GameState extends IDResolverImpl {
	//also extends (Vetoer, Surgeon, Publisher, Subscriber)? 


	private List<Participant> __participants;
	private Set<Participant> __oustedParticipantSet;
	private Participant __activeParticipant;

    private static final Logger logger =
            Logger.getLogger(GameState.class.getName());

    public GameState(GameState gsToBeCopied) {
		//Publisher.__init__(self, kIDToObject, getKID);
		//kindergarten.idresolver.IDResolver.__init__(self);
		//Logger.__init__(self);
		
		if (gsToBeCopied != null) {
			throw new NYIException();  // TODO code GameState copying
		}
		
		__participants = new ArrayList<Participant>();
		__oustedParticipantSet = new HashSet<Participant>();
		__activeParticipant = null;

		// Set the default log level.
		//setLogLevel(WARNING);
	}

	public void addParticipant(Participant participant) {
		// Give the participant a KID if they don't already have one.
		if (!hasKID(participant)) {
			this.createKIDFor(participant);
		}
		
		if (__participants.contains(participant)) {
			throw new ValueError(String.format(
					"The participant '%s' (//%d) has already joined.",
					participant.getName(), getOrCreateKID(participant)));
		}		
		else {
			// Append to list.
			__participants.add(participant);
		}
	}

	/**
	 * Choose a non-ousted participant at random.
	 * 
	 * @return a participant that is still in the game
	 */
	public Participant chooseRandomNonOustedParticipant() {
		Set<Participant> nonOustedParticipants = subtract(
				new SetFrom<>(__participants),
				__oustedParticipantSet);
		
		return random.choice(new ListFrom<>(nonOustedParticipants));
	}

	public Participant getActiveParticipant() {
		return __activeParticipant;
	}

	public Participant getNonOustedParticipantAfter(Participant participant) {
		int originalParticipantIx = __participants.indexOf(participant);

		int testParticipantIx = originalParticipantIx;

		while (true) {
			testParticipantIx += 1;

			// Reset the index to the beginning in case we've gone off the edge.
			if (testParticipantIx >= len(__participants)) {
				testParticipantIx = 0;
			}
			
			boolean testParticipantHasBeenOusted = (
					__oustedParticipantSet.contains(__participants.get(testParticipantIx)) );

			if (testParticipantIx == originalParticipantIx) {
				if (testParticipantHasBeenOusted) {
					// There are no more active participants!

					throw new AssertionError(
					 "kindergarten.g_state.GameState.getNonOustedParticipantAfter:  All participants have been ousted"
					);
				}
				else {
					
					logger.log(Level.WARNING,
					 "kindergarten.g_state.GameState.getNonOustedParticipantAfter:  Participant '{0}' (#{1}) is the only non-ousted participant", 
					 new Object[] {participant.getName(), getOrCreateKID(participant)}
					);
					

					// The next test below is going to pass, so the active
					// participant shall be returned.
				}
			}
			
			if (!testParticipantHasBeenOusted) {
				return __participants.get(testParticipantIx);
			}
			// Otherwise, the loop continues.
		}
	}

	public Set<Participant> getNonOustedParticipantSet() {
		// Note these are NOT KIDs.
		return subtract(new SetFrom<>(__participants), __oustedParticipantSet);
	}

	public List<Participant> getParticipantList() {
		return __participants;
	}

	public boolean hasBeenOusted(Participant participant) {
		return __oustedParticipantSet.contains(participant);
	}

	/** 
	 * Determine if there is one participant or fewer remaining.
	 */
	public boolean hasEnded() {
		if (len(subtract(new SetFrom<>(__participants), __oustedParticipantSet)) <= 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Set the active participant to the participant after them.
	 */
	public void nextActiveParticipant() {
		
		setActiveParticipant(getNonOustedParticipantAfter(
			getActiveParticipant()));
	}

	public void pickRandomActiveParticipant() {
		setActiveParticipant(chooseRandomNonOustedParticipant());
	}

	public void setActiveParticipant(Participant participant) {
		if (!__participants.contains(participant)) {
			throw new ValueError("Participant not recognized: " + repr(participant));
		}
		
		__activeParticipant = participant;
	}

	public IDResolver getKIDResolver() {
		return this;
	}


}
