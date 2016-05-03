package braids.game.util.kindergarten;

import java.util.function.*;

public class OperationMatcher {
    private BiFunction<IDResolver, Operation, Boolean> __matchingProcedure;

	public OperationMatcher(BiFunction<IDResolver,Operation,Boolean> matchingProcedure) {

        __matchingProcedure = matchingProcedure;
    }

    public Boolean matches(Operation operation, IDResolver resolver) {
        return __matchingProcedure.apply(resolver, operation);
    }

    public String toString() {
        return String.format("OperationMatcher(%s)", __matchingProcedure.toString());
    }
}
