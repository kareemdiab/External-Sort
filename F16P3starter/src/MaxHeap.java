import java.io.IOException;
import java.io.RandomAccessFile;

// Max-heap implementation by Patrick Sullivan, based on OpenDSA Heap code
// Can use `java -ea` (Java's VM arguments) to Enable Assertions
// These assertions will check for valid heap positions

// Many of these methods are not going to be useful for ExternalSorting...
// Prune those methods out if you don't want to test them.

/**
 * MaxHeap class to handle building and sorting and making appropriate calls to
 * buffer pool.
 * 
 * @author Kareem Diab (kareemdiab3)
 * @version 2022.10.23
 *
 * @param <T>
 */
class MaxHeap<T extends Comparable<T>> {
    // private ByteFile heap; // Pointer to the heap array
    // private int capacity; // Maximum size of the heap
    private int n; // Number of things currently in heap
    //private int size;
    private BufferPool pool;
    private int count;

// private MaxLinkedList<T> inList;
// private MaxLinkedList<T> outList;
//

    // Constructor supporting preloading of heap contents
    /**
     * Constructor to build heap with appropriate number of blocks and buffer
     * pool.
     * 
     * @param blocks
     *            Number of blocks.
     * @param bufferPool
     *            Bufferpool.
     * @throws IOException
     */
    public MaxHeap(int blocks, BufferPool bufferPool) throws IOException {
        // heap = h;
        // assert capacity <= heap.getNumBlocks() * 1024 : "capacity is beyond
        // array limits";
        // assert heapSize <= capacity : "Heap size is beyond max";
        this.n = blocks * ByteFile.RECORDS_PER_BLOCK;
        //this.size = n;
        pool = bufferPool;
        count = 0;

        // in = new BufferPool(file, capacity, 1024 / capacity);
        // out = new BufferPool(file, capacity, 1024 / capacity);
        buildHeap();

    }


    // Return position for left child of pos
    /**
     * Return position for left child of pos.
     * 
     * @param pos
     *            Current index.
     * @return Left child position.
     */
    public static int leftChild(int pos) {
        return 2 * pos + 1;
    }




    // Return position for the parent of pos
    /**
     * Return position for the parent of pos.
     * 
     * @param pos
     *            Current index.
     * @return Parent position.
     */
    public static int parent(int pos) {
        return (pos - 1) / 2;
    }




    // Return true if pos a leaf position, false otherwise
    /**
     * Return true if pos is a leaf position and false otherwise.
     * 
     * @param pos
     *            Current index.
     * @return True if leaf.
     */
    public boolean isLeaf(int pos) {
        return (n / 2 <= pos) && (pos < n);
    }




    // Organize contents of array to satisfy the heap structure
    /**
     * Organizes the contents of an array to satisfy the heap structure. In this
     * case the array will be the file.
     * 
     * @throws IOException
     */
    public void buildHeap() throws IOException {
        for (int i = parent(n - 1); i >= 0; i--) {
            siftDown(i);
        }
    }


    // Moves an element down to its correct place
    /**
     * Moves an element down to its correct position.
     * 
     * @param pos
     *            Position of element.
     * @return Returns the position.
     * @throws IOException
     */
    public int siftDown(int pos) throws IOException {
        assert (0 <= pos && pos < n) : "Invalid heap position ";
        while (!isLeaf(pos)) {
            int child = leftChild(pos);
            if ((child + 1 < n) && isGreaterThan(child + 1, child)) {
                child = child + 1; // child is now index with the smaller value
            }
            if (!isGreaterThan(child, pos)) {
                return pos; // stop early
            }
            swap(pos, child);
            pos = child; // keep sifting down
        }
        return pos;
    }





    // Remove and return maximum value
    /**
     * Removes the maximum value swaps with last value in array.
     * 
     * @throws IOException
     */
    public void removeMax() throws IOException {
        count++;
        assert n > 0 : "Heap is empty; cannot remove";
        n--;
        if (n > 0) {
            swap(0, n); // Swap maximum with last value
            siftDown(0); // Put new heap root val in correct place
        }

    }



    // swaps the elements at two positions
    /**
     * Swaps the elements at two positions.
     * 
     * @param pos1
     *            Position of first element.
     * @param pos2
     *            Position of second element.
     * @throws IOException
     */
    public void swap(int pos1, int pos2) throws IOException {
        Buffer buffer = pool.acquireBuffer(pos1);
        Record temp = buffer.getRecord(pos1);

        // System.out.println(temp);
        // System.out.println(buffer.getRecord(pos1 - 1));

        buffer = pool.acquireBuffer(pos2);

        Record temp2 = buffer.getRecord(pos2);

        buffer = pool.acquireBuffer(pos1);

        // buffer = pool.acquireBuffer(pos1);

        buffer.addRecord(pos1, temp2);
        // buffer.writeBack();

        buffer = pool.acquireBuffer(pos2);

        buffer.addRecord(pos2, temp);

    }


    // does fundamental comparison used for checking heap validity
    /**
     * Compares key values of different positions in the disk.
     * 
     * @param pos1
     *            First position.
     * @param pos2
     *            Second position.
     * @return True if pos1 key is greater than pos2 key.
     * @throws IOException
     */
    private boolean isGreaterThan(int pos1, int pos2) throws IOException {
        Buffer buffer = pool.acquireBuffer(pos1);
        Record one = buffer.getRecord(pos1);

        buffer = pool.acquireBuffer(pos2);

        Record two = buffer.getRecord(pos2);

        return one.getKey() > two.getKey();
    }


    /**
     * Will sort the heap by continuously discarding the max value and swapping
     * first and last positions.
     * 
     * @throws IOException
     */
    public void heapSort() throws IOException {
        // Record[] sorted = new Record[n];

        while (n > 0) {
            removeMax();
            // Buffer buffer = pool.acquireBuffer(n - 1);

            // buffer.addRecord(n - 1, removeMax());

        }

    }

}
