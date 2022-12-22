import java.io.IOException;
import student.TestCase;

/**
 * Test class for max linked list.
 * 
 * @author Kareem Diab (kareemdiab3)
 * @version 2022.10.14
 */
public class MaxLinkedListTest extends TestCase {

    // USE THIS IN BUFFERPOOL TEST INSTEAD.
    private MaxLinkedList<Buffer> mLL;
    private ByteFile bFile;
    private Buffer buf1;
    private Buffer buf2;
    private Buffer buf3;
    private Buffer buf4;
    private Buffer buf5;
    private Buffer buf6;

    /**
     * Sets up.
     * 
     * @throws IOException
     */
    public void setUp() throws IOException {
        mLL = new MaxLinkedList<Buffer>(5);
        bFile = new ByteFile("testFile.bin", 6, 6);

        buf1 = new Buffer(bFile, 1);
        buf2 = new Buffer(bFile, 2);
        buf3 = new Buffer(bFile, 3);
        buf4 = new Buffer(bFile, 4);
        buf5 = new Buffer(bFile, 5);
        buf6 = new Buffer(bFile, 6);

    }


    /**
     * Tests toString method.
     * 
     * @throws IOException
     */
    public void testToString() throws IOException {
        mLL.insert(buf1);
        mLL.insert(buf2);
        mLL.insert(buf3);
        mLL.insert(buf4);
        mLL.insert(buf5);
        // System.out.println(mLL.toString());
        mLL.insert(buf6);
        // System.out.println(mLL.toString());
        mLL.moveToFront(buf3);
        // System.out.println(mLL.toString());

    }


    /**
     * Tests insert and get methods.
     * 
     * @throws IOException
     */
    public void testGetItems() throws IOException {
        mLL.insert(buf1);
        mLL.insert(buf2);
        mLL.insert(buf3);
        mLL.insert(buf4);
        mLL.insert(buf5);

        assertNotNull(mLL.get(1));
        // System.out.println(mLL.toString());
        assertNotNull(mLL.get(2));
        assertNotNull(mLL.get(3));
        assertNotNull(mLL.get(4));
        assertNotNull(mLL.get(5));

        // System.out.println(mLL.get(1).getBlock());
    }


    /**
     * Tests moveToFront method.
     * 
     * @throws IOException
     */
    public void testMoveToFront() throws IOException {
        mLL.insert(buf1);
        mLL.insert(buf2);
        mLL.insert(buf3);
        mLL.insert(buf4);
        mLL.insert(buf5);

        System.out.println(mLL.toString());
        mLL.moveToFront(buf1);
        System.out.println(mLL.toString());
        mLL.moveToFront(buf5);
        mLL.moveToFront(buf5);
        System.out.println(mLL.toString());
        assertNotNull(mLL.get(5));
    }
}
