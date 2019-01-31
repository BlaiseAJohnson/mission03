/*
 * Blaise Johnson
 * CS 3308
 * Isaac Griffith
 * 1/24/19
 */
package edu.isu.cs.cs3308.structures.impl;

import edu.isu.cs.cs3308.structures.List;


/**
 * A generic template implementation of a singly-lined list structure.
 * @author Written by Blaise Johnson in compliance with preexisting code and unit tests by Isaac Griffith.
 */
public class SinglyLinkedList<E> implements List<E> {

    private Node head = null;
    private Node tail = null;
    private int size = 0;

    /**
     * @return first element in the list or null if the list is empty.
     */
    @Override
    public E first() {
        if (head == null) {
            return null;
        }
        else {
            return head.data;
        }
    }

    /**
     * @return last element in the list or null if the list is empty.
     */
    @Override
    public E last() {
        if (tail == null) {
            return null;
        }
        else {
            return tail.data;
        }
    }

    /**
     * Adds the provided element to the end of the list, only if the element is
     * not null.
     *
     * @param element Element to be added to the end of the list.
     */
    @Override
    public void addLast(E element) {
        if (element != null) {
            Node newNode = new Node(element);

            if (isEmpty()) {
                head = newNode;
                tail = newNode;
            }
            else {
                // Travel to the correct node.
                Node currentNode = head;
                while (currentNode.next() != null) {
                    currentNode = currentNode.next();
                }

                // Append the node and update tail.
                currentNode.next = newNode;
                tail = newNode;
            }

            size++;
        }
    }

    /**
     * Adds the provided element to the front of the list, only if the element
     * is not null.
     *
     * @param element Element to be added to the front of the list.
     */
    @Override
    public void addFirst(E element) {
        if (element != null) {
            Node newHead = new Node(element);

            // Prepend the node and update head
            newHead.next = head;
            head = newHead;

            size++;
        }
    }

    /**
     * Removes the element at the front of the list.
     *
     * @return Element at the front of the list, or null if the list is empty.
     */
    @Override
    public E removeFirst() { return remove(0); }

    /**
     * Removes the element at the end of the list.
     *
     * @return Element at the end of the list, or null if the list is empty.
     */
    @Override
    public E removeLast() {
        return remove(size - 1);
    }

    /**
     * Inserts the given element into the list at the provided index. The
     * element will not be inserted if either the element provided is null or if
     * the index provided is less than 0. If the index is greater than or equal
     * to the current size of the list, the element will be added to the end of
     * the list.
     *
     * @param element Element to be added (as long as it is not null).
     * @param index   Index in the list where the element is to be inserted.
     */
    @Override
    public void insert(E element, int index) {
        if (element != null && index >= 0) {
            if (index >= size) {
                addLast(element);
            }
            else if (index == 0) {
                addFirst(element);
            }
            else {
                Node newNode = new Node(element);
                Node currentNode = head;
                Node previousNode = head;

                // Travel to correct index while storing the previous node.
                for (int i = 0; i < index; i++) {
                    previousNode = currentNode;
                    currentNode = currentNode.next;
                }

                // Connect the newNode to the previous node, and the current node to the new node.
                previousNode.next = newNode;
                newNode.next = currentNode;

                // Increment the list size.
                size++;
            }
        }
    }

    /**
     * Removes the element at the given index and returns the value.
     *
     * @param index Index of the element to remove
     * @return The value of the element at the given index, or null if the index
     * is greater than or equal to the size of the list or less than 0.
     */
    @Override
    public E remove(int index) {
        if (index >= size || index < 0) return null;

        Node currentNode = head;
        Node nodeAfterCurrent;
        Node nodeBeforeCurrent = head;

        // Travel to the correct node while storing the previous node
        for (int i = 0; i < index; i++) {
            nodeBeforeCurrent = currentNode;
            currentNode = currentNode.next;
        }
        nodeAfterCurrent = currentNode.next;

        // Connect the nodes on either side of the current node, then detach it.
        nodeBeforeCurrent.next = nodeAfterCurrent;
        currentNode.next = null;

        // Update head or tail.
        if (index == 0) {
            head = nodeAfterCurrent;
        }
        else if (index == size - 1) {
            tail = nodeBeforeCurrent;
        }

        // Return contents of removed node and decrease list size.
        size--;
        return currentNode.data;

    }

    /**
     * Retrieves the value at the specified index. Will return null if the index
     * provided is less than 0 or greater than or equal to the current size of
     * the list.
     *
     * @param index Index of the value to be retrieved.
     * @return Element at the given index, or null if the index is less than 0
     * or greater than or equal to the list size.
     */
    @Override
    public E get(int index) {
        if (index >= size || index < 0) return null;

        // Travel to the correct node
        Node currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next();
        }

        return currentNode.data;

    }

    /**
     * @return The current size of the list. Note that 0 is returned for an
     * empty list.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * @return true if there are no items currently stored in the list, false
     * otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Prints the contents of the list in a single line separating each element
     * by a space to the default System.out
     */
    @Override
    public void printList() {
        Node currentNode = head;

        while (true) {
            System.out.println(currentNode.data);

            if (currentNode.next == null) break;

            currentNode = currentNode.next;
        }
    }

    /**
     * Locates a given element in the list and returns the index of that element.
     */
    @Override
    public int indexOf(E element) {
        if (element == null || size == 0) return -1;

        Node currentNode = head;
        int currentIndex = 0;
        boolean elementFound = false;

        // Walk the list until the element is found or the end is reached and it is not found.
        while (!elementFound) {
            if (currentNode.data.equals(element)) {
                elementFound = true;
            } else if (currentNode.next == null) {
                break;
            } else {
                currentNode = currentNode.next();
                currentIndex++;
            }
        }

        return elementFound ? currentIndex : -1;
    }

    /**
     * Represents the data container used in the SinglyLinkedList class.
     */
    protected class Node {
        E data;
        Node next;

        Node(E data) {
            this.data = data;
            this.next = null;
        }


        Node next() {
            return next;
        }
    }
}
