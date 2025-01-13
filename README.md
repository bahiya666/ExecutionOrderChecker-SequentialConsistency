# ExecutionOrderChecker-SequentialConsistency
This project allows you to generate and verify all possible valid execution orders of method calls based on sequential consistency. It simulates a system with multiple threads where each thread performs a sequence of method calls. The program ensures that the order of method calls respects the logical constraints defined by the sequential consistency model.

Features
Sequential Consistency: Verifies that the method calls in each thread maintain the correct order, and that the interleaving of method calls between threads respects enqueue (enq) and dequeue (deq) actions.
All Valid Orders: It can generate all possible valid execution orders of the method calls that respect the sequential consistency constraints.
Custom Method Calls: You can define the thread ID, the order of the method calls within the thread, and the action (either enq or deq).

Components
1. MethodCall Class
Represents a method call within a thread. Each method call has:

threadId: The ID of the thread executing the method call.
orderInThread: The order of the method call within the thread.
action: The action being performed (either enq or deq).

2. ExecutionOrderChecker Class
This class provides the main functionality to generate all valid execution orders of method calls. It checks if a method call can be added to a sequence while respecting sequential consistency rules (correct order within threads and matching enq/deq operations).

3. Main Class
Sets up a list of MethodCall objects and calls the ExecutionOrderChecker to find and print all possible valid execution orders.

Compile and run :
javac *.java
java Main

