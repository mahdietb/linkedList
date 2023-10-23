
public class LinkedListForSort{
    Node head = null;

    static class Node {
        int value;
        Node next;

        public Node(int value)
        {
            this.value = value;
        }
    }

    Node sortedMerge(Node a, Node b)
    {
        Node result = null;
        if (a == null)
            return b;
        if (b == null)
            return a;

        if (a.value <= b.value) {
            result = a;
            result.next = sortedMerge(a.next, b);
        }
        else {
            result = b;
            result.next = sortedMerge(a, b.next);
        }
        return result;
    }

    public Node mergeSort(Node h)
    {
        // Base case : if head is null
        if (h == null || h.next == null) {
            return h;
        }

        // get the middle of the list
        Node middle = getMiddle(h);
        Node nextOfMiddle = middle.next;

        // set the next of middle node to null
        middle.next = null;

        // Apply mergeSort on left list
        Node left = mergeSort(h);

        // Apply mergeSort on right list
        Node right = mergeSort(nextOfMiddle);

        // Merge the left and right lists
        return sortedMerge(left, right);
    }

    // Utility function to get the middle of the linked list
    public Node getMiddle(Node head)
    {
        if (head == null)
            return head;

        Node slow = head, fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

   public void push(int newData)
    {
        /* allocate node */
        Node newNode = new Node(newData);

        /* link the old list of the new node */
        newNode.next = head;

        /* move the head to point to the new node */
        head = newNode;
    }


    void printList(Node headRef)
    {
        while (headRef != null) {
            System.out.print(headRef.value + " ");
            headRef = headRef.next;
        }
    }
    public void sort() {
        System.out.print("before sort");
        printList(head);
        head=mergeSort(head);
        System.out.println();
        System.out.print("after sort :");
        printList(head);
    }

}


