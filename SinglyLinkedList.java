/**
 * Your implementation of a non-circular SinglyLinkedList with a tail pointer.
 *
 * @author Mohamed Ghanem
 * @version 1.0
 * @userid mghanem8
 * @GTID 903533880
 */
public class SinglyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private SinglyLinkedListNode<T> head;
    private SinglyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index to add the new element
     * @param data  the data to add
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bounds.");
        } else if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into the list.");
        } else if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            size++;
            SinglyLinkedListNode<T> temp1 = head;
            for (int i = 1; i < index; i++) {
                temp1 = temp1.getNext();
            }
            SinglyLinkedListNode<T> temp2 = new SinglyLinkedListNode<T>(data, temp1.getNext());
            temp1.setNext(temp2);
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into the list.");
        }
        size++;
        if (head == null) {
            head = new SinglyLinkedListNode<T>(data);
            tail = head;
        } else {
            SinglyLinkedListNode<T> temp = head;
            head  = new SinglyLinkedListNode<T>(data, temp);
        }
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into the list.");
        } else if (size == 0) {
            addToFront(data);
        } else {
            size++;
            SinglyLinkedListNode<T> temp = new SinglyLinkedListNode<T>(data);
            tail.setNext(temp);
            tail = temp;
        }
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and O(n) for all other
     * cases.
     *
     * @param index the index of the element to remove
     * @return the data that was removed
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds.");
        } else if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        } else {
            size--;
            SinglyLinkedListNode<T> temp1 = head;
            for (int i = 1; i < index; i++) {
                temp1 = temp1.getNext();
            }
            SinglyLinkedListNode<T> temp2 = temp1.getNext();
            temp1.setNext(temp2.getNext());
            return temp2.getData();
        }
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (head == null) {
            throw new java.util.NoSuchElementException("List is empty.");
        } else if (size == 1) {
            T tempData = head.getData();
            clear();
            return tempData;
        } else {
            size--;
            T tempData = head.getData();
            head = head.getNext();
            return tempData;
        }
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (head == null) {
            throw new java.util.NoSuchElementException("List is empty.");
        } else if (size == 1) {
            return removeFromFront();
        } else if (size == 2) {
            T tempData = head.getNext().getData();
            tail = head;
            head.setNext(null);
            size--;
            return tempData;
        } else {
            SinglyLinkedListNode<T> temp = head;
            for (int i = 1; i < size - 2; i++) {
                temp = temp.getNext();
            }
            T tempData = temp.getNext().getNext().getData();
            temp.getNext().setNext(null);
            tail = temp.getNext();
            size--;
            return tempData;
        }
    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds.");
        } else if (index == 0) {
            return head.getData();
        } else if (index == (size - 1)) {
            return tail.getData();
        } else {
            SinglyLinkedListNode<T> temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.getNext();
            }
            return temp.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (head == null);
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into the list.");
        } else if (size == 0) {
            throw new java.util.NoSuchElementException("List is empty");
        } else if (tail.getData().equals(data)) {
            return removeFromBack();
        } else if (size == 1) {
            if (head.getData().equals(data)) {
                return removeFromBack();
            } else {
                throw new java.util.NoSuchElementException("Element was not found in the list.");
            }
        } else {
            SinglyLinkedListNode<T> tempCur = head;
            SinglyLinkedListNode<T> tempPrev = null;
            for (int i = 2; i < size; i++) {
                if (tempCur.getNext().getData().equals(data)) {
                    tempPrev = tempCur;
                }
                tempCur = tempCur.getNext();
            }
            if (head.getData().equals(data)) {
                return removeFromFront();
            } else if (tempPrev == null) {
                throw new java.util.NoSuchElementException("Element was not found in the list.");
            } else {
                SinglyLinkedListNode<T> tempRemove = tempPrev.getNext();
                T tempData = tempRemove.getData();
                tempPrev.setNext(tempRemove.getNext());
                tempRemove.setNext(null);
                size--;
                return tempData;
            }
        }
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] tArr = (T[]) new Object[size];
        for (int i = 0; i < size; i++) {
            tArr[i] = get(i);
        }
        return tArr;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public SinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public SinglyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}