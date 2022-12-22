import java.io.IOException;
import student.TestCase;

/**
 * Test class for MaxHeap.
 * 
 * @author Kareem Diab (kareemdiab3)
 * @version 2022.10.15
 *
 */
public class MaxHeapTest extends TestCase {
    private ByteFile bFile;
    // private BufferPool pool;

    /**
     * Sets up.
     * 
     * @throws IOException
     */
    public void setUp() throws IOException {
        // pool = new BufferPool(bFile, 1, 1);
        bFile = new ByteFile("testFile", 1, 1);

    }


    /**
     * Tests the heap is built correctly.
     * 
     * @throws IOException
     */
    public void testHeap() throws IOException {
        System.out.println(bFile.getPool().acquireBuffer(0));
    }
// /**
// * Sample test method.
// */
// public void testMaxHeap() {
//
// Integer[] vals = { 2, 7, 4, 9, 2, 1 };
//
// MaxHeap<Integer> heap = new MaxHeap<Integer>(vals, 6, 6);
// // This constructor calls build-heap automatically
//
// assertEquals(6, heap.heapSize());
// assertEquals(9, (int)vals[0]);
// assertEquals(9, (int)heap.removeMax());
//
// assertEquals(7, (int)vals[0]);
// assertEquals(9, (int)vals[5]); // still in array, but out of heap
// assertEquals(5, heap.heapSize());
//
// heap.insert(3);
// assertEquals(7, (int)vals[0]);
// assertEquals(6, heap.heapSize());
//
// assertEquals(7, (int)heap.removeMax());
// assertEquals(4, (int)heap.removeMax());
// assertEquals(3, (int)heap.removeMax());
// assertEquals(2, (int)heap.removeMax());
// assertEquals(2, (int)heap.removeMax());
// assertEquals(1, (int)heap.removeMax());
// }

}
