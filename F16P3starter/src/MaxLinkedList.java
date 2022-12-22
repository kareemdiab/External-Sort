import java.io.IOException;

/**
 * This class will represent the buffer pool for the heap.
 * 
 * @author Kareem Diab (kareemdiab3)
 * @version 2022.10.12
 * @param <T>
 *            Generic type for node data.
 *
 */
public class MaxLinkedList<T> {

    private int max;
    // private File stats;

    private Node<T> head;

    private int size;
    

    /**
     * Constructs with given max number of buffers.
     * 
     * @param maxBuffers
     *            Max capacity of linked list.
     */
    public MaxLinkedList(int maxBuffers) {
        this.max = maxBuffers;
        // this.stats = statsFile;
        this.head = null;
        this.size = 0;


    }


    /**
     * Size getter method.
     * 
     * @return Size.
     */
    public int getSize() {
        return size;
    }


    /**
     * Adds the object to the position in the list
     *
     * @param data
     *            the object to add
     * @throws IOException
     * 
     */
    public void insert(T data) throws IOException {
        // check if the object is null

        if (data == null) {
            throw new IllegalArgumentException("Record is null");
        }
        if (isEmpty()) {
            head = new Node<T>(data);
            size++;
            return;

        }
        size++;

        Node<T> newNode = new Node<T>(data);
        newNode.setNext(head);
        head = newNode;
        if (size > max) {
            removeLastNode();
            size--;
        }

    }


    /**
     * Swaps node to front of list if just used.
     * 
     * @param buf
     *            Buffer in node of interest.
     */
    public void moveToFront(Buffer buf) {

        Node<T> curr = head;
//        if (curr != null && ((Buffer)curr.getData()).getBlock() == buf
//            .getBlock()) {
//            return;
//        }
        @SuppressWarnings("unchecked")
        Node<T> newFront = new Node<T>((T)buf, head);

        while (curr.next != null) {
            if (((Buffer)curr.next.getData()).getBlock() == buf.getBlock()) {
                curr.setNext(curr.next.next);
                head = newFront;
                return;
            }
            curr = curr.next;

        }

    }


    /**
     * Removes the last node of in the list.
     *
     * @return true if successful
     * @throws IOException
     */
    public boolean removeLastNode() throws IOException {
        Node<T> current = head;
       

        // account for matching head
        if (null == head) {
            return false;
        }
        if (current.next == null) {
            ((Buffer)current.getData()).writeBack();

            current = null;
            return true;
        }

        // account for 2+ size
        while (current.next.next != null) {

            current = current.next;
        }
        ((Buffer)current.next.getData()).writeBack();
        current.setNext(null);

        // this accounts for the isEmpty case or the object does not exist

        return true;
    }


 


    /**
     * Checks if buffer pool is empty.
     * 
     * @return True if empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }


//    /**
//     * ToString method for the linked list.
//     * 
//     * @return String result.
//     */
//    public String toString() {
//        String result = "LinkedList Size: " + size + "\n";
//
//        Node<T> curr = head;
//        if (curr.next == null) {
//            result += "Block: " + ((Buffer)curr.getData()).getBlock() + "\n";
//            // result += ((Buffer)curr.getData()).toString();
//            return result;
//        }
//        while (curr.next != null) {
//            result += "Block: " + ((Buffer)curr.getData()).getBlock() + "\n";
//            // result += ((Buffer)curr.getData()).toString();
//            curr = curr.next;
//        }
//        result += "Block: " + ((Buffer)curr.getData()).getBlock();
//        return result;
//    }


    /**
     * Returns buffer within the linked list.
     * 
     * @param block
     *            Block of buffer to find.
     * @return Returns the buffer.
     */
    public T get(int block) {

        if (head == null) {
            return null;
        }
        Node<T> curr = head;
        while (curr.next != null) {
            if (((Buffer)curr.getData()).getBlock() == block) {
                return curr.getData();
            }
            curr = curr.next;
        }
        if (((Buffer)curr.getData()).getBlock() == block) {
            return curr.getData();
        }
        else {
            return null;
        }

    }

    /**
     * Node class for max linked list.
     * 
     * @author Kareem Diab (kareemdiab3)
     * @version 2022.10.15
     *
     * @param <T>
     */
    public static class Node<T> {
        private Node<T> next;
        private T data;

        /**
         * Node constructor that sets record and next node.
         * 
         * @param rec
         *            Record the node will hold.
         * @param nextNode
         *            Next node.
         */
        public Node(T rec, Node<T> nextNode) {
            this.next = nextNode;
            this.data = rec;
        }


        /**
         * Constructor that doesn't specify next node.
         * 
         * @param rec
         *            Record the node will hold.
         */
        public Node(T rec) {
            this.data = rec;
        }


        /**
         * Next node setter.
         * 
         * @param nextNode
         *            Next node.
         */
        public void setNext(Node<T> nextNode) {
            this.next = nextNode;
        }


        /**
         * Next Node getter.
         * 
         * @return Returns next node.
         */
        public Node<T> getNext() {
            return this.next;
        }


        /**
         * Record getter.
         * 
         * @return Returns record.
         */
        public T getData() {
            return this.data;
        }


        /**
         * Sets the record that the node will hold.
         * 
         * @param rec
         *            Record to be stored.
         */
        public void setData(T rec) {
            this.data = rec;
        }
    }

}
