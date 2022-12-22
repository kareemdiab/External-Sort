import java.io.IOException;
import student.TestCase;

/**
 * Buffer pool test class.
 * 
 * @author Kareem Diab
 * @version 2022.10.12
 *
 */
public class BufferPoolTest extends TestCase {

    /**
     * Sets up test class.
     * 
     * @throws IOException
     */
    public void setUp() throws IOException {

        // bFile = new ByteFile("testFile.bin", 3, 3);
        // pool = new BufferPool(bFile, 3);
        // bFile.writeRandomRecords();

    }



    /**
     * Tests that buffer pool still works when its smaller than the amount of
     * buffers in the file.
     * 
     * @throws IOException
     */
    public void testSmallBufferPool() throws IOException {

        ByteFile bFile = new ByteFile("tes.bin", 1, 2);
        // bFile.writeRandomRecords();
        // System.out.println(bFile.secondDump());

        bFile.sortFile();
        // System.out.println(bFile.secondDump());

        assertTrue(bFile.isSorted());

        // System.out.println("removeMax Count: " + bFile.count());

        // assertTrue(bFile.isSorted());

        // System.out.println(bFile.dump());
        // assertEquals(pool.getCacheMisses(), 3);
        // System.out.println(pool.getList().toString());
    }


    // ERRORS: 858, 850, 838, 827, 816, 802, 800, 786, 761
    /**
     * Ensures a big enough buffer pool won't have too many cache misses.
     * 
     * @throws IOException
     */
    public void testBigBufferPool() throws IOException {

        ByteFile bFile = new ByteFile("testFile.bin", 7, 7);

        bFile.sortFile();
        // System.out.println(bFile.secondDump());

        assertTrue(bFile.isSorted());
        // assertEquals(bFile.getCacheMisses(), 3);

    }


    /**
     * Tests heap sort on large file with small buffer pool.
     * 
     * @throws IOException
     */
    public void testHugeFile() throws IOException {
        ByteFile bFile = new ByteFile("newFile.bin", 20, 50);
        // System.out.println(bFile.dump());
        //System.out.println(bFile.dump());
        assertFalse(bFile.isSorted());
        bFile.sortFile();
        System.out.println(bFile.dump());

        // System.out.println(bFile.dump());
        // System.out.println(bFile.secondDump());
        assertTrue(bFile.isSorted());
    }


    /**
     * Tests that getBlock properly calculates the block from index.
     * 
     * @throws IOException
     */
    public void testGetBlock() throws IOException {
        ByteFile bFile = new ByteFile("testFile.bin", 10, 20);
        BufferPool calc = new BufferPool(bFile, 10, 10);
        assertEquals(calc.getBlock(1000), 0);
        assertEquals(calc.getBlock(0), 0);
        assertEquals(calc.getBlock(1023), 0);
        assertEquals(calc.getBlock(1024), 1);
        assertEquals(calc.getBlock(2047), 1);
        assertEquals(calc.getBlock(2048), 2);
    }


    /**
     * Tests what webcat says is missing.
     * 
     * @throws IOException
     */
    public void testMore() throws IOException {
        ByteFile bFile = new ByteFile("testFile.bin", 9, 9);
        bFile.sortFile();
        assertTrue(bFile.isSorted());
    }


    /**
     * Tests acuireBuffer correctly reads from disk and buffer pool.
     * 
     * @throws IOException
     */
    public void testAcquireBuffer() throws IOException {
        ByteFile bFile = new ByteFile("testFile.bin", 8, 50);
        BufferPool pool = bFile.getPool();
        // System.out.println(pool.getList().toString());
        pool.acquireBuffer(6000);
        pool.acquireBuffer(5000);
        pool.acquireBuffer(4000);
        pool.acquireBuffer(3000);
        pool.acquireBuffer(2000);
        pool.acquireBuffer(6000);
        pool.acquireBuffer(2000);
        // System.out.println(pool.getList().toString());

    }

}
