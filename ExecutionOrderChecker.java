import java.util.*;

public class ExecutionOrderChecker {

    // Generates all possible valid orders of method calls that respect sequential consistency
    public static List<List<MethodCall>> findPossibleOrders(List<MethodCall> methodCalls) {
        List<List<MethodCall>> resultOrders = new ArrayList<>();
        generateOrders(resultOrders, new ArrayList<>(), methodCalls, new HashSet<>());
        return resultOrders;
    }

    // Recursive method to generate all valid permutations of method calls
    private static void generateOrders(List<List<MethodCall>> resultOrders, List<MethodCall> currentSequence, 
                                       List<MethodCall> remainingCalls, Set<MethodCall> usedCalls) {
        if (currentSequence.size() == remainingCalls.size()) {
            resultOrders.add(new ArrayList<>(currentSequence));
            return;
        }

        for (MethodCall call : remainingCalls) {
            if (usedCalls.contains(call)) continue;

            if (isPermissible(currentSequence, call)) {
                currentSequence.add(call);
                usedCalls.add(call);

                generateOrders(resultOrders, currentSequence, remainingCalls, usedCalls);

                currentSequence.remove(currentSequence.size() - 1);
                usedCalls.remove(call);
            }
        }
    }

    // Checks if adding a method call to the current sequence is valid
    private static boolean isPermissible(List<MethodCall> currentSequence, MethodCall newCall) {
        // Check thread order constraints
        for (MethodCall existingCall : currentSequence) {
            if (existingCall.threadId.equals(newCall.threadId) && existingCall.orderInThread > newCall.orderInThread) {
                return false;
            }
        }

        Deque<String> buffer = new ArrayDeque<>();
        for (MethodCall existingCall : currentSequence) {
            if (existingCall.action.startsWith("enq")) {
                buffer.addLast(existingCall.action.substring(4, 5));
            } else if (existingCall.action.startsWith("deq")) {
                if (buffer.isEmpty() || !buffer.pollFirst().equals(existingCall.action.substring(4, 5))) {
                    return false;
                }
            }
        }

        if (newCall.action.startsWith("enq")) {
            return true;
        } else if (newCall.action.startsWith("deq")) {
            String item = newCall.action.substring(4, 5);
            return !buffer.isEmpty() && buffer.peekFirst().equals(item);
        }

        return false;
    }
}
