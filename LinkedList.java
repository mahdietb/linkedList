import java.util.NoSuchElementException;

public class LinkedList<T> {


    private class Node {
        private T value;
        int key;


        private Node next;
        private Node previous;

        public Node(T value) {
            this.value = value;
        }

    }


    private Node first;
    private Node last;
    private int size;


    public Node getHead() {
        return first;
    }

    public Node getTail() {
        return last;
    }

    public T getFirst() {
        var node = first;
        if (node == null)
            throw new NoSuchElementException();
        return node.value;

    }


    public T getLast() {
        var node = last;
        if (last == null)
            throw new NoSuchElementException();
        return node.value;
    }

    public void addLast(T item) {
        var node = new Node(item);
        if (isEmpty())
            first = last = node;
        else {
            last.next = node;
            node.previous = last;
            last = node;

        }
        size++;
    }


    public void add(int position, T item) {
        var current = first;
        int currentPos = 0;
        var node = new Node(item);
        checkPositionIndex(position);
        if (position == 0)
            addFirst(item);
        else if (first == last) {
            addLast(item);
        } else {
            while (current != null && currentPos < position) {
                current = current.next;
                currentPos++;
            }
            if (current == null)
                addLast(item);
            else {
                node.next = current;
                node.previous = current.previous;
                current.previous.next = node;
                current.previous = node;
            }

        }
        size++;
    }

    public void addFirst(T item) {
        var node = new Node(item);
        if (isEmpty())
            first = last = node;
        else {
            node.next = first;
            first.previous = node;
            first = node;

        }
        size++;

    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index))
            throw new IndexOutOfBoundsException("position not found");
    }

    private boolean isIndexValid(int index) {
        return index >= 0 && index < size;
    }

    private void checkIndex(int index) {
        if (!isIndexValid(index))
            throw new IndexOutOfBoundsException("position not found");
    }

    public Node getNode(int index) {

        checkIndex(index);
        if (index < (size() / 2)) {
            var current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            var current = last;
            for (int i = size(); i > index; i--) {
                current = current.previous;
            }
            return current;
        }

    }

    public T get(int index) {
        return getNode(index).value;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int indexOf(T item) {
        int index = 0;
        var current = first;
        while (current != null) {
            if (current.value == item)
                return index;
            current = current.next;
            index++;
        }
        return -1;
    }

    public boolean contains(T item) {
        return indexOf(item) != -1;
    }

    public void removeFirst() {
        //for the time that our list is empty
        if (isEmpty())
            throw new NoSuchElementException();
        //for when we have just one item
        if (first == last)
            first = last = null;
        else {
            //it is for when at least we have two items in our list
            var second = first.next;
            first.next = null;
            first = second;
            first.previous = null;
        }
        size--;
    }


    public T remove(int index) {
        var current = first;
        int currentPos = 0;
        T value = null;
        if (isEmpty()) {
            System.out.println("list is empty");
        } else {
            if (index == 0) {
                removeFirst();
            } else if (index == size() - 1) {
                removeLast();
            } else {
                while (current != null && currentPos < index) {
                    current = current.next;
                    currentPos++;
                }
                assert current != null;
                current.previous.next = current.next;
                current.next.previous = current.previous;
                value = current.value;
                current.value = null;
                size--;
            }
        }
        return value;
    }


    public void removeLast() {
        //[10->20->30]
        //last->30
        //previous ->20
        if (isEmpty())
            throw new NoSuchElementException();
        if (first == last)
            first = last = null;
        else {
            var node = last.previous;
            last = node;
            last.next = null;
        }

        size--;
    }


    //complexity->O(1)
    public int size() {
        return size;
    }

    public Object[] toArray() {
        Object[] array = new Object[size()];
        var current = first;
        int index = 0;
        while (current != null) {
            array[index++] = current.value;
            current = current.next;
        }
        return array;
    }

    public void printList() {
        var current = first;
        if (isEmpty()) {
            System.out.println("list is empty");
        } else {
            while (current != null) {
                System.out.print(current.value);
                if (current.next != null)
                    System.out.print(",");
                current = current.next;
            }
        }
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Node current = first;
        while (current.next != null) {
            result.append(current.value);
            current = current.next;
        }
        return result.toString();
    }

    public LinkedList<Integer> mergeTwoSortedLists(LinkedList<Integer> halfOne, LinkedList<Integer> halfTwo) {
        LinkedList<Integer> mainList = new LinkedList<>();

        var one = halfOne.first;
        var two = halfTwo.first;
        while (one != null && two != null) {
            if (one.value < two.value) {
                mainList.addLast(one.value);
                one = one.next;
            } else {
                mainList.addLast(two.value);
                two = two.next;
            }
        }

        while (one != null) {
            mainList.addLast(one.value);
            one = one.next;
        }

        while (two != null) {
            mainList.addLast(two.value);
            two = two.next;
        }

        return mainList;
    }

    public Node midNode(Node head, Node tail) {
        Node fast = head;
        Node slow = head;

        while (fast != tail && fast.next != tail) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }

    public LinkedList<Integer> mergeSort(Node head, Node tail) {
        if (head == tail) {
            LinkedList<Integer> basic = new LinkedList<>();
            basic.addLast((Integer) head.value);
            return basic;
        }

        Node mid = midNode(head, tail);
        LinkedList<Integer> leftHalf = mergeSort(head, mid);
        LinkedList<Integer> rightHalf = mergeSort(mid.next, tail);
        return mergeTwoSortedLists(leftHalf, rightHalf);
    }

    public void sort(LinkedList<Integer> list) {
        System.out.println("before sort : ");
        printList();
        System.out.println();
        list = list.mergeSort(list.getHead(), list.getTail());
        System.out.println("after sort : ");
        list.printList();
    }

    public void reverse() {
        if (isEmpty()) return;
        var previous = first;
        var current = first;
        while (current != null) {
            var next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        last = first;
        last.next = null;
        first = previous;
    }

    public T KthElementFromEnd(int k) {
        if (isEmpty())
            throw new IllegalArgumentException();
        var a = first;
        var b = first;
        for (int i = 0; i < k - 1; i++) {
            b = b.next;
            if (b == null)
                throw new IllegalArgumentException();
            while (b != last) {
                a = a.next;
                b = b.next;
            }
        }
        return a.value;
    }
}