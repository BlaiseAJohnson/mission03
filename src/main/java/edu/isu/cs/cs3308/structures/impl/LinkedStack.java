package edu.isu.cs.cs3308.structures.impl;

import edu.isu.cs.cs3308.structures.Stack;

public class LinkedStack<E>  implements Stack<E> {

    private DoublyLinkedList<E> stack;

    public LinkedStack() {
        stack = new DoublyLinkedList<>();
    }

    /**
     * Adds the provided item to the top of the stack. Note that if the item is
     * null, nothing occurs.
     *
     * @param element Element added to the top of the stack, unless this item is
     *                null.
     */
    @Override
    public void push(E element) {
        stack.addLast(element);
    }

    /**
     * Returns the value of the top item in the stack, without removing it. If
     * the stack is empty then null is returned.
     *
     * @return The value of the item at the top of the stack, or null if the
     * stack is empty.
     */
    @Override
    public E peek() {
        E peekData = stack.removeLast();
        stack.addLast(peekData);
        return peekData;
    }

    /**
     * Removes the top item from the stack and returns it's value. If the stack
     * is currently empty, null is returned.
     *
     * @return The value of the top item in the stack, or null if the stack is
     * empty.
     */
    @Override
    public E pop() {
        return stack.removeLast();
    }

    /**
     * @return The current number of items in this stack.
     */
    @Override
    public int size() {
        return stack.size();
    }

    /**
     * A test to determine if this Stack is currently empty.
     *
     * @return True if this stack is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    /**
     * Transfers the contents of this stack into the provided stack. The contents
     * of this stack are to be found in reverse order at the top of the provided
     * stack. This stack should be empty once the transfer is completed. Note
     * that if the provided stack is null, nothing is to happen.
     *
     * @param to The new stack onto which the reversed order of contents from
     *           this stack are to be transferred to the top of, unless the provided stack
     *           is null.
     */
    @Override
    public void transfer(Stack<E> to) {
        if (to != null) {
            while (size() > 0) {
                to.push(this.pop());
            }
        }
    }

    /**
     * Reverses the contents of this stack.
     */
    @Override
    public void reverse() {
        LinkedStack<E> stepOne = new LinkedStack<>();
        LinkedStack<E> stepTwo = new LinkedStack<>();

        // Three reversals are performed here, ending in the original stack.
        // This effectively reverses the content of the original stack.
        transfer(stepOne);
        stepOne.transfer(stepTwo);
        stepTwo.transfer(this);
    }

    /**
     * Merges the contents of the provided stack onto the bottom of this stack.
     * The order of both stacks must be preserved in the order of this stack
     * after the method call. Furthermore, the provided stack must still contain
     * its original contents in their original order after the method is
     * complete. If the provided stack is null, no changes should occur.
     *
     * @param other Stack whose contents are to be merged onto the bottom of
     *              this stack.
     */
    @Override
    public void merge(Stack<E> other) {
        if (other != null) {
            LinkedStack<E> thisTemp = new LinkedStack<>();
            LinkedStack<E> otherCopy = copy(other);

            // Store this into a temp stack.
            transfer(thisTemp);

            // Reverse other so order is preserve during transfer.
            otherCopy.reverse();

            // Transfer other into this.
            otherCopy.transfer(this);

            // Transfer original contents back into this.
            thisTemp.transfer(this);
        }
    }

    private LinkedStack<E> copy(Stack<E> of) {
        LinkedStack<E> originalTemp = new LinkedStack<>();
        LinkedStack<E> copyTemp = new LinkedStack<>();
        LinkedStack<E> copy = new LinkedStack<>();

        // Pop each item off the original and push a copy into each of two temp stacks.
        while(of.size() > 0) {
            E stackData = of.pop();
            originalTemp.push(stackData);
            copyTemp.push(stackData);
        }

        // Transfer the data back from the temps into the original and the copy respectively.
        originalTemp.transfer(of);
        copyTemp.transfer(copy);

        return copy;
    }

    /**
     * Prints the contents of the stack starting at top, one item per line. Note
     * this method should not empty the contents of the stack.
     */
    @Override
    public void printStack() {
        LinkedStack<E> thisTemp = new LinkedStack<>();

        while (size() > 0) {
            E stackData = pop();

            System.out.println(stackData);

            thisTemp.push(stackData);
        }

        thisTemp.transfer(this);
    }
}
